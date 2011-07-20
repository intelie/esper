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

import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Inbound unit for wrapped events.
 */
public class InboundUnitSendWrapped implements InboundUnitRunnable
{
    private static final Log log = LogFactory.getLog(InboundUnitSendWrapped.class);
    private final EventBean eventBean;
    private final EPRuntimeEventSender runtime;

    /**
     * Ctor.
     * @param event inbound event, wrapped
     * @param runtime to process
     */
    public InboundUnitSendWrapped(EventBean event, EPRuntimeEventSender runtime)
    {
        this.eventBean = event;
        this.runtime = runtime;
    }

    public void run()
    {
        try
        {
            runtime.processWrappedEvent(eventBean);
        }
        catch (RuntimeException e)
        {
            log.error("Unexpected error processing wrapped event: " + e.getMessage(), e);
        }
    }
}
