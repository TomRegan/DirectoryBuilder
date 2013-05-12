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

public class DescriptorFactory
{
    private DescriptorFactory()
    {
    }

    public static DescriptorFactory newInstance()
    {
        return new DescriptorFactory();
    }

    public Descriptor getDescriptorForElement(String element)
    {
        Descriptor result = null;
        if (element.equals("directory"))
        {
            result = DirectoryDescriptor.newInstance();
        }
        else if (element.equals("file"))
        {
            result = FileDescriptor.newInstance();
        }
        return result;
    }
}
