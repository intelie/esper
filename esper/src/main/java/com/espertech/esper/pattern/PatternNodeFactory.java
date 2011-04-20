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
    public EvalNode makeFilterNode(FilterSpecRaw filterSpecification,String eventAsName);
    public EvalNode makeFollowedByNode(List<ExprNode> maxExpressions);
    public EvalNode makeGuardNode(PatternGuardSpec patternGuardSpec);
    public EvalNode makeMatchUntilNode(ExprNode lowerBounds, ExprNode upperBounds);
    public EvalNode makeNotNode();
    public EvalNode makeObserverNode(PatternObserverSpec patternObserverSpec);
    public EvalNode makeOrNode();
    public EvalRootNode makeRootNode();
}
