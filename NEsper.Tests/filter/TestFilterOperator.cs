using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestFilterOperator 
	{
		[Test]
		public virtual void  testOperatorsFromString()
		{
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator("!=") == FilterOperator.NOT_EQUAL);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator(">") == FilterOperator.GREATER);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator("=") == FilterOperator.EQUAL);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator("<=") == FilterOperator.LESS_OR_EQUAL);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator("<") == FilterOperator.LESS);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator(">=") == FilterOperator.GREATER_OR_EQUAL);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator("d") == null);
            Assert.IsTrue(FilterOperatorHelper.ParseComparisonOperator(null) == null);
		}
		
		[Test]
		public virtual void  testRanges()
		{
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(false, false) == FilterOperator.RANGE_OPEN);
			Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(true, true) == FilterOperator.RANGE_CLOSED);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(true, false) == FilterOperator.RANGE_HALF_OPEN);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(false, true) == FilterOperator.RANGE_HALF_CLOSED);
		}
		
		[Test]
		public virtual void  testIsComparison()
		{
			Assert.IsTrue(FilterOperatorHelper.IsComparisonOperator(FilterOperator.GREATER));
			Assert.IsTrue(FilterOperatorHelper.IsComparisonOperator(FilterOperator.GREATER_OR_EQUAL));
			Assert.IsTrue(FilterOperatorHelper.IsComparisonOperator(FilterOperator.LESS));
			Assert.IsTrue(FilterOperatorHelper.IsComparisonOperator(FilterOperator.LESS_OR_EQUAL));
			Assert.IsFalse(FilterOperatorHelper.IsComparisonOperator(FilterOperator.RANGE_CLOSED));
			Assert.IsFalse(FilterOperatorHelper.IsComparisonOperator(FilterOperator.EQUAL));
			Assert.IsFalse(FilterOperatorHelper.IsComparisonOperator(FilterOperator.NOT_EQUAL));
		}
		
		[Test]
		public virtual void  testIsRange()
		{
			Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_OPEN));
			Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_CLOSED));
			Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_HALF_OPEN));
			Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_HALF_CLOSED));
			Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.LESS));
			Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.EQUAL));
			Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.NOT_EQUAL));
		}
	}
}
