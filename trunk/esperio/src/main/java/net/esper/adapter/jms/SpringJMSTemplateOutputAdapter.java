package net.esper.adapter.jms;

import net.esper.client.EPException;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Message;
import javax.jms.Session;

/**
 * Created for ESPER.
 */

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
      jmsTemplate.send(messageCreator);
      lastEvent = eventBean;
      eventCount++;
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
