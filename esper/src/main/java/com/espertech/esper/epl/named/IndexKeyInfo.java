package com.espertech.esper.epl.named;

import com.espertech.esper.epl.join.plan.CoercionDesc;
import com.espertech.esper.epl.lookup.SubordPropHashKey;
import com.espertech.esper.epl.lookup.SubordPropRangeKey;

import java.util.List;

public class IndexKeyInfo {

    private List<SubordPropHashKey> orderedKeyProperties;
    private CoercionDesc orderedKeyCoercionTypes;
    private List<SubordPropRangeKey> orderedRangeDesc;
    private CoercionDesc orderedRangeCoercionTypes;

    public IndexKeyInfo(List<SubordPropHashKey> orderedKeyProperties, CoercionDesc orderedKeyCoercionTypes, List<SubordPropRangeKey> orderedRangeDesc, CoercionDesc orderedRangeCoercionTypes) {
        this.orderedKeyProperties = orderedKeyProperties;
        this.orderedKeyCoercionTypes = orderedKeyCoercionTypes;
        this.orderedRangeDesc = orderedRangeDesc;
        this.orderedRangeCoercionTypes = orderedRangeCoercionTypes;
    }

    public List<SubordPropHashKey> getOrderedHashProperties() {
        return orderedKeyProperties;
    }

    public CoercionDesc getOrderedKeyCoercionTypes() {
        return orderedKeyCoercionTypes;
    }

    public List<SubordPropRangeKey> getOrderedRangeDesc() {
        return orderedRangeDesc;
    }

    public CoercionDesc getOrderedRangeCoercionTypes() {
        return orderedRangeCoercionTypes;
    }
}
