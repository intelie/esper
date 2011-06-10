package com.espertech.esper.pattern.observer;

import junit.framework.TestCase;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMapImpl;
import com.espertech.esper.schedule.ScheduleSpec;
import com.espertech.esper.type.ScheduleUnit;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.support.guard.SupportObserverEvaluator;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.timer.TimeSourceService;
import com.espertech.esper.timer.TimeSourceServiceImpl;

public class TestTimerCronObserver extends TestCase
{
    private TimerAtObserver observer;
    private SchedulingServiceImpl scheduleService;
    private SupportObserverEvaluator evaluator;
    private MatchedEventMap beginState;

    public void setUp()
    {
        beginState = new MatchedEventMapImpl();

        scheduleService = new SchedulingServiceImpl(new TimeSourceServiceImpl());
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(scheduleService);
        PatternContext context = new PatternContext(stmtContext, 1, null);

        ScheduleSpec scheduleSpec = new ScheduleSpec();
        scheduleSpec.addValue(ScheduleUnit.SECONDS, 1);

        evaluator = new SupportObserverEvaluator(context);

        observer =  new TimerAtObserver(scheduleSpec, beginState, evaluator);
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
        scheduleService.setTime(60999);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(0, evaluator.getMatchEvents().size());

        scheduleService.setTime(61000); // 1 minute plus 1 second
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
        scheduleService.setTime(61000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));

        observer.stopObserve();
        observer.startObserve();

        scheduleService.setTime(150000);
        SupportSchedulingServiceImpl.evaluateSchedule(scheduleService);
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));
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
