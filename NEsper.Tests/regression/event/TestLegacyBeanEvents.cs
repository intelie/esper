using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.events
{
    [TestFixture]
    public class TestLegacyBeanEvents
    {
        private SupportLegacyBean legacyBean;
        private EPServiceProvider epService;

        [SetUp]
        public virtual void setUp()
        {
            EDictionary<String, String> mappedProperty = new HashDictionary<String, String>();
            mappedProperty.Put("key1", "value1");
            mappedProperty.Put("key2", "value2");
            legacyBean = new SupportLegacyBean("leg", new String[] { "a", "b" }, mappedProperty, "nest");
        }

        [TearDown]
        public virtual void tearDown()
        {
            epService = null;
            legacyBean = null;
        }

        [Test]
        public void testPublicAccessors()
        {
            tryPublicAccessors(ConfigurationEventTypeLegacy.CodeGenerationEnum.ENABLED);
        }

        [Test]
        public void testPublicAccessorsNoCodeGen()
        {
            tryPublicAccessors(ConfigurationEventTypeLegacy.CodeGenerationEnum.DISABLED);
        }

        [Test]
        public void testExplicitOnly()
        {
            tryExplicitOnlyAccessors(ConfigurationEventTypeLegacy.CodeGenerationEnum.ENABLED);
        }

        [Test]
        public void testExplicitOnlyNoCodeGen()
        {
            tryExplicitOnlyAccessors(ConfigurationEventTypeLegacy.CodeGenerationEnum.DISABLED);
        }

        [Test]
        public void testJavaBeanAccessor()
        {
            tryJavaBeanAccessor(ConfigurationEventTypeLegacy.CodeGenerationEnum.ENABLED);
        }

        [Test]
        public void testJavaBeanAccessorNoCodeGen()
        {
            tryJavaBeanAccessor(ConfigurationEventTypeLegacy.CodeGenerationEnum.DISABLED);
        }

        [Test]
        public void testFinalClass()
        {
            tryFinalClass(ConfigurationEventTypeLegacy.CodeGenerationEnum.ENABLED);
        }

        [Test]
        public void testFinalClassNoCodeGen()
        {
            tryFinalClass(ConfigurationEventTypeLegacy.CodeGenerationEnum.DISABLED);
        }

        private void tryPublicAccessors(ConfigurationEventTypeLegacy.CodeGenerationEnum codeGeneration)
        {
            Configuration config = new Configuration();

            ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.PUBLIC;
            legacyDef.CodeGeneration = codeGeneration;
            legacyDef.AddFieldProperty("explicitFSimple", "fieldLegacyVal");
            legacyDef.AddFieldProperty("explicitFIndexed", "fieldStringArray");
            legacyDef.AddFieldProperty("explicitFNested", "fieldNested");
            legacyDef.AddMethodProperty("explicitMSimple", "readLegacyBeanVal");
            legacyDef.AddMethodProperty("explicitMArray", "readStringArray");
            legacyDef.AddMethodProperty("explicitMIndexed", "readStringIndexed");
            legacyDef.AddMethodProperty("explicitMMapped", "readMapByKey");
            config.AddEventTypeAlias("MyLegacyEvent", typeof(SupportLegacyBean).FullName, legacyDef);

            legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.PUBLIC;
            legacyDef.CodeGeneration = ConfigurationEventTypeLegacy.CodeGenerationEnum.DISABLED;
            config.AddEventTypeAlias("MyLegacyNestedEvent", typeof(SupportLegacyBean.LegacyNested).FullName, legacyDef);

            epService = EPServiceProviderManager.GetProvider(this.GetType().Name + ".test1" + codeGeneration, config);
            epService.Initialize();

            String statementText =
                "select " + 
                "fieldLegacyVal as fieldSimple," + 
                "fieldStringArray as fieldArr," + 
                "fieldStringArray[1] as fieldArrIndexed," + 
                "fieldMapped as fieldMap," + 
                "fieldNested as fieldNested," + 
                "fieldNested.readNestedValue as fieldNestedVal," + 
                "readLegacyBeanVal as simple," + 
                "readLegacyNested as nestedObject," + 
                "readLegacyNested.readNestedValue as nested," +
                "readStringArray[0] as array," +
                "readStringIndexed[1] as indexed," + 
                "readMapByKey('key1') as mapped," +
                "readMap as mapItself," +
                "explicitFSimple, " + 
                "explicitFIndexed[0], " + 
                "explicitFNested, " +
                "explicitMSimple, " + 
                "explicitMArray[0], " +
                "explicitMIndexed[1], " +
                "explicitMMapped('key2')" +
                " from MyLegacyEvent.win:length(5)";

            EPStatement statement = epService.EPAdministrator.CreateEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);

            EventType eventType = statement.EventType;
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("fieldSimple"));
            Assert.AreEqual(typeof(String[]), eventType.GetPropertyType("fieldArr"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("fieldArrIndexed"));
            Assert.AreEqual(typeof(IDictionary<String, String>), eventType.GetPropertyType("fieldMap"));
            Assert.AreEqual(typeof(SupportLegacyBean.LegacyNested), eventType.GetPropertyType("fieldNested"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("fieldNestedVal"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("simple"));
            Assert.AreEqual(typeof(SupportLegacyBean.LegacyNested), eventType.GetPropertyType("nestedObject"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("nested"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("array"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("indexed"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("mapped"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("explicitFSimple"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("explicitFIndexed[0]"));
            Assert.AreEqual(typeof(SupportLegacyBean.LegacyNested), eventType.GetPropertyType("explicitFNested"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("explicitMSimple"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("explicitMArray[0]"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("explicitMIndexed[1]"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("explicitMMapped('key2')"));

            epService.EPRuntime.SendEvent(legacyBean);

            Assert.AreEqual(legacyBean.fieldLegacyVal, listener.LastNewData[0]["fieldSimple"]);
            Assert.AreEqual(legacyBean.fieldStringArray, listener.LastNewData[0]["fieldArr"]);
            Assert.AreEqual(legacyBean.fieldStringArray[1], listener.LastNewData[0]["fieldArrIndexed"]);
            Assert.AreEqual(legacyBean.fieldMapped, listener.LastNewData[0]["fieldMap"]);
            Assert.AreEqual(legacyBean.fieldNested, listener.LastNewData[0]["fieldNested"]);
            Assert.AreEqual(legacyBean.fieldNested.readNestedValue(), listener.LastNewData[0]["fieldNestedVal"]);

            Assert.AreEqual(legacyBean.readLegacyBeanVal(), listener.LastNewData[0]["simple"]);
            Assert.AreEqual(legacyBean.readLegacyNested(), listener.LastNewData[0]["nestedObject"]);
            Assert.AreEqual(legacyBean.readLegacyNested().readNestedValue(), listener.LastNewData[0]["nested"]);
            Assert.AreEqual(legacyBean.readStringIndexed(0), listener.LastNewData[0]["array"]);
            Assert.AreEqual(legacyBean.readStringIndexed(1), listener.LastNewData[0]["indexed"]);
            Assert.AreEqual(legacyBean.readMapByKey("key1"), listener.LastNewData[0]["mapped"]);
            Assert.AreEqual(legacyBean.readMap(), listener.LastNewData[0]["mapItself"]);

            Assert.AreEqual(legacyBean.readLegacyBeanVal(), listener.LastNewData[0]["explicitFSimple"]);
            Assert.AreEqual(legacyBean.readLegacyBeanVal(), listener.LastNewData[0]["explicitMSimple"]);
            Assert.AreEqual(legacyBean.readLegacyNested(), listener.LastNewData[0]["explicitFNested"]);
            Assert.AreEqual(legacyBean.readStringIndexed(0), listener.LastNewData[0]["explicitFIndexed[0]"]);
            Assert.AreEqual(legacyBean.readStringIndexed(0), listener.LastNewData[0]["explicitMArray[0]"]);
            Assert.AreEqual(legacyBean.readStringIndexed(1), listener.LastNewData[0]["explicitMIndexed[1]"]);
            Assert.AreEqual(legacyBean.readMapByKey("key2"), listener.LastNewData[0]["explicitMMapped('key2')"]);
        }

        private void tryExplicitOnlyAccessors(ConfigurationEventTypeLegacy.CodeGenerationEnum codeGeneration)
        {
            Configuration config = new Configuration();

            ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.EXPLICIT;
            legacyDef.CodeGeneration = codeGeneration;
            legacyDef.AddFieldProperty("explicitFNested", "fieldNested");
            legacyDef.AddMethodProperty("explicitMNested", "readLegacyNested");
            config.AddEventTypeAlias("MyLegacyEvent", typeof(SupportLegacyBean).FullName, legacyDef);

            legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.EXPLICIT;
            legacyDef.CodeGeneration = codeGeneration;
            legacyDef.AddFieldProperty("fieldNestedClassValue", "fieldNestedValue");
            legacyDef.AddMethodProperty("readNestedClassValue", "readNestedValue");
            config.AddEventTypeAlias("MyLegacyNestedEvent", typeof(SupportLegacyBean.LegacyNested).FullName, legacyDef);

            legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.EXPLICIT;
            legacyDef.CodeGeneration = codeGeneration;
            config.AddEventTypeAlias("MySupportBean", typeof(SupportBean).FullName, legacyDef);

            epService = EPServiceProviderManager.GetProvider(this.GetType().Name + ".test2" + codeGeneration, config);
            epService.Initialize();

            String statementText =
                "select " +
                "explicitFNested.fieldNestedClassValue as fnested, " +
                "explicitMNested.readNestedClassValue as mnested" + 
                " from MyLegacyEvent.win:length(5)";

            EPStatement statement = epService.EPAdministrator.CreateEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);

            EventType eventType = statement.EventType;
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("fnested"));
            Assert.AreEqual(typeof(String), eventType.GetPropertyType("mnested"));

            epService.EPRuntime.SendEvent(legacyBean);

            Assert.AreEqual(legacyBean.fieldNested.readNestedValue(), listener.LastNewData[0]["fnested"]);
            Assert.AreEqual(legacyBean.fieldNested.readNestedValue(), listener.LastNewData[0]["mnested"]);

            try
            {
                // invalid statement, JavaBean-style getters not exposed
                statementText = "select intPrimitive from MySupportBean.win:length(5)";
                epService.EPAdministrator.CreateEQL(statementText);
            }
            catch (EPStatementException ex)
            {
                // expected
            }
        }

        public virtual void tryJavaBeanAccessor(ConfigurationEventTypeLegacy.CodeGenerationEnum codeGeneration)
        {
            Configuration config = new Configuration();

            ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.NATIVE;
            legacyDef.CodeGeneration = codeGeneration;
            legacyDef.AddFieldProperty("explicitFInt", "fieldIntPrimitive");
            legacyDef.AddMethodProperty("explicitMGetInt", "getIntPrimitive");
            legacyDef.AddMethodProperty("explicitMReadInt", "readIntPrimitive");
            config.AddEventTypeAlias("MyLegacyEvent", typeof(SupportLegacyBeanInt).FullName, legacyDef);

            epService = EPServiceProviderManager.GetProvider(this.GetType().Name + ".test3" + codeGeneration, config);
            epService.Initialize();

            String statementText = "select intPrimitive, explicitFInt, explicitMGetInt, explicitMReadInt " + " from MyLegacyEvent.win:length(5)";

            EPStatement statement = epService.EPAdministrator.CreateEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);
            EventType eventType = statement.EventType;

            SupportLegacyBeanInt _event = new SupportLegacyBeanInt(10);
            epService.EPRuntime.SendEvent(_event);

            string[] _items = new string[] {
                "intPrimitive",
                "explicitFInt",
                "explicitMGetInt",
                "explicitMReadInt"
            };

            foreach (String name in _items)
            {
                Assert.AreEqual(typeof(int), eventType.GetPropertyType(name));
                Assert.AreEqual(10, listener.LastNewData[0][name]);
            }
        }

        private void tryFinalClass(ConfigurationEventTypeLegacy.CodeGenerationEnum codeGeneration)
        {
            Configuration config = new Configuration();
            ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
            legacyDef.AccessorStyle = ConfigurationEventTypeLegacy.AccessorStyleEnum.NATIVE;
            legacyDef.CodeGeneration = codeGeneration;
            config.AddEventTypeAlias("MyFinalEvent", typeof(SupportBeanFinal).FullName, legacyDef);

            epService = EPServiceProviderManager.GetProvider(this.GetType().Name + ".test4" + codeGeneration);
            epService.Initialize();

            String statementText = "select intPrimitive " + "from " + typeof(SupportBeanFinal).FullName + ".win:length(5)";

            EPStatement statement = epService.EPAdministrator.CreateEQL(statementText);
            SupportUpdateListener listener = new SupportUpdateListener();
            statement.AddListener(listener);

            SupportBeanFinal _event = new SupportBeanFinal(10);
            epService.EPRuntime.SendEvent(_event);
            Assert.AreEqual(10, listener.LastNewData[0]["intPrimitive"]);
        }
    }
}
