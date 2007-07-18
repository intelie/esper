// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerfTimeWindowMinFilter
	{
	    private EPServiceProvider epService;
	    private EPStatement joinView;

	    [SetUp]
	    public void SetUp()
	    {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

            Configuration config = new Configuration();
	        config.AddEventTypeAlias("MD", typeof(SupportMarketDataIDBean));
	        epService = EPServiceProviderManager.GetDefaultProvider(config);
	        epService.Initialize();
	    }

	    public void TearDown()
	    {
	        epService.Initialize();
	    }

	    [Test]
	    public void testPerf()
	    {
	        EPStatement[] statements = new EPStatement[100];
	        SupportUpdateListener[] listeners = new SupportUpdateListener[statements.Length];
	        for (int i = 0; i < statements.Length; i++)
	        {
	            int secondsWindowSpan = i % 30 + 1;
	            double percent = 0.25 + i;
	            int id = i % 5;

	            String text = "select symbol, min(price) " +
	                    "from MD(id='${id}').win:time(${secondsWindowSpan})\n" +
	                    "having price >= min(price) * ${percent}";

	            text = text.Replace("${id}", Convert.ToString(id));
	            text = text.Replace("${secondsWindowSpan}", Convert.ToString(secondsWindowSpan));
	            text = text.Replace("${percent}", Convert.ToString(percent));

	            statements[i] = epService.EPAdministrator.CreateEQL(text);
	            listeners[i] = new SupportUpdateListener();
                statements[i].AddListener(listeners[i]);
	        }

	        long start = DateTimeHelper.CurrentTimeMillis;
	        int count = 0;
	        for (int i = 0; i < 10000; i++)
	        {
	            count++;
	            /*
	            if (i % 10000 == 0)
	            {
	                long now = DateTimeHelper.CurrentTimeMillis;
	                double deltaSec = (now - start) / 1000.0;
	                double throughput = 10000.0 / deltaSec;
	                Console.WriteLine("total=" + i + " deltaSec=" + deltaSec + " per sec:" + throughput);
	                for (int j = 0; j < listeners.Length; j++)
	                {
	                    listeners[j].Reset();
	                }
	                start = now;
	            }
	            */
	            SupportMarketDataIDBean bean = new SupportMarketDataIDBean("IBM", Convert.ToString(i % 5), 1);
	            epService.EPRuntime.SendEvent(bean);
	        }
	        long end = DateTimeHelper.CurrentTimeMillis;
	        long delta = end - start;
	        Assert.IsTrue(delta < 2000,"Delta=" + delta);
	        //Console.WriteLine("total=" + count + " delta=" + delta + " per sec:" + 10000.0 / (delta / 1000.0));
	    }
	}
} // End of namespace
