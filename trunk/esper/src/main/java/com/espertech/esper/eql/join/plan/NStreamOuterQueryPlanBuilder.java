/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.join.plan;

import com.espertech.esper.eql.join.assemble.AssemblyStrategyTreeBuilder;
import com.espertech.esper.eql.join.assemble.BaseAssemblyNode;
import com.espertech.esper.eql.spec.OuterJoinDesc;
import com.espertech.esper.event.EventType;
import com.espertech.esper.type.OuterJoinType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * Builds a query plan for 3 or more streams in a outer join.
 */
public class NStreamOuterQueryPlanBuilder
{
    /**
     * Build a query plan based on the stream property relationships indicated in queryGraph.
     * @param queryGraph - navigation info between streams
     * @param streamNames - stream names or aliases
     * @param outerJoinDescList - descriptors for all outer joins
     * @param typesPerStream - event types for each stream
     * @return query plan
     */
    protected static QueryPlan build(QueryGraph queryGraph,
                                     List<OuterJoinDesc> outerJoinDescList,
                                     String[] streamNames,
                                     EventType[] typesPerStream)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".build queryGraph=" + queryGraph);
        }

        int numStreams = queryGraph.getNumStreams();
        QueryPlanNode[] planNodeSpecs = new QueryPlanNode[numStreams];

        // Build index specifications
        QueryPlanIndex[] indexSpecs = QueryPlanIndexBuilder.buildIndexSpec(queryGraph);
        if (log.isDebugEnabled())
        {
            log.debug(".build Index build completed, indexes=" + QueryPlanIndex.print(indexSpecs));
        }

        // Build graph of the outer and inner joins
        OuterInnerDirectionalGraph outerInnerGraph = graphOuterJoins(numStreams, outerJoinDescList);
        if (log.isDebugEnabled())
        {
           log.debug(".build directional graph=" + outerInnerGraph.print());
        }

        // For each stream determine the query plan
        for (int streamNo = 0; streamNo < numStreams; streamNo++)
        {
            QueryPlanNode queryPlanNode = build(numStreams, streamNo, streamNames, queryGraph, outerInnerGraph, indexSpecs, typesPerStream);

            if (log.isDebugEnabled())
            {
                log.debug(".build spec for stream '" + streamNames[streamNo] +
                        "' number " + streamNo + " is " + queryPlanNode);
            }

            planNodeSpecs[streamNo] = queryPlanNode;
        }

        QueryPlan queryPlan = new QueryPlan(indexSpecs, planNodeSpecs);
        if (log.isDebugEnabled())
        {
            log.debug(".build query plan=" + queryPlan.toString());
        }

        return queryPlan;
    }

    private static QueryPlanNode build(int numStreams,
                                       int streamNo,
                                       String[] streamNames,
                                       QueryGraph queryGraph,
                                       OuterInnerDirectionalGraph outerInnerGraph,
                                       QueryPlanIndex[] indexSpecs,
                                       EventType[] typesPerStream)
    {
        // For each stream build an array of substreams, considering required streams (inner joins) first
        // The order is relevant therefore preserving order via a LinkedHashMap.
        LinkedHashMap<Integer, int[]> substreamsPerStream = new LinkedHashMap<Integer, int[]>();
        boolean[] requiredPerStream = new boolean[numStreams];

        // Recursive populating the required (outer) and optional (inner) relationships
        // of this stream and the substream
        Set<Integer> completedStreams = new HashSet<Integer>();
        recursiveBuild(streamNo, queryGraph, outerInnerGraph, completedStreams, substreamsPerStream, requiredPerStream);

        // verify the substreamsPerStream, all streams must exists and be linked
        verifyJoinedPerStream(streamNo, substreamsPerStream);

        // build list of instructions for lookup
        List<LookupInstructionPlan> lookupInstructions = buildLookupInstructions(substreamsPerStream, requiredPerStream,
                streamNames, queryGraph, indexSpecs, typesPerStream);

        // build strategy tree for putting the result back together
        BaseAssemblyNode assemblyTopNode = AssemblyStrategyTreeBuilder.build(streamNo, substreamsPerStream, requiredPerStream);
        List<BaseAssemblyNode> assemblyInstructions = BaseAssemblyNode.getDescendentNodesBottomUp(assemblyTopNode);

        return new LookupInstructionQueryPlanNode(streamNo, streamNames[streamNo], numStreams, requiredPerStream,
                lookupInstructions, assemblyInstructions);
    }

    private static List<LookupInstructionPlan> buildLookupInstructions(
            LinkedHashMap<Integer, int[]> substreamsPerStream,
            boolean[] requiredPerStream,
            String[] streamNames,
            QueryGraph queryGraph,
            QueryPlanIndex[] indexSpecs,
            EventType[] typesPerStream)
    {
        List<LookupInstructionPlan> result = new LinkedList<LookupInstructionPlan>();

        for (int fromStream : substreamsPerStream.keySet())
        {
            int[] substreams = substreamsPerStream.get(fromStream);

            // for streams with no substreams we don't need to look up
            if (substreams.length == 0)
            {
                continue;
            }

            TableLookupPlan plans[] = new TableLookupPlan[substreams.length];

            for (int i = 0; i < substreams.length; i++)
            {
                int toStream = substreams[i];
                TableLookupPlan tableLookupPlan = NStreamQueryPlanBuilder.createLookupPlan(queryGraph, fromStream, toStream, indexSpecs[toStream], typesPerStream);
                plans[i] = tableLookupPlan;
            }

            String fromStreamName = streamNames[fromStream];
            LookupInstructionPlan instruction = new LookupInstructionPlan(fromStream, fromStreamName, substreams, plans, requiredPerStream);
            result.add(instruction);
        }

        return result;
    }

    /**
     * Recusivly builds a substream-per-stream ordered tree graph using the
     * join information supplied for outer joins and from the query graph (where clause).
     * <p>
     * Required streams are considered first and their lookup is placed first in the list
     * to gain performance.
     * @param streamNum is the root stream number that supplies the incoming event to build the tree for
     * @param queryGraph contains where-clause stream relationship info
     * @param outerInnerGraph contains the outer join stream relationship info
     * @param completedStreams is a temporary holder for streams already considered
     * @param substreamsPerStream is the ordered, tree-like structure to be filled
     * @param requiredPerStream indicates which streams are required and which are optional
     */
    protected static void recursiveBuild(int streamNum,
                                                QueryGraph queryGraph,
                                                OuterInnerDirectionalGraph outerInnerGraph,
                                                Set<Integer> completedStreams,
                                                LinkedHashMap<Integer, int[]> substreamsPerStream,
                                                boolean[] requiredPerStream
                                                )
    {
        // add this stream to the set of completed streams
        completedStreams.add(streamNum);

        // Determine the streams we can navigate to from this stream
        Set<Integer> navigableStreams = queryGraph.getNavigableStreams(streamNum);

        // remove those already done
        navigableStreams.removeAll(completedStreams);

        // Which streams are inner streams to this stream (optional), which ones are outer to the stream (required)
        Set<Integer> requiredStreams = getOuterStreams(streamNum, navigableStreams, outerInnerGraph);
        Set<Integer> optionalStreams = getInnerStreams(streamNum, navigableStreams, outerInnerGraph);

        // Remove from the required streams the optional streams which places 'full' joined streams
        // into the optional stream category
        requiredStreams.removeAll(optionalStreams);

        if (navigableStreams.size() != (requiredStreams.size() + optionalStreams.size()))
        {
            throw new IllegalArgumentException("Navigable streams size not constisting of inner and outer streams");
        }

        // if we are a leaf node, we are done
        if (navigableStreams.isEmpty())
        {
            substreamsPerStream.put(streamNum, new int[0]);
            return;
        }

        // First the outer (required) streams to this stream, then the inner (optional) streams
        int[] substreams = new int[requiredStreams.size() + optionalStreams.size()];
        substreamsPerStream.put(streamNum, substreams);
        int count = 0;
        for (int stream : requiredStreams)
        {
            substreams[count++] = stream;
            requiredPerStream[stream] = true;
        }
        for (int stream : optionalStreams)
        {
            substreams[count++] = stream;
        }

        // next we look at all the required streams and add their dependent streams
        for (int stream : requiredStreams)
        {
            recursiveBuild(stream, queryGraph, outerInnerGraph,
                           completedStreams, substreamsPerStream, requiredPerStream);
        }
        // look at all the optional streams and add their dependent streams
        for (int stream : optionalStreams)
        {
            recursiveBuild(stream, queryGraph, outerInnerGraph,
                           completedStreams, substreamsPerStream, requiredPerStream);
        }
    }

    private static Set<Integer> getInnerStreams(int fromStream, Set<Integer> toStreams, OuterInnerDirectionalGraph outerInnerGraph)
    {
        Set<Integer> innerStreams = new HashSet<Integer>();
        for (int toStream : toStreams)
        {
            if (outerInnerGraph.isInner(fromStream, toStream))
            {
                innerStreams.add(toStream);
            }
        }
        return innerStreams;
    }

    // which streams are to this table an outer stream
    private static Set<Integer> getOuterStreams(int fromStream, Set<Integer> toStreams, OuterInnerDirectionalGraph outerInnerGraph)
    {
        Set<Integer> outerStreams = new HashSet<Integer>();
        for (int toStream : toStreams)
        {
            if (outerInnerGraph.isOuter(toStream, fromStream))
            {
                outerStreams.add(toStream);
            }
        }
        return outerStreams;
    }

    /**
     * Builds a graph of outer joins given the outer join information from the statement.
     * Eliminates right and left joins and full joins by placing the information in a graph object.
     * @param numStreams - is the number of streams
     * @param outerJoinDescList - list of outer join stream numbers and property names
     * @return graph object
     */
    protected static OuterInnerDirectionalGraph graphOuterJoins(int numStreams, List<OuterJoinDesc> outerJoinDescList)
    {
        if ((outerJoinDescList.size() + 1) != numStreams)
        {
            throw new IllegalArgumentException("Number of outer join descriptors and number of streams not matching up");
        }

        OuterInnerDirectionalGraph graph = new OuterInnerDirectionalGraph(numStreams);

        for (int i = 0; i < outerJoinDescList.size(); i++)
        {
            OuterJoinDesc desc = outerJoinDescList.get(i);
            int streamMax = i + 1;       // the outer join must references streams less then streamMax

            // Check outer join
            int streamOne = desc.getLeftNode().getStreamId();
            int streamTwo = desc.getRightNode().getStreamId();

            if ((streamOne > streamMax) || (streamTwo > streamMax) ||
                (streamOne == streamTwo))
            {
                throw new IllegalArgumentException("Outer join descriptors reference future streams, or same streams");
            }

            // Determine who is the first stream in the streams listed
            int lowerStream = streamOne;
            int higherStream = streamTwo;
            if (streamOne > streamTwo)
            {
                lowerStream = streamTwo;
                higherStream = streamOne;
            }

            // Add to graph
            if (desc.getOuterJoinType() == OuterJoinType.FULL)
            {
                graph.add(streamOne, streamTwo);
                graph.add(streamTwo, streamOne);
            }
            else if (desc.getOuterJoinType() == OuterJoinType.LEFT)
            {
                graph.add(lowerStream, higherStream);
            }
            else if (desc.getOuterJoinType() == OuterJoinType.RIGHT)
            {
                graph.add(higherStream, lowerStream);
            }
            else
            {
                throw new IllegalArgumentException("Outer join descriptors join type not handled, type=" + desc.getOuterJoinType());
            }
        }

        return graph;
    }

    /**
     * Verifies that the tree-like structure representing which streams join (lookup) into which sub-streams
     * is correct, ie. all streams are included and none are listed twice.
     * @param rootStream is the stream supplying the incoming event
     * @param streamsJoinedPerStream is keyed by the from-stream number and contains as values all
     * stream numbers of lookup into to-streams.
     */
    public static void verifyJoinedPerStream(int rootStream, Map<Integer, int[]> streamsJoinedPerStream)
    {
        Set<Integer> streams = new HashSet<Integer>();
        streams.add(rootStream);

        recursiveAdd(rootStream, streamsJoinedPerStream, streams);

        if (streams.size() != streamsJoinedPerStream.size())
        {
            throw new IllegalArgumentException("Not all streams found, streamsJoinedPerStream=" +
                    print(streamsJoinedPerStream));
        }
    }

    private static void recursiveAdd(int currentStream, Map<Integer, int[]> streamsJoinedPerStream, Set<Integer> streams)
    {
        if (currentStream >= streamsJoinedPerStream.size())
        {
            throw new IllegalArgumentException("Error in stream " + currentStream + " streamsJoinedPerStream=" +
                    print(streamsJoinedPerStream));
        }
        int[] joinedStreams = streamsJoinedPerStream.get(currentStream);
        for (int i = 0; i < joinedStreams.length; i++)
        {
            int addStream = joinedStreams[i];
            if (streams.contains(addStream))
            {
                throw new IllegalArgumentException("Stream " + addStream + " found twice");
            }
            streams.add(addStream);
            recursiveAdd(addStream, streamsJoinedPerStream, streams);
        }
    }

    /**
     * Returns textual presentation of stream-substream relationships.
     * @param streamsJoinedPerStream is the tree-like structure of stream-substream
     * @return textual presentation
     */
    public static String print(Map<Integer, int[]> streamsJoinedPerStream)
    {
        StringWriter buf = new StringWriter();
        PrintWriter printer = new PrintWriter(buf);

        for (int stream : streamsJoinedPerStream.keySet())
        {
            int[] substreams = streamsJoinedPerStream.get(stream);
            printer.println("stream " + stream + " : " + Arrays.toString(substreams));
        }

        return buf.toString();
    }

    private static Log log = LogFactory.getLog(NStreamOuterQueryPlanBuilder.class);
}
