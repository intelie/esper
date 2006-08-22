package net.esper.eql.expression;

/**
 * Indicates a property exists in multiple streams.
 */
public class DuplicatePropertyException extends StreamTypesException
{
    /**
     * Ctor.
     * @param msg - exception message
     */
    public DuplicatePropertyException(String msg)
    {
        super(msg);
    }
}
