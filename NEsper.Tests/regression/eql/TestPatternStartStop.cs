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
	public class TestPatternStartStop 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener updateListener;
		private EPStatement statement;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();
			
			String stmtText = "select * from pattern [every(a=" + typeof(SupportBean).FullName + " or b=" + typeof(SupportBeanComplexProps).FullName + ")]";
			statement = epService.EPAdministrator.createEQL(stmtText);
			statement.AddListener(updateListener);
		}
		
		[Test]
		public virtual void  testStartStop()
		{
			for (int i = 0; i < 100; i++)
			{
				sendAndAssert();
				
				statement.Stop();
				
				epService.EPRuntime.SendEvent(new SupportBean());
				epService.EPRuntime.SendEvent(SupportBeanComplexProps.makeDefaultBean());
				Assert.IsFalse(updateListener.Invoked);
				
				statement.Start();
			}
		}
		
		private void  sendAndAssert()
		{
			for (int i = 0; i < 1000; i++)
			{
				Object _event = null;
				if (i % 3 == 0)
				{
					_event = new SupportBean();
				}
				else
				{
					_event = SupportBeanComplexProps.makeDefaultBean();
				}
				
				epService.EPRuntime.SendEvent(_event);
				
				EventBean eventBean = updateListener.assertOneGetNewAndReset();
				if (_event is SupportBean)
				{
					Assert.AreSame(_event, eventBean["a"]);
					Assert.IsNull(eventBean["b"]);
				}
				else
				{
					Assert.AreSame(_event, eventBean["b"]);
					Assert.IsNull(eventBean["a"]);
				}
			}
		}
	}
}
