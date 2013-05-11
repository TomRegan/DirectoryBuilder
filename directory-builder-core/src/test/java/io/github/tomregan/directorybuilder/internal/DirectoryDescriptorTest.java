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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectoryDescriptorTest
{
    @Test
    public void testObjectEquality()
    {
        DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.newInstance();
        assertEquals("DirectoryDescriptor not equal to itself", directoryDescriptor, directoryDescriptor);
    }

    @Test
    public void testEqualityOnName()
    {
        DirectoryDescriptor a0 = DirectoryDescriptor.newInstance();
        DirectoryDescriptor a1 = DirectoryDescriptor.newInstance();
        a0.setProperty("name", "a");
        a1.setProperty("name", "a");
        assertEquals("a0 not equal to a1", true, a0.equals(a1));
    }

    @Test
    public void testEqualityWithOtherObjects()
    {
        DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.newInstance();
        String string = "bar";
        //noinspection EqualsBetweenInconvertibleTypes
        assertEquals("did not identify inequality of DirectoryDescriptor and String", false, directoryDescriptor.equals(string));
    }

    @Test
    public void testEqualityWithChildren()
    {
        DirectoryDescriptor a = DirectoryDescriptor.newInstance();
        DirectoryDescriptor b = DirectoryDescriptor.newInstance();
        DirectoryDescriptor c = DirectoryDescriptor.newInstance();
        a.setProperty("name", "a");
        b.setProperty("name", "a");
        c.setProperty("name", "c");
        b.addChild(c);
        assertEquals("a equal to b", false, a.equals(b));
        a.addChild(c);
        assertEquals("a not equal to b", true, a.equals(b));
    }

}
