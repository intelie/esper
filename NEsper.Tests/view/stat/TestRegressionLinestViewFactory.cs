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
using net.esper.support.view;
using net.esper.view.std;
using net.esper.view;

namespace net.esper.view.stat
{
	[TestFixture]
	public class TestRegressionLinestViewFactory
	{
	    private RegressionLinestViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new RegressionLinestViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {"price", "volume"}, "price", "volume");

	        TryInvalidParameter(new Object[] {"a", 1.1d});
	        TryInvalidParameter(new Object[] {1.1d, "a"});
	        TryInvalidParameter(new Object[] {1.1d});
	        TryInvalidParameter(new Object[] {"a", "b", "c"});
	        TryInvalidParameter(new Object[] {new String[] {"a", "b"}});
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {"a", "b"});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new RegressionLinestView(SupportStatementContextFactory.MakeContext(), "a", "c")));
	        Assert.IsFalse(factory.CanReuse(new RegressionLinestView(SupportStatementContextFactory.MakeContext(), "x", "b")));
	        Assert.IsTrue(factory.CanReuse(new RegressionLinestView(SupportStatementContextFactory.MakeContext(), "a", "b")));
	    }

	    [Test]
	    public void testAttaches()
	    {
	        // Should attach to anything as long as the fields exists
	        EventType parentType = SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean));

	        factory.SetViewParameters(null, new Object[] {"price", "volume"});
	        factory.Attach(parentType, SupportStatementContextFactory.MakeContext(), null, null);
            Assert.AreEqual(typeof(double), factory.EventType.GetPropertyType(ViewFieldEnum.REGRESSION__SLOPE.Name));

	        try
	        {
	            factory.SetViewParameters(null, new Object[] {"xxx", "y"});
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
	            factory.SetViewParameters(null, paramList);
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] paramList, String fieldNameX, String fieldNameY)
	    {
	        factory.SetViewParameters(null, paramList);
	        RegressionLinestView view = (RegressionLinestView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(fieldNameX, view.FieldNameX);
	        Assert.AreEqual(fieldNameY, view.FieldNameY);
	    }
	}
} // End of namespace
