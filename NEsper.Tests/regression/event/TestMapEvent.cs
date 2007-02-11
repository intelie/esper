using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.events
{
	[TestFixture]
    public class TestMapEvent 
    {
        internal EDictionary<string,string> properties;
        internal EDataDictionary map;
        internal EPServiceProvider epService;

	    [SetUp]
        public virtual void setUp()
        {
            properties = new EHashDictionary<string,string>();
            properties[(String)"myInt"] = (String)"int";
            properties[(String)"myString"] = (String)"StringValue";
            properties[(String)"beanA"] = (String)typeof(SupportBeanComplexProps).FullName;

            map = new EDataDictionary() ;
            map.Put("myInt", 3);
            map.Put("myString", "some string");
            map.Put("beanA", SupportBeanComplexProps.makeDefaultBean());

            Configuration configuration = new Configuration();
            configuration.addEventTypeAlias("myMapEvent", properties);

            epService = EPServiceProviderManager.GetProvider("myProvider", configuration);
        }

        [Test]
        public virtual void testNestedObjects()
        {
            String statementText = "select beanA.simpleProperty as simple," + "beanA.nested.nestedValue as nested," + "beanA.indexed[1] as indexed," + "beanA.nested.nestedNested.nestedNestedValue as nestednested " + "from myMapEvent.win:length(5)";
            EPStatement statement = epService.EPAdministrator.createEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);

            epService.EPRuntime.SendEvent(map, "myMapEvent");
            Assert.AreEqual("nestedValue", listener.LastNewData[0]["nested"]);
            Assert.AreEqual(2, listener.LastNewData[0]["indexed"]);
            Assert.AreEqual("nestedNestedValue", listener.LastNewData[0]["nestednested"]);
            statement.Stop();
        }

        [Test]
        public virtual void testQueryFields()
        {
            String statementText = "select myInt + 2 as intVal, 'x' || myString || 'x' as stringVal from myMapEvent.win:length(5)";
            EPStatement statement = epService.EPAdministrator.createEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);

            // send EDictionary<String, Object> event
            epService.EPRuntime.SendEvent(map, "myMapEvent");
            Assert.AreEqual(5, listener.LastNewData[0]["intVal"]);
            Assert.AreEqual("xsome stringx", listener.LastNewData[0]["stringVal"]);

            // send Map base event
            EDataDictionary mapNoType = new EDataDictionary();
            mapNoType["myInt"] = 4;
            mapNoType["myString"] = "string2";
            epService.EPRuntime.SendEvent(mapNoType, "myMapEvent");
            Assert.AreEqual(6, listener.LastNewData[0]["intVal"]);
            Assert.AreEqual("xstring2x", listener.LastNewData[0]["stringVal"]);

            statement.Stop();
        }

        [Test]
        public virtual void testPrimitivesTypes()
        {
            properties = new EHashDictionary<string, string>();
            properties["myInt"] = typeof(int).FullName;
            properties["byteArr"] = typeof(sbyte[]).FullName;
            properties["myInt2"] = "int";
            properties["double"] = "double";
            properties["boolean"] = "boolean";
            properties["long"] = "long";
            properties["astring"] = "StringValue";

            Configuration configuration = new Configuration();
            configuration.addEventTypeAlias("MyPrimMapEvent", properties);

            epService = EPServiceProviderManager.GetProvider("testPrimitivesTypes", configuration);
        }

        [Test]
        public virtual void testInvalidConfig()
        {
            properties = new EHashDictionary<string, string>();
            properties[(String)"astring"] = (String)"XXXX";

            Configuration configuration = new Configuration();
            configuration.addEventTypeAlias("MyInvalidEvent", properties);

            try
            {
                epService = EPServiceProviderManager.GetProvider("testInvalidConfig", configuration);
                Assert.Fail();
            }
            catch (ConfigurationException ex)
            {
                log.Debug(ex);
                // expected
            }
        }

        [Test]
        public virtual void testInvalidStatement()
        {
            tryInvalid("select XXX from myMapEvent.win:length(5)");
            tryInvalid("select myString * 2 from myMapEvent.win:length(5)");
            tryInvalid("select String.trim(myInt) from myMapEvent.win:length(5)");
        }

        [Test]
        public virtual void testSendMapNative()
        {
            String statementText = "select * from myMapEvent.win:length(5)";
            EPStatement statement = epService.EPAdministrator.createEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);

            // send EDictionary<String, Object> event
            epService.EPRuntime.SendEvent(map, "myMapEvent");

            Assert.IsTrue(listener.getAndClearIsInvoked());
            Assert.AreEqual(1, listener.LastNewData.Length);
            Assert.AreEqual(map, listener.LastNewData[0].Underlying);
            Assert.AreEqual(3, listener.LastNewData[0]["myInt"]);
            Assert.AreEqual("some string", listener.LastNewData[0]["myString"]);

            // send Map base event
            EDataDictionary mapNoType = new EDataDictionary();
            mapNoType["myInt"] = 4;
            mapNoType["myString"] = "string2";
            epService.EPRuntime.SendEvent(mapNoType, "myMapEvent");

            Assert.IsTrue(listener.getAndClearIsInvoked());
            Assert.AreEqual(1, listener.LastNewData.Length);
            Assert.AreEqual(mapNoType, listener.LastNewData[0].Underlying);
            Assert.AreEqual(4, listener.LastNewData[0]["myInt"]);
            Assert.AreEqual("string2", listener.LastNewData[0]["myString"]);

            // send IDictionary<String, String> event, works too since not querying the fields
            EDataDictionary mapStrings = new EDataDictionary();
            mapStrings.Put("myInt", "5");
            mapStrings.Put("myString", "string3");
            epService.EPRuntime.SendEvent(mapStrings, "myMapEvent");

            Assert.IsTrue(listener.getAndClearIsInvoked());
            Assert.AreEqual("5", listener.LastNewData[0]["myInt"]);
            Assert.AreEqual("string3", listener.LastNewData[0]["myString"]);
        }

        public virtual void tryInvalid(String statementText)
        {
            try
            {
                epService.EPAdministrator.createEQL(statementText);
                Assert.Fail();
            }
            catch (EPException)
            {
                // expected
            }
        }

        private static Log log = LogFactory.GetLog(typeof(TestMapEvent));
    }
}
