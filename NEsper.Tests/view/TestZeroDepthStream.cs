using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{
	[TestFixture]
	public class TestZeroDepthStream 
	{
		private ZeroDepthStream stream;
		private SupportSchemaNeutralView testChildView;
		private EventType eventType;
		
		private EventBean eventBean;
		
		[SetUp]
		public virtual void  setUp()
		{
			eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean_A));
			
			stream = new ZeroDepthStream(eventType);
			
			testChildView = new SupportSchemaNeutralView();
			stream.AddView(testChildView);
			testChildView.Parent = stream;
			
			eventBean = SupportEventBeanFactory.createObject(new SupportBean_A("a1"));
		}
		
		[Test]
		public virtual void  testInsert()
		{
			testChildView.clearLastNewData();
			stream.Insert(eventBean);
			
			Assert.IsTrue(testChildView.LastNewData != null);
			Assert.AreEqual(1, testChildView.LastNewData.Length);
			Assert.AreEqual(eventBean, testChildView.LastNewData[0]);
			
			// Remove view
			testChildView.clearLastNewData();
			stream.RemoveView(testChildView);
			stream.Insert(eventBean);
			Assert.IsTrue(testChildView.LastNewData == null);
		}
	}
}
