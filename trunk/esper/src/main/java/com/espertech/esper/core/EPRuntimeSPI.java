package com.espertech.esper.core;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.event.EventBean;

/**
 * SPI interface of the runtime exposes fire-and-forget, non-continuous query functionality.
 */
public interface EPRuntimeSPI extends EPRuntime
{
    /**
     * Execute a free-form EPL dynamically, non-continuously, in a fire-and-forget fashion, against named windows.
     * @param epl is the EPL to execute
     * @return query result
     */
    public EPQueryResult executeQuery(String epl);

    /**
     * Prepare a non-continuous, fire-and-forget query for repeated execution.
     * @param epl to prepare
     * @return proxy to execute upon, that also provides the event type of the returned results
     */
    public EPPreparedQuery prepareQuery(String epl);
}
