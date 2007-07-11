///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestEventTypeIndexBuilder
	{
	    private EventTypeIndex eventTypeIndex;
	    private EventTypeIndexBuilder indexBuilder;

	    private EventType typeOne;
	    private EventType typeTwo;

	    private FilterValueSet valueSetOne;
	    private FilterValueSet valueSetTwo;

	    private FilterHandle callbackOne;
	    private FilterHandle callbackTwo;

	    [SetUp]
	    public void SetUp()
	    {
	        eventTypeIndex = new EventTypeIndex();
	        indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);

	        typeOne = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        typeTwo = SupportEventTypeFactory.CreateBeanType(typeof(SupportBeanSimple));

	        valueSetOne = SupportFilterSpecBuilder.Build(typeOne, new Object[0]).GetValueSet(null);
	        valueSetTwo = SupportFilterSpecBuilder.Build(typeTwo, new Object[0]).GetValueSet(null);

	        callbackOne = new SupportFilterHandle();
	        callbackTwo = new SupportFilterHandle();
	    }

	    [Test]
	    public void testAddRemove()
	    {
	        Assert.IsNull(eventTypeIndex[typeOne]);
	        Assert.IsNull(eventTypeIndex[typeTwo]);

	        indexBuilder.Add(valueSetOne, callbackOne);
	        indexBuilder.Add(valueSetTwo, callbackTwo);

	        Assert.IsTrue(eventTypeIndex[typeOne] != null);
	        Assert.IsTrue(eventTypeIndex[typeTwo] != null);

	        try
	        {
	            indexBuilder.Add(valueSetOne, callbackOne);
	            Assert.IsTrue(false);
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected exception
	        }

	        indexBuilder.Remove(callbackOne);
	        indexBuilder.Add(valueSetOne, callbackOne);
	        indexBuilder.Remove(callbackOne);

	        // Try invalid remove
	        try
	        {
	            indexBuilder.Remove(callbackOne);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected Exception
	        }
	    }
	}
} // End of namespace
