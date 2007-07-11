using System;
using System.IO;
using System.Xml;
using System.Xml.XPath;

using net.esper.client;
using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.xml
{
    public class TestSimpleXMLEventType
    {
        private const String xml =
             "<simpleEvent>\n" +
             "\t<nested1 attr1=\"SAMPLE_ATTR1\">\n" +
             "\t\t<prop1>SAMPLE_V1</prop1>\n" +
             "\t\t<prop2>true</prop2>\n" +
             "\t\t<nested2>\n" +
             "\t\t\t<prop3>3</prop3>\n" +
             "\t\t\t<prop3>4</prop3>\n" +
             "\t\t\t<prop3>5</prop3>\n" +
             "\t\t</nested2>\n" +
             "\t</nested1>\n" +
             "\t<prop4 attr2=\"true\">SAMPLE_V6</prop4>\n" +
             "\t<nested3>\n" +
             "\t\t<nested4 id=\"a\">\n" +
             "\t\t\t<prop5>SAMPLE_V7</prop5>\n" +
             "\t\t\t<prop5>SAMPLE_V8</prop5>\n" +
             "\t\t</nested4>\n" +
             "\t\t<nested4 id=\"b\">\n" +
             "\t\t\t<prop5>SAMPLE_V9</prop5>\n" +
             "\t\t</nested4>\n" +
             "\t\t<nested4 id=\"c\">\n" +
             "\t\t\t<prop5>SAMPLE_V10</prop5>\n" +
             "\t\t\t<prop5>SAMPLE_V11</prop5>\n" +
             "\t\t</nested4>\n" +
             "\t</nested3>\n" +
             "</simpleEvent>";

        private EventBean _event;

        [SetUp]
        public virtual void setUp()
        {
            XmlDocument simpleDoc = new XmlDocument();
            simpleDoc.LoadXml(xml);

            ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
            config.RootElementName = "simpleEvent";
            config.AddXPathProperty("customProp", "count(/simpleEvent/nested3/nested4)", XPathResultType.Number);

            SimpleXMLEventType eventType = new SimpleXMLEventType(config);
            _event = new XMLEventBean(simpleDoc, eventType);
        }

        [Test]
        public void testSimpleProperies()
        {
            Assert.AreEqual("SAMPLE_V6", _event["prop4"]);
        }

        [Test]
        public void testNestedProperties()
        {
            Assert.AreEqual("true", _event["nested1.prop2"]);
        }

        [Test]
        public void testMappedProperties()
        {
            Assert.AreEqual("SAMPLE_V7", _event["nested3.nested4('a').prop5[1]"]);
            Assert.AreEqual("SAMPLE_V11", _event["nested3.nested4('c').prop5[2]"]);
        }

        [Test]
        public void testIndexedProperties()
        {
            Assert.AreEqual("4", _event["nested1.nested2.prop3[2]"]);
            Assert.AreEqual(typeof(String), _event.EventType.GetPropertyType("nested1.nested2.prop3[2]"));
        }

        [Test]
        public void testCustomProperty()
        {
            Assert.AreEqual(typeof(double), _event.EventType.GetPropertyType("customProp"));
            Assert.AreEqual((double)3, _event["customProp"]);
        }
    }
}
