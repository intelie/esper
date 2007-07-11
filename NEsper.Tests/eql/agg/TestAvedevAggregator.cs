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
	public class TestAvedevAggregator
	{
	    [Test]
	    public void testAggregateFunction()
	    {
	        AvedevAggregator agg = new AvedevAggregator();
	        Assert.AreEqual(typeof(double?), agg.ValueType);

	        Assert.IsNull(agg.Value);

	        agg.Enter(82);
	        Assert.AreEqual(0D, agg.Value);

	        agg.Enter(78);
	        Assert.AreEqual(2D, agg.Value);

	        agg.Enter(70);
	        double result = (double)agg.Value;
	        Assert.AreEqual("4.4444", Convert.ToString(result).Substring(0, 6));

	        agg.Enter(58);
	        Assert.AreEqual(8D, agg.Value);

	        agg.Enter(42);
	        Assert.AreEqual(12.8D, agg.Value);

	        agg.Leave(82);
	        Assert.AreEqual(12D, agg.Value);

	        agg.Leave(58);
	        result = (double)agg.Value;
	        Assert.AreEqual("14.2222", Convert.ToString(result).Substring(0, 7));
	    }

	}
} // End of namespace
