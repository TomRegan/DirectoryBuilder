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

import directorybuilder.DirectoryBuilder;
import directorybuilder.ConfigurationProcessor;
import directorybuilder.descriptors.Descriptor;
import io.github.tomregan.directorybuilderdemo.descriptors.JavaSourcesDescriptorFactory;
import io.github.tomregan.directorybuilderdemo.messaging.MessageService;
import io.github.tomregan.directorybuilderdemo.messaging.Subscriber;
import io.github.tomregan.directorybuilderdemo.ui.UserInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DirectoryBuilderDemo implements Subscriber
{

    public static void main(String[] args)
    {
        DirectoryBuilderDemo app = DirectoryBuilderDemo.newInstance();
        System.exit(app.run(new File(System.getProperty("user.dir")), new UserInterface()));
    }

    int run(File workingDirectory, UserInterface userInterface)
    {
        String descriptor = "/structure.xml";
        try
        {
            // create a message service to handle two-way communication between the demo application
            // and the descriptors
            MessageService messageService = MessageService.newInstance();
            messageService.addSubscriber(this, "source_directory");

            // create an overridden instance of the descriptor factory that provides descriptors to handle
            // custom elements in the demo application's config
            JavaSourcesDescriptorFactory descriptorFactory = JavaSourcesDescriptorFactory.newInstance(messageService);

            // create a configuration processor to read the config, passing in configuration from
            // the classpath (contained in the jar at runtime)
            ConfigurationProcessor configurationProcessor = ConfigurationProcessor.newInstance(descriptorFactory);
            Descriptor[] descriptors = configurationProcessor.getDescriptors(
                    getClass().getResourceAsStream(descriptor));

            // ask the user for input, and update one of the descriptors via the message service
            messageService.updateSubject("user", userInterface.getUserName());

            // create a directory builder and write the directory structure and files to disk
            DirectoryBuilder builder = DirectoryBuilder.newInstance(workingDirectory);
            builder.createDirectoryStructure(descriptors);
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
            return 1;
        }
        catch (SAXException e)
        {
            e.printStackTrace();
            return 1;
        }
        catch (IOException e)
        {
            // FIXME not tested
            if (e instanceof java.net.MalformedURLException)
            {
                System.err.println("Could not find configuration " + descriptor + " on classpath.");
            }
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    static DirectoryBuilderDemo newInstance()
    {
        return new DirectoryBuilderDemo();
    }

    @Override
    public void update(String subject, String message)
    {
        if (subject.equals("source_directory"))
        {
            System.out.println("A source directory was created in " + message);
        }
    }
}
