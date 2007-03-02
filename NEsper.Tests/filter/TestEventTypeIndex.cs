using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{

	[TestFixture]
    public class TestEventTypeIndex 
    {
        private EventTypeIndex testIndex;

        private EventBean testEventBean;
        private EventType testEventType;

        private FilterCallbackSetNode callbackSetNode;
        private FilterCallback filterCallback;

        [SetUp]
        public virtual void setUp()
        {
            SupportBean testBean = new SupportBean();
            testEventBean = SupportEventBeanFactory.createObject(testBean);
            testEventType = testEventBean.EventType;

            callbackSetNode = new FilterCallbackSetNode();
            filterCallback = new SupportFilterCallback();
            callbackSetNode.Add(filterCallback);

            testIndex = new EventTypeIndex();
            testIndex.Add(testEventType, callbackSetNode);
        }

        [Test]
        public virtual void testMatch()
        {
            IList<FilterCallback> matchesList = new List<FilterCallback>();

            // Invoke match
            testIndex.MatchEvent(testEventBean, matchesList);

            Assert.AreEqual(1, matchesList.Count);
            Assert.AreEqual(filterCallback, matchesList[0]);
        }

        [Test]
        public virtual void testInvalidSecondAdd()
        {
            try
            {
                testIndex.Add(testEventType, callbackSetNode);
                Assert.IsTrue(false);
            }
            catch (System.SystemException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testGet()
        {
            Assert.AreEqual(callbackSetNode, testIndex[testEventType]);
        }

        [Test]
        public virtual void testSuperclassMatch()
        {
            testEventBean = SupportEventBeanFactory.createObject(new ISupportAImplSuperGImplPlus());
            testEventType = SupportEventTypeFactory.createBeanType(typeof(ISupportA));

            testIndex = new EventTypeIndex();
            testIndex.Add(testEventType, callbackSetNode);

            IList<FilterCallback> matchesList = new List<FilterCallback>();
            testIndex.MatchEvent(testEventBean, matchesList);

            Assert.AreEqual(1, matchesList.Count);
            Assert.AreEqual(filterCallback, matchesList[0]);
        }

        [Test]
        public virtual void testInterfaceMatch()
        {
            testEventBean = SupportEventBeanFactory.createObject(new ISupportABCImpl("a", "b", "ab", "c"));
            testEventType = SupportEventTypeFactory.createBeanType(typeof(ISupportBaseAB));

            testIndex = new EventTypeIndex();
            testIndex.Add(testEventType, callbackSetNode);

            IList<FilterCallback> matchesList = new List<FilterCallback>();
            testIndex.MatchEvent(testEventBean, matchesList);

            Assert.AreEqual(1, matchesList.Count);
            Assert.AreEqual(filterCallback, matchesList[0]);
        }
    }
}
