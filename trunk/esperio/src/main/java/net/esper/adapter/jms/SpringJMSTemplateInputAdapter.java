package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.client.*;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

import javax.jms.*;

public class SpringJMSTemplateInputAdapter extends JMSInputAdapter
        implements MessageListener
{
    private JmsTemplate jmsTemplate;

    private final Log log = LogFactory.getLog(getClass());

    // getters and setters called by Spring
    public JmsTemplate getJmsTemplate()
    {
        return jmsTemplate;
    }

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
                Object event = jmsMessageUnmarshaler.unmarshal(epServiceProviderSPI.getEventAdapterService(), message);

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
