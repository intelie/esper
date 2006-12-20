package net.esper.eql.parse;

import net.esper.client.EPException;
import net.esper.client.EPStatementException;
import antlr.TokenStreamException;
import antlr.RecognitionException;

/**
 * This exception is thrown to indicate a problem in statement creation.
 */
public class ASTWalkException extends RuntimeException
{
    /**
     * Ctor.
     * @param message is the error message
     * @param t is the inner throwable
     */
    public ASTWalkException(String message, Throwable t)
    {
        super(message, t);
    }

    /**
     * Ctor.
     * @param message is the error message
     */
    public ASTWalkException(String message)
    {
        super(message);
    }

}

