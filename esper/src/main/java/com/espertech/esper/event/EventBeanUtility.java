/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.collection.MultiKeyUntypedEventPair;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.util.JavaClassHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Method to getSelectListEvents events in collections to other collections or other event types.
 */
public class EventBeanUtility
{
    public static EventPropertyGetter getAssertPropertyGetter(EventType type, String propertyName) {
        EventPropertyGetter getter = type.getGetter(propertyName);
        if (getter == null) {
            throw new IllegalStateException("Property " + propertyName + " not found in type " + type.getName());
        }
        return getter;
    }

    public static EventPropertyGetter getAssertPropertyGetter(EventType[] eventTypes, int keyStreamNum, String property) {
        return getAssertPropertyGetter(eventTypes[keyStreamNum], property);
    }

    /**
     * Resizes an array of events to a new size.
     * <p>
     * Returns the same array reference if the size is the same.
     * @param oldArray array to resize
     * @param newSize new array size
     * @return resized array
     */
    public static EventBean[] resizeArray(EventBean[] oldArray, int newSize)
    {
        if (oldArray == null)
        {
            return null;
        }
        if (oldArray.length == newSize)
        {
            return oldArray;
        }
        EventBean[] newArray = new EventBean[newSize];
        int preserveLength = Math.min(oldArray.length, newSize);
        if (preserveLength > 0)
        {
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }
        return newArray;
    }

    /**
     * Flatten the vector of arrays to an array. Return null if an empty vector was passed, else
     * return an array containing all the events.
     * @param eventVector vector
     * @return array with all events
     */
    public static UniformPair<EventBean[]> flattenList(ArrayDeque<UniformPair<EventBean[]>> eventVector)
    {
        if (eventVector.isEmpty())
        {
            return null;
        }

        if (eventVector.size() == 1)
        {
            return eventVector.getFirst();
        }

        int totalNew = 0;
        int totalOld = 0;
        for (UniformPair<EventBean[]> pair : eventVector)
        {
            if (pair != null)
            {
                if (pair.getFirst() != null)
                {
                    totalNew += pair.getFirst().length;
                }
                if (pair.getSecond() != null)
                {
                    totalOld += pair.getSecond().length;
                }
            }
        }

        if ((totalNew + totalOld) == 0)
        {
            return null;
        }

        EventBean[] resultNew = null;
        if (totalNew > 0)
        {
            resultNew = new EventBean[totalNew];
        }

        EventBean[] resultOld = null;
        if (totalOld > 0)
        {
            resultOld = new EventBean[totalOld];
        }

        int destPosNew = 0;
        int destPosOld = 0;
        for (UniformPair<EventBean[]> pair : eventVector)
        {
            if (pair != null)
            {
                if (pair.getFirst() != null)
                {
                    System.arraycopy(pair.getFirst(), 0, resultNew, destPosNew, pair.getFirst().length);
                    destPosNew += pair.getFirst().length;
                }
                if (pair.getSecond() != null)
                {
                    System.arraycopy(pair.getSecond(), 0, resultOld, destPosOld, pair.getSecond().length);
                    destPosOld += pair.getSecond().length;
                }
            }
        }

        return new UniformPair<EventBean[]>(resultNew, resultOld);
    }

    /**
     * Flatten the vector of arrays to an array. Return null if an empty vector was passed, else
     * return an array containing all the events.
     * @param eventVector vector
     * @return array with all events
     */
    public static EventBean[] flatten(ArrayDeque<EventBean[]> eventVector)
    {
        if (eventVector.isEmpty())
        {
            return null;
        }

        if (eventVector.size() == 1)
        {
            return eventVector.getFirst();
        }

        int totalElements = 0;
        for (EventBean[] arr : eventVector)
        {
            if (arr != null)
            {
                totalElements += arr.length;
            }
        }

        if (totalElements == 0)
        {
            return null;
        }

        EventBean[] result = new EventBean[totalElements];
        int destPos = 0;
        for (EventBean[] arr : eventVector)
        {
            if (arr != null)
            {
                System.arraycopy(arr, 0, result, destPos, arr.length);
                destPos += arr.length;
            }
        }

        return result;
    }

