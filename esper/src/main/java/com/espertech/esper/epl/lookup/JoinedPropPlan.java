package com.espertech.esper.epl.lookup;

import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class JoinedPropPlan {
    private final Map<String, JoinedPropDesc> hashProps;
    private final Map<String, SubqueryRangeKeyDesc> rangeProps;
    private final boolean mustCoerce;

    public JoinedPropPlan() {
        hashProps = Collections.<String, JoinedPropDesc>emptyMap();
        rangeProps = Collections.<String, SubqueryRangeKeyDesc>emptyMap();
        mustCoerce = false;
    }

    public JoinedPropPlan(LinkedHashMap<String, JoinedPropDesc> hashProps, LinkedHashMap<String, SubqueryRangeKeyDesc> rangeProps, boolean mustCoerce) {
        this.hashProps = hashProps;
        this.mustCoerce = mustCoerce;
        this.rangeProps = rangeProps;
    }

    public Map<String, SubqueryRangeKeyDesc> getRangeProps() {
        return rangeProps;
    }

    public Map<String, JoinedPropDesc> getHashProps() {
        return hashProps;
    }

    public boolean isMustCoerce() {
        return mustCoerce;
    }
}
