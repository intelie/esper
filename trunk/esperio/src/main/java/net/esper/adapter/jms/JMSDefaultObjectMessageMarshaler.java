package net.esper.adapter.jms;

import org.apache.commons.logging.*;

import javax.jms.*;

import net.esper.event.*;
import net.esper.client.*;

import java.io.*;

/**
 * Created for ESPER.
 */
public class JMSDefaultObjectMessageMarshaler implements JMSMessageMarshaler
{
  private final Log log = LogFactory.getLog(this.getClass());

  /**
   * Marshals the response out of the event bean into a jms map message.
   */

  public JMSDefaultObjectMessageMarshaler()
  {

  }

  public Message marshal(EventBean eventBean, Session session,
    long timestamp) throws EPException
  {
    EventType eventType = eventBean.getEventType();
    Message message = null;
    try
    {
      if (eventBean instanceof Serializable)
      {
        message = session.createObjectMessage((Serializable)eventBean);
        message.setJMSTimestamp(timestamp);
      }
   }
   catch (JMSException ex)
   {
     throw new EPException(ex);
   }
   return message;
 }


}
