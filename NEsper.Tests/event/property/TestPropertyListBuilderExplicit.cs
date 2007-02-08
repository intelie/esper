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
            config.addFieldProperty("f_legVal", "fieldLegacyVal");
            config.addFieldProperty("f_strArr", "fieldStringArray");
            config.addFieldProperty("f_strMap", "fieldMapped");
            config.addFieldProperty("f_legNested", "fieldNested");

            config.addMethodProperty("m_legVal", "readLegacyBeanVal");
            config.addMethodProperty("m_strArr", "readStringArray");
            config.addMethodProperty("m_strInd", "readStringIndexed");
            config.addMethodProperty("m_strMapKeyed", "readMapByKey");
            config.addMethodProperty("m_strMap", "readMap");
            config.addMethodProperty("m_legNested", "readLegacyNested");

            builder = new PropertyListBuilderExplicit(config);
        }

        public const BindingFlags bindings = BindingFlags.Instance | BindingFlags.Public | BindingFlags.Static;

        [Test]
        public virtual void testBuildPropList()
        {
            IList<EventPropertyDescriptor> descList = builder.assessProperties(typeof(SupportLegacyBean));

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

            ArrayAssertionUtil.assertEqualsAnyOrder(expected, descList);
        }

        [Test]
        public virtual void testInvalid()
        {
            tryInvalidField("x", typeof(SupportBean));
            tryInvalidField("IntPrimitive", typeof(SupportBean));

            tryInvalidMethod("x", typeof(SupportBean));
            tryInvalidMethod("IntPrimitive", typeof(SupportBean));
        }

        private void tryInvalidMethod(String methodName, Type clazz)
        {
            ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();
            config.addMethodProperty("name", methodName);
            builder = new PropertyListBuilderExplicit(config);

            try
            {
                builder.assessProperties(clazz);
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
            config.addFieldProperty("name", fieldName);
            builder = new PropertyListBuilderExplicit(config);

            try
            {
                builder.assessProperties(clazz);
            }
            catch (ConfigurationException ex)
            {
                // expected
                log.Debug(ex);
            }
        }

        private static Log log = LogFactory.GetLog(typeof(TestPropertyListBuilderExplicit));
    }
}
