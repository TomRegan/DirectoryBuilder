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
import io.github.tomregan.directorybuilderdemo.messaging.MessageService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaFileDescriptorTest
{

    private JavaFileDescriptor descriptor;

    @Before
    public void setUp() throws Exception
    {
        descriptor = JavaFileDescriptor.newInstance(MessageService.newInstance());
    }

    @Test
    public void testGetters()
    {
        assertEquals("HelloWorld", descriptor.getClassName());
        assertEquals("you", descriptor.getUser());
    }

    @Test
    public void shouldCreateNewJavaFile() throws IOException
    {
        File rootDirectory = Files.createTempDir();
        descriptor.setValueForAttribute("name", "HelloWorld.java");
        descriptor.setValueForAttribute("template", "src/main/resources/HelloWorld.vm");
        String filename = descriptor.getValueForAttribute("name");
        descriptor.create(rootDirectory);
        assertEquals("did not create java file", true, new File(rootDirectory, filename).isFile());
    }

    @Test
    public void shouldReqisterForUserUpdates()
    {
        MessageService messageService = MessageService.newInstance();
        descriptor = JavaFileDescriptor.newInstance(messageService);
        messageService.updateSubject("user", "stink");
        assertEquals("did not set name", descriptor.getUser(), "stink");
    }


}
