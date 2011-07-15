package com.espertech.esper.epl.join.plan;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

public abstract class QueryGraphValueEntryRange implements QueryGraphValueEntry, Serializable {

    private final QueryGraphRangeEnum type;

    protected QueryGraphValueEntryRange(QueryGraphRangeEnum type) {
        this.type = type;
    }

    public QueryGraphRangeEnum getType() {
        return type;
    }

    public abstract String toQueryPlan();

    public static String toQueryPlan(List<QueryGraphValueEntryRange> rangeKeyPairs) {
        StringWriter writer = new StringWriter();
        String delimiter = "";
        for (QueryGraphValueEntryRange item : rangeKeyPairs) {
            writer.write(delimiter);
            writer.write(item.toQueryPlan());
            delimiter = ", ";
        }
        return writer.toString();
    }
}
