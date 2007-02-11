using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprNotNode
    {
        private ExprNotNode notNode;

        [SetUp]
        public virtual void setUp()
        {
            notNode = new ExprNotNode();
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(bool?), notNode.ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            // fails with zero expressions
            try
            {
                notNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // fails with too many sub-expressions
            notNode.AddChildNode(new SupportExprNode(typeof(bool)));
            notNode.AddChildNode(new SupportExprNode(typeof(bool)));
            try
            {
                notNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // test failure, type mismatch
            notNode = new ExprNotNode();
            notNode.AddChildNode(new SupportExprNode(typeof(String)));
            try
            {
                notNode.validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }

            // validates
            notNode = new ExprNotNode();
            notNode.AddChildNode(new SupportExprNode(typeof(bool)));
            notNode.validate(null, null);
        }

        [Test]
        public virtual void testEvaluate()
        {
            notNode.AddChildNode(new SupportBoolExprNode(true));
            Assert.IsFalse((bool)notNode.Evaluate(null));

            notNode = new ExprNotNode();
            notNode.AddChildNode(new SupportBoolExprNode(false));
            Assert.IsTrue((bool)notNode.Evaluate(null));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            notNode.AddChildNode(new SupportExprNode(true));
            Assert.AreEqual("NOT(True)", notNode.ExpressionString);
        }

        [Test]
        public virtual void testEqualsNode()
        {
            Assert.IsTrue(notNode.EqualsNode(notNode));
            Assert.IsFalse(notNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
            Assert.IsFalse(notNode.EqualsNode(new ExprOrNode()));
            Assert.IsTrue(notNode.EqualsNode(new ExprNotNode()));
        }
    }
}