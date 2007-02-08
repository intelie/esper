using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestFilterSpecParamRange 
	{
		[Test]
		public virtual void  testConstruct()
		{
			DoubleRange range = new DoubleRange(3, 3);
			
			makeParam("a", FilterOperator.RANGE_HALF_OPEN, range);
			
			try
			{
				makeParam("a", FilterOperator.EQUAL, range);
				Assert.IsTrue(false);
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
		}
		
		[Test]
		public virtual void  testEquals()
		{
			FilterSpecParam c1 = makeParam("a", FilterOperator.RANGE_CLOSED, new DoubleRange(5, 6));
			FilterSpecParam c2 = makeParam("b", FilterOperator.RANGE_CLOSED, new DoubleRange(5, 6));
			FilterSpecParam c3 = makeParam("a", FilterOperator.RANGE_HALF_CLOSED, new DoubleRange(5, 6));
			FilterSpecParam c4 = makeParam("a", FilterOperator.RANGE_CLOSED, new DoubleRange(7, 6));
			FilterSpecParam c5 = makeParam("a", FilterOperator.RANGE_CLOSED, new DoubleRange(5, 6));
			
			Assert.IsFalse(c1.Equals(c2));
			Assert.IsFalse(c1.Equals(c3));
			Assert.IsFalse(c1.Equals(c4));
			Assert.IsTrue(c1.Equals(c5));
		}
		
		private FilterSpecParamRange makeParam(String propertyName, FilterOperator filterOp, DoubleRange doubleRange)
		{
			return new FilterSpecParamRange(propertyName, filterOp, new RangeValueDouble(doubleRange.Min), new RangeValueDouble(doubleRange.Max));
		}
	}
}
