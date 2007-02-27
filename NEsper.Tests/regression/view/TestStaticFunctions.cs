using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

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

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
            stream = " from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) ";
        }

        [Test]
        public virtual void testRuntimeException()
        {
            String className = typeof(SupportStaticMethodLib).FullName;
            statementText = "select price, " + className + ".throwException() " + stream;
            try
            {
                createStatementAndGetProperty(true, "price");
                Assert.Fail();
            }
            catch (EPException)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testAutoImports()
        {
            Configuration configuration = new Configuration();
            configuration.AddImport("mull");
            epService = EPServiceProviderManager.GetProvider("1", configuration);

            statementText = "select Convert.ToString(7, 2) " + stream;
            try
            {
                createStatementAndGetProperty(true, "Convert.ToString(7, 2)");
                Assert.Fail();
            }
            catch (EPStatementException)
            {
                // expected
            }

            configuration.AddImport("System");
            epService = EPServiceProviderManager.GetProvider("2", configuration);

            Object[] result = createStatementAndGetProperty(true, "Convert.ToString(7, 2)");
            Assert.AreEqual(Convert.ToString(7, 2), result[0]);
        }

        [Test]
        public virtual void testNoParameters()
        {
            long startTime = DateTimeHelper.CurrentTimeMillis;
            statementText = "select net.esper.compat.DateTimeHelper.GetCurrentTimeMillis() " + stream;
            long? result = (long)createStatementAndGet("net.esper.compat.DateTimeHelper.GetCurrentTimeMillis()");
            long finishTime = DateTimeHelper.CurrentTimeMillis;
            Assert.IsTrue(startTime <= result);
            Assert.IsTrue(result <= finishTime);

            statementText = "select UnknownClass.invalidMethod() " + stream;
            try
            {
                createStatementAndGetProperty(true, "invalidMethod()");
                Assert.Fail();
            }
            catch (EPStatementException)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testSingleParameter()
        {
            statementText = "select Convert.ToString(7, 2) " + stream;
            Object[] result = createStatementAndGetProperty(true, "Convert.ToString(7, 2)");
            Assert.AreEqual(Convert.ToString(7, 2), result[0]);

            statementText = "select Int32.Parse(\"6\") " + stream;
            result = createStatementAndGetProperty(true, "Int32.Parse(\"6\")");
            Assert.AreEqual(Int32.Parse("6"), result[0]);

            statementText = "select Convert.ToString(\'a\') " + stream;
            result = createStatementAndGetProperty(true, "Convert.ToString(\"a\")" ) ;
            Assert.AreEqual(Convert.ToString('a'), result[0]);
        }

        [Test]
        public virtual void testTwoParameters()
        {
            statementText = "select System.Math.Max(2, 3) " + stream;
            Assert.AreEqual(3, createStatementAndGetProperty(true, "System.Math.Max(2, 3)")[0]);

            statementText = "select System.Math.Max(2, 3d) " + stream;
            Assert.AreEqual(3d, createStatementAndGetProperty(true, "System.Math.Max(2, 3)")[0]);

            statementText = "select System.Convert.ToInt64(\"123\", 10)" + stream;
            Object expected = 123L;
            Assert.AreEqual(expected, createStatementAndGetProperty(true, "System.Convert.ToInt64(\"123\", 10)")[0]);
        }

        [Test]
        public virtual void testUserDefined()
        {
            String className = typeof(SupportStaticMethodLib).FullName;
            statementText = "select " + className + ".staticMethod(2)" + stream;
            Assert.AreEqual(2, createStatementAndGetProperty(true, className + ".staticMethod(2)")[0]);
        }

        [Test]
        public virtual void testComplexParameters()
        {
            statementText = "select System.Convert.ToString(price) " + stream;
            Object[] result = createStatementAndGetProperty(true, "System.Convert.ToString(price)");
            Assert.AreEqual(Convert.ToString(10d), result[0]);

            statementText = "select System.Convert.ToString(2 + 3*5) " + stream;
            result = createStatementAndGetProperty(true, "System.Convert.ToString((2+(3*5)))");
            Assert.AreEqual(Convert.ToString(17), result[0]);

            statementText = "select System.Convert.ToString(price*volume +volume) " + stream;
            result = createStatementAndGetProperty(true, "System.Convert.ToString(((price*volume)+volume))");
            Assert.AreEqual(Convert.ToString(44d), result[0]);

            statementText = "select System.Convert.ToString(Math.Pow(price, Int32.Parse(\"2\"))) " + stream;
            result = createStatementAndGetProperty(true, "System.Convert.ToString(Math.Pow(price, Int32.Parse(\"2\")))");
            Assert.AreEqual(Convert.ToString(100d), result[0]);
        }

        [Test]
        public virtual void testMultipleMethodInvocations()
        {
            statementText = "select System.Math.Max(2d, price), System.Math.Max(volume, 4d)" + stream;
            Object[] props = createStatementAndGetProperty(
                true, 
                "System.Math.Max(2, price)",
                "System.Math.Max(volume, 4)");
            Assert.AreEqual(10d, props[0]);
            Assert.AreEqual(4d, props[1]);
        }

        [Test]
        public virtual void testOtherClauses()
        {
            // where
            statementText = "select *" + stream + "where System.Math.Pow(price, .5) > 2";
            Assert.AreEqual("IBM", createStatementAndGetProperty(true, "symbol")[0]);
            SendEvent("CAT", 4d, 100);
            Assert.IsNull(getProperty("symbol"));

            // group-by
            statementText = "select symbol, sum(price)" + stream + "group by System.Convert.ToString(symbol)";
            Assert.AreEqual(10d, createStatementAndGetProperty(true, "sum(price)")[0]);
            SendEvent("IBM", 4d, 100);
            Assert.AreEqual(14d, getProperty("sum(price)"));

            epService.Initialize();

            // having
            statementText = "select symbol, sum(price)" + stream + "having System.Math.Pow(sum(price), .5) > 3";
            Assert.AreEqual(10d, createStatementAndGetProperty(true, "sum(price)")[0]);
            SendEvent("IBM", 100d, 100);
            Assert.AreEqual(110d, getProperty("sum(price)"));

            // order-by
            statementText = "select symbol, price" + stream + "output every 3 events order by System.Math.Pow(price, 2)";
            createStatementAndGetProperty(false, "symbol");
            SendEvent("CAT", 10d, 0L);
            SendEvent("MAT", 3d, 0L);

            EventBean[] newEvents = listener.getAndResetLastNewData();
            Assert.IsTrue(newEvents.Length == 3);
            Assert.AreEqual("MAT", newEvents[0]["symbol"]);
            Assert.AreEqual("IBM", newEvents[1]["symbol"]);
            Assert.AreEqual("CAT", newEvents[2]["symbol"]);
        }

        private Object createStatementAndGet(String propertyName)
        {
            statement = epService.EPAdministrator.CreateEQL(statementText);
            listener = new SupportUpdateListener();
            statement.AddListener(listener.Update);
            epService.EPRuntime.SendEvent(new SupportMarketDataBean("IBM", 10d, 4L, ""));
            return getProperty(propertyName);
        }

        private Object getProperty(String propertyName)
        {
            EventBean[] newData = listener.getAndResetLastNewData();
            if (newData == null || newData.Length == 0)
            {
                return null;
            }
            else
            {
                return newData[0][propertyName];
            }
        }

        private Object[] createStatementAndGetProperty(bool expectResult, params string[] propertyNames)
        {
            statement = epService.EPAdministrator.CreateEQL(statementText);
            listener = new SupportUpdateListener();
            statement.AddListener(listener.Update);
            SendEvent("IBM", 10d, 4L);

            if (expectResult)
            {
                List<Object> properties = new List<Object>();
                EventBean _event = listener.getAndResetLastNewData()[0];
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
}