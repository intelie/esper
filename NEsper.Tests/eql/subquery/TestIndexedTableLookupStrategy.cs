// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.eql.join.table;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.eql.subquery
{
	[TestFixture]
	public class TestIndexedTableLookupStrategy
	{
	    private IndexedTableLookupStrategy strategy;
	    private PropertyIndexedEventTable table;

	    [SetUp]
	    public void SetUp()
	    {
	        EventType beanType = SupportEventAdapterService.GetService().AddBeanType(typeof(SupportBean).FullName, typeof(SupportBean));
	        EventType[] eventTypes = new EventType[] {
	                beanType,
	                SupportEventAdapterService.GetService().AddBeanType(typeof(SupportMarketDataBean).FullName, typeof(SupportMarketDataBean))
	        };

	        table = new PropertyIndexedEventTable(2, beanType, new String[] {"intBoxed", "longBoxed", "intPrimitive"});
	        strategy = new IndexedTableLookupStrategy(eventTypes,
	                new int[] {0, 1, 0},
	                new String[] {"intPrimitive", "volume", "intBoxed"},
	                table);
	    }

	    [Test]
	    public void testFlow()
	    {
	        Assert.IsNull(strategy.Lookup(MakeLookupEventBeans(0, 0, 0)));

	        EventBean[] tableBean = MakeTableBean(20, 100, 5000);
	        table.Add(tableBean);
	        Assert.AreEqual(1, strategy.Lookup(MakeLookupEventBeans(20, 100, 5000)).Count);

	        table.Add(MakeTableBean(20, 100, 5000));
	        Assert.AreEqual(2, strategy.Lookup(MakeLookupEventBeans(20, 100, 5000)).Count);

	        table.Add(MakeTableBean(20, 100, 5001));
	        Assert.AreEqual(1, strategy.Lookup(MakeLookupEventBeans(20, 100, 5001)).Count);
	    }

	    public EventBean[] MakeLookupEventBeans(int intPrimitive, long volume, int intBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntBoxed(intBoxed);
	        bean.SetIntPrimitive(intPrimitive);
	        SupportMarketDataBean market = new SupportMarketDataBean("", 0, volume, "");
	        return new EventBean[] {
	                SupportEventAdapterService.GetService().AdapterForBean(bean),
	                SupportEventAdapterService.GetService().AdapterForBean(market)
	        };
	    }

	    private EventBean[] MakeTableBean(int intBoxed, long longBoxed, int intPrimitive)
	    {
	        SupportBean bean = MakeBean(intBoxed, longBoxed, intPrimitive);
	        return new EventBean[] {SupportEventAdapterService.GetService().AdapterForBean(bean)};
	    }

	    private SupportBean MakeBean(int intBoxed, long longBoxed, int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntBoxed(intBoxed);
	        bean.SetLongBoxed(longBoxed);
	        bean.SetIntPrimitive(intPrimitive);
	        return bean;
	    }
	}
} // End of namespace
