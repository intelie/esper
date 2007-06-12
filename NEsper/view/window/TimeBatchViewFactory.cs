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
using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.parse;
using net.esper.events;
using net.esper.util;
using net.esper.view;

namespace net.esper.view.window
{
	/// <summary>Factory for {@link TimeBatchView}.</summary>
	public class TimeBatchViewFactory : ViewFactory
	{
	    private long millisecondsBeforeExpiry;
	    private long? optionalReferencePoint;
	    private EventType eventType;
	    private RelativeAccessByEventNIndexGetter relativeAccessGetterImpl;

        /// <summary>
        /// Indicates user EQL query view parameters to the view factory.
        /// </summary>
        /// <param name="viewFactoryContext">supplied context information for the view factory</param>
        /// <param name="viewParameters">is the objects representing the view parameters</param>
        /// <throws>
        /// ViewParameterException if the parameters don't match view parameter needs
        /// </throws>
	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "Time batch view requires a single numeric or time period parameter, and an optional long-typed reference point in msec";
	        if ((viewParameters.Count < 1) || (viewParameters.Count > 2))
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        Object parameter = viewParameters[0];
	        if (parameter is TimePeriodParameter)
	        {
	            TimePeriodParameter param = (TimePeriodParameter) parameter;
	            millisecondsBeforeExpiry = (long) Math.Round(1000d * param.NumSeconds);
	        }
	        else if (TypeHelper.IsFloatingPointNumber(parameter))
            {
            	millisecondsBeforeExpiry = (long) Math.Round(1000d * Convert.ToDouble(parameter));
            }
            else if (TypeHelper.IsIntegralNumber(parameter))
            {
            	millisecondsBeforeExpiry = 1000 * Convert.ToInt64(parameter);
            }
            else
            {
	            throw new ViewParameterException(errorMessage);
            }

	        if (millisecondsBeforeExpiry < 100)
	        {
	            throw new ViewParameterException("Time batch view requires a size of at least 100 msec");
	        }

	        if (viewParameters.Count == 2)
	        {
	        	Object paramRef = viewParameters[1];
	        	if (!TypeHelper.IsIntegralNumber( paramRef ))
	        	{
	                throw new ViewParameterException("Time batch view requires a Long-typed reference point in msec as a second parameter");
	            }
	        	optionalReferencePoint = Convert.ToInt64(paramRef);
	        }
	    }

        /// <summary>
        /// Attaches the factory to a parent event type such that the factory can validate
        /// attach requirements and determine an event type for resulting views.
        /// </summary>
        /// <param name="parentEventType">is the parent event stream's or view factory's event type</param>
        /// <param name="statementContext">contains the services needed for creating a new event type</param>
        /// <param name="optionalParentFactory">is null when there is no parent view factory, or contains the
        /// parent view factory</param>
        /// <param name="parentViewFactories">is a list of all the parent view factories or empty list if there are none</param>
        /// <throws>
        /// ViewAttachException is thrown to indicate that this view factories's view would not play
        /// with the parent view factories view
        /// </throws>
	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        this.eventType = parentEventType;
	    }

        /// <summary>
        /// Returns true if the view factory can make views that provide a view resource with the
        /// given capability.
        /// </summary>
        /// <param name="viewCapability">is the view resource needed</param>
        /// <returns>
        /// true to indicate that the view can provide the resource, or false if not
        /// </returns>
	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        return viewCapability is ViewCapDataWindowAccess;
	    }

        /// <summary>
        /// Indicates to the view factory to provide the view resource indicated.
        /// </summary>
        /// <param name="viewCapability">is the required resource descriptor</param>
        /// <param name="resourceCallback">is the callback to use to supply the resource needed</param>
	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        if (!CanProvideCapability(viewCapability))
	        {
	        	throw new UnsupportedOperationException("View capability " + viewCapability.GetType().FullName + " not supported");
	        }
	        if (relativeAccessGetterImpl == null)
	        {
	            relativeAccessGetterImpl = new RelativeAccessByEventNIndexGetter();
	        }
	        resourceCallback.ViewResource = relativeAccessGetterImpl;
	    }

        /// <summary>
        /// Create a new view.
        /// </summary>
        /// <param name="statementContext">contains view services</param>
        /// <returns>new view</returns>
	    public View MakeView(StatementContext statementContext)
	    {
	        IStreamRelativeAccess relativeAccessByEvent = null;

	        if (relativeAccessGetterImpl != null)
	        {
	            relativeAccessByEvent = new IStreamRelativeAccess(relativeAccessGetterImpl);
	            relativeAccessGetterImpl.Updated(relativeAccessByEvent, null);
	        }

	        return new TimeBatchView(this, statementContext, millisecondsBeforeExpiry, optionalReferencePoint, relativeAccessByEvent);
	    }

        /// <summary>
        /// Returns the event type that the view that is created by the view factory would create for events posted
        /// by the view.
        /// </summary>
        /// <value></value>
        /// <returns>event type of view's created by the view factory</returns>
	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

        /// <summary>
        /// Determines if the given view could be used instead of creating a new view,
        /// requires the view factory to compare view type, parameters and other capabilities provided.
        /// </summary>
        /// <param name="view">is the candidate view to compare to</param>
        /// <returns>
        /// true if the given view can be reused instead of creating a new view, or false to indicate
        /// the view is not right for reuse
        /// </returns>
	    public bool CanReuse(View view)
	    {
	        if (!(view is TimeBatchView))
	        {
	            return false;
	        }

	        TimeBatchView myView = (TimeBatchView) view;
	        if (myView.MsecIntervalSize != millisecondsBeforeExpiry)
	        {
	            return false;
	        }

	        if ((myView.InitialReferencePoint != null) && (optionalReferencePoint != null))
	        {
	            if (myView.InitialReferencePoint != optionalReferencePoint)
	            {
	                return false;
	            }
	        }
	        if ( ((myView.InitialReferencePoint == null) && (optionalReferencePoint != null)) ||
	             ((myView.InitialReferencePoint != null) && (optionalReferencePoint == null)) )
	        {
	            return false;
	        }

	        return myView.IsEmpty;
	    }
	}
} // End of namespace
