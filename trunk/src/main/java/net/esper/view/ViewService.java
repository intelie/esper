package net.esper.view;

import net.esper.event.EventType;

import java.util.List;

/**
 * Service interface for creating views.
 */
public interface ViewService
{
    /**
     * ViewFactoryFactory responsibilities:
     * (1) return event type of resulting view
     *      --> now expressions can resolve, allowing expression to indicate additional view requirements
     * (2) check if an existing view satisfies view requirements (view spec plus additional view requirements)
     *      --> this can now be a bit more flexible in determining if a re-use is possible
     * (3) create new view instance
     *
     * public UnmaterializedViewable (EventStream eventStream, List<ViewSpec> viewSpecList)
     *      UnmaterializedView.getEventType();
     *      Unmaterialized: List<ViewFactoryFactory>
     *
     * public Viewable materialize (UnmaterializedViewable unmaterialized, ViewServiceContext context)
     *
     * interface ExprValidator
     *      public void validate(StreamTypeService, AutoImportService, ViewResourceReqBlackboard)
     *
     *      public ViewResourceReqBlackboard(UnmaterializedViewable[])    // Ctor
     * interface ViewResourceReqBlackboard
     *      public boolean requestFinalView(int stream, RandomAccessViewResource.class, ViewResourceCallback)
     *
     * interface ViewResourceCallback
     *      public void setResource(ViewResource viewResource)
     *
     *      public ViewFactoryImpl(List<Object> viewParameters, EventType parentEventType)     // Ctor
     *
     * interface ViewFactoryFactory
     *      public EventType getEventType()
     *
     *      public boolean providesCapability(Class capability);
     *      public boolean addCapability(Class capability, ViewResourceCallback);
     *
     *      public boolean equals(...);
     *      public boolean canReuse(View view)
     *
     *      public Viewable makeView();
     *
     *
     *
     * Returns the final view's event type in a chain of views defined by the given event stream and
     * view specifications for each view in the chain.
     * <p>
     * Does not actually hook up the views against the event stream, but creates views
     * to determine if views can hook up with each other, and returns the final view's event type.
     * <p>
     * Since views are expected to be lazy-init, view construction is not considered
     * an expensive operation.
     * @param eventStream - the event stream that originates the raw events
     * @param viewSpecList - the specification for the chain to be created
     * @param context - dependent services
     * @return event type of last view in chain
     * @throws ViewProcessingException thrown if a view cannot be, or views refuse to hook onto each other
    public EventType createUnmaterialized(EventStream eventStream,
                           List<ViewSpec> viewSpecList,
                           ViewServiceContext context)
        throws ViewProcessingException;
     */

    public ViewFactoryChain createFactories(EventType parentEventType, List<ViewSpec> viewSpecList, ViewServiceContext context)
            throws ViewProcessingException;

    public Viewable createViews(Viewable eventStreamViewable,
                                List<ViewFactory> viewFactoryChain,
                                ViewServiceContext context);

    /**
     * Creates a chain of views returning the last view in the chain.
     * @param eventStream - the event stream that originates the raw events
     * @param viewSpecList - the specification for the chain to be created
     * @param context - dependent services
     * @return last view in chain
     * @throws ViewProcessingException thrown if a view cannot be created
    public Viewable createView(EventStream eventStream,
                           List<ViewSpec> viewSpecList,
                           ViewServiceContext context)
        throws ViewProcessingException;
     */

    /**
     * Removes a view discoupling the view and any of it's parent views up the tree to the last shared parent view.
     * @param eventStream - the event stream that originates the raw events
     * @param view - the view (should be the last in a chain) to remove
     */
    public void remove(EventStream eventStream, Viewable view);
}
