using System;
using System.Collections.Generic;

namespace net.esper.collection
{
	/// <summary> General-purpose pair of values of any type. The pair only equals another pair if
	/// the objects that form the pair equal, ie. first pair first object equals (.equals) the second pair first object,
	/// and the first pair second object equals the second pair second object.
	/// </summary>
    
    public sealed class UniformPair<T>
	{
		/// <summary> Returns first value within pair.</summary>
		/// <returns> first value within pair
		/// </returns>
		/// <summary> Set the first value of the pair to a new value.</summary>
		/// <param name="first">value to be set
		/// </param>
		public T First
		{
			get { return first; }
			set { this.first = value; }
	    }

		/// <summary> Returns second value within pair.</summary>
		/// <returns> second value within pair
		/// </returns>
		/// <summary> Set the second value of the pair to a new value.</summary>
		/// <param name="second">value to be set
		/// </param>

        public T Second
		{
            get { return second; }
            set { this.second = value; }
		}

		private T first;
		private T second;
		
		/// <summary> Construct pair of values.</summary>
		/// <param name="first">is the first value
		/// </param>
		/// <param name="second">is the second value
		/// </param>

        public UniformPair(T first, T second)
		{
			this.first = first;
			this.second = second;
		}
		
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
		
		public override int GetHashCode()
		{
			return (first == null?0:first.GetHashCode()) ^ (second == null?0:second.GetHashCode());
		}
		
		public override String ToString()
		{
			return "Pair [" + first + ':' + second + ']';
		}
	}
}
