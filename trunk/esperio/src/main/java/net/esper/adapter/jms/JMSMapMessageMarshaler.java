package net.esper.adapter.jms;

import net.esper.event.*;

import javax.jms.*;

/**
 * Created for ESPER.
 */
public interface JMSMapMessageMarshaler
{
  public MapMessage marshal(EventBean eventBean, Session session, long timestamp);
}
