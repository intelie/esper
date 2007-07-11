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
        public void testGet()
        {
            Assert.IsNull(cache.GetCached(Make("a")));

            cache.PutCached(Make("a"), lists[0]); // a
            Assert.AreSame(lists[0], cache.GetCached(Make("a")));

            cache.PutCached(Make("b"), lists[1]); // b, a
            Assert.AreSame(lists[1], cache.GetCached(Make("b"))); // b, a

            Assert.AreSame(lists[0], cache.GetCached(Make("a"))); // a, b

            cache.PutCached(Make("c"), lists[2]); // c, a, b
            cache.PutCached(Make("d"), lists[3]); // d, c, a  (b gone)

            Assert.IsNull(cache.GetCached(Make("b")));

            Assert.AreEqual(lists[2], cache.GetCached(Make("c"))); // c, d, a
            Assert.AreEqual(lists[0], cache.GetCached(Make("a"))); // a, c, d

            cache.PutCached(Make("e"), lists[4]); // e, a, c (d and b gone)

            Assert.IsNull(cache.GetCached(Make("d")));
            Assert.IsNull(cache.GetCached(Make("b")));
        }

        private Object[] Make(String key)
        {
            return new Object[] { key };
        }
    }
}
