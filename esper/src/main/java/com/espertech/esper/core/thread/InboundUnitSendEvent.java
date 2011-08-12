/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core.thread;

import com.espertech.esper.core.EPRuntimeImpl;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * Inbound unit for unwrapped events.
 */
public class InboundUnitSendEvent implements InboundUnitRunnable
{
    private static final Log log = LogFactory.getLog(InboundUnitSendEvent.class);
    private final Object event;
    private final EPRuntimeImpl runtime;

    /**
     * Ctor.
     * @param event to process
     * @param runtime to process event
     */
    public InboundUnitSendEvent(Object event, EPRuntimeImpl runtime)
    {
        this.event = event;
        this.runtime = runtime;
    }

    public void run()
    {
        try
        {
            runtime.processEvent(event);
        }
        catch (RuntimeException e)
        {
            log.error("Unexpected error processing unwrapped event: " + e.getMessage(), e);
        }
    }
}
