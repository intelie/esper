package com.espertech.esper.epl.join.table;

import com.espertech.esper.epl.join.plan.QueryGraphValueRange;

public class SubqueryRangeKeyDesc {
    private int keyStreamNum;
    private int startStreamNum;
    private int endStreamNum;
    private QueryGraphValueRange rangeInfo;

    public static SubqueryRangeKeyDesc createSingleStreamNum(QueryGraphValueRange rangeKeyPair, int allKeyStreamNum) {
        if (rangeKeyPair.getType().isRange()) {
            return new SubqueryRangeKeyDesc(allKeyStreamNum, allKeyStreamNum, rangeKeyPair);
        }
        else {
            return new SubqueryRangeKeyDesc(allKeyStreamNum, rangeKeyPair);
        }
    }

    public SubqueryRangeKeyDesc(int keyStreamNum, QueryGraphValueRange rangeInfo) {
        if (rangeInfo.getType().isRange()) {
            throw new IllegalArgumentException("Ctor not applicable to ranges");
        }
        this.keyStreamNum = keyStreamNum;
        this.rangeInfo = rangeInfo;
    }

    public SubqueryRangeKeyDesc(int startStreamNum, int endStreamNum, QueryGraphValueRange rangeInfo) {
        if (!rangeInfo.getType().isRange()) {
            throw new IllegalArgumentException("Ctor only applicable to ranges");
        }
        this.startStreamNum = startStreamNum;
        this.endStreamNum = endStreamNum;
        this.rangeInfo = rangeInfo;
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

    public QueryGraphValueRange getRangeInfo() {
        return rangeInfo;
    }

    @Override
    public String toString() {
        return "SubqueryRangeKeyDesc{" +
                "keyStreamNum=" + keyStreamNum +
                ", startStreamNum=" + startStreamNum +
                ", endStreamNum=" + endStreamNum +
                ", rangeKey=" + rangeInfo +
                '}';
    }
}
