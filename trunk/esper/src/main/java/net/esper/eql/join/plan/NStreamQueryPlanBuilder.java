/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

import java.util.Arrays;

import net.esper.collection.NumberSetPermutationEnumeration;
import net.esper.event.EventType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 2 Stream query strategy/execution tree
 (stream 0)         Lookup in stream 1
 (stream 1)         Lookup in stream 0

 * ------ Example 1   a 3 table join
 *
 *          " where streamA.id = streamB.id " +
            "   and streamB.id = streamC.id";

 => Index propery names for each stream
    for stream 0 to 4 = "id"

 => join order, ie.
    for stream 0 = {1, 2}
    for stream 1 = {factor [0,2]}
    for stream 2 = {1, 0}

 => IndexKeyGen optionalIndexKeyGen, created by nested query plan nodes


 3 Stream query strategy
 (stream 0)          Nested iteration
    Lookup in stream 1        Lookup in stream 2

 (stream 1)         Factor
    Lookup in stream 0        Lookup in stream 2

 (stream 2)         Nested iteration
    Lookup in stream 1        Lookup in stream 0


 * ------ Example 2  a 4 table join
 *
 *          " where streamA.id = streamB.id " +
            "   and streamB.id = streamC.id";
            "   and streamC.id = streamD.id";

 => join order, ie.
    for stream 0 = {1, 2, 3}
    for stream 1 = {factor [0,2], use 2 for 3}
    for stream 2 = {factor [1,3], use 1 for 0}
    for stream 3 = {2, 1, 0}


 concepts... nested iteration, inner loop

 select * from s1, s2, s3, s4 where s1.id=s2.id and s2.id=s3.id and s3.id=s4.id


 (stream 0)              Nested iteration
    Lookup in stream 1        Lookup in stream 2        Lookup in stream 3

 (stream 1)              Factor
 lookup in stream 0                 Nested iteration
                          Lookup in stream 2        Lookup in stream 3

 (stream 2)              Factor
 lookup in stream 3                 Nested iteration
                          Lookup in stream 1        Lookup in stream 0

 (stream 3)              Nested iteration
    Lookup in stream 2        Lookup in stream 1        Lookup in stream 0

 * ------ Example 4  a 4 table join, orphan table
 *
 *          " where streamA.id = streamB.id " +
            "   and streamB.id = streamC.id"; (no table D join criteria)

 * ------ Example 5  a 3 table join with 2 indexes for stream B
 *
 *          " where streamA.A1 = streamB.B1 " +
            "   and streamB.B2 = streamC.C1"; (no table D join criteria)
 */

/**
 * Builds a query plan for 3 or more streams in a join.
 */
public class NStreamQueryPlanBuilder
{
    /**
     * Build a query plan based on the stream property relationships indicated in queryGraph.
     * @param queryGraph - navigation info between streams
     * @return query plan
     */
    protected static QueryPlan build(QueryGraph queryGraph)
    {
        log.debug(".build queryGraph=" + queryGraph);

        int numStreams = queryGraph.getNumStreams();
        QueryPlanIndex[] indexSpecs = QueryPlanIndexBuilder.buildIndexSpec(queryGraph);
        log.debug(".build Index build completed, indexes=" + QueryPlanIndex.print(indexSpecs));

        QueryPlanNode[] planNodeSpecs = new QueryPlanNode[numStreams];
        for (int streamNo = 0; streamNo < numStreams; streamNo++)
        {
            BestChainResult bestChainResult = computeBestPath(streamNo, queryGraph);
            int[] bestChain = bestChainResult.getChain();
            log.debug(".build For stream " + streamNo + " bestChain=" + Arrays.toString(bestChain));

            planNodeSpecs[streamNo] = createStreamPlan(streamNo, bestChain, queryGraph, indexSpecs);
            log.debug(".build spec=" + planNodeSpecs[streamNo]);
        }

        return new QueryPlan(indexSpecs, planNodeSpecs);
    }

