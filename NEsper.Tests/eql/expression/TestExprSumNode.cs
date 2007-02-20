using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.support.eql;
using net.esper.type;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprSumNode : TestExprAggregateNodeAdapter
    {
        private ExprSumNode sumNode;

        [SetUp]
        public virtual void setUp()
        {
            sumNode = new ExprSumNode(false);

            base.validatedNodeToTest = makeNode(5, typeof(int));
        }

        [Test]
        public virtual void testGetType()
        {
            sumNode.AddChildNode(new SupportExprNode(typeof(int)));
            sumNode.validate(null, null);
            Assert.AreEqual(typeof(int?), sumNode.ReturnType);

            sumNode = new ExprSumNode(false);
            sumNode.AddChildNode(new SupportExprNode(typeof(float)));
            sumNode.validate(null, null);
            Assert.AreEqual(typeof(float?), sumNode.ReturnType);

            sumNode = new ExprSumNode(false);
            sumNode.AddChildNode(new SupportExprNode(typeof(short)));
            sumNode.validate(null, null);
            Assert.AreEqual(typeof(int?), sumNode.ReturnType);
        }

        [Test]
        public virtual void testToExpressionString()
        {
            // Build sum(4-2)
            ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
            arithNodeChild.AddChildNode(new SupportExprNode(4));
            arithNodeChild.AddChildNode(new SupportExprNode(2));

            sumNode = new ExprSumNode(false);
            sumNode.AddChildNode(arithNodeChild);

            Assert.AreEqual("sum((4-2))", sumNode.ExpressionString);
        }

        [Test]
        public virtual void testValidate()
        {
            // Must have exactly 1 subnodes
            try
            {
                sumNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // Must have only number-type subnodes
            sumNode.AddChildNode(new SupportExprNode(typeof(String)));
            sumNode.AddChildNode(new SupportExprNode(typeof(int?)));
            try
            {
                sumNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testMakeAggregator()
        {
            Assert.IsTrue(makeNode(5, typeof(int?)).PrototypeAggregator is ExprSumNode.IntegerSum);
            Assert.IsTrue(makeNode(5, typeof(float?)).PrototypeAggregator is ExprSumNode.FloatSum);
            Assert.IsTrue(makeNode(5, typeof(double?)).PrototypeAggregator is ExprSumNode.DoubleSum);
            Assert.IsTrue(makeNode(5, typeof(short?)).PrototypeAggregator is ExprSumNode.NumberIntegerSum);
            Assert.IsTrue(makeNode(5, typeof(long?)).PrototypeAggregator is ExprSumNode.LongSum);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(sumNode.EqualsNode(sumNode));
            Assert.IsFalse(sumNode.EqualsNode(new ExprOrNode()));
        }

        private ExprSumNode makeNode(Object value, Type type)
        {
            ExprSumNode sumNode = new ExprSumNode(false);
            sumNode.AddChildNode(new SupportExprNode(value, type));
            sumNode.validate(null, null);
            return sumNode;
        }
    }
}