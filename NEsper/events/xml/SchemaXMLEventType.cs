using System;
using System.Collections.Generic;
using System.Xml;
using System.Xml.XPath;
using System.Xml.Schema;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.util;

namespace net.esper.events.xml
{
    /// <summary>
	/// EventType for xml events that have a Schema.
    /// Mapped and Indexed properties are supported.
    /// All property types resolved via the declared xsd types.
    /// Can access attributes.
    /// Validates the property string at construction time. 
    /// </summary>
    /// <author> pablo</author>
 
	public class SchemaXMLEventType : BaseXMLEventType
    {
        // schema model
		private XmlSchema schema;

        // rootElementNamespace of the root Element
        private String rootElementNamespace;

        private EDictionary<String, TypedEventPropertyGetter> propertyGetterCache;

		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="configurationEventTypeXMLDOM">configuration for type</param>
        
		public SchemaXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
            : base(configurationEventTypeXMLDOM)
        {
            propertyGetterCache = new EHashDictionary<String, TypedEventPropertyGetter>();

            // Load schema
            String schemaResource = configurationEventTypeXMLDOM.SchemaResource;
            try
            {
                readSchema(schemaResource);
            }
            catch (EPException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                throw new EPException("Failed to read schema '" + schemaResource + "'", ex);
            }

            // Use the root namespace for resolving the root element
            rootElementNamespace = configurationEventTypeXMLDOM.RootElementNamespace;

            // Set of namespace context for XPath expressions
            XmlNamespaceManager nsManager = XPathNamespaceContext.Create();
            if (configurationEventTypeXMLDOM.DefaultNamespace != null)
            {
            	nsManager.AddNamespace( String.Empty, configurationEventTypeXMLDOM.DefaultNamespace ) ;
            }

            foreach (KeyValuePair<String, String> entry in configurationEventTypeXMLDOM.NamespacePrefixes)
            {
                nsManager.AddNamespace(
                    entry.Key,         // prefix
                    entry.Value);      // namespace
            }

            base.NamespaceManager = nsManager;

            // Finally add XPath properties as that may depend on the rootElementNamespace
            base.SetExplicitProperties(configurationEventTypeXMLDOM.XPathProperties.Values);
        }

		/// <summary>
		/// Reads an XML schema from the named resource.
		/// </summary>
		/// <param name="schemaResource">Name of a file containing the schema</param>
		
        private void readSchema(String schemaResource)
        {
            //Uri url = ResourceLoader.resolveURLResource("schema", schemaResource);

			try
			{
				XmlDocument document = new XmlDocument();
				document.Load( schemaResource );
				
				XmlSchema schemaObj = XmlSchema.Read( new XmlNodeReader( document.DocumentElement ), null );
				XmlSchemaSet schemaSet = new XmlSchemaSet() ;
				schemaSet.Add( schemaObj ) ;
				schemaSet.Compile() ;
				
				schema = schemaObj ;
			}
			catch ( XmlSchemaException xx )
			{
				throw new EPException( String.Format( "Failed to read schema from file '{0}': {1}", schemaResource, xx ) );
			}

			//if (xsModel == null)
			//{
			//    throw new EPException("Failed to read schema via URL '" + schemaResource + "'");
			//}
        }

        internal override Type DoResolvePropertyType(String property)
        {
            TypedEventPropertyGetter getter = propertyGetterCache.Fetch(property, null);
            if (getter != null)
            {
                return getter.ResultClass;
            }

            getter = (TypedEventPropertyGetter)DoResolvePropertyGetter(property);
            if (getter != null)
            {
                return getter.ResultClass;
            }
            
            return null;
        }

        internal override EventPropertyGetter DoResolvePropertyGetter(String property)
        {
            TypedEventPropertyGetter getter = propertyGetterCache.Fetch(property);
            if (getter != null)
            {
                return getter;
            }

            try
            {
                getter = SchemaXMLPropertyParser.Parse(property, RootElementName, rootElementNamespace, schema);
                propertyGetterCache[property] = getter;
                return getter;
            }
            catch (XPathException e)
            {
                throw new EPException("Error constructing XPath expression from property name '" + property + "'", e);
            }
        }

        internal override String[] DoListPropertyNames()
        {
            return null;
        }
    }
}
