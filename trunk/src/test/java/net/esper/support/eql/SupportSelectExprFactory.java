package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.type.ArithTypeEnum;

import java.util.List;
import java.util.LinkedList;

public class SupportSelectExprFactory
{
    public static List<SelectExprElement> makeInvalidSelectList() throws Exception
    {
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
        selectionList.add(new SelectExprElement(node, null));
        return selectionList;
    }

    public static List<SelectExprElement> makeSelectListFromIdent(String propertyName, String streamName) throws Exception
    {
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode(propertyName, streamName);
        selectionList.add(new SelectExprElement(identNode, null));
        return selectionList;
    }

    public static List<SelectExprElement> makeNoAggregateSelectList() throws Exception
    {
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElement(identNode, null));
        selectionList.add(new SelectExprElement(mathNode, "result"));
        return selectionList;
    }

    public static List<SelectExprElement> makeAggregateSelectListWithProps() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        selectionList.add(new SelectExprElement(top, null));
        return selectionList;
    }

    public static List<SelectExprElement> makeAggregatePlusNoAggregate() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        ExprNode identNode2 = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        selectionList.add(new SelectExprElement(top, null));
        selectionList.add(new SelectExprElement(identNode2, null));
        return selectionList;
    }

    public static List<SelectExprElement> makeAggregateMixed() throws Exception
    {
        // make a "select doubleBoxed, sum(intPrimitive)" -equivalent
        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();

        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElement(identNode, null));

        ExprNode top = new ExprSumNode(false);
        identNode = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0");
        top.addChildNode(identNode);
        selectionList.add(new SelectExprElement(top, null));

        return selectionList;
    }

    public static List<SelectExprElement> makeAggregateSelectListNoProps() throws Exception
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

        top.validateDescendents(null);

        List<SelectExprElement> selectionList = new LinkedList<SelectExprElement>();
        selectionList.add(new SelectExprElement(top, null));
        return selectionList;
    }
}
