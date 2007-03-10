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
        return new EPStatementSyntaxException(e.getMessage() + getPositionInfo(e), expression);
    }

    /**
     * Converts end-of-input error from a syntax error to a nice statement exception.
     * @param e is the syntax error
     * @param expression is the expression text
     * @param tokenNameExpected is the name or paraphrase of the expected token
     * @return syntax exception
     */
    public static EPStatementSyntaxException convertEndOfInput(RecognitionException e, String tokenNameExpected, String expression)
    {
        return new EPStatementSyntaxException("end of input when expecting " + tokenNameExpected + getPositionInfo(e), expression);
    }

    /**
     * Converts end-of-input error from a syntax error to a nice statement exception.
     * @param e is the syntax error
     * @param expression is the expression text
     * @return syntax exception
     */
    public static EPStatementSyntaxException convertEndOfInput(RecognitionException e, String expression)
    {
        return new EPStatementSyntaxException("end of input" + getPositionInfo(e), expression);
    }

    /**
     * Returns the position information string for a parser exception.
     * @param e is the parser exception
     * @return is a string with line and column information
     */
    public static String getPositionInfo(RecognitionException e)
    {
        return e.getLine() > 0 && e.getColumn() > 0
                ? " near line " + e.getLine() + ", column " + e.getColumn()
                : "";
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

