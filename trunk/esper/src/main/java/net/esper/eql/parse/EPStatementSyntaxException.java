package net.esper.eql.parse;

import net.esper.client.EPException;
import net.esper.client.EPStatementException;
import antlr.TokenStreamException;
import antlr.RecognitionException;

/**
 * This exception is thrown to indicate a problem in statement creation.
 */
public class EPStatementSyntaxException extends EPStatementException
{
    /**
     * Ctor.
     * @param message - error message
     * @param expression - expression text
     */
    public EPStatementSyntaxException(String message, String expression)
    {
        super(message, expression);
    }

    /**
     * Converts from a syntax error to a nice statement exception.
     * @param e is the syntax error
     * @param expression is the expression text
     * @return syntax exception
     */
    public static EPStatementSyntaxException convert(RecognitionException e, String expression)
    {
        String positionInfo = e.getLine() > 0 && e.getColumn() > 0
                ? " near line " + e.getLine() + ", column " + e.getColumn()
                : "";
        return new EPStatementSyntaxException(e.getMessage() + positionInfo, expression);
    }

    /**
     * Converts from a syntax (token stream) error to a nice statement exception.
     * @param e is the syntax error
     * @param expression is the expression text
     * @return syntax exception
     */
    public static EPStatementSyntaxException convert(TokenStreamException e, String expression)
    {
        return new EPStatementSyntaxException(e.getMessage(), expression);
    }
}

