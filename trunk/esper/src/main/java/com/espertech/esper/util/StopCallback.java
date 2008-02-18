package com.espertech.esper.util;

/**
 * General pupose callback to stop a resource and free it's underlying resources.
 */
public interface StopCallback
{
    /**
     * Stops the underlying resources.
     */
    public void stop();
}
