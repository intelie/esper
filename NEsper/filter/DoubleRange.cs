using System;
namespace net.esper.filter
{
	
	
	/// <summary> Holds a range of double values with a minimum (Start) value and a maximum (end) value.</summary>
	public sealed class DoubleRange
	{
		/// <summary> Returns low endpoint.</summary>
		/// <returns> low endpoint
		/// </returns>
		public double Min
		{
			get
			{
				return min;
			}
			
		}
		/// <summary> Returns high endpoint.</summary>
		/// <returns> high endpoint
		/// </returns>
		public double Max
		{
			get
			{
				return max;
			}
			
		}
		private double min;
		private double max;
		private int hashCode;
		
		/// <summary> Constructor - takes range endpoints.</summary>
		/// <param name="min">is the low endpoint
		/// </param>
		/// <param name="max">is the high endpoint
		/// </param>
		public DoubleRange(double min, double max)
		{
			this.min = min;
			this.max = max;
			
			if (min > max)
			{
				this.max = min;
				this.min = max;
			}
			
			hashCode = ((Double) min).GetHashCode() ^ ((Double) max).GetHashCode();
		}
		
		public  override bool Equals(Object other)
		{
			if (other == this)
			{
				return true;
			}
			if (!(other is DoubleRange))
			{
				return false;
			}
			
			DoubleRange otherRange = (DoubleRange) other;
			return ((otherRange.max == this.max) && (otherRange.min == this.min));
		}
		
		public override int GetHashCode()
		{
			return hashCode;
		}
		
		public override String ToString()
		{
			return "DoubleRange" + " min=" + min + " max=" + max;
		}
	}
}