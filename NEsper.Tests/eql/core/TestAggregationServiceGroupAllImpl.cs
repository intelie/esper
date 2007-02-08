using System;

using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{

    [TestFixture]
    public class TestAggregationServiceGroupAllImpl
    {
        private AggregationServiceGroupAllImpl service;

        [SetUp]
        public virtual void setUp()
        {
            SupportAggregator[] aggregators = new SupportAggregator[2];
            for (int i = 0; i < aggregators.Length; i++)
            {
                aggregators[i] = new SupportAggregator();
            }

            ExprEvaluator[] evaluators = new ExprEvaluator[] { new SupportExprNode(5), new SupportExprNode(2) };

            service = new AggregationServiceGroupAllImpl(evaluators, aggregators);
        }

        [Test]
        public virtual void testApplyEnter()
        {
            // apply two rows, all aggregators evaluated their sub-expressions(constants 5 and 2) twice
            service.applyEnter(new EventBean[1], null);
            service.applyEnter(new EventBean[1], null);
            Assert.AreEqual(10, service.getValue(0));
            Assert.AreEqual(4, service.getValue(1));
        }

        [Test]
        public virtual void testApplyLeave()
        {
            // apply 3 rows, all aggregators evaluated their sub-expressions(constants 5 and 2)
            service.applyLeave(new EventBean[1], null);
            service.applyLeave(new EventBean[1], null);
            service.applyLeave(new EventBean[1], null);
            Assert.AreEqual(-15, service.getValue(0));
            Assert.AreEqual(-6, service.getValue(1));
        }

        private static EventBean[][] MakeEvents(int countRows)
        {
            EventBean[][] tmpArray = new EventBean[countRows][];
            for (int i = 0; i < countRows; i++)
            {
                tmpArray[i] = new EventBean[0];
            }
            EventBean[][] result = tmpArray;
            for (int i = 0; i < countRows; i++)
            {
                result[i] = new EventBean[0];
            }
            return result;
        }
    }
}