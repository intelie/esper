using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.property
{

	[TestFixture]
	public class TestMappedProperty
	{
		private MappedProperty[] mapped;
		private EventBean _event;
		private BeanEventType eventType;

		[SetUp]
		public virtual void  setUp()
		{
			mapped = new MappedProperty[2];
			mapped[0] = new MappedProperty("mapped", "keyOne");
			mapped[1] = new MappedProperty("mapped", "keyTwo");

			_event = SupportEventBeanFactory.CreateObject(SupportBeanComplexProps.MakeDefaultBean());
			eventType = (BeanEventType) _event.EventType;
		}

		[Test]
		public virtual void  testGetGetter()
		{
			Object[] expected = new String[]{"valueOne", "valueTwo"};
			for (int i = 0; i < mapped.Length; i++)
			{
				EventPropertyGetter getter = mapped[i].GetGetter(eventType);
                Assert.AreEqual(expected[i], getter.GetValue(_event));
			}

			// try invalid case
			MappedProperty mpd = new MappedProperty("dummy", "dummy");
			Assert.IsNull(mpd.GetGetter(eventType));
		}

		[Test]
		public virtual void  testGetPropertyType()
		{
			Type[] expected = new Type[]{typeof(String), typeof(String)};
			for (int i = 0; i < mapped.Length; i++)
			{
				Assert.AreEqual(expected[i], mapped[i].GetPropertyType(eventType));
			}

			// try invalid case
			MappedProperty mpd = new MappedProperty("dummy", "dummy");
			Assert.IsNull(mpd.GetPropertyType(eventType));
			mpd = new MappedProperty("mapProperty", "dummy");
			Assert.IsNull(mpd.GetPropertyType(eventType));
		}
	}
}
