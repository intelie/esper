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
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.util;

namespace net.esper.regression.db
{
	[TestFixture]
	public class TestDatabaseJoin
	{
	    private readonly static String ALL_FIELDS = "mybigint, myint, myvarchar, mychar, mybool, mynumeric, mydecimal, mydouble, myreal";

	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        ConfigurationDBRef configDB = new ConfigurationDBRef();
	        configDB.DriverManagerConnection = (SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
	        configDB.ConnectionLifecycle = (ConnectionLifecycleEnum.RETAIN);
	        configDB.ConnectionCatalog = ("test");
	        configDB.ConnectionReadOnly = (true);
	        configDB.ConnectionTransactionIsolation = (1);
	        configDB.ConnectionAutoCommit = (true);

	        Configuration configuration = new Configuration();
	        configuration.AddDatabaseReference("MyDB", configDB);

	        epService = EPServiceProviderManager.GetProvider("TestDatabaseJoinRetained", configuration);
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestTimeBatch()
	    {
	        String stmtText = "select " + ALL_FIELDS + " from " +
	                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s0," +
	                typeof(SupportBean).FullName + ".win:time_batch(10 sec) as s1";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));

	        SendSupportBeanEvent(10);
	        SendSupportBeanEvent(5);
	        SendSupportBeanEvent(2);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(10000));
	        EventBean[] received = listener.LastNewData;
	        Assert.AreEqual(3, received.Length);
	        Assert.AreEqual(100, received[0]["myint"]);
	        Assert.AreEqual(50, received[1]["myint"]);
	        Assert.AreEqual(20, received[2]["myint"]);
	    }

	    [Test]
	    public void TestInvalidSQL()
	    {
	        String stmtText = "select myvarchar from " +
	                " sql:MyDB ['select mychar,, from mytesttable where '] as s0," +
	                typeof(SupportBeanComplexProps).FullName + " as s1";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Error in statement 'select mychar,, from mytesttable where ', failed to obtain result metadata, please check the statement, reason: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ' from mytesttable where' at line 1 [select myvarchar from  sql:MyDB ['select mychar,, from mytesttable where '] as s0,net.esper.support.bean.SupportBeanComplexProps as s1]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestInvalidBothHistorical()
	    {
	        String sqlOne = "sql:MyDB ['select myvarchar from mytesttable where ${mychar} = mytesttable.mybigint']";
	        String sqlTwo = "sql:MyDB ['select mychar from mytesttable where ${myvarchar} = mytesttable.mybigint']";
	        String stmtText = "select s0.myvarchar as s0Name, s1.mychar as s1Name from " +
	                sqlOne + " as s0, " + sqlTwo + "  as s1";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Joins between historical data streams are not supported [select s0.myvarchar as s0Name, s1.mychar as s1Name from sql:MyDB ['select myvarchar from mytesttable where ${mychar} = mytesttable.mybigint'] as s0, sql:MyDB ['select mychar from mytesttable where ${myvarchar} = mytesttable.mybigint']  as s1]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestInvalid3Streams()
	    {
	        String sql = "sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.mybigint']";
	        String stmtText = "select s0.myvarchar as s0Name from " +
	                sql + " as s0, " + typeof(SupportBean).FullName + " as s1," + typeof(SupportBean_S0).FullName + " as s2";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Joins between historical data require a only one event stream in the join [select s0.myvarchar as s0Name from sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s0, net.esper.support.bean.SupportBean as s1,net.esper.support.bean.SupportBean_S0 as s2]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestInvalidPropertyEvent()
	    {
	        String stmtText = "select myvarchar from " +
	                " sql:MyDB ['select mychar from mytesttable where ${s1.xxx[0]} = mytesttable.mybigint'] as s0," +
	                typeof(SupportBeanComplexProps).FullName + " as s1";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Property 's1.xxx[0]' failed to resolve, reason: Property named 'xxx[0]' is not valid in stream s1 [select myvarchar from  sql:MyDB ['select mychar from mytesttable where ${s1.xxx[0]} = mytesttable.mybigint'] as s0,net.esper.support.bean.SupportBeanComplexProps as s1]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestInvalidPropertyHistorical()
	    {
	        String stmtText = "select myvarchar from " +
	                " sql:MyDB ['select myvarchar from mytesttable where ${myvarchar} = mytesttable.mybigint'] as s0," +
	                typeof(SupportBeanComplexProps).FullName + " as s1";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Invalid property 'myvarchar' resolves to the historical data itself [select myvarchar from  sql:MyDB ['select myvarchar from mytesttable where ${myvarchar} = mytesttable.mybigint'] as s0,net.esper.support.bean.SupportBeanComplexProps as s1]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestInvalid1Stream()
	    {
	        String sql = "sql:MyDB ['select myvarchar, mybigint from mytesttable where ${mybigint} = myint']";
	        String stmtText = "select myvarchar as s0Name from " + sql + " as s0";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Invalid property 'mybigint' resolves to the historical data itself [select myvarchar as s0Name from sql:MyDB ['select myvarchar, mybigint from mytesttable where ${mybigint} = myint'] as s0]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestInvalidSubviews()
	    {
	        String sql = "sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.myint'].win:time(30 sec)";
	        String stmtText = "select myvarchar as s0Name from " +
	                sql + " as s0, " + typeof(SupportBean).FullName + " as s1";

	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Historical data joins do not allow views onto the data, view 'win:time' is not valid in this context [select myvarchar as s0Name from sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.myint'].win:time(30 sec) as s0, net.esper.support.bean.SupportBean as s1]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestStreamNamesAndRename()
	    {
	        String stmtText = "select s1.a as mybigint, " +
	                " s1.b as myint," +
	                " s1.c as myvarchar," +
	                " s1.d as mychar," +
	                " s1.e as mybool," +
	                " s1.f as mynumeric," +
	                " s1.g as mydecimal," +
	                " s1.h as mydouble," +
	                " s1.i as myreal " +
	                " from " + typeof(SupportBean_S0).FullName + " as s0," +
	                " sql:MyDB ['select mybigint as a, " +
	                " myint as b," +
	                " myvarchar as c," +
	                " mychar as d," +
	                " mybool as e," +
	                " mynumeric as f," +
	                " mydecimal as g," +
	                " mydouble as h," +
	                " myreal as i " +
	                "from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        SendEventS0(1);
	        AssertReceived(1, 10, "A", "Z", true, 5000.0m, 100.0m, 1.2, 1.3);
	    }

	    [Test]
	    public void TestWithPattern()
	    {
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));

	        String stmtText = "select mychar from " +
	                " sql:MyDB ['select mychar from mytesttable where mytesttable.mybigint = 2'] as s0," +
	                " pattern [every timer:interval(5 sec) ]";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(5000));
	        Assert.AreEqual("Y", listener.AssertOneGetNewAndReset()["mychar"]);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(9999));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(10000));
	        Assert.AreEqual("Y", listener.AssertOneGetNewAndReset()["mychar"]);
	    }

	    [Test]
	    public void TestPropertyResolution()
	    {
	        String stmtText = "select " + ALL_FIELDS + " from " +
	                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${s1.arrayProperty[0]} = mytesttable.mybigint'] as s0," +
	                typeof(SupportBeanComplexProps).FullName + " as s1";
	        // s1.arrayProperty[0] returns 10 for that bean

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        epService.EPRuntime.SendEvent(SupportBeanComplexProps.MakeDefaultBean());
	        AssertReceived(10, 100, "J", "P", true, null, 1000.0m, 10.2, 10.3);
	    }

	    [Test]
	    public void TestSimpleJoinLeft()
	    {
	        String stmtText = "select " + ALL_FIELDS + " from " +
	                typeof(SupportBean_S0).FullName + " as s0," +
	                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        SendEventS0(1);
	        AssertReceived(1, 10, "A", "Z", true, 5000.0m, 100.0m, 1.2, 1.3);
	    }

	    [Test]
	    public void TestRestartStatement()
	    {
	        String stmtText = "select mychar from " +
	                typeof(SupportBean_S0).FullName + " as s0," +
	                " sql:MyDB ['select mychar from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        // Too many connections unless the stop actually relieves them
	        for (int i = 0; i < 100; i++)
	        {
	            statement.Stop();

	            SendEventS0(1);
	            Assert.IsFalse(listener.IsInvoked);

	            statement.Start();
	            SendEventS0(1);
	            Assert.AreEqual("Z", listener.AssertOneGetNewAndReset()["mychar"]);
	        }
	    }

	    [Test]
	    public void TestSimpleJoinRight()
	    {
	        String stmtText = "select " + ALL_FIELDS + " from " +
	                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${id} = mytesttable.mybigint'] as s0," +
	                typeof(SupportBean_S0).FullName + " as s1";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        EventType eventType = statement.EventType;
	        Assert.AreEqual(typeof(long?), eventType.GetPropertyType("mybigint"));
	        Assert.AreEqual(typeof(int?), eventType.GetPropertyType("myint"));
	        Assert.AreEqual(typeof(string), eventType.GetPropertyType("myvarchar"));
	        Assert.AreEqual(typeof(string), eventType.GetPropertyType("mychar"));
	        Assert.AreEqual(typeof(bool?), eventType.GetPropertyType("mybool"));
	        Assert.AreEqual(typeof(decimal?), eventType.GetPropertyType("mynumeric"));
	        Assert.AreEqual(typeof(decimal?), eventType.GetPropertyType("mydecimal"));
	        Assert.AreEqual(typeof(double?), eventType.GetPropertyType("mydouble"));
	        Assert.AreEqual(typeof(double?), eventType.GetPropertyType("myreal"));

	        SendEventS0(1);
	        AssertReceived(1, 10, "A", "Z", true, 5000.0m, 100.0m, 1.2, 1.3);
	    }

	    private void AssertReceived(long mybigint, int myint, String myvarchar, String mychar, bool mybool, decimal? mynumeric, decimal? mydecimal, double? mydouble, double? myreal)
	    {
	        EventBean _event = listener.AssertOneGetNewAndReset();
	        AssertReceived(_event, mybigint, myint, myvarchar, mychar, mybool, mynumeric, mydecimal, mydouble, myreal);
	    }

	    private void AssertReceived(EventBean _event, long? mybigint, int? myint, String myvarchar, String mychar, bool? mybool, decimal? mynumeric, decimal? mydecimal, double? mydouble, double? myreal)
	    {
	        Assert.AreEqual(mybigint, _event["mybigint"]);
	        Assert.AreEqual(myint, _event["myint"]);
	        Assert.AreEqual(myvarchar, _event["myvarchar"]);
	        Assert.AreEqual(mychar, _event["mychar"]);
	        Assert.AreEqual(mybool, _event["mybool"]);
	        Assert.AreEqual(mynumeric, _event["mynumeric"]);
	        Assert.AreEqual(mydecimal, _event["mydecimal"]);
	        Assert.AreEqual(mydouble, _event["mydouble"]);
	        Object r = _event["myreal"];
	        Assert.AreEqual(myreal, _event["myreal"]);
	    }

	    [Test]
	    public void TestMySQLDatabaseConnection()
	    {
            //Type.ForName(SupportDatabaseService.DRIVER).NewInstance();
            //Connection conn = null;
            //try
            //{
            //    conn = DriverManager.GetConnection(SupportDatabaseService.FULLURL);
            //}
            //catch (SqlException ex) {
            //    // handle any errors
            //    Console.WriteLine("SQLException: " + ex.Message);
            //    Console.WriteLine("SQLState: " + ex.GetSQLState());
            //    Console.WriteLine("VendorError: " + ex.GetErrorCode());
            //    throw ex;
            //}
            //Statement stmt = conn.CreateStatement( );
            //ResultSet rs = stmt.ExecuteQuery( "SELECT * FROM mytesttable");
            //rs.Close();
            //stmt.Close();
            //conn.Close();
	    }

	    private void SendEventS0(int id)
	    {
	        SupportBean_S0 bean = new SupportBean_S0(id);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendSupportBeanEvent(int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        epService.EPRuntime.SendEvent(bean);
	    }
	}
} // End of namespace
