/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.jms;

import com.espertech.esper.client.*;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

import javax.jms.*;

/**
 * Output adapter for sending engine events out into the JMS world using Spring JMS templates.
 */
public class SpringJMSTemplateOutputAdapter extends JMSOutputAdapter
{
    private JmsTemplate jmsTemplate;
    private SpringMessageCreator messageCreator;

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Returns the jms template.
     * @return Spring JMS template
     */
    public JmsTemplate getJmsTemplate()
    {
        return jmsTemplate;
    }

    /**
     * Sets the Spring JMS template
     * @param jmsTemplate to set
     */
    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final EventBean eventBean,
                     JMSMessageMarshaller jmsMessageMarshaller) throws EPException
    {
        if (jmsTemplate != null)
        {
            if (messageCreator == null)
            {
                messageCreator = new SpringMessageCreator();
            }
            messageCreator.setMessageParameters(
                    eventBean, ((jmsMessageMarshaller != null) ?
                    jmsMessageMarshaller :
                    this.jmsMessageMarshaller));
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
        JMSMessageMarshaller jmsMessageMarshaller;

        public void setMessageParameters(EventBean eventBean,
                                         JMSMessageMarshaller jmsMessageMarshaller)
        {
            this.eventBean = eventBean;
            this.jmsMessageMarshaller = jmsMessageMarshaller;
        }

        public Message createMessage(Session session)
        {
            if ((eventBean == null) || (jmsMessageMarshaller == null))
            {
                return null;
            }
            Message msg =
                    jmsMessageMarshaller.marshal(eventBean, session, System.currentTimeMillis());
            if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
            {
                log.debug("Creating jms message from event." + msg.toString());
            }
            return msg;
        }
    }
}
