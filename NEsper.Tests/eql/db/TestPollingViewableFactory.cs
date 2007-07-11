///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.eql.db;
using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.view;

namespace net.esper.eql.db
{
	//[TestFixture]
	public class TestPollingViewableFactory
	{
        [Test]
	    public void testDBStatementViewFactory()
	    {
	        DBStatementStreamSpec spec = new DBStatementStreamSpec("s0", new List<ViewSpec>(),
	                "mydb_part", "select * from mytesttable where mybigint=${idnum}");

	        EventCollection eventCollection = PollingViewableFactory.CreateDBStatementView(1, spec,
	                SupportDatabaseService.MakeService(),
	                SupportEventAdapterService.GetService(), null);

	        Assert.AreEqual(typeof(long?), eventCollection.EventType.GetPropertyType("mybigint"));
	        Assert.AreEqual(typeof(String), eventCollection.EventType.GetPropertyType("myvarchar"));
	        Assert.AreEqual(typeof(bool?), eventCollection.EventType.GetPropertyType("mybool"));
	        Assert.AreEqual(typeof(decimal?), eventCollection.EventType.GetPropertyType("mynumeric"));
	        Assert.AreEqual(typeof(decimal?), eventCollection.EventType.GetPropertyType("mydecimal"));
	    }
	}
} // End of namespace
