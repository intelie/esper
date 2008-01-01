/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.eql.generated.EsperEPLParser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 * For selection of the parse rule to use.
 */
public interface ParseRuleSelector
{
    /**
     * Implementations can invoke a parse rule of their choice on the parser.
     * @param parser - to invoke parse rule on
     * @throws RecognitionException is a parse exception
     */
    public Tree invokeParseRule(EsperEPLParser parser) throws RecognitionException;
}



