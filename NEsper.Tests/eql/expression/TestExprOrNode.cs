using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprOrNode
    {
        private ExprOrNode orNode;

        [SetUp]
        public virtual void setUp()
        {
            orNode = new ExprOrNode();
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(bool?), orNode.ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            // test success
            orNode.AddChildNode(new SupportExprNode(typeof(bool)));
            orNode.AddChildNode(new SupportExprNode(typeof(bool)));
            orNode.validate(null, null);

            // test failure, type mismatch
            orNode.AddChildNode(new SupportExprNode(typeof(String)));
            try
            {
                orNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // test failed - with just one child
            orNode = new ExprOrNode();
            orNode.AddChildNode(new SupportExprNode(typeof(bool)));
            try
            {
                orNode.validate(null, null);
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
            orNode.AddChildNode(new SupportBoolExprNode(true));
            orNode.AddChildNode(new SupportBoolExprNode(false));
            Assert.IsTrue((bool)orNode.Evaluate(null));

            orNode = new ExprOrNode();
            orNode.AddChildNode(new SupportBoolExprNode(false));
            orNode.AddChildNode(new SupportBoolExprNode(false));
            Assert.IsFalse((bool)orNode.Evaluate(null));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            orNode.AddChildNode(new SupportExprNode(true));
            orNode.AddChildNode(new SupportExprNode(false));
            Assert.AreEqual("(True OR False)", orNode.ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(orNode.EqualsNode(orNode));
            Assert.IsFalse(orNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
            Assert.IsTrue(orNode.EqualsNode(new ExprOrNode()));
        }
    }
}