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

package io.github.tomregan.directorybuilderdemo.descriptors;

import io.github.tomregan.directorybuilder.descriptors.Descriptor;
import io.github.tomregan.directorybuilder.descriptors.DescriptorFactory;
import io.github.tomregan.directorybuilderdemo.messaging.MessageService;

public class JavaSourcesDescriptorFactory extends DescriptorFactory
{
    private final MessageService messageService;
    //TODO add registry to DescriptorFactory to avoid subclassing

    private JavaSourcesDescriptorFactory(MessageService messageService)
    {
        this.messageService = messageService;
    }

    public static JavaSourcesDescriptorFactory newInstance(MessageService messageService)
    {
        return new JavaSourcesDescriptorFactory(messageService);
    }

    @Override
    public Descriptor getDescriptorForElement(String element)
    {
        Descriptor result = super.getDescriptorForElement(element);
        if (result == null)
        {
            if (element.equals("sourcedirectory"))
            {
                result = SourceDirectoryDescriptor.newInstance(messageService);
            }
            else if (element.equals("javafile"))
            {
                result = JavaFileDescriptor.newInstance(messageService);
            }
        }
        return result;
    }
}
