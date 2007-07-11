///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterOperator
	{
	    [Test]
	    public void testOperatorsFromString()
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
	    public void testRanges()
	    {
	        Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(false, false, false) == FilterOperator.RANGE_OPEN);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(true, true, false) == FilterOperator.RANGE_CLOSED);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(true, false, false) == FilterOperator.RANGE_HALF_OPEN);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(false, true, false) == FilterOperator.RANGE_HALF_CLOSED);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(false, false, true) == FilterOperator.NOT_RANGE_OPEN);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(true, true, true) == FilterOperator.NOT_RANGE_CLOSED);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(true, false, true) == FilterOperator.NOT_RANGE_HALF_OPEN);
            Assert.IsTrue(FilterOperatorHelper.ParseRangeOperator(false, true, true) == FilterOperator.NOT_RANGE_HALF_CLOSED);
	    }

	    [Test]
	    public void testIsComparison()
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
	    public void testIsRange()
	    {
            Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_OPEN));
            Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_CLOSED));
            Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_HALF_OPEN));
            Assert.IsTrue(FilterOperatorHelper.IsRangeOperator(FilterOperator.RANGE_HALF_CLOSED));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.NOT_RANGE_HALF_CLOSED));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.NOT_RANGE_OPEN));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.NOT_RANGE_CLOSED));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.NOT_RANGE_HALF_OPEN));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.LESS));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.EQUAL));
            Assert.IsFalse(FilterOperatorHelper.IsRangeOperator(FilterOperator.NOT_EQUAL));
	    }

	    [Test]
	    public void testIsInvertedRange()
	    {
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.RANGE_OPEN));
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.RANGE_CLOSED));
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.RANGE_HALF_OPEN));
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.RANGE_HALF_CLOSED));
            Assert.IsTrue(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.NOT_RANGE_HALF_CLOSED));
            Assert.IsTrue(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.NOT_RANGE_OPEN));
            Assert.IsTrue(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.NOT_RANGE_CLOSED));
            Assert.IsTrue(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.NOT_RANGE_HALF_OPEN));
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.LESS));
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.EQUAL));
            Assert.IsFalse(FilterOperatorHelper.IsInvertedRangeOperator(FilterOperator.NOT_EQUAL));
	    }
	}
} // End of namespace
