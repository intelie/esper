package net.esper.pattern.guard;

import junit.framework.TestCase;
import net.esper.pattern.PatternContext;
import net.esper.schedule.SchedulingServiceImpl;
import net.esper.support.guard.SupportQuitable;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;

public class TestTimerWithinGuard extends TestCase
{
    private TimerWithinGuard guard;
    private SchedulingServiceImpl scheduleService;
    private SupportQuitable quitable;

    public void setUp()
    {
        scheduleService = new SchedulingServiceImpl();
        PatternContext context = new PatternContext(null, scheduleService, scheduleService.allocateBucket(), SupportEventAdapterService.getService(), null);

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
