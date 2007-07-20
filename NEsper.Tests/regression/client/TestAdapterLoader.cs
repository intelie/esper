// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.adapter;

namespace net.esper.regression.client
{
	[TestFixture]
	public class TestAdapterLoader
	{
	    [Test]
	    public void _TestAdapterLoader()
	    {
	        Configuration config = new Configuration();
	        Properties props = new Properties();
	        props.Put("name", "val");
	        config.AddAdapterLoader("MyLoader", typeof(SupportAdapterLoader).FullName, props);

	        EPServiceProvider service = EPServiceProviderManager.GetProvider("TestAdapterLoader", config);
	        Assert.AreEqual("MyLoader", SupportAdapterLoader.Names[0]);
	        Assert.AreEqual("val", SupportAdapterLoader.Props[0].Fetch("name"));
	    }
	}
} // End of namespace
