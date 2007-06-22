using System;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerf5StreamJoin 
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();
		}
		
		[Test]
		public virtual void  testPerfAllProps()
		{
			String statement = 
                "select * from " + 
                typeof(SupportBean_S0).FullName + ".win:length(100000) as s0," +
                typeof(SupportBean_S1).FullName + ".win:length(100000) as s1," + 
                typeof(SupportBean_S2).FullName + ".win:length(100000) as s2," +
                typeof(SupportBean_S3).FullName + ".win:length(100000) as s3," + 
                typeof(SupportBean_S4).FullName + ".win:length(100000) as s4" + 
                " where s0.p00 = s1.p10 " +
                "and s1.p10 = s2.p20 " + 
                "and s2.p20 = s3.p30 " + 
                "and s3.p30 = s4.p40 ";
			
			joinView = epService.EPAdministrator.CreateEQL(statement);
			joinView.AddListener(updateListener);
			
			log.Info(".testPerfAllProps Preloading events");
            long startTime = DateTimeHelper.CurrentTimeMillis ;
			for (int i = 0; i < 1000; i++)
			{
				sendEvents(new int[]{0, 0, 0, 0, 0}, new String[]{"s0" + i, "s1" + i, "s2" + i, "s3" + i, "s4" + i});
			}

            long endTime = DateTimeHelper.CurrentTimeMillis;
			log.Info(".testPerfAllProps delta=" + (endTime - startTime));
			Assert.IsTrue((endTime - startTime) < 1500);
			
			// test if join returns data
			Assert.IsNull(updateListener.LastNewData);
			String[] propertyValues = new String[]{"x", "x", "x", "x", "x"};
			int[] ids = new int[]{1, 2, 3, 4, 5};
			sendEvents(ids, propertyValues);
			assertEventsReceived(ids);
		}
		
		private void  assertEventsReceived(int[] expectedIds)
		{
			Assert.AreEqual(1, updateListener.LastNewData.Length);
			Assert.IsNull(updateListener.LastOldData);
			EventBean _event = updateListener.LastNewData[0];
			Assert.AreEqual(expectedIds[0], ((SupportBean_S0) _event["s0"]).Id);
			Assert.AreEqual(expectedIds[1], ((SupportBean_S1) _event["s1"]).Id);
			Assert.AreEqual(expectedIds[2], ((SupportBean_S2) _event["s2"]).Id);
			Assert.AreEqual(expectedIds[3], ((SupportBean_S3) _event["s3"]).Id);
			Assert.AreEqual(expectedIds[4], ((SupportBean_S4) _event["s4"]).Id);
		}
		
		private void  SendEvent(Object _event)
		{
			epService.EPRuntime.SendEvent(_event);
		}
		
		private void  sendEvents(int[] ids, String[] propertyValues)
		{
			SendEvent(new SupportBean_S0(ids[0], propertyValues[0]));
			SendEvent(new SupportBean_S1(ids[1], propertyValues[1]));
			SendEvent(new SupportBean_S2(ids[2], propertyValues[2]));
			SendEvent(new SupportBean_S3(ids[3], propertyValues[3]));
			SendEvent(new SupportBean_S4(ids[4], propertyValues[4]));
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
