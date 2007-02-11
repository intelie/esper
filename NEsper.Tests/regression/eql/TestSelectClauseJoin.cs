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
	public class TestSelectClauseJoin 
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
			
			String eventA = typeof(SupportBean).FullName;
			String eventB = typeof(SupportBean).FullName;
			
			String joinStatement = "select s0.doubleBoxed, s1.intPrimitive*s1.intBoxed/2.0 as div from " + eventA + "(string='s0').win:length(3) as s0," + eventB + "(string='s1').win:length(3) as s1" + " where s0.doubleBoxed = s1.doubleBoxed";
			
			joinView = epService.EPAdministrator.createEQL(joinStatement);
			joinView.AddListener(updateListener);
		}
		
		[Test]
		public virtual void  testJoinSelect()
		{
			Assert.IsNull(updateListener.LastNewData);
			
			SendEvent("s0", 1, 4, 5);
			SendEvent("s1", 1, 3, 2);
			
			EventBean[] newEvents = updateListener.LastNewData;
			Assert.AreEqual(1d, newEvents[0]["s0.doubleBoxed"]);
			Assert.AreEqual(3d, newEvents[0]["div"]);
		}
		
		[Test]
		public virtual void  testEventType()
		{
			EventType result = joinView.EventType;
			Assert.AreEqual(typeof(Double), result.GetPropertyType("s0.doubleBoxed"));
			Assert.AreEqual(typeof(Double), result.GetPropertyType("div"));
			Assert.AreEqual(2, joinView.EventType.PropertyNames.Count);
		}
		
		private void  SendEvent(String s, double doubleBoxed, int intPrimitive, int intBoxed)
		{
			SupportBean bean = new SupportBean();
			bean.StringValue = s;
			bean.doubleBoxed = doubleBoxed;
			bean.intPrimitive = intPrimitive;
			bean.intBoxed = intBoxed;
			epService.EPRuntime.SendEvent(bean);
		}
	}
}
