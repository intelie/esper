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
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.lookup.SubordPropHashKey;
import com.espertech.esper.epl.lookup.SubordPropPlan;
import com.espertech.esper.epl.lookup.SubordPropRangeKey;
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

                QueryGraphValue value = queryGraph.getGraphValue(streamLookup, streamIndexed);
                QueryGraphValuePairHashKeyIndex hashKeyAndIndexProps = value.getHashKeyProps();

                // Sort index properties, but use the sorted properties only to eliminate duplicates
                String[] hashIndexProps = hashKeyAndIndexProps.getIndexed();
                List<QueryGraphValueEntryHashKeyed> hashKeyProps = hashKeyAndIndexProps.getKeys();
                CoercionDesc indexCoercionTypes = CoercionUtil.getCoercionTypesHash(typePerStream, streamLookup, streamIndexed, hashKeyProps, hashIndexProps);

                QueryGraphValuePairRangeIndex rangeAndIndexProps = value.getRangeProps();
                String[] rangeIndexProps = rangeAndIndexProps.getIndexed();
                List<QueryGraphValueEntryRange> rangeKeyProps = rangeAndIndexProps.getKeys();
                CoercionDesc rangeCoercionTypes = CoercionUtil.getCoercionTypesRange(typePerStream, streamIndexed, rangeIndexProps, rangeKeyProps);

                if (hashIndexProps.length == 0 && rangeIndexProps.length == 0) {
                    continue;
                }

                QueryPlanIndexItem proposed = new QueryPlanIndexItem(hashIndexProps, indexCoercionTypes.getCoercionTypes(), rangeIndexProps, rangeCoercionTypes.getCoercionTypes());
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

    public static SubordPropPlan getJoinProps(ExprNode filterExpr, int outsideStreamCount, EventType[] allStreamTypesZeroIndexed)
    {
        // No filter expression means full table scan
        if (filterExpr == null)
        {
            return new SubordPropPlan();
        }

        // analyze query graph
        QueryGraph queryGraph = new QueryGraph(outsideStreamCount + 1);
        FilterExprAnalyzer.analyze(filterExpr, queryGraph, false);

        // Build a list of streams and indexes
        LinkedHashMap<String, SubordPropHashKey> joinProps = new LinkedHashMap<String, SubordPropHashKey>();
        LinkedHashMap<String, SubordPropRangeKey> rangeProps = new LinkedHashMap<String, SubordPropRangeKey>();
        for (int stream = 0; stream <  outsideStreamCount; stream++)
        {
            int lookupStream = stream + 1;

            QueryGraphValue queryGraphValue = queryGraph.getGraphValue(lookupStream, 0);
            QueryGraphValuePairHashKeyIndex hashKeysAndIndexes = queryGraphValue.getHashKeyProps();

            // handle key-lookups
            List<QueryGraphValueEntryHashKeyed> keyPropertiesJoin = hashKeysAndIndexes.getKeys();
            String[] indexPropertiesJoin = hashKeysAndIndexes.getIndexed();
            if (!keyPropertiesJoin.isEmpty())
            {
                if (keyPropertiesJoin.size() != indexPropertiesJoin.length)
                {
                    throw new IllegalStateException("Invalid query key and index property collection for stream " + stream);
                }

                for (int i = 0; i < keyPropertiesJoin.size(); i++)
                {
                    QueryGraphValueEntryHashKeyed keyDesc = keyPropertiesJoin.get(i);
                    ExprNode compareNode = keyDesc.getKeyExpr();

                    Class keyPropType = JavaClassHelper.getBoxedType(compareNode.getExprEvaluator().getType());
                    Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(indexPropertiesJoin[i]));
                    Class coercionType = indexedPropType;
                    if (keyPropType != indexedPropType)
                    {
                        coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, indexedPropType);
                    }

                    SubordPropHashKey desc;
                    if (keyPropertiesJoin.get(i) instanceof QueryGraphValueEntryHashKeyedExpr) {
                        QueryGraphValueEntryHashKeyedExpr keyExpr = (QueryGraphValueEntryHashKeyedExpr) keyPropertiesJoin.get(i);
                        Integer keyStreamNum = keyExpr.isRequiresKey() ? stream : null;
                        desc = new SubordPropHashKey(keyDesc, keyStreamNum, coercionType);
                    }
                    else {
                        QueryGraphValueEntryHashKeyedProp prop = (QueryGraphValueEntryHashKeyedProp) keyDesc;
                        desc = new SubordPropHashKey(prop, stream, coercionType);
                    }
                    joinProps.put(indexPropertiesJoin[i], desc);
                }
            }

            // handle range lookups
            QueryGraphValuePairRangeIndex rangeKeysAndIndexes = queryGraphValue.getRangeProps();
            String[] rangeIndexes = rangeKeysAndIndexes.getIndexed();
            List<QueryGraphValueEntryRange> rangeDescs = rangeKeysAndIndexes.getKeys();
            if (rangeDescs.isEmpty()) {
                continue;
            }

            // get all ranges lookups
            int count = -1;
            for (QueryGraphValueEntryRange rangeDesc : rangeDescs) {
                count++;
                String rangeIndexProp = rangeIndexes[count];

                SubordPropRangeKey subqRangeDesc = rangeProps.get(rangeIndexProp);

                // other streams may specify the start or end endpoint of a range, therefore this operation can be additive
                if (subqRangeDesc != null) {
                    if (subqRangeDesc.getRangeInfo().getType().isRange()) {
                        continue;
                    }

                    // see if we can make this additive by using a range
                    QueryGraphValueEntryRangeRelOp relOpOther = (QueryGraphValueEntryRangeRelOp) subqRangeDesc.getRangeInfo();
                    QueryGraphValueEntryRangeRelOp relOpThis = (QueryGraphValueEntryRangeRelOp) rangeDesc;

                    QueryGraphRangeConsolidateDesc opsDesc = QueryGraphRangeUtil.getCanConsolidate(relOpThis.getType(), relOpOther.getType());
                    if (opsDesc != null) {
                        ExprNode start;
                        ExprNode end;
                        int streamNumStart;
                        int streamNumEnd;
                        if (!opsDesc.isReverse()) {
                            start = relOpOther.getExpression();
                            end = relOpThis.getExpression();
                            streamNumEnd = stream;
                        }
                        else {
                            start = relOpThis.getExpression();
                            streamNumStart = stream;
                            end = relOpOther.getExpression();
                        }
                        boolean allowRangeReversal = relOpOther.isBetweenPart() && relOpThis.isBetweenPart();
                        QueryGraphValueEntryRangeIn in = new QueryGraphValueEntryRangeIn(opsDesc.getType(), start, end, allowRangeReversal);

                        Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(rangeIndexProp));
                        Class coercionType = indexedPropType;
                        Class proposedType = CoercionUtil.getCoercionTypeRangeIn(indexedPropType, in.getExprStart(), in.getExprEnd());
                        if (proposedType != null && proposedType != indexedPropType)
                        {
                            coercionType = proposedType;
                        }

                        subqRangeDesc = new SubordPropRangeKey(in, coercionType);
                        rangeProps.put(rangeIndexProp, subqRangeDesc);
                    }
                    // ignore
                    continue;
                }

                // an existing entry has not been found
                if (rangeDesc.getType().isRange()) {
                    QueryGraphValueEntryRangeIn in = (QueryGraphValueEntryRangeIn) rangeDesc;
                    Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(rangeIndexProp));
                    Class coercionType = indexedPropType;
                    Class proposedType = CoercionUtil.getCoercionTypeRangeIn(indexedPropType, in.getExprStart(), in.getExprEnd());
                    if (proposedType != null && proposedType != indexedPropType)
                    {
                        coercionType = proposedType;
                    }
                    subqRangeDesc = new SubordPropRangeKey(rangeDesc, coercionType);
                }
                else {
                    QueryGraphValueEntryRangeRelOp relOp = (QueryGraphValueEntryRangeRelOp) rangeDesc;
                    Class keyPropType = relOp.getExpression().getExprEvaluator().getType();
                    Class indexedPropType = JavaClassHelper.getBoxedType(allStreamTypesZeroIndexed[0].getPropertyType(rangeIndexProp));
                    Class coercionType = indexedPropType;
                    if (keyPropType != indexedPropType)
                    {
                        coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, indexedPropType);
                    }
                    subqRangeDesc = new SubordPropRangeKey(rangeDesc, coercionType);
                }
                rangeProps.put(rangeIndexProp, subqRangeDesc);
            }

        }
        return new SubordPropPlan(joinProps, rangeProps);
    }
}
