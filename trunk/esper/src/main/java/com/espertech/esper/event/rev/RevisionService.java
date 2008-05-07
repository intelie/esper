package com.espertech.esper.event.rev;

import com.espertech.esper.client.ConfigurationRevisionEventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.StatementStopService;

import java.util.Map;

/**
 * Service associating named windows and revision event types.
 */
public interface RevisionService
{
    /**
     * Called at initialization time, verifies configurations provided.
     * @param config to use
     * @param eventAdapterService for obtaining event type information for each alias
     */
    public void init(Map<String, ConfigurationRevisionEventType> config, EventAdapterService eventAdapterService);

    /**
     * Called at runtime to add new revision event types.
     * @param alias to add
     * @param config the revision event type configuration
     * @param eventAdapterService for obtaining event type information for each alias
     */
    public void add(String alias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService);

    /**
     * Upon named window creation, and during resolution of type specified as part of a named window create statement,
     * returns looks up the revision event type alias provided and return the revision event type if found, or null if not found.
     * @param alias to look up
     * @return null if not found, of event type
     */
    public EventType getRevisionUnderlyingType(String alias);

    /**
     * Upon named window creation, create a unique revision event type that this window processes.
     * @param namedWindowName name of window
     * @param alias alias to use
     * @param statementStopService for handling stops
     * @param eventAdapterService for event type info
     * @return revision event type
     */
    public EventType createRevisionType(String namedWindowName, String alias, StatementStopService statementStopService, EventAdapterService eventAdapterService);

    /**
     * Upon named window creation, check if the alias used is a revision event type alias.
     * @param alias to check
     * @return true if revision event type, false if not
     */
    public boolean isRevisionTypeAlias(String alias);

    /**
     * For use with insert-into when processing selected events, check if the specified insert matches event types.
     * @param namedWindowName name window
     * @param eventType type to check
     * @return revision event type
     */
    public EventType getIsNamedWindowRevisionType(String namedWindowName, EventType eventType);

    /**
     * Creates a new revision event processor upon start of a create window statement.
     * @param alias of the named window (not the revision event type)
     * @return processor
     */
    public RevisionProcessor getRevisionProcessor(String alias);
}
