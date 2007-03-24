package net.esper.support.view;

import net.esper.core.StatementContext;
import net.esper.view.ViewResolutionServiceImpl;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.schedule.ScheduleBucket;

public class SupportStatementContextFactory
{
    public static StatementContext makeContext()
    {
        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
        ScheduleBucket bucket = sched.allocateBucket();
        return makeContext(sched);                
    }

    public static StatementContext makeContext(SupportSchedulingServiceImpl stub)
    {
        return new StatementContext("stmtId", "stmtName", "exprHere", stub, stub.allocateBucket(),
                SupportEventAdapterService.getService(), null, new ViewResolutionServiceImpl(null), null, null, null);
    }
}
