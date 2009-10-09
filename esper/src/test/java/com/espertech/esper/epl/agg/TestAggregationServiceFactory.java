package com.espertech.esper.epl.agg;

import junit.framework.TestCase;
import com.espertech.esper.epl.expression.ExprAggregateNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esper.support.epl.SupportExprNodeFactory;

import java.util.LinkedList;
import java.util.List;

public class TestAggregationServiceFactory extends TestCase
{
    private List<ExprAggregateNode> selectAggregateNodes;
    private List<ExprAggregateNode> havingAggregateNodes;
    private List<ExprAggregateNode> orderByAggregateNodes;
    private List<ExprNode> sortByNodes;
    private MethodResolutionService methodResolutionService;

    public void setUp()
    {
        selectAggregateNodes = new LinkedList<ExprAggregateNode>();
        havingAggregateNodes = new LinkedList<ExprAggregateNode>();
        orderByAggregateNodes = new LinkedList<ExprAggregateNode>();
        sortByNodes = new LinkedList<ExprNode>();
        methodResolutionService = new MethodResolutionServiceImpl(null, true);
    }

    public void testGetService() throws Exception
    {
        // Test with aggregates but no group by
        selectAggregateNodes.add(SupportExprNodeFactory.makeSumAggregateNode());
        AggregationService service = AggregationServiceFactory.getService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService, null, null);
        assertTrue(service instanceof AggregationServiceGroupAllImpl);

        // Test with aggregates and group by
        service = AggregationServiceFactory.getService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, true, methodResolutionService, null, null);
        assertTrue(service instanceof AggregationServiceGroupByRefcountedImpl);
    }

    public void testGetNullService() throws Exception
    {
        // Test no aggregates and no group-by
    	AggregationService service = AggregationServiceFactory.getService(selectAggregateNodes,havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService, null, null);
    	assertTrue(service instanceof AggregationServiceNull);
    }
}
