using System;
using System.Collections.Generic;
using System.Reflection;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.property
{
	[TestFixture]
    public class TestNestedPropertyGetter 
    {
        private NestedPropertyGetter getter;
        private NestedPropertyGetter getterNull;
        private EventBean _event;
        private SupportBeanCombinedProps bean;
        private BeanEventAdapter beanEventAdapter;

        [SetUp]
        public virtual void setUp()
        {
            beanEventAdapter = new BeanEventAdapter(null);
            bean = SupportBeanCombinedProps.makeDefaultBean();
            _event = SupportEventBeanFactory.createObject(bean);

            IList<EventPropertyGetter> getters = new List<EventPropertyGetter>();
            getters.Add(makeGetterOne(0));
            getters.Add(makeGetterTwo("0ma"));
            getter = new NestedPropertyGetter(getters, beanEventAdapter);

            getters = new List<EventPropertyGetter>();
            getters.Add(makeGetterOne(2));
            getters.Add(makeGetterTwo("0ma"));
            getterNull = new NestedPropertyGetter(getters, beanEventAdapter);
        }

        [Test]
        public virtual void testGet()
        {
            Assert.AreEqual(bean.getIndexed(0).getMapped("0ma"), getter.GetValue(_event));

            // test null value returned
            Assert.IsNull(getterNull.GetValue(_event));

            try
            {
                getter.GetValue(SupportEventBeanFactory.createObject(""));
                Assert.Fail();
            }
            catch (PropertyAccessException ex)
            {
                // expected
            }
        }

        private KeyedPropertyGetter makeGetterOne(int index)
        {
            Type type = typeof(SupportBeanCombinedProps);
            MethodInfo methodOne = type.GetMethod("getIndexed", new Type[] { typeof(int) });
            IndexedPropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor("indexed", methodOne);
            return new KeyedPropertyGetter(descriptor, index);
        }

        private KeyedPropertyGetter makeGetterTwo(String key)
        {
            Type type = typeof(SupportBeanCombinedProps.NestedLevOne);
            MethodInfo methodTwo = type.GetMethod("getMapped", new Type[] { typeof(string) });
            IndexedPropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor("mapped", methodTwo);
            return new KeyedPropertyGetter(descriptor, key);
        }
    }
}
