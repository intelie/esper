using System;
using System.Collections.Generic;
using System.Xml;
using System.Xml.XPath;

using net.esper.client;
using net.esper.events;

namespace net.esper.events.xml
{
    /// <summary>
    /// Optimistic try to resolve the property string into an appropiate xPath,
    /// and use it as getter.
    /// Mapped and Indexed properties supported.
    /// Because no type information is given, all property are resolved to String.
    /// No namespace support.
    /// Cannot access to xml attributes, only elements content.
    /// 
    /// If an xsd is present, then use {@link net.esper.event.xml.SchemaXMLEventType SchemaXMLEventType }
    /// 
    /// </summary>
    /// <author>  pablo
    /// 
    /// </author>
    public class SimpleXMLEventType : BaseXMLEventType
    {
        private IDictionary<String, TypedEventPropertyGetter> propertyGetterCache;

        /// <summary> Ctor.</summary>
        /// <param name="configurationEventTypeXMLDOM">configures the event type
        /// </param>
        public SimpleXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
            : base(configurationEventTypeXMLDOM.RootElementName)
        {
            base.setExplicitProperties(configurationEventTypeXMLDOM.XPathProperties.Values);
            propertyGetterCache = new Dictionary<String, TypedEventPropertyGetter>();
        }

        internal override Type doResolvePropertyType(String property)
        {
            return typeof(String);
        }

        internal override EventPropertyGetter doResolvePropertyGetter(String property)
        {
            TypedEventPropertyGetter getter = propertyGetterCache[property];
            if (getter != null)
            {
                return getter;
            }

            XPathExpression xPathExpression = null;
            try
            {
                xPathExpression = SimpleXMLPropertyParser.parse(property, RootElementName);
            }
            catch (XPathException e)
            {
                throw new EPException("Error constructing XPath expression from property name '" + property + "'", e);
            }

            getter = new XPathPropertyGetter(property, xPathExpression, typeof(string)) ;
            propertyGetterCache[property] = getter;
            return getter;
        }
    }
}
