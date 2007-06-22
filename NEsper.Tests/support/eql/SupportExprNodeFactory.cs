///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.type;
using net.esper.view;
using net.esper.view.window;

namespace net.esper.support.eql
{
	public class SupportExprNodeFactory
	{
	    public static ExprEqualsNode MakeEqualsNode()
	    {
	        ExprEqualsNode topNode = new ExprEqualsNode(false);
	        ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
	        ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
	        topNode.AddChildNode(i1_1);
	        topNode.AddChildNode(i1_2);

	        Validate(topNode);

	        return topNode;
	    }

	    public static ExprPreviousNode MakePreviousNode()
	    {
	        ExprPreviousNode prevNode = new ExprPreviousNode();
	        ExprNode indexNode = new ExprIdentNode("intPrimitive", "s1");
	        prevNode.AddChildNode(indexNode);
	        ExprNode propNode = new ExprIdentNode("doublePrimitive", "s1");
	        prevNode.AddChildNode(propNode);

	        Validate(prevNode);

	        return prevNode;
	    }

	    public static ExprPriorNode MakePriorNode()
	    {
	        ExprPriorNode priorNode = new ExprPriorNode();
	        ExprNode indexNode = new ExprConstantNode(1);
	        priorNode.AddChildNode(indexNode);
	        ExprNode propNode = new ExprIdentNode("doublePrimitive", "s1");
	        priorNode.AddChildNode(propNode);

	        Validate(priorNode);

	        return priorNode;
	    }

	    public static ExprAndNode Make2SubNodeAnd()
	    {
	        ExprAndNode topNode = new ExprAndNode();

	        ExprEqualsNode e1 = new ExprEqualsNode(false);
	        ExprEqualsNode e2 = new ExprEqualsNode(false);

	        topNode.AddChildNode(e1);
	        topNode.AddChildNode(e2);

	        ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
	        ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
	        e1.AddChildNode(i1_1);
	        e1.AddChildNode(i1_2);

	        ExprIdentNode i2_1 = new ExprIdentNode("string", "s1");
	        ExprIdentNode i2_2 = new ExprIdentNode("string", "s0");
	        e2.AddChildNode(i2_1);
	        e2.AddChildNode(i2_2);

	        Validate(topNode);

	        return topNode;
	    }

	    public static ExprNode Make3SubNodeAnd()
	    {
	        ExprNode topNode = new ExprAndNode();

	        ExprEqualsNode[] equalNodes = new ExprEqualsNode[3];
	        for (int i = 0; i < equalNodes.Length; i++)
	        {
	            equalNodes[i] = new ExprEqualsNode(false);
	            topNode.AddChildNode(equalNodes[i]);
	        }

	        ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
	        ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
	        equalNodes[0].AddChildNode(i1_1);
	        equalNodes[0].AddChildNode(i1_2);

	        ExprIdentNode i2_1 = new ExprIdentNode("string", "s1");
	        ExprIdentNode i2_2 = new ExprIdentNode("string", "s0");
	        equalNodes[1].AddChildNode(i2_1);
	        equalNodes[1].AddChildNode(i2_2);

	        ExprIdentNode i3_1 = new ExprIdentNode("boolBoxed", "s0");
	        ExprIdentNode i3_2 = new ExprIdentNode("boolPrimitive", "s1");
	        equalNodes[2].AddChildNode(i3_1);
	        equalNodes[2].AddChildNode(i3_2);

	        Validate(topNode);

	        return topNode;
	    }

	    public static ExprNode MakeIdentNode(String fieldName, String streamName)
	    {
	        ExprIdentNode node = new ExprIdentNode(fieldName, streamName);
	        Validate(node);
	        return node;
	    }

	    public static ExprNode MakeMathNode()
	    {
	        ExprIdentNode node1 = new ExprIdentNode("intBoxed", "s0");
	        ExprIdentNode node2 = new ExprIdentNode("intPrimitive", "s0");
	        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
	        mathNode.AddChildNode(node1);
	        mathNode.AddChildNode(node2);

	        Validate(mathNode);

	        return mathNode;
	    }

	    public static ExprNode MakeMathNode(MathArithTypeEnum operator_, Object valueLeft_, Object valueRight_)
	    {
	        ExprMathNode mathNode = new ExprMathNode(operator_);
	        mathNode.AddChildNode(new SupportExprNode(valueLeft_));
	        mathNode.AddChildNode(new SupportExprNode(valueRight_));
	        Validate(mathNode);
	        return mathNode;
	    }

	    public static ExprNode MakeSumAndFactorNode()
	    {
	        // sum node
	        ExprSumNode sum = new ExprSumNode(false);
	        ExprIdentNode ident = new ExprIdentNode("intPrimitive", "s0");
	        sum.AddChildNode(ident);

	        ExprIdentNode node = new ExprIdentNode("intBoxed", "s0");
	        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
	        mathNode.AddChildNode(node);
	        mathNode.AddChildNode(sum);

	        Validate(mathNode);

	        return mathNode;
	    }

	    public static ExprAggregateNode MakeSumAggregateNode()
	    {
	        ExprSumNode top = new ExprSumNode(false);
	        ExprIdentNode ident = new ExprIdentNode("intPrimitive", "s0");
	        top.AddChildNode(ident);

	        Validate(top);

	        return top;
	    }

	    public static ExprNode MakeCountNode(Object value, Type type)
	    {
	        ExprCountNode countNode = new ExprCountNode(false);
	        countNode.AddChildNode(new SupportExprNode(value, type));
	        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
	        countNode.SetAggregationResultFuture(future, 1);
	        Validate(countNode);
	        return countNode;
	    }

