using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.eql.core;
using net.esper.support.eql;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprAvedevNode : TestExprAggregateNodeAdapter
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
            Assert.AreEqual("avedev(5)", validatedNodeToTest.ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
            Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprStddevNode(false)));
        }

        [Test]
        public virtual void testAggregateFunction()
        {
            Aggregator agg = validatedNodeToTest.AggregationFunction;
            Assert.AreEqual(typeof(double?), agg.ValueType);

            Assert.IsNull(agg.Value);

            agg.enter(82);
            Assert.AreEqual(0D, agg.Value);

            agg.enter(78);
            Assert.AreEqual(2D, agg.Value);

            agg.enter(70);
            double result = (double)agg.Value;
            Assert.AreEqual("4.4444", result.ToString().Substring(0, (6) - (0)));

            agg.enter(58);
            Assert.AreEqual(8D, agg.Value);

            agg.enter(42);
            Assert.AreEqual(12.8D, agg.Value);

            agg.leave(82);
            Assert.AreEqual(12D, agg.Value);

            agg.leave(58);
            result = (Double)agg.Value;
            Assert.AreEqual("14.2222", result.ToString().Substring(0, (7) - (0)));
        }

        private ExprAvedevNode makeNode(Object value, Type type)
        {
            ExprAvedevNode avedevNode = new ExprAvedevNode(false);
            avedevNode.AddChildNode(new SupportExprNode(value, type));
            avedevNode.Validate(null, null);
            return avedevNode;
        }
    }
}