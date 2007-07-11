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
using net.esper.support.events;
using net.esper.support.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestAddPropertyValueView
	{
	    private AddPropertyValueView myView;
	    private SupportMapView parentView;
	    private SupportSchemaNeutralView childView;
	    private EventType parentEventType;

	    [SetUp]
	    public void SetUp()
	    {
	        EDictionary<String, Type> schema = new HashDictionary<String, Type>();
	        schema.Put("STDDEV", typeof(double?));
	        parentEventType = SupportEventTypeFactory.CreateMapType(schema);

	        EventType mergeEventType = SupportEventAdapterService.GetService().CreateAddToEventType(parentEventType,
	                new String[] {"symbol"}, new Type[] {typeof(String)});

	        // Set up length window view and a test child view
	        myView = new AddPropertyValueView(SupportStatementContextFactory.MakeContext(), new String[] {"symbol"}, new Object[] {"IBM"}, mergeEventType);

	        parentView = new SupportMapView(schema);
	        parentView.AddView(myView);

	        childView = new SupportSchemaNeutralView();
	        myView.AddView(childView);
	    }

	    [Test]
	    public void testViewUpdate()
	    {
            DataDictionary eventData = new DataDictionary();

	        // Generate some events
	        eventData.Put("STDDEV", 100);
	        EventBean eventBeanOne = SupportEventBeanFactory.CreateMapFromValues(eventData, parentEventType);
	        eventData.Put("STDDEV", 0);
	        EventBean eventBeanTwo = SupportEventBeanFactory.CreateMapFromValues(eventData, parentEventType);
	        eventData.Put("STDDEV", 99999);
	        EventBean eventBeanThree = SupportEventBeanFactory.CreateMapFromValues(eventData, parentEventType);

	        // Send events
	        parentView.Update(new EventBean[] { eventBeanOne, eventBeanTwo},
	                          new EventBean[] { eventBeanThree });

	        // Checks
	        EventBean[] newData = childView.LastNewData;
	        Assert.AreEqual(2, newData.Length);
	        Assert.AreEqual("IBM", newData[0]["symbol"]);
	        Assert.AreEqual(100, newData[0]["STDDEV"]);
	        Assert.AreEqual("IBM", newData[1]["symbol"]);
	        Assert.AreEqual(0, newData[1]["STDDEV"]);

	        EventBean[] oldData = childView.LastOldData;
	        Assert.AreEqual(1, oldData.Length);
	        Assert.AreEqual("IBM", oldData[0]["symbol"]);
	        Assert.AreEqual(99999, oldData[0]["STDDEV"]);
	    }

	    [Test]
	    public void testCopyView()
	    {
	        AddPropertyValueView copied = (AddPropertyValueView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(myView.PropertyNames, copied.PropertyNames);
	        Assert.AreEqual(myView.PropertyValues, copied.PropertyValues);
	    }

	    [Test]
	    public void testAddProperty()
	    {
	        DataDictionary eventData = new DataDictionary() ;
	        eventData.Put("STDDEV", 100);
	        EventBean _eventBean = SupportEventBeanFactory.CreateMapFromValues(eventData, parentEventType);

            EventType newEventType = SupportEventAdapterService.GetService().CreateAddToEventType(parentEventType, new String[] { "test" }, new Type[] { typeof(int?) });
	        EventBean newBean = AddPropertyValueView.AddProperty(_eventBean, new String[] {"test"}, new Object[] {2}, newEventType, SupportEventAdapterService.GetService());

	        Assert.AreEqual(2, newBean["test"]);
	        Assert.AreEqual(100, newBean["STDDEV"]);
	    }
	}
} // End of namespace
