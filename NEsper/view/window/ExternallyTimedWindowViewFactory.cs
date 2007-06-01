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
	/// <summary>Factory for {@link ExternallyTimedWindowView}.</summary>
	public class ExternallyTimedWindowViewFactory : ViewFactory
	{
	    private String timestampFieldName;
	    private long millisecondsBeforeExpiry;
	    private EventType eventType;
	    private RandomAccessByIndexGetter randomAccessGetterImpl;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters)
	    {
	        String errorMessage = "Externally-timed window view requires a timestamp field name and a numeric or time period parameter";
	        if (viewParameters.Size() != 2)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if (!(viewParameters.Get(0) is String))
	        {
	            throw new ViewParameterException(errorMessage);
	        }
	        timestampFieldName = (String) viewParameters.Get(0);

	        Object parameter = viewParameters.Get(1);
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
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
	    {
	        String message = PropertyCheckHelper.CheckLong(parentEventType, timestampFieldName);
	        if (message != null)
	        {
	            throw new ViewAttachException(message);
	        }
	        this.eventType = parentEventType;
	    }

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

	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        if (!canProvideCapability(viewCapability))
	        {
	            throw new UnsupportedOperationException("View capability " + viewCapability.Class.SimpleName + " not supported");
	        }
	        if (randomAccessGetterImpl == null)
	        {
	            randomAccessGetterImpl = new RandomAccessByIndexGetter();
	        }
	        resourceCallbackViewResource = randomAccessGetterImpl;
	    }

	    public View MakeView(StatementContext statementContext)
	    {
	        IStreamRandomAccess randomAccess = null;

	        if (randomAccessGetterImpl != null)
	        {
	            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
	            randomAccessGetterImpl.Updated(randomAccess);
	        }

	        return new ExternallyTimedWindowView(this, timestampFieldName, millisecondsBeforeExpiry, randomAccess);
	    }

	    public EventType GetEventType()
	    {
	        return eventType;
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is ExternallyTimedWindowView))
	        {
	            return false;
	        }

	        ExternallyTimedWindowView myView = (ExternallyTimedWindowView) view;
	        if ((myView.MillisecondsBeforeExpiry != millisecondsBeforeExpiry) ||
	            (!myView.TimestampFieldName.Equals(timestampFieldName)))
	        {
	            return false;
	        }
	        return myView.IsEmpty();
	    }
	}
} // End of namespace
