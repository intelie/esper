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



