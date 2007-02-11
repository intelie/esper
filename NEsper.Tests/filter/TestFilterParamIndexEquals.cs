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
    public class TestFilterParamIndexEquals 
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
        public virtual void testLong()
        {
            FilterParamIndexEquals index = new FilterParamIndexEquals("shortBoxed", testEventType);

            index.Put(1, testEvaluator);
            index.Put(20, testEvaluator);

            verifyShortBoxed(index, (short)10, 0);
            verifyShortBoxed(index, (short)1, 1);
            verifyShortBoxed(index, (short)20, 1);
            verifyShortBoxed(index, null, 0);

            Assert.AreEqual(testEvaluator, index[(short) 1]);
            Assert.IsTrue(index.ReadWriteLock != null);
            Assert.IsTrue(index.Remove((short)1));
            Assert.IsFalse(index.Remove((short)1));
            Assert.AreEqual(null, index[(short)1]);

            try
            {
                index.Put("a", testEvaluator);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testBoolean()
        {
            FilterParamIndexEquals index = new FilterParamIndexEquals("boolPrimitive", testEventType);

            index.Put(false, testEvaluator);

            verifyBooleanPrimitive(index, false, 1);
            verifyBooleanPrimitive(index, true, 0);
        }

        [Test]
        public virtual void testString()
        {
            FilterParamIndexEquals index = new FilterParamIndexEquals("str", testEventType);

            index.Put("hello", testEvaluator);
            index.Put("test", testEvaluator);

            verifyString(index, null, 0);
            verifyString(index, "dudu", 0);
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

        [Test]
        public virtual void testFloatPrimitive()
        {
            FilterParamIndexEquals index = new FilterParamIndexEquals("floatPrimitive", testEventType);

            index.Put(1.5f, testEvaluator);

            verifyfloatPrimitive(index, 1.5f, 1);
            verifyfloatPrimitive(index, 2.2f, 0);
            verifyfloatPrimitive(index, 0, 0);

            try
            {
                index.Put((double)20, testEvaluator);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }

        private void verifyShortBoxed(FilterParamIndex index, short? testValue, int numExpected)
        {
            testBean.shortBoxed = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }

        private void verifyBooleanPrimitive(FilterParamIndex index, bool testValue, int numExpected)
        {
            testBean.boolPrimitive = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }

        private void verifyString(FilterParamIndex index, String testValue, int numExpected)
        {
            testBean.str = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }

        private void verifyfloatPrimitive(FilterParamIndex index, float testValue, int numExpected)
        {
            testBean.floatPrimitive = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }
    }
}
