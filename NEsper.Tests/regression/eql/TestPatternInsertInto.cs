using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	
	[TestFixture]
	public class TestPatternInsertInto 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener updateListener;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testPropsWildcard()
		{
			String stmtText = "insert into MyThirdStream(es0id, es1id) " + "select es0.id, es1.id " + "from " + "pattern [every (es0=" + typeof(SupportBean_S0).FullName + " or es1=" + typeof(SupportBean_S1).FullName + ")]";
			epService.EPAdministrator.CreateEQL(stmtText);
			
			String stmtTwoText = "select * from MyThirdStream";
			EPStatement statement = epService.EPAdministrator.CreateEQL(stmtTwoText);
			
			updateListener = new SupportUpdateListener();
            statement.AddListener(updateListener.Update);
			
			sendEventsAndAssert();
		}
		
		[Test]
		public virtual void  testProps()
		{
			String stmtText = "insert into MySecondStream(s0, s1) " + "select es0, es1 " + "from " + "pattern [every (es0=" + typeof(SupportBean_S0).FullName + " or es1=" + typeof(SupportBean_S1).FullName + ")]";
			epService.EPAdministrator.CreateEQL(stmtText);
			
			String stmtTwoText = "select s0.id as es0id, s1.id as es1id from MySecondStream";
			EPStatement statement = epService.EPAdministrator.CreateEQL(stmtTwoText);
			
			updateListener = new SupportUpdateListener();
            statement.AddListener(updateListener.Update);
			
			sendEventsAndAssert();
		}
		
		[Test]
		public virtual void  testNoProps()
		{
			String stmtText = "insert into MyStream " + "select es0, es1 " + "from " + "pattern [every (es0=" + typeof(SupportBean_S0).FullName + " or es1=" + typeof(SupportBean_S1).FullName + ")]";
			epService.EPAdministrator.CreateEQL(stmtText);
			
			String stmtTwoText = "select es0.id as es0id, es1.id as es1id from MyStream.win:length(10)";
			EPStatement statement = epService.EPAdministrator.CreateEQL(stmtTwoText);
			
			updateListener = new SupportUpdateListener();
            statement.AddListener(updateListener.Update);
			
			sendEventsAndAssert();
		}
		
		private void  sendEventsAndAssert()
		{
			sendEventS1(10, "");
			EventBean _event = updateListener.assertOneGetNewAndReset();
			Assert.IsNull(_event["es0id"]);
			Assert.AreEqual(10, _event["es1id"]);
			
			sendEventS0(20, "");
			_event = updateListener.assertOneGetNewAndReset();
			Assert.AreEqual(20, _event["es0id"]);
			Assert.IsNull(_event["es1id"]);
		}
		
		private SupportBean_S0 sendEventS0(int id, String p00)
		{
			SupportBean_S0 _event = new SupportBean_S0(id, p00);
			epService.EPRuntime.SendEvent(_event);
			return _event;
		}
		
		private SupportBean_S1 sendEventS1(int id, String p10)
		{
			SupportBean_S1 _event = new SupportBean_S1(id, p10);
			epService.EPRuntime.SendEvent(_event);
			return _event;
		}
	}
}
