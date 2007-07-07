package net.esper.support.view;

import net.esper.core.StatementContext;
import net.esper.schedule.SchedulingService;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.view.ViewResolutionServiceImpl;

public class SupportStatementContextFactory
{
    public static StatementContext makeContext()
    {
        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
        return makeContext(sched);                
    }

    public static StatementContext makeContext(SchedulingService stub)
    {
        return new StatementContext("engURI",
                "engInstId",
                "stmtId",
                "stmtName",
                "exprHere",
                stub,
                stub.allocateBucket(),
                SupportEventAdapterService.getService(),
                null,
                new ViewResolutionServiceImpl(null),
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
