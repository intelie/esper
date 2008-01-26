package net.esper.core;

import net.esper.event.EventBean;

/**
 * Interface for a service that routes events within the engine for further processing.
 */
public interface InternalEventRouter
{
    /**
     * Route the event such that the event is processed as required.
     * @param event to route
     * @param statementHandle provides statement resources
     */
    public void route(EventBean event, EPStatementHandle statementHandle);
}
