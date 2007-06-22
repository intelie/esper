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
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.events
{
	[TestFixture]
	public class TestBeanEventAdapter
	{
	    private BeanEventAdapter beanEventAdapter;

	    [SetUp]
	    public void SetUp()
	    {
	        beanEventAdapter = new BeanEventAdapter();
	    }

	    [Test]
	    public void TestCreateBeanType()
	    {
	        EventTypeSPI eventType = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanSimple));

	        Assert.AreEqual(typeof(SupportBeanSimple), eventType.UnderlyingType);
	        Assert.AreEqual(2, eventType.PropertyNames.Count);
	        Assert.AreEqual("TYPE_net.esper.support.bean.SupportBeanSimple", eventType.EventTypeId);

	        // Second call to create the event type, should be the same instance as the first
	        EventType eventTypeTwo = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanSimple));
	        Assert.IsTrue(eventTypeTwo == eventType);
            Assert.AreEqual("TYPE_net.esper.support.bean.SupportBeanSimple", eventType.EventTypeId);

	        // Third call to create the event type, getting a given event type id
	        EventType eventTypeThree = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanSimple));
	        Assert.IsTrue(eventTypeThree == eventType);
	        Assert.AreEqual("TYPE_net.esper.support.bean.SupportBeanSimple", eventType.EventTypeId);
	    }

	    [Test]
	    public void TestInterfaceProperty()
	    {
	        // Assert implementations have full set of properties
	        ISupportDImpl _event = new ISupportDImpl("D", "BaseD", "BaseDBase");
	        EventBean bean = beanEventAdapter.AdapterForBean(_event, null);
	        Assert.AreEqual("D", bean["d"]);
	        Assert.AreEqual("BaseD", bean["baseD"]);
	        Assert.AreEqual("BaseDBase", bean["baseDBase"]);
	        Assert.AreEqual(3, bean.EventType.PropertyNames.Count);
	        ArrayAssertionUtil.AreEqualAnyOrder(bean.EventType.PropertyNames,
	                new String[] {"d", "baseD", "baseDBase"});

	        // Assert intermediate interfaces have full set of fields
	        EventType interfaceType = beanEventAdapter.CreateOrGetBeanType(typeof(ISupportD));
            ArrayAssertionUtil.AreEqualAnyOrder(interfaceType.PropertyNames,
	                new String[] {"d", "baseD", "baseDBase"});
	    }

	    [Test]
	    public void TestMappedIndexedNestedProperty()
	    {
	    	EventType eventType = beanEventAdapter.CreateOrGetBeanType(typeof(SupportBeanComplexProps));

	        Assert.AreEqual(typeof(IDataDictionary), eventType.GetPropertyType("mapProperty"));
	        Assert.AreEqual(typeof(String), eventType.GetPropertyType("mapped('x')"));
	        Assert.AreEqual(typeof(int), eventType.GetPropertyType("indexed[1]"));
	        Assert.AreEqual(typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNested), eventType.GetPropertyType("nested"));
	        Assert.AreEqual(typeof(int[]), eventType.GetPropertyType("arrayProperty"));
	    }
	}
} // End of namespace
