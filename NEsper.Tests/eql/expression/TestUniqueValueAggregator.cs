///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.eql.agg;
using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestUniqueValueAggregator
	{
	    private DistinctValueAggregator agg;

	    [SetUp]
	    public void SetUp()
	    {
	        agg = new DistinctValueAggregator(new SupportAggregator());
	    }

	    [Test]
	    public void testEnter()
	    {
	        agg.Enter(1);
	        agg.Enter(10);
	        agg.Enter(null);
	    }

	    [Test]
	    public void testLeave()
	    {
	        agg.Enter(1);
	        agg.Leave(1);

	        try
	        {
	            agg.Leave(1);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testGetValue()
	    {
	        Assert.AreEqual(0, agg.Value);

	        agg.Enter(10);
	        Assert.AreEqual(10, agg.Value);

	        agg.Enter(10);
	        Assert.AreEqual(10, agg.Value);

	        agg.Enter(2);
	        Assert.AreEqual(12, agg.Value);

	        agg.Leave(10);
	        Assert.AreEqual(12, agg.Value);

	        agg.Leave(10);
	        Assert.AreEqual(2, agg.Value);

	        agg.Leave(2);
	        Assert.AreEqual(0, agg.Value);
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(int?), agg.ValueType);
	    }
	}
} // End of namespace
