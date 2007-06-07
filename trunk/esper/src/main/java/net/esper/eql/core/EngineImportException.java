package net.esper.eql.core;

/**
 * Indicates a problem importing classes, aggregation functions and the like.
 */
public class EngineImportException extends Exception
{
    /**
     * Ctor.
     * @param msg - exception message
     */
    public EngineImportException(String msg)
    {
        super(msg);
    }

    /**
     * Ctor.
     * @param msg - exception message
     * @param ex - inner exception
     */
    public EngineImportException(String msg, Exception ex)
    {
        super(msg, ex);
    }
}
