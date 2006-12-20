package net.esper.event;

import java.util.*;

import junit.framework.TestCase;
import net.esper.collection.Pair;
import net.esper.collection.MultiKeyUntyped;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventTypeFactory;

public class TestEventBeanUtility extends TestCase
{
    public void testFlatten()
    {
        // test many arrays
        EventBean[] testEvents = makeEventArray(new String[] {"a1", "a2", "b1", "b2", "b3", "c1", "c2"});
        Vector<EventBean[]> eventVector = new Vector<EventBean[]>();
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
