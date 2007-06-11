// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Xml.XPath;

using net.esper.compat;

namespace net.esper.client
{
    /// <summary>
    /// Configuration object for enabling the engine to process events represented as XML DOM document nodes.
    ///
    /// Use this class to configure the engine for processing of XML DOM objects that represent events
    /// and contain all the data for event properties used by statements.
    ///
    /// Minimally required is the root element name which allows the engine to map the document
    /// to the event type that has been named in an EQL or pattern statement.
    ///
    /// Event properties that are results of XPath expressions can be made known to the engine via this class.
    /// For XPath expressions that must refer to namespace prefixes those prefixes and their
    /// namespace name must be supplied to the engine. A default namespace can be supplied as well.
    ///
    /// By supplying a schema resource the engine can interrogate the schema, allowing the engine to
    /// verify event properties and return event properties in the type defined by the schema.
    /// When a schema resource is supplied, the optional root element namespace defines the namespace in case the
    /// root element name occurs in multiple namespaces.
    /// </summary>

    public class ConfigurationEventTypeXMLDOM
    {
        private String rootElementName;

        // Root element namespace.
        // Used to find root element in schema. Useful and required in the case where the root element exists in
        // multiple namespaces.
        private String rootElementNamespace;

        // Default name space.
        // For XPath expression evaluation.
        private String defaultNamespace;

        private String schemaResource;
        private EDictionary<String, XPathPropertyDesc> xPathProperties;
        private EDictionary<String, String> namespacePrefixes;

        /// <summary> Gets or sets the root element name.</summary>

        virtual public String RootElementName
        {
            get { return rootElementName; }
            set { this.rootElementName = value; }
        }

        /// <summary> Gets or sets the root element namespace.</summary>

        virtual public String RootElementNamespace
        {
            get { return rootElementNamespace; }
            set { this.rootElementNamespace = value; }
        }

        /// <summary> Gets or sets the default namespace.</summary>

        virtual public String DefaultNamespace
        {
            get { return defaultNamespace; }
            set { this.defaultNamespace = value; }

        }

        /// <summary>
        /// Gets or sets  the schema resource.
        /// </summary>

        virtual public String SchemaResource
        {
            get { return schemaResource; }
            set { this.schemaResource = value; }
        }

        /// <summary>
        /// Ctor.
        /// </summary>

        public ConfigurationEventTypeXMLDOM()
        {
            xPathProperties = new EHashDictionary<String, XPathPropertyDesc>();
            namespacePrefixes = new EHashDictionary<String, String>();
        }

        /// <summary> Returns a map of property name and descriptor for XPath-expression properties.</summary>
        /// <returns> XPath property information
        /// </returns>

        public EDictionary<String, XPathPropertyDesc> XPathProperties
        {
            get { return xPathProperties; }
        }

        /// <summary>
        /// Adds an event property for which the engine uses the supplied XPath expression against
        /// a DOM document node to resolve a property value.
        /// </summary>
        /// <param name="name">name of the event property</param>
        /// <param name="xpath">is an arbitrary xpath expression</param>
        /// <param name="type">is the return type of the expression</param>

        public virtual void AddXPathProperty(String name, String xpath, XPathResultType type)
        {
            XPathPropertyDesc desc = new XPathPropertyDesc(name, xpath, type);
            xPathProperties[name] = desc;
        }

        /// <summary> Returns the namespace prefixes in a map of prefix as key and namespace name as value.</summary>
        /// <returns> namespace prefixes
        /// </returns>

        public EDictionary<String, String> NamespacePrefixes
        {
            get { return namespacePrefixes; }
        }

        /// <summary> Add a prefix and namespace name for use in XPath expressions refering to that prefix.</summary>
        /// <param name="prefix">is the prefix of the namespace
        /// </param>
        /// <param name="namespace_">is the namespace name</param>

        public virtual void AddNamespacePrefix(String prefix, String namespace_)
        {
            namespacePrefixes[prefix] = namespace_;
        }

        public override bool Equals(Object otherObj)
        {
            ConfigurationEventTypeXMLDOM other = otherObj as ConfigurationEventTypeXMLDOM;
            if (other == null)
            {
                return false;
            }

            if (other.rootElementNamespace != this.rootElementName)
            {
                return false;
            }

            if (((other.rootElementNamespace == null) && (rootElementNamespace != null)) ||
                ((other.rootElementNamespace != null) && (rootElementNamespace == null)))
            {
                return false;
            }
            return rootElementNamespace == other.rootElementNamespace;
        }

        /// <summary>
        /// Descriptor class for event properties that are resolved via XPath-expression.
        /// </summary>

        public class XPathPropertyDesc
        {
            private String name;
            private String xpath;
            private XPathResultType type;
            private Type nativeType;

            /// <summary> Returns the event property name.</summary>
            /// <returns> event property name
            /// </returns>

            virtual public String Name
            {
                get { return name; }
            }

            /// <summary> Returns the XPath expression.</summary>
            /// <returns> XPath expression
            /// </returns>

            virtual public String XPath
            {
                get { return xpath; }
            }

            /// <summary> Returns the representing the event property type.</summary>
            /// <returns> type infomation
            /// </returns>

            virtual public XPathResultType ResultType
            {
                get { return type; }
            }

            /// <summary>
            /// Returns the native data type representing the event property.
            /// </summary>

            virtual public Type ResultDataType
            {
                get { return nativeType; }
            }

            /// <summary> Ctor.</summary>
            /// <param name="name">is the event property name
            /// </param>
            /// <param name="xpath">is an arbitrary XPath expression
            /// </param>
            /// <param name="type">is a javax.xml.xpath.XPathConstants constant
            /// </param>

            public XPathPropertyDesc(String name, String xpath, XPathResultType type)
            {
                this.name = name;
                this.xpath = xpath;
                this.type = type;
                this.nativeType = typeof(string);

                switch (type)
                {
                    case XPathResultType.Boolean:
                        this.nativeType = typeof(bool);
                        break;
                    case XPathResultType.String:
                        this.nativeType = typeof(string);
                        break;
                    case XPathResultType.Number:
                        this.nativeType = typeof(double);
                        break;
                }
            }

            public override int GetHashCode()
            {
                return
                    name.GetHashCode() * 31 +
                    xpath.GetHashCode();
            }
        }
    }
}
