using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{

	[TestFixture]
    public class TestQueryPlanBuilder 
    {
        [Test]
        public virtual void testGetPlan()
        {
            IList<OuterJoinDesc> descList = new ELinkedList<OuterJoinDesc>();
            OuterJoinDesc joinDesc = SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT);
            descList.Add(joinDesc);

            QueryPlan plan = QueryPlanBuilder.getPlan(2, new ELinkedList<OuterJoinDesc>(), null, null);
            assertPlan(plan);

            plan = QueryPlanBuilder.getPlan(2, descList, null, null);
            assertPlan(plan);

            plan = QueryPlanBuilder.getPlan(2, descList, SupportExprNodeFactory.makeEqualsNode(), null);
            assertPlan(plan);

            plan = QueryPlanBuilder.getPlan(2, new ELinkedList<OuterJoinDesc>(), SupportExprNodeFactory.makeEqualsNode(), null);
            assertPlan(plan);
        }

        private void assertPlan(QueryPlan plan)
        {
            Assert.AreEqual(2, plan.ExecNodeSpecs.Length);
            Assert.AreEqual(2, plan.ExecNodeSpecs.Length);
        }
    }
}
