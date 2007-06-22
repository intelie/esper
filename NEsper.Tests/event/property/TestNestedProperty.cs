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

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.events.property
{
	[TestFixture]
	public class TestNestedProperty
	{
	    private NestedProperty[] nested;
	    private EventBean _event;
	    private BeanEventAdapter beanEventAdapter;

	    [SetUp]
	    public void SetUp()
	    {
	        beanEventAdapter = new BeanEventAdapter();

	        nested = new NestedProperty[2];
	        nested[0] = MakeProperty(new String[] {"nested", "nestedValue"});
	        nested[1] = MakeProperty(new String[] {"nested", "nestedNested", "nestedNestedValue"});

	        _event = SupportEventBeanFactory.CreateObject(SupportBeanComplexProps.MakeDefaultBean());
	    }

	    [Test]
	    public void TestGetGetter()
	    {
	        EventPropertyGetter getter = nested[0].GetGetter((BeanEventType)_event.EventType);
	        Assert.AreEqual("nestedValue", getter.GetValue(_event));

	        getter = nested[1].GetGetter((BeanEventType)_event.EventType);
	        Assert.AreEqual("nestedNestedValue", getter.GetValue(_event));
	    }

	    [Test]
	    public void TestGetPropertyType()
	    {
	        Assert.AreEqual(typeof(String), nested[0].GetPropertyType((BeanEventType)_event.EventType));
	        Assert.AreEqual(typeof(String), nested[1].GetPropertyType((BeanEventType)_event.EventType));
	    }

	    private NestedProperty MakeProperty(String[] propertyNames)
	    {
	        IList<Property> properties = new List<Property>();
	        foreach (String prop in propertyNames)
	        {
	            properties.Add(new SimpleProperty(prop));
	        }
	        return new NestedProperty(properties, beanEventAdapter);
	    }
	}
} // End of namespace
