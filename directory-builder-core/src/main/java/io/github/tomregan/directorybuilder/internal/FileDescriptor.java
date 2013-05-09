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

import io.github.tomregan.directorybuilder.Descriptor;

import java.io.File;
import java.io.IOException;

public class FileDescriptor implements Descriptor
{
    private final FileFactory fileFactory;
    private String name;
    private final File template;

    private FileDescriptor(File template, FileFactory fileFactory)
    {
        this.template = template;
        this.fileFactory = fileFactory;
    }

    private FileDescriptor(File template)
    {
        this(template, FileFactory.createFileFactory());
    }

    @SuppressWarnings("SameParameterValue")
    public static FileDescriptor newInstance(File template, FileFactory fileFactory)
    {
        return new FileDescriptor(template, fileFactory);
    }

    public static FileDescriptor newInstance(File template)
    {
        return new FileDescriptor(template);
    }

    @Override
    public void create(File parentDirectory) throws IOException
    {
        File file = fileFactory.createFile(template, parentDirectory, name, this);
        if (!file.createNewFile())
        {
            throw new IOException("could not create " + file.getAbsolutePath());
        }
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    public String getClassName()
    {
        return getClass().getSimpleName();
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (super.equals(0))
        {
            return true;
        }
        if (!(getClass() == o.getClass()))
        {
            return false;
        }
        FileDescriptor that = (FileDescriptor) o;
        if (name != null ? !name.equals(that.name) : that.name != null)
        {
            return false;
        }
        return template.getAbsolutePath().equals(that.template.getAbsolutePath());
    }

}
