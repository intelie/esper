package net.esper.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.collection.Pair;
import net.esper.event.EventType;

/**
 * Implementation of the view evaluation service business interface.
 */
public final class ViewServiceImpl implements ViewService
{
    /**
     * Ctor.
     */
    public ViewServiceImpl()
    {
    }

    public ViewFactoryChain createFactories(EventType parentEventType,
                                            List<ViewSpec> viewSpecDefinitions,
                                            ViewServiceContext context)
            throws ViewProcessingException
    {
        // Clone the view spec list to prevent parameter modification
        List<ViewSpec> viewSpecList = new ArrayList<ViewSpec>(viewSpecDefinitions);

        // Inspect views and add merge views if required
        ViewServiceHelper.addMergeViews(viewSpecList);

        // Instantiate factories, not making them aware of each other yet
        List<ViewFactory> viewFactories = ViewServiceHelper.instantiateFactoryChain(viewSpecList, context);

        ViewFactory parentViewFactory = null;
        for (int i = 0; i < viewFactories.size(); i++)
        {
            try
            {
                viewFactories.get(i).attach(parentEventType, context, parentViewFactory);
            }
            catch (ViewAttachException ex)
            {
                String text = "Error attaching view to parent view";
                if (i == 0)
                {
                    text = "Error attaching view to event stream";
                }
                throw new ViewProcessingException(text + ": " + ex.getMessage(), ex); 
            }
        }

        return new ViewFactoryChain(parentEventType, viewFactories);
    }

    public Viewable createViews(Viewable eventStreamViewable,
                                List<ViewFactory> viewFactories,
                                ViewServiceContext context)
    {
        // Attempt to find existing views under the stream that match specs.
        // The viewSpecList may have been changed by this method.
        Pair<Viewable, List<View>> resultPair = ViewServiceHelper.matchExistingViews(eventStreamViewable, viewFactories);

        Viewable parentViewable = resultPair.getFirst();
        List<View> existingParentViews = resultPair.getSecond();

        if (viewFactories.size() == 0)
        {
            if (log.isDebugEnabled())
            {
                log.debug(".createView No new views created, dumping stream ... " + eventStreamViewable);
                ViewSupport.dumpChildViews("EventStream ", eventStreamViewable);
            }

            return parentViewable;   // we know its a view here since the factory list is empty
        }

        // Instantiate remaining chain of views from the remaining factories which didn't match to existing views.
        List<View> views = ViewServiceHelper.instantiateChain(existingParentViews, parentViewable, viewFactories, context);

        if (log.isDebugEnabled())
        {
            log.debug(".createView New views created for stream, all views ... " + eventStreamViewable);
            ViewSupport.dumpChildViews("EventStream ", eventStreamViewable);
        }

        View lastView = views.get(views.size() - 1);

        return lastView;
    }

    /*
    public Viewable createView(EventStream eventStream,
                               List<ViewSpec> viewSpecDefinitions,
                               ViewServiceContext context) throws ViewProcessingException
    {
        // Clone the view spec list to prevent parameter modification
        List<ViewSpec> viewSpecList = new LinkedList<ViewSpec>(viewSpecDefinitions);

        // Inspect views and add merge views if required
        ViewServiceHelper.addMergeViews(viewSpecList);

        Map<View, ViewSpec> existingViewSpecs = streams.get(eventStream);

        // Create new stream if none existed for this event type and key
        if (existingViewSpecs == null)
        {
            existingViewSpecs = new HashMap<View, ViewSpec>();
            streams.put(eventStream, existingViewSpecs);
        }

        // Attempt to find existing views under the stream that match specs.
        // The viewSpecList may have been changed by this method.
        Pair<Viewable, List<View>> resultPair = ViewServiceHelper.matchExistingViews(eventStream,
                existingViewSpecs, viewSpecList);
        Viewable parentViewable = resultPair.getFirst();
        List<View> existingParentViews = resultPair.getSecond();

        if (viewSpecList.size() == 0)
        {
            if (log.isDebugEnabled())
            {
                log.debug(".createView No new views created, dumping stream ... " + eventStream);
                ViewSupport.dumpChildViews("EventStream ", eventStream);
            }

            return parentViewable;   // we know its a view here since the spec list is empty 
        }

        // Instantiate remaining chain of views from the remaining specifications which didn't match to existing views.
        // Could return an empty list if exactly the same view already exists.
        List<View> views = ViewServiceHelper.instantiateChain(existingParentViews, parentViewable, viewSpecList, context);

        // Populate map of view specifications.
        if (views.size() != viewSpecList.size())
        {
            throw new IllegalStateException("Instantiated view list does not match view spec list");
        }

        // Add new views to table
        for (int i = 0; i < views.size(); i++)
        {
            View view = views.get(i);

            ViewSpec spec = viewSpecList.get(i);
            existingViewSpecs.put(view, spec);
        }

        if (log.isDebugEnabled())
        {
            log.debug(".createView New views created for stream, all views ... " + eventStream);
            ViewSupport.dumpChildViews("EventStream ", eventStream);
        }

        View lastView = views.get(views.size() - 1);

        return lastView;
    }
    */

    public void remove(EventStream eventStream, Viewable viewToRemove)
    {
        // If the viewToRemove to remove has child viewToRemove, don't disconnect - the child viewToRemove(s) need this
        if (viewToRemove.hasViews())
        {
            return;
        }

        if (log.isDebugEnabled())
        {
            log.debug(".remove Views before the remove of view " + viewToRemove + ", for event stream " + eventStream);
            ViewSupport.dumpChildViews("EventStream ", eventStream);
        }

        // Remove views in chain leaving only non-empty parent views to the child view to be removed
        ViewServiceHelper.removeChainLeafView(eventStream, viewToRemove);

        if (log.isDebugEnabled())
        {
            log.debug(".remove Views after the remove, for event stream " + eventStream);
            ViewSupport.dumpChildViews("EventStream ", eventStream);
        }
    }

    private static final Log log = LogFactory.getLog(ViewServiceImpl.class);
}
