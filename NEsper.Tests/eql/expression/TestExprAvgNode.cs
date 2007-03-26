using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprAvgNode : TestExprAggregateNodeAdapter
    {
        private ExprAvgNode avgNodeDistinct;

        [SetUp]
        public virtual void setUp()
        {
            base.validatedNodeToTest = makeNode(5, typeof(int?), false);
            this.avgNodeDistinct = makeNode(6, typeof(int?), true);
        }

        [Test]
        public virtual void testAggregation()
        {
            ExprAvgNode.DoubleAvg agg = new ExprAvgNode.DoubleAvg();
            Assert.AreEqual(typeof(double?), agg.ValueType);
            Assert.AreEqual(null, agg.Value);
            Assert.IsTrue(agg.NewAggregator() is ExprAvgNode.DoubleAvg);

            agg.Enter(5);
            Assert.AreEqual(5d, agg.Value);

            agg.Enter(10);
            Assert.AreEqual(7.5d, agg.Value);

            agg.Leave(5);
            Assert.AreEqual(10d, agg.Value);
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(double?), validatedNodeToTest.ReturnType);
        }

        [Test]
        public virtual void testToExpressionString()
        {
            Assert.AreEqual("avg(5)", validatedNodeToTest.ExpressionString);
            Assert.AreEqual("avg(distinct 6)", avgNodeDistinct.ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
            Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprSumNode(false)));
        }

        private ExprAvgNode makeNode(Object value, Type type, bool isDistinct)
        {
            ExprAvgNode avgNode = new ExprAvgNode(isDistinct);
            avgNode.AddChildNode(new SupportExprNode(value, type));
            avgNode.Validate(null, null);
            return avgNode;
        }
    }
}
