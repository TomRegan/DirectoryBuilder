package io.github.tomregan.directorybuilderdemo.messaging;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


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
        String message = "ohai xxx";
        Subscriber subscriber = mock(Subscriber.class);
        messageService.addSubscriber(subscriber, "testMessages");
        messageService.updateSubject("testMessages", message);
        verify(subscriber).update(message);
    }

    @Test
    public void shouldHandleMultipleSubjects()
    {
        // create subscribers on two separate subjects

        // set up message service by adding subscribers

        // call updateSubject on MS

        // verify subscribers are called with message updates for which they subscribed

        // AND they are NOT updated on subjects to which they did not subscribe
    }
}
