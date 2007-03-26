using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events
{
	[TestFixture]
    public class TestMapEventType 
    {
        private EventType eventType;
        private EventAdapterService eventAdapterService;

        [SetUp]
        public virtual void setUp()
        {
            eventAdapterService = SupportEventAdapterService.Service;

            EDictionary<String, Type> testTypesMap = new EHashDictionary<String, Type>();
            testTypesMap.Put("myInt", typeof(int));
            testTypesMap.Put("myString", typeof(String));
            testTypesMap.Put("myNullableString", typeof(String));
            testTypesMap.Put("mySupportBean", typeof(SupportBean));
            testTypesMap.Put("myComplexBean", typeof(SupportBeanComplexProps));
            testTypesMap.Put("myNullableSupportBean", typeof(SupportBean));
            eventType = new MapEventType(testTypesMap, eventAdapterService);
        }

        [Test]
        public virtual void testGetPropertyNames()
        {
            ICollection<string> properties = eventType.PropertyNames;
            ArrayAssertionUtil.assertEqualsAnyOrder(
                (ICollection<string>) properties,
                (ICollection<string>) new String[] { 
                    "myInt",
                    "myString",
                    "myNullableString",
                    "mySupportBean",
                    "myComplexBean",
                    "myNullableSupportBean" 
                });
        }

        [Test]
        public virtual void testGetPropertyType()
        {
            Assert.AreEqual(typeof(int), eventType.GetPropertyType("myInt"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("myString"));
            Assert.AreEqual(typeof(SupportBean), eventType.GetPropertyType("mySupportBean"));
            Assert.AreEqual(typeof(SupportBeanComplexProps), eventType.GetPropertyType("myComplexBean"));
            Assert.AreEqual(typeof(int), eventType.GetPropertyType("mySupportBean.intPrimitive"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("myComplexBean.nested.nestedValue"));
            Assert.AreEqual(typeof(int), eventType.GetPropertyType("myComplexBean.indexed[1]"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("myComplexBean.mapped('a')"));

            Assert.IsNull(eventType.GetPropertyType("dummy"));
            Assert.IsNull(eventType.GetPropertyType("mySupportBean.dfgdg"));
            Assert.IsNull(eventType.GetPropertyType("xxx.intPrimitive"));
            Assert.IsNull(eventType.GetPropertyType("myComplexBean.nested.nestedValueXXX"));
        }

        [Test]
        public virtual void testGetUnderlyingType()
        {
            Assert.AreEqual(typeof(System.Collections.IDictionary), eventType.UnderlyingType);
        }

        [Test]
        public virtual void testIsValidProperty()
        {
            Assert.IsTrue(eventType.IsProperty("myInt"));
            Assert.IsTrue(eventType.IsProperty("myString"));
            Assert.IsTrue(eventType.IsProperty("mySupportBean.intPrimitive"));
            Assert.IsTrue(eventType.IsProperty("myComplexBean.nested.nestedValue"));
            Assert.IsTrue(eventType.IsProperty("myComplexBean.indexed[1]"));
            Assert.IsTrue(eventType.IsProperty("myComplexBean.mapped('a')"));

            Assert.IsFalse(eventType.IsProperty("dummy"));
            Assert.IsFalse(eventType.IsProperty("mySupportBean.dfgdg"));
            Assert.IsFalse(eventType.IsProperty("xxx.intPrimitive"));
            Assert.IsFalse(eventType.IsProperty("myComplexBean.nested.nestedValueXXX"));
        }

        [Test]
        public virtual void testGetGetter()
        {
            SupportBean nestedSupportBean = new SupportBean();
            nestedSupportBean.intPrimitive = 100;
            SupportBeanComplexProps complexPropBean = SupportBeanComplexProps.makeDefaultBean();

            Assert.AreEqual(null, eventType.GetGetter("dummy"));

            EDataDictionary valuesMap = new EDataDictionary();
            valuesMap.Put("myInt", 20);
            valuesMap.Put("myString", "a");
            valuesMap.Put("mySupportBean", nestedSupportBean);
            valuesMap.Put("myComplexBean", complexPropBean);
            valuesMap.Put("myNullableSupportBean", null);
            valuesMap.Put("myNullableString", null);
            EventBean eventBean = new MapEventBean(valuesMap, eventType);

            EventPropertyGetter getter = eventType.GetGetter("myInt");
            Assert.AreEqual(20, getter.GetValue(eventBean));

            getter = eventType.GetGetter("myString");
            Assert.AreEqual("a", getter.GetValue(eventBean));

            getter = eventType.GetGetter("myNullableString");
            Assert.IsNull(getter.GetValue(eventBean));

            getter = eventType.GetGetter("mySupportBean");
            Assert.AreEqual(nestedSupportBean, getter.GetValue(eventBean));

            getter = eventType.GetGetter("mySupportBean.intPrimitive");
            Assert.AreEqual(100, getter.GetValue(eventBean));

            getter = eventType.GetGetter("myNullableSupportBean.intPrimitive");
            Assert.IsNull(getter.GetValue(eventBean));

            getter = eventType.GetGetter("myComplexBean.nested.nestedValue");
            Assert.AreEqual("nestedValue", getter.GetValue(eventBean));

            try
            {
                eventBean = SupportEventBeanFactory.createObject(new Object());
                Object temp = getter.GetValue(eventBean);
                Assert.Fail();
            }
            catch (PropertyAccessException ex)
            {
                // Expected
                log.Debug(".testGetGetter Expected exception, msg=" + ex.Message);
            }
        }

        [Test]
        public virtual void testGetSuperTypes()
        {
            Assert.IsNull(eventType.SuperTypes);
        }

        [Test]
        public virtual void testEquals()
        {
            EDictionary<String, Type> mapTwo = new LinkedDictionary<String, Type>();
            mapTwo.Put("myInt", typeof(int));
            mapTwo.Put("mySupportBean", typeof(SupportBean));
            mapTwo.Put("myNullableSupportBean", typeof(SupportBean));
            mapTwo.Put("myComplexBean", typeof(SupportBeanComplexProps));
            Assert.AreNotEqual(new MapEventType(mapTwo, eventAdapterService), eventType);
            mapTwo.Put("myString", typeof(String));
            mapTwo.Put("myNullableString", typeof(String));

            // compare, should equal
            Assert.AreEqual(new MapEventType(mapTwo, eventAdapterService), eventType);

            mapTwo.Put("xx", typeof(int));
            Assert.AreNotEqual(eventType, new MapEventType(mapTwo, eventAdapterService));
            mapTwo.Remove("xx");
            Assert.AreEqual(eventType, new MapEventType(mapTwo, eventAdapterService));

            mapTwo.Put("myInt", typeof(int?));
            Assert.AreNotEqual(eventType, new MapEventType(mapTwo, eventAdapterService));
            mapTwo.Remove("myInt");
            Assert.AreNotEqual(eventType, new MapEventType(mapTwo, eventAdapterService));
            mapTwo.Put("myInt", typeof(int));
            Assert.AreEqual(eventType,new MapEventType(mapTwo, eventAdapterService));
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TestMapEventType));
    }
}
