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

import io.github.tomregan.directorybuilder.impl.DirectoryDescriptor;
import io.github.tomregan.directorybuilder.impl.FileDescriptor;
import io.github.tomregan.directorybuilder.impl.XmlDirectoryDescriptorReaderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class XmlDirectoryDescriptorReaderTest
{

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private XmlDirectoryDescriptorReader xmlDirectoryDescriptorReader;

    private File getInput(String filename)
    {
        return new File("src/test/resources/", filename);
    }

    @Test
    public void shouldCreateDirectoryDescriptorGivenValidDirectoryXML() throws IOException, SAXException, ParserConfigurationException
    {
        DirectoryDescriptor directoryDescriptor = DirectoryDescriptor.createDirectoryDescriptor("test");
        Descriptor[] expected = {directoryDescriptor};
        xmlDirectoryDescriptorReader = XmlDirectoryDescriptorReaderImpl.newReaderInstance();
        assertArrayEquals("did not create DirectoryDescriptor", expected, xmlDirectoryDescriptorReader.getDescriptors(getInput("testDirectoryDescriptor.xml")));
    }

    @Test
    public void shouldCreateFileDescriptorGivenValidFileXML() throws IOException, SAXException, ParserConfigurationException
    {
        FileDescriptor fileDescriptor = FileDescriptor.createFileDescriptor(new File("foo.template"), "foo.txt");
        Descriptor[] expected = {fileDescriptor};
        xmlDirectoryDescriptorReader = XmlDirectoryDescriptorReaderImpl.newReaderInstance();
        assertArrayEquals("did not create FileDescriptor", expected, xmlDirectoryDescriptorReader.getDescriptors(getInput("testFileDescriptor.xml")));
    }

    @Test
    public void shouldThrowExceptionGivenInvalidXML() throws IOException, SAXException, ParserConfigurationException
    {
        xmlDirectoryDescriptorReader = XmlDirectoryDescriptorReaderImpl.newReaderInstance();
        exception.expect(SAXParseException.class);
        xmlDirectoryDescriptorReader.getDescriptors(getInput("invalid.txt"));
    }
}
