using System;
using System.IO;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using antlr;
using antlr.collections;

using net.esper.compat;
using net.esper.eql.generated;
using net.esper.events;
using net.esper.type;

using org.apache.commons.logging;

namespace net.esper.events.xml
{
	/// <summary> Parses event property names and transforms to XPath expressions. Supports
	/// nested, indexed and mapped event properties.
	/// </summary>
	
    public class SimpleXMLPropertyParser : EqlTokenTypes
	{
		/// <summary>
		/// Return the xPath corresponding to the given property.
		/// The propertyName String may be simple, nested, indexed or mapped.
		/// </summary>
		/// <param name="propertyName">is the property name to parse</param>
		/// <param name="rootElementName">is the name of the root element for generating the XPath expression</param>
		/// <returns>xpath expression</returns>
		/// <throws>  XPathExpressionException </throws>
		
		public static XPathExpression Parse(String propertyName, String rootElementName)
		{
			AST ast = Parse(propertyName);
			
			StringBuilder xPathBuf = new StringBuilder();
			xPathBuf.Append('/');
			xPathBuf.Append(rootElementName);
			
			if (ast.getNumberOfChildren() == 1)
			{
                xPathBuf.Append(MakeProperty(ast.getFirstChild()));
			}
			else
			{
				AST child = ast.getFirstChild();
				do 
				{
                    xPathBuf.Append(MakeProperty(child));
					child = child.getNextSibling();
				}
				while (child != null);
			}
			
			String xPath = xPathBuf.ToString();
			log.Debug(".parse For property '" + propertyName + "' the xpath is '" + xPath + "'");

			return XPathExpression.Compile( xPath );
		}
		
		private static String MakeProperty(AST child)
		{
			switch (child.Type)
			{
				case EqlTokenTypes.EVENT_PROP_SIMPLE: 
					return "/" + child.getFirstChild().getText();
				
				case EqlTokenTypes.EVENT_PROP_MAPPED: 
					String key = StringValue.ParseString(child.getFirstChild().getNextSibling().getText());
					return "/" + child.getFirstChild().getText() + "[@id='" + key + "']";
				
				case EqlTokenTypes.EVENT_PROP_INDEXED: 
					int index = IntValue.ParseString(child.getFirstChild().getNextSibling().getText());
					return "/" + child.getFirstChild().getText() + "[position() = " + index + "]";
				
				default: 
					throw new IllegalStateException("Event property AST node not recognized, type=" + child.Type);
				
			}
		}
		
		/// <summary> Parses a given property name returning an AST.</summary>
		/// <param name="propertyName">to parse
		/// </param>
		/// <returns> AST syntax tree
		/// </returns>

		internal static AST Parse(String propertyName)
		{
			EQLStatementLexer lexer = new EQLStatementLexer(new StringReader(propertyName));
			EQLStatementParser parser = new EQLStatementParser(lexer);
			
			try
			{
				parser.startEventPropertyRule();
			}
			catch (TokenStreamException e)
			{
				throw new PropertyAccessException("Failed to parse property name '" + propertyName + "'", e);
			}
			catch (RecognitionException e)
			{
				throw new PropertyAccessException("Failed to parse property name '" + propertyName + "'", e);
			}
			
			return parser.getAST();
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}