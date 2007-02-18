package net.esper.adapter;

import net.esper.event.*;

/**
 * Created for ESPER.
 */
public interface EventBeanListener
{
  public EventBean getLastEvent();
  public int getEventCount();
  public int getAndResetEventCount();
}
