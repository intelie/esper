package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VariantEventType implements EventType
{
    private final EventType[] variants;
    private final VariantPropResolutionStrategy propertyResStrategy;
    private final Map<String, VariantPropertyDesc> propertyDesc;
    private final String[] propertyNames;

    public VariantEventType(VariantSpec variantSpec, VariantPropResolutionStrategy propertyResStrategy)
    {
        this.variants = variantSpec.getEventTypes();
        this.propertyResStrategy = propertyResStrategy;
        propertyDesc = new HashMap<String, VariantPropertyDesc>();

        // for each of the properties in each type, attempt to load the property to build a property list
        for (EventType type : variants)
        {
            String[] properties = type.getPropertyNames();
            properties = PropertyUtility.copyAndSort(properties);
            PropertyUtility.removePropNamePostfixes(properties);
            for (String property : properties)
            {
                if (!propertyDesc.containsKey(property))
                {
                    findProperty(property);
                }
            }
        }
        Set<String> keySet = propertyDesc.keySet();
        propertyNames = keySet.toArray(new String[keySet.size()]);
    }

    public Class getPropertyType(String property)
    {
        VariantPropertyDesc entry = propertyDesc.get(property);
        if (entry != null)
        {
            return entry.getPropertyType();
        }
        entry = findProperty(property);
        if (entry != null)
        {
            return entry.getPropertyType();
        }
        return null;
    }

    public Class getUnderlyingType()
    {
        return Object.class;
    }

    public EventPropertyGetter getGetter(String property)
    {
        VariantPropertyDesc entry = propertyDesc.get(property);
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
        return propertyNames;
    }

    public boolean isProperty(String property)
    {
        VariantPropertyDesc entry = propertyDesc.get(property);
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
        VariantPropertyDesc desc = propertyResStrategy.resolveProperty(propertyName, variants);
        if (desc != null)
        {
            propertyDesc.put(propertyName, desc);
        }
        return desc;
    }
}
