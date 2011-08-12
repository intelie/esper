/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.join.util;

import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.join.plan.QueryGraph;

public class RangeFilterAnalyzer {

    public static void apply(ExprNode target, ExprNode start, ExprNode end,
                             boolean includeStart, boolean includeEnd, boolean isNot,
                             QueryGraph queryGraph) {
        if ( ((target instanceof ExprIdentNode)) &&
             ((start instanceof ExprIdentNode)) &&
             ((end instanceof ExprIdentNode)) )
        {
            ExprIdentNode identNodeValue = (ExprIdentNode) target;
            ExprIdentNode identNodeStart = (ExprIdentNode) start;
            ExprIdentNode identNodeEnd = (ExprIdentNode) end;

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
        if (target instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) target;
            int indexedStream = identNode.getStreamId();
            String indexedProp = identNode.getResolvedPropertyName();

            EligibilityDesc eligibilityStart = EligibilityUtil.verifyInputStream(start, indexedStream);
            if (!eligibilityStart.getEligibility().isEligible()) {
                return;
            }
            EligibilityDesc eligibilityEnd = EligibilityUtil.verifyInputStream(end, indexedStream);
            if (!eligibilityEnd.getEligibility().isEligible()) {
                return;
            }

            queryGraph.addRangeExpr(indexedStream, indexedProp, start, eligibilityStart.getStreamNum(), end, eligibilityEnd.getStreamNum());
        }
    }
}
