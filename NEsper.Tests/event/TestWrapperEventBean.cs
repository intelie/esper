// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.events
{
	[TestFixture]
	public class TestWrapperEventBean
	{
		private EventBean _eventBeanSimple;
		private EventBean _eventBeanCombined;
		private EDictionary<String, Object> properties;
		private EventType eventTypeSimple;
		private EventType eventTypeCombined;
		private EventAdapterService eventService;

        [SetUp]
		protected void SetUp()
		{
			eventService = SupportEventAdapterService.GetService();
			EventType underlyingEventTypeSimple = eventService.AddBeanType("underlyingSimpleBean", typeof(SupportBeanSimple));
			EventType underlyingEventTypeCombined = eventService.AddBeanType("underlyingCombinedBean", typeof(SupportBeanCombinedProps));

			EDictionary<String, Type> typeMap = new HashDictionary<String, Type>();
			typeMap.Put("string", typeof(string));
			typeMap.Put("int", typeof(int?));

			eventTypeSimple = new WrapperEventType("mytype", underlyingEventTypeSimple, typeMap, eventService);
			eventTypeCombined = new WrapperEventType("mytype", underlyingEventTypeCombined, typeMap, eventService);
			properties = new HashDictionary<String, Object>();
			properties.Put("string", "xx");
			properties.Put("int", 11);

	        EventBean wrappedSimple = eventService.AdapterForBean(new SupportBeanSimple("eventString", 0));
	        _eventBeanSimple = eventService.CreateWrapper(wrappedSimple, properties, eventTypeSimple);

	        EventBean wrappedCombined = eventService.AdapterForBean(SupportBeanCombinedProps.MakeDefaultBean());
	        _eventBeanCombined = eventService.CreateWrapper(wrappedCombined, properties, eventTypeCombined);
		}

		[Test]
		public void testGetSimple()
		{
            Assert.AreEqual("eventString", _eventBeanSimple["myString"]);
            Assert.AreEqual(0, _eventBeanSimple["myInt"]);
			AssertMap(_eventBeanSimple);
		}

		[Test]
		public void testGetCombined()
		{
	        Assert.AreEqual("0ma0", _eventBeanCombined["indexed[0].mapped('0ma').value"]);
	        Assert.AreEqual("0ma1", _eventBeanCombined["indexed[0].mapped('0mb').value"]);
	        Assert.AreEqual("1ma0", _eventBeanCombined["indexed[1].mapped('1ma').value"]);
	        Assert.AreEqual("1ma1", _eventBeanCombined["indexed[1].mapped('1mb').value"]);

	        Assert.AreEqual("0ma0", _eventBeanCombined["array[0].mapped('0ma').value"]);
	        Assert.AreEqual("1ma1", _eventBeanCombined["array[1].mapped('1mb').value"]);

			AssertMap(_eventBeanCombined);
		}

		private void AssertMap(EventBean _eventBean)
		{
			Assert.AreEqual("xx", _eventBean["string"]);
            Assert.AreEqual(11, _eventBean["int"]);
		}
	}
} // End of namespace
