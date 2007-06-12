// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Data;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.IO;
using System.Xml;
using System.Xml.XPath;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.client
{
    /// <summary>
    /// Parser for configuration XML.
    /// </summary>

    public class ConfigurationParser
    {
        /// <summary>
        /// Use the configuration specified in the given input stream.
        /// </summary>
        /// <param name="configuration">is the configuration object to populate</param>
        /// <param name="stream">The stream.</param>
        /// <param name="resourceName">The name to use in warning/error messages</param>
        /// <throws>  net.esper.client.EPException </throws>

        public static void DoConfigure(Configuration configuration, Stream stream, String resourceName)
        {
            XmlDocument document = null;

            try
            {
                document = new XmlDocument();
                document.Load(stream);
            }
            catch (XmlException ex)
            {
                throw new EPException("Could not parse configuration: " + resourceName, ex);
            }
            catch (IOException ex)
            {
                throw new EPException("Could not read configuration: " + resourceName, ex);
            }
            finally
            {
                try
                {
                    stream.Close();
                }
                catch (IOException ioe)
                {
                    ConfigurationParser.log.Warn("could not close input stream for: " + resourceName, ioe);
                }
            }

            DoConfigure(configuration, document);
        }

        /// <summary> Parse the W3C DOM document.</summary>
        /// <param name="configuration">is the configuration object to populate
        /// </param>
        /// <param name="doc">to parse
        /// </param>
        /// <throws>  net.esper.client.EPException </throws>
		public static void DoConfigure( Configuration configuration, XmlDocument doc )
        {
            XmlElement root = (XmlElement)doc.DocumentElement;

            HandleEventTypes(configuration, root);
            HandleAutoImports(configuration, root);
            HandleDatabaseRefs(configuration, root);
			HandlePlugInView(configuration, root);
			HandlePlugInAggregation(configuration, root);
			HandlePlugInPatternObjects(configuration, root);
			HandleAdapterLoaders(configuration, root);
        }

        private static void HandleEventTypes(Configuration configuration, XmlElement parentElement)
        {
            XmlNodeList nodes = parentElement.GetElementsByTagName("event-type");
            for (int i = 0; i < nodes.Count; i++)
            {
                String name = ((XmlAttributeCollection)nodes.Item(i).Attributes).GetNamedItem("alias").InnerText;
                XmlNode classNode = ((XmlAttributeCollection)nodes.Item(i).Attributes).GetNamedItem("class");

                String optionalClassName = null;
                if (classNode != null)
                {
                    optionalClassName = classNode.InnerText;
                    configuration.AddEventTypeAlias(name, optionalClassName);
                }
                HandleSubElement(name, optionalClassName, configuration, nodes.Item(i));
            }
        }

        private static void HandleSubElement(String aliasName, String optionalClassName, Configuration configuration, XmlNode parentNode)
        {
            IEnumerator<XmlElement> eventTypeNodeEnumerator = CreateElementEnumerator(parentNode.ChildNodes);
            while (eventTypeNodeEnumerator.MoveNext())
            {
                XmlElement eventTypeElement = eventTypeNodeEnumerator.Current;
                String nodeName = eventTypeElement.Name;
                if (nodeName.Equals("xml-dom"))
                {
                    HandleXMLDOM(aliasName, configuration, eventTypeElement);
                }
                else if (nodeName.Equals("java-util-map"))
                {
                    HandleMap(aliasName, configuration, eventTypeElement);
                }
                else if (nodeName.Equals("legacy-type"))
                {
                    HandleLegacy(aliasName, optionalClassName, configuration, eventTypeElement);
                }
            }
        }

        private static void HandleMap(String aliasName, Configuration configuration, XmlElement eventTypeElement)
        {
            Properties propertyTypeNames = new Properties();
            XmlNodeList propertyList = eventTypeElement.GetElementsByTagName("map-property");
            foreach( XmlNode propertyNode in propertyList )
            {
                String name = propertyNode.Attributes.GetNamedItem("name").InnerText;
                String type = propertyNode.Attributes.GetNamedItem("class").InnerText;
                propertyTypeNames[name] = type;
            }
            configuration.AddEventTypeAlias(aliasName, propertyTypeNames);
        }

        private static void HandleXMLDOM(String aliasName, Configuration configuration, XmlElement xmldomElement)
        {
            String rootElementName = xmldomElement.Attributes.GetNamedItem("root-element-name").InnerText;
            String rootElementNamespace = GetOptionalAttribute(xmldomElement, "root-element-namespace");
            String schemaResource = GetOptionalAttribute(xmldomElement, "schema-resource");
            String defaultNamespace = GetOptionalAttribute(xmldomElement, "default-namespace");

            ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
            xmlDOMEventTypeDesc.RootElementName = rootElementName;
            xmlDOMEventTypeDesc.SchemaResource = schemaResource;
            xmlDOMEventTypeDesc.RootElementNamespace = rootElementNamespace;
            xmlDOMEventTypeDesc.DefaultNamespace = defaultNamespace;
            configuration.AddEventTypeAlias(aliasName, xmlDOMEventTypeDesc);

            IEnumerator<XmlElement> propertyNodeEnumerator = CreateElementEnumerator(xmldomElement.ChildNodes);
            while (propertyNodeEnumerator.MoveNext())
            {
                XmlElement propertyElement = propertyNodeEnumerator.Current;
                if (propertyElement.Name.Equals("namespace-prefix"))
                {
                    String prefix = propertyElement.Attributes.GetNamedItem("prefix").InnerText;
                    String namespace_ = propertyElement.Attributes.GetNamedItem("namespace").InnerText;
                    xmlDOMEventTypeDesc.AddNamespacePrefix(prefix, namespace_);
                }
                if (propertyElement.Name.Equals("xpath-property"))
                {
                    String propertyName = propertyElement.Attributes.GetNamedItem("property-name").InnerText;
                    String xPath = propertyElement.Attributes.GetNamedItem("xpath").InnerText;
                    String propertyType = propertyElement.Attributes.GetNamedItem("type").InnerText;

                    XPathResultType xpathConstantType = XPathResultType.Any;
                    if (propertyType.Equals("NUMBER", StringComparison.InvariantCultureIgnoreCase))
                    {
                        xpathConstantType = XPathResultType.Number;
                    }
                    else if (propertyType.Equals("STRING", StringComparison.InvariantCultureIgnoreCase))
                    {
                        xpathConstantType = XPathResultType.String;
                    }
                    else if (propertyType.Equals("BOOLEAN", StringComparison.InvariantCultureIgnoreCase))
                    {
                        xpathConstantType = XPathResultType.Boolean;
                    }
                    else
                    {
                        throw new ArgumentException("Invalid xpath property type for property '" + propertyName + "' and type '" + propertyType + "'");
                    }
                    
                    xmlDOMEventTypeDesc.AddXPathProperty(propertyName, xPath, xpathConstantType);
                }
            }
        }

        private static void HandleLegacy(String aliasName, String className, Configuration configuration, XmlElement xmldomElement)
        {
            // Class name is required for legacy classes
            if (className == null)
            {
                throw new ConfigurationException("Required class name not supplied for legacy type definition");
            }

            String accessorStyle = xmldomElement.Attributes.GetNamedItem("accessor-style").InnerText;
            String codeGeneration = xmldomElement.Attributes.GetNamedItem("code-generation").InnerText;

            ConfigurationEventTypeLegacy legacyDesc = new ConfigurationEventTypeLegacy();
            legacyDesc.AccessorStyle = (ConfigurationEventTypeLegacy.AccessorStyleEnum) Enum.Parse( typeof( ConfigurationEventTypeLegacy.AccessorStyleEnum ), accessorStyle, true ) ;
            legacyDesc.CodeGeneration = (ConfigurationEventTypeLegacy.CodeGenerationEnum) Enum.Parse( typeof( ConfigurationEventTypeLegacy.CodeGenerationEnum ), codeGeneration, true ) ;
            configuration.AddEventTypeAlias(aliasName, className, legacyDesc);

            IEnumerator<XmlElement> propertyNodeEnumerator = CreateElementEnumerator(xmldomElement.ChildNodes);
            while (propertyNodeEnumerator.MoveNext())
            {
                XmlElement propertyElement = propertyNodeEnumerator.Current;
                if (propertyElement.Name.Equals("method-property"))
                {
                    String name = propertyElement.Attributes.GetNamedItem("name").InnerText;
                    String method = propertyElement.Attributes.GetNamedItem("accessor-method").InnerText;
                    legacyDesc.AddMethodProperty(name, method);
                }
                else if (propertyElement.Name.Equals("field-property"))
                {
                    String name = propertyElement.Attributes.GetNamedItem("name").InnerText;
                    String field = propertyElement.Attributes.GetNamedItem("accessor-field").InnerText;
                    legacyDesc.AddFieldProperty(name, field);
                }
                else
                {
                    throw new ConfigurationException("Invalid node " + propertyElement.Name + " encountered while parsing legacy type definition");
                }
            }
        }

        private static void HandleAutoImports(Configuration configuration, XmlElement parentNode)
        {
            XmlNodeList importNodes = parentNode.GetElementsByTagName("auto-import");
            for (int i = 0; i < importNodes.Count; i++)
            {
                String name = importNodes.Item(i).Attributes.GetNamedItem("import-name").InnerText;
                configuration.AddImport(name);
            }
        }

        private static void HandleDatabaseRefs(Configuration configuration, XmlElement parentNode)
        {
            XmlNodeList dbRefNodes = parentNode.GetElementsByTagName("database-reference");
            for (int i = 0; i < dbRefNodes.Count; i++)
            {
                String name = dbRefNodes.Item(i).Attributes.GetNamedItem("name").InnerText;
                ConfigurationDBRef configDBRef = new ConfigurationDBRef();
                configuration.AddDatabaseReference(name, configDBRef);

                IEnumerator<XmlElement> nodeEnumerator = CreateElementEnumerator(dbRefNodes.Item(i).ChildNodes);
                while (nodeEnumerator.MoveNext())
                {
                    XmlElement subElement = nodeEnumerator.Current;
                    if (subElement.Name.Equals("provider-connection"))
                    {
                        ConnectionStringSettings settings = new ConnectionStringSettings() ;
                        settings.Name = subElement.Attributes.GetNamedItem("provider").InnerText;
                        settings.ProviderName = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("provider").InnerText;
                        settings.ConnectionString = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("connection-string").InnerText;
                        configDBRef.SetDatabaseProviderConnection(settings);
                    }
                    else if (subElement.Name.Equals("connection-lifecycle"))
                    {
                        String value = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("value").InnerText;
                        configDBRef.ConnectionLifecycle = (ConnectionLifecycleEnum) Enum.Parse( typeof( ConnectionLifecycleEnum ), value, true );
                    }
                    else if (subElement.Name.Equals("connection-settings"))
                    {
                        if (((XmlAttributeCollection)subElement.Attributes).GetNamedItem("auto-commit") != null)
                        {
                            String autoCommit = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("auto-commit").InnerText;
                            configDBRef.ConnectionAutoCommit = Boolean.Parse(autoCommit);
                        }
                        if (((XmlAttributeCollection)subElement.Attributes).GetNamedItem("transaction-isolation") != null)
                        {
                            String transactionIsolation = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("transaction-isolation").InnerText;
                            configDBRef.ConnectionTransactionIsolation = (IsolationLevel) Enum.Parse(typeof(IsolationLevel), transactionIsolation);
                        }
                        if (((XmlAttributeCollection)subElement.Attributes).GetNamedItem("catalog") != null)
                        {
                            String catalog = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("catalog").InnerText;
                            configDBRef.ConnectionCatalog = catalog;
                        }
                        if (((XmlAttributeCollection)subElement.Attributes).GetNamedItem("read-only") != null)
                        {
                            String readOnly = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("read-only").InnerText;
                            configDBRef.ConnectionReadOnly = Boolean.Parse(readOnly);
                        }
                    }
                    else if (subElement.Name.Equals("expiry-time-cache"))
                    {
                        String maxAge = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("max-age-seconds").InnerText;
                        String purgeInterval = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("purge-interval-seconds").InnerText;
                        configDBRef.SetExpiryTimeCache(Double.Parse(maxAge), Double.Parse(purgeInterval));
                    }
                    else if (subElement.Name.Equals("lru-cache"))
                    {
                        String size = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("size").InnerText;
                        configDBRef.LRUCache = Int32.Parse(size);
                    }
                }
            }
        }
		
	    private static void HandlePlugInView(Configuration configuration, XmlElement parentElement)
	    {
	        XmlNodeList nodes = parentElement.GetElementsByTagName("plugin-view");
            foreach (XmlNode node in nodes)
            {
                String _namespace = node.Attributes.GetNamedItem("namespace").InnerText;
                String name = node.Attributes.GetNamedItem("name").InnerText;
	            String factoryClassName = node.Attributes.GetNamedItem("factory-class").InnerText;
	            configuration.AddPlugInView(_namespace, name, factoryClassName);
	        }
	    }

	    private static void HandlePlugInAggregation(Configuration configuration, XmlElement parentElement)
	    {
	        XmlNodeList nodes = parentElement.GetElementsByTagName("plugin-aggregation-function");
            foreach( XmlNode node in nodes )
	        {
	            String name = node.Attributes.GetNamedItem("name").InnerText;
	            String functionClassName = node.Attributes.GetNamedItem("function-class").InnerText;
	            configuration.AddPlugInAggregationFunction(name, functionClassName);
	        }
	    }

	    private static void HandlePlugInPatternObjects(Configuration configuration, XmlElement parentElement)
	    {
	        XmlNodeList nodes = parentElement.GetElementsByTagName("plugin-pattern-guard");
            foreach( XmlNode node in nodes )
	        {
	            String _namespace = node.Attributes.GetNamedItem("namespace").InnerText;
	            String name = node.Attributes.GetNamedItem("name").InnerText;
	            String factoryClassName = node.Attributes.GetNamedItem("factory-class").InnerText;
	            configuration.AddPlugInPatternGuard(_namespace, name, factoryClassName);
	        }

	        nodes = parentElement.GetElementsByTagName("plugin-pattern-observer");
            foreach( XmlNode node in nodes )
	        {
	            String _namespace = node.Attributes.GetNamedItem("namespace").InnerText;
	            String name = node.Attributes.GetNamedItem("name").InnerText;
	            String factoryClassName = node.Attributes.GetNamedItem("factory-class").InnerText;
	            configuration.AddPlugInPatternObserver(_namespace, name, factoryClassName);
	        }
	    }

	    private static void HandleAdapterLoaders(Configuration configuration, XmlElement parentElement)
	    {
	        XmlNodeList nodes = parentElement.GetElementsByTagName("adapter-loader");
	        foreach(XmlNode node in nodes)
	        {
	            String loaderName = node.Attributes.GetNamedItem("name").InnerText;
	            String className = node.Attributes.GetNamedItem("class-name").InnerText;
	            Properties properties = new Properties();
	            IEnumerator<XmlElement> nodeEnumerator = CreateElementEnumerator(node.ChildNodes);
	            while (nodeEnumerator.MoveNext())
	            {
	                XmlElement subElement = nodeEnumerator.Current;
	                if (subElement.Name == "init-arg")
	                {
	                    String name = subElement.Attributes.GetNamedItem("name").InnerText;
	                    String value = subElement.Attributes.GetNamedItem("value").InnerText;
	                    properties[name] = value;
	                }
	            }
	            configuration.AddAdapterLoader(loaderName, className, properties);
	        }
	    }

        private static NameValueCollection HandleProperties(XmlElement element, String propElementName)
        {
            NameValueCollection properties = new NameValueCollection();
            IEnumerator<XmlElement> nodeEnumerator = CreateElementEnumerator(element.ChildNodes);
            while (nodeEnumerator.MoveNext())
            {
                XmlElement subElement = nodeEnumerator.Current;
                if (subElement.Name.Equals(propElementName))
                {
                    String name = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("name").InnerText;
                    String value = ((XmlAttributeCollection)subElement.Attributes).GetNamedItem("value").InnerText;
                    properties[(String)name] = (String)value;
                }
            }
            return properties;
        }

		/// <summary>
		/// Returns an input stream from an application resource in the classpath.
		/// </summary>
		/// <param name="resource">to get input stream for</param>
		/// <returns>input stream for resource</returns>

		public static Stream GetResourceAsStream( String resource )
        {
            String stripped = resource.StartsWith("/") ? resource.Substring(1) : resource;

            Stream stream = ResourceManager.GetResourceAsStream( resource ) ;
            if ( stream == null )
            {
            	stream = ResourceManager.GetResourceAsStream( stripped ) ;
            }
            if (stream == null)
            {
                throw new EPException(resource + " not found");
            }
            return stream;
        }

        private static String GetOptionalAttribute(XmlNode node, String key)
        {
            XmlNode valueNode = ((XmlAttributeCollection)node.Attributes).GetNamedItem(key);
            if (valueNode != null)
            {
                return valueNode.InnerText;
            }
            return null;
        }
        
        private static IEnumerator<XmlElement> CreateElementEnumerator( XmlNodeList nodeList )
        {
        	foreach( XmlNode node in nodeList )
            {
                if (node is XmlElement)
                {
                    yield return node as XmlElement ;
                }
            }
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
