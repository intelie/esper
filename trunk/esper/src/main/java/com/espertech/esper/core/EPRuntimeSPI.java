/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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
