using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
    [TestFixture]
    public class TestTimeWindowIterator
    {
        private EDictionary<String, EventBean> events;

        [SetUp]
        public virtual void setUp()
        {
            events = EventFactoryHelper.MakeEventMap(new String[] { "a", "b", "c", "d", "f", "g" });
        }

        [Test]
        public void testEmpty()
        {
            LinkedList<Pair<Int64, List<EventBean>>> testWindow = new LinkedList<Pair<Int64, List<EventBean>>>();
            IEnumerator<EventBean> it = new TimeWindowIterator(testWindow);
            ArrayAssertionUtil.AreEqualExactOrder(it, (EventBean[])null);
        }

        [Test]
        public void testOneElement()
        {
            LinkedList<Pair<Int64, List<EventBean>>> testWindow = new LinkedList<Pair<Int64, List<EventBean>>>();
            List<EventBean> list = new List<EventBean>();
            list.Add(events.Fetch("avalue"));
            addToWindow(testWindow, 10L, list);

            IEnumerator<EventBean> it = new TimeWindowIterator(testWindow);
            ArrayAssertionUtil.AreEqualExactOrder(
                it,
                new EventBean[]
                    {
                        events.Fetch("avalue")
                    });
        }

        [Test]
        public void testTwoInOneEntryElement()
        {
            LinkedList<Pair<Int64, List<EventBean>>> testWindow = new LinkedList<Pair<Int64, List<EventBean>>>();
            List<EventBean> list = new List<EventBean>();
            list.Add(events.Fetch("a"));
            list.Add(events.Fetch("b"));
            addToWindow(testWindow, 10L, list);

            IEnumerator<EventBean> it = new TimeWindowIterator(testWindow);
            ArrayAssertionUtil.AreEqualExactOrder(
                it,
                new EventBean[]
		        {
			        events.Fetch("a"),
			        events.Fetch("b")
		        });
        }

        [Test]
        public void testTwoSeparateEntryElement()
        {
            LinkedList<Pair<Int64, List<EventBean>>> testWindow = new LinkedList<Pair<Int64, List<EventBean>>>();
            List<EventBean> list2 = new List<EventBean>();
            list2.Add(events.Fetch("b"));
            addToWindow(testWindow, 5L, list2); // Actually before list1

            List<EventBean> list1 = new List<EventBean>();
            list1.Add(events.Fetch("a"));
            addToWindow(testWindow, 10L, list1);

            IEnumerator<EventBean> it = new TimeWindowIterator(testWindow);
            ArrayAssertionUtil.AreEqualExactOrder(
                it,
                new EventBean[]
		        {
			        events.Fetch("b"),
			        events.Fetch("a")
		        });
        }

        [Test]
        public void testTwoByTwoEntryElement()
        {
            LinkedList<Pair<Int64, List<EventBean>>> testWindow = new LinkedList<Pair<Int64, List<EventBean>>>();

            List<EventBean> list1 = new List<EventBean>();
            list1.Add(events.Fetch("a"));
            list1.Add(events.Fetch("b"));
            addToWindow(testWindow, 10L, list1);

            List<EventBean> list2 = new List<EventBean>();
            list2.Add(events.Fetch("c"));
            list2.Add(events.Fetch("d"));
            addToWindow(testWindow, 15L, list2);

            IEnumerator<EventBean> it = new TimeWindowIterator(testWindow);
            ArrayAssertionUtil.AreEqualExactOrder(
                it,
                new EventBean[]
		        {
			        events.Fetch("a"),
			        events.Fetch("b"),
			        events.Fetch("c"),
			        events.Fetch("d")
		        });
        }

        [Test]
        public void testMixedEntryElement()
        {
            LinkedList<Pair<Int64, List<EventBean>>> testWindow = new LinkedList<Pair<Int64, List<EventBean>>>();

            List<EventBean> list1 = new List<EventBean>();
            list1.Add(events.Fetch("a"));
            addToWindow(testWindow, 10L, list1);

            List<EventBean> list2 = new List<EventBean>();
            list2.Add(events.Fetch("c"));
            list2.Add(events.Fetch("d"));
            addToWindow(testWindow, 15L, list2);

            List<EventBean> list3 = new List<EventBean>();
            list3.Add(events.Fetch("e"));
            list3.Add(events.Fetch("f"));
            list3.Add(events.Fetch("g"));
            addToWindow(testWindow, 20L, list3);

            IEnumerator<EventBean> it = new TimeWindowIterator(testWindow);
            ArrayAssertionUtil.AreEqualExactOrder(
                it,
                new EventBean[]
		        {
                    events.Fetch("a"),
                    events.Fetch("c"),
                    events.Fetch("d"),
                    events.Fetch("e"),
                    events.Fetch("f"),
                    events.Fetch("g")
                });
        }

        private void addToWindow(
            LinkedList<Pair<Int64, List<EventBean>>> testWindow,
            Int64 key,
            List<EventBean> value)
        {
            testWindow.AddLast(new Pair<Int64, List<EventBean>>(key, value));
        }

        /// <summary>
        /// Widens an enumerator for use in test comparisons
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="enumIn"></param>
        /// <returns></returns>

        private IEnumerator<Object> Widen<T>(IEnumerator<T> enumIn)
        {
            while (enumIn.MoveNext())
            {
                yield return enumIn.Current;
            }
        }
    }
}
