package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;
import com.espertech.esper.util.JavaClassHelper;

import java.util.List;
import java.util.Map;

public class CoercionUtil {

    private static final Class[] NULL_ARRAY = new Class[0];

    public static CoercionDesc getCoercionTypes(EventType[] typesPerStream, int lookupStream, int indexedStream, List<QueryGraphValueRange> rangeEntries) {
        if (rangeEntries.isEmpty()) {
            return new CoercionDesc(false, NULL_ARRAY);
        }

        Class[] coercionTypes = new Class[rangeEntries.size()];
        boolean mustCoerce = false;
        for (int i = 0; i < rangeEntries.size(); i++)
        {
            QueryGraphValueRange entry = rangeEntries.get(i);

            Class valuePropType = JavaClassHelper.getBoxedType(typesPerStream[indexedStream].getPropertyType(entry.getPropertyValue()));
            Class coercionType;

            if (entry.getType().isRange()) {
                QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) entry;
                coercionType = getCoercionTypeRangeIn(valuePropType,
                                        in.getPropertyStart(), typesPerStream[lookupStream],
                                        in.getPropertyEnd(), typesPerStream[lookupStream]);
            }
            else {
                QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) entry;
                coercionType = getCoercionType(valuePropType,
                                    relOp.getPropertyKey(), typesPerStream[lookupStream]);
            }

            if (coercionType == null) {
                coercionTypes[i] = valuePropType;
            }
            else {
                mustCoerce = true;
                coercionTypes[i] = coercionType;
            }
        }

        return new CoercionDesc(mustCoerce, coercionTypes);
    }

    /**
     * Returns null if no coercion is required, or an array of classes for use in coercing the
     * lookup keys and index keys into a common type.
     * @param typesPerStream is the event types for each stream
     * @param lookupStream is the stream looked up from
     * @param indexedStream is the indexed stream
     * @param keyProps is the properties to use to look up
     * @param indexProps is the properties to index on
     * @return coercion types, or null if none required
     */
    public static CoercionDesc getCoercionTypes(EventType[] typesPerStream,
                                            int lookupStream,
                                            int indexedStream,
                                            String[] keyProps,
                                            String[] indexProps)
    {
        if (indexProps == null && keyProps == null) {
            return new CoercionDesc(false, NULL_ARRAY);
        }
        if (indexProps.length != keyProps.length)
        {
            throw new IllegalStateException("Mismatch in the number of key and index properties");
        }

        Class[] coercionTypes = new Class[indexProps.length];
        boolean mustCoerce = false;
        for (int i = 0; i < keyProps.length; i++)
        {
            Class keyPropType = JavaClassHelper.getBoxedType(typesPerStream[lookupStream].getPropertyType(keyProps[i]));
            Class indexedPropType = JavaClassHelper.getBoxedType(typesPerStream[indexedStream].getPropertyType(indexProps[i]));
            Class coercionType = indexedPropType;
            if (keyPropType != indexedPropType)
            {
                coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, indexedPropType);
                mustCoerce = true;
            }
            coercionTypes[i] = coercionType;
        }
        return new CoercionDesc(mustCoerce, coercionTypes);
    }

    public static Class getCoercionType(EventType indexedType, String indexedProp, EventType keyType, String key) {
        return getCoercionType(indexedType.getPropertyType(indexedProp), key, keyType);
    }

    public static Class getCoercionType(EventType indexedType, String indexedProp, SubqueryRangeKeyDesc rangeKey, EventType[] keyTypes) {
        QueryGraphValueRange desc = rangeKey.getRangeInfo();
        if (desc.getType().isRange()) {
            QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) desc;
            return getCoercionTypeRangeIn(indexedType.getPropertyType(indexedProp),
                                    in.getPropertyStart(), keyTypes[rangeKey.getStartStreamNum()],
                                    in.getPropertyEnd(), keyTypes[rangeKey.getEndStreamNum()]);
        }
        else {
            QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) desc;
            return getCoercionType(indexedType.getPropertyType(indexedProp),
                                    relOp.getPropertyKey(), keyTypes[rangeKey.getKeyStreamNum()]);
        }
    }

    public static CoercionDesc getCoercionTypes(EventType viewableEventType, Map<String, SubqueryRangeKeyDesc> rangeProps, EventType[] typesPerStream) {
        if (rangeProps.isEmpty()) {
            return new CoercionDesc(false, NULL_ARRAY);
        }

        Class[] coercionTypes = new Class[rangeProps.size()];
        boolean mustCoerce = false;
        int count = 0;
        for (Map.Entry<String, SubqueryRangeKeyDesc> entry : rangeProps.entrySet())
        {
            SubqueryRangeKeyDesc subQRange = entry.getValue();
            QueryGraphValueRange rangeDesc = entry.getValue().getRangeInfo();

            Class valuePropType = JavaClassHelper.getBoxedType(viewableEventType.getPropertyType(entry.getKey()));
            Class coercionType;

            if (rangeDesc.getType().isRange()) {
                QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) rangeDesc;
                coercionType = getCoercionTypeRangeIn(valuePropType,
                                        in.getPropertyStart(), typesPerStream[subQRange.getStartStreamNum()],
                                        in.getPropertyEnd(), typesPerStream[subQRange.getEndStreamNum()]);
            }
            else {
                QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) rangeDesc;
                coercionType = getCoercionType(valuePropType,
                                    relOp.getPropertyKey(), typesPerStream[subQRange.getKeyStreamNum()]);
            }

            if (coercionType == null) {
                coercionTypes[count++] = valuePropType;
            }
            else {
                mustCoerce = true;
                coercionTypes[count++] = coercionType;
            }
        }
        return new CoercionDesc(mustCoerce, coercionTypes);
    }

    private static Class getCoercionType(Class valuePropType, String propertyKey, EventType eventTypeKey) {
        Class coercionType = null;
        Class keyPropType = JavaClassHelper.getBoxedType(eventTypeKey.getPropertyType(propertyKey));
        if (valuePropType != keyPropType)
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(valuePropType, keyPropType);
        }
        return coercionType;
    }

    public static Class getCoercionTypeRangeIn(Class valuePropType,
                                              String propertyStart, EventType eventTypeStart,
                                              String propertyEnd, EventType eventTypeEnd) {
        Class coercionType = null;
        Class startPropType = JavaClassHelper.getBoxedType(eventTypeStart.getPropertyType(propertyStart));
        Class endPropType = JavaClassHelper.getBoxedType(eventTypeEnd.getPropertyType(propertyEnd));

        if (valuePropType != startPropType)
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(valuePropType, startPropType);
        }
        if (valuePropType != endPropType)
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(coercionType, endPropType);
        }
        return coercionType;
    }
}
