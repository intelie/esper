package com.espertech.esper.client;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

public class ConfigurationRevisionEventType implements Serializable
{
    private String aliasFullEventType;
    private Set<String> aliasDeltaEventTypes;
    private PropertyRevision propertyRevision;
    private String[] keyPropertyNames;

    public ConfigurationRevisionEventType()
    {
        aliasDeltaEventTypes = new HashSet<String>();
        propertyRevision = PropertyRevision.OVERLAY_DECLARED;
    }

    public String getAliasFullEventType()
    {
        return aliasFullEventType;
    }

    public void setAliasFullEventType(String aliasFullEventType)
    {
        this.aliasFullEventType = aliasFullEventType;
    }

    public Set<String> getAliasDeltaEventTypes()
    {
        return aliasDeltaEventTypes;
    }

    public void addAliasDeltaEvent(String aliasRevisionEvent)
    {
        aliasDeltaEventTypes.add(aliasRevisionEvent);
    }

    public PropertyRevision getPropertyRevision()
    {
        return propertyRevision;
    }

    public void setPropertyRevision(PropertyRevision propertyRevision)
    {
        this.propertyRevision = propertyRevision;
    }

    public String[] getKeyPropertyNames()
    {
        return keyPropertyNames;
    }

    public void setKeyPropertyNames(String[] keyPropertyNames)
    {
        this.keyPropertyNames = keyPropertyNames;
    }

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
