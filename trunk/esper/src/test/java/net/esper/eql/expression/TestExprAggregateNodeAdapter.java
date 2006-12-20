package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportAggregationResultFuture;
import net.esper.support.eql.SupportAggregateExprNode;
import net.esper.support.eql.SupportExprNode;

import java.util.List;

public abstract class TestExprAggregateNodeAdapter extends TestCase
{
    protected ExprAggregateNode validatedNodeToTest;

    public void testEvaluate() throws Exception
    {
        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
        validatedNodeToTest.setAggregationResultFuture(future, 1);

        assertEquals(20, validatedNodeToTest.evaluate(null));
    }
}

