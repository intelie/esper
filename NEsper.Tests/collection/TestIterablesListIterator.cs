using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
	[TestFixture]
    public class TestIterableListIterator 
    {
        private EDictionary<String, EventBean> events;

        [SetUp]
        public virtual void setUp()
        {
            events = EventFactoryHelper.MakeEventMap(
                new String[] {
                    "a",
                    "b",
                    "c",
                    "d", 
                    "e", 
                    "f",
                    "g", 
                    "h",
                    "i", 
                    "z"
                });
        }

        [Test]
        public void testIterator()
        {
            IList<IEnumerable<EventBean>> iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "a", "b", "c" }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "a", "b", "c" }));

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "a" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "b" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "c" }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "a", "b", "c" }));

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "a", "b" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "c" }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "a", "b", "c" }));

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "a", "b" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "c" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "a", "b", "c" }));

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            checkResults(iterables, null);

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            checkResults(iterables, null);

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "d" }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "d" }));

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "d" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "d" }));

            iterables = new List<IEnumerable<EventBean>>();
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "a", "b", "c" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "d" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "e", "f" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "g" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "h", "i" }));
            iterables.Add(EventFactoryHelper.MakeList(events, new String[] { "z" }));
            checkResults(iterables, EventFactoryHelper.MakeArray(events, new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "z" }));

            iterables = new List<IEnumerable<EventBean>>();
            checkResults(iterables, null);
        }

        private void checkResults(IList<IEnumerable<EventBean>> iterables, EventBean[] expectedValues)
        {
            IterablesListIterator iterator = new IterablesListIterator(iterables);
            ArrayAssertionUtil.AreEqualExactOrder(iterator, expectedValues);
        }
    }
}

