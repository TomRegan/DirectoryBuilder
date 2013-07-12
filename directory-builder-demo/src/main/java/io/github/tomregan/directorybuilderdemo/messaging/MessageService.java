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

package io.github.tomregan.directorybuilderdemo.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageService
{
    private HashMap<String, List<Subscriber>> subscribers;

    protected MessageService()
    {
        subscribers = new HashMap<String, List<Subscriber>>();
    }

    public static MessageService newInstance()
    {
        return new MessageService();
    }

    public void addSubscriber(Subscriber subscriber, String subject)
    {
        if (subscribers.get(subject) == null)
        {
            subscribers.put(subject, new ArrayList<Subscriber>());
        }
        subscribers.get(subject).add(subscriber);
    }

    public void updateSubject(String subject, String message)
    {
        if (subscribers.get(subject) == null)
        {
            return;
        }
        for (Subscriber subscriber : subscribers.get(subject))
        {
            subscriber.update(subject, message);
        }
    }
}
