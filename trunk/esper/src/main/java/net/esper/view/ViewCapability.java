package net.esper.view;

import net.esper.core.StatementContext;

import java.util.List;

/**
 * Interface for use by expression nodes to indicate view resource requirements
 * allowing inspection and modification of view factories.
 */
public interface ViewCapability
{
    /**
     * Inspect view factories returning false to indicate that view factories do not meet
     * view resource requirements, or true to indicate view capability and view factories can be compatible.
     * @param streamNumber is the number of the stream
     * @param viewFactories is a list of view factories that originate the final views
     * @param statementContext is the statement-level services
     * @return true to indicate inspection success, or false to indicate inspection failure
     */
    public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext);
}
