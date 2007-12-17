/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.spec.OutputLimitLimitType;
import net.esper.eql.spec.OutputLimitRateType;
import net.esper.eql.generated.EqlTokenTypes;
import antlr.collections.AST;

/**
 * Builds an output limit spec from an output limit AST node.
 */
public class ASTOutputLimitHelper implements EqlTokenTypes
{
    /**
     * Build an output limit spec from the AST node supplied.
     *
     * @param node - parse node
     * @return output limit spec
     */
    public static OutputLimitSpec buildSpec(AST node)
    {
        AST child = node.getFirstChild();

        OutputLimitLimitType displayLimit = OutputLimitLimitType.ALL;
        if (child.getType() == FIRST)
        {
            displayLimit = OutputLimitLimitType.FIRST;
            child = child.getNextSibling();
        }
        else if (child.getType() == LAST)
        {
            displayLimit = OutputLimitLimitType.LAST;
            child = child.getNextSibling();
        }
        else if (child.getType() == SNAPSHOT)
        {
            displayLimit = OutputLimitLimitType.SNAPSHOT;
            child = child.getNextSibling();
        }
        else if (child.getType() == ALL)
        {
            child = child.getNextSibling();
        }

        String variableName = null;
        double rate = -1;
        if (child.getType() == IDENT)
        {
            variableName = child.getText();
        }
        else
        {
            rate = Double.parseDouble(child.getText());
        }

        switch (node.getType())
        {
            case EVENT_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.EVENTS, displayLimit);
            case SEC_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_SEC, displayLimit);
            case MIN_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_MIN, displayLimit);
            default:
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized output limit type");
		 }
	 }

}
