package com.espertech.esper.event;

import java.util.*;

import junit.framework.TestCase;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestEventBeanUtility extends TestCase
{
    public void testFlattenList()
    {
        // test many arrays
        EventBean[] testEvents = makeEventArray(new String[] {"a1", "a2", "b1", "b2", "b3", "c1", "c2"});
        ArrayDequeJDK6Backport<Pair<EventBean[], EventBean[]>> eventVector = new ArrayDequeJDK6Backport<Pair<EventBean[], EventBean[]>>();

        eventVector.add(new Pair<EventBean[], EventBean[]>(null, new EventBean[] {testEvents[0], testEvents[1]}));
        eventVector.add(new Pair<EventBean[], EventBean[]>(new EventBean[] {testEvents[2]}, null));
        eventVector.add(new Pair<EventBean[], EventBean[]>(null, new EventBean[] {testEvents[3], testEvents[4], testEvents[5]}));
        eventVector.add(new Pair<EventBean[], EventBean[]>(new EventBean[] {testEvents[6]}, null));

        Pair<EventBean[], EventBean[]> events = EventBeanUtility.flattenList(eventVector);
        ArrayAssertionUtil.assertEqualsExactOrder(events.getFirst(), new EventBean[] {testEvents[2], testEvents[6]});
        ArrayAssertionUtil.assertEqualsExactOrder(events.getSecond(), new EventBean[] {testEvents[0], testEvents[1], testEvents[3], testEvents[4], testEvents[5]});

        // test just one array
        eventVector.clear();
        eventVector.add(new Pair<EventBean[], EventBean[]>(new EventBean[] {testEvents[2]}, null));
        events = EventBeanUtility.flattenList(eventVector);
        ArrayAssertionUtil.assertEqualsExactOrder(events.getFirst(), new EventBean[] {testEvents[2]});
        ArrayAssertionUtil.assertEqualsExactOrder(events.getSecond(), null);

        // test empty vector
        eventVector.clear();
        events = EventBeanUtility.flattenList(eventVector);
        assertNull(events);
    }

    public void testFlatten()
    {
        // test many arrays
        EventBean[] testEvents = makeEventArray(new String[] {"a1", "a2", "b1", "b2", "b3", "c1", "c2"});
        ArrayDequeJDK6Backport<EventBean[]> eventVector = new ArrayDequeJDK6Backport<EventBean[]>();
        eventVector.add(new EventBean[] {testEvents[0], testEvents[1]});
        eventVector.add(new EventBean[] {testEvents[2]});
        eventVector.add(new EventBean[] {testEvents[3], testEvents[4], testEvents[5]});
        eventVector.add(new EventBean[] {testEvents[6]});

        EventBean[] events = EventBeanUtility.flatten(eventVector);
        assertEquals(7, events.length);
        for (int i = 0; i < testEvents.length; i++)
        {
            assertEquals(events[i], testEvents[i]);
        }

        // test just one array
        eventVector.clear();
        eventVector.add(new EventBean[] {testEvents[2]});
        events = EventBeanUtility.flatten(eventVector);
        assertEquals(events[0], testEvents[2]);

        // test empty vector
        eventVector.clear();
        events = EventBeanUtility.flatten(eventVector);
        assertNull(events);
    }

    public void testAppend()
    {
        EventBean[] setOne = makeEventArray(new String[] {"a1", "a2"});
        EventBean[] setTwo = makeEventArray(new String[] {"b1", "b2", "b3"});
        EventBean[] total = EventBeanUtility.append(setOne, setTwo);

        assertEquals(setOne[0], total[0]);
        assertEquals(setOne[1], total[1]);
        assertEquals(setTwo[0], total[2]);
        assertEquals(setTwo[1], total[3]);
        assertEquals(setTwo[2], total[4]);

        setOne = makeEventArray(new String[] {"a1"});
        setTwo = makeEventArray(new String[] {"b1"});
        total = EventBeanUtility.append(setOne, setTwo);

        assertEquals(setOne[0], total[0]);
        assertEquals(setTwo[0], total[1]);
    }

    public void testToArray()
    {
        // Test list with 2 elements
        List<EventBean> eventList = makeEventList(new String[] {"a1", "a2"});
        EventBean[] array = EventBeanUtility.toArray(eventList);
        assertEquals(2, array.length);
        assertEquals(eventList.get(0), array[0]);
        assertEquals(eventList.get(1), array[1]);

        // Test list with 1 element
        eventList = makeEventList(new String[] {"a1"});
        array = EventBeanUtility.toArray(eventList);
        assertEquals(1, array.length);
        assertEquals(eventList.get(0), array[0]);

        // Test empty list
        eventList = makeEventList(new String[0]);
        array = EventBeanUtility.toArray(eventList);
        assertNull(array);

        // Test null
        array = EventBeanUtility.toArray(null);
        assertNull(array);
    }

    public void testGetPropertyArray()
    {
        // try 2 properties
        EventPropertyGetter[] getters = makeGetters();
        EventBean event = SupportEventBeanFactory.createObject(new SupportBean("a", 10));
        Object[] properties = EventBeanUtility.getPropertyArray(event, getters);
        assertEquals(2, properties.length);
        assertEquals("a", properties[0]);
        assertEquals(10, properties[1]);

        // try no properties
        properties = EventBeanUtility.getPropertyArray(event, new EventPropertyGetter[0]);
        assertEquals(0, properties.length);
    }

    public void testMultiKey()
    {
        // try 2 properties
        EventPropertyGetter[] getters = makeGetters();
        EventBean event = SupportEventBeanFactory.createObject(new SupportBean("a", 10));
        MultiKeyUntyped multikey = EventBeanUtility.getMultiKey(event, getters);
        assertEquals(2, multikey.getKeys().length);
        assertEquals("a", multikey.getKeys()[0]);
        assertEquals(10, multikey.getKeys()[1]);

        // try no properties
        multikey = EventBeanUtility.getMultiKey(event, new EventPropertyGetter[0]);
        assertEquals(0, multikey.getKeys().length);
    }

    private EventPropertyGetter[] makeGetters()
    {
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        EventPropertyGetter[] getters = new EventPropertyGetter[2];
        getters[0] = eventType.getGetter("string");
        getters[1] = eventType.getGetter("intPrimitive");
        return getters;
    }

    private EventType makeEventType()
    {
        Map<String, Class> eventTypeMap = new HashMap<String, Class>();
        eventTypeMap.put("a", SupportBean.class);
        eventTypeMap.put("b", SupportBean.class);
        EventType eventType = SupportEventTypeFactory.createMapType(eventTypeMap);
        return eventType;
    }

    private EventBean[] makeEventArray(String[] texts)
    {
        EventBean[] events = new EventBean[texts.length];
        for (int i = 0; i < texts.length; i++)
        {
            events[i] = SupportEventBeanFactory.createObject(texts[i]);
        }
        return events;
    }

    private List<EventBean> makeEventList(String[] texts)
    {
        List<EventBean> events = new LinkedList<EventBean>();
        for (int i = 0; i < texts.length; i++)
        {
            events.add(SupportEventBeanFactory.createObject(texts[i]));
        }
        return events;
    }
}
