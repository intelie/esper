///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.support.bean;
using net.esper.support.events;

using org.apache.commons.logging;

namespace net.esper.events
{
	[TestFixture]
	public class TestBeanEventBean
	{
	    SupportBean testEvent;

	    [SetUp]
	    public void SetUp()
	    {
	        testEvent = new SupportBean();
	        testEvent.SetIntPrimitive(10);
	    }

	    [Test]
	    public void TestGet()
	    {
	        EventType eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        BeanEventBean _eventBean = new BeanEventBean(testEvent, eventType, 0);

	        Assert.AreEqual(eventType, _eventBean.EventType);
	        Assert.AreEqual(testEvent, _eventBean.Underlying);

	        Assert.AreEqual(10, _eventBean["intPrimitive"]);

	        // Test wrong property name
	        try
	        {
                Object theVoid = _eventBean["dummy"];
	            Assert.IsTrue(false);
	        }
	        catch (PropertyAccessException ex)
	        {
	            // Expected
	            log.Debug(".testGetter Expected exception, msg=" + ex.Message);
	        }

	        // Test wrong event type - not possible to happen under normal use
	        try
	        {
	            eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBeanSimple));
	            _eventBean = new BeanEventBean(testEvent, eventType, 1);
                Object theVoid = _eventBean["myString"];
	            Assert.IsTrue(false);
	        }
	        catch (PropertyAccessException ex)
	        {
	            // Expected
	            log.Debug(".testGetter Expected exception, msg=" + ex.Message);
	        }
	    }

	    [Test]
	    public void TestGetComplexProperty()
	    {
	        SupportBeanCombinedProps _event = SupportBeanCombinedProps.MakeDefaultBean();
	        EventBean _eventBean = SupportEventBeanFactory.CreateObject(_event);

	        Assert.AreEqual("0ma0", _eventBean["indexed[0].Mapped('0ma').value"]);
	        Assert.AreEqual("0ma1", _eventBean["indexed[0].Mapped('0mb').value"]);
	        Assert.AreEqual("1ma0", _eventBean["indexed[1].Mapped('1ma').value"]);
	        Assert.AreEqual("1ma1", _eventBean["indexed[1].Mapped('1mb').value"]);

	        Assert.AreEqual("0ma0", _eventBean["array[0].Mapped('0ma').value"]);
	        Assert.AreEqual("1ma1", _eventBean["array[1].Mapped('1mb').value"]);

	        TryInvalid(_eventBean, "array[0].Mapprop('0ma').value");
	        TryInvalid(_eventBean, "dummy");
	        TryInvalid(_eventBean, "dummy[1]");
	        TryInvalid(_eventBean, "dummy('dd')");
	        TryInvalid(_eventBean, "dummy.dummy1");
	    }

	    private static void TryInvalid(EventBean _eventBean, String propName)
	    {
	        try
	        {
	            Object theVoid = _eventBean[propName];
	            Assert.Fail();
	        }
	        catch (PropertyAccessException)
	        {
	            // expected
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
