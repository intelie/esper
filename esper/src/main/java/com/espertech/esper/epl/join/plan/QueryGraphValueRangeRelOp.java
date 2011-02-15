package com.espertech.esper.epl.join.plan;

public class QueryGraphValueRangeRelOp extends QueryGraphValueRange {

    private final String propertyKey;
    private final boolean isBetweenPart; // indicate that this is part of a between-clause or in-clause

    public QueryGraphValueRangeRelOp(QueryGraphRangeEnum type, String propertyKey, String propertyValue, boolean isBetweenPart) {
        super(type, propertyValue);
        if (type.isRange()) {
            throw new IllegalArgumentException("Invalid ctor for use with ranges");
        }
        this.propertyKey = propertyKey;
        this.isBetweenPart = isBetweenPart;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public boolean isBetweenPart() {
        return isBetweenPart;
    }

    public RangeKeyDesc getRangeKey() {
        return new RangeKeyDesc(getType(), propertyKey);
    }
}
