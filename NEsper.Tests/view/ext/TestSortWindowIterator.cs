using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.ext
{

    [TestFixture]
    public class TestSortWindowIterator
    {
        private EDictionary<String, EventBean> events;
        private ETreeDictionary<MultiKey<Object>, ELinkedList<EventBean>> testMap;
        private IComparer<MultiKey<Object>> comparator;

        [SetUp]
        public virtual void setUp()
        {
            events = EventFactoryHelper.MakeEventMap(new String[] { "a", "b", "c", "d", "f", "g" });
            comparator = new MultiKeyComparator<Object>(new bool[] { false });
            testMap = new ETreeDictionary<MultiKey<Object>, ELinkedList<EventBean>>(comparator);
        }

        [Test]
        public virtual void testEmpty()
        {
            IEnumerator<EventBean> it = new SortWindowIterator(testMap);
            ArrayAssertionUtil.assertEqualsExactOrder(it, (EventBean[]) null);
        }

        [Test]
        public virtual void testOneElement()
        {
            ELinkedList<EventBean> list = new ELinkedList<EventBean>();
            list.Add(events.Fetch("a"));
            MultiKey<Object> key = new MultiKey<Object>(new Object[] { "akey" });
            testMap.Put(key, list);

            IEnumerator<EventBean> it = new SortWindowIterator(testMap);
            ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {
                events.Fetch("a") });
        }

        [Test]
        public virtual void testTwoInOneEntryElement()
        {
            ELinkedList<EventBean> list = new ELinkedList<EventBean>();
            list.Add(events.Fetch("a"));
            list.Add(events.Fetch("b"));
            MultiKey<Object> key = new MultiKey<Object>(new Object[] { "keyA" });
            testMap.Put(key, list);

            IEnumerator<EventBean> it = new SortWindowIterator(testMap);
            ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {
                events.Fetch("a"), 
                events.Fetch("a") });
        }

        [Test]
        public virtual void testTwoSeparateEntryElement()
        {
            ELinkedList<EventBean> list1 = new ELinkedList<EventBean>();
            list1.Add(events.Fetch("a"));
            MultiKey<Object> keyB = new MultiKey<Object>(new Object[] { "keyB" });
            testMap.Put(keyB, list1);

            ELinkedList<EventBean> list2 = new ELinkedList<EventBean>();
            list2.Add(events.Fetch("b"));
            MultiKey<Object> keyA = new MultiKey<Object>(new Object[] { "keyA" });
            testMap.Put(keyA, list2); // Actually before list1

            IEnumerator<EventBean> it = new SortWindowIterator(testMap);
            ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {
                events.Fetch("b"),
                events.Fetch("b") });
        }

        [Test]
        public virtual void testTwoByTwoEntryElement()
        {
            ELinkedList<EventBean> list1 = new ELinkedList<EventBean>();
            list1.Add(events.Fetch("a"));
            list1.Add(events.Fetch("b"));
            MultiKey<Object> keyB = new MultiKey<Object>(new Object[] { "keyB" });
            testMap.Put(keyB, list1);

            ELinkedList<EventBean> list2 = new ELinkedList<EventBean>();
            list2.Add(events.Fetch("c"));
            list2.Add(events.Fetch("d"));
            MultiKey<Object> keyC = new MultiKey<Object>(new Object[] { "keyC" });
            testMap.Put(keyC, list2);

            IEnumerator<EventBean> it = new SortWindowIterator(testMap);
            ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {
                events.Fetch("a"), 
                events.Fetch("a"),
                events.Fetch("a"),
                events.Fetch("a") });
        }


        [Test]
        public virtual void testMixedEntryElement()
        {
            ELinkedList<EventBean> list1 = new ELinkedList<EventBean>();
            list1.Add(events.Fetch("a"));
            MultiKey<Object> keyA = new MultiKey<Object>(new Object[] { "keyA" });
            testMap.Put(keyA, list1);

            ELinkedList<EventBean> list2 = new ELinkedList<EventBean>();
            list2.Add(events.Fetch("c"));
            list2.Add(events.Fetch("d"));
            MultiKey<Object> keyB = new MultiKey<Object>(new Object[] { "keyB" });
            testMap.Put(keyB, list2);

            ELinkedList<EventBean> list3 = new ELinkedList<EventBean>();
            list3.Add(events.Fetch("e"));
            list3.Add(events.Fetch("f"));
            list3.Add(events.Fetch("g"));
            MultiKey<Object> keyC = new MultiKey<Object>(new Object[] { "keyC" });
            testMap.Put(keyC, list3);

            IEnumerator<EventBean> it = new SortWindowIterator(testMap);
            ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {
                events.Fetch("a"),
                events.Fetch("a"),
                events.Fetch("a"), 
                events.Fetch("a"),
                events.Fetch("a"),
                events.Fetch("a") });
        }
    }
}
