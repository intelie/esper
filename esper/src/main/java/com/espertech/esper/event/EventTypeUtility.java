package com.espertech.esper.event;

import com.espertech.esper.client.EventType;

import java.util.Iterator;

public class EventTypeUtility {

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
