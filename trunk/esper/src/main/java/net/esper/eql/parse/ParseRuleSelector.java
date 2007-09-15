/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.eql.generated.EQLStatementParser;
import antlr.TokenStreamException;
import antlr.RecognitionException;

/**
 * For selection of the parse rule to use.
 */
public interface ParseRuleSelector
{
    /**
     * Implementations can invoke a parse rule of their choice on the parser.
     * @param parser - to invoke parse rule on
     * @throws TokenStreamException is a parse exception
     * @throws RecognitionException is a parse exception
     */
    public void invokeParseRule(EQLStatementParser parser) throws TokenStreamException, RecognitionException;
}



