package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.EventType;
import com.espertech.esper.client.ConfigurationVariantStream;

/**
 * Specification for a variant event stream.
 */
public class VariantSpec
{
    private final String variantStreamName;
    private final EventType[] eventTypes;
    private final ConfigurationVariantStream.TypeVariance typeVariance;

    /**
     * Ctor.
     * @param variantStreamName name of variant stream
     * @param eventTypes types of events for variant stream, or empty list
     * @param typeVariance enum specifying type variance
     */
    public VariantSpec(String variantStreamName, EventType[] eventTypes, ConfigurationVariantStream.TypeVariance typeVariance)
    {
        this.variantStreamName = variantStreamName;
        this.eventTypes = eventTypes;
        this.typeVariance = typeVariance;
    }

    /**
     * Returns name of variant stream.
     * @return name
     */
    public String getVariantStreamName()
    {
        return variantStreamName;
    }

    /**
     * Returns types allowed for variant streams.
     * @return types
     */
    public EventType[] getEventTypes()
    {
        return eventTypes;
    }

    /**
     * Returns the type variance enum.
     * @return type variance
     */
    public ConfigurationVariantStream.TypeVariance getTypeVariance()
    {
        return typeVariance;
    }
}
