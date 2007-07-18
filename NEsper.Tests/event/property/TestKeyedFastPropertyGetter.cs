using System;
using System.Reflection;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.property
{
	[TestFixture]
	public class TestKeyedFastPropertyGetter
	{
		private KeyedPropertyGetter getter;
		private EventBean _event;
		private SupportBeanComplexProps bean;

		[SetUp]
		public virtual void setUp()
		{
			bean = SupportBeanComplexProps.MakeDefaultBean();
			_event = SupportEventBeanFactory.CreateObject(bean);

            Type type = typeof(SupportBeanComplexProps);
            MethodInfo methodOne = type.GetMethod("GetIndexed", new Type[] { typeof(int) });
            IndexedPropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor("indexed", methodOne);
            getter = new KeyedPropertyGetter(descriptor, 1);
		}

		[Test]
		public void testGet()
		{
            Assert.AreEqual(bean.GetIndexed(1), getter.GetValue(_event));

			try
			{
                getter.GetValue(SupportEventBeanFactory.CreateObject(""));
				Assert.Fail();
			}
			catch (PropertyAccessException ex)
			{
				// expected
			}
		}
	}
}
