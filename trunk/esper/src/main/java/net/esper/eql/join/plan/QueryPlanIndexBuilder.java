/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

import net.esper.collection.MultiKey;

import java.util.*;

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
    public static QueryPlanIndex[] buildIndexSpec(QueryGraph queryGraph)
    {
        int numStreams = queryGraph.getNumStreams();
        QueryPlanIndex[] indexSpecs = new QueryPlanIndex[numStreams];

        // For each stream compile a list of index property sets.
        for (int streamIndexed = 0; streamIndexed < numStreams; streamIndexed++)
        {
            Set<MultiKey<String>> indexesSet = new HashSet<MultiKey<String>>();
            List<String[]> indexesList = new LinkedList<String[]>();

            // Look at the index from the viewpoint of the stream looking up in the index
            for (int streamLookup = 0; streamLookup < numStreams; streamLookup++)
            {
                if (streamIndexed == streamLookup)
                {
                    continue;
                }

                String[] indexProps = queryGraph.getIndexProperties(streamLookup, streamIndexed);
                if (indexProps != null)
                {
                    // Eliminate duplicates by sorting and using a set
                    Arrays.sort(indexProps);
                    MultiKey<String> indexPropsUniqueKey = new MultiKey<String>(indexProps);
                    if (!indexesSet.contains(indexPropsUniqueKey))
                    {
                        indexesSet.add(indexPropsUniqueKey);
                        indexesList.add(indexProps);
                    }
                }
            }

            // Copy the index properties for the stream to a QueryPlanIndex instance
            String[][] indexProps = null;
            if (!indexesSet.isEmpty())
            {
                indexProps = new String[indexesSet.size()][];
                int count = 0;
                for (String[] entry : indexesList)
                {
                    indexProps[count] = entry;
                    count++;
                }
            }
            else
            {
                // There are no indexes, create a default table for the event set
                indexProps = new String[1][0];
                indexProps[0] = new String[0];
            }
            indexSpecs[streamIndexed] = new QueryPlanIndex(indexProps, new Class[indexProps.length][]);
        }

        return indexSpecs;
    }
}
