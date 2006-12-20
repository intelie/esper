package net.esper.pattern.observer;

import net.esper.schedule.ScheduleSpec;
import net.esper.pattern.observer.TimerIntervalObserver;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.TimerAtObserver;
import net.esper.pattern.observer.TimerObserverFactory;
import net.esper.pattern.PatternContext;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.pattern.SupportPatternContextFactory;
import junit.framework.TestCase;

public class TestTimerObserverFactory extends TestCase
{
    private PatternContext patternContext;

    public void setUp()
    {
        patternContext = SupportPatternContextFactory.makeContext();
    }

    public void testCron()
    {
        ScheduleSpec spec = new ScheduleSpec();
        TimerObserverFactory factory = new TimerObserverFactory(spec);

        EventObserver eventObserver = factory.makeObserver(patternContext, null, null);

        assertTrue(eventObserver instanceof TimerAtObserver);
    }

    public void testIntervalWait()
    {
        TimerObserverFactory factory = new TimerObserverFactory(10);

        EventObserver eventObserver = factory.makeObserver(patternContext, null, null);

        assertTrue(eventObserver instanceof TimerIntervalObserver);
    }
}
