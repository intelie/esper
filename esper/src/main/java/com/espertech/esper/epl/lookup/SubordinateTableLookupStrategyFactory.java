package com.espertech.esper.epl.lookup;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.CoercionDesc;
import com.espertech.esper.epl.join.table.*;

import java.util.List;

public class SubordinateTableLookupStrategyFactory {

    public static SubordTableLookupStrategy getLookupStrategy(EventType[] outerStreamTypesZeroIndexed,
                                                             List<SubordPropHashKey> hashKeys,
                                                             CoercionDesc hashKeyCoercionTypes,
                                                             List<SubordPropRangeKey> rangeKeys,
                                                             CoercionDesc rangeKeyCoercionTypes,
                                                             boolean isNWOnTrigger,
                                                             EventTable eventTable) {


        boolean isStrictKeys = SubordPropUtil.isStrictKeyJoin(hashKeys);
        String[] hashStrictKeys = null;
        int[] hashStrictKeyStreams = null;
        if (isStrictKeys) {
            hashStrictKeyStreams = SubordPropUtil.getKeyStreamNums(hashKeys);
            hashStrictKeys = SubordPropUtil.getKeyProperties(hashKeys);
        }

        int numStreamsTotal = outerStreamTypesZeroIndexed.length + 1;
        SubordTableLookupStrategy lookupStrategy;
        if (hashKeys.isEmpty() && rangeKeys.isEmpty()) {
            lookupStrategy = new SubordFullTableScanLookupStrategy( (UnindexedEventTable) eventTable);
        }
        else if (hashKeys.size() > 0 && rangeKeys.isEmpty()) {
            if (hashKeys.size() == 1) {
                if (!hashKeyCoercionTypes.isCoerce()) {
                    if (isStrictKeys) {
                        lookupStrategy = new SubordIndexedTableLookupStrategySingleProp(isNWOnTrigger, outerStreamTypesZeroIndexed, hashStrictKeyStreams[0], hashStrictKeys[0], (PropertyIndexedEventTableSingle) eventTable);
                    }
                    else {
                        lookupStrategy = new SubordIndexedTableLookupStrategySingleExpr(isNWOnTrigger, numStreamsTotal, hashKeys.get(0), (PropertyIndexedEventTableSingle) eventTable);
                    }
                }
                else {
                    lookupStrategy = new SubordIndexedTableLookupStrategySingleCoercing(isNWOnTrigger, numStreamsTotal, hashKeys.get(0), (PropertyIndexedEventTableSingle) eventTable, hashKeyCoercionTypes.getCoercionTypes()[0]);
                }
            }
            else {
                if (!hashKeyCoercionTypes.isCoerce()) {
                    if (isStrictKeys) {
                        lookupStrategy = new SubordIndexedTableLookupStrategyProp(isNWOnTrigger, outerStreamTypesZeroIndexed, hashStrictKeyStreams, hashStrictKeys, (PropertyIndexedEventTable) eventTable);
                    }
                    else {
                        lookupStrategy = new SubordIndexedTableLookupStrategyExpr(isNWOnTrigger, numStreamsTotal, hashKeys, (PropertyIndexedEventTable) eventTable);
                    }
                }
                else {
                    lookupStrategy = new SubordIndexedTableLookupStrategyCoercing(isNWOnTrigger, numStreamsTotal, hashKeys, (PropertyIndexedEventTable) eventTable, hashKeyCoercionTypes.getCoercionTypes());
                }
            }
        }
        else if (hashKeys.size() == 0 && rangeKeys.size() == 1) {
            lookupStrategy = new SubordSortedTableLookupStrategy(isNWOnTrigger, numStreamsTotal, rangeKeys.get(0), (PropertySortedEventTable) eventTable);
        }
        else {
            lookupStrategy = new SubordCompositeTableLookupStrategy(isNWOnTrigger, numStreamsTotal, hashKeys, hashKeyCoercionTypes.getCoercionTypes(),
                    rangeKeys, rangeKeyCoercionTypes.getCoercionTypes(), (PropertyCompositeEventTable) eventTable);
        }
        return lookupStrategy;
    }
}
