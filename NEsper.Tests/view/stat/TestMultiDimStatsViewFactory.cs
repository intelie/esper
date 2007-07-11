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
using net.esper.view.stat.olap;
using net.esper.view.std;
using net.esper.view;

namespace net.esper.view.stat
{
	[TestFixture]
	public class TestMultiDimStatsViewFactory
	{
	    private MultiDimStatsViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new MultiDimStatsViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {new String[] {"stddev"}, "price", "volume"},
	                     new String[] {"stddev"}, "price", "volume", null, null);

	        TryParameter(new Object[] {new String[] {"stddev"}, "price", "volume", "a"},
	                     new String[] {"stddev"}, "price", "volume", "a", null);

	        TryParameter(new Object[] {new String[] {"stddev"}, "price", "volume", "a", "b"},
	                     new String[] {"stddev"}, "price", "volume", "a", "b");

	        TryInvalidParameter(new Object[] {new String[] {"stdev"}, "a"});
	        TryInvalidParameter(new Object[] {1.1d, "a"});
	        TryInvalidParameter(new Object[] {1.1d});
	        TryInvalidParameter(new Object[] {"a", "b", "c"});
	        TryInvalidParameter(new Object[] {new String[] {"a", "b"}});
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {new String[] {"stddev"}, "price", "volume"});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new MultiDimStatsView(SupportStatementContextFactory.MakeContext(),
	                new String[] {"stddev", "average"}, "price", "volume", null, null)));
	        Assert.IsTrue(factory.CanReuse(new MultiDimStatsView(SupportStatementContextFactory.MakeContext(),
	                new String[] {"stddev"}, "price", "volume", null, null)));

	        factory.SetViewParameters(null, new Object[] {new String[] {"stddev"}, "price", "volume", "a", "b"});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new MultiDimStatsView(SupportStatementContextFactory.MakeContext(),
	                new String[] {"stddev"}, "price", "volume", "x", "b")));
	        Assert.IsTrue(factory.CanReuse(new MultiDimStatsView(SupportStatementContextFactory.MakeContext(),
	                new String[] {"stddev"}, "price", "volume", "a", "b")));
	    }

	    [Test]
	    public void testAttaches()
	    {
	        // Should attach to anything as long as the fields exists
	        EventType parentType = SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean));

	        factory.SetViewParameters(null, new Object[] {new String[] {"stddev"}, "price", "volume"});
	        factory.Attach(parentType, SupportStatementContextFactory.MakeContext(), null, null);
            Assert.AreEqual(typeof(Cube), factory.EventType.GetPropertyType(ViewFieldEnum.MULTIDIM_OLAP__CUBE.Name));

	        try
	        {
	            factory.SetViewParameters(null, new Object[] {new String[] {"stddev"}, "xxx", "y"});
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

	    private void TryParameter(Object[] paramList, String[] derived, String measureField, String columnField, String rowField, String pageField)
	    {
	        factory.SetViewParameters(null, paramList);
	        MultiDimStatsView view = (MultiDimStatsView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(measureField, view.MeasureField);
	        Assert.AreEqual(pageField, view.PageField);
	        Assert.AreEqual(rowField, view.RowField);
	        Assert.AreEqual(columnField, view.ColumnField);
	        ArrayAssertionUtil.AreEqualExactOrder(view.DerivedMeasures, derived);
	    }
	}
} // End of namespace
