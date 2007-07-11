// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

namespace net.esper.eql.agg
{
	[TestFixture]
	public class TestStddevAggregator
	{
	    [Test]
	    public void testAggregateFunction()
	    {
	        AggregationMethod agg = new StddevAggregator();
	        Assert.AreEqual(typeof(double?), agg.ValueType);

	        Assert.IsNull(agg.Value);

	        agg.Enter(10);
	        Assert.IsNull(agg.Value);

	        agg.Enter(8);
	        double result = (double)agg.Value;
	        Assert.AreEqual("1.4142", Convert.ToString(result).Substring(0, 6));

	        agg.Enter(5);
	        result = (double)agg.Value;
	        Assert.AreEqual("2.5166", Convert.ToString(result).Substring(0, 6));

	        agg.Enter(9);
	        result = (double)agg.Value;
	        Assert.AreEqual("2.1602", Convert.ToString(result).Substring(0, 6));

	        agg.Leave(10);
	        result = (double)agg.Value;
	        Assert.AreEqual("2.0816", Convert.ToString(result).Substring(0, 6));
	    }

	}


} // End of namespace