    /**
     * Walks the chain of lookups and constructs lookup strategy and plan specification based
     * on the index specifications.
     * @param lookupStream - the stream to construct the query plan for
     * @param bestChain - the chain that the lookup follows to make best use of indexes
     * @param queryGraph - the repository for key properties to indexes
     * @param indexSpecsPerStream - specifications of indexes
     * @return NestedIterationNode with lookups attached underneath
     */
    protected static QueryPlanNode createStreamPlan(int lookupStream, int[] bestChain, QueryGraph queryGraph,
                                                        QueryPlanIndex[] indexSpecsPerStream)
    {
        NestedIterationNode nestedIterNode = new NestedIterationNode(bestChain);
        int currentLookupStream = lookupStream;

        // Walk through each successive lookup
        for (int i = 0; i < bestChain.length; i++)
        {
            int indexedStream = bestChain[i];

            TableLookupPlan tableLookupPlan = createLookupPlan(queryGraph, currentLookupStream, indexedStream, indexSpecsPerStream[indexedStream]);
            TableLookupNode tableLookupNode = new TableLookupNode(tableLookupPlan);
            nestedIterNode.addChildNode(tableLookupNode);

            currentLookupStream = bestChain[i];
        }

        return nestedIterNode;
    }

    /**
     * Create the table lookup plan for a from-stream to look up in an indexed stream
     * using the columns supplied in the query graph and looking at the actual indexes available
     * and their index number.
     * @param queryGraph - contains properties joining the 2 streams
     * @param currentLookupStream - stream to use key values from
     * @param indexedStream - stream to look up in
     * @param indexSpecs - index specification defining indexes to be created for stream
     * @return plan for performing a lookup in a given table using one of the indexes supplied
     */
    protected static TableLookupPlan createLookupPlan(QueryGraph queryGraph, int currentLookupStream, int indexedStream,
                                               QueryPlanIndex indexSpecs)
    {
        String[] indexedStreamIndexProps = queryGraph.getIndexProperties(currentLookupStream, indexedStream);
        int indexNum = -1;

        // We use an index if there are index properties for the 2 streams
        TableLookupPlan tableLookupPlan = null;

        if (indexedStreamIndexProps != null)
        {
            // Determine the index number assigned by looking at the index specifications
            indexNum = indexSpecs.getIndexNum(indexedStreamIndexProps);

            // Constructed keyed lookup strategy
            String[] keyGenFields = queryGraph.getKeyProperties(currentLookupStream, indexedStream);
            tableLookupPlan = new IndexedTableLookupPlan(currentLookupStream, indexedStream, indexNum, keyGenFields);
        }
        else
        {
            // We don't use a keyed index but use the full stream set as the stream does not have any indexes
            indexNum = indexSpecs.getIndexNum(new String[0]);

            // If no such full set index exists yet, add to specs
            if (indexNum == -1)
            {
                indexNum = indexSpecs.addIndex(new String[0]);
            }

            tableLookupPlan = new FullTableScanLookupPlan(currentLookupStream, indexedStream, indexNum);
        }

        return tableLookupPlan;
    }


    /**
     * Compute a best chain or path for lookups to take for the lookup stream passed in and the query
     * property relationships.
     * The method runs through all possible permutations of lookup path {@link NumberSetPermutationEnumeration}
     * until a path is found in which all streams can be accessed via an index.
     * If not such path is found, the method returns the path with the greatest depth, ie. where
     * the first one or more streams are index accesses.
     * If no depth other then zero is found, returns the default nesting order.
     *
     * @param lookupStream - stream to start look up
     * @param queryGraph - navigability between streams
     * @return chain and chain depth
     */
    protected static BestChainResult computeBestPath(int lookupStream, QueryGraph queryGraph)
    {
        int[] defNestingorder = buildDefaultNestingOrder(queryGraph.getNumStreams(), lookupStream);
        NumberSetPermutationEnumeration permutations = new NumberSetPermutationEnumeration(defNestingorder);
        int[] bestPermutation = null;
        int bestDepth = -1;

        while(permutations.hasMoreElements())
        {
            int[] permutation = permutations.nextElement();
            int permutationDepth = computeNavigableDepth(lookupStream, permutation, queryGraph);

            if (permutationDepth > bestDepth)
            {
                bestPermutation = permutation;
                bestDepth = permutationDepth;
            }

            // Stop when the permutation yielding the full depth (lenght of stream chain) was hit
            if (permutationDepth == queryGraph.getNumStreams() - 1)
            {
                break;
            }
        }

        return new BestChainResult(bestDepth, bestPermutation);
    }

