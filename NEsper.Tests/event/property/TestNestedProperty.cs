using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.property
{

	[TestFixture]
    public class TestNestedProperty 
    {
        private NestedProperty[] nested;
        private EventBean _event;
        private BeanEventAdapter beanEventAdapter;

        [SetUp]
        public virtual void setUp()
        {
            beanEventAdapter = new BeanEventAdapter(null);

            nested = new NestedProperty[2];
            nested[0] = makeProperty(new String[] { "nested", "nestedValue" });
            nested[1] = makeProperty(new String[] { "nested", "nestedNested", "nestedNestedValue" });

            _event = SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean());
        }

        [Test]
        public virtual void testGetGetter()
        {
            EventPropertyGetter getter = nested[0].GetGetter((BeanEventType)_event.EventType);
            Assert.AreEqual("nestedValue", getter.GetValue(_event));

            getter = nested[1].GetGetter((BeanEventType)_event.EventType);
            Assert.AreEqual("nestedNestedValue", getter.GetValue(_event));
        }

        [Test]
        public virtual void testGetPropertyType()
        {
            Assert.AreEqual(typeof(String), nested[0].GetPropertyType((BeanEventType)_event.EventType));
            Assert.AreEqual(typeof(String), nested[1].GetPropertyType((BeanEventType)_event.EventType));
        }

        private NestedProperty makeProperty(String[] propertyNames)
        {
            IList<Property> properties = new List<Property>();
            foreach (String prop in propertyNames)
            {
                properties.Add(new SimpleProperty(prop));
            }
            return new NestedProperty(properties, beanEventAdapter);
        }
    }
}
