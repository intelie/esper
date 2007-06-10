using System;
using System.Collections.Generic;
using System.Reflection;

using net.esper.core;
using net.esper.eql.core;
using net.esper.events;

using LogFactory = org.apache.commons.logging.LogFactory;
using Log = org.apache.commons.logging.Log;

namespace net.esper.view
{
	/// <summary>
    /// Static factory for creating view instances based on a view specification and a given parent view.
    /// </summary>

    public interface ViewFactory
	{
	    /// <summary>Indicates user EQL query view parameters to the view factory.</summary>
	    /// <param name="viewFactoryContext">
	    /// supplied context information for the view factory
	    /// </param>
	    /// <param name="viewParameters">is the objects representing the view parameters</param>
	    /// <throws>
	    /// ViewParameterException if the parameters don't match view parameter needs
	    /// </throws>
	    void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters);

	    /// <summary>
	    /// Attaches the factory to a parent event type such that the factory can validate
	    /// attach requirements and determine an event type for resulting views.
	    /// </summary>
	    /// <param name="parentEventType">
	    /// is the parent event stream's or view factory's event type
	    /// </param>
	    /// <param name="statementContext">
	    /// contains the services needed for creating a new event type
	    /// </param>
	    /// <param name="optionalParentFactory">
	    /// is null when there is no parent view factory, or contains the
	    /// parent view factory
	    /// </param>
	    /// <param name="parentViewFactories">
	    /// is a list of all the parent view factories or empty list if there are none
	    /// </param>
	    /// <throws>
	    /// ViewAttachException is thrown to indicate that this view factories's view would not play
	    /// with the parent view factories view
	    /// </throws>
	    void Attach(EventType parentEventType,
	                StatementContext statementContext,
	                ViewFactory optionalParentFactory,
					IList<ViewFactory> parentViewFactories);

	    /// <summary>
	    /// Returns true if the view factory can make views that provide a view resource with the
	    /// given capability.
	    /// </summary>
	    /// <param name="viewCapability">is the view resource needed</param>
	    /// <returns>
	    /// true to indicate that the view can provide the resource, or false if not
	    /// </returns>
	    bool CanProvideCapability(ViewCapability viewCapability);

	    /// <summary>
	    /// Indicates to the view factory to provide the view resource indicated.
	    /// </summary>
	    /// <param name="viewCapability">is the required resource descriptor</param>
	    /// <param name="resourceCallback">
	    /// is the callback to use to supply the resource needed
	    /// </param>
	    void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback);

	    /// <summary>Create a new view.</summary>
	    /// <param name="statementContext">contains view services</param>
	    /// <returns>new view</returns>
	    View MakeView(StatementContext statementContext);

	    /// <summary>
	    /// Returns the event type that the view that is created by the view factory would create for events posted
	    /// by the view.
	    /// </summary>
	    /// <returns>event type of view's created by the view factory</returns>
	    EventType EventType { get ; }

	    /// <summary>
	    /// Determines if the given view could be used instead of creating a new view,
	    /// requires the view factory to compare view type, parameters and other capabilities provided.
	    /// </summary>
	    /// <param name="view">is the candidate view to compare to</param>
	    /// <returns>
	    /// true if the given view can be reused instead of creating a new view, or false to indicate
	    /// the view is not right for reuse
	    /// </returns>
	    bool CanReuse(View view);
	}
}
