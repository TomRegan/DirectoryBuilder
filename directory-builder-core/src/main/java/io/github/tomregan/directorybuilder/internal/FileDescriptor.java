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

import java.io.File;
import java.io.IOException;

public class FileDescriptor implements IDescriptor
{
    private final FileFactory fileFactory;
    private final String name;
    private final File template;

    private FileDescriptor(File template, String name, FileFactory fileFactory)
    {
        this.template = template;
        this.fileFactory = fileFactory;
        this.name = name;
    }

    private FileDescriptor(File template, String name)
    {
        this(template, name, FileFactory.createFileFactory());
    }

    @SuppressWarnings("SameParameterValue")
    public static FileDescriptor createFileDescriptor(File template, String name, FileFactory fileFactory)
    {
        return new FileDescriptor(template, name, fileFactory);
    }

    public static FileDescriptor createFileDescriptor(File template, String name)
    {
        return new FileDescriptor(template, name);
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

    public String getClassName()
    {
        return getClass().getSimpleName();
    }

    @SuppressWarnings("unused") // used in template
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
        return name.equals(that.name) && template.getAbsolutePath().equals(that.template.getAbsolutePath());
    }

}
