package net.esper.eql.join.plan;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.spec.OuterJoinDesc;
import net.esper.type.OuterJoinType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Build a query plan based on filtering information.
 */
public class QueryPlanBuilder
{
    /**
     * Build query plan using the filter.
     * @param numStreams - number of streams
     * @param outerJoinDescList - list of outer join criteria, or null if there are no outer joins
     * @param optionalFilterNode - filter tree
     * @param streamNames - names of streams
     * @return query plan
     */
    public static QueryPlan getPlan(int numStreams, 
                                    List<OuterJoinDesc> outerJoinDescList, 
                                    ExprNode optionalFilterNode,
                                    String[] streamNames)
    {
        String methodName = ".getPlan ";

        if (numStreams < 2)
        {
            throw new IllegalArgumentException("Number of join stream types is less then 2");
        }
        if (outerJoinDescList.size() >= numStreams)
        {
            throw new IllegalArgumentException("Too many outer join descriptors found");
        }

        QueryGraph queryGraph = new QueryGraph(numStreams);

        // For outer joins the query graph will just contain outer join relationships
        if (outerJoinDescList.size() != 0)
        {
            OuterJoinAnalyzer.analyze(outerJoinDescList, queryGraph);
            log.debug(methodName + " After outer join queryGraph=\n" + queryGraph);
        }
        else
        {
            // Let the query graph reflect the where-clause
            if (optionalFilterNode != null)
            {
                // Analyze relationships between streams using the optional filter expression.
                // Relationships are properties in AND and EQUALS nodes of joins.
                FilterExprAnalyzer.analyze(optionalFilterNode, queryGraph);
                log.debug(methodName + "After filter expression queryGraph=\n" + queryGraph);

                // Add navigation entries based on key and index property equivalency (a=b, b=c follows a=c)
                QueryGraph.fillEquivalentNav(queryGraph);
                log.debug(methodName + "After fill equiv. nav. queryGraph=\n" + queryGraph);
            }
        }

        if (numStreams == 2)
        {
            OuterJoinType outerJoinType = null;
            if (outerJoinDescList.size() != 0)
            {
                outerJoinType = outerJoinDescList.get(0).getOuterJoinType();
            }

            QueryPlan queryPlan = TwoStreamQueryPlanBuilder.build(queryGraph, outerJoinType);

            if (log.isInfoEnabled())
            {
                log.debug(methodName + "2-Stream queryPlan=" + queryPlan);
            }
            return queryPlan;
        }

        if (outerJoinDescList.size() == 0)
        {
            QueryPlan queryPlan = NStreamQueryPlanBuilder.build(queryGraph);

            if (log.isInfoEnabled())
            {
                log.debug(methodName + "N-Stream no-outer-join queryPlan=" + queryPlan);
            }

            return queryPlan;
        }

        return NStreamOuterQueryPlanBuilder.build(queryGraph, outerJoinDescList, streamNames);
    }

    private static final Log log = LogFactory.getLog(QueryPlanBuilder.class);
}
