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
	public class TestJoinInheritAndInterface 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testInterfaceJoin()
		{
			String viewExpr = "select a, b from " + typeof(ISupportA).FullName + ".win:length(10), " + typeof(ISupportB).FullName + ".win:length(10)" + " where a = b";
			
			EPStatement testView = epService.EPAdministrator.createEQL(viewExpr);
			testListener = new SupportUpdateListener();
			testView.AddListener(testListener);
			
			epService.EPRuntime.SendEvent(new ISupportAImpl("1", "ab1"));
			epService.EPRuntime.SendEvent(new ISupportBImpl("2", "ab2"));
			Assert.IsFalse(testListener.Invoked);
			
			epService.EPRuntime.SendEvent(new ISupportBImpl("1", "ab3"));
			Assert.IsTrue(testListener.Invoked);
			EventBean _event = testListener.getAndResetLastNewData()[0];
			Assert.AreEqual("1", _event["a"]);
			Assert.AreEqual("1", _event["b"]);
		}
	}
}
