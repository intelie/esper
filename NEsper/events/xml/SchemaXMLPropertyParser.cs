using System;
using System.Text;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;
using System.Xml.XPath;

using antlr.collections;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql;
using net.esper.eql.generated;
using net.esper.events;
using net.esper.type;

using org.apache.commons.logging;

namespace net.esper.events.xml
{
	/// <summary>
	/// Parses event property names and transforms to XPath expressions using the schema
    /// information supplied. Supports the nested, indexed and mapped event properties.
	/// </summary>

	public class SchemaXMLPropertyParser : EqlTokenTypes
	{
		/// <summary>
		/// Gets the CLR type for the qualified name
		/// </summary>
		/// <param name="name"></param>
		/// <returns></returns>
		
		public static Type GetTypeForName( XmlQualifiedName name )
		{
            switch (name.Name)
            {
                case "bool":
                case "boolean":
                    return typeof(bool);
                case "int":
                    return typeof(double);
                case "ID":
                case "string":
                    return typeof(string);
                case "node":
                    return typeof(XmlNode);
                default:
                    return typeof(string);
            }
		}
		
		/// <summary>
		/// Return the xPath corresponding to the given property.
		/// The propertyName String may be simple, nested, indexed or mapped.
		/// </summary>
		/// <param name="propertyName">is the event property name</param>
		/// <param name="rootElementName">is the name of the root element</param>
		/// <param name="namespace_">The namespace.</param>
		/// <param name="xsModel">is the schema model</param>
		/// <returns>xpath expression</returns>
		/// <throws>  XPathExpressionException </throws>

		public static TypedEventPropertyGetter Parse(
			String propertyName,
			String rootElementName,
			String namespace_,
			XmlSchema xsModel )
		{
			XmlNamespaceManager nsManager = XPathNamespaceContext.Create();
			XmlQualifiedName[] names = xsModel.Namespaces.ToArray();

			for ( int i = 0 ; i < names.Length ; i++ )
			{
                XmlQualifiedName name = names[i] ;
                nsManager.AddNamespace( "n" + i, name.Namespace ) ;
			}

			AST ast = SimpleXMLPropertyParser.Parse( propertyName );

			// Find the element that has the same rootElementName and namespace as
			// the information that we have been provided with.
			
            XmlSchemaElement root = SchemaUtil.FindRootElement(xsModel, namespace_, rootElementName);
            if (root == null) {
                throw new PropertyAccessException("Invalid rootElementName - name must match the rootElement defined in the schema and have the correct namespace");
            }

            XmlSchemaComplexType rootElementType = root.ElementSchemaType as XmlSchemaComplexType ;
            if ( rootElementType == null ) {
                throw new PropertyAccessException("Invalid schema - the root element must have at least either attribute declarations or childs elements");
            }

            String prefix = String.Empty ;
            XmlQualifiedName rootName = root.QualifiedName;
            if ( ! String.IsNullOrEmpty( rootName.Namespace ) ) {
            	prefix = nsManager.LookupPrefix( rootName.Namespace ) + ":" ;
            }
            
            StringBuilder xPathBuf = new StringBuilder();
            xPathBuf.Append("/");
            xPathBuf.Append(prefix);
            xPathBuf.Append(rootElementName);

            Pair<String, XmlQualifiedName> pair = null;
            if (ast.getNumberOfChildren() == 1)
            {
                pair = MakeProperty(rootElementType, ast.getFirstChild(), nsManager);
                if (pair == null)
                {
                    throw new PropertyAccessException("Failed to locate property '" + propertyName + "' in schema");
                }
                
                xPathBuf.Append(pair.First);
            }
            else
            {
                AST child = ast.getFirstChild();
                
                do
                {
                    pair = MakeProperty(rootElementType, child, nsManager);
                    if (pair == null)
                    {
                        throw new PropertyAccessException("Failed to locate property '" + propertyName + "' nested property part '" + child.ToString() + "' in schema");
                    }

                    xPathBuf.Append(pair.First);

                    // We need to consider the recursive aspect of how an
                    // extended path operates.  Find the mapping for the
                    // current "text" ... if the child is an element then
                    // reset the rootElementType to the subElement and proceed
                    // to the next part of the path.
                    
                    String text = child.getFirstChild().getText();

                    XmlSchemaObject obj = SchemaUtil.FindPropertyMapping(rootElementType, text);
                    if (obj is XmlSchemaElement)
                    {
                    	XmlSchemaElement elementObj = obj as XmlSchemaElement ;
                    	XmlSchemaType elementSchemaType = elementObj.ElementSchemaType ;
                    	if ( elementSchemaType is XmlSchemaComplexType )
                    	{
                    		rootElementType = elementSchemaType as XmlSchemaComplexType ;
                    	}
                    }

                    child = child.getNextSibling();
                }
                while (child != null);
            }

            String xPath = xPathBuf.ToString();
            log.Debug(".parse XPath for property '" + propertyName + "' is expression=" + xPath);

            // Compile assembled XPath expression

            XPathExpression expr = XPathExpression.Compile(xPath, nsManager);

            return new XPathPropertyGetter(propertyName, expr, GetTypeForName( pair.Second ) ) ;
		}

