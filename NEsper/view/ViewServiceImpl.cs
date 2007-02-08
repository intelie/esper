using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.view
{
    /// <summary>
    /// Implementation of the view evaluation service business interface.
    /// </summary>

    public sealed class ViewServiceImpl : ViewService
    {
        private EDictionary<EventStream, IDictionary<View, ViewSpec>> streams;

        /// <summary> Ctor.</summary>
        public ViewServiceImpl()
        {
            streams = new EHashDictionary<EventStream, IDictionary<View, ViewSpec>>();
        }

        public Viewable CreateView(EventStream eventStream, IList<ViewSpec> viewSpecDefinitions, ViewServiceContext context)
        {
            // Clone the view spec list to prevent parameter modification
            IList<ViewSpec> viewSpecList = new ELinkedList<ViewSpec>(viewSpecDefinitions);

            // Inspect views and add merge views if required
            ViewServiceHelper.AddMergeViews(viewSpecList);

            IDictionary<View, ViewSpec> existingViewSpecs = streams.Fetch(eventStream, null);

            // Create new stream if none existed for this event type and key
            if (existingViewSpecs == null)
            {
                existingViewSpecs = new Dictionary<View, ViewSpec>();
                streams[eventStream] = existingViewSpecs;
            }

            // Attempt to find existing views under the stream that match specs.
            // The viewSpecList may have been changed by this method.
            Pair<Viewable, IList<View>> resultPair = ViewServiceHelper.MatchExistingViews(eventStream, existingViewSpecs, viewSpecList);
            Viewable parentViewable = resultPair.First;
            IList<View> existingParentViews = resultPair.Second;

            if (viewSpecList.Count == 0)
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(".createView No new views created, dumping stream ... " + eventStream);
                    ViewSupport.dumpChildViews("EventStream ", eventStream);
                }

                return parentViewable; // we know its a view here since the spec list is empty 
            }

            // Instantiate remaining chain of views from the remaining specifications which didn't match to existing views.
            // Could return an empty list if exactly the same view already exists.
            IList<View> views = ViewServiceHelper.InstantiateChain(existingParentViews, parentViewable, viewSpecList, context);

            // Populate map of view specifications.
            if (views.Count != viewSpecList.Count)
            {
                throw new SystemException("Instantiated view list does not match view spec list");
            }

            // Add new views to table
            for (int i = 0; i < views.Count; i++)
            {
                View view = views[i];

                ViewSpec spec = viewSpecList[i];
                existingViewSpecs[view] = spec;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".createView New views created for stream, all views ... " + eventStream);
                ViewSupport.dumpChildViews("EventStream ", eventStream);
            }

            View lastView = views[views.Count - 1];

            return lastView;
        }

        public void Remove(EventStream eventStream, Viewable viewToRemove)
        {
            // If the viewToRemove to remove has child viewToRemove, don't disconnect - the child viewToRemove(s) need this viewToRemove
            if (viewToRemove.HasViews())
            {
                return;
            }

            // Get view specifications kept for this stream
            IDictionary<View, ViewSpec> existingViewSpecs = streams.Fetch(eventStream, null);
            if (existingViewSpecs == null)
            {
                String message = "Stream information not found for event stream " + eventStream;
                log.Fatal(".remove " + message);
                throw new ArgumentException(message);
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".remove Views before the remove of view " + viewToRemove + ", for event stream " + eventStream);
                ViewSupport.dumpChildViews("EventStream ", eventStream);
            }

            // Remove views in chain leaving only non-empty parent views to the child view to be removed
            IList<View> removedViews = ViewServiceHelper.RemoveChainLeafView(eventStream, viewToRemove);
            foreach (View view in removedViews)
            {
                existingViewSpecs.Remove(view);
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".remove Views after the remove, for event stream " + eventStream);
                ViewSupport.dumpChildViews("EventStream ", eventStream);
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(ViewServiceImpl));
    }
}
