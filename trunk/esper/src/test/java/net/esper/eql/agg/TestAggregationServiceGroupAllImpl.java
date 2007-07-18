package net.esper.eql.agg;

import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportAggregator;
import net.esper.event.EventBean;
import net.esper.eql.agg.AggregationServiceGroupAllImpl;
import net.esper.eql.expression.ExprEvaluator;
import junit.framework.TestCase;

public class TestAggregationServiceGroupAllImpl extends TestCase
{
    private AggregationServiceGroupAllImpl service;

    public void setUp()
    {
        SupportAggregator aggregators[] = new SupportAggregator[2];
        for (int i = 0; i < aggregators.length; i++)
        {
            aggregators[i] = new SupportAggregator();
        }

        ExprEvaluator evaluators[] = new ExprEvaluator[] { new SupportExprNode(5), new SupportExprNode(2) };

        service = new AggregationServiceGroupAllImpl(evaluators, aggregators);
    }

    public void testApplyEnter()
    {
        // apply two rows, all aggregators evaluated their sub-expressions(constants 5 and 2) twice
        service.applyEnter(new EventBean[1], null);
        service.applyEnter(new EventBean[1], null);
        assertEquals(10, service.getValue(0));
        assertEquals(4, service.getValue(1));
    }

    public void testApplyLeave()
    {
        // apply 3 rows, all aggregators evaluated their sub-expressions(constants 5 and 2)
        service.applyLeave(new EventBean[1], null);
        service.applyLeave(new EventBean[1], null);
        service.applyLeave(new EventBean[1], null);
        assertEquals(-15, service.getValue(0));
        assertEquals(-6, service.getValue(1));
    }

    private static EventBean[][] makeEvents(int countRows)
    {
        EventBean[][] result = new EventBean[countRows][0];
        for (int i = 0; i < countRows; i++)
        {
            result[i] = new EventBean[0];
        }
        return result;
    }
}
