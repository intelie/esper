using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.events;
using net.esper.eql.spec;

namespace net.esper.view
{
	/// <summary> Service interface for creating views.</summary>
    public interface ViewService
    {
	    /// <summary>
	    /// Returns a chain of view factories that can be used to obtain the final event type,
	    /// and that can later be used to actually create the chain of views or reuse existing views.
	    /// <p>
	    /// Does not actually hook up the view factories or views against the event stream, but creates view
	    /// factories and sets parameters on each view factory as supplied. Determines if
	    /// view factories are compatible in the chain via the attach method.
		/// </p>
	    /// </summary>
	    /// <param name="streamNum">
	    /// the stream number starting at zero, a join would have N streams
	    /// </param>
	    /// <param name="parentEventType">
	    /// the event type of the event stream that originates the raw events
	    /// </param>
	    /// <param name="viewSpecList">
	    /// the specification for each view factory in the chain to be created
	    /// </param>
	    /// <param name="context">dependent services</param>
	    /// <returns>chain of view factories</returns>
	    /// <throws>
	    /// ViewProcessingException thrown if a view factory doesn't take parameters as supplied,
	    /// or cannot hook onto it's parent view or event stream
	    /// </throws>
	    ViewFactoryChain CreateFactories(int streamNum,
                                         EventType parentEventType,
										 IList<ViewSpec> viewSpecList,
                                         StatementContext context);

	    /// <summary>
	    /// Creates the views given a chain of view factories.
	    /// &lt;p&gt;
	    /// Attempts to reuse compatible views under then parent event stream viewable as
	    /// indicated by each view factories reuse method.
	    /// </summary>
	    /// <param name="eventStreamViewable">is the event stream to hook into</param>
	    /// <param name="viewFactoryChain">
	    /// defines the list of view factorys to call makeView or canReuse on
	    /// </param>
	    /// <param name="context">provides services</param>
	    /// <returns>
	    /// last viewable in chain, or the eventStreamViewable if no view factories are supplied
	    /// </returns>
	    Viewable CreateViews(Viewable eventStreamViewable,
	                         IList<ViewFactory> viewFactoryChain,
	                         StatementContext context);

        /// <summary> Removes a view discoupling the view and any of it's parent views up the tree to the last shared parent view.</summary>
        /// <param name="eventStream">the event stream that originates the raw events
        /// </param>
        /// <param name="view">the view (should be the last in a chain) to remove
        /// </param>
        void Remove(EventStream eventStream, Viewable view);
    }
}
