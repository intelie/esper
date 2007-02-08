using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.collection
{
	/// <summary>
    /// A utility class for an iterator that has one element.
    /// </summary>
    
    public class SingleEventIterator : IEnumerator<EventBean>
	{
        internal enum CurrentPosition
        {
            Start,
            At,
            End
        };

        private EventBean eventBean;
        private CurrentPosition currentPosition;

        public EventBean Current
		{
			get
			{
                if (currentPosition == CurrentPosition.At)
                {
                    return eventBean;
                }

                throw new ArgumentOutOfRangeException();
			}
		}
		
		/// <summary> Constructor, takes the single event to iterate over as a parameter.
		/// The single event can be null indicating that there are no more elements.
		/// </summary>
		/// <param name="eventBean">single bean that the iterator returns, or null for an empty iterator
		/// </param>

        public SingleEventIterator(EventBean eventBean)
		{
            this.eventBean = eventBean;
            this.currentPosition =
                (this.eventBean != null) ?
                (CurrentPosition.Start) :
                (CurrentPosition.End);
		}
		
		public virtual bool MoveNext()
		{
            switch (currentPosition)
            {
                case CurrentPosition.Start:
                    currentPosition = CurrentPosition.At;
                    return true;
                case CurrentPosition.At:
                    currentPosition = CurrentPosition.End;
                    return false;
                case CurrentPosition.End:
                default:
                    return false;
            }
		}

        /// <summary>
        /// Sets the enumerator to its initial position, which is before the first element in the collection.
        /// </summary>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>
        
        virtual public void  Reset()
		{
            this.currentPosition =
                (this.eventBean != null) ?
                (CurrentPosition.Start) :
                (CurrentPosition.End);
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