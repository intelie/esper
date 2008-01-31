package com.espertech.esper.eql.core;

/**
 * Indicates a problem importing classes, aggregation functions and the like.
 */
public class EngineImportUndefinedException extends Exception
{
    /**
     * Ctor.
     * @param msg - exception message
     */
    public EngineImportUndefinedException(String msg)
    {
        super(msg);
    }

    /**
     * Ctor.
     * @param msg - exception message
     * @param ex - inner exception
     */
    public EngineImportUndefinedException(String msg, Exception ex)
    {
        super(msg, ex);
    }
}
