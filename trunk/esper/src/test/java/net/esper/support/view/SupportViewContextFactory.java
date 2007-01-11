package net.esper.support.view;

import net.esper.view.ViewServiceContext;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.schedule.ScheduleBucket;

public class SupportViewContextFactory
{
    public static ViewServiceContext makeContext()
    {
        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
        ScheduleBucket bucket = sched.allocateBucket();
        return new ViewServiceContext(sched, bucket, SupportEventAdapterService.getService(), null);
    }

    public static ViewServiceContext makeContext(SupportSchedulingServiceImpl stub)
    {
        return new ViewServiceContext(stub, stub.allocateBucket(), SupportEventAdapterService.getService(), null);
    }
}
