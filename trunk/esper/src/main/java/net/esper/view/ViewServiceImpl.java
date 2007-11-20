package net.esper.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.collection.Pair;
import net.esper.event.EventType;
import net.esper.core.StatementContext;
import net.esper.eql.spec.ViewSpec;

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

    public ViewFactoryChain createFactories(int streamNum,
                                            EventType parentEventType,
                                            List<ViewSpec> viewSpecDefinitions,
                                            StatementContext context)
            throws ViewProcessingException
    {
        // Clone the view spec list to prevent parameter modification
        List<ViewSpec> viewSpecList = new ArrayList<ViewSpec>(viewSpecDefinitions);

        // Inspect views and add merge views if required
        ViewServiceHelper.addMergeViews(viewSpecList);

        // Instantiate factories, not making them aware of each other yet
        List<ViewFactory> viewFactories = ViewServiceHelper.instantiateFactories(streamNum, viewSpecList, context);

        ViewFactory parentViewFactory = null;
        List<ViewFactory> attachedViewFactories = new LinkedList<ViewFactory>();
        for (int i = 0; i < viewFactories.size(); i++)
        {
            ViewFactory factoryToAttach = viewFactories.get(i);
            try
            {
                factoryToAttach.attach(parentEventType, context, parentViewFactory, attachedViewFactories);
                attachedViewFactories.add(viewFactories.get(i));
                parentEventType = factoryToAttach.getEventType(); 
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
                                StatementContext context)
    {
        // Attempt to find existing views under the stream that match specs.
        // The viewSpecList may have been changed by this method.
        Pair<Viewable, List<View>> resultPair = ViewServiceHelper.matchExistingViews(eventStreamViewable, viewFactories);

        Viewable parentViewable = resultPair.getFirst();

        if (viewFactories.isEmpty())
        {
            if (log.isDebugEnabled())
            {
                log.debug(".createView No new views created, dumping stream ... " + eventStreamViewable);
                ViewSupport.dumpChildViews("EventStream ", eventStreamViewable);
            }

            return parentViewable;   // we know its a view here since the factory list is empty
        }

        // Instantiate remaining chain of views from the remaining factories which didn't match to existing views.
        List<View> views = ViewServiceHelper.instantiateChain(parentViewable, viewFactories, context);

        // Initialize any views that need initializing after the chain is complete
        for (View view : views)
        {
            if (view instanceof InitializableView)
            {
                InitializableView initView = (InitializableView) view;
                initView.initialize();
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(".createView New views created for stream, all views ... " + eventStreamViewable);
            ViewSupport.dumpChildViews("EventStream ", eventStreamViewable);
        }

        return views.get(views.size() - 1);
    }

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
