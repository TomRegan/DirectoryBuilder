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
import java.util.ArrayList;
import java.util.List;


public class XmlDirectoryDescriptorReaderImpl implements XmlDirectoryDescriptorReader
{
    private final DescriptorFactory descriptorFactory;
    private List<Descriptor> descriptorStack = new ArrayList<Descriptor>();
    private final XMLReader xmlReader;
    private int depth;

    private XmlDirectoryDescriptorReaderImpl(DescriptorFactory descriptorFactory) throws ParserConfigurationException, SAXException
    {
        this.descriptorFactory = descriptorFactory;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(this);
    }

    public static XmlDirectoryDescriptorReader newInstance(DescriptorFactory descriptorFactory) throws ParserConfigurationException, SAXException
    {
        return new XmlDirectoryDescriptorReaderImpl(descriptorFactory);
    }

    @Override
    public Descriptor[] getDescriptors(File directoryStructureXML) throws IOException, SAXException
    {
        xmlReader.parse(directoryStructureXML.getPath());
        return descriptorStack.toArray(new Descriptor[descriptorStack.size()]);
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
        Descriptor descriptor = descriptorFactory.getDescriptorForElement(qName);
        if (descriptor != null)
        {
            depth++;
            descriptor.setProperty("name", attributes.getValue("name"));
            String template = attributes.getValue("template");
            if (template != null)
            {
                descriptor.setProperty("template", template);
            }
            addChild(descriptor);
            descriptorStack.add(descriptor);
        }
    }

    private void addChild(Descriptor descriptor)
    {
        if (descriptorStack.size() > 0)
        {
            descriptorStack.get(descriptorStack.size() - 1).addChild(descriptor);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (depth > 1)
        {
            descriptorStack.remove(descriptorStack.size() - 1);
        }
        depth--;
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
