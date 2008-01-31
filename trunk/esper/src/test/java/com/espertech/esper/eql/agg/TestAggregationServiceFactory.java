package com.espertech.esper.eql.agg;

import junit.framework.TestCase;
import com.espertech.esper.eql.expression.ExprAggregateNode;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.core.MethodResolutionServiceImpl;
import com.espertech.esper.support.eql.SupportExprNodeFactory;

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
        methodResolutionService = new MethodResolutionServiceImpl(null);
    }

    public void testGetService() throws Exception
    {
        // Test with aggregates but no group by
        selectAggregateNodes.add(SupportExprNodeFactory.makeSumAggregateNode());
        AggregationService service = AggregationServiceFactory.getService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService);
        assertTrue(service instanceof AggregationServiceGroupAllImpl);

        // Test with aggregates and group by
        service = AggregationServiceFactory.getService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, true, methodResolutionService);
        assertTrue(service instanceof AggregationServiceGroupByImpl);
    }

    public void testGetNullService() throws Exception
    {
        // Test no aggregates and no group-by
    	AggregationService service = AggregationServiceFactory.getService(selectAggregateNodes,havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService);
    	assertTrue(service instanceof AggregationServiceNull);
    }
}
