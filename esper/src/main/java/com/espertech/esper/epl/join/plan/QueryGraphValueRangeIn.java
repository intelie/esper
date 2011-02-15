package com.espertech.esper.epl.join.plan;

public class QueryGraphValueRangeIn extends QueryGraphValueRange {

    private String propertyStart;
    private String propertyEnd;
    private boolean allowRangeReversal; // indicate whether "a between 60 and 50" should return no results (false, equivalent to a>= X and a <=Y) or should return results (true, equivalent to 'between' and 'in')

    public QueryGraphValueRangeIn(QueryGraphRangeEnum rangeType, String propertyStart, String propertyEnd, String propertyValue, boolean allowRangeReversal) {
        super(rangeType, propertyValue);
        if (!rangeType.isRange()) {
            throw new IllegalArgumentException("Range type expected but received " + rangeType.name());
        }
        this.propertyStart = propertyStart;
        this.propertyEnd = propertyEnd;
        this.allowRangeReversal = allowRangeReversal;
    }

    public boolean isAllowRangeReversal() {
        return allowRangeReversal;
    }

    public String getPropertyStart() {
        return propertyStart;
    }

    public String getPropertyEnd() {
        return propertyEnd;
    }

    public RangeKeyDesc getRangeKey() {
        return new RangeKeyDesc(getType(), propertyStart, propertyEnd, allowRangeReversal);
    }
}
