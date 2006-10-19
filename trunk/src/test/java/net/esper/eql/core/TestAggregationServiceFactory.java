package net.esper.eql.core;

import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.eql.core.*;
import net.esper.eql.expression.ExprAggregateNode;
import net.esper.eql.expression.ExprNode;

import java.util.List;
import java.util.LinkedList;

import junit.framework.TestCase;

public class TestAggregationServiceFactory extends TestCase
{
    private List<ExprAggregateNode> aggregateNodes;
    private List<ExprNode> sortByNodes;

    public void setUp()
    {
        aggregateNodes = new LinkedList<ExprAggregateNode>();
        sortByNodes = new LinkedList<ExprNode>();
    }

    public void testGetService() throws Exception
    {
        // Test with aggregates but no group by
        aggregateNodes.add(SupportExprNodeFactory.makeSumAggregateNode());
        AggregationService service = AggregationServiceFactory.getService(aggregateNodes, false, null, sortByNodes);
        assertTrue(service instanceof AggregationServiceGroupAllImpl);

        // Test with aggregates and group by
        service = AggregationServiceFactory.getService(aggregateNodes, true, null, sortByNodes);
        assertTrue(service instanceof AggregationServiceGroupByImpl);
    }

    public void testGetNullService() throws Exception
    {
        // Test no aggregates and no group-by
    	AggregationService service = AggregationServiceFactory.getService(aggregateNodes,false, null, sortByNodes);
    	assertTrue(service instanceof AggregationServiceNull);
    }
}
