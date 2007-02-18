package net.esper.adapter.jms;

import net.esper.event.*;

import javax.jms.*;

/**
 * Created for ESPER.
 */
public interface JMSMessageMarshaler
{
  /**
   * Marshals the response out of the event bean.
  */

  public Message marshal(EventBean eventBean, Session session, long timestamp);
  
}
