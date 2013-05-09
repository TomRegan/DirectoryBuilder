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

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FileDescriptorTest
{

    private File template;

    @Before
    public void setUp()
    {
        template = new File("");
    }

    @Test
    public void testGetClassName() throws Exception
    {
        assertEquals("name was not FileDescriptor", "FileDescriptor", FileDescriptor.newInstance(template).getClassName());
    }

    @Test
    public void testEqualsDetectsIdentity()
    {
        FileDescriptor a = FileDescriptor.newInstance(template);
        a.setName("a.txt");
        assertEquals("a not equal to a", a, a);
    }

    @Test
    public void testEqualsSelectsOnName()
    {
        FileDescriptor a = FileDescriptor.newInstance(template);
        a.setName("a.txt");
        FileDescriptor b = FileDescriptor.newInstance(template);
        b.setName("a.txt");
        FileDescriptor c = FileDescriptor.newInstance(template);
        c.setName("c.txt");
        assertEquals("a not equal to b", a, b);
        assertFalse("a equal to c", a.equals(c));
    }

    @Test
    public void testEqualsDetectsDifferentClasses()
    {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse("FileDescriptor equal to String", FileDescriptor.newInstance(template).equals(""));
    }

    @Test
    public void testEqualsSelectsOnTemplate()
    {
        FileDescriptor a = FileDescriptor.newInstance(template);
        a.setName("a.txt");
        FileDescriptor b = FileDescriptor.newInstance(new File("a.template"));
        b.setName("a.txt");
        FileDescriptor c = FileDescriptor.newInstance(new File("a.template"));
        c.setName("a.txt");
        assertFalse("a equal to b", a.equals(b));
        assertEquals("b not equal to c", b, c);
    }

    @Test
    public void testGetters()
    {
        FileDescriptor fileDescriptor = FileDescriptor.newInstance(template);
        fileDescriptor.setName("SomeName.txt");
        // getName is only used in the test template, so it's not excercised by other tests
        assertEquals("getter did not return name", "SomeName.txt", fileDescriptor.getName());
    }
}
