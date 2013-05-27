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

import com.google.common.io.Files;
import io.github.tomregan.directorybuilder.internal.FileFactory;
import io.github.tomregan.directorybuilder.internal.ResourceResolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class FileDescriptorTest
{

    private String template;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private File rootDirectory;

    private FileDescriptor getFileDescriptor(String name, String template)
    {
        FileDescriptor fileDescriptor = FileDescriptor.newInstance();
        fileDescriptor.setValueForAttribute("name", name);
        fileDescriptor.setValueForAttribute("template", template);
        return fileDescriptor;
    }

    @Before
    public void setUp()
    {
        template = "src/test/resources/test.template";
        rootDirectory = Files.createTempDir();

    }

    @Test
    public void testGetClassName() throws Exception
    {
        assertEquals("name was not FileDescriptor", "FileDescriptor", FileDescriptor.newInstance().getDescriptorId());
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
    }

    @Test
    public void shouldThrowExceptionWhenTemplateMissing() throws IOException
    {
        FileDescriptor fileDescriptor = getFileDescriptor("Crash.txt", "nx.template");
        exception.expect(IOException.class);
        fileDescriptor.create(rootDirectory);
    }

    @Test
    public void testDescriptorReturnsAttributeList()
    {
        FileDescriptor fileDescriptor = getFileDescriptor("Filename.txt", "Template.template");
        Set<String> expected = new HashSet<String>();
        expected.add("template");
        expected.add("name");
        assertEquals("did not return template and name", expected, fileDescriptor.getAttributeNames());
    }

    @Test
    public void shouldThrowMeaningfulExceptionWhenTemplateNotSet()
    {
        try
        {
            FileDescriptor descriptor = FileDescriptor.newInstance();
            descriptor.create(rootDirectory);
        }
        catch (IOException e)
        {
            assertEquals("message for no template was gibberish", "No template file was specified for FileDescriptor", e.getMessage());
        }
    }

    @Ignore // under development
    @Test
    public void shouldSetResourceResolver() throws IOException
    {
        String templateURI = "classpath:" + template;
        FileFactory factory = mock(FileFactory.class);
        when(factory.createFile(any(String.class))).thenReturn(new File(template));
        when(factory.createFile(any(File.class), any(File.class), any(String.class), any(FileDescriptor.class), any(ResourceResolver.class))).thenReturn(new File(rootDirectory, "foo.txt"));
        FileDescriptor descriptor = FileDescriptor.newInstance(factory);
        descriptor.setValueForAttribute("name", "foo.txt");
        descriptor.setValueForAttribute("template", templateURI);
        descriptor.create(rootDirectory);

        verify(factory).createFile(any(File.class), any(File.class), any(String.class), any(FileDescriptor.class), eq(ResourceResolver.CLASSPATH));
    }
}
