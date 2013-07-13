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

import io.github.tomregan.directorybuilder.descriptors.DirectoryDescriptor;
import io.github.tomregan.directorybuilder.internal.FileFactory;
import io.github.tomregan.directorybuilderdemo.messaging.MessageService;

import java.io.File;
import java.io.IOException;

public class SourceDirectoryDescriptor extends DirectoryDescriptor
{
    private MessageService messageService;

    protected SourceDirectoryDescriptor(FileFactory fileFactory, MessageService messageService)
    {
        super(fileFactory);
        this.messageService = messageService;
    }

    public static SourceDirectoryDescriptor newInstance(MessageService messageService)
    {
        return new SourceDirectoryDescriptor(FileFactory.newInstance(), messageService);
    }

    @Override
    public void create(File parentDirectory) throws IOException
    {
        super.create(parentDirectory);
        String name = getValueForAttribute("name");
        messageService.updateSubject("source_directory", new File(parentDirectory, name).getPath());
    }
}
