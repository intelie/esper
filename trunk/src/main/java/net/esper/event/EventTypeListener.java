package net.esper.event;

/**
 * Created for ESPER.
 */
public interface EventTypeListener
{
  public void registeredEventType(String eventTypeAlias, EventType eventType);
  public EventBean getLastEvent();
  public int getEventCount();
  public int getAndResetEventCount();  
}
