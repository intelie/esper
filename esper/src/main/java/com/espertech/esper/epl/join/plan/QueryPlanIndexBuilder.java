/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;
import com.espertech.esper.util.JavaClassHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Build query index plans.
 */
public class QueryPlanIndexBuilder
{
    /**
     * Build index specification from navigability info.
     * <p>
     * Looks at each stream and determines which properties in the stream must be indexed
     * in order for other streams to look up into the stream. Determines the unique set of properties
     * to avoid building duplicate indexes on the same set of properties.
     * @param queryGraph - navigability info
     * @return query index specs for each stream
     */
    public static QueryPlanIndex[] buildIndexSpec(QueryGraph queryGraph, EventType[] typePerStream)
    {
        int numStreams = queryGraph.getNumStreams();
        QueryPlanIndex[] indexSpecs = new QueryPlanIndex[numStreams];

        // For each stream compile a list of index property sets.
        for (int streamIndexed = 0; streamIndexed < numStreams; streamIndexed++)
        {
            List<QueryPlanIndexItem> indexesSet = new ArrayList<QueryPlanIndexItem>();

            // Look at the index from the viewpoint of the stream looking up in the index
            for (int streamLookup = 0; streamLookup < numStreams; streamLookup++)
            {
                if (streamIndexed == streamLookup)
                {
                    continue;
                }

                // Sort index properties, but use the sorted properties only to eliminate duplicates
                String[] indexProps = queryGraph.getIndexProperties(streamLookup, streamIndexed);
                String[] keyProps = queryGraph.getKeyProperties(streamLookup, streamIndexed);
                String[] rangeProps = queryGraph.getRangeProperties(streamLookup, streamIndexed);
                Class[] indexCoercionTypes = getCoercionTypes(typePerStream, streamLookup, streamIndexed, keyProps, indexProps);
                Class[] rangeCoercionTypes = getCoercionTypes(typePerStream, streamLookup, streamIndexed, queryGraph.getGraphValue(streamLookup, streamIndexed, false).getRangeEntries());

                if (indexProps == null && rangeProps == null) {
                    continue;
                }

                QueryPlanIndexItem proposed = new QueryPlanIndexItem(indexProps, indexCoercionTypes, rangeProps, rangeCoercionTypes);
                boolean found = false;
                for (QueryPlanIndexItem index : indexesSet) {
                    if (proposed.equalsCompareSortedProps(index)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    indexesSet.add(proposed);
                }
            }

            // Copy the index properties for the stream to a QueryPlanIndex instance
            if (indexesSet.isEmpty()) {
                indexesSet.add(new QueryPlanIndexItem(null, null, null, null));
            }

            indexSpecs[streamIndexed] = QueryPlanIndex.makeIndex(indexesSet);
        }

        return indexSpecs;
    }

    public static Class[] getCoercionTypes(EventType[] typesPerStream, int lookupStream, int indexedStream, List<QueryGraphValueRange> rangeEntries) {
        if (rangeEntries.isEmpty()) {
            return null;
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
                coercionType = getCoercionTypeRangeRelOp(valuePropType,
                                    relOp.getPropertyKey(), typesPerStream[lookupStream]);
            }

            if (coercionType != valuePropType) {
                mustCoerce = true;
            }
            coercionTypes[i] = coercionType;
        }
        if (!mustCoerce)
        {
            return null;
        }
        return coercionTypes;
    }

    private static Class getCoercionTypeRangeRelOp(Class valuePropType,
                                              String propertyKey, EventType eventTypeKey) {
        Class coercionType = null;
        Class keyPropType = JavaClassHelper.getBoxedType(eventTypeKey.getPropertyType(propertyKey));
        if (valuePropType != keyPropType)
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(valuePropType, keyPropType);
        }
        return coercionType;
    }

    private static Class getCoercionTypeRangeIn(Class valuePropType,
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
    public static Class[] getCoercionTypes(EventType[] typesPerStream,
                                            int lookupStream,
                                            int indexedStream,
                                            String[] keyProps,
                                            String[] indexProps)
    {
        if (indexProps == null && keyProps == null) {
            return null;
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
        if (!mustCoerce)
        {
            return null;
        }
        return coercionTypes;
    }

    public static Class getCoercionType(EventType indexedType, String indexedProp, SubqueryRangeKeyDesc rangeKey, EventType[] keyTypes) {
        RangeKeyDesc desc = rangeKey.getRangeKey();
        if (desc.getOp().isRange()) {
            return getCoercionTypeRangeIn(indexedType.getPropertyType(indexedProp),
                                    desc.getStart(), keyTypes[rangeKey.getStartStreamNum()],
                                    desc.getEnd(), keyTypes[rangeKey.getEndStreamNum()]);
        }
        else {
            return getCoercionTypeRangeRelOp(indexedType.getPropertyType(indexedProp),
                                    desc.getKey(), keyTypes[rangeKey.getKeyStreamNum()]);
        }
    }
}
