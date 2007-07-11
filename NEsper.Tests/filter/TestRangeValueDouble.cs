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
	public class TestRangeValueDouble
	{
		private FilterSpecParamRangeValue[] _params = new FilterSpecParamRangeValue[5];

	    [SetUp]
	    public void SetUp()
	    {
	        _params[0] = new RangeValueDouble(5.5);
	        _params[1] = new RangeValueDouble(0);
	        _params[2] = new RangeValueDouble(5.5);
	    }

	    [Test]
	    public void testGetFilterValue()
	    {
	        Assert.AreEqual(5.5, _params[0].GetFilterValue(null));
	    }

	    [Test]
	    public void testEquals()
	    {
	        Assert.AreNotEqual(_params[0],_params[1]);
	        Assert.AreNotEqual(_params[1],_params[2]);
	        Assert.AreEqual(_params[0],_params[2]);
	    }
	}
} // End of namespace
