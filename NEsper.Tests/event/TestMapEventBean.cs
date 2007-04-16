using System;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events
{

	[TestFixture]
    public class TestMapEventBean 
    {
        private EDictionary<String, Type> testTypesMap;
        private EDataDictionary testValuesMap;

        private EventType eventType;
        private MapEventBean eventBean;

        private SupportBeanComplexProps supportBean = SupportBeanComplexProps.makeDefaultBean();

        [SetUp]
        public virtual void setUp()
        {
            testTypesMap = new EHashDictionary<String, Type>();
            testTypesMap.Put("aString", typeof(String));
            testTypesMap.Put("anInt", typeof(Int32));
            testTypesMap.Put("myComplexBean", typeof(SupportBeanComplexProps));

            testValuesMap = new EDataDictionary() ;
            testValuesMap.Put("aString", "test");
            testValuesMap.Put("anInt", 10);
            testValuesMap.Put("myComplexBean", supportBean);

            eventType = new MapEventType(testTypesMap, SupportEventAdapterService.Service);
            eventBean = new MapEventBean(testValuesMap, eventType);
        }

        [Test]
        public virtual void testGet()
        {
            Assert.AreEqual(eventType, eventBean.EventType);
            Assert.AreEqual(testValuesMap, eventBean.Underlying);

            Assert.AreEqual("test", eventBean["aString"]);
            Assert.AreEqual(10, eventBean["anInt"]);

            Assert.AreEqual("nestedValue", eventBean["myComplexBean.nested.nestedValue"]);

            // test wrong property name
            try
            {
                Object temp = eventBean["dummy"];
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected
                log.Debug(".testGetter Expected exception, msg=" + ex.Message);
            }
        }

        [Test]
        public virtual void testEquals()
        {
            MapEventBean other = new MapEventBean(testValuesMap, eventType);
            Assert.IsTrue(eventBean.Equals(other));

            testValuesMap.Put("aString", "xxx");
            other = new MapEventBean(testValuesMap, eventType);
            Assert.IsFalse(eventBean.Equals(other));
            Assert.IsFalse(other.Equals(eventBean));

            testValuesMap.Put("aString", "test");
            other = new MapEventBean(testValuesMap, eventType);
            Assert.IsTrue(eventBean.Equals(other));

            // try another event type
            EventType otherEventType = SupportEventAdapterService.Service.CreateAnonymousMapType(testTypesMap);
            other = new MapEventBean(testValuesMap, otherEventType);
            Assert.IsFalse(other.Equals(eventBean));
            Assert.IsFalse(eventBean.Equals(other));

            // Try same event type but differint object references
            testValuesMap.Put("anInt", 10);
            other = new MapEventBean(testValuesMap, eventType);
            Assert.IsTrue(eventBean.Equals(other));

            // try null values
            MapEventBean beanOne = new MapEventBean(testValuesMap, eventType);

            EDataDictionary beanTwoValues = new EDataDictionary();
            beanTwoValues.Put("aString", null);
            beanTwoValues.Put("anInt", null);
            MapEventBean beanTwo = new MapEventBean(beanTwoValues, eventType);
            Assert.IsFalse(beanOne.Equals(beanTwo));
            Assert.IsFalse(beanTwo.Equals(beanOne));
        }

        [Test]
        public virtual void testCreateUnderlying()
        {
            SupportBean beanOne = new SupportBean();
            SupportBean_A beanTwo = new SupportBean_A("a");

            EventBean eventOne = SupportEventBeanFactory.createObject(beanOne);
            EventBean eventTwo = SupportEventBeanFactory.createObject(beanTwo);

            // Set up event type
            testTypesMap.Clear();
            testTypesMap.Put("a", typeof(SupportBean));
            testTypesMap.Put("b", typeof(SupportBean_A));
            EventType eventType = SupportEventAdapterService.Service.CreateAnonymousMapType(testTypesMap);

            EDictionary<String,EventBean> events = new EHashDictionary<String,EventBean>();
            events.Put("a", eventOne);
            events.Put("b", eventTwo);

            MapEventBean _event = new MapEventBean(eventType, events);
            Assert.IsTrue(_event["a"] == beanOne);
            Assert.IsTrue(_event["b"] == beanTwo);
        }

        [Test]
        public virtual void testHash()
        {
            // try out with a null value
            testValuesMap.Put("aString", null);
            testValuesMap.Put("myComplexBean", null);
            eventBean = new MapEventBean(testValuesMap, eventType);

            Assert.AreEqual((10).GetHashCode() ^ "anInt".GetHashCode(), eventBean.GetHashCode());
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
