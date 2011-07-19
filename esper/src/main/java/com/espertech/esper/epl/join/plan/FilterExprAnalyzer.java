/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.datetime.eval.ExprDotNodeFilterAnalyzerDTIntervalDesc;
import com.espertech.esper.epl.expression.*;

import java.util.Set;

/**
 * Analyzes a filter expression and builds a query graph model.
 * The 'equals', 'and' 'between' and relational operators expressions in the filter expression are extracted
 * and placed in the query graph model as navigable relationships (by key and index
 * properties as well as ranges) between streams.
 */
public class FilterExprAnalyzer
{
    /**
     * Analyzes filter expression to build query graph model.
     * @param topNode - filter top node
     * @param queryGraph - model containing relationships between streams, to be written to
     */
    public static void analyze(ExprNode topNode, QueryGraph queryGraph, boolean isOuterJoin)
    {
        // Analyze relationships between streams. Relationships are properties in AND and EQUALS nodes of joins.
        if (topNode instanceof ExprEqualsNode)
        {
            ExprEqualsNode equalsNode = (ExprEqualsNode) topNode;
            if (!equalsNode.isNotEquals())
            {
                analyzeEqualsNode(equalsNode, queryGraph, isOuterJoin);
            }
        }
        else if (topNode instanceof ExprAndNode)
        {
            ExprAndNode andNode = (ExprAndNode) topNode;
            analyzeAndNode(andNode, queryGraph, isOuterJoin);
        }
        else if (topNode instanceof ExprBetweenNode) {
            ExprBetweenNode betweenNode = (ExprBetweenNode) topNode;
            analyzeBetweenNode(betweenNode, queryGraph);
        }
        else if (topNode instanceof ExprRelationalOpNode) {
            ExprRelationalOpNode relNode = (ExprRelationalOpNode) topNode;
            analyzeRelationalOpNode(relNode, queryGraph);
        }
        else if (topNode instanceof ExprDotNode && !isOuterJoin)
        {
            ExprDotNode dotNode = (ExprDotNode) topNode;
            analyzeDotNode(dotNode, queryGraph);
        }
    }

    private static void analyzeDotNode(ExprDotNode dotNode, QueryGraph queryGraph) {
        ExprDotNodeFilterAnalyzerDTIntervalDesc interval = dotNode.getIntervalFilterDesc();
        if (interval == null) {
            return;
        }
        interval.apply(queryGraph);
    }

