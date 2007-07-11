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
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestGroupByViewFactory
	{
	    private GroupByViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new GroupByViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {"price"}, new String[] {"price"});
	        TryParameter(new Object[] {"price", "volume"}, new String[] {"price", "volume"});
	        TryParameter(new Object[] {new String[] {"price", "volume"}}, new String[] {"price", "volume"});
	        TryParameter(new Object[] {new String[] {"price"}}, new String[] {"price"});

	        TryInvalidParameter(new Object[] {"a", 1.1d});
	        TryInvalidParameter(new Object[] {1.1d});
	        TryInvalidParameter(new Object[] {new String[] {}});
	        TryInvalidParameter(new Object[] {new String[] {}, new String[] {}});
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {"a", "b"});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new GroupByView(SupportStatementContextFactory.MakeContext(), new String[] {"a"})));
	        Assert.IsTrue(factory.CanReuse(new GroupByView(SupportStatementContextFactory.MakeContext(), new String[] {"a", "b"})));

	        factory.SetViewParameters(null, new Object[] {new String[] {"a", "b"}});
	        Assert.IsFalse(factory.CanReuse(new GroupByView(SupportStatementContextFactory.MakeContext(), new String[] {"a"})));
	        Assert.IsTrue(factory.CanReuse(new GroupByView(SupportStatementContextFactory.MakeContext(), new String[] {"a", "b"})));
	    }

	    [Test]
	    public void testAttaches()
	    {
	        // Should attach to anything as long as the fields exists
	        EventType parentType = SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean));

	        factory.SetViewParameters(null, new Object[] {"price"});
	        factory.Attach(parentType, null, null, null);

	        try
	        {
	            factory.SetViewParameters(null, new Object[] {"xxx"});
	            factory.Attach(parentType, null, null, null);
	            Assert.Fail();
	        }
	        catch (ViewAttachException ex)
	        {
	            // expected;
	        }
	    }

	    private void TryInvalidParameter(Object[] paramList)
	    {
	        try
	        {
	            GroupByViewFactory factory = new GroupByViewFactory();
	            factory.SetViewParameters(null, paramList);
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] paramList, String[] fieldNames)
	    {
	        GroupByViewFactory factory = new GroupByViewFactory();
	        factory.SetViewParameters(null, paramList);
	        GroupByView view = (GroupByView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        ArrayAssertionUtil.AreEqualExactOrder(fieldNames, view.GroupFieldNames);
	    }
	}
} // End of namespace
