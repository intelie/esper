using System;
using System.Collections.Generic;

using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.db
{
	//[TestFixture]
	public class TestPollingViewableFactory 
	{
		[Test]
		public virtual void testDBStatementViewFactory()
		{
            DBStatementStreamSpec spec = new DBStatementStreamSpec(
                "s0",
                new List<ViewSpec>(),
                "test",
                "select * from mytesttable where mybigint=${idnum}");

			EventCollection eventCollection = PollingViewableFactory.CreateDBStatementView(1, spec, SupportDatabaseService.makeService(), SupportEventAdapterService.Service);
			
			Assert.AreEqual(typeof(Int64), eventCollection.EventType.GetPropertyType("mybigint"));
			Assert.AreEqual(typeof(String), eventCollection.EventType.GetPropertyType("myvarchar"));
			Assert.AreEqual(typeof(bool), eventCollection.EventType.GetPropertyType("mybool"));
			Assert.AreEqual(typeof(System.Decimal), eventCollection.EventType.GetPropertyType("mynumeric"));
			Assert.AreEqual(typeof(System.Decimal), eventCollection.EventType.GetPropertyType("mydecimal"));
		}
	}
}
