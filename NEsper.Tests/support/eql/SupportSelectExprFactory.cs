///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.type;

namespace net.esper.support.eql
{
	public class SupportSelectExprFactory
	{
	    public static IList<SelectExprElementRawSpec> MakeInvalidSelectList()
	    {
	        IList<SelectExprElementRawSpec> selectionList = new List<SelectExprElementRawSpec>();
	        ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
	        selectionList.Add(new SelectExprElementRawSpec(node, null));
	        return selectionList;
	    }

	    public static IList<SelectExprElementCompiledSpec> MakeSelectListFromIdent(String propertyName, String streamName)
	    {
	        IList<SelectExprElementCompiledSpec> selectionList = new List<SelectExprElementCompiledSpec>();
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode(propertyName, streamName);
	        selectionList.Add(new SelectExprElementCompiledSpec(identNode, "propertyName"));
	        return selectionList;
	    }

	    public static IList<SelectExprElementCompiledSpec> MakeNoAggregateSelectList()
	    {
	        IList<SelectExprElementCompiledSpec> selectionList = new List<SelectExprElementCompiledSpec>();
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        ExprNode mathNode = SupportExprNodeFactory.MakeMathNode();
	        selectionList.Add(new SelectExprElementCompiledSpec(identNode, "resultOne"));
	        selectionList.Add(new SelectExprElementCompiledSpec(mathNode, "resultTwo"));
	        return selectionList;
	    }

        public static IList<SelectExprElementRawSpec> MakeNoAggregateSelectListUnnamed()
	    {
	        IList<SelectExprElementRawSpec> selectionList = new List<SelectExprElementRawSpec>();
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        ExprNode mathNode = SupportExprNodeFactory.MakeMathNode();
	        selectionList.Add(new SelectExprElementRawSpec(identNode, null));
	        selectionList.Add(new SelectExprElementRawSpec(mathNode, "result"));
	        return selectionList;
	    }

        public static IList<SelectExprElementRawSpec> MakeAggregateSelectListWithProps()
	    {
	        ExprNode top = new ExprSumNode(false);
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        top.AddChildNode(identNode);

            IList<SelectExprElementRawSpec> selectionList = new List<SelectExprElementRawSpec>();
	        selectionList.Add(new SelectExprElementRawSpec(top, null));
	        return selectionList;
	    }

        public static IList<SelectExprElementRawSpec> MakeAggregatePlusNoAggregate()
	    {
	        ExprNode top = new ExprSumNode(false);
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        top.AddChildNode(identNode);

	        ExprNode identNode2 = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");

            IList<SelectExprElementRawSpec> selectionList = new List<SelectExprElementRawSpec>();
	        selectionList.Add(new SelectExprElementRawSpec(top, null));
	        selectionList.Add(new SelectExprElementRawSpec(identNode2, null));
	        return selectionList;
	    }

        public static IList<SelectExprElementRawSpec> MakeAggregateMixed()
	    {
	        // make a "select doubleBoxed, Sum(intPrimitive)" -equivalent
            IList<SelectExprElementRawSpec> selectionList = new List<SelectExprElementRawSpec>();

	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        selectionList.Add(new SelectExprElementRawSpec(identNode, null));

	        ExprNode top = new ExprSumNode(false);
	        identNode = SupportExprNodeFactory.MakeIdentNode("intPrimitive", "s0");
	        top.AddChildNode(identNode);
	        selectionList.Add(new SelectExprElementRawSpec(top, null));

	        return selectionList;
	    }

        public static IList<SelectExprElementRawSpec> MakeAggregateSelectListNoProps()
	    {
	        /*
	                                    top (*)
	                  c1 (sum)                            c2 (10)
	                  c1_1 (5)
	        */

	        ExprNode top = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
	        ExprNode c1 = new ExprSumNode(false);
	        ExprNode c1_1 = new SupportExprNode(5);
	        ExprNode c2 = new SupportExprNode(10);

	        top.AddChildNode(c1);
	        top.AddChildNode(c2);
	        c1.AddChildNode(c1_1);

	        top.GetValidatedSubtree(null, null, null);

            IList<SelectExprElementRawSpec> selectionList = new List<SelectExprElementRawSpec>();
	        selectionList.Add(new SelectExprElementRawSpec(top, null));
	        return selectionList;
	    }
	}
} // End of namespace
