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

import io.github.tomregan.directorybuilder.descriptors.FileDescriptor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class TemplateFile extends File
{
    private final File template;
    private final FileDescriptor delegate;

    public TemplateFile(File template, File parentDirectory, String name, FileDescriptor delegate)
    {
        super(parentDirectory, name);
        this.template = template;
        this.delegate = delegate;
    }

    public boolean createNewFile() throws IOException
    {
        if (super.createNewFile())
        {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put(delegate.getDescriptorId(), delegate);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(super.getAbsoluteFile()));
            // TODO set an appropriate resource loader
            // see
            // https://velocity.apache.org/engine/releases/velocity-1.5/developer-guide.html#resourceloaders
            // should template name be a URI?
            try
            {
                Template velocityTemplate = Velocity.getTemplate(template.getPath());
                velocityTemplate.merge(velocityContext, bufferedWriter);
                bufferedWriter.flush();
            }
            catch (Exception e)
            {
                // to be substitutable, we have to ignore this exception
            }
            finally
            {
                bufferedWriter.close();
            }
            return true;
        }
        return false;
    }

}
