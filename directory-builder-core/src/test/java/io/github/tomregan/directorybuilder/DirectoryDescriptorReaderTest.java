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

import io.github.tomregan.directorybuilder.descriptors.Descriptor;
import io.github.tomregan.directorybuilder.descriptors.DescriptorFactory;
import io.github.tomregan.directorybuilder.descriptors.DirectoryDescriptor;
import io.github.tomregan.directorybuilder.descriptors.FileDescriptor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DirectoryDescriptorReaderTest
{

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private ConfigurationProcessor directoryDescriptorReader;

    private File getInput(String filename)
    {
        return new File("src/test/resources/", filename);
    }

    @Before
    public void setUp() throws ParserConfigurationException, SAXException
    {
        DescriptorFactory descriptorFactory = DescriptorFactory.newInstance();
        directoryDescriptorReader = ConfigurationProcessor.newInstance(descriptorFactory);
    }

    @Test
    public void shouldCreateDirectoryDescriptorGivenValidDirectoryXML() throws IOException, SAXException
    {
        DirectoryDescriptor foo = DirectoryDescriptor.newInstance();
        foo.setValueForAttribute("name", "foo");
        Descriptor[] expected = {foo};
        Descriptor[] actual = directoryDescriptorReader.getDescriptors(getInput("testDirectoryDescriptor.xml"));
        DirectoryDescriptor directoryDescriptor = (DirectoryDescriptor) actual[0];
        assertArrayEquals("did not create DirectoryDescriptor", expected, actual);
        assertEquals("did not return name property", "foo", directoryDescriptor.getValueForAttribute("name"));
    }

    @Test
    public void shouldCreateFileDescriptorGivenValidFileXML() throws IOException, SAXException
    {
        FileDescriptor foo = FileDescriptor.newInstance();
        foo.setValueForAttribute("name", "foo.txt");
        foo.setValueForAttribute("template", "foo.template");
        Descriptor[] expected = {foo};
        Descriptor[] actual = directoryDescriptorReader.getDescriptors(getInput("testFileDescriptor.xml"));
        FileDescriptor fileDescriptor = (FileDescriptor) actual[0];
        assertArrayEquals("did not create FileDescriptor", expected, actual);
        assertEquals("did not return 'name' property", "foo.txt", fileDescriptor.getValueForAttribute("name"));
    }

    @Test
    public void shouldThrowExceptionGivenInvalidXML() throws IOException, SAXException
    {
        exception.expect(SAXParseException.class);
        directoryDescriptorReader.getDescriptors(getInput("invalid.txt"));
    }

    @Test
    public void shouldBuildDescriptorTree() throws IOException, SAXException
    {
        Descriptor[] actual = directoryDescriptorReader.getDescriptors(getInput("testDirectoryStructure.xml"));
        assertEquals("did not create 3 root descriptors", 3, actual.length);
    }

    @Test
    public void shouldAcceptInputStream() throws IOException, SAXException
    {
        InputStream inputStream = new FileInputStream(getInput("testDirectoryStructure.xml"));
        directoryDescriptorReader.getDescriptors(inputStream);
    }
}
