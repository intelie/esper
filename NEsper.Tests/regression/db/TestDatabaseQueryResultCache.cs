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
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.db
{
	[TestFixture]
	public class TestDatabaseQueryResultCache
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [Test]
	    public void TestExpireCacheNoPurge()
	    {
	        ConfigurationDBRef configDB = GetDefaultConfig();
	        configDB.SetExpiryTimeCache(1.0d, Double.MaxValue);
	        TryCache(configDB, 5000, 1000, false);
	    }

	    [Test]
	    public void TestLRUCache()
	    {
	        ConfigurationDBRef configDB = GetDefaultConfig();
	        configDB.LRUCache = (100);
	        TryCache(configDB, 2000, 1000, false);
	    }

	    [Test]
	    public void TestLRUCache25k()
	    {
	        ConfigurationDBRef configDB = GetDefaultConfig();
	        configDB.LRUCache = (100);
	        TryCache(configDB, 7000, 25000, false);
	    }

	    [Test]
	    public void TestExpireCache25k()
	    {
	        ConfigurationDBRef configDB = GetDefaultConfig();
	        configDB.SetExpiryTimeCache(2, 2);
	        TryCache(configDB, 7000, 25000, false);
	    }

	    [Test]
	    public void TestExpireRandomKeys()
	    {
	        ConfigurationDBRef configDB = GetDefaultConfig();
	        configDB.SetExpiryTimeCache(1, 1);
	        TryCache(configDB, 7000, 25000, true);
	    }

	    private void TryCache(ConfigurationDBRef configDB, long assertMaximumTime, int numEvents, bool useRandomLookupKey)
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddDatabaseReference("MyDB", configDB);

	        epService = EPServiceProviderManager.GetProvider("TestDatabaseQueryResultCache", configuration);
	        epService.Initialize();

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        TrySendEvents(epService, numEvents, useRandomLookupKey);
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(".tryCache " + configDB.DataCacheDesc + " delta=" + (endTime - startTime));
	        Assert.IsTrue(endTime - startTime < assertMaximumTime);
	    }

	    private void TrySendEvents(EPServiceProvider engine, int numEvents, bool useRandomLookupKey)
	    {
	        Random random = new Random();
	        String stmtText = "select myint from " +
	                typeof(SupportBean_S0).FullName + " as s0," +
	                " sql:MyDB ['select myint from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = engine.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        log.Debug(".trySendEvents Sending " + numEvents + " events");
	        for (int i = 0; i < numEvents; i++)
	        {
	            int id = 0;
	            if (useRandomLookupKey)
	            {
	                id = random.Next(1000);
	            }
	            else
	            {
	                id = i % 10 + 1;
	            }

	            SupportBean_S0 bean = new SupportBean_S0(id);
	            engine.EPRuntime.SendEvent(bean);

	            if ((!useRandomLookupKey) || ((id >= 1) && (id <= 10)))
	            {
	                EventBean received = listener.AssertOneGetNewAndReset();
	                Assert.AreEqual(id * 10, received["myint"]);
	            }
	        }

	        log.Debug(".trySendEvents Stopping statement");
	        statement.Stop();
	    }

	    private ConfigurationDBRef GetDefaultConfig()
	    {
	        ConfigurationDBRef configDB = new ConfigurationDBRef();
	        configDB.DriverManagerConnection = (SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
	        configDB.ConnectionLifecycle = (ConnectionLifecycleEnum.RETAIN);
	        return configDB;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
