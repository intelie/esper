using System;

using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprAndNode
    {
        private ExprAndNode andNode;

        [SetUp]
        public virtual void setUp()
        {
            andNode = new ExprAndNode();
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(bool?), andNode.ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            // test success
            andNode.AddChildNode(new SupportExprNode(typeof(bool)));
            andNode.AddChildNode(new SupportExprNode(typeof(bool)));
            andNode.Validate(null, null);

            // test failure, type mismatch
            andNode.AddChildNode(new SupportExprNode(typeof(String)));
            try
            {
                andNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // test failed - with just one child
            andNode = new ExprAndNode();
            andNode.AddChildNode(new SupportExprNode(typeof(bool)));
            try
            {
                andNode.Validate(null, null);
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
            andNode.AddChildNode(new SupportBoolExprNode(true));
            andNode.AddChildNode(new SupportBoolExprNode(true));
            Assert.IsTrue((bool)andNode.Evaluate(null));

            andNode = new ExprAndNode();
            andNode.AddChildNode(new SupportBoolExprNode(true));
            andNode.AddChildNode(new SupportBoolExprNode(false));
            Assert.IsFalse((bool)andNode.Evaluate(null));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            andNode.AddChildNode(new SupportExprNode(true));
            andNode.AddChildNode(new SupportExprNode(false));

            Assert.AreEqual("(True AND False)", andNode.ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(andNode.EqualsNode(new ExprAndNode()));
            Assert.IsFalse(andNode.EqualsNode(new ExprOrNode()));
        }
    }
}