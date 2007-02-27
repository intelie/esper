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
	public class TestPerfPropertyAccess 
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
		public virtual void  testPerfPropertyAccess()
		{
			String methodName = ".testPerfPropertyAccess";
			
			String joinStatement =
                "select * from " +
                typeof(SupportBeanCombinedProps).FullName + ".win:length(1)" +
                " where indexed[0].mapped('a').value = 'dummy'";
			
			joinView = epService.EPAdministrator.CreateEQL(joinStatement);
			joinView.AddListener(updateListener.Update);
			
			// Send events for each stream
			SupportBeanCombinedProps _event = SupportBeanCombinedProps.makeDefaultBean();
			log.Info(methodName + " Sending events");

            long startTime = DateTimeHelper.CurrentTimeMillis;
			for (int i = 0; i < 10000; i++)
			{
				SendEvent(_event);
			}
			log.Info(methodName + " Done sending events");

            long endTime = DateTimeHelper.CurrentTimeMillis;
			log.Info(methodName + " delta=" + (endTime - startTime));
			
			// Stays at 250, below 500ms
			Assert.IsTrue((endTime - startTime) < 1000);
		}
		
		private void  SendEvent(Object _event)
		{
			epService.EPRuntime.SendEvent(_event);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
