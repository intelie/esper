package com.espertech.esper.epl.named;

import com.espertech.esper.epl.join.plan.CoercionDesc;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.List;

public class IndexKeyInfo {

    private String[] OrderedKeyProperties;
    private CoercionDesc orderedKeyCoercionTypes;
    private List<SubqueryRangeKeyDesc> orderedRangeDesc;
    private CoercionDesc orderedRangeCoercionTypes;

    public IndexKeyInfo(String[] orderedKeyProperties, CoercionDesc orderedKeyCoercionTypes, List<SubqueryRangeKeyDesc> orderedRangeDesc, CoercionDesc orderedRangeCoercionTypes) {
        this.OrderedKeyProperties = orderedKeyProperties;
        this.orderedKeyCoercionTypes = orderedKeyCoercionTypes;
        this.orderedRangeDesc = orderedRangeDesc;
        this.orderedRangeCoercionTypes = orderedRangeCoercionTypes;
    }

    public String[] getOrderedKeyProperties() {
        return OrderedKeyProperties;
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
