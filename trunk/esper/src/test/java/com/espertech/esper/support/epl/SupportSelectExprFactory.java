package com.espertech.esper.support.epl;

import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.spec.SelectClauseExprRawSpec;
import com.espertech.esper.epl.spec.SelectClauseExprCompiledSpec;
import com.espertech.esper.epl.spec.SelectClauseElementCompiled;
import com.espertech.esper.type.MathArithTypeEnum;

import java.util.List;
import java.util.LinkedList;

public class SupportSelectExprFactory
{
    public static List<SelectClauseElementCompiled> makeInvalidSelectList() throws Exception
    {
        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        ExprIdentNode node = new ExprIdentNode("xxxx", "s0");
        selectionList.add(new SelectClauseExprCompiledSpec(node, null));
        return selectionList;
    }

    public static List<SelectClauseExprCompiledSpec> makeSelectListFromIdent(String propertyName, String streamName) throws Exception
    {
        List<SelectClauseExprCompiledSpec> selectionList = new LinkedList<SelectClauseExprCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode(propertyName, streamName);
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, "propertyName"));
        return selectionList;
    }

    public static List<SelectClauseExprCompiledSpec> makeNoAggregateSelectList() throws Exception
    {
        List<SelectClauseExprCompiledSpec> selectionList = new LinkedList<SelectClauseExprCompiledSpec>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, "resultOne"));
        selectionList.add(new SelectClauseExprCompiledSpec(mathNode, "resultTwo"));
        return selectionList;
    }

    public static List<SelectClauseElementCompiled> makeNoAggregateSelectListUnnamed() throws Exception
    {
        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, null));
        selectionList.add(new SelectClauseExprCompiledSpec(mathNode, "result"));
        return selectionList;
    }

    public static List<SelectClauseElementCompiled> makeAggregateSelectListWithProps() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        selectionList.add(new SelectClauseExprCompiledSpec(top, null));
        return selectionList;
    }

    public static List<SelectClauseElementCompiled> makeAggregatePlusNoAggregate() throws Exception
    {
        ExprNode top = new ExprSumNode(false);
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        top.addChildNode(identNode);

        ExprNode identNode2 = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();
        selectionList.add(new SelectClauseExprCompiledSpec(top, null));
        selectionList.add(new SelectClauseExprCompiledSpec(identNode2, null));
        return selectionList;
    }

    public static List<SelectClauseElementCompiled> makeAggregateMixed() throws Exception
    {
        // make a "select doubleBoxed, sum(intPrimitive)" -equivalent
        List<SelectClauseElementCompiled> selectionList = new LinkedList<SelectClauseElementCompiled>();

        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
        selectionList.add(new SelectClauseExprCompiledSpec(identNode, null));

        ExprNode top = new ExprSumNode(false);
        identNode = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0");
        top.addChildNode(identNode);
        selectionList.add(new SelectClauseExprCompiledSpec(top, null));

        return selectionList;
    }

    public static List<SelectClauseExprRawSpec> makeAggregateSelectListNoProps() throws Exception
    {
        /*
                                    top (*)
                  c1 (sum)                            c2 (10)
                  c1_1 (5)
        */

        ExprNode top = new ExprMathNode(MathArithTypeEnum.MULTIPLY, false, false);
        ExprNode c1 = new ExprSumNode(false);
        ExprNode c1_1 = new SupportExprNode(5);
        ExprNode c2 = new SupportExprNode(10);

        top.addChildNode(c1);
        top.addChildNode(c2);
        c1.addChildNode(c1_1);

        top.getValidatedSubtree(null, null, null, null, null);

        List<SelectClauseExprRawSpec> selectionList = new LinkedList<SelectClauseExprRawSpec>();
        selectionList.add(new SelectClauseExprRawSpec(top, null));
        return selectionList;
    }
}
