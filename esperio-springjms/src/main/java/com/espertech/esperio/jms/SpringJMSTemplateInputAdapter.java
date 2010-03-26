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
import com.espertech.esper.adapter.AdapterState;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

import javax.jms.*;

/**
 * Input adapter for receiving engine from the JMS world using Spring JMS templates and sending these to an engine.
 */
public class SpringJMSTemplateInputAdapter extends JMSInputAdapter
        implements MessageListener
{
    private JmsTemplate jmsTemplate;

    private final Log log = LogFactory.getLog(getClass());

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
     * @param jmsTemplate is the jms template
     */
    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    public void onMessage(Message message)
    {
        try
        {
            message.acknowledge();

            if (stateManager.getState() == AdapterState.DESTROYED)
            {
                return;
            }

            if (epServiceProviderSPI == null)
            {
                log.warn(".onMessage Event message not sent to engine, service provider not set yet, message ack'd");
                return;
            }

            synchronized (message)
            {
                Object event = jmsMessageUnmarshaller.unmarshal(epServiceProviderSPI.getEventAdapterService(), message);

                if (event != null)
                {
                    epServiceProviderSPI.getEPRuntime().sendEvent(event);
                }
                else
                {
                    if (log.isWarnEnabled())
                    {
                        log.warn(".onMessage Event object not sent to engine: " + message.getJMSMessageID());
                    }
                }
            }
        }
        catch (JMSException ex)
        {
            throw new EPException(ex);
        }
        catch (EPException ex)
        {
            log.error(".onMessage exception", ex);
            if (stateManager.getState() == AdapterState.STARTED)
            {
                stop();
            }
            else
            {
                destroy();
            }
        }
    }
}
