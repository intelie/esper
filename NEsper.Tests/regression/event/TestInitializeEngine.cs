using System;

using net.esper.client;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.events
{
	
	[TestFixture]
	public class TestInitializeEngine 
	{
		[Test]
		public virtual void  testInitialize()
		{
			EPServiceProvider epService = EPServiceProviderManager.GetProvider("TestInitializeEngine");
			
			String eqlOne = "insert into A(a) select 1 from " + typeof(SupportBean).FullName + ".win:length(100)";
			String eqlTwo = "insert into A(a, b) select 1,2 from " + typeof(SupportBean).FullName + ".win:length(100)";
			
			// Asserting that the engine allows to use the new event stream A with more properties then the old A
			epService.EPAdministrator.createEQL(eqlOne);
			epService.Initialize();
			epService.EPAdministrator.createEQL(eqlTwo);
		}
	}
}
