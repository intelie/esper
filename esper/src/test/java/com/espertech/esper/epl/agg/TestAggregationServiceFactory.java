package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esper.epl.expression.ExprAggregateNode;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class TestAggregationServiceFactory extends TestCase
{
    private List<ExprAggregateNode> selectAggregateNodes;
    private List<ExprAggregateNode> havingAggregateNodes;
    private List<ExprAggregateNode> orderByAggregateNodes;
    private MethodResolutionService methodResolutionService;

    public void setUp()
    {
        selectAggregateNodes = new LinkedList<ExprAggregateNode>();
        havingAggregateNodes = new LinkedList<ExprAggregateNode>();
        orderByAggregateNodes = new LinkedList<ExprAggregateNode>();
        methodResolutionService = new MethodResolutionServiceImpl(null, null, true);
    }

    public void testGetService() throws Exception
    {
        // Test with aggregates but no group by
        selectAggregateNodes.add(SupportExprNodeFactory.makeSumAggregateNode());
        AggregationService service = AggregationServiceFactory.getService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService, null, null, null, null, false, null, null);
        assertTrue(service instanceof AggSvcGroupAllNoAccessImpl);

        // Test with aggregates and group by
        service = AggregationServiceFactory.getService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, true, methodResolutionService, null, null, null, null, false, null, null);
        assertTrue(service instanceof AggSvcGroupByRefcountedNoAccessImpl);
    }

    public void testGetNullService() throws Exception
    {
        // Test no aggregates and no group-by
    	AggregationService service = AggregationServiceFactory.getService(selectAggregateNodes,havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService, null, null, null, null, false, null, null);
    	assertTrue(service instanceof AggregationServiceNull);
    }
}