    /**
     * Flatten the vector of arrays to an array. Return null if an empty vector was passed, else
     * return an array containing all the events.
     * @param updateVector is a list of updates of old and new events
     * @return array with all events
     */
    public static UniformPair<EventBean[]> flattenBatchStream(List<UniformPair<EventBean[]>> updateVector)
    {
        if (updateVector.isEmpty())
        {
            return new UniformPair<EventBean[]>(null, null);
        }

        if (updateVector.size() == 1)
        {
            return new UniformPair<EventBean[]>(updateVector.get(0).getFirst(), updateVector.get(0).getSecond());
        }

        int totalNewEvents = 0;
        int totalOldEvents = 0;
        for (UniformPair<EventBean[]> pair : updateVector)
        {
            if (pair.getFirst() != null)
            {
                totalNewEvents += pair.getFirst().length;
            }
            if (pair.getSecond() != null)
            {
                totalOldEvents += pair.getSecond().length;
            }
        }

        if ((totalNewEvents == 0) && (totalOldEvents == 0))
        {
            return new UniformPair<EventBean[]>(null, null);
        }

        EventBean[] newEvents = null;
        EventBean[] oldEvents = null;
        if (totalNewEvents != 0)
        {
            newEvents = new EventBean[totalNewEvents];
        }
        if (totalOldEvents != 0)
        {
            oldEvents = new EventBean[totalOldEvents];
        }

        int destPosNew = 0;
        int destPosOld = 0;
        for (UniformPair<EventBean[]> pair : updateVector)
        {
            EventBean[] newData = pair.getFirst();
            EventBean[] oldData = pair.getSecond();

            if (newData != null)
            {
                int newDataLen = newData.length;
                System.arraycopy(newData, 0, newEvents, destPosNew, newDataLen);
                destPosNew += newDataLen;
            }
            if (oldData != null)
            {
                int oldDataLen = oldData.length;
                System.arraycopy(oldData, 0, oldEvents, destPosOld, oldDataLen);
                destPosOld += oldDataLen;
            }
        }

        return new UniformPair<EventBean[]>(newEvents, oldEvents);
    }

     /**
     * Append arrays.
     * @param source array
     * @param append array
     * @return appended array
     */
    protected static EventBean[] append(EventBean[] source, EventBean[] append)
    {
        EventBean[] result = new EventBean[source.length + append.length];
        System.arraycopy(source, 0, result, 0, source.length);
        System.arraycopy(append, 0, result, source.length, append.length);
        return result;
    }

    /**
     * Convert list of events to array, returning null for empty or null lists.
     * @param eventList is a list of events to convert
     * @return array of events
     */
    public static EventBean[] toArray(List<EventBean> eventList)
    {
        if ((eventList == null) || (eventList.isEmpty()))
        {
            return null;
        }
        return eventList.toArray(new EventBean[eventList.size()]);
    }

    /**
     * Returns object array containing property values of given properties, retrieved via EventPropertyGetter
     * instances.
     * @param event - event to get property values from
     * @param propertyGetters - getters to use for getting property values
     * @return object array with property values
     */
    public static Object[] getPropertyArray(EventBean event, EventPropertyGetter[] propertyGetters)
    {
        Object[] keyValues = new Object[propertyGetters.length];
        for (int i = 0; i < propertyGetters.length; i++)
        {
            keyValues[i] = propertyGetters[i].get(event);
        }
        return keyValues;
    }

    public static Object[] getPropertyArray(EventBean[] eventsPerStream, EventPropertyGetter[] propertyGetters, int[] streamNums)
    {
        Object[] keyValues = new Object[propertyGetters.length];
        for (int i = 0; i < propertyGetters.length; i++)
        {
            keyValues[i] = propertyGetters[i].get(eventsPerStream[streamNums[i]]);
        }
        return keyValues;
    }

    /**
     * Returns Multikey instance for given event and getters.
     * @param event - event to get property values from
     * @param propertyGetters - getters for access to properties
     * @return MultiKey with property values
     */
    public static MultiKeyUntyped getMultiKey(EventBean event, EventPropertyGetter[] propertyGetters)
    {
        Object[] keyValues = getPropertyArray(event, propertyGetters);
        return new MultiKeyUntyped(keyValues);
    }

