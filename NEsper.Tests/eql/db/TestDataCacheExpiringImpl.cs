using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.schedule;
using net.esper.support.schedule;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.db
{
	[TestFixture]
    public class TestDataCacheExpiringImpl 
    {
        private SupportSchedulingServiceImpl scheduler;
        private DataCacheExpiringImpl cache;
        private IList<EventBean>[] lists = new List<EventBean>[10];

        [SetUp]
        public virtual void setUp()
        {
            for (int i = 0; i < lists.Length; i++)
            {
                lists[i] = new List<EventBean>();
            }
        }

        [Test]
        public virtual void testPurgeInterval()
        {
            SchedulingServiceImpl scheduler = new SchedulingServiceImpl();
            cache = new DataCacheExpiringImpl(10, 20, scheduler, null); // age 10 sec, purge 1000 seconds

            // test single entry in cache
            scheduler.Time = 5000;
            cache.PutCached(make("a"), lists[0]); // a at 5 sec
            Assert.AreSame(lists[0], cache.GetCached(make("a")));

            scheduler.Time = 26000;
            scheduler.Evaluate();
            Assert.AreEqual(0, cache.Size);

            // test 4 entries in cache
            scheduler.Time = 30000;
            cache.PutCached(make("b"), lists[1]); // b at 30 sec

            scheduler.Time = 35000;
            cache.PutCached(make("c"), lists[2]); // c at 35 sec

            scheduler.Time = 40000;
            cache.PutCached(make("d"), lists[3]); // d at 40 sec

            scheduler.Time = 45000;
            cache.PutCached(make("e"), lists[4]); // d at 40 sec

            scheduler.Time = 50000;
            scheduler.Evaluate();
            Assert.AreEqual(2, cache.Size); // only d and e

            Assert.AreSame(lists[3], cache.GetCached(make("d")));
            Assert.AreSame(lists[4], cache.GetCached(make("e")));
        }

        [Test]
        public virtual void testGet()
        {
            scheduler = new SupportSchedulingServiceImpl();
            cache = new DataCacheExpiringImpl(10, 1000, scheduler, null); // age 10 sec, purge 1000 seconds

            Assert.IsNull(cache.GetCached(make("a")));

            scheduler.Time = 5000;
            cache.PutCached(make("a"), lists[0]); // a at 5 sec
            Assert.AreSame(lists[0], cache.GetCached(make("a")));

            scheduler.Time = 10000;
            cache.PutCached(make("b"), lists[1]); // b at 10 sec
            Assert.AreSame(lists[0], cache.GetCached(make("a")));
            Assert.AreSame(lists[1], cache.GetCached(make("b")));

            scheduler.Time = 11000;
            cache.PutCached(make("c"), lists[2]); // c at 11 sec
            cache.PutCached(make("d"), lists[3]); // d at 11 sec

            scheduler.Time = 14999;
            Assert.AreSame(lists[0], cache.GetCached(make("a")));

            scheduler.Time = 15000;
            Assert.AreSame(lists[0], cache.GetCached(make("a")));

            scheduler.Time = 15001;
            Assert.IsNull(cache.GetCached(make("a")));

            scheduler.Time = 15001;
            Assert.IsNull(cache.GetCached(make("a")));

            scheduler.Time = 15001;
            Assert.IsNull(cache.GetCached(make("a")));
            Assert.AreSame(lists[1], cache.GetCached(make("b")));
            Assert.AreSame(lists[2], cache.GetCached(make("c")));
            Assert.AreSame(lists[3], cache.GetCached(make("d")));

            scheduler.Time = 20000;
            Assert.AreSame(lists[1], cache.GetCached(make("b")));

            scheduler.Time = 20001;
            Assert.IsNull(cache.GetCached(make("b")));

            scheduler.Time = 21001;
            Assert.IsNull(cache.GetCached(make("a")));
            Assert.IsNull(cache.GetCached(make("b")));
            Assert.IsNull(cache.GetCached(make("c")));
            Assert.IsNull(cache.GetCached(make("d")));

            scheduler.Time = 22000;
            cache.PutCached(make("b"), lists[1]); // b at 22 sec
            cache.PutCached(make("d"), lists[3]); // d at 22 sec

            scheduler.Time = 32000;
            Assert.AreSame(lists[1], cache.GetCached(make("b")));
            Assert.AreSame(lists[3], cache.GetCached(make("d")));
        }

        private Object[] make(String key)
        {
            return new Object[] { key };
        }
    }
}
