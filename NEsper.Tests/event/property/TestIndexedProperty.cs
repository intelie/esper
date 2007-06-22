using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.property
{

	[TestFixture]
	public class TestIndexedProperty
	{
		private IndexedProperty[] indexed;
		private EventBean _event;
		private BeanEventType eventType;

		[SetUp]
		public virtual void  setUp()
		{
			indexed = new IndexedProperty[4];
			indexed[0] = new IndexedProperty("indexed", 0);
			indexed[1] = new IndexedProperty("indexed", 1);
			indexed[2] = new IndexedProperty("arrayProperty", 0);
			indexed[3] = new IndexedProperty("arrayProperty", 1);

			_event = SupportEventBeanFactory.CreateObject(SupportBeanComplexProps.MakeDefaultBean());
			eventType = (BeanEventType) _event.EventType;
		}

		[Test]
		public virtual void  testGetGetter()
		{
			int[] expected = new int[]{1, 2, 10, 20};
			for (int i = 0; i < indexed.Length; i++)
			{
				EventPropertyGetter getter = indexed[i].GetGetter(eventType);
                Assert.AreEqual(expected[i], getter.GetValue(_event));
			}

			// try invalid case
			IndexedProperty ind = new IndexedProperty("dummy", 0);
			Assert.IsNull(ind.GetGetter(eventType));
		}

		[Test]
		public virtual void  testGetPropertyType()
		{
			Type[] expected = new Type[]{typeof(int), typeof(int), typeof(int), typeof(int)};
			for (int i = 0; i < indexed.Length; i++)
			{
				Assert.AreEqual(expected[i], indexed[i].GetPropertyType(eventType));
			}

			// try invalid case
			IndexedProperty ind = new IndexedProperty("dummy", 0);
			Assert.IsNull(ind.GetPropertyType(eventType));
		}
	}
}
