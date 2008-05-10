package com.espertech.esper.event;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class VariantPropertyGetterCache
{
    private final Map<EventType, VariantPropertyGetterRow> allGetters;

    private volatile EventType[] knownTypes;
    private volatile VariantPropertyGetterRow lastUsedGetters;
    private List<String> properties;

    public VariantPropertyGetterCache(EventType[] knownTypes)
    {
        this.knownTypes = knownTypes;
        allGetters = new HashMap<EventType, VariantPropertyGetterRow>();
        properties = new ArrayList<String>();
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
        properties.add(propertyName);
    }

    public EventPropertyGetter getGetter(int assignedPropertyNumber, EventType eventType)
    {
        VariantPropertyGetterRow lastGetters = lastUsedGetters;
        if ((lastGetters != null) && (lastGetters.eventType == eventType))
        {
            return lastGetters.getGetterPerProp()[assignedPropertyNumber];
        }

        VariantPropertyGetterRow row = allGetters.get(eventType);

        // newly seen type (Using ANY type variance or as a subtype of an existing variance type)
        if (row == null)
        {
            row = addType(eventType);
        }

        EventPropertyGetter getter = row.getGetterPerProp()[assignedPropertyNumber];
        lastUsedGetters = row;
        return getter;
    }

    // add to event types
    // add to allGetters, building a getter row for each
    private synchronized VariantPropertyGetterRow addType(EventType eventType)
    {
        EventType[] newKnownTypes = (EventType[]) resizeArray(knownTypes, knownTypes.length + 1);
        newKnownTypes[newKnownTypes.length - 1] = eventType;

        // create getters
        EventPropertyGetter[] getters = new EventPropertyGetter[properties.size()];
        for (int i = 0; i < properties.size(); i++)
        {
            getters[i] = eventType.getGetter(properties.get(i));
        }

        VariantPropertyGetterRow row = new VariantPropertyGetterRow(eventType, getters);
        allGetters.put(eventType, row);
        knownTypes = newKnownTypes;
        return row;
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
