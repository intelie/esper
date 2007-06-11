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

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        this.eventType = parentEventType;
	    }

	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        return viewCapability is ViewCapDataWindowAccess;
	    }

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

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

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
