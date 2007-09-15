package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.eql.spec.SelectExprElementRawSpec;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.type.MathArithTypeEnum;

import java.util.List;
import java.util.LinkedList;

public class SupportSelectExprFactory
{
    public static List<SelectExprElementRawSpec> makeInvalidSelectList() throws Exception
    {
        List<SelectExprElementRawSpec> selectionList = new LinkedList<SelectExprElementRawSpec>();
        ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
        selectionList.add(new SelectExprElementRawSpec(node, null));
        return selectionList;
    }

    public static List<SelectExprElementCompiledSpec> makeSelectListFromIdent(String propertyName, String streamName) throws Exception
    {
        List<SelectExprElementCompiledSpec> selectionList = new LinkedList<SelectExprElementCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode(propertyName, streamName);
        selectionList.add(new SelectExprElementCompiledSpec(identNode, "propertyName"));
        return selectionList;
    }

    public static List<SelectExprElementCompiledSpec> makeNoAggregateSelectList() throws Exception
    {
        List<SelectExprElementCompiledSpec> selectionList = new LinkedList<SelectExprElementCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementCompiledSpec(identNode, "resultOne"));
        selectionList.add(new SelectExprElementCompiledSpec(mathNode, "resultTwo"));
        return selectionList;
    }

    public static List<SelectExprElementRawSpec> makeNoAggregateSelectListUnnamed() throws Exception
    {
        List<SelectExprElementRawSpec> selectionList = new LinkedList<SelectExprElementRawSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectExprElementRawSpec(identNode, null));
        selectionList.add(new SelectExprElementRawSpec(mathNode, "result"));
        return selectionList;
    }

    public static List<SelectExprElementRawSpec> makeAggregateSelectListWithProps() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        List<SelectExprElementRawSpec> selectionList = new LinkedList<SelectExprElementRawSpec>();
        selectionList.add(new SelectExprElementRawSpec(top, null));
        return selectionList;
    }

    public static List<SelectExprElementRawSpec> makeAggregatePlusNoAggregate() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        ExprNode identNode2 = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        List<SelectExprElementRawSpec> selectionList = new LinkedList<SelectExprElementRawSpec>();
        selectionList.add(new SelectExprElementRawSpec(top, null));
        selectionList.add(new SelectExprElementRawSpec(identNode2, null));
        return selectionList;
    }

    public static List<SelectExprElementRawSpec> makeAggregateMixed() throws Exception
    {
        // make a "select doubleBoxed, sum(intPrimitive)" -equivalent
        List<SelectExprElementRawSpec> selectionList = new LinkedList<SelectExprElementRawSpec>();

        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectExprElementRawSpec(identNode, null));

        ExprNode top = new ExprSumNode(false);
        identNode = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0");
        top.addChildNode(identNode);
        selectionList.add(new SelectExprElementRawSpec(top, null));

        return selectionList;
    }

    public static List<SelectExprElementRawSpec> makeAggregateSelectListNoProps() throws Exception
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

        top.getValidatedSubtree(null, null, null, null);

        List<SelectExprElementRawSpec> selectionList = new LinkedList<SelectExprElementRawSpec>();
        selectionList.add(new SelectExprElementRawSpec(top, null));
        return selectionList;
    }
}
