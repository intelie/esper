package net.esper.pattern;

import net.esper.core.StatementContext;

/**
 * Factory for pattern context instances, creating context objects for each distinct pattern based on the
 * patterns root node and stream id.
 */
public interface PatternContextFactory
{
    /**
     * Create a pattern context.
     * @param statementContext is the statement information and services
     * @param streamId is the stream id
     * @param rootNode is the pattern root node
     * @return pattern context
     */
    public PatternContext createContext(StatementContext statementContext,
                                        int streamId,
                                        EvalRootNode rootNode);
}
