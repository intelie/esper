/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

import net.esper.eql.expression.ExprIdentNode;
import net.esper.eql.spec.OuterJoinDesc;

import java.util.List;

/**
 * Analyzes an outer join descriptor list and builds a query graph model from it.
 * The 'on' expression identifiers are extracted
 * and placed in the query graph model as navigable relationships (by key and index
 * properties) between streams.
 */
public class OuterJoinAnalyzer
{
    /**
     * Analyzes the outer join descriptor list to build a query graph model.
     * @param outerJoinDescList - list of outer join descriptors
     * @param queryGraph - model containing relationships between streams that is written into
     * @return queryGraph object
     */
    public static QueryGraph analyze(List<OuterJoinDesc> outerJoinDescList, QueryGraph queryGraph)
    {
        for (OuterJoinDesc outerJoinDesc : outerJoinDescList)
        {
            ExprIdentNode identNodeLeft = outerJoinDesc.getLeftNode();
            ExprIdentNode identNodeRight = outerJoinDesc.getRightNode();

            queryGraph.add(identNodeLeft.getStreamId(), identNodeLeft.getResolvedPropertyName(),
                    identNodeRight.getStreamId(), identNodeRight.getResolvedPropertyName());
        }

        return queryGraph;
    }
}