using System;
using System.Collections.Generic;
using System.Text;

using antlr.collections;

using net.esper.core;
using net.esper.compat;
using net.esper.eql.generated;
using net.esper.events;
using net.esper.filter;
using net.esper.type;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	/// <summary>
    /// Builds a filter specification from filter AST nodes.
    /// </summary>
	
    public class ASTFilterSpecHelper : EqlTokenTypes
	{
		/// <summary> Return the generated property name that is defined by the AST child node and it's siblings.</summary>
		/// <param name="propertyNameExprChildNode">is the child node from which to Start putting the property name together
		/// </param>
		/// <returns> property name, ie. indexed[1] or mapped('key') or nested.nested or a combination or just 'simple'.
		/// </returns>
		
        public static String getPropertyName(AST propertyNameExprChildNode)
		{
			StringBuilder buffer = new StringBuilder();
			String delimiter = "";
			AST child = propertyNameExprChildNode;
			
			do 
			{
				buffer.Append(delimiter);
				
				switch (child.Type)
				{
					
					case EqlTokenTypes.EVENT_PROP_SIMPLE: 
						buffer.Append(child.getFirstChild().getText());
						break;
					
					case EqlTokenTypes.EVENT_PROP_MAPPED: 
						buffer.Append(child.getFirstChild().getText());
						buffer.Append("(");
						buffer.Append(child.getFirstChild().getNextSibling().getText());
						buffer.Append(")");
						break;
					
					case EqlTokenTypes.EVENT_PROP_INDEXED: 
						buffer.Append(child.getFirstChild().getText());
						buffer.Append("[");
						buffer.Append(child.getFirstChild().getNextSibling().getText());
						buffer.Append("]");
						break;
					
					default: 
						throw new SystemException("Event property AST node not recognized, type=" + child.Type);
					
				}
				
				delimiter = ".";
				child = child.getNextSibling();
			}
			while (child != null);
			
			return buffer.ToString();
		}
		
		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
