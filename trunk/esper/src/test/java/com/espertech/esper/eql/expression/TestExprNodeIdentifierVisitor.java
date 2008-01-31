package com.espertech.esper.eql.expression;

import com.espertech.esper.support.eql.SupportExprNodeFactory;
import junit.framework.TestCase;

public class TestExprNodeIdentifierVisitor extends TestCase
{
    private ExprNode exprNode;

    public void setUp() throws Exception
    {
        exprNode = SupportExprNodeFactory.makeMathNode();
    }

    public void testVisit() throws Exception
    {
        // test without aggregation nodes
        ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(false);
        exprNode.accept(visitor);

        assertEquals(2, visitor.getExprProperties().size());
        assertEquals(0, (Object) visitor.getExprProperties().get(0).getFirst());
        assertEquals("intBoxed", (Object) visitor.getExprProperties().get(0).getSecond());
        assertEquals(0, (Object) visitor.getExprProperties().get(1).getFirst());
        assertEquals("intPrimitive", (Object) visitor.getExprProperties().get(1).getSecond());

        // test with aggregation nodes, such as "intBoxed * sum(intPrimitive)"
        exprNode = SupportExprNodeFactory.makeSumAndFactorNode();
        visitor = new ExprNodeIdentifierVisitor(true);
        exprNode.accept(visitor);
        assertEquals(2, visitor.getExprProperties().size());
        assertEquals("intBoxed", (Object) visitor.getExprProperties().get(0).getSecond());
        assertEquals("intPrimitive", (Object) visitor.getExprProperties().get(1).getSecond());

        visitor = new ExprNodeIdentifierVisitor(false);
        exprNode.accept(visitor);
        assertEquals(1, visitor.getExprProperties().size());
        assertEquals("intBoxed", (Object) visitor.getExprProperties().get(0).getSecond());
    }
}
