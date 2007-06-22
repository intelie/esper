///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestAggregateRowForAllHaving
	{
	    private readonly static String JOIN_KEY = "KEY";

	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;
	    private EPStatement selectTestView;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestSumOneView()
	    {
	        String viewExpr = "select Sum(longBoxed) as mySum " +
	                          "from " + typeof(SupportBean).FullName + ".win:time(10 seconds) " +
	                          "having Sum(longBoxed) > 10";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        RunAssert();
	    }

	    [Test]
	    public void TestSumJoin()
	    {
	        String viewExpr = "select Sum(longBoxed) as mySum " +
	                          "from " + typeof(SupportBeanString).FullName + ".win:time(10 seconds) as one, " +
	                                    typeof(SupportBean).FullName + ".win:time(10 seconds) as two " +
	                          "where one.string = two.string " +
	                          "having Sum(longBoxed) > 10";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        epService.EPRuntime.SendEvent(new SupportBeanString(JOIN_KEY));

	        RunAssert();
	    }

	    private void RunAssert()
	    {
	        // assert select result type
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("mySum"));

	        SendTimerEvent(0);
	        SendEvent(10);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendTimerEvent(5000);
	        SendEvent(15);
	        Assert.AreEqual(25L, testListener.GetAndResetLastNewData()[0]["mySum"]);

	        SendTimerEvent(8000);
	        SendEvent(-5);
	        Assert.AreEqual(20L, testListener.GetAndResetLastNewData()[0]["mySum"]);
	        Assert.IsNull(testListener.LastOldData);

	        SendTimerEvent(10000);
	        Assert.AreEqual(20L, testListener.LastOldData[0]["mySum"]);
	        Assert.IsNull(testListener.GetAndResetLastNewData());
	    }

	    private void SendEvent(long longBoxed, int intBoxed, short shortBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(JOIN_KEY);
	        bean.SetLongBoxed(longBoxed);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetShortBoxed(shortBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendEvent(long longBoxed)
	    {
	        SendEvent(longBoxed, 0, (short)0);
	    }

	    private void SendTimerEvent(long msec)
	    {
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(msec));
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
