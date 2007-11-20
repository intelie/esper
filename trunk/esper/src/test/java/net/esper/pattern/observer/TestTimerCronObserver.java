package net.esper.pattern.observer;

import junit.framework.TestCase;
import net.esper.pattern.MatchedEventMap;
import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMapImpl;
import net.esper.schedule.ScheduleSpec;
import net.esper.type.ScheduleUnit;
import net.esper.schedule.SchedulingServiceImpl;
import net.esper.support.guard.SupportObserverEvaluator;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.core.StatementContext;

public class TestTimerCronObserver extends TestCase
{
    private TimerAtObserver observer;
    private SchedulingServiceImpl scheduleService;
    private SupportObserverEvaluator evaluator;
    private MatchedEventMap beginState;

    public void setUp()
    {
        beginState = new MatchedEventMapImpl();

        scheduleService = new SchedulingServiceImpl();
        StatementContext stmtContext = SupportStatementContextFactory.makeContext(scheduleService);
        PatternContext context = new PatternContext(stmtContext, 1, null);

        ScheduleSpec scheduleSpec = new ScheduleSpec();
        scheduleSpec.addValue(ScheduleUnit.SECONDS, 1);

        evaluator = new SupportObserverEvaluator();

        observer =  new TimerAtObserver(scheduleSpec, context, beginState, evaluator);
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
