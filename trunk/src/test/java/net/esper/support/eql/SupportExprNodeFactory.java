package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.type.ArithTypeEnum;

public class SupportExprNodeFactory
{
    public static ExprEqualsNode makeEqualsNode() throws Exception
    {
        ExprEqualsNode topNode = new ExprEqualsNode(false);
        ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
        ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
        topNode.addChildNode(i1_1);
        topNode.addChildNode(i1_2);

        validate(topNode);

        return topNode;
    }

    public static ExprAndNode make2SubNodeAnd() throws Exception
    {
        ExprAndNode topNode = new ExprAndNode();

        ExprEqualsNode e1 = new ExprEqualsNode(false);
        ExprEqualsNode e2 = new ExprEqualsNode(false);

        topNode.addChildNode(e1);
        topNode.addChildNode(e2);

        ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
        ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
        e1.addChildNode(i1_1);
        e1.addChildNode(i1_2);

        ExprIdentNode i2_1 = new ExprIdentNode("string", "s1");
        ExprIdentNode i2_2 = new ExprIdentNode("string", "s0");
        e2.addChildNode(i2_1);
        e2.addChildNode(i2_2);

        validate(topNode);

        return topNode;
    }

    public static ExprNode make3SubNodeAnd() throws Exception
    {
        ExprNode topNode = new ExprAndNode();

        ExprEqualsNode[] equalNodes = new ExprEqualsNode[3];
        for (int i = 0; i < equalNodes.length; i++)
        {
            equalNodes[i] = new ExprEqualsNode(false);
            topNode.addChildNode(equalNodes[i]);
        }

        ExprIdentNode i1_1 = new ExprIdentNode("intPrimitive", "s0");
        ExprIdentNode i1_2 = new ExprIdentNode("intBoxed", "s1");
        equalNodes[0].addChildNode(i1_1);
        equalNodes[0].addChildNode(i1_2);

        ExprIdentNode i2_1 = new ExprIdentNode("string", "s1");
        ExprIdentNode i2_2 = new ExprIdentNode("string", "s0");
        equalNodes[1].addChildNode(i2_1);
        equalNodes[1].addChildNode(i2_2);

        ExprIdentNode i3_1 = new ExprIdentNode("boolBoxed", "s0");
        ExprIdentNode i3_2 = new ExprIdentNode("boolPrimitive", "s1");
        equalNodes[2].addChildNode(i3_1);
        equalNodes[2].addChildNode(i3_2);

        validate(topNode);

        return topNode;
    }

    public static ExprNode makeIdentNode(String fieldName, String streamName) throws Exception
    {
        ExprIdentNode node = new ExprIdentNode(fieldName, streamName);
        validate(node);
        return node;
    }

    public static ExprNode makeMathNode() throws Exception
    {
        ExprIdentNode node1 = new ExprIdentNode("intBoxed", "s0");
        ExprIdentNode node2 = new ExprIdentNode("intPrimitive", "s0");
        ExprMathNode mathNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        mathNode.addChildNode(node1);
        mathNode.addChildNode(node2);

        validate(mathNode);

        return mathNode;
    }

    public static ExprNode makeSumAndFactorNode() throws Exception
    {
        // sum node
        ExprSumNode sum = new ExprSumNode(false);
        ExprIdentNode ident = new ExprIdentNode("intPrimitive", "s0");
        sum.addChildNode(ident);

        ExprIdentNode node = new ExprIdentNode("intBoxed", "s0");
        ExprMathNode mathNode = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        mathNode.addChildNode(node);
        mathNode.addChildNode(sum);

        validate(mathNode);

        return mathNode;
    }

    public static ExprAggregateNode makeSumAggregateNode() throws Exception
    {
        ExprSumNode top = new ExprSumNode(false);
        ExprIdentNode ident = new ExprIdentNode("intPrimitive", "s0");
        top.addChildNode(ident);

        validate(top);

        return top;
    }

    private static void validate(ExprNode topNode) throws Exception
    {
        StreamTypeService streamTypeService = new SupportStreamTypeSvc3Stream();
        topNode.validateDescendents(streamTypeService);
    }
}
