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

package io.github.tomregan.directorybuilderdemo.descriptors;

import com.google.common.io.Files;
import io.github.tomregan.directorybuilder.descriptors.DirectoryDescriptor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SourceDirectoryDescriptorTest
{
    @Test
    public void shouldCreateDirectory() throws IOException
    {
        File rootDir = Files.createTempDir();
        DirectoryDescriptor src = DirectoryDescriptor.newInstance();
        DirectoryDescriptor main = DirectoryDescriptor.newInstance();
        SourceDirectoryDescriptor java = SourceDirectoryDescriptor.newInstance();
        src.setValueForAttribute("name", "src");
        main.setValueForAttribute("name", "main");
        java.setValueForAttribute("name", "java");
        src.addChild(main);
        main.addChild(java);
        src.create(rootDir);
        assertEquals("did not create source directory", true, new File(rootDir, "src/main/java").isDirectory());
    }
}
