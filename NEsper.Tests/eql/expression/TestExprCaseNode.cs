using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprCaseNode
    {
        [Test]
        public virtual void testGetType()
        {
            // Template expression is:
            // case when (so.floatPrimitive>s1.shortBoxed) then count(5) when (so.longPrimitive>s1.intPrimitive) then (25 + 130.5) else (3*3) end
            ExprCaseNode caseNode = SupportExprNodeFactory.makeCaseSyntax1Node();
            Assert.AreEqual(typeof(String), caseNode.ReturnType);

            // case when (2.5>2) then count(5) when (1>3) then (25 + 130.5) else (3*3) end
            // First when node is true, case node type is the first when node type.
            caseNode = SupportExprNodeFactory.makeCaseSyntax2Node();
            Assert.AreEqual(typeof(String), caseNode.ReturnType);
        }

        [Test]
        public virtual void testValidate()
        {
            ExprCaseNode caseNode = SupportExprNodeFactory.makeCaseSyntax1Node();
            caseNode.Validate(null, null);

            caseNode = SupportExprNodeFactory.makeCaseSyntax2Node();
            caseNode.Validate(null, null);

            // No subnodes: Exception is thrown.
            tryInvalidValidate(new ExprCaseNode(false));
            tryInvalidValidate(new ExprCaseNode(true));

            // singe child node not possible, must be 2 at least
            caseNode = new ExprCaseNode(false);
            caseNode.AddChildNode(new SupportExprNode((Object)4));
            tryInvalidValidate(caseNode);

            // in a case 1 expression (e.g. case when a=b then 1 else 2) the when child nodes must return boolean
            caseNode.AddChildNode(new SupportExprNode((Object)2));
            tryInvalidValidate(caseNode);

            // in a case 2 expression (e.g. case a when b then 1 else 2) then a and b types must be comparable
            caseNode = new ExprCaseNode(true);
            caseNode.AddChildNode(new SupportExprNode("a"));
            caseNode.AddChildNode(new SupportExprNode(1));
            caseNode.AddChildNode(new SupportExprNode(2));
            tryInvalidValidate(caseNode);
        }

        [Test]
        public virtual void testEvaluate()
        {
            ExprCaseNode caseNode = SupportExprNodeFactory.makeCaseSyntax1Node();
            caseNode.Validate(null, null);

            Assert.AreEqual("a", caseNode.Evaluate(MakeEvent(1)));
            Assert.AreEqual("b", caseNode.Evaluate(MakeEvent(2)));
            Assert.AreEqual("c", caseNode.Evaluate(MakeEvent(3)));

            caseNode = SupportExprNodeFactory.makeCaseSyntax2Node();
            caseNode.Validate(null, null);

            Assert.AreEqual("a", caseNode.Evaluate(MakeEvent(1)));
            Assert.AreEqual("b", caseNode.Evaluate(MakeEvent(2)));
            Assert.AreEqual("c", caseNode.Evaluate(MakeEvent(3)));
        }

        [Test]
        public virtual void testEquals()
        {
            ExprCaseNode caseNode = SupportExprNodeFactory.makeCaseSyntax1Node();
            ExprCaseNode otherCaseNode = SupportExprNodeFactory.makeCaseSyntax1Node();
            ExprCaseNode caseNodeSyntax2 = SupportExprNodeFactory.makeCaseSyntax2Node();
            ExprCaseNode otherCaseNodeSyntax2 = SupportExprNodeFactory.makeCaseSyntax2Node();

            Assert.IsTrue(caseNode.EqualsNode(otherCaseNode));
            Assert.IsTrue(otherCaseNode.EqualsNode(caseNode));
            Assert.IsFalse(caseNode.EqualsNode(caseNodeSyntax2));
            Assert.IsFalse(caseNodeSyntax2.EqualsNode(caseNode));
            Assert.IsTrue(caseNodeSyntax2.EqualsNode(otherCaseNodeSyntax2));
        }

        [Test]
        public virtual void testToExpressionString()
        {
            ExprCaseNode _caseNode = SupportExprNodeFactory.makeCaseSyntax1Node();
            Assert.AreEqual("case when s0.intPrimitive = 1 then \"a\" when s0.intPrimitive = 2 then \"b\" else \"c\" end", _caseNode.ExpressionString);

            _caseNode = SupportExprNodeFactory.makeCaseSyntax2Node();
            Assert.AreEqual("case s0.intPrimitive when 1 then \"a\" when 2 then \"b\" else \"c\" end", _caseNode.ExpressionString);
        }

        private void tryInvalidValidate(ExprCaseNode exprCaseNode)
        {
            try
            {
                exprCaseNode.Validate(null, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }
        }

        private EventBean[] MakeEvent(int intPrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intPrimitive;
            return new EventBean[] { SupportEventBeanFactory.createObject(_event) };
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}