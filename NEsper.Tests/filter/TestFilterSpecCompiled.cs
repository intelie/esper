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

using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterSpecCompiled
	{
	    private EventType eventType;
	    private String eventTypeId;
	    private String eventTypeAlias;

	    [SetUp]
	    public void SetUp()
	    {
	        eventTypeAlias = typeof(SupportBean).FullName;
	        eventType = SupportEventAdapterService.GetService().AddBeanType(eventTypeAlias, typeof(SupportBean));
	        eventTypeId = SupportEventAdapterService.GetService().GetIdByAlias(eventTypeAlias);
	    }

	    [Test]
	    public void TestHashCode()
	    {
	        FilterSpecCompiled spec = SupportFilterSpecBuilder.Build(eventType, new Object[] { "intPrimitive", FilterOperator.EQUAL, 2,
	                                                                 "intBoxed", FilterOperator.EQUAL, 3 });

	        int expectedHash = eventType.GetHashCode() ^ "intPrimitive".GetHashCode() ^ "intBoxed".GetHashCode();
	        Assert.AreEqual(expectedHash, spec.GetHashCode());
	    }

	    [Test]
	    public void TestEquals()
	    {
	        Object[][] paramList = new Object[][] {
	            new object[]{ "intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3 },
	            new object[]{ "intPrimitive", FilterOperator.EQUAL, 3, "intBoxed", FilterOperator.EQUAL, 3 },
	            new object[]{ "intPrimitive", FilterOperator.EQUAL, 2 },
	            new object[]{ "intPrimitive", FilterOperator.RANGE_CLOSED, 1, 10 },
	            new object[]{ "intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3 },
	            new object[]{ },
	            new object[]{ },
	            };

	        List<FilterSpecCompiled> specVec = new List<FilterSpecCompiled>();
	        foreach (Object[] param in paramList)
	        {
	            FilterSpecCompiled spec = SupportFilterSpecBuilder.Build(eventType, param);
	            specVec.Add(spec);
	        }

	        Assert.IsFalse(specVec[0].Equals(specVec[1]));
	        Assert.IsFalse(specVec[0].Equals(specVec[2]));
	        Assert.IsFalse(specVec[0].Equals(specVec[3]));
	        Assert.AreEqual(specVec[0], specVec[4]);
	        Assert.IsFalse(specVec[0].Equals(specVec[5]));
	        Assert.AreEqual(specVec[5], specVec[6]);

	        Assert.IsFalse(specVec[2].Equals(specVec[4]));
	    }

	    [Test]
	    public void TestGetValueSet()
	    {
	        List<FilterSpecParam> _params = SupportFilterSpecBuilder.BuildList(new Object[]
	                                    { "intPrimitive", FilterOperator.EQUAL, 2 });
	        _params.Add(new FilterSpecParamEventProp("doubleBoxed", FilterOperator.EQUAL, "asName", "doublePrimitive", false, typeof(double?)));
	        FilterSpecCompiled filterSpec = new FilterSpecCompiled(eventType, _params);

	        SupportBean _eventBean = new SupportBean();
	        _eventBean.SetDoublePrimitive(999.999);
	        EventBean _event = SupportEventBeanFactory.CreateObject(_eventBean);
	        MatchedEventMap matchedEvents = new MatchedEventMapImpl();
	        matchedEvents.Add("asName", _event);
	        FilterValueSet valueSet = filterSpec.GetValueSet(matchedEvents);

	        // Assert the generated filter value container
	        Assert.AreSame(eventType, valueSet.EventType);
	        Assert.AreEqual(2, valueSet.Parameters.Count);

	        // Assert the first param
	        FilterValueSetParam param = valueSet.Parameters[0];
	        Assert.AreEqual("intPrimitive", param.PropertyName);
	        Assert.AreEqual(FilterOperator.EQUAL, param.FilterOperator);
	        Assert.AreEqual(2, param.FilterForValue);

	        // Assert the second param
	        param = valueSet.Parameters[1];
	        Assert.AreEqual("doubleBoxed", param.PropertyName);
	        Assert.AreEqual(FilterOperator.EQUAL, param.FilterOperator);
	        Assert.AreEqual(999.999, param.FilterForValue);
	    }
	}
} // End of namespace
