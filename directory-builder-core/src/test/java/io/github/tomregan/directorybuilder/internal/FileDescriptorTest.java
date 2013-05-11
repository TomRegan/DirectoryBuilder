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

import java.util.Properties;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class FileDescriptorTest
{

    private String template;

    private FileDescriptor getFileDescriptor(String name, String template)
    {
        FileDescriptor fileDescriptor = FileDescriptor.newInstance();
        fileDescriptor.setProperty("name", name);
        fileDescriptor.setProperty("template", template);
        return fileDescriptor;
    }

    @Before
    public void setUp()
    {
        template = "";
    }

    @Test
    public void testGetClassName() throws Exception
    {
        assertEquals("name was not FileDescriptor", "FileDescriptor", FileDescriptor.newInstance().getClassName());
    }

    @Test
    public void testEqualsDetectsIdentity()
    {
        FileDescriptor a = getFileDescriptor("a.txt", template);
        assertEquals("a not equal to a", a, a);
    }

    @Test
    public void testEqualsSelectsOnName()
    {
        FileDescriptor a = getFileDescriptor("a.txt", template);
        FileDescriptor b = getFileDescriptor("a.txt", template);
        FileDescriptor c = getFileDescriptor("c.txt", template);
        assertEquals("a not equal to b", a, b);
        assertFalse("a equal to c", a.equals(c));
    }

    @Test
    public void testEqualsDetectsDifferentClasses()
    {
        assertFalse("FileDescriptor equal to String", FileDescriptor.newInstance().equals(""));
    }

    @Test
    public void testEqualsSelectsOnTemplate()
    {
        FileDescriptor a = getFileDescriptor("a.txt", template);
        FileDescriptor b = getFileDescriptor("a.txt", "a.template");
        FileDescriptor c = getFileDescriptor("a.txt", "a.template");
        assertFalse("a equal to b", a.equals(b));
        assertEquals("b not equal to c", b, c);
    }

    @Test
    public void testGetters()
    {
        FileDescriptor fileDescriptor = getFileDescriptor("SomeName.txt", template);
        // getName is only used in the test template, so it's not exercised by other tests
        assertEquals("getter did not return name", "SomeName.txt", fileDescriptor.getName());
        assertThat("did not return properties object", fileDescriptor.getProperties(), instanceOf(java.util.Properties.class));
        // I want to get a copy of the properties object, not a reference, so I can muck about with it
        Properties p1 = fileDescriptor.getProperties();
        Properties p2 = fileDescriptor.getProperties();
        assertEquals("did not return unique properties object", false, p1 == p2);
    }
}
