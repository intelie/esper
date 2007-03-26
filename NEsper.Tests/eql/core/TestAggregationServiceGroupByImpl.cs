using System;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestAggregationServiceGroupByImpl 
	{
		private AggregationServiceGroupByImpl service;
		private MultiKey < Object > groupOneKey;
		private MultiKey < Object > groupTwoKey;
		
		[SetUp]
		public virtual void  setUp()
		{
			SupportAggregator[] aggregators = new SupportAggregator[2];
			for (int i = 0; i < aggregators.Length; i++)
			{
				aggregators[i] = new SupportAggregator();
			}

			ExprEvaluator[] evaluators = new ExprEvaluator[]{new SupportExprNode(5), new SupportExprNode(2)};
			
			service = new AggregationServiceGroupByImpl(evaluators, aggregators);

            groupOneKey = new MultiKey<Object>(new Object[] { "x", "y1" });
            groupTwoKey = new MultiKey<Object>(new Object[] { "x", "y2" });
		}
		
		[Test]
		public virtual void  testGetValue()
		{
			// apply 3 rows to group key 1, all aggregators evaluated their sub-expressions(constants 5 and 2)
			service.ApplyEnter(new EventBean[1], groupOneKey);
			service.ApplyEnter(new EventBean[1], groupOneKey);
			service.ApplyEnter(new EventBean[1], groupTwoKey);
			
			service.CurrentRow = groupOneKey;
            Assert.AreEqual(10, service.GetValue(0));
            Assert.AreEqual(4, service.GetValue(1));
			service.CurrentRow = groupTwoKey;
            Assert.AreEqual(5, service.GetValue(0));
            Assert.AreEqual(2, service.GetValue(1));
			
			service.ApplyLeave(new EventBean[1], groupTwoKey);
			service.ApplyLeave(new EventBean[1], groupTwoKey);
			service.ApplyLeave(new EventBean[1], groupTwoKey);
			service.ApplyLeave(new EventBean[1], groupOneKey);
			
			service.CurrentRow = groupOneKey;
            Assert.AreEqual(10 - 5, service.GetValue(0));
            Assert.AreEqual(4 - 2, service.GetValue(1));
			service.CurrentRow = groupTwoKey;
            Assert.AreEqual(5 - 15, service.GetValue(0));
            Assert.AreEqual(2 - 6, service.GetValue(1));
		}
	}
}
