using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestDoubleRange 
	{
		[Test]
		public virtual void  testNew()
		{
			DoubleRange range = new DoubleRange(10, 20);
			Assert.AreEqual(20d, range.Max);
			Assert.AreEqual(10d, range.Min);
			
			range = new DoubleRange(20, 10);
			Assert.AreEqual(20d, range.Max);
			Assert.AreEqual(10d, range.Min);
		}
		
		[Test]
		public virtual void  testEquals()
		{
			DoubleRange rangeOne = new DoubleRange(10, 20);
			DoubleRange rangeTwo = new DoubleRange(20, 10);
			DoubleRange rangeThree = new DoubleRange(20, 11);
			DoubleRange rangeFour = new DoubleRange(21, 10);
			
			Assert.AreEqual(rangeOne, rangeTwo);
			Assert.AreEqual(rangeTwo, rangeOne);
			Assert.IsFalse(rangeOne.Equals(rangeThree));
			Assert.IsFalse(rangeOne.Equals(rangeFour));
			Assert.IsFalse(rangeThree.Equals(rangeFour));
		}
		
		[Test]
		public virtual void  testHash()
		{
			DoubleRange range = new DoubleRange(10, 20);
			Double a = 10 ;
			Double b = 20 ;
			int hashCode = a.GetHashCode() ^ b.GetHashCode();
			
			Assert.AreEqual(hashCode, range.GetHashCode());
		}
	}
}
