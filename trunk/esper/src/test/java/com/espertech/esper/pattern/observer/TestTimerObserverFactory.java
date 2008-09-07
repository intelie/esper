package com.espertech.esper.pattern.observer;

import com.espertech.esper.schedule.ScheduleSpec;
import com.espertech.esper.pattern.observer.TimerIntervalObserver;
import com.espertech.esper.pattern.observer.EventObserver;
import com.espertech.esper.pattern.observer.TimerAtObserver;
import com.espertech.esper.pattern.observer.TimerIntervalObserverFactory;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.support.pattern.SupportPatternContextFactory;
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
