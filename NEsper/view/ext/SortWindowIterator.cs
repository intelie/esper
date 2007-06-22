using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;

namespace net.esper.view.ext
{
    using _WindowList = TreeDictionary<MultiKeyUntyped, LinkedList<EventBean>>;
    using _WindowListEnum = IEnumerator<KeyValuePair<MultiKeyUntyped, LinkedList<EventBean>>>;

    /// <summary>
    /// Iterator for use by <seealso cref="SortWindowView"/>.
    /// </summary>

    public sealed class SortWindowIterator : IEnumerator<EventBean>
    {
        private readonly _WindowList window;
        private readonly _WindowListEnum windowIterator;
        private IEnumerator<EventBean> currentListIterator;

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        /// <value></value>
        /// <returns>The element in the collection at the current position of the enumerator.</returns>
        
        public EventBean Current
        {
            get
            {
                if (currentListIterator == null)
                {
                    throw new ArgumentOutOfRangeException();
                }

                return currentListIterator.Current;
            }
        }

        /// <summary> Ctor.</summary>
        /// <param name="window">sorted map with events
        /// </param>

        public SortWindowIterator(TreeDictionary<MultiKeyUntyped, LinkedList<EventBean>> window)
        {
            this.window = window;
            this.windowIterator = window.GetEnumerator();
            AdvanceChild();
        }

        /// <summary>
        /// Advances the enumerator to the next element of the collection.
        /// </summary>
        /// <returns>
        /// true if the enumerator was successfully advanced to the next element; false if the enumerator has passed the end of the collection.
        /// </returns>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>
        
        public bool MoveNext()
        {
            while (currentListIterator != null)
            {
                if (currentListIterator.MoveNext())
                {
                    return true;
                }

                AdvanceChild();
            }

            return false;
        }

        /// <summary>
        /// Advances the currentListIterator to the next item in the
        /// parent enumerator.
        /// </summary>

        private void AdvanceChild()
        {
            this.currentListIterator = null;

            if (windowIterator.MoveNext())
            {
                this.currentListIterator = windowIterator.Current.Value.GetEnumerator();
            }
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
