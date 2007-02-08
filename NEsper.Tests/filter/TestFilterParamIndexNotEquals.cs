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
    public class TestFilterParamIndexNotEquals 
    {
        private SupportEventEvaluator testEvaluator;
        private SupportBean testBean;
        private EventBean testEventBean;
        private EventType testEventType;
        private IList<FilterCallback> matchesList;

        [SetUp]
        public virtual void setUp()
        {
            testEvaluator = new SupportEventEvaluator();
            testBean = new SupportBean();
            testEventBean = SupportEventBeanFactory.createObject(testBean);
            testEventType = testEventBean.EventType;
            matchesList = new List<FilterCallback>();
        }

        [Test]
        public virtual void testBoolean()
        {
            FilterParamIndexNotEquals index = new FilterParamIndexNotEquals("boolPrimitive", testEventType);
            Assert.AreEqual(FilterOperator.NOT_EQUAL, index.FilterOperator);
            Assert.AreEqual("boolPrimitive", index.PropertyName);

            index.Put(false, testEvaluator);

            verifyBooleanPrimitive(index, true, 1);
            verifyBooleanPrimitive(index, false, 0);
        }

        [Test]
        public virtual void testString()
        {
            FilterParamIndexNotEquals index = new FilterParamIndexNotEquals("string", testEventType);

            index.Put("hello", testEvaluator);
            index.Put("test", testEvaluator);

            verifyString(index, null, 0);
            verifyString(index, "dudu", 2);
            verifyString(index, "hello", 1);
            verifyString(index, "test", 1);

            try
            {
                index.Put(10, testEvaluator);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }

        private void verifyBooleanPrimitive(FilterParamIndex index, bool testValue, int numExpected)
        {
            testBean.BoolPrimitive = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }

        private void verifyString(FilterParamIndex index, String testValue, int numExpected)
        {
            testBean.StringValue = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }
    }
}
