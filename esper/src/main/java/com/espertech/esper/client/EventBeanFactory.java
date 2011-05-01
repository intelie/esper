package com.espertech.esper.client;

/**
 * Factory for {@link EventBean} instances given an underlying event object.
 * <p>
 * Not transferable between engine instances.
 */
public interface EventBeanFactory {

    /**
     * Wraps the underlying event object.
     * @param underlying event to wrap
     * @return event bean
     */
    public EventBean wrap(Object underlying);
}
