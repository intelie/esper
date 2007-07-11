// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.type;

namespace net.esper.eql.agg
{
	[TestFixture]
	public class TestMinMaxAggregator
	{
	    [Test]
	    public void testAggregatorMax()
	    {
	        MinMaxAggregator agg = new MinMaxAggregator(MinMaxTypeEnum.MAX, typeof(int));
	        Assert.AreEqual(null, agg.Value);
	        agg.Enter(10);
	        Assert.AreEqual(10, agg.Value);
	        agg.Enter(20);
	        Assert.AreEqual(20, agg.Value);
	        agg.Enter(10);
	        Assert.AreEqual(20, agg.Value);
	        agg.Leave(10);
	        Assert.AreEqual(20, agg.Value);
	        agg.Leave(20);
	        Assert.AreEqual(10, agg.Value);
	        agg.Leave(10);
	        Assert.AreEqual(null, agg.Value);
	    }

	    [Test]
	    public void testAggregatorMin()
	    {
	        MinMaxAggregator agg = new MinMaxAggregator(MinMaxTypeEnum.MIN, typeof(int));
	        Assert.AreEqual(null, agg.Value);
	        agg.Enter(10);
	        Assert.AreEqual(10, agg.Value);
	        agg.Enter(20);
	        Assert.AreEqual(10, agg.Value);
	        agg.Enter(10);
	        Assert.AreEqual(10, agg.Value);
	        agg.Leave(10);
	        Assert.AreEqual(10, agg.Value);
	        agg.Leave(20);
	        Assert.AreEqual(10, agg.Value);
	        agg.Leave(10);
	        Assert.AreEqual(null, agg.Value);
	    }
	}
} // End of namespace
