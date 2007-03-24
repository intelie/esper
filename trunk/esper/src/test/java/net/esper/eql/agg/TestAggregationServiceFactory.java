package net.esper.eql.agg;

import junit.framework.TestCase;
import net.esper.eql.expression.ExprAggregateNode;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.agg.*;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.MethodResolutionServiceImpl;
import net.esper.support.eql.SupportExprNodeFactory;

import java.util.LinkedList;
import java.util.List;

public class TestAggregationServiceFactory extends TestCase
{
    private List<ExprAggregateNode> aggregateNodes;
    private List<ExprNode> sortByNodes;
    private MethodResolutionService methodResolutionService;

    public void setUp()
    {
        aggregateNodes = new LinkedList<ExprAggregateNode>();
        sortByNodes = new LinkedList<ExprNode>();
        methodResolutionService = new MethodResolutionServiceImpl(null);
    }

    public void testGetService() throws Exception
    {
        // Test with aggregates but no group by
        aggregateNodes.add(SupportExprNodeFactory.makeSumAggregateNode());
        AggregationService service = AggregationServiceFactory.getService(aggregateNodes, false, null, sortByNodes, methodResolutionService);
        assertTrue(service instanceof AggregationServiceGroupAllImpl);

        // Test with aggregates and group by
        service = AggregationServiceFactory.getService(aggregateNodes, true, null, sortByNodes, methodResolutionService);
        assertTrue(service instanceof AggregationServiceGroupByImpl);
    }

    public void testGetNullService() throws Exception
    {
        // Test no aggregates and no group-by
    	AggregationService service = AggregationServiceFactory.getService(aggregateNodes,false, null, sortByNodes, methodResolutionService);
    	assertTrue(service instanceof AggregationServiceNull);
    }
}
