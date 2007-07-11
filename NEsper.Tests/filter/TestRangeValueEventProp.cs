///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.filter
{
	[TestFixture]
	public class TestRangeValueEventProp
	{
	    private FilterSpecParamRangeValue[] paramList = new FilterSpecParamRangeValue[5];

	    [SetUp]
	    public void SetUp()
	    {
	        paramList[0] = new RangeValueEventProp("a", "b");
	        paramList[1] = new RangeValueEventProp("asName", "b");
	        paramList[2] = new RangeValueEventProp("asName", "boolPrimitive");
	        paramList[3] = new RangeValueEventProp("asName", "intPrimitive");
	        paramList[4] = new RangeValueEventProp("asName", "intPrimitive");
	    }

	    [Test]
	    public void testGetFilterValue()
	    {
	        SupportBean _eventBean = new SupportBean();
	        _eventBean.SetIntPrimitive(1000);
	        EventBean _event = SupportEventBeanFactory.CreateObject(_eventBean);
	        MatchedEventMap matchedEvents = new MatchedEventMapImpl();
	        matchedEvents.Add("asName", _event);

	        TryInvalidGetFilterValue(matchedEvents, paramList[0]);
	        TryInvalidGetFilterValue(matchedEvents, paramList[1]);
	        Assert.AreEqual(1000.0, paramList[3].GetFilterValue(matchedEvents));
	    }

	    [Test]
	    public void testEquals()
	    {
	        Assert.IsFalse(paramList[0].Equals(paramList[1]));
	        Assert.IsFalse(paramList[2].Equals(paramList[3]));
	        Assert.IsTrue(paramList[3].Equals(paramList[4]));
	    }

	    private void TryInvalidGetFilterValue(MatchedEventMap matchedEvents, FilterSpecParamRangeValue value)
	    {
	        try
	        {
	            value.GetFilterValue(matchedEvents);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }
	        catch (PropertyAccessException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
