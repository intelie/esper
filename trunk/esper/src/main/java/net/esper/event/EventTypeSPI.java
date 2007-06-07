package net.esper.event;

/**
 * Event type SPI for internal use.
 */
public interface EventTypeSPI extends EventType
{
    /**
     * Returns the type's event type id.
     * @return type id
     */
    public String getEventTypeId();
}
