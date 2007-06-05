package net.esper.core;

import net.esper.event.EventBean;

/**
 * Interface for a service that routes events within the engine for further processing.
 */
public interface InternalEventRouter
{
    /**
     * Route the event such that the event is processed as required.
     * @param events to route
     */
    public void route(EventBean[] events, EPStatementHandle statementHandle);
}
