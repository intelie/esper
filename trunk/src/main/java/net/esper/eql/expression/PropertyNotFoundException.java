package net.esper.eql.expression;

/**
 * Exception to indicate that a property name used in a filter doesn't resolve.
 */
public class PropertyNotFoundException extends StreamTypesException
{
    /**
     * Ctor.
     * @param msg - message
     */
    public PropertyNotFoundException(String msg)
    {
        super(msg);
    }
}
