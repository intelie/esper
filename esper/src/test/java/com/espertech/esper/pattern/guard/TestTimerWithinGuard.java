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

package com.espertech.esper.pattern.guard;

import junit.framework.TestCase;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.support.guard.SupportQuitable;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.timer.TimeSourceService;
import com.espertech.esper.timer.TimeSourceServiceImpl;

public class TestTimerWithinGuard extends TestCase
{
    private TimerWithinGuard guard;
    private SchedulingService scheduleService;
    private SupportQuitable quitable;

    public void setUp()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(new SchedulingServiceImpl(new TimeSourceServiceImpl()));
        PatternContext context = new PatternContext(stmtContext, 1, null);
        scheduleService = stmtContext.getSchedulingService();

        quitable = new SupportQuitable(context);

        guard =  new TimerWithinGuard(1000, quitable);
    }

    public void testInspect()
    {
        assertTrue(guard.inspect(null));
    }

    /**
     * Make sure the timer calls guardQuit after the set time period
     */
    public void testStartAndTrigger()
    {
        scheduleService.setTime(0);

        guard.startGuard();

        assertEquals(0, quitable.getAndResetQuitCounter());

        scheduleService.setTime(1000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);

        assertEquals(1, quitable.getAndResetQuitCounter());
    }

    public void testStartAndStop()
    {
        scheduleService.setTime(0);

        guard.startGuard();

        guard.stopGuard();

        scheduleService.setTime(1001);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);        

        assertEquals(0, quitable.getAndResetQuitCounter());
    }

    public void testInvalid()
    {
        try
        {
            guard.startGuard();
            guard.startGuard();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected exception
        }
    }
}
