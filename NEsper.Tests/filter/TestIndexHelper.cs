using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{

	[TestFixture]
    public class TestIndexHelper 
    {
        private EventType eventType;
        private ETreeSet<FilterValueSetParam> parameters;
        private FilterValueSetParam parameterOne;
        private FilterValueSetParam parameterTwo;
        private FilterValueSetParam parameterThree;

        [SetUp]
        public virtual void setUp()
        {
            eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
            parameters = new ETreeSet<FilterValueSetParam>(new FilterSpecParamComparator());

            // Create parameter test list
            parameterOne = new FilterValueSetParamImpl("IntPrimitive", FilterOperator.GREATER, 10);
            parameters.Add(parameterOne);
            parameterTwo = new FilterValueSetParamImpl("doubleBoxed", FilterOperator.GREATER, 20d);
            parameters.Add(parameterTwo);
            parameterThree = new FilterValueSetParamImpl("string", FilterOperator.EQUAL, "sometext");
            parameters.Add(parameterThree);
        }

        [Test]
        public virtual void testFindIndex()
        {
            IList<FilterParamIndex> indexes = new List<FilterParamIndex>();

            // Create index list wity index that doesn't match
            FilterParamIndex indexOne = IndexFactory.createIndex(eventType, "boolPrimitive", FilterOperator.EQUAL);
            indexes.Add(indexOne);
            Assert.IsTrue(IndexHelper.findIndex(parameters, indexes) == null);

            // Create index list wity index that doesn't match
            indexOne = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.GREATER_OR_EQUAL);
            indexes.Clear();
            indexes.Add(indexOne);
            Assert.IsTrue(IndexHelper.findIndex(parameters, indexes) == null);

            // Add an index that does match a parameter
            FilterParamIndex indexTwo = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.GREATER);
            indexes.Add(indexTwo);
            Pair<FilterValueSetParam, FilterParamIndex> pair = IndexHelper.findIndex(parameters, indexes);
            Assert.IsTrue(pair != null);
            Assert.AreEqual(parameterTwo, pair.First);
            Assert.AreEqual(indexTwo, pair.Second);

            // Add another index that does match a parameter, should return first match however which is doubleBoxed
            FilterParamIndex indexThree = IndexFactory.createIndex(eventType, "IntPrimitive", FilterOperator.GREATER);
            indexes.Add(indexThree);
            pair = IndexHelper.findIndex(parameters, indexes);
            Assert.AreEqual(parameterTwo, pair.First);
            Assert.AreEqual(indexTwo, pair.Second);

            // Try again removing one index
            indexes.Remove(indexTwo);
            pair = IndexHelper.findIndex(parameters, indexes);
            Assert.AreEqual(parameterOne, pair.First);
            Assert.AreEqual(indexThree, pair.Second);
        }

        [Test]
        public virtual void testFindParameter()
        {
            FilterParamIndex indexOne = IndexFactory.createIndex(eventType, "boolPrimitive", FilterOperator.EQUAL);
            Assert.IsNull(IndexHelper.findParameter(parameters, indexOne));

            FilterParamIndex indexTwo = IndexFactory.createIndex(eventType, "string", FilterOperator.EQUAL);
            Assert.AreEqual(parameterThree, IndexHelper.findParameter(parameters, indexTwo));

            FilterParamIndex indexThree = IndexFactory.createIndex(eventType, "IntPrimitive", FilterOperator.GREATER);
            Assert.AreEqual(parameterOne, IndexHelper.findParameter(parameters, indexThree));
        }
    }
}
