package com.espertech.esper.epl.join.plan;

public class RangeKeyDesc {
    private QueryGraphRangeEnum op;
    private String key;
    private String start;
    private String end;
    private boolean allowRangeReversal; // indicate whether "a between 60 and 50" should return no results (false, equivalent to a>= X and a <=Y) or should return results (true, equivalent to 'between' and 'in')

    public RangeKeyDesc(QueryGraphRangeEnum op, String key) {
        if (op.isRange()) {
            throw new IllegalArgumentException("Invalid ctor for range operation: " + op);
        }
        this.op = op;
        this.key = key;
    }

    public RangeKeyDesc(QueryGraphRangeEnum rangeOp, String start, String end, boolean allowRangeReversal) {
        if (!rangeOp.isRange()) {
            throw new IllegalArgumentException("Not a range operation: " + rangeOp);
        }
        this.op = rangeOp;
        this.start = start;
        this.end = end;
        this.allowRangeReversal = allowRangeReversal;
    }

    public boolean isAllowRangeReversal() {
        return allowRangeReversal;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public QueryGraphRangeEnum getOp() {
        return op;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "RangeKeyPair{" +
                "op=" + op +
                ", key='" + key + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
