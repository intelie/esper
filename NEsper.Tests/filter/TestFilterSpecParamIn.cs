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

using net.esper.collection;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterSpecParamIn
	{
	    private FilterSpecParamIn values;

	    [Test]
	    public void TestEquals()
	    {
	        values = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, GetList(new Object[] {"A", "B"}));
	        FilterSpecParamIn values2 = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, GetList(new Object[] {"A"}));
	        FilterSpecParamIn values3 = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, GetList(new Object[] {"A", "B"}));
	        FilterSpecParamIn values4 = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, GetList(new Object[] {"A", "C"}));

	        Assert.IsFalse(values.Equals(new FilterSpecParamConstant("a", FilterOperator.EQUAL, "a")));
	        Assert.IsFalse(values.Equals(values2));
	        Assert.IsTrue(values.Equals(values3));
	        Assert.IsFalse(values.Equals(values4));
	    }

	    private IList<FilterSpecParamInValue> GetList(Object[] keys)
	    {
            IList<FilterSpecParamInValue> list = new List<FilterSpecParamInValue>();
	        for (int i = 0; i < keys.Length; i++)
	        {
	            list.Add(new InSetOfValuesConstant(keys[i]));
	        }
	        return list;
	    }
	}
} // End of namespace
