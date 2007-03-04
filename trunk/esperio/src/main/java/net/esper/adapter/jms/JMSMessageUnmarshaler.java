package net.esper.adapter.jms;

import net.esper.client.*;
import net.esper.event.*;

import javax.jms.*;

/**
 * Created for ESPER.
 */
public interface JMSMessageUnmarshaler
{
  Object unmarshal(EventAdapterService eventAdapterService, Message message)
    throws EPException;
}
