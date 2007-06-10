using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.core;
using net.esper.eql.spec;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.view
{
    /// <summary>
    /// Implementation of the view evaluation service business interface.
    /// </summary>

    public sealed class ViewServiceImpl : ViewService
    {
        /// <summary> Ctor.</summary>
        public ViewServiceImpl()
        {
        }

	    public ViewFactoryChain CreateFactories(int streamNum,
	                                            EventType parentEventType,
	                                            IList<ViewSpec> viewSpecDefinitions,
	                                            StatementContext context)
	    {
	        // Clone the view spec list to prevent parameter modification
	        IList<ViewSpec> viewSpecList = new List<ViewSpec>(viewSpecDefinitions);

	        // Inspect views and add merge views if required
	        ViewServiceHelper.AddMergeViews(viewSpecList);

	        // Instantiate factories, not making them aware of each other yet
	        IList<ViewFactory> viewFactories = ViewServiceHelper.InstantiateFactories(streamNum, viewSpecList, context);

	        ViewFactory parentViewFactory = null;
	        IList<ViewFactory> attachedViewFactories = new List<ViewFactory>();
	        for (int i = 0; i < viewFactories.Count; i++)
	        {
	            ViewFactory factoryToAttach = viewFactories[i];
	            try
	            {
	                factoryToAttach.Attach(parentEventType, context, parentViewFactory, attachedViewFactories);
	                attachedViewFactories.Add(viewFactories[i]);
	                parentEventType = factoryToAttach.EventType; 
	            }
	            catch (ViewAttachException ex)
	            {
	                String text = "Error attaching view to parent view";
	                if (i == 0)
	                {
	                    text = "Error attaching view to event stream";
	                }
	                throw new ViewProcessingException(text + ": " + ex.Message, ex); 
	            }
	        }

	        return new ViewFactoryChain(parentEventType, viewFactories);
	    }

	    public Viewable CreateViews(Viewable eventStreamViewable,
	                                IList<ViewFactory> viewFactories,
	                                StatementContext context)
	    {
	        // Attempt to find existing views under the stream that match specs.
	        // The viewSpecList may have been changed by this method.
	        Pair<Viewable, IList<View>> resultPair = ViewServiceHelper.MatchExistingViews(eventStreamViewable, viewFactories);

	        Viewable parentViewable = resultPair.First;
	        IList<View> existingParentViews = resultPair.Second;

	        if (viewFactories.Count == 0)
	        {
	            if (log.IsDebugEnabled)
	            {
	                log.Debug(".CreateView No new views created, dumping stream ... " + eventStreamViewable);
	                ViewSupport.DumpChildViews("EventStream ", eventStreamViewable);
	            }

	            return parentViewable;   // we know its a view here since the factory list is empty
	        }

	        // Instantiate remaining chain of views from the remaining factories which didn't match to existing views.
	        IList<View> views = ViewServiceHelper.InstantiateChain(parentViewable, viewFactories, context);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".createView New views created for stream, all views ... " + eventStreamViewable);
	            ViewSupport.DumpChildViews("EventStream ", eventStreamViewable);
	        }

	        View lastView = views[views.Count - 1];

	        return lastView;
	    }

	    public void Remove(EventStream eventStream, Viewable viewToRemove)
	    {
	        // If the viewToRemove to remove has child viewToRemove, don't disconnect - the child viewToRemove(s) need this
	        if (viewToRemove.HasViews)
	        {
	            return;
	        }

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".remove Views before the remove of view " + viewToRemove + ", for event stream " + eventStream);
	            ViewSupport.DumpChildViews("EventStream ", eventStream);
	        }

	        // Remove views in chain leaving only non-empty parent views to the child view to be removed
	        ViewServiceHelper.RemoveChainLeafView(eventStream, viewToRemove);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".Remove Views after the remove, for event stream " + eventStream);
	            ViewSupport.DumpChildViews("EventStream ", eventStream);
	        }		
	    }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
