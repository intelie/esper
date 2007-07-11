///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.events
{
	[TestFixture]
	public class TestEventBeanUtility
	{
	    [Test]
	    public void testFlatten()
	    {
	        // test many arrays
	        EventBean[] testEvents = MakeEventArray(new String[] {"a1", "a2", "b1", "b2", "b3", "c1", "c2"});
	        List<EventBean[]> eventVector = new List<EventBean[]>();
	        eventVector.Add(new EventBean[] {testEvents[0], testEvents[1]});
	        eventVector.Add(new EventBean[] {testEvents[2]});
	        eventVector.Add(new EventBean[] {testEvents[3], testEvents[4], testEvents[5]});
	        eventVector.Add(new EventBean[] {testEvents[6]});

	        EventBean[] events = EventBeanUtility.Flatten(eventVector);
	        Assert.AreEqual(7, events.Length);
	        for (int i = 0; i < testEvents.Length; i++)
	        {
	            Assert.AreEqual(events[i], testEvents[i]);
	        }

	        // test just one array
	        eventVector.Clear();
	        eventVector.Add(new EventBean[] {testEvents[2]});
	        events = EventBeanUtility.Flatten(eventVector);
	        Assert.AreEqual(events[0], testEvents[2]);

	        // test empty vector
	        eventVector.Clear();
	        events = EventBeanUtility.Flatten(eventVector);
	        Assert.IsNull(events);
	    }

	    [Test]
	    public void testAppend()
	    {
	        EventBean[] setOne = MakeEventArray(new String[] {"a1", "a2"});
	        EventBean[] setTwo = MakeEventArray(new String[] {"b1", "b2", "b3"});
	        EventBean[] total = EventBeanUtility.Append(setOne, setTwo);

	        Assert.AreEqual(setOne[0], total[0]);
	        Assert.AreEqual(setOne[1], total[1]);
	        Assert.AreEqual(setTwo[0], total[2]);
	        Assert.AreEqual(setTwo[1], total[3]);
	        Assert.AreEqual(setTwo[2], total[4]);

	        setOne = MakeEventArray(new String[] {"a1"});
	        setTwo = MakeEventArray(new String[] {"b1"});
	        total = EventBeanUtility.Append(setOne, setTwo);

	        Assert.AreEqual(setOne[0], total[0]);
	        Assert.AreEqual(setTwo[0], total[1]);
	    }

	    [Test]
	    public void testToArray()
	    {
	        // Test list with 2 elements
	        List<EventBean> eventList = MakeEventList(new String[] {"a1", "a2"});
	        EventBean[] array = EventBeanUtility.ToArray(eventList);
	        Assert.AreEqual(2, array.Length);
	        Assert.AreEqual(eventList[0], array[0]);
	        Assert.AreEqual(eventList[1], array[1]);

	        // Test list with 1 element
	        eventList = MakeEventList(new String[] {"a1"});
	        array = EventBeanUtility.ToArray(eventList);
	        Assert.AreEqual(1, array.Length);
	        Assert.AreEqual(eventList[0], array[0]);

	        // Test empty list
	        eventList = MakeEventList(new String[0]);
	        array = EventBeanUtility.ToArray(eventList);
	        Assert.IsNull(array);

	        // Test null
	        array = EventBeanUtility.ToArray(null);
	        Assert.IsNull(array);
	    }

	    [Test]
	    public void testGetPropertyArray()
	    {
	        // try 2 properties
	        EventPropertyGetter[] getters = MakeGetters();
	        EventBean _event = SupportEventBeanFactory.CreateObject(new SupportBean("a", 10));
	        Object[] properties = EventBeanUtility.GetPropertyArray(_event, getters);
	        Assert.AreEqual(2, properties.Length);
	        Assert.AreEqual("a", properties[0]);
	        Assert.AreEqual(10, properties[1]);

	        // try no properties
	        properties = EventBeanUtility.GetPropertyArray(_event, new EventPropertyGetter[0]);
	        Assert.AreEqual(0, properties.Length);
	    }

	    [Test]
	    public void testMultiKey()
	    {
	        // try 2 properties
	        EventPropertyGetter[] getters = MakeGetters();
	        EventBean _event = SupportEventBeanFactory.CreateObject(new SupportBean("a", 10));
	        MultiKeyUntyped multikey = EventBeanUtility.GetMultiKey(_event, getters);
	        Assert.AreEqual(2, multikey.Keys.Length);
	        Assert.AreEqual("a", multikey.Keys[0]);
	        Assert.AreEqual(10, multikey.Keys[1]);

	        // try no properties
	        multikey = EventBeanUtility.GetMultiKey(_event, new EventPropertyGetter[0]);
	        Assert.AreEqual(0, multikey.Keys.Length);
	    }

	    private static EventPropertyGetter[] MakeGetters()
	    {
	        EventType eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        EventPropertyGetter[] getters = new EventPropertyGetter[2];
	        getters[0] = eventType.GetGetter("string");
	        getters[1] = eventType.GetGetter("intPrimitive");
	        return getters;
	    }

	    private EventType MakeEventType()
	    {
            EDictionary<String, Type> eventTypeMap = new HashDictionary<String, Type>();
	        eventTypeMap.Put("a", typeof(SupportBean));
	        eventTypeMap.Put("b", typeof(SupportBean));
	        EventType eventType = SupportEventTypeFactory.CreateMapType(eventTypeMap);
	        return eventType;
	    }

	    private static EventBean[] MakeEventArray(String[] texts)
	    {
	        EventBean[] events = new EventBean[texts.Length];
	        for (int i = 0; i < texts.Length; i++)
	        {
	            events[i] = SupportEventBeanFactory.CreateObject(texts[i]);
	        }
	        return events;
	    }

	    private static List<EventBean> MakeEventList(String[] texts)
	    {
	        List<EventBean> events = new List<EventBean>();
	        for (int i = 0; i < texts.Length; i++)
	        {
	            events.Add(SupportEventBeanFactory.CreateObject(texts[i]));
	        }
	        return events;
	    }
	}
} // End of namespace
