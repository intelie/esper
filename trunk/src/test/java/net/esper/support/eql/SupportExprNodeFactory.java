package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.type.ArithTypeEnum;
import net.esper.type.RelationalOpEnum;
import net.esper.collection.Pair;

import net.esper.event.EventType;

import java.util.List;
import java.util.LinkedList;

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

    public static ExprNode makeMathNode(ArithTypeEnum operator_, Object valueLeft_, Object valueRight_) throws Exception
    {
        ExprMathNode mathNode = new ExprMathNode(operator_);
        mathNode.addChildNode(new SupportExprNode(valueLeft_));
        mathNode.addChildNode(new SupportExprNode(valueRight_));
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

    public static ExprNode makeCountNode(Object value, Class type) throws Exception
    {
        ExprCountNode countNode = new ExprCountNode(false);
        countNode.addChildNode(new SupportExprNode(value, type));
        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
        countNode.setAggregationResultFuture(future, 1);
        validate(countNode);
        return countNode;
    }

    public static ExprNode makeRelationalOpNode(RelationalOpEnum operator_, Object valueLeft_, Class typeLeft_, Object valueRight_, Class typeRight_) throws Exception
    {
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
        opNode.addChildNode(new SupportExprNode(valueLeft_, typeLeft_));
        opNode.addChildNode(new SupportExprNode(valueRight_, typeRight_));
        validate(opNode);
        return opNode;
    }

    public static ExprNode makeRelationalOpNode(RelationalOpEnum operator_, Class typeLeft_, Class typeRight_) throws Exception
    {
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
        opNode.addChildNode(new SupportExprNode(typeLeft_));
        opNode.addChildNode(new SupportExprNode(typeRight_));
        validate(opNode);
        return opNode;
    }

    public static ExprNode makeRelationalOpNode(RelationalOpEnum operator_, ExprNode nodeLeft_, ExprNode nodeRight_) throws Exception
    {
        ExprRelationalOpNode opNode = new ExprRelationalOpNode(operator_);
        opNode.addChildNode(nodeLeft_);
        opNode.addChildNode(nodeRight_);
        validate(opNode);
        return opNode;
    }

    public static ExprCaseNode makeCaseNode() throws Exception
    {
        //Build:
        // case when (so.floatPrimitive>s1.shortBoxed) then count(5) when (so.LongPrimitive>s1.intPrimitive) then (25 + 130.5) else (3*3) end
        List<Pair<ExprNode, ExprNode>> listExprNode = new LinkedList<Pair<ExprNode, ExprNode>>();
        ExprNode node1, node2;
        ExprNode[] identNodes = new ExprNode[4];
        identNodes[0] = makeIdentNode("intPrimitive","s1");
        identNodes[1] = makeIdentNode("floatPrimitive", "s0");
        identNodes[2] = makeIdentNode("shortBoxed", "s1");
        identNodes[3] = makeIdentNode("longPrimitive", "s0");
        node1 =  makeRelationalOpNode(RelationalOpEnum.GT, identNodes[1], identNodes[2]);
        node2 = makeCountNode(5, Integer.class);
        Pair<ExprNode, ExprNode> p = new Pair(node1,node2);
        listExprNode.add(p);
        node1 =  makeRelationalOpNode(RelationalOpEnum.GT, identNodes[3], identNodes[0]);
        node2 = makeMathNode(ArithTypeEnum.ADD, new Integer(25), new Double(130.5));
        p = new Pair(node1,node2);
        listExprNode.add(p);
        node2 = makeMathNode(ArithTypeEnum.MULTIPLY, new Integer(3), new Integer(3));
        p = new Pair(null,node2);
        listExprNode.add(p);
        ExprCaseNode node = new ExprCaseNode(false, listExprNode);
        return (node);
    }

    public static ExprCaseNode makeCase2Node() throws Exception
    {
        // Build:
        // case s0.intPrimitive when s1.intBoxed then count(5) when (5*2) then (s0.intPrimitive*4) else (10*20) end
        List<Pair<ExprNode, ExprNode>> listExprNode = new LinkedList<Pair<ExprNode, ExprNode>>();
        ExprNode[] mathNodes = new  ExprNode[2];
        ExprNode[] identNodes = new ExprNode[2];
        identNodes[0] = makeIdentNode("intPrimitive","s0");
        identNodes[1] = makeIdentNode("intBoxed", "s1");
        ExprNode countNode = makeCountNode(5, Integer.class);
        Pair<ExprNode, ExprNode> p = new Pair(identNodes[1],countNode);
        listExprNode.add(p);
        mathNodes[0] = makeMathNode(ArithTypeEnum.MULTIPLY, new Integer(5), new Integer(2));
        mathNodes[1] = new ExprMathNode(ArithTypeEnum.MULTIPLY);
        mathNodes[1].addChildNode(identNodes[0]);
        mathNodes[1].addChildNode(new SupportExprNode(new Double(4.0)));
        validate(mathNodes[1]);
        p = new Pair(mathNodes[0],mathNodes[1]);
        listExprNode.add(p);
        mathNodes[0] = makeMathNode(ArithTypeEnum.MULTIPLY, new Double(10.0), new Double(20.0));
        p = new Pair(null,mathNodes[0]);
        listExprNode.add(p);
        ExprCaseNode node = new ExprCaseNode(true, listExprNode);
        node.addChildNode(identNodes[0]);
        return (node);
    }

    private static void validate(ExprNode topNode) throws Exception
    {
        StreamTypeService streamTypeService = new SupportStreamTypeSvc3Stream();
        topNode.validateDescendents(streamTypeService);
    }
}
