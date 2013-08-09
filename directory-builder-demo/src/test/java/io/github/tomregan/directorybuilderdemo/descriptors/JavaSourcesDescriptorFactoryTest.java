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

import directorybuilder.descriptors.DirectoryDescriptor;
import directorybuilder.descriptors.FileDescriptor;
import io.github.tomregan.directorybuilderdemo.messaging.MessageService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class JavaSourcesDescriptorFactoryTest
{

    private JavaSourcesDescriptorFactory factory;

    @Before
    public void setUp()
    {
        factory = JavaSourcesDescriptorFactory.newInstance(MessageService.newInstance());
    }

    @Test
    public void shouldReturnInstanceOfJavaSourceDescriptorFactory()
    {
        assertThat("not an instance of JavaSourcesDescriptorFactory", JavaSourcesDescriptorFactory.newInstance(null), instanceOf(JavaSourcesDescriptorFactory.class));
    }

    @Test
    public void shouldReturnCorrectDescriptorForElement()
    {
        assertThat("did not return directory descriptor", factory.getDescriptorForElement("directory"), instanceOf(DirectoryDescriptor.class));
        assertThat("did not return file descriptor", factory.getDescriptorForElement("file"), instanceOf(FileDescriptor.class));
        assertThat("did not return source directory descriptor", factory.getDescriptorForElement("sourcedirectory"), instanceOf(SourceDirectoryDescriptor.class));
        assertThat("did not return java file descriptor", factory.getDescriptorForElement("javafile"), instanceOf(JavaFileDescriptor.class));
    }
}
