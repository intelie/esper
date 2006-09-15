package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.eql.spec.SelectExprElementSpec;
import net.esper.type.ArithTypeEnum;

import java.util.List;
import java.util.LinkedList;

public class SupportSelectExprFactory
{
    public static List<SelectExprElementSpec> makeInvalidSelectList() throws Exception
    {
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
        selectionList.add(new SelectExprElementSpec(node, null));
        return selectionList;
    }

    public static List<SelectExprElementSpec> makeSelectListFromIdent(String propertyName, String streamName) throws Exception
    {
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode(propertyName, streamName);
        selectionList.add(new SelectExprElementSpec(identNode, null));
        return selectionList;
    }

    public static List<SelectExprElementSpec> makeNoAggregateSelectList() throws Exception
    {
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementSpec(identNode, null));
        selectionList.add(new SelectExprElementSpec(mathNode, "result"));
        return selectionList;
    }

    public static List<SelectExprElementSpec> makeAggregateSelectListWithProps() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        selectionList.add(new SelectExprElementSpec(top, null));
        return selectionList;
    }

    public static List<SelectExprElementSpec> makeAggregatePlusNoAggregate() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        ExprNode identNode2 = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        selectionList.add(new SelectExprElementSpec(top, null));
        selectionList.add(new SelectExprElementSpec(identNode2, null));
        return selectionList;
    }

    public static List<SelectExprElementSpec> makeAggregateMixed() throws Exception
    {
        // make a "select doubleBoxed, sum(intPrimitive)" -equivalent
        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();

        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElementSpec(identNode, null));

        ExprNode top = new ExprSumNode(false);
        identNode = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0");
        top.addChildNode(identNode);
        selectionList.add(new SelectExprElementSpec(top, null));

        return selectionList;
    }

    public static List<SelectExprElementSpec> makeAggregateSelectListNoProps() throws Exception
    {
        /*
                                    top (*)
                  c1 (sum)                            c2 (10)
                  c1_1 (5)
        */

        ExprNode top = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        ExprNode c1 = new ExprSumNode(false);
        ExprNode c1_1 = new SupportExprNode(5);
        ExprNode c2 = new SupportExprNode(10);

        top.addChildNode(c1);
        top.addChildNode(c2);
        c1.addChildNode(c1_1);

        top.getValidatedSubtree(null, null);

        List<SelectExprElementSpec> selectionList = new LinkedList<SelectExprElementSpec>();
        selectionList.add(new SelectExprElementSpec(top, null));
        return selectionList;
    }
}
