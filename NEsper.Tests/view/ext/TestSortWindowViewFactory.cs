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

using net.esper.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view.std;
using net.esper.view;

namespace net.esper.view.ext
{
	[TestFixture]
	public class TestSortWindowViewFactory
	{
	    private SortWindowViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new SortWindowViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {"price", true, 100},
	                     new String[] {"price"}, new Boolean[] {true}, 100);

	        TryParameter(new Object[] {new Object[] {"price", true, "volume", false}, 100},
	                     new String[] {"price", "volume"}, new Boolean[] {true, false}, 100);

	        TryInvalidParameter(new Object[] {new Object[] {"price", "a", "volume", false}, 100});
	        TryInvalidParameter(new Object[] {});
	        TryInvalidParameter(new Object[] {"price", "x", 100});
	        TryInvalidParameter(new Object[] {1.1, "x", 100});
	        TryInvalidParameter(new Object[] {"price", true, "x"});
	    }

	    [Test]
	    public void testAttaches()
	    {
	        // Should attach to anything as long as the fields exists
	        EventType parentType = SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean));

	        factory.SetViewParameters(null, new Object[] {"price", true, 100});
	        factory.Attach(parentType, SupportStatementContextFactory.MakeContext(), null, null);

	        try
	        {
	            factory.SetViewParameters(null, new Object[] {"xxx", true, 100});
	            factory.Attach(parentType, null, null, null);
	            Assert.Fail();
	        }
	        catch (ViewAttachException ex)
	        {
	            // expected;
	        }
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        StatementContext context = SupportStatementContextFactory.MakeContext();

	        factory.SetViewParameters(null, new Object[] {"price", true, 100});
	        Assert.IsFalse(factory.CanReuse(new SizeView(context)));
	        Assert.IsTrue(factory.CanReuse(new SortWindowView(factory, new String[] {"price"}, new Boolean[] {true}, 100, null)));
	        Assert.IsFalse(factory.CanReuse(new SortWindowView(factory, new String[] {"volume"}, new Boolean[] {true}, 100, null)));
	        Assert.IsFalse(factory.CanReuse(new SortWindowView(factory, new String[] {"price"}, new Boolean[] {false}, 100, null)));
	        Assert.IsFalse(factory.CanReuse(new SortWindowView(factory, new String[] {"price"}, new Boolean[] {true}, 99, null)));

	        factory.SetViewParameters(null, new Object[] {new Object[] {"price", true, "volume", false}, 100});
	        Assert.IsTrue(factory.CanReuse(new SortWindowView(factory, new String[] {"price", "volume"}, new Boolean[] {true, false}, 100, null)));
	        Assert.IsFalse(factory.CanReuse(new SortWindowView(factory, new String[] {"price", "xxx"}, new Boolean[] {true, false}, 100, null)));
	    }

	    private void TryInvalidParameter(Object[] paramList)
	    {
	        try
	        {
	            factory.SetViewParameters(null, paramList);
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] paramList, String[] fieldNames, Boolean[] ascInd, int size)
	    {
	        factory.SetViewParameters(null, paramList);
	        SortWindowView view = (SortWindowView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(size, view.SortWindowSize);
	        ArrayAssertionUtil.AreEqualExactOrder(fieldNames, view.SortFieldNames);
	        ArrayAssertionUtil.AreEqualExactOrder(ascInd, view.IsDescendingValues);
	    }
	}
} // End of namespace
