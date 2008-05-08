package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VariantEventType implements EventType
{
    private final EventType[] variants;
    private final VariantPropertyResolutionStrategy propertyResStrategy;
    private final Map<String, VariantPropertyDesc> propertyCache;

    public VariantEventType(EventType[] variants, VariantPropertyResolutionStrategy propertyResStrategy)
    {
        this.variants = variants;
        this.propertyResStrategy = propertyResStrategy;
        propertyCache = new HashMap<String, VariantPropertyDesc>();
    }

    public Class getPropertyType(String property)
    {
        VariantPropertyDesc entry = propertyCache.get(property);
        if (entry != null)
        {
            return entry.getClass();
        }
        entry = findProperty(property);
        if (entry != null)
        {
            return entry.getClass();
        }
        return null;
    }

    public Class getUnderlyingType()
    {
        return Object.class;
    }

    public EventPropertyGetter getGetter(String property)
    {
        VariantPropertyDesc entry = propertyCache.get(property);
        if (entry != null)
        {
            return entry.getGetter();
        }
        entry = findProperty(property);
        if (entry != null)
        {
            return entry.getGetter();
        }
        return null;
    }

    public String[] getPropertyNames()
    {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isProperty(String property)
    {
        VariantPropertyDesc entry = propertyCache.get(property);
        if (entry != null)
        {
            return entry.isProperty();
        }
        entry = findProperty(property);
        if (entry != null)
        {
            return entry.isProperty();
        }
        return false;
    }

    public EventType[] getSuperTypes()
    {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return null;
    }

    private VariantPropertyDesc findProperty(String propertyName)
    {
        return propertyResStrategy.resolveProperty(propertyName, variants);
    }
}
