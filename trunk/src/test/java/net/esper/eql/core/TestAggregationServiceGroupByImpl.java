package net.esper.eql.core;

import net.esper.support.eql.SupportAggregator;
import net.esper.support.eql.SupportExprNode;
import net.esper.event.EventBean;
import net.esper.collection.MultiKeyUntyped;
import net.esper.eql.expression.ExprEvaluator;
import junit.framework.TestCase;

public class TestAggregationServiceGroupByImpl extends TestCase
{
    private AggregationServiceGroupByImpl service;
    private MultiKeyUntyped groupOneKey;
    private MultiKeyUntyped groupTwoKey;

    public void setUp()
    {
        SupportAggregator aggregators[] = new SupportAggregator[2];
        for (int i = 0; i < aggregators.length; i++)
        {
            aggregators[i] = new SupportAggregator();
        }
        ExprEvaluator evaluators[] = new ExprEvaluator[] { new SupportExprNode(5), new SupportExprNode(2) };

        service = new AggregationServiceGroupByImpl(evaluators, aggregators);

        groupOneKey = new MultiKeyUntyped(new Object[] {"x", "y1"});
        groupTwoKey = new MultiKeyUntyped(new Object[] {"x", "y2"});
    }

    public void testGetValue()
    {
        // apply 3 rows to group key 1, all aggregators evaluated their sub-expressions(constants 5 and 2)
        service.applyEnter(new EventBean[1], groupOneKey);
        service.applyEnter(new EventBean[1], groupOneKey);
        service.applyEnter(new EventBean[1], groupTwoKey);

        service.setCurrentRow(groupOneKey);
        assertEquals(10, service.getValue(0));
        assertEquals(4, service.getValue(1));
        service.setCurrentRow(groupTwoKey);
        assertEquals(5, service.getValue(0));
        assertEquals(2, service.getValue(1));

        service.applyLeave(new EventBean[1], groupTwoKey);
        service.applyLeave(new EventBean[1], groupTwoKey);
        service.applyLeave(new EventBean[1], groupTwoKey);
        service.applyLeave(new EventBean[1], groupOneKey);

        service.setCurrentRow(groupOneKey);
        assertEquals(10 - 5, service.getValue(0));
        assertEquals(4 - 2, service.getValue(1));
        service.setCurrentRow(groupTwoKey);
        assertEquals(5 - 15, service.getValue(0));
        assertEquals(2 - 6, service.getValue(1));
    }
}
