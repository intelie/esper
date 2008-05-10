package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;

// TODO:
// - properties that don't exist on all types are allowed?
// - properties that don't have the same type on all types are allowed?
public interface VariantPropResolutionStrategy
{
    public VariantPropertyDesc resolveProperty(String propertyName, EventType[] variants);
}
