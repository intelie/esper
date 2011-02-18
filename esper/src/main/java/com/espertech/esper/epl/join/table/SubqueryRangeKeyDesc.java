package com.espertech.esper.epl.join.table;

import com.espertech.esper.epl.join.plan.QueryGraphValueRange;

public class SubqueryRangeKeyDesc {
    private int keyStreamNum;
    private int startStreamNum;
    private int endStreamNum;
    private QueryGraphValueRange rangeInfo;
    private Class coercionType;

    public static SubqueryRangeKeyDesc createSingleStreamNum(QueryGraphValueRange rangeKeyPair, int allKeyStreamNum, Class coercionType) {
        if (rangeKeyPair.getType().isRange()) {
            return new SubqueryRangeKeyDesc(allKeyStreamNum, allKeyStreamNum, rangeKeyPair, coercionType);
        }
        else {
            return new SubqueryRangeKeyDesc(allKeyStreamNum, rangeKeyPair, coercionType);
        }
    }

    public SubqueryRangeKeyDesc(int keyStreamNum, QueryGraphValueRange rangeInfo, Class coercionType) {
        if (rangeInfo.getType().isRange()) {
            throw new IllegalArgumentException("Ctor not applicable to ranges");
        }
        this.keyStreamNum = keyStreamNum;
        this.rangeInfo = rangeInfo;
        this.coercionType = coercionType;
    }

    public SubqueryRangeKeyDesc(int startStreamNum, int endStreamNum, QueryGraphValueRange rangeInfo, Class coercionType) {
        if (!rangeInfo.getType().isRange()) {
            throw new IllegalArgumentException("Ctor only applicable to ranges");
        }
        this.startStreamNum = startStreamNum;
        this.endStreamNum = endStreamNum;
        this.rangeInfo = rangeInfo;
        this.coercionType = coercionType;
    }

    public void setCoercionType(Class coercionType) {
        this.coercionType = coercionType;
    }

    public Class getCoercionType() {
        return coercionType;
    }

    public int getKeyStreamNum() {
        return keyStreamNum;
    }

    public void setKeyStreamNum(int keyStreamNum) {
        this.keyStreamNum = keyStreamNum;
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

    public void incStreamNum(int streamNumOffset) {
        keyStreamNum += streamNumOffset;
        startStreamNum += streamNumOffset;
        endStreamNum += streamNumOffset;
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
