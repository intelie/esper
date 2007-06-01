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
using net.esper.events;
using net.esper.view;

namespace net.esper.view.std
{
	/// <summary>Factory for {@link SizeView} instances.</summary>
	public class SizeViewFactory : ViewFactory
	{
	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters)
	    {
	        String errorMessage = "'Size' view does not take any parameters";
	        if (!viewParameters.IsEmpty())
	        {
	            throw new ViewParameterException(errorMessage);
	        }
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
	    {
	        eventType = SizeView.CreateEventType(statementContext);
	    }

	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        return false;
	    }

	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        throw new UnsupportedOperationException("View capability " + viewCapability.Class.SimpleName + " not supported");
	    }

	    public View MakeView(StatementContext statementContext)
	    {
	        return new SizeView(statementContext);
	    }

	    public EventType GetEventType()
	    {
	        return eventType;
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is SizeView))
	        {
	            return false;
	        }
	        return true;
	    }
	}
} // End of namespace
