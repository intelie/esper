package net.esper.pattern.observer;

import junit.framework.TestCase;
import net.esper.pattern.MatchedEventMap;
import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMapImpl;
import net.esper.schedule.SchedulingServiceImpl;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.guard.SupportObserverEvaluator;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.core.StatementContext;

public class TestTimerIntervalObserver extends TestCase
{
    private PatternContext context;
    private TimerIntervalObserver observer;
    private SchedulingServiceImpl scheduleService;
    private SupportObserverEvaluator evaluator;
    private MatchedEventMap beginState;

    public void setUp()
    {
        
        beginState = new MatchedEventMapImpl();

        scheduleService = new SchedulingServiceImpl();
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(scheduleService);
        context = new PatternContext(stmtContext, 1, null);

        evaluator = new SupportObserverEvaluator();

        observer =  new TimerIntervalObserver(1000, context, beginState, evaluator);
    }

    public void testStartAndObserve()
    {
        scheduleService.setTime(0);
        observer.startObserve();
        scheduleService.setTime(1000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));

        // Test start again
        observer.startObserve();
        scheduleService.setTime(1999);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(0, evaluator.getMatchEvents().size());

        scheduleService.setTime(2000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));
    }

    public void testStartAndStop()
    {
        // Start then stop
        scheduleService.setTime(0);
        observer.startObserve();
        observer.stopObserve();
        scheduleService.setTime(1000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(0, evaluator.getAndClearMatchEvents().size());

        // Test start again
        observer.startObserve();
        scheduleService.setTime(2500);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));

        observer.stopObserve();
        observer.startObserve();

        scheduleService.setTime(3500);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));
    }

    public void testImmediateTrigger()
    {
        // Should fireStatementStopped right away, wait time set to zero
        observer =  new TimerIntervalObserver(0, context, beginState, evaluator);

        scheduleService.setTime(0);
        observer.startObserve();
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));
        scheduleService.setTime(10000000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(0, evaluator.getAndClearMatchEvents().size());
    }

    public void testInvalid()
    {
        try
        {
            observer.startObserve();
            observer.startObserve();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected exception
        }
    }

}
