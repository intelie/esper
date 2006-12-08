package net.esper.view;

/**
 * Thrown to indicate a validation error in staggered views.
 */
public class ViewAttachException extends Exception
{
    /**
     * Ctor.
     * @param message - validation error message
     */
    public ViewAttachException(String message)
    {
        super(message);
    }
}
