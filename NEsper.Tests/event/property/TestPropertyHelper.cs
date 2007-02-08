using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Reflection;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events.property
{
    [TestFixture]
    public class TestPropertyHelper 
    {
        [Test]
        public virtual void testAddMappedProperties()
        {
            IList<EventPropertyDescriptor> result = new List<EventPropertyDescriptor>();
            PropertyHelper.AddMappedProperties(typeof(SupportBeanPropertyNames), result);

            foreach (EventPropertyDescriptor desc in result)
            {
                log.Debug("desc=" + desc.PropertyName);
            }

            Assert.AreEqual(6, result.Count);
            Assert.AreEqual("a", result[0].PropertyName);
            Assert.AreEqual("AB", result[1].PropertyName);
            Assert.AreEqual("ABC", result[2].PropertyName);
            Assert.AreEqual("ab", result[3].PropertyName);
            Assert.AreEqual("abc", result[4].PropertyName);
            Assert.AreEqual("fooBah", result[5].PropertyName);
        }

        [Test]
        public virtual void testAddIntrospectProperties()
        {
            IList<EventPropertyDescriptor> result = new List<EventPropertyDescriptor>();
            PropertyHelper.AddSimpleProperties(typeof(SupportBeanPropertyNames), result);

            foreach (EventPropertyDescriptor desc in result)
            {
                log.Debug("desc=" + desc.PropertyName);
            }

            Assert.AreEqual(9, result.Count); // for "class" is also in there
            Assert.AreEqual("indexed", result[8].PropertyName);
            Assert.IsNotNull(result[8].Descriptor);
        }

        [Test]
        public virtual void testRemoveDuplicateProperties()
        {
            IList<EventPropertyDescriptor> result = new List<EventPropertyDescriptor>();
            result.Add(new EventPropertyDescriptor("x", "x", (PropertyDescriptor) null, EventPropertyType.SIMPLE));
            result.Add(new EventPropertyDescriptor("x", "x", (PropertyDescriptor) null, EventPropertyType.SIMPLE));
            result.Add(new EventPropertyDescriptor("y", "y", (PropertyDescriptor) null, EventPropertyType.SIMPLE));

            PropertyHelper.RemoveDuplicateProperties(result);

            Assert.AreEqual(2, result.Count);
            Assert.AreEqual("x", result[0].PropertyName);
            Assert.AreEqual("y", result[1].PropertyName);
        }

        [Test]
        public virtual void testRemoveClrProperties()
        {
            IList<EventPropertyDescriptor> result = new List<EventPropertyDescriptor>();
            result.Add(new EventPropertyDescriptor("x", "x", (PropertyDescriptor)null, EventPropertyType.SIMPLE));
            result.Add(new EventPropertyDescriptor("Type", "Type", (PropertyDescriptor)null, EventPropertyType.SIMPLE));
            result.Add(new EventPropertyDescriptor("HashCode", "HashCode", (PropertyDescriptor)null, EventPropertyType.SIMPLE));
            result.Add(new EventPropertyDescriptor("ToString", "ToString", (PropertyDescriptor)null, EventPropertyType.SIMPLE));
            result.Add(new EventPropertyDescriptor("GetType", "GetType", (PropertyDescriptor)null, EventPropertyType.SIMPLE));

            PropertyHelper.RemoveClrProperties(result);

            Assert.AreEqual(1, result.Count);
            Assert.AreEqual("x", result[0].PropertyName);
        }

        [Test]
        public virtual void testGetGetter()
        {
            EventBean bean = SupportEventBeanFactory.createObject(new SupportBeanPropertyNames());
            MethodInfo method = typeof(SupportBeanPropertyNames).GetMethod("getA") ;
            PropertyDescriptor descriptor = new SimpleAccessorPropertyDescriptor(method.Name, method);
            EventPropertyGetter getter = PropertyHelper.GetGetter(descriptor);
            Assert.AreEqual("", getter.GetValue(bean));
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TestPropertyHelper));
    }
}
