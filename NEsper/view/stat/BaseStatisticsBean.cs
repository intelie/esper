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
                if (_dataPoints == 0)
				{
					return Double.NaN;
				}

                double temp = (_sumXSq - _sumX * _sumX / _dataPoints) / _dataPoints;
				return Math.Sqrt(temp);
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
                if (_dataPoints == 0)
				{
					return Double.NaN;
				}

                double temp = (_sumYSq - _sumY * _sumY / _dataPoints) / _dataPoints;
				return Math.Sqrt(temp);
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
                if (_dataPoints < 2)
				{
					return Double.NaN;
				}
				
				double variance = XVariance;
				return Math.Sqrt(variance);
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
                if (_dataPoints < 2)
				{
					return Double.NaN;
				}
				
				double variance = YVariance;
				return Math.Sqrt(variance);
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
                if (_dataPoints < 2)
				{
					return Double.NaN;
				}

                double variance = (_sumXSq - _sumX * _sumX / _dataPoints) / (_dataPoints - 1);
				return variance;
			}
		}

        virtual public double xvariance
        {
            get { return this.XVariance; }
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
                if (_dataPoints < 2)
				{
					return Double.NaN;
				}

                double variance = (_sumYSq - _sumY * _sumY / _dataPoints) / (_dataPoints - 1);
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
                return _dataPoints;
			}
		}

		/// <summary> Returns the sum of all X data points.</summary>
		/// <returns> sum of X data points
		/// </returns>
		virtual public double XSum
		{
			get
			{
                return _sumX;
			}			
		}

        virtual public double xsum
        {
            get { return this.XSum; }
        }

		/// <summary> Returns the sum of all Y data points.</summary>
		/// <returns> sum of Y data points
		/// </returns>
		virtual public double YSum
		{
			get
			{
                return _sumY;
			}
		}

        virtual public double ysum
        {
            get { return this.YSum; }
        }
        
        /// <summary> Returns the average of all X data points.</summary>
		/// <returns> average of X data points
		/// </returns>
		virtual public double XAverage
		{
			get
			{
                if (_dataPoints == 0)
				{
					return Double.NaN;
				}

                return _sumX / _dataPoints;
			}			
		}

        virtual public double xaverage
        {
            get { return this.XAverage; }
        }
        
        /// <summary> Returns the average of all Y data points.</summary>
		/// <returns> average of Y data points
		/// </returns>
		virtual public double YAverage
		{
			get
			{
                if (_dataPoints == 0)
				{
					return Double.NaN;
				}

                return _sumY / _dataPoints;
			}			
		}

        virtual public double yaverage
        {
            get { return this.YAverage; }
        }

		/// <summary> For use by subclasses, returns sum (X * X).</summary>
		/// <returns> sum of X squared
		/// </returns>
		virtual public double SumXSq
		{
			get
			{
                return _sumXSq;
			}			
		}

		/// <summary> For use by subclasses, returns sum (Y * Y).</summary>
		/// <returns> sum of Y squared
		/// </returns>
		virtual public double SumYSq
		{
			get
			{
                return _sumYSq;
			}			
		}

		/// <summary> For use by subclasses, returns sum (X * Y).</summary>
		/// <returns> sum of X times Y
		/// </returns>
		virtual public double SumXY
		{
			get
			{
                return _sumXY;
			}
        }

		private double _sumX;
        private double _sumXSq;
        private double _sumY;
        private double _sumYSq;
        private double _sumXY;
        private long _dataPoints;
		
		private void  Initialize()
		{
            _sumX = 0;
            _sumXSq = 0;
            _sumY = 0;
            _sumYSq = 0;
            _sumXY = 0;
            _dataPoints = 0;
		}
		
		/// <summary> Add a data point for the X data set only.</summary>
		/// <param name="x">is the X data point to add.
		/// </param>
		public void AddPoint(double x)
		{
            _dataPoints++;
            _sumX += x;
            _sumXSq += x * x;
		}
		
		/// <summary> Add a data point.</summary>
		/// <param name="x">is the X data point to add.
		/// </param>
		/// <param name="y">is the Y data point to add.
		/// </param>
		public void AddPoint(double x, double y)
		{
            _dataPoints++;
            _sumX += x;
            _sumXSq += x * x;
            _sumY += y;
            _sumYSq += y * y;
            _sumXY += x * y;
		}
		
		/// <summary> Remove a X data point only.</summary>
		/// <param name="x">is the X data point to remove.
		/// </param>
		public void RemovePoint(double x)
		{
            _dataPoints--;
            if (_dataPoints <= 0)
			{
				Initialize();
			}
			else
			{
                _sumX -= x;
                _sumXSq -= x * x;
			}
		}
		
		/// <summary> Remove a data point.</summary>
		/// <param name="x">is the X data point to remove.
		/// </param>
		/// <param name="y">is the Y data point to remove.
		/// </param>
		public void RemovePoint(double x, double y)
		{
            _dataPoints--;
            if (_dataPoints <= 0)
			{
				Initialize();
			}
			else
			{
                _sumX -= x;
                _sumXSq -= x * x;
                _sumY -= y;
                _sumYSq -= y * y;
                _sumXY -= x * y;
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
                "datapoints=" + this._dataPoints +
                "  sumX=" + this._sumX +
                "  sumXSq=" + this._sumXSq +
                "  sumY=" + this._sumY +
                "  sumYSq=" + this._sumYSq +
                "  sumXY=" + this._sumXY;
		}
	}
}
