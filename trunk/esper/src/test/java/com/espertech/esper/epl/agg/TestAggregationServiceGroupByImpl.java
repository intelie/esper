package com.espertech.esper.epl.agg;

import com.espertech.esper.support.epl.SupportAggregator;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.agg.AggregationServiceGroupByImpl;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import junit.framework.TestCase;

public class TestAggregationServiceGroupByImpl extends TestCase
{
    private AggregationServiceGroupByImpl service;
    private MultiKeyUntyped groupOneKey;
    private MultiKeyUntyped groupTwoKey;
    private MethodResolutionService methodResolutionService;

    public void setUp()
    {
        SupportAggregator aggregators[] = new SupportAggregator[2];
        for (int i = 0; i < aggregators.length; i++)
        {
            aggregators[i] = new SupportAggregator();
        }
        ExprEvaluator evaluators[] = new ExprEvaluator[] { new SupportExprNode(5), new SupportExprNode(2) };
        methodResolutionService = new MethodResolutionServiceImpl(null);

        service = new AggregationServiceGroupByImpl(evaluators, aggregators, methodResolutionService);

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
