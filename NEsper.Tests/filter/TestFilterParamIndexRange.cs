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
    public class TestFilterParamIndexRange 
    {
        private SupportEventEvaluator testEvaluator;
        private SupportBean testBean;
        private EventBean testEventBean;
        private EventType testEventType;
        private IList<FilterCallback> matchesList;
        private DoubleRange testRange;

        [SetUp]
        public virtual void setUp()
        {
            testEvaluator = new SupportEventEvaluator();
            testBean = new SupportBean();
            testEventBean = SupportEventBeanFactory.createObject(testBean);
            testEventType = testEventBean.EventType;
            matchesList = new List<FilterCallback>();

            testRange = new DoubleRange(10, 20);
        }

        [Test]
        public virtual void testInvalid()
        {
            try
            {
                new FilterParamIndexRange("doublePrimitive", FilterOperator.EQUAL, testEventType);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                new FilterParamIndexCompare("StringValue", FilterOperator.RANGE_CLOSED, testEventType);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public virtual void testLongBothEndpointsIncluded()
        {
            FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_CLOSED);
            verifylongPrimitive(index, -1, 0);
            verifylongPrimitive(index, 0, 2);
            verifylongPrimitive(index, 1, 5);
            verifylongPrimitive(index, 2, 5);
            verifylongPrimitive(index, 3, 7);
            verifylongPrimitive(index, 4, 6);
            verifylongPrimitive(index, 5, 6);
            verifylongPrimitive(index, 6, 6);
            verifylongPrimitive(index, 7, 6);
            verifylongPrimitive(index, 8, 6);
            verifylongPrimitive(index, 9, 5);
            verifylongPrimitive(index, 10, 3);
            verifylongPrimitive(index, 11, 1);

            index.Put(testRange, testEvaluator);
            Assert.AreEqual(testEvaluator, index[testRange]);
            Assert.IsTrue(index.ReadWriteLock != null);
            Assert.IsTrue(index.Remove(testRange));
            Assert.IsFalse(index.Remove(testRange));
            Assert.AreEqual(null, index[testRange]);

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
        public virtual void testLongLowEndpointIncluded()
        {
            FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_HALF_OPEN);
            verifylongPrimitive(index, -1, 0);
            verifylongPrimitive(index, 0, 2);
            verifylongPrimitive(index, 1, 5);
            verifylongPrimitive(index, 2, 5);
            verifylongPrimitive(index, 3, 6);
            verifylongPrimitive(index, 4, 6);
            verifylongPrimitive(index, 5, 3);
            verifylongPrimitive(index, 6, 5);
            verifylongPrimitive(index, 7, 4);
            verifylongPrimitive(index, 8, 5);
            verifylongPrimitive(index, 9, 3);
            verifylongPrimitive(index, 10, 1);
            verifylongPrimitive(index, 11, 1);
        }

        [Test]
        public virtual void testLongHighEndpointIncluded()
        {
            FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_HALF_CLOSED);
            verifylongPrimitive(index, -1, 0);
            verifylongPrimitive(index, 0, 0);
            verifylongPrimitive(index, 1, 2);
            verifylongPrimitive(index, 2, 5);
            verifylongPrimitive(index, 3, 5);
            verifylongPrimitive(index, 4, 6);
            verifylongPrimitive(index, 5, 6);
            verifylongPrimitive(index, 6, 3);
            verifylongPrimitive(index, 7, 5);
            verifylongPrimitive(index, 8, 4);
            verifylongPrimitive(index, 9, 5);
            verifylongPrimitive(index, 10, 3);
            verifylongPrimitive(index, 11, 1);
        }

        [Test]
        public virtual void testLongNeitherEndpointIncluded()
        {
            FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_OPEN);
            verifylongPrimitive(index, -1, 0);
            verifylongPrimitive(index, 0, 0);
            verifylongPrimitive(index, 1, 2);
            verifylongPrimitive(index, 2, 5);
            verifylongPrimitive(index, 3, 4);
            verifylongPrimitive(index, 4, 6);
            verifylongPrimitive(index, 5, 3);
            verifylongPrimitive(index, 6, 2);
            verifylongPrimitive(index, 7, 3);
            verifylongPrimitive(index, 8, 3);
            verifylongPrimitive(index, 9, 3);
            verifylongPrimitive(index, 10, 1);
            verifylongPrimitive(index, 11, 1);
        }

        [Test]
        public virtual void testDoubleBothEndpointsIncluded()
        {
            FilterParamIndexRange index = this.getDoubleDataset(FilterOperator.RANGE_CLOSED);
            verifydoublePrimitive(index, 1.49, 0);
            verifydoublePrimitive(index, 1.5, 1);
            verifydoublePrimitive(index, 2.5, 1);
            verifydoublePrimitive(index, 2.51, 0);
            verifydoublePrimitive(index, 3.5, 2);
            verifydoublePrimitive(index, 4.4, 2);
            verifydoublePrimitive(index, 4.5, 2);
            verifydoublePrimitive(index, 4.5001, 1);
            verifydoublePrimitive(index, 5.1, 1);
            verifydoublePrimitive(index, 5.8, 2);
            verifydoublePrimitive(index, 6.7, 2);
            verifydoublePrimitive(index, 6.8, 1);
            verifydoublePrimitive(index, 9.5, 1);
            verifydoublePrimitive(index, 10.1, 0);
        }

        [Test]
        public virtual void testDoubleFixedRangeSize()
        {
            FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", FilterOperator.RANGE_OPEN, testEventType);

            for (int i = 0; i < 10000; i++)
            {
                DoubleRange range = new DoubleRange(i, i + 1);
                index.Put(range, testEvaluator);
            }

            verifydoublePrimitive(index, 5000, 0);
            verifydoublePrimitive(index, 5000.5, 1);
            verifydoublePrimitive(index, 5001, 0);
        }

        [Test]
        public virtual void testDoubleVariableRangeSize()
        {
            FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", FilterOperator.RANGE_CLOSED, testEventType);

            for (int i = 0; i < 100; i++)
            {
                DoubleRange range = new DoubleRange(i, 2 * i);
                index.Put(range, testEvaluator);
            }

            // 1 to 2
            // 2 to 4
            // 3 to 6
            // and so on

            verifydoublePrimitive(index, 1, 1);
            verifydoublePrimitive(index, 2, 2);
            verifydoublePrimitive(index, 2.001, 1);
            verifydoublePrimitive(index, 3, 2);
            verifydoublePrimitive(index, 4, 3);
            verifydoublePrimitive(index, 4.5, 2);
            verifydoublePrimitive(index, 50, 26);
        }

        private FilterParamIndexRange getLongDataset(FilterOperator operatorType)
        {
            FilterParamIndexRange index = new FilterParamIndexRange("longPrimitive", operatorType, testEventType);

            AddToIndex(index, 0, 5);
            AddToIndex(index, 0, 6);
            AddToIndex(index, 1, 3);
            AddToIndex(index, 1, 5);
            AddToIndex(index, 1, 7);
            AddToIndex(index, 3, 5);
            AddToIndex(index, 3, 7);
            AddToIndex(index, 6, 9);
            AddToIndex(index, 6, 10);
            AddToIndex(index, 6, Int32.MaxValue - 1);
            AddToIndex(index, 7, 8);
            AddToIndex(index, 8, 9);
            AddToIndex(index, 8, 10);

            return index;
        }

        private FilterParamIndexRange getDoubleDataset(FilterOperator operatorType)
        {
            FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", operatorType, testEventType);

            AddToIndex(index, 1.5, 2.5);
            AddToIndex(index, 3.5, 4.5);
            AddToIndex(index, 3.5, 9.5);
            AddToIndex(index, 5.6, 6.7);

            return index;
        }

        private void verifydoublePrimitive(FilterParamIndex index, double testValue, int numExpected)
        {
            testBean.doublePrimitive = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }

        private void verifylongPrimitive(FilterParamIndex index, long testValue, int numExpected)
        {
            testBean.longPrimitive = testValue;
            index.matchEvent(testEventBean, matchesList);
            Assert.AreEqual(numExpected, testEvaluator.AndResetCountInvoked);
        }

        private void AddToIndex(FilterParamIndexRange index, double min, double max)
        {
            DoubleRange r = new DoubleRange(min, max);
            index.Put(r, testEvaluator);
        }
    }
}
