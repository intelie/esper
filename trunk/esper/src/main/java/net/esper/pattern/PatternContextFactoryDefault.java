package net.esper.pattern;

import net.esper.core.StatementContext;

/**
 * Default pattern context factory.
 */
public class PatternContextFactoryDefault implements PatternContextFactory
{
    public PatternContext createContext(StatementContext statementContext,
                                        int streamId,
                                        EvalRootNode rootNode)
    {
        PatternStateFactory patternStateFactory = new PatternStateFactoryImpl();

        PatternContext patternContext = new PatternContext(statementContext, streamId, patternStateFactory);

        patternStateFactory.setContext(patternContext);

        return patternContext;
    }
}
