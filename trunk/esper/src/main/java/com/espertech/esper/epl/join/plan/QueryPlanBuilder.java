/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.event.EventType;
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
     * @param outerJoinDescList - list of outer join criteria, or null if there are no outer joins
     * @param optionalFilterNode - filter tree
     * @param streamNames - names of streams
     * @param typesPerStream - event types for each stream
     * @return query plan
     */
    public static QueryPlan getPlan(EventType[] typesPerStream,
                                    List<OuterJoinDesc> outerJoinDescList,
                                    ExprNode optionalFilterNode,
                                    String[] streamNames)
    {
        String methodName = ".getPlan ";

        int numStreams = typesPerStream.length;
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
        if (!outerJoinDescList.isEmpty())
        {
            OuterJoinAnalyzer.analyze(outerJoinDescList, queryGraph);
            if (log.isDebugEnabled())
            {
                log.debug(methodName + " After outer join queryGraph=\n" + queryGraph);
            }
        }
        else
        {
            // Let the query graph reflect the where-clause
            if (optionalFilterNode != null)
            {
                // Analyze relationships between streams using the optional filter expression.
                // Relationships are properties in AND and EQUALS nodes of joins.
                FilterExprAnalyzer.analyze(optionalFilterNode, queryGraph);
                if (log.isDebugEnabled())
                {
                    log.debug(methodName + "After filter expression queryGraph=\n" + queryGraph);
                }

                // Add navigation entries based on key and index property equivalency (a=b, b=c follows a=c)
                QueryGraph.fillEquivalentNav(queryGraph);
                if (log.isDebugEnabled())
                {
                    log.debug(methodName + "After fill equiv. nav. queryGraph=\n" + queryGraph);
                }
            }
        }

        if (numStreams == 2)
        {
            OuterJoinType outerJoinType = null;
            if (!outerJoinDescList.isEmpty())
            {
                outerJoinType = outerJoinDescList.get(0).getOuterJoinType();
            }

            QueryPlan queryPlan = TwoStreamQueryPlanBuilder.build(typesPerStream, queryGraph, outerJoinType);

            if (log.isDebugEnabled())
            {
                log.debug(methodName + "2-Stream queryPlan=" + queryPlan);
            }
            return queryPlan;
        }

        if (outerJoinDescList.isEmpty())
        {
            QueryPlan queryPlan = NStreamQueryPlanBuilder.build(queryGraph, typesPerStream);

            if (log.isDebugEnabled())
            {
                log.debug(methodName + "N-Stream no-outer-join queryPlan=" + queryPlan);
            }

            return queryPlan;
        }

        return NStreamOuterQueryPlanBuilder.build(queryGraph, outerJoinDescList, streamNames, typesPerStream);
    }

    private static final Log log = LogFactory.getLog(QueryPlanBuilder.class);
}
