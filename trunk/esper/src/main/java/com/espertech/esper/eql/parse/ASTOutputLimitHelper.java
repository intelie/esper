/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.parse;

import com.espertech.esper.eql.spec.OutputLimitSpec;
import com.espertech.esper.eql.spec.OutputLimitLimitType;
import com.espertech.esper.eql.spec.OutputLimitRateType;
import com.espertech.esper.eql.generated.EsperEPL2GrammarParser;
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
    public static OutputLimitSpec buildOutputLimitSpec(Tree node)
    {
        int count = 0;
        Tree child = node.getChild(count);

        OutputLimitLimitType displayLimit = OutputLimitLimitType.DEFAULT;
        if (child.getType() == EsperEPL2GrammarParser.FIRST)
        {
            displayLimit = OutputLimitLimitType.FIRST;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPL2GrammarParser.LAST)
        {
            displayLimit = OutputLimitLimitType.LAST;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPL2GrammarParser.SNAPSHOT)
        {
            displayLimit = OutputLimitLimitType.SNAPSHOT;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPL2GrammarParser.ALL)
        {
            displayLimit = OutputLimitLimitType.ALL;
            child = node.getChild(++count);
        }

        String variableName = null;
        double rate = -1;
        if (child.getType() == EsperEPL2GrammarParser.IDENT)
        {
            variableName = child.getText();
        }
        else
        {
            rate = Double.parseDouble(child.getText());
        }

        switch (node.getType())
        {
            case EsperEPL2GrammarParser.EVENT_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.EVENTS, displayLimit);
            case EsperEPL2GrammarParser.SEC_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_SEC, displayLimit);
            case EsperEPL2GrammarParser.MIN_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_MIN, displayLimit);
            default:
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized output limit type");
		 }
	 }

}
