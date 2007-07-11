using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{
    [TestFixture]
    public class TestPropertyCheckHelper
    {
        [Test]
        public void testCheckNumeric()
        {
            EventType mySchema = SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean));

            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "dummy") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "symbol") != null);

            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "volume") == null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "price") == null);

            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "dummy", "dummy2") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "symbol", "dummy2") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "symbol", "price") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "price", "dummy") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "dummy", "price") != null);

            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "price", "price") == null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "price", "volume") == null);
            Assert.IsTrue(PropertyCheckHelper.CheckNumeric(mySchema, "volume", "price") == null);
        }

        [Test]
        public void testCheckLong()
        {
            EventType mySchema = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));

            Assert.AreEqual(null, PropertyCheckHelper.CheckLong(mySchema, "longPrimitive"));
            Assert.AreEqual(null, PropertyCheckHelper.CheckLong(mySchema, "longBoxed"));
            Assert.AreEqual(null, PropertyCheckHelper.CheckLong(mySchema, "longBoxed"));
            Assert.IsTrue(PropertyCheckHelper.CheckLong(mySchema, "dummy") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckLong(mySchema, "intPrimitive") != null);
            Assert.IsTrue(PropertyCheckHelper.CheckLong(mySchema, "doubleBoxed") != null);
        }

        [Test]
        public void testFieldExist()
        {
            EventType mySchema = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));

            Assert.AreEqual(null, PropertyCheckHelper.Exists(mySchema, "longPrimitive"));
            Assert.IsTrue(PropertyCheckHelper.Exists(mySchema, "dummy") != null);
        }

        [Test]
        public void test2FieldExist()
        {
            EventType mySchema = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));

            Assert.AreEqual(null, PropertyCheckHelper.Exists(mySchema, "longPrimitive", "longBoxed"));
            Assert.IsTrue(PropertyCheckHelper.Exists(mySchema, "dummy", "longPrimitive") != null);
            Assert.IsTrue(PropertyCheckHelper.Exists(mySchema, "longPrimitive", "dummy") != null);
            Assert.IsTrue(PropertyCheckHelper.Exists(mySchema, "dummy", "dummy") != null);
        }
    }
}
