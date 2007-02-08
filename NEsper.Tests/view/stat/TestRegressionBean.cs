using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat
{
	
	[TestFixture]
	public class TestRegressionBean 
	{
		private int PRECISION_DIGITS = 6;
		
		[Test]
		public virtual void  testLINEST()
		{
			RegressionBean stat = new RegressionBean();
			
			Assert.AreEqual(Double.NaN, stat.Slope);
			Assert.AreEqual(Double.NaN, stat.YIntercept);
			Assert.AreEqual(0, stat.N);
			
			stat.AddPoint(1, 15);
			Assert.AreEqual(Double.NaN, stat.Slope);
			Assert.AreEqual(Double.NaN, stat.YIntercept);
			Assert.AreEqual(1, stat.N);
			
			stat.AddPoint(2, 20);
			Assert.AreEqual(5d, stat.Slope);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.YIntercept, 10, PRECISION_DIGITS));
			Assert.AreEqual(2, stat.N);
			
			stat.AddPoint(1, 17);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Slope, 4, PRECISION_DIGITS));
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.YIntercept, 12, PRECISION_DIGITS));
			Assert.AreEqual(3, stat.N);
			
			stat.AddPoint(1.4, 14);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Slope, 3.731343284, PRECISION_DIGITS));
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.YIntercept, 11.46268657, PRECISION_DIGITS));
			Assert.AreEqual(4, stat.N);
			
			stat.RemovePoint(1, 17);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Slope, 5.394736842, PRECISION_DIGITS));
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.YIntercept, 8.421052632, PRECISION_DIGITS));
			Assert.AreEqual(3, stat.N);
			
			stat.AddPoint(0, 0);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Slope, 9.764150943, PRECISION_DIGITS));
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.YIntercept, 1.509433962, PRECISION_DIGITS));
			Assert.AreEqual(4, stat.N);
		}
	}
}
