package net.esper.pattern.observer;

import net.esper.schedule.ScheduleSpec;
import net.esper.pattern.observer.TimerIntervalObserver;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.TimerAtObserver;
import net.esper.pattern.observer.TimerIntervalObserverFactory;
import net.esper.pattern.PatternContext;
import net.esper.support.pattern.SupportPatternContextFactory;
import junit.framework.TestCase;

import java.util.Arrays;

public class TestTimerObserverFactory extends TestCase
{
    private PatternContext patternContext;

    public void setUp()
    {
        patternContext = SupportPatternContextFactory.makeContext();
    }

    public void testIntervalWait() throws Exception
    {
        TimerIntervalObserverFactory factory = new TimerIntervalObserverFactory();
        factory.setObserverParameters(Arrays.asList(new Object[] {1}));
        EventObserver eventObserver = factory.makeObserver(patternContext, null, null, null, null);

        assertTrue(eventObserver instanceof TimerIntervalObserver);
    }
}
