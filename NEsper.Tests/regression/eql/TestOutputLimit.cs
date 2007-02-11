using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestOutputLimit 
	{
		private EPServiceProvider epService;
		private long currentTime;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testLimitTime()
		{
			String eventName = typeof(SupportBean).FullName;
			String selectStatement = "select * from " + eventName + ".win:length(5)";
			
			// test integer seconds
			String statementString1 = selectStatement + " output every 3 seconds";
			timeCallback(statementString1, 3000);
			
			// test fractional seconds
			String statementString2 = selectStatement + " output every 3.3 seconds";
			timeCallback(statementString2, 3300);
			
			// test integer minutes
			String statementString3 = selectStatement + " output every 2 minutes";
			timeCallback(statementString3, 120000);
			
			// test fractional minutes
			String statementString4 = "select * from " + eventName + ".win:length(5)" + " output every .05 minutes";
			timeCallback(statementString4, 3000);
		}
		
		[Test]
		public virtual void  testWithGroupBy()
		{
			String eventName = typeof(SupportMarketDataBean).FullName;
			String statementString =
				"select symbol, sum(price) from " + eventName + ".win:length(5) group by symbol output every 5 events";
			EPStatement statement = epService.EPAdministrator.createEQL(statementString);
			SupportUpdateListener updateListener = new SupportUpdateListener();
			statement.AddListener(updateListener);
			
			// send some events and check that only the most recent  
			// ones are kept
			sendMarketEvent("IBM", 1D);
			sendMarketEvent("IBM", 2D);
			sendMarketEvent("HP", 1D);
			sendMarketEvent("IBM", 3D);
			sendMarketEvent("MAC", 1D);
			
			Assert.IsTrue(updateListener.getAndClearIsInvoked());
			EventBean[] newData = updateListener.LastNewData;
			Assert.AreEqual(3, newData.Length);
			assertSingleInstance(newData, "IBM");
			assertSingleInstance(newData, "HP");
			assertSingleInstance(newData, "MAC");
			EventBean[] oldData = updateListener.LastOldData;
			assertSingleInstance(oldData, "IBM");
			assertSingleInstance(oldData, "HP");
			assertSingleInstance(oldData, "MAC");
		}
		
		[Test]
		public virtual void  testLimitEventJoin()
		{
			String eventName1 = typeof(SupportBean).FullName;
			String eventName2 = typeof(SupportBean_A).FullName;
			String joinStatement =
				"select * from " + 
				eventName1 + ".win:length(5) as event1," + 
				eventName2 + ".win:length(5) as event2" + 
				" where event1.string = event2.id";
			String outputStmt1 = joinStatement + " output every 1 events";
			String outputStmt3 = joinStatement + " output every 3 events";
			
			EPStatement fireEvery1 = epService.EPAdministrator.createEQL(outputStmt1);
			EPStatement fireEvery3 = epService.EPAdministrator.createEQL(outputStmt3);
			
			SupportUpdateListener updateListener1 = new SupportUpdateListener();
			fireEvery1.AddListener(updateListener1);
			SupportUpdateListener updateListener3 = new SupportUpdateListener();
			fireEvery3.AddListener(updateListener3);
			
			// send event 1
			sendJoinEvents("s1");
			
			Assert.IsTrue(updateListener1.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener1.LastNewData.Length);
			Assert.IsNull(updateListener1.LastOldData);
			
			Assert.IsFalse(updateListener3.getAndClearIsInvoked());
			Assert.IsNull(updateListener3.LastNewData);
			Assert.IsNull(updateListener3.LastOldData);
			
			// send event 2
			sendJoinEvents("s2");
			
			Assert.IsTrue(updateListener1.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener1.LastNewData.Length);
			Assert.IsNull(updateListener1.LastOldData);
			
			Assert.IsFalse(updateListener3.getAndClearIsInvoked());
			Assert.IsNull(updateListener3.LastNewData);
			Assert.IsNull(updateListener3.LastOldData);
			
			// send event 3
			sendJoinEvents("s3");
			
			Assert.IsTrue(updateListener1.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener1.LastNewData.Length);
			Assert.IsNull(updateListener1.LastOldData);
			
			Assert.IsTrue(updateListener3.getAndClearIsInvoked());
			Assert.AreEqual(3, updateListener3.LastNewData.Length);
			Assert.IsNull(updateListener3.LastOldData);
		}
		
		[Test]
		public virtual void  testLimitEventSimple()
		{
			SupportUpdateListener updateListener1 = new SupportUpdateListener();
			SupportUpdateListener updateListener2 = new SupportUpdateListener();
			SupportUpdateListener updateListener3 = new SupportUpdateListener();
			
			String eventName = typeof(SupportBean).FullName;
			String selectStmt = "select * from " + eventName + ".win:length(5)";
			String statement1 = selectStmt + " output every 1 events";
			String statement2 = selectStmt + " output every 2 events";
			String statement3 = selectStmt + " output every 3 events";
			
			EPStatement rateLimitStmt1 = epService.EPAdministrator.createEQL(statement1);
			rateLimitStmt1.AddListener(updateListener1);
			EPStatement rateLimitStmt2 = epService.EPAdministrator.createEQL(statement2);
			rateLimitStmt2.AddListener(updateListener2);
			EPStatement rateLimitStmt3 = epService.EPAdministrator.createEQL(statement3);
			rateLimitStmt3.AddListener(updateListener3);
			
			// send event 1
			SendEvent("s1");
			
			Assert.IsTrue(updateListener1.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener1.LastNewData.Length);
			Assert.IsNull(updateListener1.LastOldData);
			
			Assert.IsFalse(updateListener2.getAndClearIsInvoked());
			Assert.IsNull(updateListener2.LastNewData);
			Assert.IsNull(updateListener2.LastOldData);
			
			Assert.IsFalse(updateListener3.getAndClearIsInvoked());
			Assert.IsNull(updateListener3.LastNewData);
			Assert.IsNull(updateListener3.LastOldData);
			
			// send event 2
			SendEvent("s2");
			
			Assert.IsTrue(updateListener1.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener1.LastNewData.Length);
			Assert.IsNull(updateListener1.LastOldData);
			
			Assert.IsTrue(updateListener2.getAndClearIsInvoked());
			Assert.AreEqual(2, updateListener2.LastNewData.Length);
			Assert.IsNull(updateListener2.LastOldData);
			
			Assert.IsFalse(updateListener3.getAndClearIsInvoked());
			
			// send event 3
			SendEvent("s3");
			
			Assert.IsTrue(updateListener1.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener1.LastNewData.Length);
			Assert.IsNull(updateListener1.LastOldData);
			
			Assert.IsFalse(updateListener2.getAndClearIsInvoked());
			
			Assert.IsTrue(updateListener3.getAndClearIsInvoked());
			Assert.AreEqual(3, updateListener3.LastNewData.Length);
			Assert.IsNull(updateListener3.LastOldData);
		}
		
		private void  assertSingleInstance(EventBean[] data, String symbol)
		{
			int instanceCount = 0;
			foreach(EventBean _event in data)
			{
				if (_event["symbol"].Equals(symbol))
				{
					instanceCount++;
				}
			}
			Assert.AreEqual(1, instanceCount);
		}
		
		private void  sendTimeEvent(int timeIncrement)
		{
			currentTime += timeIncrement;
			CurrentTimeEvent _event = new CurrentTimeEvent(currentTime);
			epService.EPRuntime.SendEvent(_event);
		}
		
		private void  timeCallback(String statementString, int timeToCallback)
		{
			// clear any old events
			epService.Initialize();
			
			// turn off external clocking
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
			
			// set the clock to 0
			currentTime = 0;
			sendTimeEvent(0);
			
			// create the eql statement and add a listener
			EPStatement statement = epService.EPAdministrator.createEQL(statementString);
			SupportUpdateListener updateListener = new SupportUpdateListener();
			statement.AddListener(updateListener);
			updateListener.reset();
			
			// send an event
			SendEvent("s1");
			
			// check that the listener hasn't been updated
			Assert.IsFalse(updateListener.getAndClearIsInvoked());
			
			// update the clock
			sendTimeEvent(timeToCallback);
			
			// check that the listener has been updated
			Assert.IsTrue(updateListener.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener.LastNewData.Length);
			Assert.IsNull(updateListener.LastOldData);
			
			// send another event
			SendEvent("s2");
			
			// check that the listener hasn't been updated
			Assert.IsFalse(updateListener.getAndClearIsInvoked());
			
			// update the clock
			sendTimeEvent(timeToCallback);
			
			// check that the listener has been updated
			Assert.IsTrue(updateListener.getAndClearIsInvoked());
			Assert.AreEqual(1, updateListener.LastNewData.Length);
			Assert.IsNull(updateListener.LastOldData);
			
			// don't send an event
			// check that the listener hasn't been updated
			Assert.IsFalse(updateListener.getAndClearIsInvoked());
			
			// update the clock
			sendTimeEvent(timeToCallback);
			
			// check that the listener has been updated
			Assert.IsTrue(updateListener.getAndClearIsInvoked());
			Assert.IsNull(updateListener.LastNewData);
			Assert.IsNull(updateListener.LastOldData);
			
			// don't send an event
			// check that the listener hasn't been updated
			Assert.IsFalse(updateListener.getAndClearIsInvoked());
			
			// update the clock
			sendTimeEvent(timeToCallback);
			
			// check that the listener has been updated
			Assert.IsTrue(updateListener.getAndClearIsInvoked());
			Assert.IsNull(updateListener.LastNewData);
			Assert.IsNull(updateListener.LastOldData);
			
			// send several events
			SendEvent("s3");
			SendEvent("s4");
			SendEvent("s5");
			
			// check that the listener hasn't been updated
			Assert.IsFalse(updateListener.getAndClearIsInvoked());
			
			// update the clock
			sendTimeEvent(timeToCallback);
			
			// check that the listener has been updated
			Assert.IsTrue(updateListener.getAndClearIsInvoked());
			Assert.AreEqual(3, updateListener.LastNewData.Length);
			Assert.IsNull(updateListener.LastOldData);
		}
		
		private void  SendEvent(String s)
		{
			SupportBean bean = new SupportBean();
			bean.str = s;
			bean.doubleBoxed = 0.0;
			bean.intPrimitive = 0;
			bean.intBoxed = 0;
			epService.EPRuntime.SendEvent(bean);
		}
		
		private void  sendJoinEvents(String s)
		{
			SupportBean event1 = new SupportBean();
			event1.str = s;
			event1.doubleBoxed = 0.0;
			event1.intPrimitive = 0;
			event1.intBoxed = 0;
			
			
			SupportBean_A event2 = new SupportBean_A(s);
			
			epService.EPRuntime.SendEvent(event1);
			epService.EPRuntime.SendEvent(event2);
		}
		
		private void  sendMarketEvent(String symbol, double price)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
			epService.EPRuntime.SendEvent(bean);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
