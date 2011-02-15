package com.espertech.esper.epl.join.plan;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryGraphValueRange {

    private final QueryGraphRangeEnum type;
    private final String propertyValue;

    public abstract RangeKeyDesc getRangeKey();

    protected QueryGraphValueRange(QueryGraphRangeEnum type, String propertyValue) {
        this.type = type;
        this.propertyValue = propertyValue;
    }

    public QueryGraphRangeEnum getType() {
        return type;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public static String[] getPropertyNamesValues(List<QueryGraphValueRange> ranges) {
        String[] props = new String[ranges.size()];
        int count = 0;
        for (QueryGraphValueRange item : ranges) {
            props[count] = item.getPropertyValue();
            count++;
        }
        return props;
    }

    public static List<RangeKeyDesc> getPropertyNamesRangeKey(List<QueryGraphValueRange> ranges) {
        List<RangeKeyDesc> result = new ArrayList<RangeKeyDesc>();
        for (QueryGraphValueRange item : ranges) {
            if (item.getType().isRange()) {
                QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) item;
                result.add(new RangeKeyDesc(item.getType(), in.getPropertyStart(), in.getPropertyEnd(), in.isAllowRangeReversal()));
            }
            else {
                QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) item;
                result.add(new RangeKeyDesc(item.getType(), relOp.getPropertyKey()));
            }
        }
        return result;
    }
}
