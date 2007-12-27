package net.esper.pattern.guard;

import junit.framework.TestCase;
import net.esper.core.StatementContext;
import net.esper.pattern.PatternContext;
import net.esper.schedule.SchedulingService;
import net.esper.schedule.SchedulingServiceImpl;
import net.esper.support.guard.SupportQuitable;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.view.SupportStatementContextFactory;

public class TestTimerWithinGuard extends TestCase
{
    private TimerWithinGuard guard;
    private SchedulingService scheduleService;
    private SupportQuitable quitable;

    public void setUp()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(new SchedulingServiceImpl());
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
