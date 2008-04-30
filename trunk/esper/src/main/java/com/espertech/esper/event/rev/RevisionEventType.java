package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;

import java.util.Iterator;
import java.util.Map;

public class RevisionEventType implements EventType
{
    private EventType fullEventType;
    private Map<String, RevisionPropertyTypeDesc> propertyDesc;

    public RevisionEventType(EventType fullEventType, Map<String, RevisionPropertyTypeDesc> propertyDesc)
    {
        this.fullEventType = fullEventType;
        this.propertyDesc = propertyDesc;
    }

    public EventPropertyGetter getGetter(String property)
    {
        final RevisionPropertyTypeDesc desc = propertyDesc.get(property);
        if (desc == null)
        {
            return null;
        }
        return desc.getRevisionGetter();
    }

    public Class getPropertyType(String property)
    {
        return fullEventType.getPropertyType(property);
    }

    public Class getUnderlyingType()
    {
        return RevisionEventType.class;
    }

    public String[] getPropertyNames()
    {
        return fullEventType.getPropertyNames();
    }

    public boolean isProperty(String property)
    {
        return fullEventType.isProperty(property);
    }

    public EventType[] getSuperTypes()
    {
        return fullEventType.getSuperTypes();
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return fullEventType.getDeepSuperTypes();
    }
}
