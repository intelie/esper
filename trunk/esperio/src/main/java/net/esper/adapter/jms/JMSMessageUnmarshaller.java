package net.esper.adapter.jms;

import net.esper.client.*;
import net.esper.event.*;

import javax.jms.*;

/**
 * Interface for a un-marshaller that takes a JMS message and creates or wraps an event object for use to
 * send as an event into an engine instance. 
 */
public interface JMSMessageUnmarshaller
{
    /**
     * Unmarshal the given JMS message into an object for sending into the engine.
     * @param eventAdapterService is the wrapper service for events
     * @param message is the message to unmarshal
     * @return event to send to engine
     * @throws EPException if the unmarshal operation failed
     */
    public Object unmarshal(EventAdapterService eventAdapterService, Message message) throws EPException;
}
