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
import com.espertech.esper.epl.spec.FilterSpecRaw;
import com.espertech.esper.epl.spec.PatternGuardSpec;
import com.espertech.esper.epl.spec.PatternObserverSpec;

import java.util.List;

public interface PatternNodeFactory {

    public EvalNode makeAndNode();
    public EvalNode makeEveryDistinctNode(List<ExprNode> expressions);
    public EvalNode makeEveryNode();
    public EvalNode makeFilterNode(FilterSpecRaw filterSpecification,String eventAsName, Integer consumptionLevel);
    public EvalNode makeFollowedByNode(List<ExprNode> maxExpressions);
    public EvalNode makeGuardNode(PatternGuardSpec patternGuardSpec);
    public EvalNode makeMatchUntilNode(ExprNode lowerBounds, ExprNode upperBounds);
    public EvalNode makeNotNode();
    public EvalNode makeObserverNode(PatternObserverSpec patternObserverSpec);
    public EvalNode makeOrNode();
    public EvalRootNode makeRootNode();
}
