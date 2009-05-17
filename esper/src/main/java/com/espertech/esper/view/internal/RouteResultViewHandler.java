package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;

/**
 * Handler for incoming events for split-stream syntax, encapsulates where-clause evaluation strategies.
 */
public interface RouteResultViewHandler
{
    /**
     * Handle event.
     * @param event to handle
     * @return true if at least one match was found, false if not 
     */
    public boolean handle(EventBean event);
}
