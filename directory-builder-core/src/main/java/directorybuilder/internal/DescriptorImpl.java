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

package directorybuilder.internal;

import directorybuilder.descriptors.Descriptor;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

abstract public class DescriptorImpl implements Descriptor
{
    private Properties properties;

    protected DescriptorImpl()
    {
        properties = new Properties();
    }

    protected DescriptorImpl(String... attributes)
    {
        this();
        for (String attribute : attributes)
        {
            properties.setProperty(attribute, "");
        }
    }

    @Override
    public void setValueForAttribute(String name, String value)
    {
        properties.setProperty(name, value);
    }

    @Override
    public String getValueForAttribute(String name)
    {
        return properties.getProperty(name);
    }

    @Override
    public Set<String> getAttributeNames()
    {
        return new HashSet<String>(properties.stringPropertyNames());
    }

}
