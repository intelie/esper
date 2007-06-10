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
using net.esper.view;

namespace net.esper.view.std
{
	/// <summary>Factory for {@link UniqueByPropertyView} instances.</summary>
	public class UniqueByPropertyViewFactory : ViewFactory
	{
	    /// <summary>Property name to evaluate unique values.</summary>
	    protected String propertyName;

	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "'Unique' view requires a single string parameter";
	        if (viewParameters.Count != 1)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        Object parameter = viewParameters[0];
	        if (!(parameter is String))
	        {
	            throw new ViewParameterException(errorMessage);
	        }
	        propertyName = (String) parameter;
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        // Attaches to just about anything as long as the field exists
	        String message = PropertyCheckHelper.Exists(parentEventType, propertyName);
	        if (message != null)
	        {
	            throw new ViewAttachException(message);
	        }
	        this.eventType = parentEventType;
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
	        return new UniqueByPropertyView(propertyName);
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is UniqueByPropertyView))
	        {
	            return false;
	        }

	        UniqueByPropertyView myView = (UniqueByPropertyView) view;
	        if (!myView.UniqueFieldName.Equals(propertyName))
	        {
	            return false;
	        }

	        return true;
	    }
	}
} // End of namespace
