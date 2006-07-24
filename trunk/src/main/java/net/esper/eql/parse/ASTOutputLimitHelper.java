package net.esper.eql.parse;

import net.esper.eql.expression.OutputLimitSpec;
import net.esper.eql.generated.EqlTokenTypes;
import antlr.collections.AST;

/**
 * Builds an output limit spec from an output limit AST node.
 *
 */public class ASTOutputLimitHelper implements EqlTokenTypes
{
	 /** 
	  * Build an output limit spec from the AST node supplied.
	  * @param node - parse node
	  * @return output limit spec
	  */
	 public static OutputLimitSpec buildSpec(AST node)
	 {
		 boolean outputLastOnly = false;
		 AST child = node.getFirstChild();
		 if(child.getType() == ALL || child.getType() == LAST)
		 {
			 outputLastOnly = (child.getType() == LAST);
			 child = child.getNextSibling();
		 }
		 
		 switch (node.getType()) {
		 case EVENT_LIMIT_EXPR:
			 return  new OutputLimitSpec(Integer.parseInt(child.getText()), outputLastOnly);
		 case SEC_LIMIT_EXPR:	
			 return  new OutputLimitSpec(Double.parseDouble(child.getText()), outputLastOnly);
		 case MIN_LIMIT_EXPR:
			 // 60 seconds to a minute
			 return  new OutputLimitSpec(60 * Double.parseDouble(child.getText()), false);
		 default:
			 throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized output limit type");
		 } 
	 }

}
