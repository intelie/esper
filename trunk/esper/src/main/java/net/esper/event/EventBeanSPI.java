package net.esper.event;

/**
 * SPI for events for internal use.
 */
public interface EventBeanSPI extends EventBean
{
    /**
     * Returns the event id object.
     * @return event id
     */
    public Object getEventBeanId();
}
