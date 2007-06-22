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

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestStaticFunctions
	{
		private EPServiceProvider epService;
		private String stream;
		private String statementText;
		private EPStatement statement;
		private SupportUpdateListener listener;

		protected void SetUp()
		{
		    epService = EPServiceProviderManager.GetDefaultProvider();
		    epService.Initialize();
		    stream = " from " + typeof(SupportMarketDataBean).FullName +".win:length(5) ";
		}

		[Test]
		public void TestRuntimeException()
		{
			String className = typeof(SupportStaticMethodLib).FullName;
			statementText = "select price, " + className + ".ThrowException() " + stream;
			try
			{
				CreateStatementAndGetProperty(true,"price");
				Assert.Fail();
			}
			catch(EPException e)
			{
				// Expected
			}
		}

		[Test]
		public void TestAutoImports()
		{
			Configuration configuration = new Configuration();
			configuration.AddImport("mull");
			epService = EPServiceProviderManager.GetProvider("1", configuration);

            statementText = "select BitWriter.Write(7) " + stream;
			try
			{
                CreateStatementAndGetProperty(true, "BitWriter.Write(7)");
				Assert.Fail();
			}
			catch(EPStatementException e)
			{
				// expected
			}

			configuration.AddImport("net.esper.support.util");
			epService = EPServiceProviderManager.GetProvider("2", configuration);

			Object[] result = CreateStatementAndGetProperty(true, "BitWriter.Write(7)");
            Assert.AreEqual(BitWriter.Write(7), result[0]);
		}

	    [Test]
	    public void TestRuntimeAutoImports()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        String text = "select SupportStaticMethodLib.MinusOne(doublePrimitive) from " + typeof(SupportBean).FullName;

	        try
	        {
	            epService.EPAdministrator.CreateEQL(text);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Could not load class by name 'SupportStaticMethodLib'  [select SupportStaticMethodLib.MinusOne(doublePrimitive) from net.esper.support.bean.SupportBean]", ex.Message);
	        }

	        epService.EPAdministrator.Configuration.AddImport(typeof(SupportStaticMethodLib).FullName);
	        epService.EPAdministrator.CreateEQL(text);
	    }

		[Test]
		public void TestNoParameters()
		{
			long startTime = DateTimeHelper.CurrentTimeMillis;
			statementText = "select DateTimeHelper.CurrentTimeMillis " + stream;
			long result = (long) CreateStatementAndGet("DateTimeHelper.CurrentTimeMillis");
			long finishTime = DateTimeHelper.CurrentTimeMillis;
			Assert.IsTrue(startTime <= result);
			Assert.IsTrue(result <= finishTime);

            //statementText = "select java.lang.ClassLoader.GetSystemClassLoader() " + stream;
            //Object expected = ClassLoader.GetSystemClassLoader();
            //Object[] resultTwo = CreateStatementAndGetProperty(true, "java.lang.ClassLoader.GetSystemClassLoader()");
            //Assert.AreEqual(expected, resultTwo[0]);

			statementText = "select UnknownClass.InvalidMethod() " + stream;
			try
			{
    			CreateStatementAndGetProperty(true, "invalidMethod()");
				Assert.Fail();
			}
			catch(EPStatementException e)
			{
				// Expected
			}
		}

		[Test]
		public void TestSingleParameter()
		{
            statementText = "select BitWriter.Write(7) " + stream;
			Object[] result = CreateStatementAndGetProperty(true, "Integer.ToBinaryString(7)");
            Assert.AreEqual(BitWriter.Write(7), result[0]);

			statementText = "select Int32.Parse(\"6\") " + stream;
			result = CreateStatementAndGetProperty(true, "Int32.Parse(\"6\")");
			Assert.AreEqual(Int32.Parse("6"), result[0]);

			statementText = "select System.Convert.ToString(\'a\') " + stream;
			result = CreateStatementAndGetProperty(true, "System.Convert.ToString(\"a\")");
            Assert.AreEqual(Convert.ToString('a'), result[0]);
		}

		[Test]
		public void TestTwoParameters()
		{
			statementText = "select Math.Max(2, 3) " + stream;
            Assert.AreEqual(3, CreateStatementAndGetProperty(true, "Math.Max(2, 3)")[0]);

			statementText = "select System.Math.Max(2, 3d) " + stream;
            Assert.AreEqual(3d, CreateStatementAndGetProperty(true, "System.Math.Max(2, 3.0)")[0]);

			statementText = "select Int64.Parse(\"123\", 10)" + stream;
			Object expected = Int64.Parse("123");
            Assert.AreEqual(expected, CreateStatementAndGetProperty(true, "Long.ParseLong(\"123\", 10)")[0]);
		}

		[Test]
		public void TestUserDefined()
		{
			String className = typeof(SupportStaticMethodLib).FullName;
			statementText = "select " + className + ".StaticMethod(2)" + stream;
            Assert.AreEqual(2, CreateStatementAndGetProperty(true, className + ".StaticMethod(2)")[0]);
		}

		[Test]
		public void TestComplexParameters()
		{
			statementText = "select Convert.ToString(price) " + stream;
			Object[] result = CreateStatementAndGetProperty(true, "Convert.ToString(price)");
            Assert.AreEqual(Convert.ToString(10d), result[0]);

			statementText = "select Convert.ToString(2 + 3*5) " + stream;
			result = CreateStatementAndGetProperty(true, "Convert.ToString((2+(3*5)))");
            Assert.AreEqual(Convert.ToString(17), result[0]);

			statementText = "select Convert.ToString(price*volume +volume) " + stream;
			result = CreateStatementAndGetProperty(true, "Convert.ToString(((price*volume)+volume))");
            Assert.AreEqual(Convert.ToString(44d), result[0]);

			statementText = "select Convert.ToString(Math.Pow(price, Int32.Parse(\"2\"))) " + stream;
			result = CreateStatementAndGetProperty(true, "Convert.ToString(Math.Pow(price, Int32.Parse(\"2\")))");
            Assert.AreEqual(Convert.ToString(100d), result[0]);
		}

		[Test]
		public void TestMultipleMethodInvocations()
		{
			statementText = "select Math.Max(2d, price), Math.Max(volume, 4d)" + stream;
			Object[] props = CreateStatementAndGetProperty(true, "Math.Max(2.0, price)", "Math.Max(volume, 4.0)");
            Assert.AreEqual(10d, props[0]);
            Assert.AreEqual(4d, props[1]);
		}

		[Test]
		public void TestOtherClauses()
		{
			// where
			statementText = "select *" + stream + "where Math.Pow(price, .5) > 2";
            Assert.AreEqual("IBM", CreateStatementAndGetProperty(true, "symbol")[0]);
            SendEvent("CAT", 4d, 100);
			Assert.IsNull(GetProperty("symbol"));

			// group-by
			statementText = "select symbol, sum(price)" + stream + "group by Convert.ToString(symbol)";
            Assert.AreEqual(10d, CreateStatementAndGetProperty(true, "sum(price)")[0]);
            SendEvent("IBM", 4d, 100);
            Assert.AreEqual(14d, GetProperty("sum(price)"));

			epService.Initialize();

			// having
			statementText = "select symbol, sum(price)" + stream + "having Math.Pow(Sum(price), .5) > 3";
            Assert.AreEqual(10d, CreateStatementAndGetProperty(true, "sum(price)")[0]);
            SendEvent("IBM", 100d, 100);
            Assert.AreEqual(110d, GetProperty("sum(price)"));

	        // order-by
			statementText = "select symbol, price" + stream + "output every 3 events order by Math.Pow(price, 2)";
			CreateStatementAndGetProperty(false, "symbol");
            SendEvent("CAT", 10d, 0L);
			SendEvent("MAT", 3d, 0L);

			EventBean[] newEvents = listener.GetAndResetLastNewData();
			Assert.IsTrue(newEvents.Length == 3);
            Assert.AreEqual("MAT", newEvents[0]["symbol"]);
            Assert.AreEqual("IBM", newEvents[1]["symbol"]);
            Assert.AreEqual("CAT", newEvents[2]["symbol"]);
		}

	    [Test]
	    public void TestNestedFunction()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddImport(typeof(SupportStaticMethodLib).FullName);
	        configuration.AddEventTypeAlias("Temperature", typeof(SupportTemperatureBean));
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        String text = "select " +
	                "SupportStaticMethodLib.AppendPipe(SupportStaticMethodLib.DelimitPipe('POLYGON ((100 100, \", 100 100, 400 400))'),temp.geom) as val" +
	                " from Temperature as temp";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportTemperatureBean("a"));
	        Assert.AreEqual("|POLYGON ((100 100, \", 100 100, 400 400))||a", listener.AssertOneGetNewAndReset()["val"]);
	    }

	    [Test]
	    public void TestPassthru()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddImport(typeof(SupportStaticMethodLib).FullName);
	        configuration.AddEventTypeAlias("Temperature", typeof(SupportTemperatureBean));
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        String text = "select " +
	                "SupportStaticMethodLib.Passthru(id) as val" +
	                " from " + typeof(SupportBean_S0).FullName;
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1));
	        Assert.AreEqual(1L, listener.AssertOneGetNewAndReset()["val"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(2L, listener.AssertOneGetNewAndReset()["val"]);
	    }

	    [Test]
	    public void TestPerfConstantParameters()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddImport(typeof(SupportStaticMethodLib).FullName);
	        configuration.AddEventTypeAlias("Temperature", typeof(SupportTemperatureBean));
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        String text = "select " +
	                "SupportStaticMethodLib.Sleep(100) as val" +
	                " from Temperature as temp";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 1000; i++)
	        {
	            epService.EPRuntime.SendEvent(new SupportTemperatureBean("a"));
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void TestPerfConstantParametersNested()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddImport(typeof(SupportStaticMethodLib).FullName);
	        configuration.AddEventTypeAlias("Temperature", typeof(SupportTemperatureBean));
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        String text = "select " +
	                "SupportStaticMethodLib.Sleep(SupportStaticMethodLib.Passthru(100)) as val" +
	                " from Temperature as temp";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 1000; i++)
	        {
	            epService.EPRuntime.SendEvent(new SupportTemperatureBean("a"));
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

            Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }

	    private Object CreateStatementAndGet(String propertyName)
		{
			statement = epService.EPAdministrator.CreateEQL(statementText);
			listener = new SupportUpdateListener();
			statement.AddListener(listener);
			epService.EPRuntime.SendEvent(new SupportMarketDataBean("IBM", 10d, 4l, ""));
			return GetProperty(propertyName);
		}

		private Object GetProperty(String propertyName)
		{
			EventBean[] newData = listener.GetAndResetLastNewData();
			if(newData == null || newData.Length == 0)
			{
				return null;
			}
			else
			{
				return newData[0][propertyName];
			}
		}

		private Object[] CreateStatementAndGetProperty(bool expectResult, params string[] propertyNames)
		{
			statement = epService.EPAdministrator.CreateEQL(statementText);
			listener = new SupportUpdateListener();
			statement.AddListener(listener);
			SendEvent("IBM", 10d, 4l);

	        if (expectResult)
	        {
	            List<Object> properties = new List<Object>();
	            EventBean _event = listener.GetAndResetLastNewData()[0];
	            foreach (String propertyName in propertyNames)
	            {
	                properties.Add(_event[propertyName]);
	            }
                return properties.ToArray();
	        }
	        return null;
	    }

		private void SendEvent(String symbol, double price, long volume)
		{
			epService.EPRuntime.SendEvent(new SupportMarketDataBean(symbol, price, volume, ""));
		}
	}
} // End of namespace
