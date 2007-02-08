using System;
namespace net.esper.view.stat
{
	
	
	/// <summary> Bean for calculating the regression slope and y intercept (same to Microsoft Excel LINEST function).</summary>
	public sealed class RegressionBean:BaseStatisticsBean
	{
		/// <summary> Returns the Y intercept.</summary>
		/// <returns> Y intercept
		/// </returns>
		public double YIntercept
		{
			get
			{
				double slope = Slope;
				
				if (slope == Double.NaN)
				{
					return Double.NaN;
				}
				
				return YSum / N - Slope * XSum / N;
			}
			
		}
		/// <summary> Returns the slope.</summary>
		/// <returns> regression slope
		/// </returns>
		public double Slope
		{
			get
			{
				if (this.N == 0)
				{
					return Double.NaN;
				}
				
				double ssx = SumXSq - XSum * XSum / N;
				
				if (ssx == 0)
				{
					return Double.NaN;
				}
				
				double sp = SumXY - this.XSum * this.YSum / N;
				
				return sp / ssx;
			}
			
		}
	}
}