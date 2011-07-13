package com.espertech.esper.event;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.EventType;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Iterator;

public class EventTypeUtility {

    public static void validateTimestampAndDuration(EventType eventType, String timestampProperty, String durationProperty)
            throws ConfigurationException {

        if (timestampProperty != null) {
            if (eventType.getGetter(timestampProperty) == null) {
                throw new ConfigurationException("Declared timestamp property name '" + timestampProperty + "' was not found");
            }
            Class type = eventType.getPropertyType(timestampProperty);
            if (!JavaClassHelper.isDatetimeClass(type)) {
                throw new ConfigurationException("Declared timestamp property '" + timestampProperty + "' is expected to return a Date, Calendar or long-typed value but returns '" + type.getName() + "'");
            }
        }

        if (durationProperty != null) {
            if (eventType.getGetter(durationProperty) == null) {
                throw new ConfigurationException("Declared duration property name '" + durationProperty + "' was not found");
            }
            Class type = eventType.getPropertyType(durationProperty);
            if (!JavaClassHelper.isDatetimeClass(type)) {
                throw new ConfigurationException("Declared duration property '" + durationProperty + "' is expected to return a Date, Calendar or long-typed value but returns '" + type.getName() + "'");
            }
        }

        if (durationProperty != null && timestampProperty == null) {
            throw new ConfigurationException("Declared duration property requires that a timestamp property is also declared for the same type");
        }
    }

    public static boolean isTypeOrSubTypeOf(EventType candidate, EventType superType) {

        if (candidate == superType) {
            return true;
        }

        if (candidate.getSuperTypes() != null) {
            for (Iterator<EventType> it = candidate.getDeepSuperTypes(); it.hasNext();) {
                if (it.next() == superType) {
                    return true;
                }
            }
        }
        return false;
    }
}
