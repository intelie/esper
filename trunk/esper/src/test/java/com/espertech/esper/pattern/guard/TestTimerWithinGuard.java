package com.espertech.esper.pattern.guard;

import junit.framework.TestCase;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.support.guard.SupportQuitable;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.timer.TimeSourceServiceMillis;

public class TestTimerWithinGuard extends TestCase
{
    private TimerWithinGuard guard;
    private SchedulingService scheduleService;
    private SupportQuitable quitable;

    public void setUp()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(new SchedulingServiceImpl(new TimeSourceServiceMillis()));
        PatternContext context = new PatternContext(stmtContext, 1, null);
        scheduleService = stmtContext.getSchedulingService();

        quitable = new SupportQuitable();

        guard =  new TimerWithinGuard(1000, context, quitable);
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
