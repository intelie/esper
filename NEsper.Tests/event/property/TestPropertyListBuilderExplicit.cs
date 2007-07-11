using System;
using System.Collections.Generic;
using System.Reflection;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events.property
{
    [TestFixture]
    public class TestPropertyListBuilderExplicit
    {
        private PropertyListBuilderExplicit builder;

        [SetUp]
        public virtual void setUp()
        {
            ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
            config.AddFieldProperty("f_legVal", "fieldLegacyVal");
            config.AddFieldProperty("f_strArr", "fieldStringArray");
            config.AddFieldProperty("f_strMap", "fieldMapped");
            config.AddFieldProperty("f_legNested", "fieldNested");

            config.AddMethodProperty("m_legVal", "readLegacyBeanVal");
            config.AddMethodProperty("m_strArr", "readStringArray");
            config.AddMethodProperty("m_strInd", "readStringIndexed");
            config.AddMethodProperty("m_strMapKeyed", "readMapByKey");
            config.AddMethodProperty("m_strMap", "readMap");
            config.AddMethodProperty("m_legNested", "readLegacyNested");

            builder = new PropertyListBuilderExplicit(config);
        }

        public const BindingFlags bindings = BindingFlags.Instance | BindingFlags.Public | BindingFlags.Static;

        [Test]
        public void testBuildPropList()
        {
            IList<EventPropertyDescriptor> descList = builder.AssessProperties(typeof(SupportLegacyBean));

            IList<EventPropertyDescriptor> expected = new List<EventPropertyDescriptor>();
            expected.Add(new EventPropertyDescriptor("f_legVal", "f_legVal", typeof(SupportLegacyBean).GetField("fieldLegacyVal", bindings), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("f_strArr", "f_strArr", typeof(SupportLegacyBean).GetField("fieldStringArray", bindings), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("f_strMap", "f_strMap", typeof(SupportLegacyBean).GetField("fieldMapped", bindings), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("f_legNested", "f_legNested", typeof(SupportLegacyBean).GetField("fieldNested", bindings), EventPropertyType.SIMPLE));

            expected.Add(new EventPropertyDescriptor("m_legVal", "m_legVal", typeof(SupportLegacyBean).GetMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("m_strArr", "m_strArr", typeof(SupportLegacyBean).GetMethod("readStringArray"), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("m_strInd", "m_strInd", typeof(SupportLegacyBean).GetMethod("readStringIndexed", new Type[] { typeof(int) }), EventPropertyType.INDEXED));
            expected.Add(new EventPropertyDescriptor("m_strMapKeyed", "m_strMapKeyed", typeof(SupportLegacyBean).GetMethod("readMapByKey", new Type[] { typeof(String) }), EventPropertyType.MAPPED));
            expected.Add(new EventPropertyDescriptor("m_strMap", "m_strMap", typeof(SupportLegacyBean).GetMethod("readMap"), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("m_legNested", "m_legNested", typeof(SupportLegacyBean).GetMethod("readLegacyNested"), EventPropertyType.SIMPLE));

            ArrayAssertionUtil.AreEqualAnyOrder(expected, descList);
        }

        [Test]
        public void testInvalid()
        {
            tryInvalidField("x", typeof(SupportBean));
            tryInvalidField("intPrimitive", typeof(SupportBean));

            tryInvalidMethod("x", typeof(SupportBean));
            tryInvalidMethod("intPrimitive", typeof(SupportBean));
        }

        private void tryInvalidMethod(String methodName, Type clazz)
        {
            ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
            config.AddMethodProperty("name", methodName);
            builder = new PropertyListBuilderExplicit(config);

            try
            {
                builder.AssessProperties(clazz);
            }
            catch (ConfigurationException ex)
            {
                // expected
                log.Debug(ex);
            }
        }

        private void tryInvalidField(String fieldName, Type clazz)
        {
            ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
            config.AddFieldProperty("name", fieldName);
            builder = new PropertyListBuilderExplicit(config);

            try
            {
                builder.AssessProperties(clazz);
            }
            catch (ConfigurationException ex)
            {
                // expected
                log.Debug(ex);
            }
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
