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
     * @param namespace is the view namespace
     * @param name is the view name
     * @return {@link ViewFactory} instance
     * @throws ViewProcessingException if the view namespace or name cannot resolve
     */
    public ViewFactory create(String namespace, String name) throws ViewProcessingException;    
}
