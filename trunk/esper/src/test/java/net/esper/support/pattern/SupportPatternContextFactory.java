package net.esper.support.pattern;

import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.pattern.PatternContext;

public class SupportPatternContextFactory
{
    public static PatternContext makeContext()
    {
        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
        return new PatternContext(null, sched, sched.allocateBucket(), SupportEventAdapterService.getService(), null);
    }
}
