using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.collection
{
	/// <summary>
    /// Iterator for {@link TimeWindow} to iterate over a timestamp slots that hold events.
    /// </summary>

    public sealed class TimeWindowIterator : IEnumerator<EventBean>
    {
        private IEnumerator<Pair<Int64, List<EventBean>>> keyIterator;
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
                if ( currentListIterator == null )
                {
                    throw new ArgumentOutOfRangeException();
                }

                return currentListIterator.Current ;
            }
        }

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="window">is the time-slotted collection</param>

        public TimeWindowIterator(LinkedList<Pair<Int64, List<EventBean>>> window)
        {
            keyIterator = window.GetEnumerator();
            if (keyIterator.MoveNext())
            {
                currentListIterator = keyIterator.Current.Second.GetEnumerator();
            }
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

                currentListIterator = null;

                if (keyIterator.MoveNext())
                {
                    currentListIterator = keyIterator.Current.Second.GetEnumerator();
                }
            }

            return false;
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