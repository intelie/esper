package net.esper.view;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.collection.Pair;

/**
 * Utility methods to deal with chains of views, and for merge/group-by views.
 */
public class ViewServiceHelper
{
    /**
     * Add merge views for any views in the chain requiring a merge (group view).
     * Appends to the list of view specifications passed in one ore more
     * new view specifications that represent merge views.
     * Merge views have the same parameter list as the (group) view they merge data for.
     * @param specifications is a list of view definitions defining the chain of views.
     */
    protected static void addMergeViews(List<ViewSpec> specifications)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".addMergeViews Incoming specifications=" + Arrays.toString(specifications.toArray()));
        }

        LinkedList<ViewSpec> mergeViewSpecs = new LinkedList<ViewSpec>();

        for (ViewSpec spec : specifications)
        {
            ViewEnum viewEnum = ViewEnum.forName(spec.getObjectNamespace(), spec.getObjectName());
            if (viewEnum == null)
            {
                continue;
            }

            if (viewEnum.getMergeView() == null)
            {
                continue;
            }

            // The merge view gets the same parameters as the view that requires the merge
            ViewSpec mergeViewSpec = new ViewSpec(viewEnum.getMergeView().getNamespace(), viewEnum.getMergeView().getName(),
                    spec.getObjectParameters());

            // The merge views are added to the beginning of the list.
            // This enables group views to stagger ie. marketdata.group("symbol").group("feed").xxx.merge(...).merge(...)
            mergeViewSpecs.addFirst(mergeViewSpec);
        }

        specifications.addAll(mergeViewSpecs);

        if (log.isDebugEnabled())
        {
            log.debug(".addMergeViews Outgoing specifications=" + Arrays.toString(specifications.toArray()));
        }
    }

    /**
     * Instantiate a chain of views.
     * @param existingParentViews - parent views
     * @param parentViewable - parent view to add the chain to
     * @param specifications - view specification, one for each chain element
     * @param context - dependent services
     * @return chain of views instantiated
     * @throws ViewProcessingException is throw to indicate an error instantiating the chain
     */
    protected static List<View> instantiateChain(List<View> existingParentViews,
                                                 Viewable parentViewable,
                                                 List<ViewSpec> specifications,
                                                 ViewServiceContext context)
        throws ViewProcessingException
    {
        List<View> newViews = new LinkedList<View>();
        Viewable parent = parentViewable;

        for (ViewSpec spec : specifications)
        {
            // Create the new view object
            View currentView = ViewFactory.create(parent, spec);
            newViews.add(currentView);
            parent.addView(currentView);

            // Set context
            if (currentView instanceof ContextAwareView)
            {
                ((ContextAwareView) currentView).setViewServiceContext(context);
            }

            // New views get their ParentAwareView interface invoked if required
            if (currentView instanceof ParentAwareView)
            {
                List<View> parentViewList = new LinkedList<View>();
                parentViewList.addAll(existingParentViews);
                parentViewList.addAll(newViews);
                ((ParentAwareView) currentView).setParentAware(parentViewList);
            }

            // Next parent is the new view
            parent = currentView;
        }

        return newViews;
    }

    /**
     * Removes a view from a parent view returning the orphaned parent views in a list.
     * @param parentViewable - parent to remove view from
     * @param viewToRemove - view to remove
     * @return chain of orphaned views
     */
    protected static List<View> removeChainLeafView(Viewable parentViewable,
                                            Viewable viewToRemove)
    {
        List<View> removedViews = new LinkedList<View>();

        // The view to remove must be a leaf node - non-leaf views are just not removed
        if (viewToRemove.hasViews())
        {
            return removedViews;
        }

        // Find child viewToRemove among descendent views
        List<View> viewPath = ViewSupport.findDescendent(parentViewable, viewToRemove);

        if (viewPath == null)
        {
            String message = "Viewable not found when removing view " + viewToRemove;
            throw new IllegalArgumentException(message);
        }

        // The viewToRemove is a direct child view of the stream
        if (viewPath.size() == 0)
        {
            boolean isViewRemoved = parentViewable.removeView( (View) viewToRemove);

            if (!isViewRemoved)
            {
                String message = "Failed to remove immediate child view " + viewToRemove;
                log.fatal(".remove " + message);
                throw new IllegalStateException(message);
            }

            removedViews.add((View) viewToRemove);
            return removedViews;
        }

        View[] viewPathArray = viewPath.toArray(new View[0]);
        View currentView = (View) viewToRemove;

        // Remove child from parent views until a parent view has more children,
        // or there are no more parents (index=0).
        for (int index = viewPathArray.length - 1; index >= 0; index--)
        {
            boolean isViewRemoved = viewPathArray[index].removeView(currentView);
            removedViews.add(currentView);

            if (!isViewRemoved)
            {
                String message = "Failed to remove view " + currentView;
                log.fatal(".remove " + message);
                throw new IllegalStateException(message);
            }

            // If the parent views has more child views, we are done
            if (viewPathArray[index].hasViews())
            {
                break;
            }

            // The parent of the top parent is the stream, remove from stream
            if (index == 0)
            {
                parentViewable.removeView(viewPathArray[0]);
                removedViews.add(viewPathArray[0]);
            }
            else
            {
                currentView = viewPathArray[index];
            }
        }

        return removedViews;
    }

    /**
     * Match the views under the stream to the list of view specications passed in.
     * The method changes the view specifications list passed in and removes those
     * specifications for which matcing views have been found.
     * If none of the views under the stream matches the first view specification passed in,
     * the method returns the stream itself and leaves the view specification list unchanged.
     * If one view under the stream matches, the view's specification is removed from the list.
     * The method will then attempt to determine if any child views of that view also match
     * specifications.
     * @param rootViewable is the top rootViewable event stream to which all views are attached as child views
     * @param specificationRepository is a map of view and specification that enables view specification comparison
     * @param specifications is the non-empty list of specifications describing the new chain of views to create.
     * This parameter is changed by this method, ie. specifications are removed if they match existing views.
     * @return a pair of (A) the stream if no views matched, or the last child view that matched (B) the full list
     * of parent views
     */
    protected static Pair<Viewable, List<View>> matchExistingViews(Viewable rootViewable,
                                                 Map<View, ViewSpec> specificationRepository,
                                                 List<ViewSpec> specifications)
    {
        Viewable currentParent = rootViewable;
        List<View> matchedViewList = new LinkedList<View>();

        boolean foundMatch = false;

        do      // while ((foundMatch) && (specifications.size() > 0));
        {
            foundMatch = false;

            for (View childView : currentParent.getViews())
            {
                ViewSpec spec = specificationRepository.get(childView);

                // It's possible that a child view is not known to this service since the
                // child view may not be reusable, such as a stateless filter (where-clause),
                // output rate limiting view, or such.
                // Continue and ignore that child view if it's view specification is not known.
                if (spec == null)
                {
                    continue;
                }

                // If the specification is found equal, remove
                if (spec.equals(specifications.get(0)))
                {
                    specifications.remove(0);
                    currentParent = childView;
                    foundMatch = true;
                    matchedViewList.add(childView);
                    break;
                }
            }
        }
        while ((foundMatch) && (specifications.size() > 0));

        return new Pair<Viewable, List<View>>(currentParent, matchedViewList);
    }

    private static final Log log = LogFactory.getLog(ViewServiceHelper.class);
}
