package net.esper.view;

import net.esper.event.EventType;

import java.util.List;

/**
 * Service interface for creating views.
 */
public interface ViewService
{
    /**
     * Returns a chain of view factories that can be used to obtain the final event type,
     * and that can later be used to actually create the chain of views or reuse existing views.
     * <p>
     * Does not actually hook up the view factories or views against the event stream, but creates view
     * factories and sets parameters on each view factory as supplied. Determines if
     * view factories are compatible in the chain via the attach method.
     * @param streamNum - the stream number starting at zero, a join would have N streams
     * @param parentEventType - is the event type of the event stream that originates the raw events
     * @param viewSpecList - the specification for each view factory in the chain to be created
     * @param context - dependent services
     * @return chain of view factories
     * @throws ViewProcessingException thrown if a view factory doesn't take parameters as supplied,
     * or cannot hook onto it's parent view or event stream
     */
    public ViewFactoryChain createFactories(int streamNum,
                                            EventType parentEventType, List<ViewSpec> viewSpecList,
                                            StatementServiceContext context)
            throws ViewProcessingException;

    /**
     * Creates the views given a chain of view factories.
     * <p>
     * Attempts to reuse compatible views under then parent event stream viewable as
     * indicated by each view factories reuse method.
     * @param eventStreamViewable is the event stream to hook into
     * @param viewFactoryChain defines the list of view factorys to call makeView or canReuse on
     * @param context provides services
     * @return last viewable in chain, or the eventStreamViewable if no view factories are supplied
     */
    public Viewable createViews(Viewable eventStreamViewable,
                                List<ViewFactory> viewFactoryChain,
                                StatementServiceContext context);

    /**
     * Removes a view discoupling the view and any of it's parent views up the tree to the last shared parent view.
     * @param eventStream - the event stream that originates the raw events
     * @param view - the view (should be the last in a chain) to remove
     */
    public void remove(EventStream eventStream, Viewable view);
}
