using System;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.pattern
{
	[TestFixture]
    public class TestMatchEvent 
    {
        private EDictionary<String, EventBean> events;

        [SetUp]
        public virtual void setUp()
        {
            events = new EHashDictionary<String, EventBean>();
            String[] ids = new String[] { "0", "a", "b", "c", "d", "e", "f" };
            foreach (String id in ids)
            {
                events.Put(id, SupportEventBeanFactory.createObject(id));
            }
        }

        [Test]
        public virtual void testPutAndGet()
        {
            MatchedEventMap _event = new MatchedEventMap();
            _event.Add("tag", events.Fetch("a"));
            _event.Add("test", events.Fetch("b"));
            Assert.IsTrue(_event.getMatchingEvents().Fetch("tag") == events.Fetch("a"));
            Assert.IsTrue(_event.getMatchingEvent("tag") == events.Fetch("a"));
            Assert.IsTrue(_event.getMatchingEvent("test") == events.Fetch("b"));
        }

        [Test]
        public virtual void testEquals()
        {
            MatchedEventMap eventOne = new MatchedEventMap();
            MatchedEventMap eventTwo = new MatchedEventMap();
            Assert.IsTrue(eventOne.Equals(eventTwo));

            eventOne.Add("test", events.Fetch("a"));
            Assert.IsFalse(eventOne.Equals(eventTwo));
            Assert.IsFalse(eventTwo.Equals(eventOne));

            eventTwo.Add("test", events.Fetch("a"));
            Assert.IsTrue(eventOne.Equals(eventTwo));

            eventOne.Add("abc", events.Fetch("b"));
            eventTwo.Add("abc", events.Fetch("c"));
            Assert.IsFalse(eventOne.Equals(eventTwo));

            eventOne.Add("abc", events.Fetch("c"));
            Assert.IsTrue(eventOne.Equals(eventTwo));

            eventOne.Add("1", events.Fetch("d"));
            eventOne.Add("2", events.Fetch("e"));
            eventTwo.Add("2", events.Fetch("e"));
            eventTwo.Add("1", events.Fetch("d"));
            Assert.IsTrue(eventOne.Equals(eventTwo));
        }

        [Test]
        public virtual void testClone()
        {
            MatchedEventMap _event = new MatchedEventMap();

            _event.Add("tag", events.Fetch("0"));
            MatchedEventMap copy = _event.shallowCopy();

            Assert.IsTrue(copy.Equals(_event));
            _event.Add("a", events.Fetch("a"));
            Assert.IsFalse(copy.Equals(_event));
            copy.Add("a", events.Fetch("a"));
            Assert.IsTrue(copy.Equals(_event));

            MatchedEventMap copyTwo = copy.shallowCopy();
            Assert.IsTrue(copy.Equals(copyTwo));
            copyTwo.Add("b", events.Fetch("b"));

            Assert.IsTrue(copyTwo.getMatchingEvents().Count == 3);
            Assert.IsTrue(copyTwo.getMatchingEvent("tag") == events.Fetch("0"));
            Assert.IsTrue(copyTwo.getMatchingEvent("a") == events.Fetch("a"));
            Assert.IsTrue(copyTwo.getMatchingEvent("b") == events.Fetch("b"));

            Assert.IsTrue(copy.getMatchingEvents().Count == 2);
            Assert.IsTrue(copy.getMatchingEvent("tag") == events.Fetch("0"));
            Assert.IsTrue(copy.getMatchingEvent("a") == events.Fetch("a"));

            Assert.IsTrue(_event.getMatchingEvents().Count == 2);
            Assert.IsTrue(_event.getMatchingEvent("tag") == events.Fetch("0"));
            Assert.IsTrue(_event.getMatchingEvent("a") == events.Fetch("a"));
        }

        [Test]
        public virtual void testMerge()
        {
            MatchedEventMap eventOne = new MatchedEventMap();
            MatchedEventMap eventTwo = new MatchedEventMap();

            eventOne.Add("tagA", events.Fetch("a"));
            eventOne.Add("tagB", events.Fetch("b"));

            eventTwo.Add("tagB", events.Fetch("c"));
            eventTwo.Add("xyz", events.Fetch("d"));

            eventOne.merge(eventTwo);
            Assert.IsTrue(eventOne.getMatchingEvent("tagA") == events.Fetch("a"));
            Assert.IsTrue(eventOne.getMatchingEvent("tagB") == events.Fetch("c"));
            Assert.IsTrue(eventOne.getMatchingEvent("xyz") == events.Fetch("d"));
            Assert.IsTrue(eventOne.getMatchingEvents().Count == 3);
        }
    }
}
