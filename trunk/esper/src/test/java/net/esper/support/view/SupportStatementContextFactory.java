package net.esper.support.view;

import net.esper.view.StatementServiceContext;
import net.esper.view.ViewResolutionServiceImpl;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.schedule.ScheduleBucket;

public class SupportStatementContextFactory
{
    public static StatementServiceContext makeContext()
    {
        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
        ScheduleBucket bucket = sched.allocateBucket();
        return new StatementServiceContext("stmtId", "stmtName", sched, bucket, SupportEventAdapterService.getService(), null, new ViewResolutionServiceImpl(null), null, null);
    }

    public static StatementServiceContext makeContext(SupportSchedulingServiceImpl stub)
    {
        return new StatementServiceContext("stmtId", "stmtName", stub, stub.allocateBucket(), SupportEventAdapterService.getService(), null, new ViewResolutionServiceImpl(null), null, null);
    }
}
