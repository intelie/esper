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
			TimePeriodParameter other = _object as TimePeriodParameter;
			return
				( other != null ) ?
				( other.numSeconds == this.numSeconds ) :
				( false ) ;
		}

		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
}
