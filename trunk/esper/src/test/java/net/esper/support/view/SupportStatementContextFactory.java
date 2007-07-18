package net.esper.support.view;

import net.esper.core.StatementContext;
import net.esper.schedule.SchedulingService;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.view.ViewResolutionServiceImpl;
import net.esper.view.ViewEnumHelper;
import net.esper.pattern.PatternObjectResolutionServiceImpl;
import net.esper.eql.view.OutputConditionFactory;
import net.esper.eql.view.OutputConditionFactoryDefault;

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
                new ViewResolutionServiceImpl(ViewEnumHelper.getBuiltinViews()),
                new PatternObjectResolutionServiceImpl(null),
                null,
                null,
                null,
                null,
                null,
                null,
                new OutputConditionFactoryDefault());
    }
}
