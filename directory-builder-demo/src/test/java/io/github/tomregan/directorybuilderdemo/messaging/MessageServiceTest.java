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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class MessageServiceTest
{
    private MessageService messageService;

    @Before
    public void setUp() throws Exception
    {
        messageService = MessageService.newInstance();
    }

    @Test
    public void shouldReturnNewInstance() throws Exception
    {
        assertThat("didn't return MessageService", MessageService.newInstance(), instanceOf(MessageService.class));
    }

    @Test
    public void shouldNotifySubscriber()
    {
        Subscriber subscriber = mock(Subscriber.class);
        messageService.addSubscriber(subscriber, "testMessages");
        messageService.updateSubject("testMessages", "ohai xxx");
        verify(subscriber).update("testMessages", "ohai xxx");
    }

    @Test
    public void shouldHandleMultipleSubjects()
    {
        Subscriber subscriber1 = mock(Subscriber.class);
        Subscriber subscriber2 = mock(Subscriber.class);

        messageService.addSubscriber(subscriber1, "subject1");
        messageService.addSubscriber(subscriber2, "subject2");

        messageService.updateSubject("subject1", "spam");
        messageService.updateSubject("subject2", "eggs");

        verify(subscriber1).update("subject1", "spam");
        verify(subscriber2).update("subject2", "eggs");

        verifyNoMoreInteractions(subscriber1);
        verifyNoMoreInteractions(subscriber2);
    }
}
