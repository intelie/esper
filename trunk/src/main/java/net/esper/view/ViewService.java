package net.esper.view;

import java.util.List;

/**
 * Service interface for creating views.
 */
public interface ViewService
{
    /**
     * Creates a chain of views returning the last view in the chain.
     * @param eventStream - the event stream that originates the raw events
     * @param viewSpecList - the specification for the chain to be created
     * @param context - dependent services
     * @return last view in chain
     * @throws ViewProcessingException thrown if a view cannot be created
     */
    public Viewable createView(EventStream eventStream,
                           List<ViewSpec> viewSpecList,
                           ViewServiceContext context)
        throws ViewProcessingException;

    /**
     * Removes a view discoupling the view and any of it's parent views up the tree to the last shared parent view.
     * @param eventStream - the event stream that originates the raw events
     * @param viewable - the view (should be the last in a chain) to remove
     */
    public void remove(EventStream eventStream, Viewable view);
}
