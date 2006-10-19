package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.eql.core.StreamTypeService;
import net.esper.type.MathArithTypeEnum;
import net.esper.type.RelationalOpEnum;

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
        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
        mathNode.addChildNode(node1);
        mathNode.addChildNode(node2);

        validate(mathNode);

        return mathNode;
    }

    public static ExprNode makeMathNode(MathArithTypeEnum operator_, Object valueLeft_, Object valueRight_) throws Exception
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
        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
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

    public static ExprInNode makeInSetNode(boolean isNotIn) throws Exception
    {
        // Build :      s0.intPrimitive in (1, 2)
        ExprInNode inNode = new ExprInNode(isNotIn);
        inNode.addChildNode(makeIdentNode("intPrimitive","s0"));
        inNode.addChildNode(new SupportExprNode(1));
        inNode.addChildNode(new SupportExprNode(2));
        validate(inNode);
        return inNode;
    }

    public static ExprCaseNode makeCaseSyntax1Node() throws Exception
    {
        // Build (case 1 expression):
        // case when s0.intPrimitive = 1 then "a"
        //      when s0.intPrimitive = 2 then "b"
        //      else "c"
        // end
        ExprCaseNode caseNode = new ExprCaseNode(false);

        ExprNode node = makeEqualsNode("intPrimitive", "s0", 1);
        caseNode.addChildNode(node);
        caseNode.addChildNode(new SupportExprNode("a"));

        node = makeEqualsNode("intPrimitive", "s0", 2);
        caseNode.addChildNode(node);
        caseNode.addChildNode(new SupportExprNode("b"));

        caseNode.addChildNode(new SupportExprNode("c"));

        validate(caseNode);

        return caseNode;
    }

    public static ExprCaseNode makeCaseSyntax2Node() throws Exception
    {
        // Build (case 2 expression):
        // case s0.intPrimitive
        //   when 1 then "a"
        //   when 2 then "b"
        //   else "c"
        // end
        ExprCaseNode caseNode = new ExprCaseNode(true);
        caseNode.addChildNode(makeIdentNode("intPrimitive","s0"));

        caseNode.addChildNode(new SupportExprNode(1));
        caseNode.addChildNode(new SupportExprNode("a"));
        caseNode.addChildNode(new SupportExprNode(2));
        caseNode.addChildNode(new SupportExprNode("b"));
        caseNode.addChildNode(new SupportExprNode("c"));

        validate(caseNode);

        return (caseNode);
    }

    private static ExprEqualsNode makeEqualsNode(String ident1, String stream1, Object value) throws Exception
    {
        ExprEqualsNode topNode = new ExprEqualsNode(false);
        ExprIdentNode i1_1 = new ExprIdentNode(ident1, stream1);
        SupportExprNode constantNode = new SupportExprNode(value);
        topNode.addChildNode(i1_1);
        topNode.addChildNode(constantNode);
        return topNode;
    }

    private static void validate(ExprNode topNode) throws Exception
    {
        StreamTypeService streamTypeService = new SupportStreamTypeSvc3Stream();
        topNode.getValidatedSubtree(streamTypeService, null);
    }
}
