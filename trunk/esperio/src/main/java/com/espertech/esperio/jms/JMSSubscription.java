package com.espertech.esperio.jms;

import com.espertech.esperio.subscription.*;
import com.espertech.esper.event.*;

/**
 * Represents the JMS-aspects of a subscription.
 */
public class JMSSubscription extends BaseSubscription
{
    private JMSMessageMarshaller jmsMessageMarshaller;

    /**
     * Empty Ctor required for use with Spring.
     */
    public JMSSubscription()
    {
    }

    /**
     * Returns the marshaller to use for this subscription.
     * @return marshaller
     */
    public JMSMessageMarshaller getJmsMessageMarshaller()
    {
        return jmsMessageMarshaller;
    }

    /**
     * Sets the marshaller to use for this subscription.
     * @param jmsMessageMarshaller to use
     */
    public void setJmsMessageMarshaller(JMSMessageMarshaller jmsMessageMarshaller)
    {
        this.jmsMessageMarshaller = jmsMessageMarshaller;
    }

    public void matchFound(EventBean event)
    {
        if (!(adapter instanceof JMSOutputAdapter))
        {
            return;
        }
        ((JMSOutputAdapter) (adapter)).send(event, jmsMessageMarshaller);
    }
}
