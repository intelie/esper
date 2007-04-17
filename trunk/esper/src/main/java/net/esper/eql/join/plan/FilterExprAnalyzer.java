/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

import net.esper.eql.expression.*;
import net.esper.util.JavaClassHelper;

/**
 * Analyzes a filter expression and builds a query graph model.
 * The 'equals' and 'and' expressions in the filter expression are extracted
 * and placed in the query graph model as navigable relationships (by key and index
 * properties) between streams.
 */
public class FilterExprAnalyzer
{
    /**
     * Analyzes filter expression to build query graph model.
     * @param topNode - filter top node
     * @param queryGraph - model containing relationships between streams, to be written to
     * @param isAllowCoercion - true to indicate that coercing filter criteria are considered, false if not
     */
    public static void analyze(ExprNode topNode, QueryGraph queryGraph, boolean isAllowCoercion)
    {
        // Analyze relationships between streams. Relationships are properties in AND and EQUALS nodes of joins.
        if (topNode instanceof ExprEqualsNode)
        {
            ExprEqualsNode equalsNode = (ExprEqualsNode) topNode;
            if (!equalsNode.isNotEquals())
            {
                analyzeEqualsNode(equalsNode, queryGraph, isAllowCoercion);
            }
        }
        else if (topNode instanceof ExprAndNode)
        {
            ExprAndNode andNode = (ExprAndNode) topNode;
            analyzeAndNode(andNode, queryGraph, isAllowCoercion);
        }
    }

    /**
     * Analye EQUALS (=) node.
     * @param equalsNode - node to analyze
     * @param queryGraph - store relationships between stream properties
     * @param isAllowCoercion - true to indicate that coercing filter criteria are considered, false if not
     */
    protected static void analyzeEqualsNode(ExprEqualsNode equalsNode, QueryGraph queryGraph, boolean isAllowCoercion)
    {
        if ( (!(equalsNode.getChildNodes().get(0) instanceof ExprIdentNode)) ||
             (!(equalsNode.getChildNodes().get(1) instanceof ExprIdentNode)) )
        {
            return;
        }

        ExprIdentNode identNodeLeft = (ExprIdentNode) equalsNode.getChildNodes().get(0);
        ExprIdentNode identNodeRight = (ExprIdentNode) equalsNode.getChildNodes().get(1);

        if (!isAllowCoercion)
        {
            try
            {
                Class boxedTypeLeft = JavaClassHelper.getBoxedType(identNodeLeft.getType());
                Class boxedTypeRight = JavaClassHelper.getBoxedType(identNodeRight.getType());
                if (!(boxedTypeLeft.equals(boxedTypeRight)))
                {
                    return;
                }
            }
            catch (ExprValidationException e)
            {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

        queryGraph.add(identNodeLeft.getStreamId(), identNodeLeft.getResolvedPropertyName(),
                identNodeRight.getStreamId(), identNodeRight.getResolvedPropertyName());
    }

    /**
     * Analyze the AND-node.
     * @param andNode - node to analyze
     * @param queryGraph - to store relationships between stream properties
     * @param isAllowCoercion - true to indicate that coercing filter criteria are considered, false if not
     */
    protected static void analyzeAndNode(ExprAndNode andNode, QueryGraph queryGraph, boolean isAllowCoercion)
    {
        for (ExprNode childNode : andNode.getChildNodes())
        {
            if (childNode instanceof ExprEqualsNode)
            {
                ExprEqualsNode equalsNode = (ExprEqualsNode) childNode;
                if (!equalsNode.isNotEquals())
                {
                    analyzeEqualsNode(equalsNode, queryGraph, isAllowCoercion);
                }
            }
        }
    }
}
