package com.espertech.esper.client;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

/**
 * Configuration information for revision event types.
 * <p>
 * The configuration information consists of the names of the base event type and the delta event types,
 * as well as the names of properties that supply key values, and a strategy.
 * <p>
 * Events of the base event type arrive before delta events; Delta events arriving before the base event
 * for the same key value are not processed, as delta events as well as base events represent new versions.
 */
public class ConfigurationRevisionEventType implements Serializable
{
    private Set<String> aliasBaseEventTypes;
    private Set<String> aliasDeltaEventTypes;
    private PropertyRevision propertyRevision;
    private String[] keyPropertyNames;

    /**
     * Ctor.
     */
    public ConfigurationRevisionEventType()
    {
        aliasBaseEventTypes = new HashSet<String>();
        aliasDeltaEventTypes = new HashSet<String>();
        propertyRevision = PropertyRevision.OVERLAY_DECLARED;
    }

    /**
     * Add a base event type by it's alias name.
     * @param aliasBaseEventType the name of the base event type to add
     */
    public void addAliasBaseEventType(String aliasBaseEventType)
    {
        aliasBaseEventTypes.add(aliasBaseEventType);
    }

    /**
     * Returns the set of event type aliases that are base event types.
     * @return aliases of base event types
     */
    public Set<String> getAliasBaseEventTypes()
    {
        return aliasBaseEventTypes;
    }

    /**
     * Returns the set of names of delta event types.
     * @return names of delta event types
     */
    public Set<String> getAliasDeltaEventTypes()
    {
        return aliasDeltaEventTypes;
    }

    /**
     * Add a delta event type by it's alias name.
     * @param aliasDeltaEventType the name of the delta event type to add
     */
    public void addAliasDeltaEventType(String aliasDeltaEventType)
    {
        aliasDeltaEventTypes.add(aliasDeltaEventType);
    }

    /**
     * Returns the enumeration value defining the strategy to use for overlay or merging
     * multiple versions of an event (instances of base and delta events).
     * @return strategy enumerator
     */
    public PropertyRevision getPropertyRevision()
    {
        return propertyRevision;
    }

    /**
     * Sets the enumeration value defining the strategy to use for overlay or merging
     * multiple versions of an event (instances of base and delta events).
     * @param propertyRevision strategy enumerator
     */
    public void setPropertyRevision(PropertyRevision propertyRevision)
    {
        this.propertyRevision = propertyRevision;
    }

    /**
     * Returns the key property names, which are the names of the properties that supply key values for relating
     * base and delta events.
     * @return array of names of key properties
     */
    public String[] getKeyPropertyNames()
    {
        return keyPropertyNames;
    }

    /**
     * Sets the key property names, which are the names of the properties that supply key values for relating
     * base and delta events.
     * @param keyPropertyNames array of names of key properties
     */
    public void setKeyPropertyNames(String[] keyPropertyNames)
    {
        this.keyPropertyNames = keyPropertyNames;
    }

    /**
     * Enumeration for specifying a strategy to use to merge or overlay properties.
     */
    public enum PropertyRevision
    {
        /**
         * A strategy for revising events by overlaying groups of properties provided by delta events
         * onto the full event.
         * <p>
         * For use when there is a small number of combinations of properties that change on an event,
         * and such combinations are known in advance.
         * <p>
         * The event type specified as "full" event type provides all properties. "Delta" event types
         * do not add properties to the resulting type.
         * <p>
         * Null values or (dynamic) properties that do not exist on delta events provide null values to resulting revision events.
         */
        OVERLAY_DECLARED,

        /**
         * A strategy for revising events by merging properties provided by delta events
         * onto a reflection of a full event, considering null values and non-existing (dynamic) properties as well.
         * <p>
         * For use when there is a larger number of combinations of properties that change on an event,
         * or combinations are not known in advance.
         * <p>
         * The properties of the revised event are all properties of the "full" event type plus
         * any additional property that any of the "Delta" event types adds.
         * <p>
         * Null values or (dynamic) properties that do not exist on delta events provide null values to resulting revision events.
         */
        MERGE_DECLARED,

        /**
         * A strategy for revising events by merging properties provided by delta events
         * onto a reflection of a full event, considering only non-null values.
         * <p>
         * For use when there is a larger number of combinations of properties that change on an event,
         * or combinations are not known in advance.
         * <p>
         * The properties of the revised event are all properties of the "full" event type plus
         * any additional property that any of the "Delta" event types adds.
         * <p>
         * Null values returned by delta event properties provide no value to resulting revision events (is not merged).
         */
        MERGE_NON_NULL,

        /**
         * A strategy for revising events by merging properties provided by delta events
         * onto a reflection of a full event, considering only values supplied by event properties that exist.
         * <p>
         * For use when there is a larger number of combinations of properties that change on an event,
         * or combinations are not known in advance.
         * <p>
         * The properties of the revised event are all properties of the "full" event type plus
         * any additional property that any of the "Delta" event types adds.
         * <p>
         * All properties are treated as dynamic properties, and if an event property
         * does not exist on a delta event the property it provides no value to resulting revision events (is not merged).
         */
        MERGE_EXISTS
    }
}
