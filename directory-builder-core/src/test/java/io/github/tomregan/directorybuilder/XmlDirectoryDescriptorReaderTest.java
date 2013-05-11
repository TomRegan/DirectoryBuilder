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

import io.github.tomregan.directorybuilder.internal.DescriptorFactory;
import io.github.tomregan.directorybuilder.internal.DirectoryDescriptor;
import io.github.tomregan.directorybuilder.internal.FileDescriptor;
import io.github.tomregan.directorybuilder.internal.XmlDirectoryDescriptorReaderImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class XmlDirectoryDescriptorReaderTest
{

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private XmlDirectoryDescriptorReader xmlDirectoryDescriptorReader;

    private File getInput(String filename)
    {
        return new File("src/test/resources/", filename);
    }

    @Before
    public void setUp() throws ParserConfigurationException, SAXException
    {
        DescriptorFactory descriptorFactory = DescriptorFactory.newInstance();
        xmlDirectoryDescriptorReader = XmlDirectoryDescriptorReaderImpl.newInstance(descriptorFactory);
    }

    @Test
    public void shouldCreateDirectoryDescriptorGivenValidDirectoryXML() throws IOException, SAXException
    {
        DirectoryDescriptor foo = DirectoryDescriptor.newInstance();
        foo.setProperty("name", "foo");
        Descriptor[] expected = {foo};
        Descriptor[] actual = xmlDirectoryDescriptorReader.getDescriptors(getInput("testDirectoryDescriptor.xml"));
        DirectoryDescriptor directoryDescriptor = (DirectoryDescriptor) actual[0];
        assertArrayEquals("did not create DirectoryDescriptor", expected, actual);
        assertEquals("did not return name property", "foo", directoryDescriptor.getProperties().getProperty("name"));
    }

    @Test
    public void shouldCreateFileDescriptorGivenValidFileXML() throws IOException, SAXException
    {
        FileDescriptor foo = FileDescriptor.newInstance();
        foo.setProperty("name", "foo.txt");
        foo.setProperty("template", "foo.template");
        Descriptor[] expected = {foo};
        Descriptor[] actual = xmlDirectoryDescriptorReader.getDescriptors(getInput("testFileDescriptor.xml"));
        FileDescriptor fileDescriptor = (FileDescriptor) actual[0];
        assertArrayEquals("did not create FileDescriptor", expected, actual);
        assertEquals("did not return 'name' property", "foo.txt", fileDescriptor.getProperties().getProperty("name"));
    }

    @Test
    public void shouldThrowExceptionGivenInvalidXML() throws IOException, SAXException
    {
        exception.expect(SAXParseException.class);
        xmlDirectoryDescriptorReader.getDescriptors(getInput("invalid.txt"));
    }

    @Test
    public void shouldBuildDescriptorTree() throws IOException, SAXException
    {
        Descriptor[] actual = xmlDirectoryDescriptorReader.getDescriptors(getInput("testDirectoryStructure.xml"));
        assertEquals("did not create 2 root directory descriptors", 2, actual.length);
    }
}
