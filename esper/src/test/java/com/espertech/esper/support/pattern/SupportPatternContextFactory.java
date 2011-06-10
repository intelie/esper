package com.espertech.esper.support.pattern;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.support.view.SupportStatementContextFactory;

public class SupportPatternContextFactory
{
    public static PatternContext makeContext()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext();
        return new PatternContext(stmtContext, 1, null);
    }
}
