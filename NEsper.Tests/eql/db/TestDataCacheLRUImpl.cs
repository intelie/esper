using System;
using System.Collections.Generic;

using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.db
{
	[TestFixture]
    public class TestDataCacheLRUImpl 
    {
        private DataCacheLRUImpl cache;
        private IList<EventBean>[] lists = new List<EventBean>[10];

        [SetUp]
        public virtual void setUp()
        {
            cache = new DataCacheLRUImpl(3);
            for (int i = 0; i < lists.Length; i++)
            {
                lists[i] = new List<EventBean>();
            }
        }

        [Test]
        public virtual void testGet()
        {
            Assert.IsNull(cache.GetCached(make("a")));

            cache.PutCached(make("a"), lists[0]); // a
            Assert.AreSame(lists[0], cache.GetCached(make("a")));

            cache.PutCached(make("b"), lists[1]); // b, a
            Assert.AreSame(lists[1], cache.GetCached(make("b"))); // b, a

            Assert.AreSame(lists[0], cache.GetCached(make("a"))); // a, b

            cache.PutCached(make("c"), lists[2]); // c, a, b
            cache.PutCached(make("d"), lists[3]); // d, c, a  (b gone)

            Assert.IsNull(cache.GetCached(make("b")));

            Assert.AreEqual(lists[2], cache.GetCached(make("c"))); // c, d, a
            Assert.AreEqual(lists[0], cache.GetCached(make("a"))); // a, c, d

            cache.PutCached(make("e"), lists[4]); // e, a, c (d and b gone)

            Assert.IsNull(cache.GetCached(make("d")));
            Assert.IsNull(cache.GetCached(make("b")));
        }

        private Object[] make(String key)
        {
            return new Object[] { key };
        }
    }
}
