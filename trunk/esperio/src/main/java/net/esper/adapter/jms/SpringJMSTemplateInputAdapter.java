package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.client.*;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

import javax.jms.*;

/**
 * Created for ESPER.
 */
public class SpringJMSTemplateInputAdapter extends JMSInputAdapter
  implements MessageListener
{
  private JmsTemplate jmsTemplate;
  private String eventTypeAlias;
  private Message jmsMessage;

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

  public String getEventTypeAlias()
  {
    return eventTypeAlias;
  }

  public void setEventTypeAlias(String eventTypeAlias)
  {
    this.eventTypeAlias = eventTypeAlias;
  }

  public Object read()
  {
    return jmsMessageUnmarshaler.unmarshal(eventAdapterSvc, jmsMessage);
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
      jmsMessage = message;
      synchronized (jmsMessage)
      {
        Object event = read();

        if (event != null)
        {
          epRuntime.sendEvent(event);
        }
      }
    }
    catch (JMSException ex)
    {
      throw new EPException(ex);
    }
    catch (EPException ex)
    {
      log.debug(".onMessage exception");
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
