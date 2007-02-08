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
	public class TestArrayFieldPropertyGetter 
	{
		private ArrayPropertyGetter getter;
		private ArrayPropertyGetter getterOutOfBounds;
		private EventBean _event;
		private SupportLegacyBean bean;
		
		[SetUp]
		public virtual void  setUp()
		{
			bean = new SupportLegacyBean(new String[]{"a", "b"});
			_event = SupportEventBeanFactory.createObject(bean);
			
			getter = makeGetter(0);
			getterOutOfBounds = makeGetter(Int32.MaxValue);
		}
		
		[Test]
		public virtual void  testCtor()
		{
			try
			{
				makeGetter(- 1);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
		
		[Test]
		public virtual void  testGet()
		{
			Assert.AreEqual(bean.fieldStringArray[0], getter.GetValue(_event));
			
			Assert.IsNull(getterOutOfBounds.GetValue(_event));
			
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
		
		private ArrayPropertyGetter makeGetter(int index)
		{
			FieldInfo field = typeof(SupportLegacyBean).GetField("fieldStringArray", BindingFlags.Instance | BindingFlags.Public | BindingFlags.Static);
            SimpleFieldPropertyDescriptor descriptor = new SimpleFieldPropertyDescriptor(field.Name, field);
			return new ArrayPropertyGetter(descriptor, index);
		}
	}
}
