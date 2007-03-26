using System;
namespace net.esper.eql.join.rep
{
	/// <summary>
    /// A utility class for an iterator that has one element.
    /// </summary>
    public class SingleCursorIterator : System.Collections.Generic.IEnumerator<Cursor>
	{
        private Cursor cursor;

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        /// <value></value>
        /// <returns>The element in the collection at the current position of the enumerator.</returns>
		public virtual Cursor Current
		{
			get
			{
				if (cursor == null)
				{
					throw new ArgumentOutOfRangeException();
				}

				Cursor c = cursor;
				this.cursor = null;
				return c;
			}
		}

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="cursor">is the single element.</param>
		
        public SingleCursorIterator(Cursor cursor)
		{
			this.cursor = cursor;
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
			return (cursor != null);
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