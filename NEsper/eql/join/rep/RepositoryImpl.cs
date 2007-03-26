using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.rep
{
    /// <summary>
    /// Implements a repository for join events and lookup results.
    /// </summary>

    public class RepositoryImpl : Repository
    {
        private readonly int rootStream;
        private readonly EventBean rootEvent;
        private readonly int numStreams;

        private IList<Node>[] nodesPerStream;

        private static IEnumerator<Cursor> emptyCursorIterator = new SingleCursorIterator(null);

        /// <summary> Ctor.</summary>
        /// <param name="rootStream">is the stream supplying the root event
        /// </param>
        /// <param name="rootEvent">is the root event
        /// </param>
        /// <param name="numStreams">is the number of streams
        /// </param>

        public RepositoryImpl(int rootStream, EventBean rootEvent, int numStreams)
        {
            this.rootStream = rootStream;
            this.rootEvent = rootEvent;
            this.numStreams = numStreams;
        }

        /// <summary>
        /// Gets the cursors.
        /// </summary>
        /// <param name="lookupFromStream">The lookup from stream.</param>
        /// <returns></returns>
        public IEnumerator<Cursor> GetCursors(int lookupFromStream)
        {
            if (lookupFromStream == rootStream)
            {
                Cursor cursor = new Cursor(rootEvent, rootStream, null);
                return new SingleCursorIterator(cursor);
            }

            IList<Node> nodeList = nodesPerStream[lookupFromStream];
            if (nodeList == null)
            {
                return emptyCursorIterator;
            }
            return new NodeCursorEnumerator(lookupFromStream, nodeList.GetEnumerator());
        }

        /// <summary>
        /// Add a lookup result.
        /// </summary>
        /// <param name="cursor">provides result position and parent event and node information</param>
        /// <param name="lookupResults">is the events found</param>
        /// <param name="resultStream">is the stream number of the stream providing the results</param>
        public void AddResult(Cursor cursor, ISet<EventBean> lookupResults, int resultStream)
        {
            if (lookupResults.Count == 0)
            {
                throw new ArgumentException("Attempting to add zero results");
            }

            Node parentNode = cursor.Node;
            if (parentNode == null)
            {
                Node leafNode = new Node(resultStream);
                leafNode.Events = lookupResults;

                if (nodesPerStream == null)
                {
                    nodesPerStream = new List<Node>[numStreams];
                }

                IList<Node> nodes = nodesPerStream[resultStream];
                if (nodes == null)
                {
                    nodes = new List<Node>();
                    nodesPerStream[resultStream] = nodes;
                }

                nodes.Add(leafNode);
                return;
            }

            Node _leafNode = new Node(resultStream);
            _leafNode.Events = lookupResults;
            _leafNode.Parent = cursor.Node;
            _leafNode.ParentEvent = cursor.Event;

            IList<Node> _nodes = nodesPerStream[resultStream];
            if (_nodes == null)
            {
                _nodes = new List<Node>();
                nodesPerStream[resultStream] = _nodes;
            }

            _nodes.Add(_leafNode);
        }

        /// <summary>
        /// Returns a list of nodes that are the lookup results per stream.
        /// </summary>
        
        public IList<Node>[] NodesPerStream
        {
            get { return nodesPerStream; }
        }
    }
}
