package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.EventType;

/**
 * Strategy for resolving a property against any of the variant types.
 */
public interface VariantPropResolutionStrategy
{
    /**
     * Resolve the property for each of the types.
     * @param propertyName to resolve
     * @param variants the variants to resolve the property for
     * @return property descriptor
     */
    public VariantPropertyDesc resolveProperty(String propertyName, EventType[] variants);
}
