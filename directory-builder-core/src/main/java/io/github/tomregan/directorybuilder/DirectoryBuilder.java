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

package io.github.tomregan.directorybuilder;


import io.github.tomregan.directorybuilder.descriptors.Descriptor;

import java.io.File;
import java.io.IOException;

/**
 * Creates directories and files from a descriptor tree or trees.
 */
public class DirectoryBuilder
{
    private final File rootDirectory;

    private DirectoryBuilder(File rootDirectory)
    {
        this.rootDirectory = rootDirectory;
    }

    /**
     * Creates a new DirectoryBuilder.
     *
     * @param rootDirectory
     * @return a DirectoryBuilder.
     */
    public static DirectoryBuilder newInstance(File rootDirectory)
    {
        return new DirectoryBuilder(rootDirectory);
    }

    /**
     * Creates directories and files on the file system.
     *
     * @param descriptors one or more directory / file descriptor trees.
     * @throws IOException
     * @see ConfigurationProcessor
     */
    public void createDirectoryStructure(Descriptor... descriptors) throws IOException
    {
        for (Descriptor descriptor : descriptors)
        {
            descriptor.create(rootDirectory);
        }
    }
}
