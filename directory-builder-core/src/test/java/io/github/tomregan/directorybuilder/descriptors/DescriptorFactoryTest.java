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

package io.github.tomregan.directorybuilder.descriptors;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class DescriptorFactoryTest
{
    private DescriptorFactory descriptorFactory;

    @Before
    public void setUp()
    {
        descriptorFactory = DescriptorFactory.newInstance();
    }

    @Test
    public void testNewInstanceReturnsDescriptorFactory()
    {
        assertThat("did not return DescriptorFactory", DescriptorFactory.newInstance(),
                instanceOf(DescriptorFactory.class));
    }

    @Test
    public void shouldReturnCorrectDescriptor()
    {
        assertThat("did not return DirectoryDescriptor", descriptorFactory.getDescriptorForElement("directory"),
                instanceOf(DirectoryDescriptor.class));
        assertThat("did not return FileDescriptor", descriptorFactory.getDescriptorForElement("file"),
                instanceOf(FileDescriptor.class));
    }
}
