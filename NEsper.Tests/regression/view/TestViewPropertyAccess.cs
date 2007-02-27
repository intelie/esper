using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewPropertyAccess 
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
		public virtual void  testWhereAndSelect()
		{
			String viewExpr = "select mapped('keyOne') as a," + "indexed[1] as b, nested.nestedNested.nestedNestedValue as c, mapProperty, " + "arrayProperty[0] " + "  from " + typeof(SupportBeanComplexProps).FullName + ".win:length(3) " + " where mapped('keyOne') = 'valueOne' and " + " indexed[1] = 2 and " + " nested.nestedNested.nestedNestedValue = 'nestedNestedValue'";
			
			EPStatement testView = epService.EPAdministrator.CreateEQL(viewExpr);
			testListener = new SupportUpdateListener();
			testView.AddListener(testListener.Update);
			
			SupportBeanComplexProps eventObject = SupportBeanComplexProps.makeDefaultBean();
			epService.EPRuntime.SendEvent(eventObject);
			EventBean _event = testListener.getAndResetLastNewData()[0];
			Assert.AreEqual(eventObject.getMapped("keyOne"), _event["a"]);
			Assert.AreEqual(eventObject.getIndexed(1), _event["b"]);
			Assert.AreEqual(eventObject.Nested.NestedNested.NestedNestedValue, _event["c"]);
            Assert.AreEqual(eventObject.MapProperty, _event["mapProperty"]);
			Assert.AreEqual(eventObject.ArrayProperty[0], _event["arrayProperty[0]"]);
			
			eventObject.setIndexed(1, Int32.MinValue);
			Assert.IsFalse(testListener.Invoked);
			epService.EPRuntime.SendEvent(eventObject);
			Assert.IsFalse(testListener.Invoked);
			
			eventObject.setIndexed(1, 2);
			epService.EPRuntime.SendEvent(eventObject);
			Assert.IsTrue(testListener.Invoked);
		}
	}
}
