package com.espertech.esper.epl.lookup;

import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.Collections;
import java.util.Map;

public class JoinedPropPlan {
    private final Map<String, JoinedPropDesc> joinProps;
    private final Map<String, SubqueryRangeKeyDesc> rangeProps;
    private final boolean mustCoerce;

    public JoinedPropPlan() {
        joinProps = Collections.<String, JoinedPropDesc>emptyMap();
        rangeProps = Collections.<String, SubqueryRangeKeyDesc>emptyMap();
        mustCoerce = false;
    }

    public JoinedPropPlan(Map<String, JoinedPropDesc> joinProps, Map<String, SubqueryRangeKeyDesc> rangeProps, boolean mustCoerce) {
        this.joinProps = joinProps;
        this.mustCoerce = mustCoerce;
        this.rangeProps = rangeProps;
    }

    public Map<String, SubqueryRangeKeyDesc> getRangeProps() {
        return rangeProps;
    }

    public Map<String, JoinedPropDesc> getJoinProps() {
        return joinProps;
    }

    public boolean isMustCoerce() {
        return mustCoerce;
    }
}
