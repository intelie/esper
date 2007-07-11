// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.filter
{
	[TestFixture]
	public class TestValueSetParamComparator
	{
	    private FilterValueSetParamComparator comparator;

	    [SetUp]
	    public void SetUp()
	    {
	        comparator = new FilterValueSetParamComparator();
	    }

	    [Test]
	    public void testCompareOneByOne()
	    {
	        FilterValueSetParamImpl param1 = new FilterValueSetParamImpl("a", FilterOperator.EQUAL, null);
	        FilterValueSetParamImpl param2 = new FilterValueSetParamImpl("b", FilterOperator.EQUAL, null);
	        FilterValueSetParamImpl param3 = new FilterValueSetParamImpl("c", FilterOperator.EQUAL, null);
	        FilterValueSetParamImpl param4 = new FilterValueSetParamImpl("d", FilterOperator.RANGE_CLOSED, null);
	        FilterValueSetParamImpl param5 = new FilterValueSetParamImpl("e", FilterOperator.RANGE_CLOSED, null);
	        FilterValueSetParamImpl param6 = new FilterValueSetParamImpl("e", FilterOperator.RANGE_CLOSED, null);
	        FilterValueSetParamImpl param7 = new FilterValueSetParamImpl("f", FilterOperator.GREATER, null);
	        FilterValueSetParamImpl param8 = new FilterValueSetParamImpl("g", FilterOperator.NOT_EQUAL, null);
	        FilterValueSetParamImpl param9 = new FilterValueSetParamImpl("h", FilterOperator.IN_LIST_OF_VALUES, null);
	        FilterValueSetParamImpl param10 = new FilterValueSetParamImpl("i", FilterOperator.NOT_RANGE_CLOSED, null);
	        FilterValueSetParamImpl param11 = new FilterValueSetParamImpl("j", FilterOperator.NOT_IN_LIST_OF_VALUES, null);

	        // Compare same comparison types
	        Assert.IsTrue(comparator.Compare(param1, param2) == -1);
	        Assert.IsTrue(comparator.Compare(param2, param1) == 1);
	        Assert.IsTrue(comparator.Compare(param3, param2) == 1);
	        Assert.IsTrue(comparator.Compare(param2, param2) == 0);
	        Assert.IsTrue(comparator.Compare(param8, param1) == 1);
	        Assert.IsTrue(comparator.Compare(param1, param8) == -1);

	        Assert.IsTrue(comparator.Compare(param4, param5) == -1);
	        Assert.IsTrue(comparator.Compare(param5, param4) == 1);
	        Assert.IsTrue(comparator.Compare(param4, param4) == 0);
	        Assert.IsTrue(comparator.Compare(param5, param6) == 0);

	        // Compare across comparison types
	        Assert.IsTrue(comparator.Compare(param7, param6) == 1);
	        Assert.IsTrue(comparator.Compare(param6, param7) == -1);

	        Assert.IsTrue(comparator.Compare(param7, param1) == 1);
	        Assert.IsTrue(comparator.Compare(param1, param7) == -1);

	        Assert.IsTrue(comparator.Compare(param4, param1) == 1);
	        Assert.IsTrue(comparator.Compare(param1, param4) == -1);

	        // 'in' is before all but after equals
	        Assert.IsTrue(comparator.Compare(param9, param4) == -1);
	        Assert.IsTrue(comparator.Compare(param9, param9) == 0);
	        Assert.IsTrue(comparator.Compare(param9, param1) == 1);

	        // inverted range is lower rank
	        Assert.IsTrue(comparator.Compare(param10, param1) == 1);
	        Assert.IsTrue(comparator.Compare(param10, param8) == -1);

	        // not-in is lower rank
	        Assert.IsTrue(comparator.Compare(param11, param1) == 1);
	        Assert.IsTrue(comparator.Compare(param11, param8) == -1);
	    }

	    [Test]
	    public void testCompareAll()
	    {
	        TreeSet<FilterValueSetParam> sorted = new TreeSet<FilterValueSetParam>(comparator);

            Array enumArray = Enum.GetValues(typeof(FilterOperator));

	        FilterValueSetParam[] spec = new FilterValueSetParam[enumArray.Length];
	        for (int i = 0; i < enumArray.Length; i++)
	        {
                FilterOperator op = (FilterOperator)enumArray.GetValue(i);
	            spec[i] = new FilterValueSetParamImpl("somename", op, null);

	            // Add to sorted collection
	            sorted.Add(spec[i]);
	        }

	        Assert.AreEqual(FilterOperator.EQUAL, sorted.First.FilterOperator);
	        Assert.AreEqual(FilterOperator.BOOLEAN_EXPRESSION, sorted.Last.FilterOperator);

	        log.Debug(".testCompareAll " + CollectionHelper.Render(sorted));
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
