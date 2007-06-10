///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.collection;
using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.view;
using net.esper.view.window;

namespace net.esper.view.internals
{
	/// <summary>Factory for making {@link PriorEventView} instances.</summary>
	public class PriorEventViewFactory : ViewFactory
	{
	    private ETreeDictionary<int, List<ViewResourceCallback>> callbacksPerIndex = new ETreeDictionary<int, List<ViewResourceCallback>>();
	    private EventType eventType;
	    private readonly bool isUnbound;

	    /// <summary>Ctor.</summary>
	    /// <param name="unbound">
	    /// to indicate the we are not receiving remove stream events (unbound stream, stream without child
	    /// views) therefore must use a different buffer.
	    /// </param>
	    public PriorEventViewFactory(bool unbound)
	    {
	        isUnbound = unbound;
	    }

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        throw new UnsupportedOperationException("View not available through EQL");
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        eventType = parentEventType;
	    }

	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        if (viewCapability is ViewCapPriorEventAccess)
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }

	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        if (!CanProvideCapability(viewCapability))
	        {
	        	throw new UnsupportedOperationException("View capability " + viewCapability.GetType().FullName + " not supported");
	        }

	        // Get the index requested, such as the 8th prior event
	        ViewCapPriorEventAccess requested = (ViewCapPriorEventAccess) viewCapability;
	        int reqIndex = requested.IndexConstant;

	        // Store in a list per index such that we can consolidate this into a single buffer
	        List<ViewResourceCallback> callbackList = callbacksPerIndex.Fetch(reqIndex);
	        if (callbackList == null)
	        {
	            callbackList = new LinkedList<ViewResourceCallback>();
	            callbacksPerIndex.Put(reqIndex, callbackList);
	        }
	        callbackList.Add(resourceCallback);
	    }

	    public View MakeView(StatementContext statementContext)
	    {
	        ViewUpdatedCollection viewUpdatedCollection = null;

	        if (callbacksPerIndex.Count == 0)
	        {
	            throw new IllegalStateException("No resources requested");
	        }

	        // Construct an array of requested prior-event indexes (such as 10th prior event, 8th prior = {10, 8})
	        int[] requested = new int[callbacksPerIndex.Count];
	        int count = 0;
	        foreach (int reqIndex in callbacksPerIndex.Keys)
	        {
	            requested[count++] = reqIndex;
	        }


	        // For unbound streams the buffer is strictly rolling new events
	        if (isUnbound)
	        {
	            viewUpdatedCollection = new PriorEventBufferUnbound(callbacksPerIndex.LastKey);
	        }
	        // For bound streams (with views posting old and new data), and if only one prior index requested
	        else if (requested.Length == 1)
	        {
	            viewUpdatedCollection = new PriorEventBufferSingle(requested[0]);
	        }
	        else
	        {
	            // For bound streams (with views posting old and new data)
	            // Multiple prior event indexes requested, such as "Prior(2, price), Prior(8, price)"
	            // Sharing a single viewUpdatedCollection for multiple prior-event indexes
	            viewUpdatedCollection = new PriorEventBufferMulti(requested);
	        }

	        // Since an expression such as "Prior(2, price), Prior(8, price)" translates
	        // into {2, 8} the relative index is {0, 1}.
	        // Map the expression-supplied index to a relative viewUpdatedCollection-known index via wrapper
	        int relativeIndex = 0;
	        foreach (int reqIndex in callbacksPerIndex.Keys)
	        {
	            List<ViewResourceCallback> callbacks = callbacksPerIndex.Fetch(reqIndex);
	            foreach (ViewResourceCallback callback in callbacks)
	            {
	                if (viewUpdatedCollection is RelativeAccessByEventNIndex)
	                {
	                    RelativeAccessByEventNIndex relativeAccess = (RelativeAccessByEventNIndex) viewUpdatedCollection;
	                    callbackViewResource = new RelativeAccessImpl(relativeAccess, relativeIndex);
	                }
	                else
	                {
	                    callbackViewResource = viewUpdatedCollection;
	                }
	            }
	            relativeIndex++;
	        }

	        PriorEventView priorEventView = new PriorEventView(viewUpdatedCollection);
	        return priorEventView;
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        return false;
	    }

	    /// <summary>Adapter to provide access given an index.</summary>
	    public class RelativeAccessImpl : RelativeAccessByEventNIndex
	    {
	        private readonly RelativeAccessByEventNIndex buffer;
            private readonly int relativeIndex;

	        /// <summary>Ctor.</summary>
	        /// <param name="buffer">is the buffer to acces</param>
	        /// <param name="relativeIndex">is the index to pull out</param>
	        public RelativeAccessImpl(RelativeAccessByEventNIndex buffer, int relativeIndex)
	        {
	            this.buffer = buffer;
	            this.relativeIndex = relativeIndex;
	        }

	        public EventBean GetRelativeToEvent(EventBean _event, int prevIndex)
	        {
	            return buffer.GetRelativeToEvent(_event, relativeIndex);
	        }
	    }
	}
} // End of namespace
