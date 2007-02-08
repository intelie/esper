using System;

using net.esper.client;
using net.esper.view;

namespace net.esper.view.stat
{
	/// <summary> Bean for performing statistical calculations. The bean keeps sums of X and Y datapoints and sums on squares
	/// that can be reused by subclasses. The bean calculates standard deviation (sample and population), variance,
	/// average and sum.
	/// </summary>
	public class BaseStatisticsBean : ICloneable
	{
		/// <summary> Calculates standard deviation for X based on the entire population given as arguments.
		/// Equivalent to Microsoft Excel formula STDEVPA.
		/// </summary>
		/// <returns> standard deviation assuming population for X
		/// </returns>
		virtual public double XStandardDeviationPop
		{
			get
			{
				if (dataPoints == 0)
				{
					return Double.NaN;
				}
				
				double temp = (sumXSq - sumX * sumX / dataPoints) / dataPoints;
				return System.Math.Sqrt(temp);
			}
			
		}
		/// <summary> Calculates standard deviation for Y based on the entire population given as arguments.
		/// Equivalent to Microsoft Excel formula STDEVPA.
		/// </summary>
		/// <returns> standard deviation assuming population for Y
		/// </returns>
		virtual public double YStandardDeviationPop
		{
			get
			{
				if (dataPoints == 0)
				{
					return Double.NaN;
				}
				
				double temp = (sumYSq - sumY * sumY / dataPoints) / dataPoints;
				return System.Math.Sqrt(temp);
			}
			
		}
		/// <summary> Calculates standard deviation for X based on the sample data points supplied.
		/// Equivalent to Microsoft Excel formula STDEV.
		/// </summary>
		/// <returns> standard deviation assuming sample for X
		/// </returns>
		virtual public double XStandardDeviationSample
		{
			get
			{
				if (dataPoints < 2)
				{
					return Double.NaN;
				}
				
				double variance = XVariance;
				return System.Math.Sqrt(variance);
			}
			
		}
		/// <summary> Calculates standard deviation for Y based on the sample data points supplied.
		/// Equivalent to Microsoft Excel formula STDEV.
		/// </summary>
		/// <returns> standard deviation assuming sample for Y
		/// </returns>
		virtual public double YStandardDeviationSample
		{
			get
			{
				if (dataPoints < 2)
				{
					return Double.NaN;
				}
				
				double variance = YVariance;
				return System.Math.Sqrt(variance);
			}
			
		}
		/// <summary> Calculates standard deviation for X based on the sample data points supplied.
		/// Equivalent to Microsoft Excel formula STDEV.
		/// </summary>
		/// <returns> variance as the square of the sample standard deviation for X
		/// </returns>
		virtual public double XVariance
		{
			get
			{
				if (dataPoints < 2)
				{
					return Double.NaN;
				}
				
				double variance = (sumXSq - sumX * sumX / dataPoints) / (dataPoints - 1);
				return variance;
			}
			
		}
		/// <summary> Calculates standard deviation for Y based on the sample data points supplied.
		/// Equivalent to Microsoft Excel formula STDEV.
		/// </summary>
		/// <returns> variance as the square of the sample standard deviation for Y
		/// </returns>
		virtual public double YVariance
		{
			get
			{
				if (dataPoints < 2)
				{
					return Double.NaN;
				}
				
				double variance = (sumYSq - sumY * sumY / dataPoints) / (dataPoints - 1);
				return variance;
			}
			
		}
		/// <summary> Returns the number of data points.</summary>
		/// <returns> number of data points
		/// </returns>
		virtual public long N
		{
			get
			{
				return dataPoints;
			}
			
		}
		/// <summary> Returns the sum of all X data points.</summary>
		/// <returns> sum of X data points
		/// </returns>
		virtual public double XSum
		{
			get
			{
				return sumX;
			}
			
		}
		/// <summary> Returns the sum of all Y data points.</summary>
		/// <returns> sum of Y data points
		/// </returns>
		virtual public double YSum
		{
			get
			{
				return sumY;
			}
			
		}
		/// <summary> Returns the average of all X data points.</summary>
		/// <returns> average of X data points
		/// </returns>
		virtual public double XAverage
		{
			get
			{
				if (dataPoints == 0)
				{
					return Double.NaN;
				}
				
				return sumX / dataPoints;
			}
			
		}
		/// <summary> Returns the average of all Y data points.</summary>
		/// <returns> average of Y data points
		/// </returns>
		virtual public double YAverage
		{
			get
			{
				if (dataPoints == 0)
				{
					return Double.NaN;
				}
				
				return sumY / dataPoints;
			}
			
		}
		/// <summary> For use by subclasses, returns sum (X * X).</summary>
		/// <returns> sum of X squared
		/// </returns>
		virtual public double SumXSq
		{
			get
			{
				return sumXSq;
			}
			
		}
		/// <summary> For use by subclasses, returns sum (Y * Y).</summary>
		/// <returns> sum of Y squared
		/// </returns>
		virtual public double SumYSq
		{
			get
			{
				return sumYSq;
			}
			
		}
		/// <summary> For use by subclasses, returns sum (X * Y).</summary>
		/// <returns> sum of X times Y
		/// </returns>
		virtual public double SumXY
		{
			get
			{
				return sumXY;
			}
			
		}
		private double sumX;
		private double sumXSq;
		private double sumY;
		private double sumYSq;
		private double sumXY;
		private long dataPoints;
		
		private void  Initialize()
		{
			sumX = 0;
			sumXSq = 0;
			sumY = 0;
			sumYSq = 0;
			sumXY = 0;
			dataPoints = 0;
		}
		
		/// <summary> Add a data point for the X data set only.</summary>
		/// <param name="x">is the X data point to add.
		/// </param>
		public void AddPoint(double x)
		{
			dataPoints++;
			sumX += x;
			sumXSq += x * x;
		}
		
		/// <summary> Add a data point.</summary>
		/// <param name="x">is the X data point to add.
		/// </param>
		/// <param name="y">is the Y data point to add.
		/// </param>
		public void AddPoint(double x, double y)
		{
			dataPoints++;
			sumX += x;
			sumXSq += x * x;
			sumY += y;
			sumYSq += y * y;
			sumXY += x * y;
		}
		
		/// <summary> Remove a X data point only.</summary>
		/// <param name="x">is the X data point to remove.
		/// </param>
		public void RemovePoint(double x)
		{
			dataPoints--;
			if (dataPoints <= 0)
			{
				Initialize();
			}
			else
			{
				sumX -= x;
				sumXSq -= x * x;
			}
		}
		
		/// <summary> Remove a data point.</summary>
		/// <param name="x">is the X data point to remove.
		/// </param>
		/// <param name="y">is the Y data point to remove.
		/// </param>
		public void RemovePoint(double x, double y)
		{
			dataPoints--;
			if (dataPoints <= 0)
			{
				Initialize();
			}
			else
			{
				sumX -= x;
				sumXSq -= x * x;
				sumY -= y;
				sumYSq -= y * y;
				sumXY -= x * y;
			}
		}
		
		public virtual Object Clone()
		{
			try
			{
				return base.MemberwiseClone();
			}
			catch (System.Exception e)
			{
				throw new EPException(e);
			}
		}
		
		public override String ToString()
		{
			return
                "datapoints=" + this.dataPoints +
                "  sumX=" + this.sumX +
                "  sumXSq=" + this.sumXSq +
                "  sumY=" + this.sumY +
                "  sumYSq=" + this.sumYSq + 
                "  sumXY=" + this.sumXY;
		}
	}
}