    /**
     * Given a chain of streams to look up and indexing information, compute the index within the
     * chain of the first non-index lookup.
     * @param lookupStream - stream to start lookup for
     * @param nextStreams - list of stream numbers next in lookup
     * @param queryGraph - indexing information
     * @return value between 0 and (nextStreams.lenght - 1)
     */
    protected static int computeNavigableDepth(int lookupStream, int[] nextStreams, QueryGraph queryGraph)
    {
        int currentStream = lookupStream;
        int currentDepth = 0;

        for (int i = 0; i < nextStreams.length; i++)
        {
            int nextStream = nextStreams[i];
            if (!queryGraph.isNavigable(currentStream, nextStream))
            {
                break;
            }
            currentStream = nextStream;
            currentDepth++;
        }

        return currentDepth;
    }

    /**
     * Returns query plan based on all unindexed full table lookups and lookups based
     * on a simple nesting order.
     * @param eventTypes - stream event types
     * @return query plan
     */
    protected static QueryPlan buildNStreamDefaultQuerySpec(EventType[] eventTypes)
    {
        QueryPlanIndex[] indexSpecs = new QueryPlanIndex[eventTypes.length];
        QueryPlanNode[] execNodeSpecs = new QueryPlanNode[eventTypes.length];

        // Build indexes without key properties
        for (int i = 0; i < indexSpecs.length; i++)
        {
            indexSpecs[i] = new QueryPlanIndex(null);
        }

        // Handle N-stream queries
        for (int streamNo = 0; streamNo < eventTypes.length; streamNo++)
        {
            int[] nestingOrder = buildDefaultNestingOrder(eventTypes.length, streamNo);
            NestedIterationNode nestedNode = new NestedIterationNode(nestingOrder);
            execNodeSpecs[streamNo] = nestedNode;
            int lookupStream = streamNo;

            for (int j = 0; j < nestingOrder.length; j++)
            {
                int indexedStream = nestingOrder[j];
                FullTableScanLookupPlan scanLookupStrategy = new FullTableScanLookupPlan(lookupStream, indexedStream, 0);
                nestedNode.addChildNode(new TableLookupNode(scanLookupStrategy));
                lookupStream = indexedStream;
            }
        }

        return new QueryPlan(indexSpecs, execNodeSpecs);
    }

    /**
     * Returns default nesting order for a given number of streams for a certain stream.
     * Example: numStreams = 5, forStream = 2, result = {0, 1, 3, 4}
     * The resulting array has all streams except the forStream, in ascdending order.
     * @param numStreams - number of streams
     * @param forStream - stream to generate a nesting order for
     * @return int array with all stream numbers starting at 0 to (numStreams - 1) leaving the
     * forStream out
     */
    protected static int[] buildDefaultNestingOrder(int numStreams, int forStream)
    {
        int[] nestingOrder = new int[numStreams - 1];

        int count = 0;
        for (int i = 0; i < numStreams; i++)
        {
            if (i == forStream)
            {
                continue;
            }
            nestingOrder[count++] = i;
        }

        return nestingOrder;
    }

    /**
     * Encapsulates the chain information.
     */
    public static class BestChainResult
    {
        private int depth;
        private int[] chain;

        /**
         * Ctor.
         * @param depth - depth this chain resolves into a indexed lookup
         * @param chain - chain for nested lookup
         */
        public BestChainResult(int depth, int[] chain)
        {
            this.depth = depth;
            this.chain = chain;
        }

        /**
         * Returns depth of lookups via index in chain.
         * @return depth
         */
        public int getDepth()
        {
            return depth;
        }

        /**
         * Returns chain of stream numbers.
         * @return array of stream numbers
         */
        public int[] getChain()
        {
            return chain;
        }

        public String toString()
        {
            return "depth=" + depth + " chain=" + Arrays.toString(chain);
        }
    }

    private static Log log = LogFactory.getLog(NStreamQueryPlanBuilder.class);
}
