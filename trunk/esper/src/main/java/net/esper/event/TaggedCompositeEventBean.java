package net.esper.event;

/**
 * Interface for composite events in which a property is itself an event.
 * <p>
 * For use with patterns in which pattern tags are properties in a result event and property values
 * are the event itself that is matching in a pattern.
 */
public interface TaggedCompositeEventBean
{
    /**
     * Returns the event for the tag.
     * @param property is the tag name
     * @return event
     */
    public EventBean getEventBean(String property);
}
