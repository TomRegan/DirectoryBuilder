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

package io.github.tomregan.directorybuilderdemo;

import io.github.tomregan.directorybuilder.DirectoryBuilder;
import io.github.tomregan.directorybuilder.XmlDirectoryDescriptorReader;
import io.github.tomregan.directorybuilder.descriptors.Descriptor;
import io.github.tomregan.directorybuilderdemo.descriptors.JavaSourcesDescriptorFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DirectoryBuilderDemo
{

    private String descriptor;

    public static void main(String[] args)
    {
        DirectoryBuilderDemo app = DirectoryBuilderDemo.newInstance();
        app.run(new File(System.getProperty("user.dir")));
    }

    int run(File workingDirectory)
    {
        try
        {
            XmlDirectoryDescriptorReader reader = XmlDirectoryDescriptorReader.newInstance(JavaSourcesDescriptorFactory.newInstance());
            descriptor = "/structure.xml";
            Descriptor[] descriptors = reader.getDescriptors(getClass().getResourceAsStream(descriptor));
            DirectoryBuilder builder = DirectoryBuilder.newInstance(workingDirectory);
            builder.createDirectoryStructure(descriptors);
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            if (e instanceof java.net.MalformedURLException)
            {
                System.err.println("Could not find XML descriptor " + descriptor + " on classpath.");
            }
            e.printStackTrace();
        }
        return 0;
    }

    static DirectoryBuilderDemo newInstance()
    {
        return new DirectoryBuilderDemo();
    }
}
