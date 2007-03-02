using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprRegexpNode
    {
        private ExprRegexpNode regexpNodeNormal;
        private ExprRegexpNode regexpNodeNot;

        [SetUp]
        public virtual void setUp()
        {
            regexpNodeNormal = SupportExprNodeFactory.makeRegexpNode(false);
            regexpNodeNot = SupportExprNodeFactory.makeRegexpNode(true);
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(bool?), regexpNodeNormal.ReturnType);
            Assert.AreEqual(typeof(bool?), regexpNodeNot.ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            // No subnodes: Exception is thrown.
            tryInvalidValidate(new ExprRegexpNode(true));

            // singe child node not possible, must be 2 at least
            regexpNodeNormal = new ExprRegexpNode(false);
            regexpNodeNormal.AddChildNode(new SupportExprNode((Object)4));
            tryInvalidValidate(regexpNodeNormal);

            // test a type mismatch
            regexpNodeNormal = new ExprRegexpNode(true);
            regexpNodeNormal.AddChildNode(new SupportExprNode("sx"));
            regexpNodeNormal.AddChildNode(new SupportExprNode(4));
            tryInvalidValidate(regexpNodeNormal);

            // test numeric supported
            regexpNodeNormal = new ExprRegexpNode(false);
            regexpNodeNormal.AddChildNode(new SupportExprNode((Object)4));
            regexpNodeNormal.AddChildNode(new SupportExprNode("sx"));
        }

        [Test]
        public virtual void testEvaluate()
        {
            Assert.IsFalse((bool)regexpNodeNormal.Evaluate(MakeEvent("bcd")));
            Assert.IsTrue((bool)regexpNodeNormal.Evaluate(MakeEvent("ab")));
            Assert.IsTrue((bool)regexpNodeNot.Evaluate(MakeEvent("bcd")));
            Assert.IsFalse((bool)regexpNodeNot.Evaluate(MakeEvent("ab")));
        }

        [Test]
        public virtual void testEquals()
        {
            ExprRegexpNode otherRegexpNodeNot = SupportExprNodeFactory.makeRegexpNode(true);

            Assert.IsTrue(regexpNodeNot.EqualsNode(otherRegexpNodeNot));
            Assert.IsFalse(regexpNodeNormal.EqualsNode(otherRegexpNodeNot));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            Assert.AreEqual("s0.str regexp \"[a-z][a-z]\"", regexpNodeNormal.ExpressionString);
            Assert.AreEqual("s0.str not regexp \"[a-z][a-z]\"", regexpNodeNot.ExpressionString);
        }

        private EventBean[] MakeEvent(String stringValue)
        {
            SupportBean _event = new SupportBean();
            _event.str = stringValue;
            return new EventBean[] { SupportEventBeanFactory.createObject(_event) };
        }

        private void tryInvalidValidate(ExprRegexpNode exprLikeRegexpNode)
        {
            try
            {
                exprLikeRegexpNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }
        }
    }
}