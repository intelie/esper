package com.espertech.esper.epl.join.plan;

import java.io.Serializable;

public abstract class QueryGraphValueEntryRange implements QueryGraphValueEntry, Serializable {

    private final QueryGraphRangeEnum type;

    protected QueryGraphValueEntryRange(QueryGraphRangeEnum type) {
        this.type = type;
    }

    public QueryGraphRangeEnum getType() {
        return type;
    }

    public abstract String toQueryPlan();
}
