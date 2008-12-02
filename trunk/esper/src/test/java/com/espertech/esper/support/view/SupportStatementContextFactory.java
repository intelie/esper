package com.espertech.esper.support.view;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.core.StatementResultServiceImpl;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.view.ViewResolutionServiceImpl;
import com.espertech.esper.view.ViewEnumHelper;
import com.espertech.esper.view.ViewFactoryContext;
import com.espertech.esper.pattern.PatternObjectResolutionServiceImpl;
import com.espertech.esper.epl.view.OutputConditionFactoryDefault;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esper.epl.core.EngineImportServiceImpl;
import com.espertech.esper.epl.named.NamedWindowServiceImpl;
import com.espertech.esper.epl.variable.VariableServiceImpl;
import com.espertech.esper.event.vaevent.ValueAddEventServiceImpl;
import com.espertech.esper.client.Configuration;

public class SupportStatementContextFactory
{
    public static StatementContext makeContext()
    {
        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
        return makeContext(sched);                
    }

    public static ViewFactoryContext makeViewContext()
    {
        StatementContext stmtContext = makeContext();
        return new ViewFactoryContext(stmtContext, 1, 1, "somenamespacetest", "somenametest");
    }

    public static StatementContext makeContext(SchedulingService stub)
    {
        VariableServiceImpl variableService = new VariableServiceImpl(1000, null, null);
        Configuration config = new Configuration();
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);

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
                new NamedWindowServiceImpl(null, variableService),
                null,
                new StatementResultServiceImpl(null, null), // statement result svc
                null, // resolution URIs
                new ValueAddEventServiceImpl(), // revison svc
                config);
    }
}
