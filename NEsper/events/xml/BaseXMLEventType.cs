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
    /// (normally via <seealso cref="net.esper.events.xml.XPathPropertyGetter"/> ).
    /// 
    /// For "on the fly" property resolvers, use either
    /// <seealso cref="net.esper.events.xml.SimpleXMLEventType"/> or
    /// <seealso cref="net.esper.events.xml.SchemaXMLEventType"/>
    /// 
    /// </summary>
    /// <author>  pablo
    /// </author>

    public abstract class BaseXMLEventType : BaseConfigurableEventType
    {
        /// <summary>
        /// Returns the name of the root element.
        /// </summary>
        /// <value>The name of the root element.</value>
        /// <returns> root element name
        /// </returns>

        virtual internal String RootElementName
        {
            get { return rootElementName; }
        }

        /// <summary>
        /// Sets the namespace context for use in XPath expression resolution.
        /// </summary>
        /// <value>The namespace manager.</value>

        virtual internal XmlNamespaceManager NamespaceManager
        {
            set { this.nsManager = value; }
        }

        private static readonly String[] EMPTY_STRING_ARRAY = new String[0];

        private readonly String rootElementName;
        private XmlNamespaceManager nsManager;
		private readonly ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM;

        /// <summary> Ctor.</summary>
        /// <param name="configurationEventTypeXMLDOM">the XML DOM configuration such as root element and schema names
        /// </param>

        public BaseXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
            : base(typeof(XmlNode))
        {
            this.rootElementName = configurationEventTypeXMLDOM.RootElementName;
			this.configurationEventTypeXMLDOM = configurationEventTypeXMLDOM;
        }

        /// <summary>
        /// Set the preconfigured event properties resolved by XPath expression.
        /// </summary>
        /// <param name="explicitProperties">are preconfigured event properties
        /// </param>
        
        protected void SetExplicitProperties(ICollection<ConfigurationEventTypeXMLDOM.XPathPropertyDesc> explicitProperties)
        {
            // Convert explicit properties to XPath expressions
            EDictionary<String, TypedEventPropertyGetter> getters = new HashDictionary<String, TypedEventPropertyGetter>();

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

            SetExplicitProperties(getters);
        }

        /// <summary>
        /// Returns an array of event types that are super to this event type, from which this event type
        /// inherited event properties.  For bean instances underlying the event this method returns the
        /// event types for all superclasses extended by the bean and all interfaces implemented by the bean.
        /// </summary>
        /// <value></value>
        /// <returns>an array of event types</returns>
        public override IEnumerable<EventType> SuperTypes
        {
            get { return null; }
        }

        /// <summary>
        /// Returns enumerable over all super types to event type, going up the hierarchy and including
        /// all interfaces (and their extended interfaces) and superclasses as EventType instances.
        /// </summary>
        /// <value></value>
        public override IEnumerable<EventType> DeepSuperTypes
        {
            get { return EventTypeArray.Empty ; }
        }

        internal override String[] DoListPropertyNames()
        {
            return EMPTY_STRING_ARRAY;
        }
		
		public override bool Equals(Object otherObj)
		{
	        BaseXMLEventType other =  otherObj as BaseXMLEventType;
			if ( other == null )
			{
				return false ;
			}
		
			return (configurationEventTypeXMLDOM.Equals(other.configurationEventTypeXMLDOM));
		}

        /// <summary>
        /// Serves as a hash function for a particular type. <see cref="M:System.Object.GetHashCode"></see> is suitable for use in hashing algorithms and data structures like a hash table.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			return configurationEventTypeXMLDOM.GetHashCode();
		}
    }
}
