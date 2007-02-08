using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
	[TestFixture]
    public class TestEventBeanUtility 
    {
        [Test]
        public virtual void testFlatten()
        {
            // test many arrays
            EventBean[] testEvents = makeEventArray(new String[] { "a1", "a2", "b1", "b2", "b3", "c1", "c2" });
            List<EventBean[]> eventVector = new List<EventBean[]>();
            eventVector.Add(new EventBean[] { testEvents[0], testEvents[1] });
            eventVector.Add(new EventBean[] { testEvents[2] });
            eventVector.Add(new EventBean[] { testEvents[3], testEvents[4], testEvents[5] });
            eventVector.Add(new EventBean[] { testEvents[6] });

            EventBean[] events = EventBeanUtility.Flatten(eventVector);
            Assert.AreEqual(7, events.Length);
            for (int i = 0; i < testEvents.Length; i++)
            {
                Assert.AreEqual(events[i], testEvents[i]);
            }

            // test just one array
            eventVector.Clear();
            eventVector.Add(new EventBean[] { testEvents[2] });
            events = EventBeanUtility.Flatten(eventVector);
            Assert.AreEqual(events[0], testEvents[2]);

            // test empty vector
            eventVector.Clear();
            events = EventBeanUtility.Flatten(eventVector);
            Assert.IsNull(events);
        }

        [Test]
        public virtual void testAppend()
        {
            EventBean[] setOne = makeEventArray(new String[] { "a1", "a2" });
            EventBean[] setTwo = makeEventArray(new String[] { "b1", "b2", "b3" });
            EventBean[] total = EventBeanUtility.Append(setOne, setTwo);

            Assert.AreEqual(setOne[0], total[0]);
            Assert.AreEqual(setOne[1], total[1]);
            Assert.AreEqual(setTwo[0], total[2]);
            Assert.AreEqual(setTwo[1], total[3]);
            Assert.AreEqual(setTwo[2], total[4]);

            setOne = makeEventArray(new String[] { "a1" });
            setTwo = makeEventArray(new String[] { "b1" });
            total = EventBeanUtility.Append(setOne, setTwo);

            Assert.AreEqual(setOne[0], total[0]);
            Assert.AreEqual(setTwo[0], total[1]);
        }

        [Test]
        public virtual void testToArray()
        {
            // Test list with 2 elements
        		IList<EventBean> eventList = makeEventList(new String[] {"a1", "a2"});
            EventBean[] array = EventBeanUtility.ToArray(eventList);
            Assert.AreEqual(2, array.Length);
            Assert.AreEqual(eventList[0], array[0]);
            Assert.AreEqual(eventList[1], array[1]);

            // Test list with 1 element
            eventList = makeEventList(new String[] { "a1" });
            array = EventBeanUtility.ToArray(eventList);
            Assert.AreEqual(1, array.Length);
            Assert.AreEqual(eventList[0], array[0]);

            // Test empty list
            eventList = makeEventList(new String[0]);
            array = EventBeanUtility.ToArray(eventList);
            Assert.IsNull(array);

            // Test null
            array = EventBeanUtility.ToArray(null);
            Assert.IsNull(array);
        }

        [Test]
        public virtual void testGetPropertyArray()
        {
            // try 2 properties
            EventPropertyGetter[] getters = makeGetters();
            EventBean _event = SupportEventBeanFactory.createObject(new SupportBean("a", 10));
            Object[] properties = EventBeanUtility.GetPropertyArray(_event, getters);
            Assert.AreEqual(2, properties.Length);
            Assert.AreEqual("a", properties[0]);
            Assert.AreEqual(10, properties[1]);

            // try no properties
            properties = EventBeanUtility.GetPropertyArray(_event, new EventPropertyGetter[0]);
            Assert.AreEqual(0, properties.Length);
        }

        [Test]
        public virtual void testMultiKey()
        {
            // try 2 properties
            EventPropertyGetter[] getters = makeGetters();
            EventBean _event = SupportEventBeanFactory.createObject(new SupportBean("a", 10));
            MultiKeyUntyped multikey = EventBeanUtility.GetMultiKey(_event, getters);
            Assert.AreEqual(2, multikey.Keys.Length);
            Assert.AreEqual("a", multikey.Keys[0]);
            Assert.AreEqual(10, multikey.Keys[1]);

            // try no properties
            multikey = EventBeanUtility.GetMultiKey(_event, new EventPropertyGetter[0]);
            Assert.AreEqual(0, multikey.Keys.Length);
        }

        private EventPropertyGetter[] makeGetters()
        {
            EventType eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
            EventPropertyGetter[] getters = new EventPropertyGetter[2];
            getters[0] = eventType.GetGetter("string");
            getters[1] = eventType.GetGetter("IntPrimitive");
            return getters;
        }

        private EventType makeEventType()
        {
			EDictionary<String, Type> eventTypeMap = new EHashDictionary<String, Type>();
            eventTypeMap.Put("a", typeof(SupportBean));
            eventTypeMap.Put("b", typeof(SupportBean));
            EventType eventType = SupportEventTypeFactory.createMapType(eventTypeMap);
            return eventType;
        }

        private EventBean[] makeEventArray(String[] texts)
        {
            EventBean[] events = new EventBean[texts.Length];
            for (int i = 0; i < texts.Length; i++)
            {
                events[i] = SupportEventBeanFactory.createObject(texts[i]);
            }
            return events;
        }

        private IList<EventBean> makeEventList(String[] texts)
        {
            IList<EventBean> events = new List<EventBean>();
            for (int i = 0; i < texts.Length; i++)
            {
                events.Add(SupportEventBeanFactory.createObject(texts[i]));
            }
            return events;
        }
    }
}
