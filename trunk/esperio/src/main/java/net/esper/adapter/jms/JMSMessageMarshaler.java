package net.esper.adapter.jms;

import net.esper.event.*;

import javax.jms.*;

/**
 * Interface for a marshaller that creates a JMS message given a JMS session and event. 
 */
public interface JMSMessageMarshaler
{
  /**
   * Marshals the response out of the event bean.
   */
  public Message marshal(EventBean eventBean, Session session, long timestamp);

}
