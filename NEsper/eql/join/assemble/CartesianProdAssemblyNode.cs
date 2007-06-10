using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.assemble
{
    /// <summary>
    /// Assembly node for an event stream that is a branch with a two or more child nodes (required and optional) below it.
    /// </summary>

    public class CartesianProdAssemblyNode : BaseAssemblyNode
    {
        private readonly int[] childStreamIndex; // maintain mapping of stream number to index in array
        private readonly bool allSubStreamsOptional;

        // keep a reference to results for processing optional child nodes not generating results
        private IList<Node> resultsForStream;

        // maintain for each child the list of stream number descending that child
        private int[][] subStreamsNumsPerChild;
        private int[][] combinedSubStreams; // for any cartesian product past 2 streams

        // For tracking when we only have a single event for this stream as a result
        private Node singleResultNode;
        private EventBean singleResultParentEvent;
        private IList<EventBean[]>[] singleResultRowsPerStream;
        private bool haveChildResults;

        // For tracking when we have multiple events for this stream
        private IDictionary<EventBean, ChildStreamResults> completedEvents;

        /// <summary> Ctor.</summary>
        /// <param name="streamNum">is the stream number
        /// </param>
        /// <param name="numStreams">is the number of streams
        /// </param>
        /// <param name="allSubStreamsOptional">true if all child nodes to this node are optional, or false if
        /// one or more child nodes are required for a result.
        /// </param>

        public CartesianProdAssemblyNode(int streamNum, int numStreams, bool allSubStreamsOptional)
            : base(streamNum, numStreams)
        {
            childStreamIndex = new int[numStreams];
            this.allSubStreamsOptional = allSubStreamsOptional;
        }

        /// <summary>
        /// Add a child node.
        /// </summary>
        /// <param name="childNode">to add</param>
        public override void AddChild(BaseAssemblyNode childNode)
        {
            childStreamIndex[childNode.StreamNum] = childNodes.Count;
            base.AddChild(childNode);
        }

        /// <summary>
        /// Provides results to assembly nodes for initialization.
        /// </summary>
        /// <param name="result">is a list of result nodes per stream</param>
        public override void Init(IList<Node>[] result)
        {
            resultsForStream = result[streamNum];
            singleResultNode = null;
            singleResultParentEvent = null;
            singleResultRowsPerStream = null;
            haveChildResults = false;

            if (subStreamsNumsPerChild == null)
            {
                if (childNodes.Count < 2)
                {
                    throw new SystemException("Expecting at least 2 child nodes");
                }
                subStreamsNumsPerChild = new int[childNodes.Count][];
                for (int i = 0; i < childNodes.Count; i++)
                {
                    subStreamsNumsPerChild[i] = childNodes[i].Substreams;
                }

                combinedSubStreams = RootCartProdAssemblyNode.ComputeCombined(subStreamsNumsPerChild);
            }

            if (resultsForStream != null)
            {
                int numNodes = resultsForStream.Count;
                if (numNodes == 1)
                {
                    Node node = resultsForStream[0];
                    Set<EventBean> nodeEvents = node.Events;

                    // If there is a single result event (typical case)
                    if (nodeEvents.Count == 1)
                    {
                        singleResultNode = node;
                        singleResultParentEvent = nodeEvents.First;
                        singleResultRowsPerStream = new List<EventBean[]>[childNodes.Count];
                    }
                }

                if (singleResultNode == null)
                {
                    completedEvents = new Dictionary<EventBean, ChildStreamResults>();
                }
            }
            else
            {
                completedEvents = new Dictionary<EventBean, ChildStreamResults>();
            }
        }

        /// <summary>
        /// Process results.
        /// </summary>
        /// <param name="result">is a list of result nodes per stream</param>
        public override void Process(IList<Node>[] result)
        {
            // there cannot be child nodes to compute a cartesian product if this node had no results
            if (resultsForStream == null)
            {
                return;
            }

            // If this node's result set consisted of a single event
            if (singleResultNode != null)
            {
                // If no child has posted any rows
                if (!haveChildResults)
                {
                    // And all substreams are optional, generate a row
                    if (allSubStreamsOptional)
                    {
                        EventBean[] row = new EventBean[numStreams];
                        row[streamNum] = singleResultParentEvent;
                        parentNode.Result(row, streamNum, singleResultNode.ParentEvent, singleResultNode);
                    }
                    return;
                }

                // Compute the cartesian product
                postCartesian(singleResultRowsPerStream, singleResultNode);
                return;
            }

            // We have multiple events for this node, generate an event row for each event not yet received from
            // event rows generated by the child node.
            foreach (Node node in resultsForStream)
            {
                Set<EventBean> events = node.Events;
                foreach (EventBean _event in events)
                {
                    ChildStreamResults results = null ;

                    // If there were no results for the event posted by any child nodes
                    if (!completedEvents.TryGetValue(_event, out results))
                    {
                        if (allSubStreamsOptional)
                        {
                            EventBean[] row = new EventBean[numStreams];
                            row[streamNum] = _event;
                            parentNode.Result(row, streamNum, node.ParentEvent, node.Parent);
                        }
                        continue;
                    }

                    // Compute the cartesian product
                    postCartesian(results.RowsPerStream, node);
                }
            }
        }

        private void postCartesian(IList<EventBean[]>[] rowsPerStream, Node node)
        {
            IList<EventBean[]> result = new List<EventBean[]>();
            CartesianUtil.ComputeCartesian(rowsPerStream[0], subStreamsNumsPerChild[0], rowsPerStream[1], subStreamsNumsPerChild[1], result);

            if (rowsPerStream.Length > 2)
            {
                for (int i = 0; i < subStreamsNumsPerChild.Length - 2; i++)
                {
                    IList<EventBean[]> product = new List<EventBean[]>();
                    CartesianUtil.ComputeCartesian(result, combinedSubStreams[i], rowsPerStream[i + 2], subStreamsNumsPerChild[i + 2], product);
                    result = product;
                }
            }

            foreach (EventBean[] row in result)
            {
                parentNode.Result(row, streamNum, node.ParentEvent, node.Parent);
            }
        }

        /// <summary>
        /// Publish a result row.
        /// </summary>
        /// <param name="row">is the result to publish</param>
        /// <param name="fromStreamNum">is the originitor that publishes the row</param>
        /// <param name="myEvent">is optional and is the event that led to the row result</param>
        /// <param name="myNode">is optional and is the result node of the event that led to the row result</param>
        public override void Result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode)
        {
            // fill event in
            row[streamNum] = myEvent;
            int childStreamArrIndex = childStreamIndex[fromStreamNum];

            // treat single-event result for this stream
            if (singleResultNode != null)
            {
                // record the fact that an event that was generated by a child
                haveChildResults = true;

                if (singleResultRowsPerStream == null)
                {
                    singleResultRowsPerStream = new List<EventBean[]>[childNodes.Count];
                }

                IList<EventBean[]> streamRows = singleResultRowsPerStream[childStreamArrIndex];
                if (streamRows == null)
                {
                    streamRows = new List<EventBean[]>();
                    singleResultRowsPerStream[childStreamArrIndex] = streamRows;
                }

                streamRows.Add(row);
                return;
            }

            ChildStreamResults childStreamResults = null;
            if ( ! completedEvents.TryGetValue( myEvent, out childStreamResults ) )
            {
                childStreamResults = new ChildStreamResults(childNodes.Count);
                completedEvents[myEvent] = childStreamResults;
            }

            childStreamResults.Add(childStreamArrIndex, row);
        }

        /// <summary>
        /// Output this node using writer, not outputting child nodes.
        /// </summary>
        /// <param name="indentWriter">to use for output</param>
        public override void Print(IndentWriter indentWriter)
        {
            indentWriter.WriteLine("CartesianProdAssemblyNode streamNum=" + streamNum);
        }

        /// <summary> Structure to represent a list of event result rows per stream.</summary>
        public class ChildStreamResults
        {
            private IList<EventBean[]>[] rowsPerStream;

            /// <summary> Ctor.</summary>
            /// <param name="size">number of streams
            /// </param>

            public ChildStreamResults(int size)
            {
                this.rowsPerStream = new List<EventBean[]>[size];
            }

            /// <summary> Add result from stream.</summary>
            /// <param name="fromStreamIndex">from stream
            /// </param>
            /// <param name="row">row to add
            /// </param>
            public virtual void Add(int fromStreamIndex, EventBean[] row)
            {
                IList<EventBean[]> rows = rowsPerStream[fromStreamIndex];
                if (rows == null)
                {
                    rows = new List<EventBean[]>();
                    rowsPerStream[fromStreamIndex] = rows;
                }

                rows.Add(row);
            }

            /// <summary> Returns rows per stream.</summary>
            /// <returns> rows per stream
            /// </returns>

            public IList<EventBean[]>[] RowsPerStream
            {
                get { return rowsPerStream; }
            }
        }
    }
}
