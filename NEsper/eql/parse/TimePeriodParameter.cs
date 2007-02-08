using System;
namespace net.esper.eql.parse
{
	
	/// <summary> Parameter for views that accept time period information such as "5 sec 100 msec".</summary>
	public class TimePeriodParameter
	{
		/// <summary> Returns the number of seconds.</summary>
		/// <returns> the number of seconds specified by time period string
		/// </returns>
		virtual public double NumSeconds
		{
			get
			{
				return numSeconds;
			}
			
		}
		private double numSeconds;
		
		/// <summary> Ctor.</summary>
		/// <param name="numSeconds">is the number of seconds represented by time period string
		/// </param>
		public TimePeriodParameter(double numSeconds)
		{
			this.numSeconds = numSeconds;
		}
		
		public  override bool Equals(Object _object)
		{
			if (!(_object is TimePeriodParameter))
			{
				return false;
			}
			TimePeriodParameter other = (TimePeriodParameter) _object;
			return other.numSeconds == this.numSeconds;
		}

		//UPGRADE_NOTE: The following method implementation was automatically added to preserve functionality. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1306'"
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
}
