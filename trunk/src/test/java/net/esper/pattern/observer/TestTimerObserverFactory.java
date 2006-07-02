package net.esper.pattern.observer;

import net.esper.schedule.ScheduleSpec;
import net.esper.pattern.observer.TimerIntervalObserver;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.TimerAtObserver;
import net.esper.pattern.observer.TimerObserverFactory;
import junit.framework.TestCase;

public class TestTimerObserverFactory extends TestCase
{
    public void testCron()
    {
        ScheduleSpec spec = new ScheduleSpec();
        TimerObserverFactory factory = new TimerObserverFactory(spec);

        EventObserver eventObserver = factory.makeObserver(null, null, null);

        assertTrue(eventObserver instanceof TimerAtObserver);
    }

    public void testIntervalWait()
    {
        TimerObserverFactory factory = new TimerObserverFactory(10);

        EventObserver eventObserver = factory.makeObserver(null, null, null);

        assertTrue(eventObserver instanceof TimerIntervalObserver);
    }
}