    public static MultiKeyUntyped getMultiKey(EventBean event, EventPropertyGetter[] propertyGetters, Class[] coercionTypes) {
        Object[] keyValues = getPropertyArray(event, propertyGetters);
        if (coercionTypes == null) {
            return new MultiKeyUntyped(keyValues);
        }
        for (int i = 0; i < coercionTypes.length; i++)
        {
            Object key = keyValues[i];
            if ((key != null) && (!key.getClass().equals(coercionTypes[i])))
            {
                if (key instanceof Number)
                {
                    key = JavaClassHelper.coerceBoxed((Number) key, coercionTypes[i]);
                    keyValues[i] = key;
                }
            }
        }
        return new MultiKeyUntyped(keyValues);
    }

    public static MultiKeyUntyped getMultiKey(EventBean[] eventsPerStream, ExprEvaluator[] evaluators, ExprEvaluatorContext context, Class[] coercionTypes) {
        Object[] keyValues = getPropertyArray(eventsPerStream, evaluators, context);
        if (coercionTypes == null) {
            return new MultiKeyUntyped(keyValues);
        }
        for (int i = 0; i < coercionTypes.length; i++)
        {
            Object key = keyValues[i];
            if ((key != null) && (!key.getClass().equals(coercionTypes[i])))
            {
                if (key instanceof Number)
                {
                    key = JavaClassHelper.coerceBoxed((Number) key, coercionTypes[i]);
                    keyValues[i] = key;
                }
            }
        }
        return new MultiKeyUntyped(keyValues);
    }

