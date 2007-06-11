using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.eql.join.plan
{
    /// <summary>
    /// Build query index plans.
    /// </summary>
    
    public class QueryPlanIndexBuilder
    {
        /// <summary>
        /// Build index specification from navigability info.
        /// <para>
        /// Looks at each stream and determines which properties in the stream must be indexed
        /// in order for other streams to look up into the stream. Determines the unique set of properties
        /// to avoid building duplicate indexes on the same set of properties.
        /// </para>
        /// </summary>
        /// <param name="queryGraph">navigability info</param>
        /// <returns>query index specs for each stream</returns>
        public static QueryPlanIndex[] BuildIndexSpec(QueryGraph queryGraph)
        {
            int numStreams = queryGraph.NumStreams;
            QueryPlanIndex[] indexSpecs = new QueryPlanIndex[numStreams];

            // For each stream compile a list of index property sets.
            for (int streamIndexed = 0; streamIndexed < numStreams; streamIndexed++)
            {
                Set<MultiKey<String>> indexesSet = new EHashSet<MultiKey<String>>();
                List<String[]> indexesList = new List<String[]>();

                // Look at the index from the viewpoint of the stream looking up in the index
                for (int streamLookup = 0; streamLookup < numStreams; streamLookup++)
                {
                    if (streamIndexed == streamLookup)
                    {
                        continue;
                    }

                    String[] indexProps = queryGraph.GetIndexProperties(streamLookup, streamIndexed);
                    if (indexProps != null)
                    {
                        // Eliminate duplicates by sorting and using a set
                        Array.Sort(indexProps);
                        MultiKey<String> indexPropsUniqueKey = new MultiKey<String>(indexProps);
                        if (!indexesSet.Contains(indexPropsUniqueKey))
                        {
                            indexesSet.Add(indexPropsUniqueKey);
                            indexesList.Add(indexProps);
                        }
                    }
                }

                // Copy the index properties for the stream to a QueryPlanIndex instance
                {
                    String[][] indexProps = null;
                    if (indexesSet.Count != 0)
                    {
                        indexProps = new String[indexesSet.Count][];
                        int count = 0;
                        foreach (String[] entry in indexesList)
                        {
                            indexProps[count] = entry;
                            count++;
                        }
                    }
                    else
                    {
                        // There are no indexes, create a default table for the event set
                        indexProps = new String[1][];
                        indexProps[0] = new String[0];
                    }
                    indexSpecs[streamIndexed] = new QueryPlanIndex(indexProps, new Type[indexProps.Length][]);
                }

                return indexSpecs;
            }
        }
    }
}
