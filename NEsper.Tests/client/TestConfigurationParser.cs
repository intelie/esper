using System;
using System.IO;
using System.Net;
using System.Xml;
using System.Xml.XPath;

using NUnit.Core;
using NUnit.Framework;

using net.esper.compat;

namespace net.esper.client
{
    [TestFixture]
    public class TestConfigurationParser
    {
        private Configuration config;

        [SetUp]
        public virtual void setUp()
        {
            config = new Configuration();
        }

        [Test]
        public virtual void testConfigureFromStream()
        {
            Uri url = ResourceManager.ResolveResourceURL(TestConfiguration.ESPER_TEST_CONFIG);
            Assert.IsNotNull(url, "Unable to resolve configuration resource");

            Stream stream = null;

            try
            {
                stream = WebRequest.Create(url).GetResponse().GetResponseStream();
                Assert.IsNotNull(stream, "Unable to allocate stream from resource URL");
            }
            catch (Exception)
            {
                Assert.Fail("Unable to open configuration resource");
            }

            try
            {
                ConfigurationParser.DoConfigure(config, stream, url.ToString());
            }
            finally
            {
                stream.Close();
            }

            assertFileConfig(config);
        }

        protected internal static void assertFileConfig(Configuration config)
        {
            // assert alias for class
            Assert.AreEqual(3, config.EventTypeAliases.Count);
            Assert.AreEqual("com.mycompany.myapp.MySampleEventOne", config.EventTypeAliases.Fetch("MySampleEventOne"));
            Assert.AreEqual("com.mycompany.myapp.MySampleEventTwo", config.EventTypeAliases.Fetch("MySampleEventTwo"));
            Assert.AreEqual("com.mycompany.package.MyLegacyTypeEvent", config.EventTypeAliases.Fetch("MyLegacyTypeEvent"));

            // assert auto imports
            Assert.AreEqual(2, config.Imports.Count);
            Assert.AreEqual("com.mycompany.myapp.*", config.Imports[0]);
            Assert.AreEqual("com.mycompany.myapp.ClassOne", config.Imports[1]);

            // assert XML DOM - no schema
            Assert.AreEqual(2, config.EventTypesXMLDOM.Count);
            ConfigurationEventTypeXMLDOM noSchemaDesc = config.EventTypesXMLDOM.Fetch("MyNoSchemaXMLEventAlias");
            Assert.AreEqual("MyNoSchemaEvent", noSchemaDesc.RootElementName);
            Assert.AreEqual("/myevent/element1", noSchemaDesc.XPathProperties.Fetch("element1").XPath);
            Assert.AreEqual(XPathResultType.Number, noSchemaDesc.XPathProperties.Fetch("element1").Type);

            // assert XML DOM - with schema
            ConfigurationEventTypeXMLDOM schemaDesc = config.EventTypesXMLDOM.Fetch("MySchemaXMLEventAlias");
            Assert.AreEqual("MySchemaEvent", schemaDesc.RootElementName);
            Assert.AreEqual("MySchemaXMLEvent.xsd", schemaDesc.SchemaResource);
            Assert.AreEqual("samples:schemas:simpleSchema", schemaDesc.RootElementNamespace);
            Assert.AreEqual("default-name-space", schemaDesc.DefaultNamespace);
            Assert.AreEqual("/myevent/element1", schemaDesc.XPathProperties.Fetch("element1").XPath);
            Assert.AreEqual(XPathResultType.Number, schemaDesc.XPathProperties.Fetch("element1").Type);
            Assert.AreEqual(1, schemaDesc.NamespacePrefixes.Count);
            Assert.AreEqual("samples:schemas:simpleSchema", schemaDesc.NamespacePrefixes.Fetch("ss"));

            // assert mapped events
            Assert.AreEqual(1, config.EventTypesMapEvents.Count);
            Assert.IsTrue(config.EventTypesMapEvents.ContainsKey("MyMapEvent"));
            EDictionary<String, String> expectedProps = new EHashDictionary<String, String>();
            expectedProps.Put("myInt", "int");
            expectedProps.Put("myString", "string");
            Assert.AreEqual(expectedProps, config.EventTypesMapEvents.Fetch("MyMapEvent"));

            // assert legacy type declaration
            Assert.AreEqual(1, config.EventTypesLegacy.Count);
            ConfigurationEventTypeLegacy legacy = config.EventTypesLegacy.Fetch("MyLegacyTypeEvent");
            Assert.AreEqual(ConfigurationEventTypeLegacy.CodeGenerationEnum.ENABLED, legacy.CodeGeneration);
            Assert.AreEqual(ConfigurationEventTypeLegacy.AccessorStyleEnum.PUBLIC, legacy.AccessorStyle);
            Assert.AreEqual(1, legacy.FieldProperties.Count);
            Assert.AreEqual("myFieldName", legacy.FieldProperties[0].AccessorFieldName);
            Assert.AreEqual("myfieldprop", legacy.FieldProperties[0].Name);
            Assert.AreEqual(1, legacy.MethodProperties.Count);
            Assert.AreEqual("myAccessorMethod", legacy.MethodProperties[0].AccessorMethodName);
            Assert.AreEqual("mymethodprop", legacy.MethodProperties[0].Name);
        }
    }
}
