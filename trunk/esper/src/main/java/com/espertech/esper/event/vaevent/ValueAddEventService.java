package com.espertech.esper.event.vaevent;

import com.espertech.esper.client.ConfigurationRevisionEventType;
import com.espertech.esper.client.ConfigurationVariantStream;
import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.StatementStopService;

import java.util.Map;

/**
 * Service associating handling vaue-added event types, such a revision event types and variant stream event types.
 * <p>
 * Associates named windows and revision event types.
 */
public interface ValueAddEventService
{
    /**
     * Called at initialization time, verifies configurations provided.
     * @param revisionTypes is the revision types to add
     * @param variantStreams is the variant streams to add
     * @param eventAdapterService for obtaining event type information for each alias
     */
    public void init(Map<String, ConfigurationRevisionEventType> revisionTypes, Map<String, ConfigurationVariantStream> variantStreams, EventAdapterService eventAdapterService);

    /**
     * Adds a new revision event types.
     * @param alias to add
     * @param config the revision event type configuration
     * @param eventAdapterService for obtaining event type information for each alias
     */
    public void addRevisionEventType(String alias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService);

    /**
     * Adds a new variant stream.
     * @param variantEventTypeAlias the alias of the type
     * @param variantStreamConfig the configs
     * @param eventAdapterService for handling nested events
     * @throws ConfigurationException if the configuration is invalid
     */
    public void addVariantStream(String variantEventTypeAlias, ConfigurationVariantStream variantStreamConfig, EventAdapterService eventAdapterService)
            throws ConfigurationException;

    /**
     * Upon named window creation, and during resolution of type specified as part of a named window create statement,
     * returns looks up the revision event type alias provided and return the revision event type if found, or null if not found.
     * @param alias to look up
     * @return null if not found, of event type
     */
    public EventType getValueAddUnderlyingType(String alias);

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
     * Gets a value-added event processor.
     * @param alias of the value-add events
     * @return processor
     */
    public ValueAddEventProcessor getValueAddProcessor(String alias);
}
