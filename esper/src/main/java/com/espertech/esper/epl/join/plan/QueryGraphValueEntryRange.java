package com.espertech.esper.epl.join.plan;

public abstract class QueryGraphValueEntryRange implements QueryGraphValueEntry {

    private final QueryGraphRangeEnum type;

    protected QueryGraphValueEntryRange(QueryGraphRangeEnum type) {
        this.type = type;
    }

    public QueryGraphRangeEnum getType() {
        return type;
    }

    public abstract String toQueryPlan();
}
