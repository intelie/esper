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
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator("!=") == FilterOperator.NOT_EQUAL);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator(">") == FilterOperator.GREATER);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator("=") == FilterOperator.EQUAL);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator("<=") == FilterOperator.LESS_OR_EQUAL);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator("<") == FilterOperator.LESS);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator(">=") == FilterOperator.GREATER_OR_EQUAL);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator("d") == null);
            Assert.IsTrue(FilterOperatorHelper.parseComparisonOperator(null) == null);
		}
		
		[Test]
		public virtual void  testRanges()
		{
            Assert.IsTrue(FilterOperatorHelper.parseRangeOperator(false, false) == FilterOperator.RANGE_OPEN);
			Assert.IsTrue(FilterOperatorHelper.parseRangeOperator(true, true) == FilterOperator.RANGE_CLOSED);
            Assert.IsTrue(FilterOperatorHelper.parseRangeOperator(true, false) == FilterOperator.RANGE_HALF_OPEN);
            Assert.IsTrue(FilterOperatorHelper.parseRangeOperator(false, true) == FilterOperator.RANGE_HALF_CLOSED);
		}
		
		[Test]
		public virtual void  testIsComparison()
		{
			Assert.IsTrue(FilterOperatorHelper.isComparisonOperator(FilterOperator.GREATER));
			Assert.IsTrue(FilterOperatorHelper.isComparisonOperator(FilterOperator.GREATER_OR_EQUAL));
			Assert.IsTrue(FilterOperatorHelper.isComparisonOperator(FilterOperator.LESS));
			Assert.IsTrue(FilterOperatorHelper.isComparisonOperator(FilterOperator.LESS_OR_EQUAL));
			Assert.IsFalse(FilterOperatorHelper.isComparisonOperator(FilterOperator.RANGE_CLOSED));
			Assert.IsFalse(FilterOperatorHelper.isComparisonOperator(FilterOperator.EQUAL));
			Assert.IsFalse(FilterOperatorHelper.isComparisonOperator(FilterOperator.NOT_EQUAL));
		}
		
		[Test]
		public virtual void  testIsRange()
		{
			Assert.IsTrue(FilterOperatorHelper.isRangeOperator(FilterOperator.RANGE_OPEN));
			Assert.IsTrue(FilterOperatorHelper.isRangeOperator(FilterOperator.RANGE_CLOSED));
			Assert.IsTrue(FilterOperatorHelper.isRangeOperator(FilterOperator.RANGE_HALF_OPEN));
			Assert.IsTrue(FilterOperatorHelper.isRangeOperator(FilterOperator.RANGE_HALF_CLOSED));
			Assert.IsFalse(FilterOperatorHelper.isRangeOperator(FilterOperator.LESS));
			Assert.IsFalse(FilterOperatorHelper.isRangeOperator(FilterOperator.EQUAL));
			Assert.IsFalse(FilterOperatorHelper.isRangeOperator(FilterOperator.NOT_EQUAL));
		}
	}
}
