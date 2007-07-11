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
    public class TestPropertyListBuilderJavaBean
    {
        private PropertyListBuilderNative builder;

        [SetUp]
        public virtual void setUp()
        {
            ConfigurationEventTypeLegacy config = new ConfigurationEventTypeLegacy();

            // add 2 explicit properties, also supported
            config.AddFieldProperty("x", "fieldNested");
            config.AddMethodProperty("y", "readLegacyBeanVal");

            builder = new PropertyListBuilderNative(config);
        }

        [Test]
        public void testBuildPropList()
        {
            IList<EventPropertyDescriptor> descList = builder.AssessProperties(typeof(SupportLegacyBean));

            IList<EventPropertyDescriptor> expected = new List<EventPropertyDescriptor>();
            expected.Add(new EventPropertyDescriptor("x", "x", typeof(SupportLegacyBean).GetField("fieldNested"), EventPropertyType.SIMPLE));
            expected.Add(new EventPropertyDescriptor("y", "y", typeof(SupportLegacyBean).GetMethod("readLegacyBeanVal"), EventPropertyType.SIMPLE));
            ArrayAssertionUtil.AreEqualAnyOrder(expected, descList);
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
