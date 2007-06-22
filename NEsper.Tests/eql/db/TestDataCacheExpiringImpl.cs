///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.events;
using net.esper.schedule;
using net.esper.support.schedule;

namespace net.esper.eql.db
{
	[TestFixture]
	public class TestDataCacheExpiringImpl
	{
	    private SupportSchedulingServiceImpl scheduler;
	    private DataCacheExpiringImpl cache;
	    private IList<EventBean>[] lists = new IList<EventBean>[10];

	    [SetUp]
	    public void SetUp()
	    {
	        for (int i = 0; i < lists.Length; i++)
	        {
	            lists[i] = new List<EventBean>();
	        }
	    }

	    [Test]
	    public void TestPurgeInterval()
	    {
	        SchedulingServiceImpl scheduler = new SchedulingServiceImpl();
	        cache = new DataCacheExpiringImpl(10, 20, scheduler, null, null);   // age 10 sec, purge 1000 seconds

	        // test single entry in cache
	        scheduler.Time = (5000);
	        cache.PutCached(Make("a"), lists[0]); // a at 5 sec
	        Assert.AreSame(lists[0], cache.GetCached(Make("a")));

	        scheduler.Time = (26000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduler);
	        Assert.AreEqual(0, cache.Size);

	        // test 4 entries in cache
	        scheduler.Time = (30000);
	        cache.PutCached(Make("b"), lists[1]);  // b at 30 sec

	        scheduler.Time = (35000);
	        cache.PutCached(Make("c"), lists[2]);  // c at 35 sec

	        scheduler.Time = (40000);
	        cache.PutCached(Make("d"), lists[3]);  // d at 40 sec

	        scheduler.Time = (45000);
	        cache.PutCached(Make("e"), lists[4]);  // d at 40 sec

	        scheduler.Time = (50000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduler);
	        Assert.AreEqual(2, cache.Size);   // only d and e

	        Assert.AreSame(lists[3], cache.GetCached(Make("d")));
	        Assert.AreSame(lists[4], cache.GetCached(Make("e")));
	    }

	    [Test]
	    public void TestGet()
	    {
	        scheduler = new SupportSchedulingServiceImpl();
	        cache = new DataCacheExpiringImpl(10, 1000, scheduler, null, null);   // age 10 sec, purge 1000 seconds

	        Assert.IsNull(cache.GetCached(Make("a")));

	        scheduler.Time = (5000);
	        cache.PutCached(Make("a"), lists[0]); // a at 5 sec
	        Assert.AreSame(lists[0], cache.GetCached(Make("a")));

	        scheduler.Time = (10000);
	        cache.PutCached(Make("b"), lists[1]); // b at 10 sec
	        Assert.AreSame(lists[0], cache.GetCached(Make("a")));
	        Assert.AreSame(lists[1], cache.GetCached(Make("b")));

	        scheduler.Time = (11000);
	        cache.PutCached(Make("c"), lists[2]); // c at 11 sec
	        cache.PutCached(Make("d"), lists[3]); // d at 11 sec

	        scheduler.Time = (14999);
	        Assert.AreSame(lists[0], cache.GetCached(Make("a")));

	        scheduler.Time = (15000);
	        Assert.AreSame(lists[0], cache.GetCached(Make("a")));

	        scheduler.Time = (15001);
	        Assert.IsNull(cache.GetCached(Make("a")));

	        scheduler.Time = (15001);
	        Assert.IsNull(cache.GetCached(Make("a")));

	        scheduler.Time = (15001);
	        Assert.IsNull(cache.GetCached(Make("a")));
	        Assert.AreSame(lists[1], cache.GetCached(Make("b")));
	        Assert.AreSame(lists[2], cache.GetCached(Make("c")));
	        Assert.AreSame(lists[3], cache.GetCached(Make("d")));

	        scheduler.Time = (20000);
	        Assert.AreSame(lists[1], cache.GetCached(Make("b")));

	        scheduler.Time = (20001);
	        Assert.IsNull(cache.GetCached(Make("b")));

	        scheduler.Time = (21001);
	        Assert.IsNull(cache.GetCached(Make("a")));
	        Assert.IsNull(cache.GetCached(Make("b")));
	        Assert.IsNull(cache.GetCached(Make("c")));
	        Assert.IsNull(cache.GetCached(Make("d")));

	        scheduler.Time = (22000);
	        cache.PutCached(Make("b"), lists[1]); // b at 22 sec
	        cache.PutCached(Make("d"), lists[3]); // d at 22 sec

	        scheduler.Time = (32000);
	        Assert.AreSame(lists[1], cache.GetCached(Make("b")));
	        Assert.AreSame(lists[3], cache.GetCached(Make("d")));
	    }

	    private Object[] Make(String key)
	    {
	        return new Object[] {key};
	    }
	}
} // End of namespace
