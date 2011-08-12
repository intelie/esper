/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.EventType;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Iterator;

public class EventTypeUtility {

    public static void validateTimestampProperties(EventType eventType, String startTimestampProperty, String endTimestampProperty)
            throws ConfigurationException {

        if (startTimestampProperty != null) {
            if (eventType.getGetter(startTimestampProperty) == null) {
                throw new ConfigurationException("Declared start timestamp property name '" + startTimestampProperty + "' was not found");
            }
            Class type = eventType.getPropertyType(startTimestampProperty);
            if (!JavaClassHelper.isDatetimeClass(type)) {
                throw new ConfigurationException("Declared start timestamp property '" + startTimestampProperty + "' is expected to return a Date, Calendar or long-typed value but returns '" + type.getName() + "'");
            }
        }

        if (endTimestampProperty != null) {
            if (startTimestampProperty == null) {
                throw new ConfigurationException("Declared end timestamp property requires that a start timestamp property is also declared");
            }
            if (eventType.getGetter(endTimestampProperty) == null) {
                throw new ConfigurationException("Declared end timestamp property name '" + endTimestampProperty + "' was not found");
            }
            Class type = eventType.getPropertyType(endTimestampProperty);
            if (!JavaClassHelper.isDatetimeClass(type)) {
                throw new ConfigurationException("Declared end timestamp property '" + endTimestampProperty + "' is expected to return a Date, Calendar or long-typed value but returns '" + type.getName() + "'");
            }
            Class startType = eventType.getPropertyType(startTimestampProperty);
            if (JavaClassHelper.getBoxedType(startType) != JavaClassHelper.getBoxedType(type)) {
                throw new ConfigurationException("Declared end timestamp property '" + endTimestampProperty + "' is expected to have the same property type as the start-timestamp property '" + startTimestampProperty + "'");
            }
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
