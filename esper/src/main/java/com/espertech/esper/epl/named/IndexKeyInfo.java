package com.espertech.esper.epl.named;

import com.espertech.esper.epl.join.plan.CoercionDesc;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.List;

public class IndexKeyInfo {

    private String[] orderedKeyProperties;
    private int[] orderedKeyStreamNums;
    private CoercionDesc orderedKeyCoercionTypes;
    private List<SubqueryRangeKeyDesc> orderedRangeDesc;
    private CoercionDesc orderedRangeCoercionTypes;

    public IndexKeyInfo(String[] orderedKeyProperties, int[] orderedKeyStreamNums, CoercionDesc orderedKeyCoercionTypes, List<SubqueryRangeKeyDesc> orderedRangeDesc, CoercionDesc orderedRangeCoercionTypes) {
        this.orderedKeyProperties = orderedKeyProperties;
        this.orderedKeyStreamNums = orderedKeyStreamNums;
        this.orderedKeyCoercionTypes = orderedKeyCoercionTypes;
        this.orderedRangeDesc = orderedRangeDesc;
        this.orderedRangeCoercionTypes = orderedRangeCoercionTypes;
    }

    public String[] getOrderedKeyProperties() {
        return orderedKeyProperties;
    }

    public int[] getOrderedKeyStreamNums() {
        return orderedKeyStreamNums;
    }

    public CoercionDesc getOrderedKeyCoercionTypes() {
        return orderedKeyCoercionTypes;
    }

    public List<SubqueryRangeKeyDesc> getOrderedRangeDesc() {
        return orderedRangeDesc;
    }

    public CoercionDesc getOrderedRangeCoercionTypes() {
        return orderedRangeCoercionTypes;
    }
}
