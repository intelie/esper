package net.esper.pattern.observer;

import net.esper.schedule.SchedulingServiceImpl;
import net.esper.schedule.ScheduleSpec;
import net.esper.schedule.ScheduleUnit;
import net.esper.support.guard.SupportObserverEvaluator;
import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.pattern.observer.TimerAtObserver;
import junit.framework.TestCase;

public class TestTimerCronObserver extends TestCase
{
    private TimerAtObserver observer;
    private SchedulingServiceImpl scheduleService;
    private SupportObserverEvaluator evaluator;
    private MatchedEventMap beginState;

    public void setUp()
    {
        beginState = new MatchedEventMap();

        scheduleService = new SchedulingServiceImpl();
        PatternContext context = new PatternContext(null, scheduleService);

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
        scheduleService.evaluate();
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));

        // Test start again
        observer.startObserve();
        scheduleService.setTime(60999);
        scheduleService.evaluate();
        assertEquals(0, evaluator.getMatchEvents().size());

        scheduleService.setTime(61000); // 1 minute plus 1 second
        scheduleService.evaluate();
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));
    }

    public void testStartAndStop()
    {
        // Start then stop
        scheduleService.setTime(0);
        observer.startObserve();
        observer.stopObserve();
        scheduleService.setTime(1000);
        scheduleService.evaluate();
        assertEquals(0, evaluator.getAndClearMatchEvents().size());

        // Test start again
        observer.startObserve();
        scheduleService.setTime(61000);
        scheduleService.evaluate();
        assertEquals(beginState, evaluator.getAndClearMatchEvents().get(0));

        observer.stopObserve();
        observer.startObserve();

        scheduleService.setTime(150000);
        scheduleService.evaluate();
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
