using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{

    [TestFixture]
    public class TestExprMathNode
    {
        private ExprMathNode arithNode;

        [SetUp]
        public virtual void setUp()
        {
            arithNode = new ExprMathNode(MathArithTypeEnum.ADD);
        }

        [Test]
        public virtual void testGetType()
        {
            arithNode.AddChildNode(new SupportExprNode(typeof(double?)));
            arithNode.AddChildNode(new SupportExprNode(typeof(int?)));
            arithNode.validate(null, null);
            Assert.AreEqual(typeof(double?), arithNode.ReturnType);
        }

        [Test]
        public virtual void testToExpressionString()
        {
            // Build (5*(4-2)), not the same as 5*4-2
            ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
            arithNodeChild.AddChildNode(new SupportExprNode(4));
            arithNodeChild.AddChildNode(new SupportExprNode(2));

            arithNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
            arithNode.AddChildNode(new SupportExprNode(5));
            arithNode.AddChildNode(arithNodeChild);

            Assert.AreEqual("(5*(4-2))", arithNode.ExpressionString);
        }

        [Test]
        public virtual void testValidate()
        {
            // Must have exactly 2 subnodes
            try
            {
                arithNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // Must have only number-type subnodes
            arithNode.AddChildNode(new SupportExprNode(typeof(String)));
            arithNode.AddChildNode(new SupportExprNode(typeof(Int32)));
            try
            {
                arithNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testEvaluate()
        {
            arithNode.AddChildNode(new SupportExprNode((Object)10));
            arithNode.AddChildNode(new SupportExprNode((Object)1.5));
            arithNode.GetValidatedSubtree(null, null);
            Assert.AreEqual(11.5d, arithNode.Evaluate(null));

            arithNode = makeNode(null, typeof(Int32), 5d, typeof(Double));
            Assert.IsNull(arithNode.Evaluate(null));

            arithNode = makeNode(5, typeof(Int32), null, typeof(Double));
            Assert.IsNull(arithNode.Evaluate(null));

            arithNode = makeNode((Object)null, typeof(Int32), (Object)null, typeof(Double));
            Assert.IsNull(arithNode.Evaluate(null));
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(arithNode.EqualsNode(arithNode));
            Assert.IsFalse(arithNode.EqualsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE)));
        }

        private ExprMathNode makeNode(Object valueLeft, Type typeLeft, Object valueRight, Type typeRight)
        {
            ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
            mathNode.AddChildNode(new SupportExprNode(valueLeft, typeLeft));
            mathNode.AddChildNode(new SupportExprNode(valueRight, typeRight));
            return mathNode;
        }
    }
}
