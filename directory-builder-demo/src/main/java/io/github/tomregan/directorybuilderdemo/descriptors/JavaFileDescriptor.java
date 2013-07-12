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

import io.github.tomregan.directorybuilder.descriptors.Delegate;
import io.github.tomregan.directorybuilder.descriptors.FileDescriptor;
import io.github.tomregan.directorybuilder.internal.FileFactory;
import io.github.tomregan.directorybuilderdemo.messaging.MessageService;
import io.github.tomregan.directorybuilderdemo.messaging.Subscriber;

public class JavaFileDescriptor extends FileDescriptor implements Delegate, Subscriber
{
    private String user;

    protected JavaFileDescriptor(FileFactory fileFactory, MessageService messageService)
    {
        super(fileFactory);
        messageService.addSubscriber(this, "user");
        user = "you";
    }

    public static JavaFileDescriptor newInstance(MessageService messageService)
    {
        return new JavaFileDescriptor(FileFactory.newInstance(), messageService);
    }

    public String getClassName()
    {
        return "HelloWorld";
    }

    public String getUser()
    {
        return user;
    }

    @Override
    public void update(String subject, String message)
    {
        user = message;
    }
}
