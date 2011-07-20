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
import com.espertech.esper.timer.TimeSourceServiceImpl;

public class TestTimerWithinOrMaxCountGuard extends TestCase {
    private TimerWithinOrMaxCountGuard guard;
    private SchedulingService scheduleService;
    private SupportQuitable quitable;

    public void setUp()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(new SchedulingServiceImpl(new TimeSourceServiceImpl()));
        PatternContext context = new PatternContext(stmtContext, 1, null);
        scheduleService = stmtContext.getSchedulingService();

        quitable = new SupportQuitable(context);

        guard =  new TimerWithinOrMaxCountGuard(1000, 2, quitable);
    }

    public void testInspect() {
        assertTrue(guard.inspect(null));
    }

    public void testInspect_max_count_exceeeded() {
        assertTrue(guard.inspect(null));
        assertTrue(guard.inspect(null));
        assertFalse(guard.inspect(null));
    }

    public void testStartAndTrigger_count() {
        guard.startGuard();

        assertEquals(0, quitable.getAndResetQuitCounter());

        guard.inspect(null);
        guard.inspect(null);
        guard.inspect(null);
        scheduleService.setTime(1000);

        assertEquals(1, quitable.getAndResetQuitCounter());
    }

    public void testStartAndTrigger_time() {
        scheduleService.setTime(0);

        guard.startGuard();

        assertEquals(0, quitable.getAndResetQuitCounter());

        scheduleService.setTime(1000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);

        assertEquals(1, quitable.getAndResetQuitCounter());
    }

    public void testStartAndTrigger_time_and_count() {
        scheduleService.setTime(0);

        guard.startGuard();

        assertEquals(0, quitable.getAndResetQuitCounter());
        guard.inspect(null);
        guard.inspect(null);
        guard.inspect(null);

        scheduleService.setTime(1000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);

        assertEquals(1, quitable.getAndResetQuitCounter());
    }

    public void testStartAndStop() {
        scheduleService.setTime(0);

        guard.startGuard();

        guard.stopGuard();

        scheduleService.setTime(1001);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);

        assertEquals(0, quitable.getAndResetQuitCounter());
    }

    public void testInvalid() {
        try {
            guard.startGuard();
            guard.startGuard();
            fail();
        }
        catch (IllegalStateException ex) {
            // Expected exception
        }
    }
}
