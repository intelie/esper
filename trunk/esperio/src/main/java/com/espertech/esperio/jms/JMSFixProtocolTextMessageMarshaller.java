package com.espertech.esperio.jms;

import com.espertech.esper.client.EPException;
import com.espertech.esper.event.EventBean;
import com.espertech.esperio.message.fix.FixMsgMarshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Marshals the response out of the event bean into a jms map message.
 */
public class JMSFixProtocolTextMessageMarshaller implements JMSMessageMarshaller
{
    private final Log log = LogFactory.getLog(this.getClass());

    public Message marshal(EventBean eventBean, Session session,
                           long timestamp) throws EPException
    {
        TextMessage textMessage;
        try
        {
            textMessage = session.createTextMessage();
            String fixText = FixMsgMarshaller.marshalFix(eventBean);
            textMessage.setText(fixText);
            textMessage.setJMSTimestamp(timestamp);
        }
        catch (JMSException ex)
        {
            throw new EPException(ex);
        }
        return textMessage;
    }
}
