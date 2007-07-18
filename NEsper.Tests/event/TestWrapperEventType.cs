// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;

namespace net.esper.events
{
	[TestFixture]
	public class TestWrapperEventType
	{
		private EventType underlyingEventTypeOne;
		private EventType underlyingEventTypeTwo;
		private EventType eventType;
		private EDictionary<String, Type> properties;
		private EventAdapterService eventAdapterService;

        [SetUp]
		protected void SetUp()
		{
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;
	        underlyingEventTypeOne = new BeanEventType(typeof(SupportBeanSimple), new BeanEventAdapter(PropertyResolutionStyle.CASE_INSENSITIVE), null, "abc");
            underlyingEventTypeTwo = new BeanEventType(typeof(SupportBean_A), new BeanEventAdapter(PropertyResolutionStyle.CASE_INSENSITIVE), null, "abc");
	        properties = new HashDictionary<String, Type>();
	        properties.Put("additionalString", typeof(string));
	        properties.Put("additionalInt", typeof(int?));
	        eventAdapterService = SupportEventAdapterService.GetService();
	        eventType = new WrapperEventType("mytype", underlyingEventTypeOne, properties, eventAdapterService);
		}

		[Test]
		public void testInvalidRepeatedNames()
		{
			properties.Clear();
			properties.Put("MyString", typeof(string));

			try
			{
				// The myString property occurs in both the event and the map
				eventType = new WrapperEventType("mytype", underlyingEventTypeOne, properties, eventAdapterService);
				Assert.Fail();
			}
			catch(EPException ex)
			{
				// Expected
			}
		}

		[Test]
		public void testPropertyNames()
		{
			String[] expected = new String[] { "MyInt", "MyString", "additionalInt", "additionalString" };
			ArrayAssertionUtil.AreEqualAnyOrder(expected, eventType.PropertyNames);
		}

		[Test]
		public void testGetPropertyType()
		{
			Assert.AreEqual(typeof(int), eventType.GetPropertyType("myInt"));
            Assert.AreEqual(typeof(int?), eventType.GetPropertyType("additionalInt"));
            Assert.AreEqual(typeof(string), eventType.GetPropertyType("additionalString"));
            Assert.AreEqual(typeof(string), eventType.GetPropertyType("myString"));
			Assert.IsNull(eventType.GetPropertyType("unknownProperty"));
		}

		[Test]
		public void testIsProperty()
		{
			Assert.IsTrue(eventType.IsProperty("myInt"));
			Assert.IsTrue(eventType.IsProperty("additionalInt"));
			Assert.IsTrue(eventType.IsProperty("additionalString"));
			Assert.IsTrue(eventType.IsProperty("myString"));
			Assert.IsFalse(eventType.IsProperty("unknownProperty"));
		}

		[Test]
		public void testEquals()
		{
		    EDictionary<String, Type> otherProperties = new HashDictionary<String, Type>();
            otherProperties.PutAll(properties);

			EventType otherType = new WrapperEventType("mytype", underlyingEventTypeOne, otherProperties, eventAdapterService);
			Assert.IsTrue(eventType.Equals(otherType));
			Assert.IsTrue(otherType.Equals(eventType));

			otherType = new WrapperEventType("mytype", underlyingEventTypeTwo, otherProperties, eventAdapterService);
			Assert.IsFalse(eventType.Equals(otherType));
			Assert.IsFalse(otherType.Equals(eventType));

			otherProperties.Put("anotherProperty", typeof(int?));
			otherType = new WrapperEventType("mytype", underlyingEventTypeOne, otherProperties, eventAdapterService);
			Assert.IsFalse(eventType.Equals(otherType));
			Assert.IsFalse(otherType.Equals(eventType));

			otherType = underlyingEventTypeOne;
			Assert.IsFalse(eventType.Equals(otherType));
			Assert.IsFalse(otherType.Equals(eventType));
		}
	}
} // End of namespace
