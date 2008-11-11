package com.espertech.esper.pattern.observer;

import junit.framework.TestCase;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMapImpl;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.support.guard.SupportObserverEvaluator;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.timer.TimeSourceService;

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

        scheduleService = new SchedulingServiceImpl(new TimeSourceService());
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
