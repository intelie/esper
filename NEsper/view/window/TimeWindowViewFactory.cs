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

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
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

	    /// <summary>Returns the number of millisecond before window contents expire.</summary>
	    /// <returns>num msec</returns>
	    public long GetMillisecondsBeforeExpiry()
	    {
	        return millisecondsBeforeExpiry;
	    }

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

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

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
