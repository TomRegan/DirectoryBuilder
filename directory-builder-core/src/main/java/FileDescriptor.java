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

import java.io.File;
import java.io.IOException;

public class FileDescriptor implements IDescriptor
{
    private final FileFactory fileFactory;
    private final String name;

    private FileDescriptor(String name)
    {
        this.fileFactory = FileFactory.createFileFactory();
        this.name = name;
    }

    public FileDescriptor(String name, FileFactory fileFactory)
    {
        this.fileFactory = fileFactory;
        this.name = name;
    }

    public static FileDescriptor createFileDescriptor(String name)
    {
        return new FileDescriptor(name);
    }

    public static FileDescriptor createFileDescriptor(String name, FileFactory fileFactory)
    {
        return new FileDescriptor(name, fileFactory);
    }

    @Override
    public void create(File parentDirectory) throws IOException
    {
        File file = fileFactory.createFile(parentDirectory, name);
        if (!file.createNewFile())
        {
            throw new IOException("could not create file " + file.getAbsolutePath());
        }
    }
}
