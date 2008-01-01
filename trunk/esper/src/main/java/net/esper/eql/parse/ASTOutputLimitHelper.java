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
import net.esper.eql.generated.EsperEPLParser;
import org.antlr.runtime.tree.Tree;

/**
 * Builds an output limit spec from an output limit AST node.
 */
public class ASTOutputLimitHelper
{
    /**
     * Build an output limit spec from the AST node supplied.
     *
     * @param node - parse node
     * @return output limit spec
     */
    public static OutputLimitSpec buildSpec(Tree node)
    {
        int count = 0;
        Tree child = node.getChild(count);

        OutputLimitLimitType displayLimit = OutputLimitLimitType.ALL;
        if (child.getType() == EsperEPLParser.FIRST)
        {
            displayLimit = OutputLimitLimitType.FIRST;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPLParser.LAST)
        {
            displayLimit = OutputLimitLimitType.LAST;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPLParser.SNAPSHOT)
        {
            displayLimit = OutputLimitLimitType.SNAPSHOT;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPLParser.ALL)
        {
            child = node.getChild(++count);
        }

        String variableName = null;
        double rate = -1;
        if (child.getType() == EsperEPLParser.IDENT)
        {
            variableName = child.getText();
        }
        else
        {
            rate = Double.parseDouble(child.getText());
        }

        switch (node.getType())
        {
            case EsperEPLParser.EVENT_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.EVENTS, displayLimit);
            case EsperEPLParser.SEC_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_SEC, displayLimit);
            case EsperEPLParser.MIN_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_MIN, displayLimit);
            default:
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized output limit type");
		 }
	 }

}
