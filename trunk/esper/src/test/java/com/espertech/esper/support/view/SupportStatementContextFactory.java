package com.espertech.esper.support.view;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.core.StatementResultServiceImpl;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.view.ViewResolutionServiceImpl;
import com.espertech.esper.view.ViewEnumHelper;
import com.espertech.esper.pattern.PatternObjectResolutionServiceImpl;
import com.espertech.esper.epl.view.OutputConditionFactoryDefault;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esper.epl.core.EngineImportServiceImpl;

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
                new MethodResolutionServiceImpl(new EngineImportServiceImpl()),
                null,
                null,
                null,
                new OutputConditionFactoryDefault(),
                null,
                null,
                new StatementResultServiceImpl(null) // statement result svc
                );
    }
}