    private static Object[] getPropertyArray(EventBean[] eventsPerStream, ExprEvaluator[] evaluators, ExprEvaluatorContext context) {
        Object[] keys = new Object[evaluators.length];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = evaluators[i].evaluate(eventsPerStream, true, context);
        }
        return keys;
    }

    public static MultiKeyUntyped getMultiKey(EventBean[] eventPerStream, EventPropertyGetter[] propertyGetters, int[] keyStreamNums, Class[] coercionTypes) {
        Object[] keyValues = getPropertyArray(eventPerStream, propertyGetters, keyStreamNums);
        if (coercionTypes == null) {
            return new MultiKeyUntyped(keyValues);
        }
        coerce(keyValues, coercionTypes);
        return new MultiKeyUntyped(keyValues);
    }

    private static void coerce(Object[] keyValues, Class[] coercionTypes) {
        for (int i = 0; i < coercionTypes.length; i++)
        {
            Object key = keyValues[i];
            if ((key != null) && (!key.getClass().equals(coercionTypes[i])))
            {
                if (key instanceof Number)
                {
                    key = JavaClassHelper.coerceBoxed((Number) key, coercionTypes[i]);
                    keyValues[i] = key;
                }
            }
        }
    }

    public static Object coerce(Object target, Class coercionType) {
        if (coercionType == null) {
            return target;
        }
        if (target != null && !target.getClass().equals(coercionType))
        {
            if (target instanceof Number)
            {
                return JavaClassHelper.coerceBoxed((Number) target, coercionType);
            }
        }
        return target;
    }

    /**
     * Format the event and return a string representation.
     * @param event is the event to format.
     * @return string representation of event
     */
    public static String printEvent(EventBean event)
    {
        StringWriter writer = new StringWriter();
        PrintWriter buf = new PrintWriter(writer);
        printEvent(buf, event);
        return writer.toString();
    }

    private static void printEvent(PrintWriter writer, EventBean event)
    {
        String[] properties = event.getEventType().getPropertyNames();
        for (int i = 0; i < properties.length; i++)
        {
            String propName = properties[i];
            Object property = event.get(propName);
            String printProperty;
            if (property == null)
            {
                printProperty = "null";
            }
            else if (property.getClass().isArray())
            {
                printProperty = "Array :" + Arrays.toString((Object[]) property);
            }
            else
            {
                printProperty = property.toString();
            }
            writer.println( "#" + i + "  " + propName + " = " + printProperty);
        }
    }

    public static void appendEvent(StringWriter writer, EventBean event)
    {
        String[] properties = event.getEventType().getPropertyNames();
        String delimiter = "";
        for (int i = 0; i < properties.length; i++)
        {
            String propName = properties[i];
            Object property = event.get(propName);
            String printProperty;
            if (property == null)
            {
                printProperty = "null";
            }
            else if (property.getClass().isArray())
            {
                printProperty = "Array :" + Arrays.toString((Object[]) property);
            }
            else
            {
                printProperty = property.toString();
            }
            writer.append(delimiter);
            writer.append(propName);
            writer.append("=");
            writer.append(printProperty);
            delimiter = ",";
        }
    }

    /**
     * Flattens a list of pairs of join result sets.
     * @param joinPostings is the list
     * @return is the consolidate sets
     */
    public static UniformPair<Set<MultiKey<EventBean>>> flattenBatchJoin(List<UniformPair<Set<MultiKey<EventBean>>>> joinPostings)
    {
        if (joinPostings.isEmpty())
        {
            return new UniformPair<Set<MultiKey<EventBean>>>(null, null);
        }

        if (joinPostings.size() == 1)
        {
            return new UniformPair<Set<MultiKey<EventBean>>>(joinPostings.get(0).getFirst(), joinPostings.get(0).getSecond());
        }

        Set<MultiKey<EventBean>> newEvents = new LinkedHashSet<MultiKey<EventBean>>();
        Set<MultiKey<EventBean>> oldEvents = new LinkedHashSet<MultiKey<EventBean>>();

        for (UniformPair<Set<MultiKey<EventBean>>> pair : joinPostings)
        {
            Set<MultiKey<EventBean>> newData = pair.getFirst();
            Set<MultiKey<EventBean>> oldData = pair.getSecond();

            if (newData != null)
            {
                newEvents.addAll(newData);
            }
            if (oldData != null)
            {
                oldEvents.addAll(oldData);
            }
        }

        return new UniformPair<Set<MultiKey<EventBean>>>(newEvents, oldEvents);
    }

    /**
     * Expand the array passed in by the single element to add.
     * @param array to expand
     * @param eventToAdd element to add
     * @return resized array
     */
    public static EventBean[] addToArray(EventBean[] array, EventBean eventToAdd)
    {
        EventBean[] newArray = new EventBean[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[newArray.length - 1] = eventToAdd;
        return newArray;
    }

    /**
     * Expand the array passed in by the multiple elements to add.
     * @param array to expand
     * @param eventsToAdd elements to add
     * @return resized array
     */
    public static EventBean[] addToArray(EventBean[] array, List<EventBean> eventsToAdd)
    {
        EventBean[] newArray = new EventBean[array.length + eventsToAdd.size()];
        System.arraycopy(array, 0, newArray, 0, array.length);

        int counter = array.length;
        for (EventBean eventToAdd : eventsToAdd)
        {
            newArray[counter++] = eventToAdd;
        }
        return newArray;
    }

    /**
     * Create a fragment event type.
     * @param propertyType property return type
     * @param genericType property generic type parameter, or null if none
     * @param eventAdapterService for event types
     * @return fragment type
     */
    public static FragmentEventType createNativeFragmentType(Class propertyType, Class genericType, EventAdapterService eventAdapterService)
    {
        boolean isIndexed = false;

        if (propertyType.isArray())
        {
            isIndexed = true;
            propertyType = propertyType.getComponentType();
        }
        else if (JavaClassHelper.isImplementsInterface(propertyType, Iterable.class))
        {
            isIndexed = true;
            if (genericType == null)
            {
                return null;
            }
            propertyType = genericType;
        }

        if (!JavaClassHelper.isFragmentableType(propertyType))
        {
            return null;
        }

        EventType type = eventAdapterService.getBeanEventTypeFactory().createBeanType(propertyType.getName(), propertyType, false, false, false);
        return new FragmentEventType(type, isIndexed, true);
    }

    /**
     * Returns the distinct events by properties.
     * @param events to inspect
     * @param reader for retrieving properties
     * @return distinct events
     */
    public static EventBean[] getDistinctByProp(ArrayDeque<EventBean> events, EventBeanReader reader)
    {
        if (events == null || events.isEmpty())
        {
            return new EventBean[0];
        }
        if (events.size() < 2)
        {
            return events.toArray(new EventBean[events.size()]);
        }

        Set<MultiKeyUntypedEventPair> set = new LinkedHashSet<MultiKeyUntypedEventPair>();
        if (events.getFirst() instanceof NaturalEventBean) {
            for (EventBean event : events)
            {
                EventBean inner = ((NaturalEventBean) event).getOptionalSynthetic();
                Object[] keys = reader.read(inner);
                MultiKeyUntypedEventPair pair = new MultiKeyUntypedEventPair(keys, event);
                set.add(pair);
            }
        }
        else {
            for (EventBean event : events)
            {
                Object[] keys = reader.read(event);
                MultiKeyUntypedEventPair pair = new MultiKeyUntypedEventPair(keys, event);
                set.add(pair);
            }
        }

        EventBean[] result = new EventBean[set.size()];
        int count = 0;
        for (MultiKeyUntypedEventPair row : set)
        {
            result[count++] = row.getEventBean();
        }
        return result;
    }

    /**
     * Returns the distinct events by properties.
     * @param events to inspect
     * @param reader for retrieving properties
     * @return distinct events
     */
    public static EventBean[] getDistinctByProp(EventBean[] events, EventBeanReader reader)
    {
        if ((events == null) || (events.length < 2))
        {
            return events;
        }

        Set<MultiKeyUntypedEventPair> set = new LinkedHashSet<MultiKeyUntypedEventPair>();
        if (events[0] instanceof NaturalEventBean) {
            for (EventBean event : events)
            {
                EventBean inner = ((NaturalEventBean) event).getOptionalSynthetic();
                Object[] keys = reader.read(inner);
                MultiKeyUntypedEventPair pair = new MultiKeyUntypedEventPair(keys, event);
                set.add(pair);
            }
        }
        else {
            for (EventBean event : events)
            {
                Object[] keys = reader.read(event);
                MultiKeyUntypedEventPair pair = new MultiKeyUntypedEventPair(keys, event);
                set.add(pair);
            }
        }

        EventBean[] result = new EventBean[set.size()];
        int count = 0;
        for (MultiKeyUntypedEventPair row : set)
        {
            result[count++] = row.getEventBean();
        }
        return result;
    }

    public static EventBean[] denaturalize(EventBean[] naturals) {
        if (naturals == null || naturals.length == 0) {
            return null;
        }
        if (!(naturals[0] instanceof NaturalEventBean)) {
            return naturals;
        }
        if (naturals.length == 1) {
            return new EventBean[] {((NaturalEventBean) naturals[0]).getOptionalSynthetic()};
        }
        EventBean[] result = new EventBean[naturals.length];
        for (int i = 0; i < naturals.length; i++) {
            result[i] = ((NaturalEventBean) naturals[i]).getOptionalSynthetic();
        }
        return result;
    }

    public static boolean compareReferences(EventBean[] reference, EventBean[] eventsPerStream) {
        if (reference.length != eventsPerStream.length) {
            return false;
        }
        for (int i = 0; i < reference.length; i++) {
            if (reference[i] != eventsPerStream[i]) {
                return false;
            }
        }
        return true;
    }

    public static String summarize(EventBean event) {
        if (event == null) {
            return "(null)";
        }
        return event.getUnderlying().toString();
    }

    public static String summarize(EventBean[] events) {
        if (events == null) {
            return "(null)";
        }
        if (events.length == 0) {
            return "(empty)";
        }
        StringWriter writer = new StringWriter();
        String delimiter = "";
        for (int i = 0; i < events.length; i++) {
            writer.write(delimiter);
            writer.write("event ");
            writer.write(Integer.toString(i));
            writer.write(":");
            if (events[i] == null) {
                writer.write("null");
            }
            else {
                writer.write(events[i].getUnderlying().toString());
            }
            delimiter = ", ";
        }
        return writer.toString();
    }

    public static void safeArrayCopy(EventBean[] eventsPerStream, EventBean[] eventsLambda) {
        if (eventsPerStream.length <= eventsLambda.length) {
            System.arraycopy(eventsPerStream, 0, eventsLambda, 0, eventsPerStream.length);
        }
        else {
            System.arraycopy(eventsPerStream, 0, eventsLambda, 0, eventsLambda.length);
        }
    }
}
