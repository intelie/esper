package net.esper.adapter.jms;

import net.esper.client.*;
import net.esper.event.*;
import net.esper.adapter.AdapterSPI;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

import javax.jms.*;

public class SpringJMSTemplateOutputAdapter extends JMSOutputAdapter
{
    private JmsTemplate jmsTemplate;
    private SpringMessageCreator messageCreator;

    private final Log log = LogFactory.getLog(this.getClass());

    // getters and setters called by Spring
    public JmsTemplate getJmsTemplate()
    {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final EventBean eventBean,
                     JMSMessageMarshaler jmsMessageMarshaler) throws EPException
    {
        if (jmsTemplate != null)
        {
            if (messageCreator == null)
            {
                messageCreator = new SpringMessageCreator();
            }
            messageCreator.setMessageParameters(
                    eventBean, ((jmsMessageMarshaler != null) ?
                    jmsMessageMarshaler :
                    this.jmsMessageMarshaler));
            if (destination != null)
            {
                jmsTemplate.send(destination, messageCreator);
            }
            else
            {
                jmsTemplate.send(messageCreator);
            }
        }
    }

    private class SpringMessageCreator implements MessageCreator
    {
        EventBean eventBean;
        JMSMessageMarshaler jmsMessageMarshaler;

        public void setMessageParameters(EventBean eventBean,
                                         JMSMessageMarshaler jmsMessageMarshaler)
        {
            this.eventBean = eventBean;
            this.jmsMessageMarshaler = jmsMessageMarshaler;
        }

        public Message createMessage(Session session)
        {
            if ((eventBean == null) || (jmsMessageMarshaler == null))
            {
                return null;
            }
            Message msg =
                    jmsMessageMarshaler.marshal(eventBean, session, getCurrentTime());
            log.debug("Creating jms message from event." + msg.toString());
            return msg;
        }
    }
}
