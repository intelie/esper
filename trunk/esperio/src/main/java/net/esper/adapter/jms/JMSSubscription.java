package net.esper.adapter.jms;

import net.esper.adapter.subscription.*;
import net.esper.adapter.*;
import net.esper.event.*;

public class JMSSubscription extends BaseSubscription
{
    private JMSMessageMarshaler jmsMessageMarshaler;

    public JMSSubscription()
    {
    }

    public JMSMessageMarshaler getJmsMessageMarshaler()
    {
        return jmsMessageMarshaler;
    }

    public void setJmsMessageMarshaler(JMSMessageMarshaler jmsMessageMarshaler)
    {
        this.jmsMessageMarshaler = jmsMessageMarshaler;
    }

    public void matchFound(EventBean event)
    {
        if (!(adapter instanceof JMSOutputAdapter))
        {
            return;
        }
        ((JMSOutputAdapter) (adapter)).send(event, jmsMessageMarshaler);
    }
}
