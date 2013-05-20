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

import io.github.tomregan.directorybuilder.internal.DescriptorImpl;
import io.github.tomregan.directorybuilder.internal.FileFactory;

import java.io.File;
import java.io.IOException;

public class FileDescriptor extends DescriptorImpl implements TemplateDelegate
{
    private final FileFactory fileFactory;

    private FileDescriptor(FileFactory fileFactory)
    {
        super("name", "template");
        this.fileFactory = fileFactory;
    }

    public static FileDescriptor newInstance(FileFactory fileFactory)
    {
        return new FileDescriptor(fileFactory);
    }

    public static FileDescriptor newInstance()
    {
        return new FileDescriptor(FileFactory.newInstance());
    }

    @Override
    public void create(File parentDirectory) throws IOException
    {
        String name = getValueForAttribute("name");
        File template = fileFactory.createFile(getValueForAttribute("template"));
        if (!fileExists(template))
        {
            throw new IOException(getValueForAttribute("template") + " file not found");
        }
        File file = fileFactory.createFile(template, parentDirectory, name, this);
        if (!file.createNewFile())
        {
            throw new IOException("could not create " + file.getAbsolutePath());
        }
    }

    private boolean fileExists(File template)
    {
        return template != null && template.isFile();
    }

    @Override
    public void addChild(Descriptor descriptor)
    {
        // currently do nothing, but maybe throw an illegal transition exception here?
    }

    @Override
    public String getDescriptorId()
    {
        return getClass().getSimpleName();
    }

    @Override
    public String getName()
    {
        return getValueForAttribute("name");
    }

    @Override
    public boolean equals(Object o)
    {
        if (super.equals(o))
        {
            return true;
        }
        if (!(getClass() == o.getClass()))
        {
            return false;
        }
        FileDescriptor that = (FileDescriptor) o;
        String thisName = getValueForAttribute("name");
        String thatName = that.getValueForAttribute("name");
        if (thisName != null ? !thisName.equals(thatName) : thatName != null)
        {
            return false;
        }
        String thisTemplate = getValueForAttribute("template");
        String thatTemplate = that.getValueForAttribute("template");
        return thisTemplate != null && thisTemplate.equals(thatTemplate);
    }

}
