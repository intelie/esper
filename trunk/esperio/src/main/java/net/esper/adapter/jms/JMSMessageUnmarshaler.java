package net.esper.adapter.jms;

import net.esper.client.*;
import net.esper.event.*;

import javax.jms.*;

/**
 * Interface for a un-marshaller that takes a JMS message and creates or wraps an event object for use to
 * send as an event into an engine instance. 
 */
public interface JMSMessageUnmarshaler
{
    public Object unmarshal(EventAdapterService eventAdapterService, Message message) throws EPException;
}
