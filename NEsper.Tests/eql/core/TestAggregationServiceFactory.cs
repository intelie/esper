using System;
using System.Collections.Generic;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
    public class TestAggregationServiceFactory 
    {
        private IList<ExprAggregateNode> aggregateNodes;
        private IList<ExprNode> sortByNodes;

        [SetUp]
        public virtual void setUp()
        {
            aggregateNodes = new List<ExprAggregateNode>();
            sortByNodes = new List<ExprNode>();
        }

        [Test]
        public virtual void testGetService()
        {
            // Test with aggregates but no group by
            aggregateNodes.Add(SupportExprNodeFactory.makeSumAggregateNode());
            AggregationService service = AggregationServiceFactory.GetService(aggregateNodes, false, null, sortByNodes);
            Assert.IsTrue(service is AggregationServiceGroupAllImpl);

            // Test with aggregates and group by
            service = AggregationServiceFactory.GetService(aggregateNodes, true, null, sortByNodes);
            Assert.IsTrue(service is AggregationServiceGroupByImpl);
        }

        [Test]
        public virtual void testGetNullService()
        {
            // Test no aggregates and no group-by
            AggregationService service = AggregationServiceFactory.GetService(aggregateNodes, false, null, sortByNodes);
            Assert.IsTrue(service is AggregationServiceNull);
        }
    }
}