	    public static ExprNode MakeRelationalOpNode(RelationalOpEnum operator_, Object valueLeft_, Type typeLeft_, Object valueRight_, Type typeRight_)
	    {
	        ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
	        opNode.AddChildNode(new SupportExprNode(valueLeft_, typeLeft_));
	        opNode.AddChildNode(new SupportExprNode(valueRight_, typeRight_));
	        Validate(opNode);
	        return opNode;
	    }

	    public static ExprNode MakeRelationalOpNode(RelationalOpEnum operator_, Type typeLeft_, Type typeRight_)
	    {
	        ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
	        opNode.AddChildNode(new SupportExprNode(typeLeft_));
	        opNode.AddChildNode(new SupportExprNode(typeRight_));
	        Validate(opNode);
	        return opNode;
	    }

	    public static ExprNode MakeRelationalOpNode(RelationalOpEnum operator_, ExprNode nodeLeft_, ExprNode nodeRight_)
	    {
	        ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
	        opNode.AddChildNode(nodeLeft_);
	        opNode.AddChildNode(nodeRight_);
	        Validate(opNode);
	        return opNode;
	    }

	    public static ExprInNode MakeInSetNode(bool isNotIn)
	    {
	        // Build :      s0.intPrimitive in (1, 2)
	        ExprInNode inNode = new ExprInNode(isNotIn);
	        inNode.AddChildNode(MakeIdentNode("intPrimitive","s0"));
	        inNode.AddChildNode(new SupportExprNode(1));
	        inNode.AddChildNode(new SupportExprNode(2));
	        Validate(inNode);
	        return inNode;
	    }

	    public static ExprRegexpNode MakeRegexpNode(bool isNot)
	    {
	        // Build :      s0.string regexp "[a-z][a-z]"  (with not)
	        ExprRegexpNode node = new ExprRegexpNode(isNot);
            node.AddChildNode(MakeIdentNode("string", "s0"));
	        node.AddChildNode(new SupportExprNode("[a-z][a-z]"));
	        Validate(node);
	        return node;
	    }

	    public static ExprLikeNode MakeLikeNode(bool isNot, String optionalEscape)
	    {
	        // Build :      s0.string like "%abc__"  (with or witout escape)
	        ExprLikeNode node = new ExprLikeNode(isNot);
            node.AddChildNode(MakeIdentNode("string", "s0"));
	        node.AddChildNode(new SupportExprNode("%abc__"));
	        if (optionalEscape != null)
	        {
	            node.AddChildNode(new SupportExprNode(optionalEscape));
	        }
	        Validate(node);
	        return node;
	    }

	    public static ExprCaseNode MakeCaseSyntax1Node()
	    {
	        // Build (case 1 expression):
	        // case when s0.intPrimitive = 1 then "a"
	        //      when s0.intPrimitive = 2 then "b"
	        //      else "c"
	        // end
	        ExprCaseNode caseNode = new ExprCaseNode(false);

	        ExprNode node = MakeEqualsNode("intPrimitive", "s0", 1);
	        caseNode.AddChildNode(node);
	        caseNode.AddChildNode(new SupportExprNode("a"));

	        node = MakeEqualsNode("intPrimitive", "s0", 2);
	        caseNode.AddChildNode(node);
	        caseNode.AddChildNode(new SupportExprNode("b"));

	        caseNode.AddChildNode(new SupportExprNode("c"));

	        Validate(caseNode);

	        return caseNode;
	    }

	    public static ExprCaseNode MakeCaseSyntax2Node()
	    {
	        // Build (case 2 expression):
	        // case s0.intPrimitive
	        //   when 1 then "a"
	        //   when 2 then "b"
	        //   else "c"
	        // end
	        ExprCaseNode caseNode = new ExprCaseNode(true);
            caseNode.AddChildNode(MakeIdentNode("intPrimitive", "s0"));

	        caseNode.AddChildNode(new SupportExprNode(1));
	        caseNode.AddChildNode(new SupportExprNode("a"));
	        caseNode.AddChildNode(new SupportExprNode(2));
	        caseNode.AddChildNode(new SupportExprNode("b"));
	        caseNode.AddChildNode(new SupportExprNode("c"));

	        Validate(caseNode);

	        return (caseNode);
	    }

	    private static ExprEqualsNode MakeEqualsNode(String ident1, String stream1, Object value)
	    {
	        ExprEqualsNode topNode = new ExprEqualsNode(false);
	        ExprIdentNode i1_1 = new ExprIdentNode(ident1, stream1);
	        SupportExprNode constantNode = new SupportExprNode(value);
	        topNode.AddChildNode(i1_1);
	        topNode.AddChildNode(constantNode);
	        return topNode;
	    }

	    public static void Validate(ExprNode topNode)
	    {
	        SupportStreamTypeSvc3Stream streamTypeService = new SupportStreamTypeSvc3Stream();

	        ViewFactoryChain[] factoriesPerStream = new ViewFactoryChain[3];
	        for (int i = 0; i < factoriesPerStream.Length; i++)
	        {
	            IList<ViewFactory> factories = new List<ViewFactory>();
	            factories.Add(new LengthWindowViewFactory());
	            factoriesPerStream[i] = new ViewFactoryChain(streamTypeService.EventTypes[i], factories);
	        }
	        ViewResourceDelegateImpl viewResources = new ViewResourceDelegateImpl(factoriesPerStream);

	        topNode.GetValidatedSubtree(streamTypeService, new MethodResolutionServiceImpl(null), viewResources);
	    }
	}
} // End of namespace
