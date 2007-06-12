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
	/// <summary>Factory for {@link TimeWindowView}.</summary>
	public class TimeWindowViewFactory : ViewFactory
	{
	    private long millisecondsBeforeExpiry;
	    private RandomAccessByIndexGetter randomAccessGetterImpl;
	    private EventType eventType;

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
	        String errorMessage = "Time window view requires a single numeric or time period parameter";
	        if (viewParameters.Count != 1)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        Object parameter = viewParameters[0];
	        if (parameter is TimePeriodParameter)
	        {
	            TimePeriodParameter param = (TimePeriodParameter) parameter;
	            millisecondsBeforeExpiry = (long) Math.Round(1000d * param.NumSeconds);
	        }
	        else if (! TypeHelper.IsNumericValue(parameter))
	        {
	            throw new ViewParameterException(errorMessage);
	        }
	        else
	        {
	        	ValueType valueType = (ValueType) parameter;
	        	
	            if (TypeHelper.IsFloatingPointNumber(parameter))
	            {
	                millisecondsBeforeExpiry = (long) Math.Round(1000d*Convert.ToDouble(parameter));
	            }
	            else
	            {
	                millisecondsBeforeExpiry = 1000*Convert.ToInt64(parameter);
	            }
	        }

	        if (millisecondsBeforeExpiry < 100)
	        {
	            throw new ViewParameterException("Time window view requires a size of at least 100 msec");
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
	        if (viewCapability is ViewCapDataWindowAccess)
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }

        /// <summary>
        /// Returns the number of millisecond before window contents expire.
        /// </summary>
        /// <returns>num msec</returns>
	    public long MillisecondsBeforeExpiry
	    {
            get { return millisecondsBeforeExpiry; }
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
	        if (randomAccessGetterImpl == null)
	        {
	            randomAccessGetterImpl = new RandomAccessByIndexGetter();
	        }
	        resourceCallback.ViewResource = randomAccessGetterImpl;
	    }

        /// <summary>
        /// Create a new view.
        /// </summary>
        /// <param name="statementContext">contains view services</param>
        /// <returns>new view</returns>
	    public View MakeView(StatementContext statementContext)
	    {
	        IStreamRandomAccess randomAccess = null;

	        if (randomAccessGetterImpl != null)
	        {
	            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
	            randomAccessGetterImpl.Updated(randomAccess);
	        }

	        return new TimeWindowView(statementContext, this, millisecondsBeforeExpiry, randomAccess);
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
	        if (!(view is TimeWindowView))
	        {
	            return false;
	        }

	        TimeWindowView myView = (TimeWindowView) view;
	        if (myView.MillisecondsBeforeExpiry != millisecondsBeforeExpiry)
	        {
	            return false;
	        }

	        // For reuse of the time window it doesn't matter if it provides random access or not
	        return myView.IsEmpty;
	    }
	}
} // End of namespace
