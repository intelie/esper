using System;
namespace net.esper.collection
{
	
	/// <summary> General-purpose pair of values of any type. The pair equals another pair if
	/// the objects that form the pair equal in any order, ie. first pair first object equals (.equals)
	/// the second pair first object or second object, and the first pair second object equals the second pair first object
	/// or second object.
	/// </summary>
	public sealed class InterchangeablePair<FirstT,SecondT>
	{
		/// <summary> Returns first value within pair.</summary>
		/// <returns> first value within pair
		/// </returns>
		/// <summary> Set the first value of the pair to a new value.</summary>
		/// <param name="first">value to be set
		/// </param>
		public FirstT First
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
		public SecondT Second
		{
			get { return second; }
			set { this.second = value; }
		}
		
		private FirstT first;
		private SecondT second;
		
		/// <summary> Construct pair of values.</summary>
		/// <param name="first">is the first value
		/// </param>
		/// <param name="second">is the second value
		/// </param>
		public InterchangeablePair(FirstT first, SecondT second)
		{
			this.first = first;
			this.second = second;
		}
		
		public override bool Equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			
			if (!(obj is InterchangeablePair<FirstT,SecondT>))
			{
				return false;
			}

			InterchangeablePair<FirstT, SecondT> other = (InterchangeablePair<FirstT, SecondT>) obj;
			
			if ((first == null) && (second == null))
			{
				return ((other.first == null) && (other.second == null));
			}
			
			if ((first == null) && (second != null))
			{
				if (other.second != null)
				{
					return (other.first == null) && second.Equals(other.second);
				}
				else
				{
					return second.Equals(other.first);
				}
			}
			
			if ((first != null) && (second == null))
			{
				if (other.first != null)
				{
					return first.Equals(other.first) && (other.second == null);
				}
				else
				{
					return first.Equals(other.second);
				}
			}
			
			return (
                (first.Equals(other.first) && second.Equals(other.second)) ||
                (first.Equals(other.second) && second.Equals(other.first))
                );
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