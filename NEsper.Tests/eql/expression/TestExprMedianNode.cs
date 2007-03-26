using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprMedianNode : TestExprAggregateNodeAdapter
    {
        [SetUp]
        public virtual void setUp()
        {
            base.validatedNodeToTest = makeNode(5, typeof(Int32));
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(double?), validatedNodeToTest.ReturnType);
        }

        [Test]
        public virtual void testToExpressionString()
        {
            Assert.AreEqual("median(5)", validatedNodeToTest.ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
            Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprSumNode(false)));
        }

        [Test]
        public virtual void testAggregator()
        {
            ExprMedianNode.DoubleMedian median = new ExprMedianNode.DoubleMedian();
            Assert.AreEqual(null, median.Value);
            median.Enter(10);
            Assert.AreEqual(10D, median.Value);
            median.Enter(20);
            Assert.AreEqual(15D, median.Value);
            median.Enter(10);
            Assert.AreEqual(10D, median.Value);

            median.Leave(10);
            Assert.AreEqual(15D, median.Value);
            median.Leave(10);
            Assert.AreEqual(20D, median.Value);
            median.Leave(20);
            Assert.AreEqual(null, median.Value);
        }

        private ExprMedianNode makeNode(Object value, Type type)
        {
            ExprMedianNode medianNode = new ExprMedianNode(false);
            medianNode.AddChildNode(new SupportExprNode(value, type));
            medianNode.Validate(null, null);
            return medianNode;
        }
    }
}