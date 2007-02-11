using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
	
	[TestFixture]
	public class TestIStreamRStreamKeywords 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;
		private SupportUpdateListener testListenerInsertInto;
		
		[SetUp]
		public virtual void  setUp()
		{
			testListener = new SupportUpdateListener();
			testListenerInsertInto = new SupportUpdateListener();
			
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testRStreamOnly()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("select rstream * from " + typeof(SupportBean).FullName + ".win:length(3)");
			statement.AddListener(testListener);
			
			Object _event = SendEvent("a");
			Assert.IsFalse(testListener.Invoked);
			
			sendEvents(new String[]{"a", "b"});
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent("d");
			Assert.AreSame(_event, testListener.LastNewData[0].Underlying); // receive 'a' as new data
			Assert.IsNull(testListener.LastOldData); // receive no more old data
			testListener.reset();
		}
		
		[Test]
		public virtual void  testRStreamInsertInto()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("insert into NextStream " + "select rstream s0.string as string from " + typeof(SupportBean).FullName + ".win:length(3) as s0");
			statement.AddListener(testListener);
			
			statement = epService.EPAdministrator.createEQL("select * from NextStream");
			statement.AddListener(testListenerInsertInto);
			
			SendEvent("a");
			Assert.IsFalse(testListener.Invoked);
			Assert.AreEqual("a", testListenerInsertInto.assertOneGetNewAndReset()["str"]); // insert into unchanged
			
			sendEvents(new String[]{"b", "c"});
			Assert.IsFalse(testListener.Invoked);
			Assert.AreEqual(2, testListenerInsertInto.NewDataList.Count); // insert into unchanged
			testListenerInsertInto.reset();
			
			SendEvent("d");
			Assert.AreSame("a", testListener.LastNewData[0]["str"]); // receive 'a' as new data
			Assert.IsNull(testListener.LastOldData); // receive no more old data
			Assert.AreEqual("d", testListenerInsertInto.LastNewData[0]["str"]); // insert into unchanged
			Assert.IsNull(testListenerInsertInto.LastOldData); // receive no old data in insert into
			testListener.reset();
		}
		
		[Test]
		public virtual void  testRStreamInsertIntoRStream()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("insert rstream into NextStream " + "select rstream s0.string as string from " + typeof(SupportBean).FullName + ".win:length(3) as s0");
			statement.AddListener(testListener);
			
			statement = epService.EPAdministrator.createEQL("select * from NextStream");
			statement.AddListener(testListenerInsertInto);
			
			SendEvent("a");
			Assert.IsFalse(testListener.Invoked);
			Assert.IsFalse(testListenerInsertInto.Invoked);
			
			sendEvents(new String[]{"b", "c"});
			Assert.IsFalse(testListener.Invoked);
			Assert.IsFalse(testListenerInsertInto.Invoked);
			
			SendEvent("d");
			Assert.AreSame("a", testListener.LastNewData[0]["str"]); // receive 'a' as new data
			Assert.IsNull(testListener.LastOldData); // receive no more old data
			Assert.AreEqual("a", testListenerInsertInto.LastNewData[0]["str"]); // insert into unchanged
			Assert.IsNull(testListener.LastOldData); // receive no old data in insert into
			testListener.reset();
		}
		
		[Test]
		public virtual void  testRStreamJoin()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("select rstream s1.intPrimitive as aID, s2.intPrimitive as bID " + "from " + typeof(SupportBean).FullName + "(string='a').win:length(2) as s1, " + typeof(SupportBean).FullName + "(string='b') as s2" + " where s1.intPrimitive = s2.intPrimitive");
			statement.AddListener(testListener);
			
			SendEvent("a", 1);
			SendEvent("b", 1);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent("a", 2);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent("a", 3);
			Assert.AreEqual(1, testListener.LastNewData[0]["aID"]); // receive 'a' as new data
			Assert.AreEqual(1, testListener.LastNewData[0]["bID"]);
			Assert.IsNull(testListener.LastOldData); // receive no more old data
			testListener.reset();
		}
		
		[Test]
		public virtual void  testIStreamOnly()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("select istream * from " + typeof(SupportBean).FullName + ".win:length(1)");
			statement.AddListener(testListener);
			
			Object _event = SendEvent("a");
			Assert.AreSame(_event, testListener.assertOneGetNewAndReset().Underlying);
			
			_event = SendEvent("b");
			Assert.AreSame(_event, testListener.LastNewData[0].Underlying);
			Assert.IsNull(testListener.LastOldData); // receive no old data, just istream events
			testListener.reset();
		}
		
		[Test]
		public virtual void  testIStreamInsertIntoRStream()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("insert rstream into NextStream " + "select istream a.string as string from " + typeof(SupportBean).FullName + ".win:length(1) as a");
			statement.AddListener(testListener);
			
			statement = epService.EPAdministrator.createEQL("select * from NextStream");
			statement.AddListener(testListenerInsertInto);
			
			SendEvent("a");
			Assert.AreEqual("a", testListener.assertOneGetNewAndReset()["str"]);
			Assert.IsFalse(testListenerInsertInto.Invoked);
			
			SendEvent("b");
			Assert.AreEqual("b", testListener.LastNewData[0]["str"]);
			Assert.IsNull(testListener.LastOldData);
			Assert.AreEqual("a", testListenerInsertInto.LastNewData[0]["str"]);
			Assert.IsNull(testListenerInsertInto.LastOldData);
		}
		
		[Test]
		public virtual void  testIStreamJoin()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("select istream s1.intPrimitive as aID, s2.intPrimitive as bID " + "from " + typeof(SupportBean).FullName + "(string='a').win:length(2) as s1, " + typeof(SupportBean).FullName + "(string='b') as s2" + " where s1.intPrimitive = s2.intPrimitive");
			statement.AddListener(testListener);
			
			SendEvent("a", 1);
			SendEvent("b", 1);
			Assert.AreEqual(1, testListener.LastNewData[0]["aID"]); // receive 'a' as new data
			Assert.AreEqual(1, testListener.LastNewData[0]["bID"]);
			Assert.IsNull(testListener.LastOldData); // receive no more old data
			testListener.reset();
			
			SendEvent("a", 2);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent("a", 3);
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  sendEvents(String[] stringValue)
		{
			for (int i = 0; i < stringValue.Length; i++)
			{
				SendEvent(stringValue[i]);
			}
		}
		
		private Object SendEvent(String stringValue)
		{
			return SendEvent(stringValue, - 1);
		}
		
		private Object SendEvent(String stringValue, int intPrimitive)
		{
			SupportBean _event = new SupportBean();
			_event.str = stringValue;
			_event.intPrimitive = intPrimitive;
			epService.EPRuntime.SendEvent(_event);
			return _event;
		}
	}
}
