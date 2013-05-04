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
import java.util.ArrayList;
import java.util.List;

public class DirectoryDescriptor implements IDescriptor
{
    private final FileFactory fileFactory;
    private final String name;
    private List<IDescriptor> children;

    private DirectoryDescriptor(String name)
    {
        this.fileFactory = FileFactory.createFileFactory();
        this.name = name;
    }

    private DirectoryDescriptor(String name, FileFactory fileFactory)
    {
        this.fileFactory = fileFactory;
        this.name = name;
    }

    public static DirectoryDescriptor createDirectoryDescriptor(String name)
    {
        return new DirectoryDescriptor(name);
    }

    public static DirectoryDescriptor createDirectoryDescriptor(String name, FileFactory fileFactory)
    {
        return new DirectoryDescriptor(name, fileFactory);
    }

    public void addChild(IDescriptor descriptor)
    {
        if (!hasChildren())
        {
            children = new ArrayList<IDescriptor>();
        }
        children.add(descriptor);
    }

    public void create(File parentDirectory) throws IOException
    {
        File currentDirectory = fileFactory.createFile(parentDirectory, name);
        if (!currentDirectory.mkdir())
        {
            throw new IOException("could not create directory " + currentDirectory.getAbsolutePath());
        }
        if (hasChildren())
        {
            for (IDescriptor child : children)
            {
                child.create(currentDirectory);
            }
        }
    }

    private boolean hasChildren()
    {
        return children != null;
    }
}
