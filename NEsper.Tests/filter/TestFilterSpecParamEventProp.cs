///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterSpecParamEventProp
	{
	    [Test]
	    public void testEquals()
	    {
	        FilterSpecParamEventProp[] _params = new FilterSpecParamEventProp[5];
	        _params[0] = MakeParam("a", "intBoxed");
	        _params[1] = MakeParam("b", "intBoxed");
	        _params[2] = MakeParam("a", "intPrimitive");
	        _params[3] = MakeParam("c", "intPrimitive");
	        _params[4] = MakeParam("a", "intBoxed");

	        Assert.AreEqual(_params[0], _params[4]);
	        Assert.AreEqual(_params[4], _params[0]);
	        Assert.IsFalse(_params[0].Equals(_params[1]));
	        Assert.IsFalse(_params[0].Equals(_params[2]));
	        Assert.IsFalse(_params[0].Equals(_params[3]));
	    }

	    [Test]
	    public void testGetFilterValue()
	    {
	        FilterSpecParamEventProp _params = MakeParam("asName", "intBoxed");

	        SupportBean _eventBean = new SupportBean();
	        _eventBean.SetIntBoxed(1000);
	        EventBean _event = SupportEventBeanFactory.CreateObject(_eventBean);

	        MatchedEventMap matchedEvents = new MatchedEventMapImpl();
	        matchedEvents.Add("asName", _event);

	        Assert.AreEqual(1000, _params.GetFilterValue(matchedEvents));

	        try
	        {
	            _params.GetFilterValue(new MatchedEventMapImpl());
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }

	        try
	        {
	            _params.GetFilterValue(null);
	            Assert.Fail();
	        }
	        catch (NullReferenceException ex)
	        {
	            // Expected
	        }
	    }

	    private FilterSpecParamEventProp MakeParam(String eventAsName, String property)
	    {
	        return new FilterSpecParamEventProp("intPrimitive", FilterOperator.EQUAL, eventAsName, property, false, typeof(int));
	    }
	}
} // End of namespace
