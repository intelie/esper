package net.esper.support.pattern;

import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.pattern.PatternContext;
import net.esper.core.StatementContext;

public class SupportPatternContextFactory
{
    public static PatternContext makeContext()
    {
        StatementContext stmtContext = SupportStatementContextFactory.makeContext();
        return new PatternContext(stmtContext, 1, null);
    }
}
