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

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

abstract public class Descriptor
{
    protected Properties properties;

    protected Descriptor()
    {
        properties = new Properties();
    }

    protected Descriptor(String... attributes)
    {
        this();
        for (String attribute : attributes)
        {
            properties.setProperty(attribute, "");
        }
    }

    abstract public void create(File parentDirectory) throws IOException;

    abstract public void addChild(Descriptor descriptor);

    public void setValueForAttribute(String name, String value)
    {
        properties.setProperty(name, value);
    }

    public String getValueForAttribute(String name)
    {
        return properties.getProperty(name);
    }

    public Set<String> getAttributeNames()
    {
        return new HashSet<String>(properties.stringPropertyNames());
    }

    protected Properties getProperties()
    {
        return new Properties(properties);
    }
}
