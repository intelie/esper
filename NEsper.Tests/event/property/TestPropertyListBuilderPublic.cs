using System;
using System.Reflection;
using System.Collections.Generic;
using System.ComponentModel;

using net.esper.client;
using net.esper.events;
using net.esper.events.property;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events.property
{
    [TestFixture]
    public class TestPropertyListBuilderPublic
    {
        private PropertyListBuilderPublic builder;

        [SetUp]
        public virtual void setUp()
        {
            ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();

            // add 2 explicit properties, also supported
            config.AddFieldProperty("x", "fieldNested");
            config.AddMethodProperty("y", "readLegacyBeanVal");

            builder = new PropertyListBuilderPublic(config);
        }

        const BindingFlags bindings = BindingFlags.Instance | BindingFlags.Public | BindingFlags.Static;

        public PropertyDescriptor MakeDescriptor(FieldInfo fieldInfo)
        {
            return new SimpleFieldPropertyDescriptor(fieldInfo.Name, fieldInfo);
        }

        public PropertyDescriptor MakeDescriptor(MethodInfo methodInfo)
        {
            return new SimpleAccessorPropertyDescriptor(methodInfo.Name, methodInfo);
        }

        [Test]
        public virtual void testBuildPropList()
        {
            IList<EventPropertyDescriptor> descList = builder.AssessProperties(typeof(SupportLegacyBean));

            List<EventPropertyDescriptor> expected = new List<EventPropertyDescriptor>();
            expected.Add(new EventPropertyDescriptor(
                "fieldLegacyVal",
                "fieldLegacyVal",
                MakeDescriptor(typeof(SupportLegacyBean).GetField("fieldLegacyVal", bindings)),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "fieldStringArray",
                "fieldStringArray",
                MakeDescriptor(typeof(SupportLegacyBean).GetField("fieldStringArray", bindings)),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "fieldMapped",
                "fieldMapped",
                MakeDescriptor(typeof(SupportLegacyBean).GetField("fieldMapped", bindings)),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "fieldNested",
                "fieldNested",
                MakeDescriptor(typeof(SupportLegacyBean).GetField("fieldNested", bindings)),
                EventPropertyType.SIMPLE));

            expected.Add(new EventPropertyDescriptor(
                "readLegacyBeanVal",
                "readLegacyBeanVal",
                MakeDescriptor(typeof(SupportLegacyBean).GetMethod("readLegacyBeanVal")),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "readStringArray",
                "readStringArray",
                MakeDescriptor(typeof(SupportLegacyBean).GetMethod("readStringArray")),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "readStringIndexed",
                "readStringIndexed",
                typeof(SupportLegacyBean).GetMethod("readStringIndexed", new Type[] { typeof(int) }),
                EventPropertyType.INDEXED));
            expected.Add(new EventPropertyDescriptor(
                "readMapByKey",
                "readMapByKey",
                typeof(SupportLegacyBean).GetMethod("readMapByKey", new Type[] { typeof(String) }),
                EventPropertyType.MAPPED));
            expected.Add(new EventPropertyDescriptor(
                "readMap",
                "readMap",
                MakeDescriptor(typeof(SupportLegacyBean).GetMethod("readMap")),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "readLegacyNested",
                "readLegacyNested",
                MakeDescriptor(typeof(SupportLegacyBean).GetMethod("readLegacyNested")),
                EventPropertyType.SIMPLE));

            expected.Add(new EventPropertyDescriptor(
                "x",
                "x",
                MakeDescriptor(typeof(SupportLegacyBean).GetField("fieldNested", bindings)),
                EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor(
                "y",
                "y",
                MakeDescriptor(typeof(SupportLegacyBean).GetMethod("readLegacyBeanVal")),
                EventPropertyType.SIMPLE));

            ArrayAssertionUtil.AreEqualAnyOrder( expected, descList ) ;
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
