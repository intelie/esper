package com.espertech.esper.core;

/**
 * Observer statement management events.
 */
public interface StatementLifecycleObserver
{
    /**
     * Observer statement state changes.
     * @param event indicates statement changed
     */
    public void observe(StatementLifecycleEvent event); 
}
