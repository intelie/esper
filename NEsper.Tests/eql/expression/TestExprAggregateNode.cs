using System;
using System.Collections.Generic;

using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprAggregateNode
    {
        [Test]
        public virtual void testGetAggregatesBottomUp()
        {
            /*
            top (ag)
            c1                            c2
            c1_1 (ag)   c1_2 (ag)            c2_1     c2_2
            c2_1_1 (ag)    c2_1_2 (ag)
			
            */

            ExprNode top = new SupportAggregateExprNode(null);
            ExprNode c1 = new SupportExprNode(null);
            ExprNode c2 = new SupportExprNode(null);
            top.AddChildNode(c1);
            top.AddChildNode(c2);

            ExprNode c1_1 = new SupportAggregateExprNode(null);
            ExprNode c1_2 = new SupportAggregateExprNode(null);
            c1.AddChildNode(c1_1);
            c1.AddChildNode(c1_2);
            c1_1.AddChildNode(new SupportExprNode(null));
            c1_2.AddChildNode(new SupportExprNode(null));

            ExprNode c2_1 = new SupportExprNode(null);
            ExprNode c2_2 = new SupportExprNode(null);
            c2.AddChildNode(c2_1);
            c2.AddChildNode(c2_2);
            c2_2.AddChildNode(new SupportExprNode(null));

            ExprNode c2_1_1 = new SupportAggregateExprNode(null);
            ExprNode c2_1_2 = new SupportAggregateExprNode(null);
            c2_1.AddChildNode(c2_1_1);
            c2_1.AddChildNode(c2_1_2);

            IList<ExprAggregateNode> aggregates = new List<ExprAggregateNode>();
            ExprAggregateNode.getAggregatesBottomUp(top, aggregates);

            Assert.AreEqual(5, aggregates.Count);
            Assert.AreSame(c2_1_1, aggregates[0]);
            Assert.AreSame(c2_1_2, aggregates[1]);
            Assert.AreSame(c1_1, aggregates[2]);
            Assert.AreSame(c1_2, aggregates[3]);
            Assert.AreSame(top, aggregates[4]);

            // Test no aggregates
            aggregates.Clear();
            ExprAggregateNode.getAggregatesBottomUp(new SupportExprNode(null), aggregates);
            Assert.IsTrue(aggregates.Count == 0);
        }
    }
}