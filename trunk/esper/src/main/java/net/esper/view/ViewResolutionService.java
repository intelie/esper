package net.esper.view;

/**
 * Factory service for resolving view names and for creating view instances based on a view specification including view name and namespace. 
 */
public interface ViewResolutionService
{
    /**
     * Instantiates a {@link ViewFactory} based on the view namespace and name stored in the view spec.
     * <p>
     * Does not actually use the view factory object created.
     * @param spec contains view name and namespace
     * @return {@link ViewFactory} instance
     * @throws ViewProcessingException if the view namespace or name cannot resolve
     */
    public ViewFactory create(ViewSpec spec) throws ViewProcessingException;
}
