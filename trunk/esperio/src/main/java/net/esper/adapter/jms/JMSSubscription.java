package net.esper.adapter.jms;

import net.esper.adapter.subscription.*;
import net.esper.adapter.*;
import net.esper.event.*;

/**
 * Created for ESPER.
 */
public class JMSSubscription extends BaseSubscription
{
  private JMSMessageMarshaler jmsMessageMarshaler;

  public JMSSubscription()
  {
  }

  public JMSSubscription(String alias, OutputAdapter adapter,
    String eventTypeAlias)
  {
    super(alias, adapter, eventTypeAlias);
  }

  public JMSSubscription(OutputAdapter adapter, String eventTypeAlias)
  {
    super(adapter, eventTypeAlias);
  }

  public JMSMessageMarshaler getJmsMessageMarshaler()
  {
    return jmsMessageMarshaler;
  }

  public void setJmsMessageMarshaler(JMSMessageMarshaler jmsMessageMarshaler)
  {
    this.jmsMessageMarshaler = jmsMessageMarshaler;
  }

  public void matchFound(EventBean event)
  {
    if (!(adapter instanceof JMSOutputAdapter))
    {
      return;
    }
    ((JMSOutputAdapter)(adapter)).send(event, jmsMessageMarshaler);
  }

}
