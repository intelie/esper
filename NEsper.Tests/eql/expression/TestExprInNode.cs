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
    public class TestExprInNode
    {
        private ExprInNode inNodeNormal;
        private ExprInNode inNodeNotIn;

        [SetUp]
        public virtual void setUp()
        {
            inNodeNormal = SupportExprNodeFactory.makeInSetNode(false);
            inNodeNotIn = SupportExprNodeFactory.makeInSetNode(true);
        }

        [Test]
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(bool?), inNodeNormal.ReturnType);
            Assert.AreEqual(typeof(bool?), inNodeNotIn.ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            inNodeNormal = SupportExprNodeFactory.makeInSetNode(true);
            inNodeNormal.Validate(null, null);

            // No subnodes: Exception is thrown.
            tryInvalidValidate(new ExprInNode(true));

            // singe child node not possible, must be 2 at least
            inNodeNormal = new ExprInNode(true);
            inNodeNormal.AddChildNode(new SupportExprNode((Object)4));
            tryInvalidValidate(inNodeNormal);

            // test a type mismatch
            inNodeNormal = new ExprInNode(true);
            inNodeNormal.AddChildNode(new SupportExprNode("sx"));
            inNodeNormal.AddChildNode(new SupportExprNode(4));
            tryInvalidValidate(inNodeNormal);
        }

        [Test]
        public virtual void testEvaluate()
        {
            Assert.IsFalse((bool)inNodeNormal.Evaluate(MakeEvent(0)));
            Assert.IsTrue((bool)inNodeNormal.Evaluate(MakeEvent(1)));
            Assert.IsTrue((bool)inNodeNormal.Evaluate(MakeEvent(2)));
            Assert.IsFalse((bool)inNodeNormal.Evaluate(MakeEvent(3)));

            Assert.IsTrue((bool)inNodeNotIn.Evaluate(MakeEvent(0)));
            Assert.IsFalse((bool)inNodeNotIn.Evaluate(MakeEvent(1)));
            Assert.IsFalse((bool)inNodeNotIn.Evaluate(MakeEvent(2)));
            Assert.IsTrue((bool)inNodeNotIn.Evaluate(MakeEvent(3)));
        }

        [Test]
        public virtual void testEquals()
        {
            ExprInNode otherInNodeNormal = SupportExprNodeFactory.makeInSetNode(false);
            ExprInNode otherInNodeNotIn = SupportExprNodeFactory.makeInSetNode(true);

            Assert.IsTrue(inNodeNormal.EqualsNode(otherInNodeNormal));
            Assert.IsTrue(inNodeNotIn.EqualsNode(otherInNodeNotIn));

            Assert.IsFalse(inNodeNormal.EqualsNode(otherInNodeNotIn));
            Assert.IsFalse(inNodeNotIn.EqualsNode(otherInNodeNormal));
            Assert.IsFalse(inNodeNotIn.EqualsNode(SupportExprNodeFactory.makeCaseSyntax1Node()));
            Assert.IsFalse(inNodeNormal.EqualsNode(SupportExprNodeFactory.makeCaseSyntax1Node()));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            Assert.AreEqual("s0.intPrimitive in (1,2)", inNodeNormal.ExpressionString);
            Assert.AreEqual("s0.intPrimitive not in (1,2)", inNodeNotIn.ExpressionString);
        }

        private EventBean[] MakeEvent(int intPrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intPrimitive;
            return new EventBean[] { SupportEventBeanFactory.createObject(_event) };
        }

        private void tryInvalidValidate(ExprInNode exprInNode)
        {
            try
            {
                exprInNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }
        }
    }
}