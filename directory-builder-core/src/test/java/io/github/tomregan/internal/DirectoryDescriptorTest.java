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

package io.github.tomregan.internal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectoryDescriptorTest
{
    @Test
    public void shouldTestEqualityOnName()
    {
        DirectoryDescriptor a0 = DirectoryDescriptor.createDirectoryDescriptor("a");
        DirectoryDescriptor a1 = DirectoryDescriptor.createDirectoryDescriptor("a");
        assertEquals("a0 not equal to a1", true, a0.equals(a1));
    }

    @Test
    public void shouldTestEqualityWithOtherObjects()
    {
        DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.createDirectoryDescriptor("foo");
        String string = "bar";
        assertEquals("did not identify inequality of DirectoryDescriptor and String", false, directoryDescriptor.equals(string));
    }

    @Test
    public void shouldTestEqualityWithChildren()
    {
        DirectoryDescriptor a = DirectoryDescriptor.createDirectoryDescriptor("foo");
        DirectoryDescriptor b = DirectoryDescriptor.createDirectoryDescriptor("foo");
        DirectoryDescriptor c = DirectoryDescriptor.createDirectoryDescriptor("bar");
        b.addChild(c);
        assertEquals("did not handle presence of children", false, a.equals(b));
    }
}
