using System;
using System.Collections.Generic;
using System.Xml;
using System.Xml.XPath;

using net.esper.client;
using net.esper.compat;
using net.esper.events;

namespace net.esper.events.xml
{
    /// <summary> Base class for XMLEventTypes.
    /// Using this class as EventType only allow preconfigured properties 
    /// (normally via {@link net.esper.event.xml.XPathPropertyGetter XPathPropertyGetter} ).
    /// 
    /// For "on the fly" property resolvers, use either
    /// {@link net.esper.event.xml.SimpleXMLEventType SimpleXMLEventType} or
    /// {@link net.esper.event.xml.SchemaXMLEventType SchemaXMLEventType}
    /// 
    /// </summary>
    /// <author>  pablo
    /// </author>

    public abstract class BaseXMLEventType : BaseConfigurableEventType
    {
        /// <summary> Returns the name of the root element.</summary>
        /// <returns> root element name
        /// </returns>

        virtual internal String RootElementName
        {
            get { return rootElementName; }
        }

        /// <summary> Sets the namespace context for use in XPath expression resolution.</summary>
        /// <param name="namespaceContext">for XPath expressions
        /// </param>

        virtual internal XmlNamespaceManager NamespaceManager
        {
            set { this.nsManager = value; }
        }

        private static readonly String[] EMPTY_STRING_ARRAY = new String[0];

        private readonly String rootElementName;
        private XmlNamespaceManager nsManager;

        /// <summary> Ctor.</summary>
        /// <param name="rootElementName">is the name of the root element
        /// </param>

        public BaseXMLEventType(String rootElementName)
            : base(typeof(XmlNode))
        {
            this.rootElementName = rootElementName;
        }

        /// <summary>
        /// Set the preconfigured event properties resolved by XPath expression.
        /// </summary>
        /// <param name="explicitProperties">are preconfigured event properties
        /// </param>
        
        protected void setExplicitProperties(ICollection<ConfigurationEventTypeXMLDOM.XPathPropertyDesc> explicitProperties)
        {
            // Convert explicit properties to XPath expressions
            EDictionary<String, TypedEventPropertyGetter> getters = new EHashDictionary<String, TypedEventPropertyGetter>();

            String xpathExpression = null;
            try
            {
                foreach (ConfigurationEventTypeXMLDOM.XPathPropertyDesc property in explicitProperties)
                {
                    XPathExpression expression = XPathExpression.Compile(property.XPath, nsManager) ;
                    getters[property.Name] = new XPathPropertyGetter(property.Name, expression, property.ResultDataType);
                }
            }
            catch (ArgumentException ex)
            {
                throw new EPException("XPath expression could not be compiled for expression '" + xpathExpression + "'", ex);
            }
            catch (XPathException ex)
            {
                throw new EPException("XPath expression could not be compiled for expression '" + xpathExpression + "'", ex);
            }

            setExplicitProperties(getters);
        }

        public override IEnumerable<EventType> SuperTypes
        {
            get { return null; }
        }

        public override IEnumerable<EventType> DeepSuperTypes
        {
            get { return EventTypeArray.Empty ; }
        }

        internal override String[] doListPropertyNames()
        {
            return EMPTY_STRING_ARRAY;
        }
    }
}
