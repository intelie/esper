using System;
using System.Collections.Generic;

using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
    [TestFixture]
    public class TestBeanEventAdapter
    {
        private BeanEventAdapter beanEventAdapter;

        [SetUp]
        public virtual void setUp()
        {
            beanEventAdapter = new BeanEventAdapter(null);
        }

        [Test]
        public virtual void testCreateBeanType()
        {
            EventType eventType = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanSimple));

            Assert.AreEqual(typeof(SupportBeanSimple), eventType.UnderlyingType);
            Assert.AreEqual(2, eventType.PropertyNames.Count);

            // Second call to create the event type, should be the same instance as the first
            EventType eventTypeTwo = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanSimple));
            Assert.IsTrue(eventTypeTwo == eventType);
        }

        [Test]
        public virtual void testInterfaceProperty()
        {
            // Assert implementations have full set of properties
            ISupportDImpl _event = new ISupportDImpl("D", "BaseD", "BaseDBase");
            EventBean bean = beanEventAdapter.AdapterForBean(_event);
            Assert.AreEqual("D", bean["d"]);
            Assert.AreEqual("BaseD", bean["baseD"]);
            Assert.AreEqual("BaseDBase", bean["baseDBase"]);
            Assert.AreEqual(3, bean.EventType.PropertyNames.Count);
            ArrayAssertionUtil.assertEqualsAnyOrder(
                (ICollection<string>) bean.EventType.PropertyNames,
                (ICollection<string>) new String[] { "d", "baseD", "baseDBase" });

            // Assert intermediate interfaces have full set of fields
            EventType interfaceType = beanEventAdapter.CreateOrGetBeanType(typeof(ISupportD));
            ArrayAssertionUtil.assertEqualsAnyOrder(
                (ICollection<string>)interfaceType.PropertyNames,
                (ICollection<string>)new String[] { "d", "baseD", "baseDBase" });
        }

        [Test]
        public virtual void testMappedIndexedNestedProperty()
        {
            EventType eventType = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanComplexProps));

            Assert.AreEqual(typeof(System.Collections.IDictionary), eventType.GetPropertyType("mapProperty"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("mapped('x')"));
            Assert.AreEqual(typeof(int), eventType.GetPropertyType("indexed[1]"));
            Assert.AreEqual(typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNested), eventType.GetPropertyType("nested"));
            Assert.AreEqual(typeof(int[]), eventType.GetPropertyType("arrayProperty"));
        }
    }
}
