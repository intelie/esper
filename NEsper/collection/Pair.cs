using System;
namespace net.esper.collection
{
	
	/// <summary> General-purpose pair of values of any type. The pair only equals another pair if
	/// the objects that form the pair equal, ie. first pair first object equals (.equals) the second pair first object,
	/// and the first pair second object equals the second pair second object.
	/// </summary>
	public sealed class Pair<FirstT, SecondT>
	{
		//UPGRADE_NOTE: Respective javadoc comments were merged.  It should be changed in order to comply with .NET documentation conventions. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1199'"
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
		
		public Pair(FirstT first, SecondT second)
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
			
			if (!(obj is Pair<FirstT,SecondT>))
			{
				return false;
			}
			
			Pair<FirstT,SecondT> other = (Pair<FirstT,SecondT>) obj;
			
			return
				(first == null?other.first == null:first.Equals(other.first)) &&
				(second == null?other.second == null:second.Equals(other.second));
		}
		
		public override int GetHashCode()
		{
			return
				(first == null?0:first.GetHashCode()) ^
				(second == null?0:second.GetHashCode());
		}
		
		public override String ToString()
		{
			return "Pair [" + first + ':' + second + ']';
		}
	}
}
