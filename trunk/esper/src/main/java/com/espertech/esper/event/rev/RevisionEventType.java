package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RevisionEventType implements EventType
{
    private String[] propertyNames;
    private Map<String, RevisionPropertyTypeDesc> propertyDesc;

    public RevisionEventType(Map<String, RevisionPropertyTypeDesc> propertyDesc)
    {
        this.propertyDesc = propertyDesc;
        Set<String> keys = propertyDesc.keySet();
        propertyNames = keys.toArray(new String[keys.size()]);
    }

    public EventPropertyGetter getGetter(String property)
    {
        RevisionPropertyTypeDesc desc = propertyDesc.get(property);
        if (desc == null)
        {
            return null;
        }
        return desc.getRevisionGetter();
    }

    public Class getPropertyType(String property)
    {
        RevisionPropertyTypeDesc desc = propertyDesc.get(property);
        if (desc == null)
        {
            return null;
        }
        return desc.getPropertyType();
    }

    public Class getUnderlyingType()
    {
        return RevisionEventType.class;
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
    }

    public boolean isProperty(String property)
    {
        return propertyDesc.containsKey(property);
    }

    public EventType[] getSuperTypes()
    {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return null;
    }
}
