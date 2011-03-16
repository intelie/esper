package com.espertech.esper.epl.lookup;

import com.espertech.esper.epl.join.plan.QueryGraphValueEntryRange;

import java.io.StringWriter;
import java.util.Collection;

public class SubordPropRangeKey {
    private QueryGraphValueEntryRange rangeInfo;
    private Class coercionType;

    public SubordPropRangeKey(QueryGraphValueEntryRange rangeInfo, Class coercionType) {
        this.rangeInfo = rangeInfo;
        this.coercionType = coercionType;
    }

    public Class getCoercionType() {
        return coercionType;
    }

    public QueryGraphValueEntryRange getRangeInfo() {
        return rangeInfo;
    }

    public String toQueryPlan() {
        return " info " + rangeInfo.toQueryPlan() + " coercion " + coercionType;
    }

    public static String toQueryPlan(Collection<SubordPropRangeKey> rangeDescs) {
        StringWriter writer = new StringWriter();
        String delimiter = "";
        for (SubordPropRangeKey key : rangeDescs) {
            writer.append(delimiter);
            writer.append(key.toQueryPlan());
            delimiter = ", ";
        }
        return writer.toString();
    }
}
