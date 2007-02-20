using System;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	
	[TestFixture]
	public class TestPerf3StreamAndPropertyJoin 
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
			// Statement where all streams are reachable from each other via properties
			String stmt = "select * from " + typeof(SupportBean_A).FullName + "().win:length(1000000) s1," + typeof(SupportBean_B).FullName + "().win:length(1000000) s2," + typeof(SupportBean_C).FullName + "().win:length(1000000) s3" + " where s1.id=s2.id and s2.id=s3.id and s1.id=s3.id";
			tryJoinPerf3Streams(stmt);
		}
		
		[Test]
		public virtual void  testPerfPartialProps()
		{
			// Statement where the s1 stream is not reachable by joining s2 to s3 and s3 to s1
			String stmt = "select * from " + typeof(SupportBean_A).FullName + ".win:length(1000000) s1," + typeof(SupportBean_B).FullName + ".win:length(1000000) s2," + typeof(SupportBean_C).FullName + ".win:length(1000000) s3" + " where s1.id=s2.id and s2.id=s3.id"; // ==> therefore s1.id = s3.id
			tryJoinPerf3Streams(stmt);
		}
		
		[Test]
		public virtual void  testPerfPartialStreams()
		{
			String methodName = ".testPerfPartialStreams";
			
			// Statement where the s1 stream is not reachable by joining s2 to s3 and s3 to s1
			String stmt = "select * from " + typeof(SupportBean_A).FullName + "().win:length(1000000) s1," + typeof(SupportBean_B).FullName + "().win:length(1000000) s2," + typeof(SupportBean_C).FullName + "().win:length(1000000) s3" + " where s1.id=s2.id"; // ==> stream s3 no properties supplied, full s3 scan
			
			joinView = epService.EPAdministrator.createEQL(stmt);
			joinView.AddListener(updateListener);
			
			// preload s3 with just 1 event
			SendEvent(new SupportBean_C("GE_0"));
			
			// Send events for each stream
			log.Info(methodName + " Preloading events");
            long startTime = DateTimeHelper.CurrentTimeMillis;
			for (int i = 0; i < 1000; i++)
			{
				SendEvent(new SupportBean_A("CSCO_" + i));
				SendEvent(new SupportBean_B("IBM_" + i));
			}
			log.Info(methodName + " Done preloading");

            long endTime = DateTimeHelper.CurrentTimeMillis;
			log.Info(methodName + " delta=" + (endTime - startTime));
			
			// Stay below 500, no index would be 4 sec plus
			Assert.IsTrue((endTime - startTime) < 500);
		}
		
		private void  tryJoinPerf3Streams(String joinStatement)
		{
			String methodName = ".tryJoinPerf3Streams";
			
			joinView = epService.EPAdministrator.createEQL(joinStatement);
			joinView.AddListener(updateListener);
			
			// Send events for each stream
			log.Info(methodName + " Preloading events");
            long startTime = DateTimeHelper.CurrentTimeMillis;
			for (int i = 0; i < 100; i++)
			{
				SendEvent(new SupportBean_A("CSCO_" + i));
				SendEvent(new SupportBean_B("IBM_" + i));
				SendEvent(new SupportBean_C("GE_" + i));
			}
			log.Info(methodName + " Done preloading");

            long endTime = DateTimeHelper.CurrentTimeMillis;
			log.Info(methodName + " delta=" + (endTime - startTime));
			
			// Stay below 500, no index would be 4 sec plus
			Assert.IsTrue((endTime - startTime) < 500);
		}
		
		private void  SendEvent(Object _event)
		{
			epService.EPRuntime.SendEvent(_event);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
