using System;

using net.esper.eql.core;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{

    [TestFixture]
    public class TestUniqueValueAggregator
    {
        private UniqueValueAggregator agg;

        [SetUp]
        public virtual void setUp()
        {
            agg = new UniqueValueAggregator(new SupportAggregator());
        }

        [Test]
        public virtual void testEnter()
        {
            agg.Enter(1);
            agg.Enter(10);
            agg.Enter(null);
        }

        [Test]
        public virtual void testLeave()
        {
            agg.Enter(1);
            agg.Leave(1);

            try
            {
                agg.Leave(1);
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testNewAggregator()
        {
            agg.Enter(1);
            Assert.AreNotSame(agg, agg.NewAggregator());
            Assert.AreEqual(0, agg.NewAggregator().Value);
        }

        [Test]
        public virtual void testGetValue()
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
        public virtual void testGetType()
        {
            Assert.AreEqual(typeof(Int32), agg.ValueType);
        }
    }
}