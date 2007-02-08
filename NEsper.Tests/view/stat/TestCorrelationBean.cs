using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat
{
	
	[TestFixture]
	public class TestCorrelationBean 
	{
		//UPGRADE_NOTE: Final was removed from the declaration of 'PRECISION_DIGITS '. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1003'"
		private int PRECISION_DIGITS = 6;
		
		[Test]
		public virtual void  testCORREL()
		{
			CorrelationBean stat = new CorrelationBean();
			
			Assert.AreEqual(Double.NaN, stat.Correlation);
			Assert.AreEqual(0, stat.N);
			
			stat.AddPoint(1, 10);
			Assert.AreEqual(Double.NaN, stat.Correlation);
			Assert.AreEqual(1, stat.N);
			
			stat.AddPoint(2, 20);
			Assert.AreEqual(1d, stat.Correlation);
			Assert.AreEqual(2, stat.N);
			
			stat.AddPoint(1.5, 14);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Correlation, 0.993399268, PRECISION_DIGITS));
			Assert.AreEqual(3, stat.N);
			
			stat.AddPoint(1.4, 14);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Correlation, 0.992631989, PRECISION_DIGITS));
			Assert.AreEqual(4, stat.N);
			
			stat.RemovePoint(1.5, 14);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Correlation, 1, PRECISION_DIGITS));
			Assert.AreEqual(3, stat.N);
			
			stat.AddPoint(100, 1);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(stat.Correlation, - 0.852632057, PRECISION_DIGITS));
			Assert.AreEqual(4, stat.N);
		}
	}
}
