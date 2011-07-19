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

package com.espertech.esper.pattern;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.FilterSpecRaw;
import com.espertech.esper.epl.spec.PatternGuardSpec;
import com.espertech.esper.epl.spec.PatternObserverSpec;

import java.util.List;

public class PatternNodeFactoryImpl implements PatternNodeFactory {

    public EvalAndNode makeAndNode() {
        return new EvalAndNode();
    }

    public EvalEveryDistinctNode makeEveryDistinctNode(List<ExprNode> expressions) {
        return new EvalEveryDistinctNode(expressions);
    }

    public EvalEveryNode makeEveryNode() {
        return new EvalEveryNode();
    }

    public EvalFilterNode makeFilterNode(FilterSpecRaw filterSpecification, String eventAsName, Integer consumptionLevel) {
        return new EvalFilterNode(filterSpecification, eventAsName, consumptionLevel);
    }

    public EvalFollowedByNode makeFollowedByNode(List<ExprNode> maxExpressions) {
        return new EvalFollowedByNode(maxExpressions);
    }

    public EvalGuardNode makeGuardNode(PatternGuardSpec patternGuardSpec) {
        return new EvalGuardNode(patternGuardSpec);
    }

    public EvalMatchUntilNode makeMatchUntilNode(ExprNode lowerBounds, ExprNode upperBounds) {
        return new EvalMatchUntilNode(lowerBounds, upperBounds);
    }

    public EvalNotNode makeNotNode() {
        return new EvalNotNode();
    }

    public EvalObserverNode makeObserverNode(PatternObserverSpec patternObserverSpec) {
        return new EvalObserverNode(patternObserverSpec);
    }

    public EvalOrNode makeOrNode() {
        return new EvalOrNode();
    }

    public EvalRootNode makeRootNode() {
        return new EvalRootNode();
    }
}
