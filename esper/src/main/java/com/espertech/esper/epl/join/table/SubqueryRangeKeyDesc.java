package com.espertech.esper.epl.join.table;

import com.espertech.esper.epl.join.plan.RangeKeyDesc;

public class SubqueryRangeKeyDesc {
    private int keyStreamNum;
    private int startStreamNum;
    private int endStreamNum;
    private RangeKeyDesc rangeKey;

    public static SubqueryRangeKeyDesc createZeroStreamNum(RangeKeyDesc rangeKeyPair) {
        if (rangeKeyPair.getOp().isRange()) {
            return new SubqueryRangeKeyDesc(0, 0, rangeKeyPair);
        }
        else {
            return new SubqueryRangeKeyDesc(0, rangeKeyPair);
        }
    }

    public SubqueryRangeKeyDesc(int keyStreamNum, RangeKeyDesc rangeKey) {
        if (rangeKey.getOp().isRange()) {
            throw new IllegalArgumentException("Ctor not applicable to ranges");
        }
        this.keyStreamNum = keyStreamNum;
        this.rangeKey = rangeKey;
    }

    public SubqueryRangeKeyDesc(int startStreamNum, int endStreamNum, RangeKeyDesc rangeKey) {
        if (!rangeKey.getOp().isRange()) {
            throw new IllegalArgumentException("Ctor only applicable to ranges");
        }
        this.startStreamNum = startStreamNum;
        this.endStreamNum = endStreamNum;
        this.rangeKey = rangeKey;
    }

    public int getKeyStreamNum() {
        return keyStreamNum;
    }

    public int getStartStreamNum() {
        return startStreamNum;
    }

    public int getEndStreamNum() {
        return endStreamNum;
    }

    public RangeKeyDesc getRangeKey() {
        return rangeKey;
    }

    @Override
    public String toString() {
        return "SubqueryRangeKeyDesc{" +
                "keyStreamNum=" + keyStreamNum +
                ", startStreamNum=" + startStreamNum +
                ", endStreamNum=" + endStreamNum +
                ", rangeKey=" + rangeKey +
                '}';
    }
}