    private static void analyzeRelationalOpNode(ExprRelationalOpNode relNode, QueryGraph queryGraph) {
        if ( ((relNode.getChildNodes().get(0) instanceof ExprIdentNode)) &&
             ((relNode.getChildNodes().get(1) instanceof ExprIdentNode)))
        {
            ExprIdentNode identNodeLeft = (ExprIdentNode) relNode.getChildNodes().get(0);
            ExprIdentNode identNodeRight = (ExprIdentNode) relNode.getChildNodes().get(1);

            if (identNodeLeft.getStreamId() != identNodeRight.getStreamId())
            {
                queryGraph.addRelationalOpStrict(identNodeLeft.getStreamId(), identNodeLeft.getResolvedPropertyName(), identNodeLeft,
                        identNodeRight.getStreamId(), identNodeRight.getResolvedPropertyName(), identNodeRight, relNode.getRelationalOpEnum());
            }
            return;
        }

        int indexedStream = -1;
        String indexedProp = null;
        ExprNode exprNodeNoIdent = null;

        if (relNode.getChildNodes().get(0) instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) relNode.getChildNodes().get(0);
            indexedStream = identNode.getStreamId();
            indexedProp = identNode.getResolvedPropertyName();
            exprNodeNoIdent = relNode.getChildNodes().get(1);
        }
        else if (relNode.getChildNodes().get(1) instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) relNode.getChildNodes().get(1);
            indexedStream = identNode.getStreamId();
            indexedProp = identNode.getResolvedPropertyName();
            exprNodeNoIdent = relNode.getChildNodes().get(0);
        }
        if (indexedStream == -1) {
            return;     // require property of right/left side of equals
        }

        EligibilityDesc eligibility = verifyInputStream(exprNodeNoIdent, indexedStream);
        if (!eligibility.getEligibility().isEligible()) {
            return;
        }

        queryGraph.addRelationalOp(indexedStream, indexedProp, eligibility.getStreamNum(), exprNodeNoIdent, relNode.getRelationalOpEnum());
    }

    private static void analyzeBetweenNode(ExprBetweenNode betweenNode, QueryGraph queryGraph) {
        if ( ((betweenNode.getChildNodes().get(0) instanceof ExprIdentNode)) &&
             ((betweenNode.getChildNodes().get(1) instanceof ExprIdentNode)) &&
             ((betweenNode.getChildNodes().get(2) instanceof ExprIdentNode)) )
        {
            ExprIdentNode identNodeValue = (ExprIdentNode) betweenNode.getChildNodes().get(0);
            ExprIdentNode identNodeStart = (ExprIdentNode) betweenNode.getChildNodes().get(1);
            ExprIdentNode identNodeEnd = (ExprIdentNode) betweenNode.getChildNodes().get(2);
            boolean includeStart = betweenNode.isLowEndpointIncluded();
            boolean includeEnd = betweenNode.isHighEndpointIncluded();
            boolean isNot = betweenNode.isNotBetween();

            int keyStreamStart = identNodeStart.getStreamId();
            int keyStreamEnd = identNodeEnd.getStreamId();
            int valueStream = identNodeValue.getStreamId();
            queryGraph.addRangeStrict(keyStreamStart, identNodeStart.getResolvedPropertyName(), identNodeStart, keyStreamEnd,
                    identNodeEnd.getResolvedPropertyName(), identNodeEnd, valueStream,
                    identNodeValue.getResolvedPropertyName(), identNodeValue,
                    includeStart, includeEnd, isNot);
            return;
        }

        // handle constant-compare or transformation case
        if (betweenNode.getChildNodes().get(0) instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) betweenNode.getChildNodes().get(0);
            int indexedStream = identNode.getStreamId();
            String indexedProp = identNode.getResolvedPropertyName();

            ExprNode startNode = betweenNode.getChildNodes().get(1);
            ExprNode endNode = betweenNode.getChildNodes().get(2);

            EligibilityDesc eligibilityStart = verifyInputStream(startNode, indexedStream);
            if (!eligibilityStart.getEligibility().isEligible()) {
                return;
            }
            EligibilityDesc eligibilityEnd = verifyInputStream(endNode, indexedStream);
            if (!eligibilityEnd.getEligibility().isEligible()) {
                return;
            }

            queryGraph.addRangeExpr(indexedStream, indexedProp, startNode, eligibilityStart.getStreamNum(), endNode, eligibilityEnd.getStreamNum());
        }
    }

    private static EligibilityDesc verifyInputStream(ExprNode expression, int indexedStream) {
        ExprNodeIdentifierCollectVisitor visitor = new ExprNodeIdentifierCollectVisitor();
        expression.accept(visitor);
        Set<Integer> inputStreamsRequired = visitor.getStreamsRequired();
        if (inputStreamsRequired.size() > 1) {  // multi-stream dependency no optimization (i.e. a+b=c)
            return new EligibilityDesc(FilterExprAnalyzer.Eligibility.INELIGIBLE, null);
        }
        if (inputStreamsRequired.size() == 1 && inputStreamsRequired.iterator().next() == indexedStream) {  // self-compared no optimization
            return new EligibilityDesc(FilterExprAnalyzer.Eligibility.INELIGIBLE, null);
        }
        if (inputStreamsRequired.isEmpty()) {
            return new EligibilityDesc(FilterExprAnalyzer.Eligibility.REQUIRE_NONE, null);
        }
        return new EligibilityDesc(FilterExprAnalyzer.Eligibility.REQUIRE_ONE, inputStreamsRequired.iterator().next());
    }

    /**
     * Analye EQUALS (=) node.
     * @param equalsNode - node to analyze
     * @param queryGraph - store relationships between stream properties
     */
    protected static void analyzeEqualsNode(ExprEqualsNode equalsNode, QueryGraph queryGraph, boolean isOuterJoin)
    {
        if ( (equalsNode.getChildNodes().get(0) instanceof ExprIdentNode) &&
             (equalsNode.getChildNodes().get(1) instanceof ExprIdentNode))
        {
            ExprIdentNode identNodeLeft = (ExprIdentNode) equalsNode.getChildNodes().get(0);
            ExprIdentNode identNodeRight = (ExprIdentNode) equalsNode.getChildNodes().get(1);

            if (identNodeLeft.getStreamId() != identNodeRight.getStreamId())
            {
                queryGraph.addStrictEquals(identNodeLeft.getStreamId(), identNodeLeft.getResolvedPropertyName(), identNodeLeft,
                        identNodeRight.getStreamId(), identNodeRight.getResolvedPropertyName(), identNodeRight);
            }

            return;
        }
        if (isOuterJoin) {      // outerjoins don't use constants or one-way expression-derived information to evaluate join
            return;
        }

        // handle constant-compare or transformation case
        int indexedStream = -1;
        String indexedProp = null;
        ExprNode exprNodeNoIdent = null;

        if (equalsNode.getChildNodes().get(0) instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) equalsNode.getChildNodes().get(0);
            indexedStream = identNode.getStreamId();
            indexedProp = identNode.getResolvedPropertyName();
            exprNodeNoIdent = equalsNode.getChildNodes().get(1);
        }
        else if (equalsNode.getChildNodes().get(1) instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) equalsNode.getChildNodes().get(1);
            indexedStream = identNode.getStreamId();
            indexedProp = identNode.getResolvedPropertyName();
            exprNodeNoIdent = equalsNode.getChildNodes().get(0);
        }
        if (indexedStream == -1) {
            return;     // require property of right/left side of equals
        }

        EligibilityDesc eligibility = verifyInputStream(exprNodeNoIdent, indexedStream);
        if (!eligibility.getEligibility().isEligible()) {
            return;
        }

        if (eligibility.getEligibility() == Eligibility.REQUIRE_NONE) {
            queryGraph.addUnkeyedExpression(indexedStream, indexedProp, exprNodeNoIdent);
        }
        else {
            queryGraph.addKeyedExpression(indexedStream, indexedProp, eligibility.getStreamNum(), exprNodeNoIdent);
        }
    }

    /**
     * Analyze the AND-node.
     * @param andNode - node to analyze
     * @param queryGraph - to store relationships between stream properties
     */
    protected static void analyzeAndNode(ExprAndNode andNode, QueryGraph queryGraph, boolean isOuterJoin)
    {
        for (ExprNode childNode : andNode.getChildNodes())
        {
            analyze(childNode, queryGraph, isOuterJoin);
        }
    }

    private static enum Eligibility {
        REQUIRE_NONE,
        REQUIRE_ONE,
        INELIGIBLE;

        public boolean isEligible() {
            return this != INELIGIBLE;
        }
    }

    private static class EligibilityDesc {
        private Eligibility eligibility;
        private Integer streamNum;

        private EligibilityDesc(Eligibility eligibility, Integer streamNum) {
            this.eligibility = eligibility;
            this.streamNum = streamNum;
        }

        public Eligibility getEligibility() {
            return eligibility;
        }

        public Integer getStreamNum() {
            return streamNum;
        }
    }
}
