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

using net.esper.eql.parse;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view.std;
using net.esper.view;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestExternallyTimedWindowViewFactory
	{
	    private ExternallyTimedWindowViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new ExternallyTimedWindowViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {"a", new TimePeriodParameter(2d)}, "a", 2000);
	        TryParameter(new Object[] {"a", 10L}, "a", 10000);
	        TryParameter(new Object[] {"a", 11}, "a", 11000);
	        TryParameter(new Object[] {"a", 2.2}, "a", 2200);

	        TryInvalidParameter(new Object[] {new TimePeriodParameter(2d)});
	        TryInvalidParameter(new Object[] {"a"});
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {"price", 1000});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new ExternallyTimedWindowView(factory, "volume", 1000, null)));
	        Assert.IsFalse(factory.CanReuse(new ExternallyTimedWindowView(factory, "price", 999, null)));
	        Assert.IsTrue(factory.CanReuse(new ExternallyTimedWindowView(factory, "price", 1000000, null)));
	    }

	    [Test]
	    public void testAttach()
	    {
	        EventType parentType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));

	        factory.SetViewParameters(null, new Object[] {"dummy", 20});
	        try
	        {
	            factory.Attach(parentType, null, null, null);
	            Assert.Fail();
	        }
	        catch (ViewAttachException ex)
	        {
	            // expected
	        }

	        factory.SetViewParameters(null, new Object[] {"string", 20});
	        try
	        {
	            factory.Attach(parentType, null, null, null);
	            Assert.Fail();
	        }
	        catch (ViewAttachException ex)
	        {
	            // expected
	        }

	        factory.SetViewParameters(null, new Object[] {"longPrimitive", 20});
	        factory.Attach(parentType, null, null, null);

	        Assert.AreSame(parentType, factory.EventType);
	    }

	    private void TryInvalidParameter(Object[] param)
	    {
	        try
	        {
	            ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
	            factory.SetViewParameters(null, new Object[] {param});
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] paramList, String fieldName, long msec)
	    {
	        ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
	        factory.SetViewParameters(null, paramList);
	        ExternallyTimedWindowView view = (ExternallyTimedWindowView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(fieldName, view.TimestampFieldName);
	        Assert.AreEqual(msec, view.MillisecondsBeforeExpiry);
	    }
	}
} // End of namespace
