using System;
using System.Collections.Generic;

using net.esper.util;

namespace net.esper.collection
{
	/// <summary> General-purpose pair of values of any type. The pair only equals another pair if
	/// the objects that form the pair equal, ie. first pair first object equals (.equals) the second pair first object,
	/// and the first pair second object equals the second pair second object.
	/// </summary>
    
    public sealed class UniformPair<T> : MetaDefItem
	{
        /// <summary>
        /// Gets or sets the first value within pair.
        /// </summary>
        /// <value>The first.</value>
		public T First
		{
			get { return first; }
			set { this.first = value; }
	    }

        /// <summary>
        /// Gets or sets the second value within pair.
        /// </summary>
        /// <value>The second.</value>

        public T Second
		{
            get { return second; }
            set { this.second = value; }
		}

		private T first;
		private T second;

        /// <summary>
        /// Construct pair of values.
        /// </summary>
        /// <param name="first">is the first value</param>
        /// <param name="second">is the second value</param>

        public UniformPair(T first, T second)
		{
			this.first = first;
			this.second = second;
		}

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>
		public  override bool Equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			
			if (!(obj is UniformPair<T>))
			{
				return false;
			}
			
			UniformPair<T> other = (UniformPair<T>) obj;
			
			return
				(first == null?other.first == null:first.Equals(other.first)) && 
				(second == null?other.second == null:second.Equals(other.second));
		}

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			return (first == null?0:first.GetHashCode()) ^ (second == null?0:second.GetHashCode());
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return "Pair [" + first + ':' + second + ']';
		}
	}
}
