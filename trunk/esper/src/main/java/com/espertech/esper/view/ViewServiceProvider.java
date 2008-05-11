package com.espertech.esper.view;

/**
 * Static factory for implementations of the {@link com.espertech.esper.view.ViewService} interface.
 */
public final class ViewServiceProvider
{
    /**
     * Creates an implementation of the ViewService interface.
     * @return implementation
     */
    public static ViewService newService()
    {
        return new ViewServiceImpl();
    }
}
