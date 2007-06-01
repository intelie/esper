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
	    private Long optionalReferencePoint;
	    private EventType eventType;
	    private RelativeAccessByEventNIndexGetter relativeAccessGetterImpl;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters)
	    {
	        String errorMessage = "Time batch view requires a single numeric or time period parameter, and an optional long-typed reference point in msec";
	        if ((viewParameters.Size() < 1) || (viewParameters.Size() > 2))
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        Object parameter = viewParameters.Get(0);
	        if (parameter is TimePeriodParameter)
	        {
	            TimePeriodParameter param = (TimePeriodParameter) parameter;
	            millisecondsBeforeExpiry = Math.Round(1000d * param.NumSeconds);
	        }
	        else if (!(parameter is Number))
	        {
	            throw new ViewParameterException(errorMessage);
	        }
	        else
	        {
	            Number param = (Number) parameter;
	            if (JavaClassHelper.IsFloatingPointNumber(param))
	            {
	                millisecondsBeforeExpiry = Math.Round(1000d * param.DoubleValue());
	            }
	            else
	            {
	                millisecondsBeforeExpiry = 1000 * param.LongValue();
	            }
	        }

	        if (millisecondsBeforeExpiry < 100)
	        {
	            throw new ViewParameterException("Time batch view requires a size of at least 100 msec");
	        }

	        if (viewParameters.Size() == 2)
	        {
	            Object paramRef = viewParameters.Get(1);
	            if ((!(paramRef is Number)) || (JavaClassHelper.IsFloatingPointNumber((Number)paramRef)))
	            {
	                throw new ViewParameterException("Time batch view requires a Long-typed reference point in msec as a second parameter");
	            }
	            optionalReferencePoint = ((Number) paramRef).LongValue();
	        }
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
	    {
	        this.eventType = parentEventType;
	    }

	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        return viewCapability is ViewCapDataWindowAccess;
	    }

	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        if (!canProvideCapability(viewCapability))
	        {
	            throw new UnsupportedOperationException("View capability " + viewCapability.Class.SimpleName + " not supported");
	        }
	        if (relativeAccessGetterImpl == null)
	        {
	            relativeAccessGetterImpl = new RelativeAccessByEventNIndexGetter();
	        }
	        resourceCallbackViewResource = relativeAccessGetterImpl;
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

	    public EventType GetEventType()
	    {
	        return eventType;
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
	            if (!myView.InitialReferencePoint.Equals(optionalReferencePoint.LongValue()))
	            {
	                return false;
	            }
	        }
	        if ( ((myView.InitialReferencePoint == null) && (optionalReferencePoint != null)) ||
	             ((myView.InitialReferencePoint != null) && (optionalReferencePoint == null)) )
	        {
	            return false;
	        }

	        return myView.IsEmpty();
	    }
	}
} // End of namespace
