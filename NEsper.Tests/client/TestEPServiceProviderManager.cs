///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

namespace net.esper.client
{
	[TestFixture]
	public class TestEPServiceProviderManager
	{
	    [Test]
	    public void testGetInstance()
	    {
	        Configuration configuration = new Configuration();

	        EPServiceProvider runtimeDef1 = EPServiceProviderManager.GetDefaultProvider();
	        EPServiceProvider runtimeA1 = EPServiceProviderManager.GetProvider("A");
	        EPServiceProvider runtimeB = EPServiceProviderManager.GetProvider("B");
	        EPServiceProvider runtimeA2 = EPServiceProviderManager.GetProvider("A");
	        EPServiceProvider runtimeDef2 = EPServiceProviderManager.GetDefaultProvider(configuration);
	        EPServiceProvider runtimeA3 = EPServiceProviderManager.GetProvider("A", configuration);

	        Assert.IsNotNull(runtimeDef1);
	        Assert.IsNotNull(runtimeA1);
	        Assert.IsNotNull(runtimeB);
	        Assert.IsTrue(runtimeDef1 == runtimeDef2);
	        Assert.IsTrue(runtimeA1 == runtimeA2);
	        Assert.IsTrue(runtimeA1 == runtimeA3);
	        Assert.IsFalse(runtimeA1 == runtimeDef1);
	        Assert.IsFalse(runtimeA1 == runtimeB);

	        Assert.AreEqual("A", runtimeA1.URI);
	        Assert.AreEqual("A", runtimeA2.URI);
	        Assert.AreEqual("B", runtimeB.URI);
	        Assert.IsNull(runtimeDef1.URI);
	        Assert.IsNull(runtimeDef2.URI);
	    }

	    [Test]
	    public void testInvalid()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddEventTypeAlias("x", "xxx.noclass");

	        try
	        {
	            EPServiceProviderManager.GetProvider("someURI", configuration);
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // Expected
	        }
	    }
	}
} // End of namespace
