package io.github.tomregan.directorybuilderdemo.messaging;

public class MessageService
{
    private String subject;
    private Subscriber subscriber;

    protected MessageService() {}

    public static MessageService newInstance()
    {
        return new MessageService();
    }

    public void addSubscriber(Subscriber subscriber, String subject)
    {
        this.subscriber = subscriber;
        this.subject = subject;
    }

    public void updateSubject(String subject, String message)
    {
        subscriber.update(message);
    }
}
