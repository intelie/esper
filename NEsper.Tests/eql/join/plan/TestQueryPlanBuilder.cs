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
            IList<OuterJoinDesc> descList = new List<OuterJoinDesc>();
            OuterJoinDesc joinDesc = SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT);
            descList.Add(joinDesc);

            QueryPlan plan = QueryPlanBuilder.GetPlan(2, new List<OuterJoinDesc>(), null, null);
            assertPlan(plan);

            plan = QueryPlanBuilder.GetPlan(2, descList, null, null);
            assertPlan(plan);

            plan = QueryPlanBuilder.GetPlan(2, descList, SupportExprNodeFactory.makeEqualsNode(), null);
            assertPlan(plan);

            plan = QueryPlanBuilder.GetPlan(2, new List<OuterJoinDesc>(), SupportExprNodeFactory.makeEqualsNode(), null);
            assertPlan(plan);
        }

        private void assertPlan(QueryPlan plan)
        {
            Assert.AreEqual(2, plan.ExecNodeSpecs.Length);
            Assert.AreEqual(2, plan.ExecNodeSpecs.Length);
        }
    }
}
