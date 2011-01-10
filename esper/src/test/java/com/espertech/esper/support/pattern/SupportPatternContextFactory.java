package com.espertech.esper.support.pattern;

import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.core.StatementContext;

public class SupportPatternContextFactory
{
    public static PatternContext makeContext()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext();
        return new PatternContext(stmtContext, 1);
    }
}
