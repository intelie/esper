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
using net.esper.events;
using net.esper.util;
using net.esper.view;

namespace net.esper.view.window
{
	/// <summary>Factory for {@link net.esper.view.window.TimeBatchView}.</summary>
	public class LengthBatchViewFactory : ViewFactory
	{
	    private int size;
	    private EventType eventType;
	    private RelativeAccessByEventNIndexGetter relativeAccessGetterImpl;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "Length window view requires a single integer-type parameter";
	        if (viewParameters.Count != 1)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        Object parameter = viewParameters[0];
            if ( !TypeHelper.IsIntegralNumber( parameter ))
            {
                throw new ViewParameterException(errorMessage);
            }

            size = Convert.ToInt32(parameter);
	        if (size <= 0)
	        {
	            throw new ViewParameterException("Length window requires a positive number");
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

	        return new LengthBatchView(this, size, relativeAccessByEvent);
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is LengthBatchView))
	        {
	            return false;
	        }

	        LengthBatchView myView = (LengthBatchView) view;
	        if (myView.Count != size)
	        {
	            return false;
	        }

	        return myView.IsEmpty;
	    }
	}
}
