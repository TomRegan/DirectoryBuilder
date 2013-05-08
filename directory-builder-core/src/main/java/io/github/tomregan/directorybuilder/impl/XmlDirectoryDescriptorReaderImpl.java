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

package io.github.tomregan.directorybuilder.impl;

import io.github.tomregan.directorybuilder.Descriptor;
import io.github.tomregan.directorybuilder.XmlDirectoryDescriptorReader;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;


public class XmlDirectoryDescriptorReaderImpl implements XmlDirectoryDescriptorReader
{
    private Descriptor[] descriptors;
    private final XMLReader xmlReader;

    private XmlDirectoryDescriptorReaderImpl() throws ParserConfigurationException, SAXException
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(this);
    }

    public static XmlDirectoryDescriptorReader newReaderInstance() throws ParserConfigurationException, SAXException
    {
        return new XmlDirectoryDescriptorReaderImpl();
    }

    @Override
    public Descriptor[] getDescriptors(File directoryStructureXML) throws IOException, SAXException
    {
        xmlReader.parse(directoryStructureXML.getPath());
        return descriptors;
    }

    @Override
    public void setDocumentLocator(Locator locator)
    {
    }

    @Override
    public void startDocument() throws SAXException
    {
    }

    @Override
    public void endDocument() throws SAXException
    {
    }

    @Override
    public void startPrefixMapping(String start, String uri) throws SAXException
    {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException
    {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equalsIgnoreCase("file"))
        {
            String template = attributes.getValue("template");
            String name = attributes.getValue("name");
            descriptors = new FileDescriptor[]{FileDescriptor.createFileDescriptor(new File(template), name)};
        }
        else
        {
            descriptors = new Descriptor[]{DirectoryDescriptor.createDirectoryDescriptor("test")};
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException
    {
    }

    @Override
    public void ignorableWhitespace(char[] chars, int start, int length) throws SAXException
    {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException
    {
    }

    @Override
    public void skippedEntity(String name) throws SAXException
    {
    }
}
