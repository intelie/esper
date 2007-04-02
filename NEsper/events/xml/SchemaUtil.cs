using System;
using System.Text;
using System.Xml;
using System.Xml.XPath;
using System.Xml.Schema;

using net.esper.client;

namespace net.esper.events.xml
{
	/// <summary> Utility class for querying schema information.</summary>
	/// <author>  pablo
	/// </author>
	
    public class SchemaUtil
	{
		/// <summary>
		/// Returns the XPathConstants type for a given Xerces type definition.
		/// </summary>
		/// <param name="definition">The definition.</param>
		/// <returns>XPathConstants type</returns>
		
		public static XmlQualifiedName SimpleTypeToQName(XmlSchemaSimpleType definition)
		{
			return definition.QualifiedName;

			//switch (definition.getPrimitiveKind())
			//{
			//    case XSSimpleType.PRIMITIVE_BOOLEAN: 
			//        return XPathConstants.BOOLEAN;
				
			//    case XSSimpleType.PRIMITIVE_DOUBLE: 
			//        return XPathConstants.NUMBER;
				
			//    case XSSimpleType.PRIMITIVE_STRING: 
			//        return XPathConstants.STRING;
				
			//    case XSSimpleType.PRIMITIVE_DECIMAL: 
			//        return XPathConstants.NUMBER;
				
			//    default: 
			//        throw new EPException("Unexpected schema simple type encountered '" + definition.getPrimitiveKind() + "'");
			//}
		}

		/// <summary>
		/// Returns the root element for a given schema given a root element name and namespace.
		/// </summary>
		/// <param name="schema">is the schema to interrogate</param>
		/// <param name="namespace_">The namespace_.</param>
		/// <param name="elementName">is the name of the root element</param>
		/// <returns>declaration of root element</returns>
		
		public static XmlSchemaElement FindRootElement(XmlSchema schema, String namespace_, String elementName)
		{
            XmlSchemaObjectTable elements = schema.Elements;
            foreach (XmlQualifiedName qname in elements.Names)
            {
            }

            if (String.IsNullOrEmpty(namespace_))
            {
                namespace_ = schema.TargetNamespace;
            }

            XmlQualifiedName name = new XmlQualifiedName(elementName, namespace_);
            XmlSchemaElement schemaObj = elements[name] as XmlSchemaElement;
            if (schemaObj != null)
            {
                return schemaObj;
            }

            StringBuilder message = new StringBuilder();
            message.AppendFormat( "Could not find root element declaration in schema using element name '{0}'", name ) ;

            throw new EPException( message.ToString() );
		}
		
		
		/// <summary>
        /// Finds an apropiate definition for the given property, Starting at the
		/// given definition.  First look if the property is an attribute. If not,
        /// look at child element definitions.
		/// </summary>
		/// <param name="def">the definition to Start looking</param>
		/// <param name="property">the property to look for</param>
		/// <returns>
		/// XmlSchemaAttribute if the property is an attribute,
		/// XmlSchemaElement if is an element,
		/// or null if not found in schema
		/// </returns>
		
		public static XmlSchemaObject FindPropertyMapping( XmlSchemaComplexType def, String property )
		{
			XmlSchemaObjectTable attrs = def.AttributeUses;
			foreach ( System.Collections.DictionaryEntry entry in attrs )
			{
				XmlSchemaAttribute attr = entry.Value as XmlSchemaAttribute ;
				if ( ( attr != null ) && String.Equals( attr.Name, property ) )
				{
					return attr ;
				}
			}

			// We are currently looking at the element type definition.  XML Schema
			// specifies that an complex type must either:
			//    (a) be empty
			//    (b) be a non-empty element composed of:
			//        (1) text-only
			//        (2) elements-only
			//        (3) text and elements (mixed)
			//
			// What does the complex schema type tell us about what we are dealing
			// with.  Again, remember we are only interested in elements.

			switch( def.ContentType ) {
				case XmlSchemaContentType.ElementOnly:
				case XmlSchemaContentType.Mixed:
					// Either of these types results in a sequence of elements.  These
					// will all represented with an XmlSchemaGroupBase particle.  It may
					// be an XmlSchemaSequence or some other type, but in the end it
					// should derive from XmlSchemaGroupBase.
					
					XmlSchemaGroupBase particle = def.Particle as XmlSchemaGroupBase;
					if ( particle != null )
					{
						foreach( XmlSchemaObject someObject in particle.Items )
						{
							XmlSchemaElement someElement = someObject as XmlSchemaElement ;
							if ( ( someElement != null ) && ( someElement.Name.Equals( property, StringComparison.InvariantCultureIgnoreCase ) ) )
							{
								return someElement ;								
							}
						}
					}
					break;
			}
			
			//property not found in schema
			return null;
		}
	}
}
