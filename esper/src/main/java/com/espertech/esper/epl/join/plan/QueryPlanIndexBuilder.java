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
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;
import com.espertech.esper.epl.lookup.JoinedPropDesc;
import com.espertech.esper.epl.lookup.JoinedPropPlan;
import com.espertech.esper.util.JavaClassHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
                CoercionDesc indexCoercionTypes = CoercionUtil.getCoercionTypes(typePerStream, streamLookup, streamIndexed, keyProps, indexProps);
                CoercionDesc rangeCoercionTypes = CoercionUtil.getCoercionTypes(typePerStream, streamLookup, streamIndexed, queryGraph.getGraphValue(streamLookup, streamIndexed, false).getRangeEntries());

                if (indexProps == null && rangeProps == null) {
                    continue;
                }

                QueryPlanIndexItem proposed = new QueryPlanIndexItem(indexProps, indexCoercionTypes.getCoercionTypes(), rangeProps, rangeCoercionTypes.getCoercionTypes());
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

    public static JoinedPropPlan getJoinProps(ExprNode filterExpr, int outsideStreamCount, EventType[] allStreamTypesZeroIndexed)
    {
        // No filter expression means full table scan
        if (filterExpr == null)
        {
            return new JoinedPropPlan();
        }

        // analyze query graph
        QueryGraph queryGraph = new QueryGraph(outsideStreamCount + 1);
        FilterExprAnalyzer.analyze(filterExpr, queryGraph);

        // Build a list of streams and indexes
        LinkedHashMap<String, JoinedPropDesc> joinProps = new LinkedHashMap<String, JoinedPropDesc>();
        LinkedHashMap<String, SubqueryRangeKeyDesc> rangeProps = new LinkedHashMap<String, SubqueryRangeKeyDesc>();
        boolean mustCoerce = false;
        for (int stream = 0; stream <  outsideStreamCount; stream++)
        {
            int lookupStream = stream + 1;

            // handle key-lookups
            String[] keyPropertiesJoin = queryGraph.getKeyProperties(lookupStream, 0);
            String[] indexPropertiesJoin = queryGraph.getIndexProperties(lookupStream, 0);
            if ((keyPropertiesJoin != null) && (keyPropertiesJoin.length != 0))
            {
                if (keyPropertiesJoin.length != indexPropertiesJoin.length)
                {
                    throw new IllegalStateException("Invalid query key and index property collection for stream " + stream);
                }

                for (int i = 0; i < keyPropertiesJoin.length; i++)
                {
                    Class keyPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[lookupStream].getPropertyType(keyPropertiesJoin[i]));
                    Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(indexPropertiesJoin[i]));
                    Class coercionType = indexedPropType;
                    if (keyPropType != indexedPropType)
                    {
                        coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, indexedPropType);
                        mustCoerce = true;
                    }

                    JoinedPropDesc desc = new JoinedPropDesc(indexPropertiesJoin[i], coercionType, keyPropertiesJoin[i], stream);
                    joinProps.put(indexPropertiesJoin[i], desc);
                }
            }

            // handle range lookups
            List<QueryGraphValueRange> rangeDescs = queryGraph.getGraphValue(lookupStream, 0, false).getRangeEntries();
            if (rangeDescs.isEmpty()) {
                continue;
            }

            // get all ranges lookups
            for (QueryGraphValueRange rangeDesc : rangeDescs) {

                SubqueryRangeKeyDesc subqRangeDesc = rangeProps.get(rangeDesc.getPropertyValue());

                // other streams may specify the start or end endpoint of a range, therefore this operation can be additive
                if (subqRangeDesc != null) {
                    if (subqRangeDesc.getRangeInfo().getType().isRange()) {
                        continue;
                    }

                    // see if we can make this additive by using a range
                    QueryGraphValueRangeRelOp relOpOther = (QueryGraphValueRangeRelOp) subqRangeDesc.getRangeInfo();
                    QueryGraphValueRangeRelOp relOpThis = (QueryGraphValueRangeRelOp) rangeDesc;

                    QueryGraphRangeConsolidateDesc opsDesc = QueryGraphRangeUtil.getCanConsolidate(relOpThis.getType(), relOpOther.getType());
                    if (opsDesc != null) {
                        String start;
                        String end;
                        int streamNumStart;
                        int streamNumEnd;
                        if (!opsDesc.isReverse()) {
                            start = relOpOther.getPropertyKey();
                            streamNumStart = subqRangeDesc.getKeyStreamNum();
                            end = relOpThis.getPropertyKey();
                            streamNumEnd = stream;
                        }
                        else {
                            start = relOpThis.getPropertyKey();
                            streamNumStart = stream;
                            end = relOpOther.getPropertyKey();
                            streamNumEnd = subqRangeDesc.getKeyStreamNum();
                        }
                        boolean allowRangeReversal = relOpOther.isBetweenPart() && relOpThis.isBetweenPart();
                        QueryGraphValueRangeIn in = new QueryGraphValueRangeIn(opsDesc.getType(), start, end, rangeDesc.getPropertyValue(), allowRangeReversal);

                        Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(in.getPropertyValue()));
                        Class coercionType = indexedPropType;
                        Class proposedType = CoercionUtil.getCoercionTypeRangeIn(indexedPropType, in.getPropertyStart(), allStreamTypesZeroIndexed[streamNumStart+1], in.getPropertyEnd(), allStreamTypesZeroIndexed[streamNumEnd+1]);
                        if (proposedType != null && proposedType != indexedPropType)
                        {
                            coercionType = proposedType;
                        }

                        subqRangeDesc = new SubqueryRangeKeyDesc(streamNumStart, streamNumEnd, in, coercionType);
                        rangeProps.put(relOpOther.getPropertyValue(), subqRangeDesc);
                    }
                    // ignore
                    continue;
                }

                // an existing entry has not been found
                if (rangeDesc.getType().isRange()) {
                    QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) rangeDesc;
                    Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(in.getPropertyValue()));
                    Class coercionType = indexedPropType;
                    Class proposedType = CoercionUtil.getCoercionTypeRangeIn(indexedPropType, in.getPropertyStart(), allStreamTypesZeroIndexed[stream+1], in.getPropertyEnd(), allStreamTypesZeroIndexed[stream+1]);
                    if (proposedType != null && proposedType != indexedPropType)
                    {
                        coercionType = proposedType;
                    }
                    subqRangeDesc = new SubqueryRangeKeyDesc(stream, stream, rangeDesc, coercionType);
                }
                else {
                    QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) rangeDesc;
                    Class keyPropType = allStreamTypesZeroIndexed[lookupStream].getPropertyType(relOp.getPropertyKey());
                    Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(relOp.getPropertyValue()));
                    Class coercionType = indexedPropType;
                    if (keyPropType != indexedPropType)
                    {
                        coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, indexedPropType);
                    }
                    subqRangeDesc = new SubqueryRangeKeyDesc(stream, rangeDesc, coercionType);
                }
                rangeProps.put(rangeDesc.getPropertyValue(), subqRangeDesc);
            }

        }
        return new JoinedPropPlan(joinProps, rangeProps, mustCoerce);
    }
}
