package com.espertech.esper.epl.join.plan;

public class QueryGraphRangeConsolidateDesc {
    private QueryGraphRangeEnum type;
    private boolean isReverse;

    public QueryGraphRangeConsolidateDesc(QueryGraphRangeEnum type, boolean reverse) {
        this.type = type;
        isReverse = reverse;
    }

    public QueryGraphRangeEnum getType() {
        return type;
    }

    public boolean isReverse() {
        return isReverse;
    }
}
