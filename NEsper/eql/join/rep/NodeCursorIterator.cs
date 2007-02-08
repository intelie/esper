using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.rep
{
    /// <summary>
    /// Iterator over a set of nodes supplying node and event-within-node position information in a {@link Cursor}.
    /// </summary>

    public class NodeCursorEnumerator : IEnumerator<Cursor>
    {
        private readonly IEnumerator<Node> nodeEnumerator;
        private readonly int stream;
        private IEnumerator<EventBean> currentEnumerator;
        private Node currentNode;

        public virtual Cursor Current
        {
            get
            {
                if (currentEnumerator == null)
                {
                    throw new System.ArgumentOutOfRangeException();
                }

                return makeCursor(currentEnumerator.Current);
            }
        }

        /// <summary> Ctor.</summary>
        /// <param name="stream">is the stream that the events in the Node belong to
        /// </param>
        /// <param name="nodeEnumerator">is the iterator over all nodes to position over
        /// </param>

        public NodeCursorEnumerator(int stream, IEnumerator<Node> nodeEnumerator)
        {
            this.stream = stream;
            this.nodeEnumerator = nodeEnumerator;
            this.AdvanceChild();
        }

        /// <summary>
        /// Advances the enumerator to the next element of the collection.
        /// </summary>
        /// <returns>
        /// true if the enumerator was successfully advanced to the next element; false if the enumerator has passed the end of the collection.
        /// </returns>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>
        
        public virtual bool MoveNext()
        {
            while (currentEnumerator != null)
            {
                if (currentEnumerator.MoveNext())
                {
                    return true;
                }

                AdvanceChild();
            }

            return false;
        }

        /// <summary>
        /// Advances this currentEnumerator to the next node in
        /// the node enumerator.
        /// </summary>
        
        private void AdvanceChild()
        {
            this.currentEnumerator = null;

            if (nodeEnumerator.MoveNext())
            {
                currentNode = nodeEnumerator.Current;
                ISet<EventBean> events = currentNode.Events;
                if (events != null)
                {
                    currentEnumerator = events.GetEnumerator();
                }
            }
        }

        private Cursor makeCursor(EventBean _event)
        {
            return new Cursor(_event, stream, currentNode);
        }

        /// <summary>
        /// Sets the enumerator to its initial position, which is before the first element in the collection.
        /// </summary>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>

        public void Reset()
        {
            throw new NotSupportedException();
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>

        public void Dispose()
        {
        }

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        /// <value></value>
        /// <returns>The element in the collection at the current position of the enumerator.</returns>

        Object System.Collections.IEnumerator.Current
        {
            get { return this.Current; }
        }
    }
}