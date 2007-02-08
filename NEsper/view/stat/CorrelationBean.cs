using System;
namespace net.esper.view.stat
{
	
	
	/// <summary> Bean for calculating the correlation (same to Microsoft Excel CORREL function).</summary>
	public sealed class CorrelationBean:BaseStatisticsBean
	{
		/// <summary> Return the correlation value for the two data series (Microsoft Excel function CORREL).</summary>
		/// <returns> correlation value
		/// </returns>
		public double Correlation
		{
			get
			{
				if (this.N == 0)
				{
					return Double.NaN;
				}
				
				double dx = this.SumXSq - (this.XSum * this.XSum) / this.N;
				double dy = this.SumYSq - (this.YSum * this.YSum) / this.N;
				
				if (dx == 0 || dy == 0)
				{
					return Double.NaN;
				}
				
				double sp = this.SumXY - this.XSum * this.YSum / this.N;
				return sp / System.Math.Sqrt(dx * dy);
			}
			
		}
	}
}