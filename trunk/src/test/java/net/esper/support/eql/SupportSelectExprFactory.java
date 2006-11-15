package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.eql.spec.SelectExprElementUnnamedSpec;
import net.esper.eql.spec.SelectExprElementNamedSpec;
import net.esper.type.MathArithTypeEnum;

import java.util.List;
import java.util.LinkedList;

public class SupportSelectExprFactory
{
    public static List<SelectExprElementUnnamedSpec> makeInvalidSelectList() throws Exception
    {
        List<SelectExprElementUnnamedSpec> selectionList = new LinkedList<SelectExprElementUnnamedSpec>();
        ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
        selectionList.add(new SelectExprElementUnnamedSpec(node, null));
        return selectionList;
    }

    public static List<SelectExprElementNamedSpec> makeSelectListFromIdent(String propertyName, String streamName) throws Exception
    {
        List<SelectExprElementNamedSpec> selectionList = new LinkedList<SelectExprElementNamedSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode(propertyName, streamName);
        selectionList.add(new SelectExprElementNamedSpec(identNode, "propertyName"));
        return selectionList;
    }

    public static List<SelectExprElementNamedSpec> makeNoAggregateSelectList() throws Exception
    {
        List<SelectExprElementNamedSpec> selectionList = new LinkedList<SelectExprElementNamedSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementNamedSpec(identNode, "resultOne"));
        selectionList.add(new SelectExprElementNamedSpec(mathNode, "resultTwo"));
        return selectionList;
    }

    public static List<SelectExprElementUnnamedSpec> makeNoAggregateSelectListUnnamed() throws Exception
    {
        List<SelectExprElementUnnamedSpec> selectionList = new LinkedList<SelectExprElementUnnamedSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementUnnamedSpec(identNode, null));
        selectionList.add(new SelectExprElementUnnamedSpec(mathNode, "result"));
        return selectionList;
    }

    public static List<SelectExprElementUnnamedSpec> makeAggregateSelectListWithProps() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        List<SelectExprElementUnnamedSpec> selectionList = new LinkedList<SelectExprElementUnnamedSpec>();
        selectionList.add(new SelectExprElementUnnamedSpec(top, null));
        return selectionList;
    }

    public static List<SelectExprElementUnnamedSpec> makeAggregatePlusNoAggregate() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        ExprNode identNode2 = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        List<SelectExprElementUnnamedSpec> selectionList = new LinkedList<SelectExprElementUnnamedSpec>();
        selectionList.add(new SelectExprElementUnnamedSpec(top, null));
        selectionList.add(new SelectExprElementUnnamedSpec(identNode2, null));
        return selectionList;
    }

    public static List<SelectExprElementUnnamedSpec> makeAggregateMixed() throws Exception
    {
        // make a "select doubleBoxed, sum(intPrimitive)" -equivalent
        List<SelectExprElementUnnamedSpec> selectionList = new LinkedList<SelectExprElementUnnamedSpec>();

        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElementUnnamedSpec(identNode, null));

        ExprNode top = new ExprSumNode(false);
        identNode = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0");
        top.addChildNode(identNode);
        selectionList.add(new SelectExprElementUnnamedSpec(top, null));

        return selectionList;
    }

    public static List<SelectExprElementUnnamedSpec> makeAggregateSelectListNoProps() throws Exception
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

        top.addChildNode(c1);
        top.addChildNode(c2);
        c1.addChildNode(c1_1);

        top.getValidatedSubtree(null, null);

        List<SelectExprElementUnnamedSpec> selectionList = new LinkedList<SelectExprElementUnnamedSpec>();
        selectionList.add(new SelectExprElementUnnamedSpec(top, null));
        return selectionList;
    }
}
