///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprCaseNode
	{
	    [Test]
	    public void TestGetType()
	    {
	        // Template expression is:
	        // case when (so.floatPrimitive>s1.shortBoxed) then Count(5) when (so.LongPrimitive>s1.intPrimitive) then (25 + 130.5) else (3*3) end
	        ExprCaseNode caseNode = SupportExprNodeFactory.MakeCaseSyntax1Node();
	        Assert.AreEqual(typeof(String), caseNode.GetType());

	        // case when (2.5>2) then Count(5) when (1>3) then (25 + 130.5) else (3*3) end
	        // First when node is true, case node type is the first when node type.
	        caseNode = SupportExprNodeFactory.MakeCaseSyntax2Node();
	        Assert.AreEqual(typeof(String), caseNode.GetType());
	    }

	    [Test]
	    public void TestValidate()
	    {
	        ExprCaseNode caseNode = SupportExprNodeFactory.MakeCaseSyntax1Node();
	        caseNode.Validate(null, null, null);

	        caseNode = SupportExprNodeFactory.MakeCaseSyntax2Node();
	        caseNode.Validate(null, null, null);

	        // No subnodes: Exception is thrown.
	        TryInvalidValidate(new ExprCaseNode(false));
	        TryInvalidValidate(new ExprCaseNode(true));

	        // singe child node not possible, must be 2 at least
	        caseNode = new ExprCaseNode(false);
	        caseNode.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(caseNode);

	        // in a case 1 expression (e.g. case when a=b then 1 else 2) the when child nodes must return bool
	        caseNode.AddChildNode(new SupportExprNode(2));
	        TryInvalidValidate(caseNode);

	        // in a case 2 expression (e.g. case a when b then 1 else 2) then a and b types must be comparable
	        caseNode = new ExprCaseNode(true);
	        caseNode.AddChildNode(new SupportExprNode("a"));
	        caseNode.AddChildNode(new SupportExprNode(1));
	        caseNode.AddChildNode(new SupportExprNode(2));
	        TryInvalidValidate(caseNode);
	    }

	    [Test]
	    public void TestEvaluate()
	    {
	        ExprCaseNode caseNode = SupportExprNodeFactory.MakeCaseSyntax1Node();
	        caseNode.Validate(null, null, null);

	        Assert.AreEqual("a", caseNode.Evaluate(MakeEvent(1), false));
	        Assert.AreEqual("b", caseNode.Evaluate(MakeEvent(2), false));
	        Assert.AreEqual("c", caseNode.Evaluate(MakeEvent(3), false));

	        caseNode = SupportExprNodeFactory.MakeCaseSyntax2Node();
	        caseNode.Validate(null, null, null);

	        Assert.AreEqual("a", caseNode.Evaluate(MakeEvent(1), false));
	        Assert.AreEqual("b", caseNode.Evaluate(MakeEvent(2), false));
	        Assert.AreEqual("c", caseNode.Evaluate(MakeEvent(3), false));
	    }

	    [Test]
	    public void TestEquals()
	    {
	        ExprCaseNode caseNode = SupportExprNodeFactory.MakeCaseSyntax1Node();
	        ExprCaseNode otherCaseNode = SupportExprNodeFactory.MakeCaseSyntax1Node();
	        ExprCaseNode caseNodeSyntax2 = SupportExprNodeFactory.MakeCaseSyntax2Node();
	        ExprCaseNode otherCaseNodeSyntax2 = SupportExprNodeFactory.MakeCaseSyntax2Node();

	        Assert.IsTrue(caseNode.EqualsNode(otherCaseNode));
	        Assert.IsTrue(otherCaseNode.EqualsNode(caseNode));
	        Assert.IsFalse(caseNode.EqualsNode(caseNodeSyntax2));
	        Assert.IsFalse(caseNodeSyntax2.EqualsNode(caseNode));
	        Assert.IsTrue(caseNodeSyntax2.EqualsNode(otherCaseNodeSyntax2));
	    }

	    [Test]
	    public void TestToExpressionString()
	    {
	        ExprCaseNode _caseNode = SupportExprNodeFactory.MakeCaseSyntax1Node();
	        Assert.AreEqual("case when s0.intPrimitive = 1 then \"a\" when s0.intPrimitive = 2 then \"b\" else \"c\" end", _caseNode.ExpressionString);

	        _caseNode = SupportExprNodeFactory.MakeCaseSyntax2Node();
	        Assert.AreEqual("case s0.intPrimitive when 1 then \"a\" when 2 then \"b\" else \"c\" end", _caseNode.ExpressionString);
	    }

	    private void TryInvalidValidate(ExprCaseNode exprCaseNode)
	    {
	        try {
	            exprCaseNode.Validate(null, null, null);
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
	        _event.SetIntPrimitive(intPrimitive);
	        return new EventBean[] {SupportEventBeanFactory.CreateObject(_event)};
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
