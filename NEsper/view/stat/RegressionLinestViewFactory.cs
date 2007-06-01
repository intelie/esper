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

namespace net.esper.view.stat
{
	/// <summary>Factory for {@link RegressionLinestView} instances.</summary>
	public class RegressionLinestViewFactory : ViewFactory
	{
	    private String fieldNameX;
	    private String fieldNameY;
	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters)
	    {
	        String errorMessage = "'Regression line' view requires two field names as parameters";
	        if (viewParameters.Size() != 2)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if ( (!(viewParameters.Get(0) is String)) ||
	             (!(viewParameters.Get(1) is String)) )
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        fieldNameX = (String) viewParameters.Get(0);
	        fieldNameY = (String) viewParameters.Get(1);
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
	    {
	        String result = PropertyCheckHelper.CheckNumeric(parentEventType, fieldNameX, fieldNameY);
	        if (result != null)
	        {
	            throw new ViewAttachException(result);
	        }

	        eventType = RegressionLinestView.CreateEventType(statementContext);
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
	        return new RegressionLinestView(statementContext, fieldNameX, fieldNameY);
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is RegressionLinestView))
	        {
	            return false;
	        }

	        RegressionLinestView myView = (RegressionLinestView) view;
	        if ((!myView.FieldNameX.Equals(fieldNameX)) ||
	            (!myView.FieldNameY.Equals(fieldNameY)))
	        {
	            return false;
	        }
	        return true;
	    }

	    public EventType GetEventType()
	    {
	        return eventType;
	    }
	}
} // End of namespace
