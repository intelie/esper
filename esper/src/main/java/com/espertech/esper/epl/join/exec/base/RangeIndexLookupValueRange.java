package com.espertech.esper.epl.join.exec.base;

import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;

public class RangeIndexLookupValueRange extends RangeIndexLookupValue {
    private QueryGraphRangeEnum operator;
    private boolean isAllowRangeReverse;

    public RangeIndexLookupValueRange(Object value, QueryGraphRangeEnum operator, boolean allowRangeReverse) {
        super(value);
        this.operator = operator;
        isAllowRangeReverse = allowRangeReverse;
    }

    public QueryGraphRangeEnum getOperator() {
        return operator;
    }

    public boolean isAllowRangeReverse() {
        return isAllowRangeReverse;
    }
}
