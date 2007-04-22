/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.spec.OutputLimitSpec.DisplayLimit;
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
		 AST child = node.getFirstChild();

		 DisplayLimit displayLimit = DisplayLimit.ALL;
		 if(child.getType() == FIRST)
		 {
			 displayLimit = DisplayLimit.FIRST;
			 child = child.getNextSibling();
		 }
		 else if(child.getType() == LAST)
		 {
			 displayLimit = DisplayLimit.LAST;
			 child = child.getNextSibling();
		 }
		 else if(child.getType() == ALL)
		 {
			 child = child.getNextSibling();
		 }

		 switch (node.getType()) {
		 case EVENT_LIMIT_EXPR:
			 return  new OutputLimitSpec(Integer.parseInt(child.getText()), displayLimit);
		 case SEC_LIMIT_EXPR:
			 return  new OutputLimitSpec(Double.parseDouble(child.getText()), displayLimit);
		 case MIN_LIMIT_EXPR:
			 // 60 seconds to a minute
			 return  new OutputLimitSpec(60 * Double.parseDouble(child.getText()), displayLimit);
		 default:
			 throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized output limit type");
		 }
	 }

}
