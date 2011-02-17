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
}
