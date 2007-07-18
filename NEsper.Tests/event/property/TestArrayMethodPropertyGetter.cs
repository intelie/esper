using System;
using System.ComponentModel;
using System.Reflection;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Framework;

namespace net.esper.events.property
{
	[TestFixture]
	public class TestArrayMethodPropertyGetter
	{
		private ArrayPropertyGetter getter;
		private ArrayPropertyGetter getterOutOfBounds;
		private EventBean _event;
		private SupportBeanComplexProps bean;

		[SetUp]
		public virtual void setUp()
		{
			bean = SupportBeanComplexProps.MakeDefaultBean();
			_event = SupportEventBeanFactory.CreateObject(bean);
			getter = makeGetter(0);
			getterOutOfBounds = makeGetter(Int32.MaxValue);
		}

		[Test]
		public void testCtor()
		{
			try
			{
				makeGetter(-1);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}

		[Test]
		public void testGet()
		{
            Assert.AreEqual(bean.ArrayProperty[0], getter.GetValue(_event));

			Assert.IsNull(getterOutOfBounds.GetValue(_event));

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

        private ArrayPropertyGetter makeGetter(int index)
		{
            Type type = typeof(SupportBeanComplexProps);
            PropertyDescriptorCollection properties = TypeDescriptor.GetProperties(type);
            PropertyDescriptor property = properties["ArrayProperty"];
			return new ArrayPropertyGetter(property, index);
		}
	}
}
