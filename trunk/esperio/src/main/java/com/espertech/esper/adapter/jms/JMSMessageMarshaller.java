package com.espertech.esper.adapter.jms;

import com.espertech.esper.event.*;

import javax.jms.*;

/**
 * Interface for a marshaller that creates a JMS message given a JMS session and event.
 */
public interface JMSMessageMarshaller
{
    /**
     * Marshals the response out of the event bean.
     *
     * @param eventBean is the event to marshal
     * @param session is the JMS session
     * @param timestamp is the timestamp to use
     * @return marshalled event as JMS message
     */
    public Message marshal(EventBean eventBean, Session session, long timestamp);

}
