package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;
import com.espertech.esper.client.ConfigurationVariantStream;

public class VariantSpec
{
    private final String variantEventTypeAlias;
    private final EventType[] eventTypes;
    private final ConfigurationVariantStream.TypeVariance typeVariance;
    private final ConfigurationVariantStream.PropertyVariance propertyVariance;

    public VariantSpec(String variantEventTypeAlias, EventType[] eventTypes, ConfigurationVariantStream.TypeVariance typeVariance, ConfigurationVariantStream.PropertyVariance propertyVariance)
    {
        this.variantEventTypeAlias = variantEventTypeAlias;
        this.eventTypes = eventTypes;
        this.typeVariance = typeVariance;
        this.propertyVariance = propertyVariance;
    }

    public String getVariantEventTypeAlias()
    {
        return variantEventTypeAlias;
    }

    public EventType[] getEventTypes()
    {
        return eventTypes;
    }

    public ConfigurationVariantStream.TypeVariance getTypeVariance()
    {
        return typeVariance;
    }

    public ConfigurationVariantStream.PropertyVariance getPropertyVariance()
    {
        return propertyVariance;
    }
}
