package net.esper.view;

/**
 * Views use this interface to indicate that the view requires services out of the context,
 * such as the scheduling service.
 */
public interface ContextAwareView
{
    /**
     * Set the services context containing service handles.
     * @param viewServiceContext with service handles
     */
    public void setViewServiceContext(ViewServiceContext viewServiceContext);

    /**
     * Returns the context instances used by the view.
     * @return context instance
     */
    public ViewServiceContext getViewServiceContext();

}
