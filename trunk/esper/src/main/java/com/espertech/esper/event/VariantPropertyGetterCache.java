package com.espertech.esper.event;

import java.util.HashMap;
import java.util.Map;

public class VariantPropertyGetterCache
{
    private final EventType[] knownTypes;
    private final Map<EventType, VariantPropertyGetterRow> allGetters;

    private volatile VariantPropertyGetterRow lastUsedGetters;

    public VariantPropertyGetterCache(EventType[] knownTypes)
    {
        this.knownTypes = knownTypes;
        allGetters = new HashMap<EventType, VariantPropertyGetterRow>();
    }

    public void addGetters(int assignedPropertyNumber, String propertyName)
    {
        for (EventType type : knownTypes)
        {
            EventPropertyGetter getter = type.getGetter(propertyName);

            VariantPropertyGetterRow row = allGetters.get(type);
            if (row == null)
            {
                row = new VariantPropertyGetterRow(type, new EventPropertyGetter[assignedPropertyNumber + 1]);
                allGetters.put(type, row);
            }
            row.addGetter(assignedPropertyNumber, getter);
        }
    }

    public EventPropertyGetter getGetter(int assignedPropertyNumber, EventType eventType)
    {
        VariantPropertyGetterRow lastGetters = lastUsedGetters;
        if ((lastGetters != null) && (lastGetters.eventType == eventType))
        {
            return lastGetters.getGetterPerProp()[assignedPropertyNumber];
        }

        VariantPropertyGetterRow row = allGetters.get(eventType);
        EventPropertyGetter getter = row.getGetterPerProp()[assignedPropertyNumber];
        lastUsedGetters = row;
        return getter;
    }

    private static Object resizeArray(Object oldArray, int newSize)
    {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(
                elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }

    private class VariantPropertyGetterRow
    {
        private EventType eventType;
        private EventPropertyGetter[] getterPerProp;

        private VariantPropertyGetterRow(EventType eventType, EventPropertyGetter[] getterPerProp)
        {
            this.eventType = eventType;
            this.getterPerProp = getterPerProp;
        }

        public EventType getEventType()
        {
            return eventType;
        }

        public EventPropertyGetter[] getGetterPerProp()
        {
            return getterPerProp;
        }

        public void setGetterPerProp(EventPropertyGetter[] getterPerProp)
        {
            this.getterPerProp = getterPerProp;
        }

        public void addGetter(int assignedPropertyNumber, EventPropertyGetter getter)
        {
            if (assignedPropertyNumber > (getterPerProp.length - 1))
            {
                getterPerProp = (EventPropertyGetter[]) resizeArray(getterPerProp, getterPerProp.length + 10);
            }
            getterPerProp[assignedPropertyNumber] = getter;
        }
    }
}
