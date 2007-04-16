using System;

using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events
{
    [TestFixture]
    public class TestBeanEventBean
    {
        internal SupportBean testEvent;

        [SetUp]
        public virtual void setUp()
        {
            testEvent = new SupportBean();
            testEvent.intPrimitive = 10;
        }

        [Test]
        public virtual void testGet()
        {
            EventType eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
            BeanEventBean eventBean = new MyBeanEventBean(testEvent, eventType);

            Assert.AreEqual(eventType, eventBean.EventType);
            Assert.AreEqual(testEvent, eventBean.Underlying);

            Assert.AreEqual(10, eventBean["intPrimitive"]);

            // Test wrong property name
            try
            {
                Object temp = eventBean["dummy"];
                Assert.IsTrue(false);
            }
            catch (PropertyAccessException ex)
            {
                // Expected
                log.Debug(".testGetter Expected exception, msg=" + ex.Message);
            }

            // Test wrong event type - not possible to happen under normal use
            try
            {
                eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBeanSimple));
                eventBean = new MyBeanEventBean(testEvent, eventType);
                Object temp = eventBean["myString"];
                Assert.IsTrue(false);
            }
            catch (PropertyAccessException ex)
            {
                // Expected
                log.Debug(".testGetter Expected exception, msg=" + ex.Message);
            }
        }

        [Test]
        public virtual void testGetComplexProperty()
        {
            SupportBeanCombinedProps _event = SupportBeanCombinedProps.makeDefaultBean();
            EventBean eventBean = SupportEventBeanFactory.createObject(_event);

            Assert.AreEqual("0ma0", eventBean["indexed[0].mapped('0ma').value"]);
            Assert.AreEqual("0ma1", eventBean["indexed[0].mapped('0mb').value"]);
            Assert.AreEqual("1ma0", eventBean["indexed[1].mapped('1ma').value"]);
            Assert.AreEqual("1ma1", eventBean["indexed[1].mapped('1mb').value"]);

            Assert.AreEqual("0ma0", eventBean["array[0].mapped('0ma').value"]);
            Assert.AreEqual("1ma1", eventBean["array[1].mapped('1mb').value"]);

            tryInvalid(eventBean, "array[0].mapprop('0ma').value");
            tryInvalid(eventBean, "dummy");
            tryInvalid(eventBean, "dummy[1]");
            tryInvalid(eventBean, "dummy('dd')");
            tryInvalid(eventBean, "dummy.dummy1");
        }

        private static void tryInvalid(EventBean eventBean, String propName)
        {
            try
            {
                Object temp = eventBean[propName];
                Assert.Fail();
            }
            catch (PropertyAccessException ex)
            {
                // expected
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        public class MyBeanEventBean : BeanEventBean
        {
            public MyBeanEventBean(Object _event, EventType eventType)
                : base(_event, eventType)
            {
            }
        }
    }
}
