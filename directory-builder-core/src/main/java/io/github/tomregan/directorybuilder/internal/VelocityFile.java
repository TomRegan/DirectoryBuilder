/*
 * Copyright 2013 Tom Regan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.tomregan.directorybuilder.internal;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class VelocityFile extends File
{
    private final File template;
    private VelocityEngine velocityEngine;
    private VelocityContext velocityContext;

    public VelocityFile(File template, File parentDirectory, String name, VelocityProvider velocityProvider)
    {
        super(parentDirectory, name);
        this.template = template;
        setUpVelocityFromProvider(velocityProvider);
    }

    private void setUpVelocityFromProvider(VelocityProvider velocityProvider)
    {
        this.velocityEngine = velocityProvider.getVelocityEngine();
        this.velocityContext = velocityProvider.getVelocityContext();
    }

    public boolean createNewFile() throws IOException
    {
        if (super.createNewFile())
        {
            writeFileContentsFromTemplate();
            return true;
        }
        return false;
    }

    private void writeFileContentsFromTemplate() throws IOException
    {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(super.getAbsoluteFile()));
        try
        {
            Template velocityTemplate = velocityEngine.getTemplate(template.getPath());
            velocityTemplate.merge(velocityContext, bufferedWriter);
            bufferedWriter.flush();
        }
        catch (Exception e)
        {
            throw new IOException();
        }
        finally
        {
            bufferedWriter.close();
        }
    }

}
