using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.type;

namespace net.esper.support.eql
{
    public class SupportSelectExprFactory
    {
        public static IList<SelectExprElementUnnamedSpec> makeInvalidSelectList()
        {
            IList<SelectExprElementUnnamedSpec> selectionList = new List<SelectExprElementUnnamedSpec>();
            ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
            selectionList.Add(new SelectExprElementUnnamedSpec(node, null));
            return selectionList;
        }

        public static IList<SelectExprElementNamedSpec> makeSelectListFromIdent(String propertyName, String streamName)
        {
            IList<SelectExprElementNamedSpec> selectionList = new List<SelectExprElementNamedSpec>();
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode(propertyName, streamName);
            selectionList.Add(new SelectExprElementNamedSpec(identNode, "propertyName"));
            return selectionList;
        }

        public static IList<SelectExprElementNamedSpec> makeNoAggregateSelectList()
        {
            IList<SelectExprElementNamedSpec> selectionList = new List<SelectExprElementNamedSpec>();
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
            selectionList.Add(new SelectExprElementNamedSpec(identNode, "resultOne"));
            selectionList.Add(new SelectExprElementNamedSpec(mathNode, "resultTwo"));
            return selectionList;
        }

        public static IList<SelectExprElementUnnamedSpec> makeNoAggregateSelectListUnnamed()
        {
            IList<SelectExprElementUnnamedSpec> selectionList = new List<SelectExprElementUnnamedSpec>();
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
            selectionList.Add(new SelectExprElementUnnamedSpec(identNode, null));
            selectionList.Add(new SelectExprElementUnnamedSpec(mathNode, "result"));
            return selectionList;
        }

        public static IList<SelectExprElementUnnamedSpec> makeAggregateSelectListWithProps()
        {
            ExprNode top = new ExprSumNode(false);
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            top.AddChildNode(identNode);

            IList<SelectExprElementUnnamedSpec> selectionList = new List<SelectExprElementUnnamedSpec>();
            selectionList.Add(new SelectExprElementUnnamedSpec(top, null));
            return selectionList;
        }

        public static IList<SelectExprElementUnnamedSpec> makeAggregatePlusNoAggregate()
        {
            ExprNode top = new ExprSumNode(false);
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            top.AddChildNode(identNode);

            ExprNode identNode2 = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

            IList<SelectExprElementUnnamedSpec> selectionList = new List<SelectExprElementUnnamedSpec>();
            selectionList.Add(new SelectExprElementUnnamedSpec(top, null));
            selectionList.Add(new SelectExprElementUnnamedSpec(identNode2, null));
            return selectionList;
        }

        public static IList<SelectExprElementUnnamedSpec> makeAggregateMixed()
        {
            // make a "select doubleBoxed, sum(intPrimitive)" -equivalent
            IList<SelectExprElementUnnamedSpec> selectionList = new List<SelectExprElementUnnamedSpec>();

            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            selectionList.Add(new SelectExprElementUnnamedSpec(identNode, null));

            ExprNode top = new ExprSumNode(false);
            identNode = SupportExprNodeFactory.makeIdentNode("IntPrimitive", "s0");
            top.AddChildNode(identNode);
            selectionList.Add(new SelectExprElementUnnamedSpec(top, null));

            return selectionList;
        }

        public static IList<SelectExprElementUnnamedSpec> makeAggregateSelectListNoProps()
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

            top.GetValidatedSubtree(null, null);

            IList<SelectExprElementUnnamedSpec> selectionList = new List<SelectExprElementUnnamedSpec>();
            selectionList.Add(new SelectExprElementUnnamedSpec(top, null));
            return selectionList;
        }
    }
}
