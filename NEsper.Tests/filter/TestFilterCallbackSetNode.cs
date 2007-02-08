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
    public class TestFilterCallbackSetNode 
    {
        private SupportEventEvaluator testEvaluator;
        private FilterCallbackSetNode testNode;

        [SetUp]
        public virtual void setUp()
        {
            testEvaluator = new SupportEventEvaluator();
            testNode = new FilterCallbackSetNode();
        }

        [Test]
        public virtual void testNodeGetSet()
        {
            FilterCallback exprOne = new SupportFilterCallback();

            // Check pre-conditions
            Assert.IsTrue(testNode.NodeRWLock != null);
            Assert.IsFalse(testNode.Contains(exprOne));
            Assert.AreEqual(0, testNode.FilterCallbackCount);
            Assert.AreEqual(0, testNode.Indizes.Count);
            Assert.IsTrue(testNode.Count == 0);

            testNode.Add(exprOne);

            // Check after add
            Assert.IsTrue(testNode.Contains(exprOne));
            Assert.AreEqual(1, testNode.FilterCallbackCount);
            Assert.IsFalse(testNode.Count == 0);

            // Add an indexOne
            FilterParamIndex indexOne = new SupportFilterParamIndex();
            testNode.Add(indexOne);

            // Check after add
            Assert.AreEqual(1, testNode.Indizes.Count);
            Assert.AreEqual(indexOne, testNode.Indizes[0]);

            // Check removes
            Assert.IsTrue(testNode.Remove(exprOne));
            Assert.IsFalse(testNode.Count == 0);
            Assert.IsFalse(testNode.Remove(exprOne));
            Assert.IsTrue(testNode.Remove(indexOne));
            Assert.IsFalse(testNode.Remove(indexOne));
            Assert.IsTrue(testNode.Count == 0);
        }

        [Test]
        public virtual void testNodeMatching()
        {
            SupportBeanSimple eventObject = new SupportBeanSimple("DepositEvent_1", 1);
            EventBean eventBean = SupportEventBeanFactory.createObject(eventObject);

            FilterCallback expr = new SupportFilterCallback();
            testNode.Add(expr);

            // Check matching without an index node
            IList<FilterCallback> matches = new List<FilterCallback>();
            testNode.matchEvent(eventBean, matches);
            Assert.AreEqual(1, matches.Count);
            Assert.AreEqual(expr, matches[0]);
            matches.Clear();

            // Create, add and populate an index node
            FilterParamIndex index = new FilterParamIndexEquals("myString", eventBean.EventType);
            testNode.Add(index);
            index["DepositEvent_1"] = testEvaluator;

            // Verify matcher instance stored in index is called
            testNode.matchEvent(eventBean, matches);

            Assert.IsTrue(testEvaluator.AndResetCountInvoked == 1);
            Assert.IsTrue(testEvaluator.LastEvent == eventBean);
            Assert.AreEqual(1, matches.Count);
            Assert.AreEqual(expr, matches[0]);
        }
    }
}