		private static Pair<String, XmlQualifiedName> MakeProperty(
            XmlSchemaComplexType parent,
            AST child,
            XmlNamespaceManager nsManager )
		{
			String text = child.getFirstChild().getText();
            XmlSchemaObject obj = SchemaUtil.FindPropertyMapping( parent, text );
            
			if ( obj is XmlSchemaElement )
			{
				return MakeElementProperty( obj as XmlSchemaElement, child, nsManager );
			}
			else if ( obj is XmlSchemaAttribute )
			{
				return MakeAttributeProperty( obj as XmlSchemaAttribute, child, nsManager );
			}
			else if ( obj != null )
			{
				throw new ArgumentException( "unable to determine what property mapping was returned" ) ;
			}
			else
			{
				return null;
			}
		}

        private static Pair<String, XmlQualifiedName> MakeAttributeProperty(
            XmlSchemaAttribute use,
            AST child,
            XmlNamespaceManager nsManager )
		{
			String prefix = nsManager.LookupPrefix( use.QualifiedName.Namespace ) ;
			if ( String.IsNullOrEmpty( prefix ) )
			{
				prefix = String.Empty;
			}
			else
			{
				prefix += ":";
			}

			switch ( child.Type )
			{
				case EVENT_PROP_SIMPLE:
					XmlQualifiedName type = use.AttributeSchemaType.QualifiedName;
					String path = String.Format( "/@{0}{1}", prefix, child.getFirstChild().getText() ) ;
                    return new Pair<String, XmlQualifiedName>(path, type);
				case EVENT_PROP_MAPPED:
					throw new Exception( "Mapped properties not applicable to attributes" );
				case EVENT_PROP_INDEXED:
					throw new Exception( "Mapped properties not applicable to attributes" );
				default:
					throw new IllegalStateException( "Event property AST node not recognized, type=" + child.Type );
			}
		}

        private static Pair<String, XmlQualifiedName> MakeElementProperty(
            XmlSchemaElement elementDecl,
            AST child,
            XmlNamespaceManager nsManager )
		{
			XmlQualifiedName type = null;
			XmlSchemaType elementDeclType = elementDecl.ElementSchemaType ;
			
			if ( elementDeclType is XmlSchemaSimpleType )
			{			
				type = elementDeclType.QualifiedName;
			}
			else
			{
				XmlSchemaComplexType complexDeclType = elementDeclType as XmlSchemaComplexType ;
                if (complexDeclType.ContentType == XmlSchemaContentType.TextOnly)
                {
                    type = new XmlQualifiedName("string", "http://www.w3.org/2001/XMLSchema");
                }
                else
                {
                    type = new XmlQualifiedName("node", "http://www.w3.org/2001/XMLSchema");
                }
				
				//XSComplexTypeDefinition complex = (XSComplexTypeDefinition) decl.getTypeDefinition();
				//if ( complex.getSimpleType() != null )
				//{
				//	type = SchemaUtil.simpleTypeToQName( (XSSimpleTypeDecl) complex.getSimpleType() );
				//}
				//else
				//{
				//	// The result is a node
				//	type = XPathConstants.NODE;
				//}
			}

			String prefix = nsManager.LookupPrefix( elementDecl.QualifiedName.Namespace ) ;
			if ( prefix == null )
			{
				prefix = "";
			}
			else
			{
				prefix += ":";
			}

			switch ( child.Type )
			{
				case EVENT_PROP_SIMPLE:
					if ( elementDecl.MaxOccurs > 1 )
					{
						throw new PropertyAccessException( "Simple property not allowed in repeating elements" );
					}

					return new Pair<String, XmlQualifiedName>( String.Format( "/{0}{1}", prefix, child.getFirstChild().getText() ), type ) ;

				case EVENT_PROP_MAPPED:
					if ( elementDecl.MaxOccurs <= 1 )
					{
						throw new PropertyAccessException( "Element " + child.getFirstChild().getText() + " is not a collection, cannot be used as mapped property" );
					}
					
					String key = StringValue.ParseString( child.getFirstChild().getNextSibling().getText() );
                    return new Pair<String, XmlQualifiedName>("/" + prefix + child.getFirstChild().getText() + "[@id='" + key + "']", type);

				case EVENT_PROP_INDEXED:
                    if ( elementDecl.MaxOccurs <= 1 )
					{
						throw new PropertyAccessException( "Element " + child.getFirstChild().getText() + " is not a collection, cannot be used as mapped property" );
					}
                    
					int index = IntValue.ParseString( child.getFirstChild().getNextSibling().getText() );
					int xPathPosition = index + 1;
					return new Pair<String, XmlQualifiedName>( "/" + prefix + child.getFirstChild().getText() + "[position() = " + xPathPosition + "]", type );

				default:
					throw new IllegalStateException( "Event property AST node not recognized, type=" + child.Type );
			}
		}

		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
