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
	public class TestMedianAggregator
	{
	    [Test]
	    public void testAggregator()
	    {
	        MedianAggregator median = new MedianAggregator();
	        Assert.AreEqual(null, median.Value);
	        median.Enter(10);
	        Assert.AreEqual(10D, median.Value);
	        median.Enter(20);
	        Assert.AreEqual(15D, median.Value);
	        median.Enter(10);
	        Assert.AreEqual(10D, median.Value);

	        median.Leave(10);
	        Assert.AreEqual(15D, median.Value);
	        median.Leave(10);
	        Assert.AreEqual(20D, median.Value);
	        median.Leave(20);
	        Assert.AreEqual(null, median.Value);
	    }
	}


} // End of namespace
