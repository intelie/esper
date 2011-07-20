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

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.core.StatementResultServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Outbound unit.
 */
public class OutboundUnitRunnable implements Runnable
{
    private static final Log log = LogFactory.getLog(OutboundUnitRunnable.class);

    private final UniformPair<EventBean[]> events;
    private final StatementResultServiceImpl statementResultService;

    /**
     * Ctor.
     * @param events to dispatch
     * @param statementResultService handles result indicate
     */
    public OutboundUnitRunnable(UniformPair<EventBean[]> events, StatementResultServiceImpl statementResultService)
    {
        this.events = events;
        this.statementResultService = statementResultService;
    }

    public void run()
    {
        try
        {
            statementResultService.processDispatch(events);
        }
        catch (RuntimeException e)
        {
            log.error("Unexpected error processing dispatch: " + e.getMessage(), e);
        }
    }
}
