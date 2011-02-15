/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.core.StreamJoinAnalysisResult;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.epl.join.table.HistoricalStreamIndexList;
import com.espertech.esper.client.EventType;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.util.DependencyGraph;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Build a query plan based on filtering information.
 */
public class QueryPlanBuilder
{
    /**
     * Build query plan using the filter.
     * @param typesPerStream - event types for each stream
     * @param outerJoinDescList - list of outer join criteria, or null if there are no outer joins
     * @param queryGraph - relationships between streams based on filter expressions and outer-join on-criteria
     * @param streamNames - names of streams
     * @param hasHistorical - indicator if there is one or more historical streams in the join
     * @param isHistorical - indicator for each stream if it is a historical streams or not
     * @param dependencyGraph - dependencies between historical streams
     * @param historicalStreamIndexLists - index management, populated for the query plan
     * @param exprEvaluatorContext expression evaluation context
     * @param streamJoinAnalysisResult
     * @return query plan
     * @throws ExprValidationException if the query plan fails
     */
    public static QueryPlan getPlan(EventType[] typesPerStream,
                                    List<OuterJoinDesc> outerJoinDescList,
                                    QueryGraph queryGraph,
                                    String[] streamNames,
                                    boolean hasHistorical,
                                    boolean[] isHistorical,
                                    DependencyGraph dependencyGraph,
                                    HistoricalStreamIndexList[] historicalStreamIndexLists,
                                    ExprEvaluatorContext exprEvaluatorContext,
                                    StreamJoinAnalysisResult streamJoinAnalysisResult)
            throws ExprValidationException
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

        if (numStreams == 2)
        {
            OuterJoinType outerJoinType = null;
            if (!outerJoinDescList.isEmpty())
            {
                outerJoinType = outerJoinDescList.get(0).getOuterJoinType();
            }

            QueryPlan queryPlan = TwoStreamQueryPlanBuilder.build(typesPerStream, queryGraph, outerJoinType);
            removeUnidirectional(queryPlan, streamJoinAnalysisResult);

            if (log.isDebugEnabled())
            {
                log.debug(methodName + "2-Stream queryPlan=" + queryPlan);
            }
            return queryPlan;
        }

        // TODO - comment back in - for now all N-stream queries get executed by the outer query plan builder
        boolean USE_NESTED_ITER = false;
        if (outerJoinDescList.isEmpty() && USE_NESTED_ITER)
        {
            QueryPlan queryPlan = NStreamQueryPlanBuilder.build(queryGraph, typesPerStream,
                                    hasHistorical, isHistorical, dependencyGraph, historicalStreamIndexLists);
            removeUnidirectional(queryPlan, streamJoinAnalysisResult);

            if (log.isDebugEnabled())
            {
                log.debug(methodName + "N-Stream no-outer-join queryPlan=" + queryPlan);
            }

            return queryPlan;
        }

        QueryPlan queryPlan = NStreamOuterQueryPlanBuilder.build(queryGraph, outerJoinDescList, streamNames, typesPerStream,
                                    hasHistorical, isHistorical, dependencyGraph, historicalStreamIndexLists, exprEvaluatorContext);
        removeUnidirectional(queryPlan, streamJoinAnalysisResult);
        return queryPlan;
    }

    // Remove plans for non-unidirectional streams
    private static void removeUnidirectional(QueryPlan queryPlan, StreamJoinAnalysisResult streamJoinAnalysisResult) {
        for (int streamNum = 0; streamNum < queryPlan.getExecNodeSpecs().length; streamNum++) {
            if (streamJoinAnalysisResult.isUnidirectional() && !streamJoinAnalysisResult.getUnidirectionalInd()[streamNum]) {
                queryPlan.getExecNodeSpecs()[streamNum] = new QueryPlanNodeNoOp();
            }
        }
    }

    private static final Log log = LogFactory.getLog(QueryPlanBuilder.class);
}
