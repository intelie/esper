package net.esper.view.factory;

/**
 * Thrown to indicate a validation error in view parameterization.
 */
public class ViewParameterException extends Exception
{
    /**
     * Ctor.
     * @param message - validation error message
     */
    public ViewParameterException(String message)
    {
        super(message);
    }
}
