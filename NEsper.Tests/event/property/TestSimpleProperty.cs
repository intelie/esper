using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.property
{
	[TestFixture]
	public class TestSimpleProperty
	{
		private SimpleProperty prop;
		private SimpleProperty invalidPropMap;
		private SimpleProperty invalidPropIndexed;
		private SimpleProperty invalidDummy;
		private EventBean _event;
		private BeanEventType eventType;

		[SetUp]
		public virtual void  setUp()
		{
			prop = new SimpleProperty("simpleProperty");
			invalidPropMap = new SimpleProperty("mapped");
			invalidPropIndexed = new SimpleProperty("indexed");
			invalidDummy = new SimpleProperty("dummy");
			_event = SupportEventBeanFactory.CreateObject(SupportBeanComplexProps.MakeDefaultBean());
			eventType = (BeanEventType) _event.EventType;
		}

		[Test]
		public virtual void  testGetGetter()
		{
			EventPropertyGetter getter = prop.GetGetter(eventType);
            Assert.AreEqual("simple", getter.GetValue(_event));

			Assert.IsNull(invalidDummy.GetGetter(eventType));
			Assert.IsNull(invalidPropMap.GetGetter(eventType));
			Assert.IsNull(invalidPropIndexed.GetGetter(eventType));
		}

		[Test]
		public virtual void  testGetPropertyType()
		{
			Assert.AreEqual(typeof(String), prop.GetPropertyType(eventType));

			Assert.IsNull(invalidDummy.GetGetter(eventType));
			Assert.IsNull(invalidPropMap.GetGetter(eventType));
			Assert.IsNull(invalidPropIndexed.GetGetter(eventType));
		}

		private void  tryInvalidGetGetter(SimpleProperty property)
		{
			try
			{
				property.GetGetter(eventType);
				Assert.Fail();
			}
			catch (PropertyAccessException ex)
			{
				// expected
			}
		}

		private void  tryInvalidGetPropertyType(SimpleProperty property)
		{
			try
			{
				property.GetPropertyType(eventType);
				Assert.Fail();
			}
			catch (PropertyAccessException ex)
			{
				// expected
			}
		}
	}
}
