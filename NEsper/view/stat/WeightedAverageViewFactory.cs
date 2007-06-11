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

namespace net.esper.view.stat
{
	/// <summary>Factory for {@link WeightedAverageView} instances.</summary>
	public class WeightedAverageViewFactory : ViewFactory
	{
	    private String fieldNameX;
	    private String fieldNameWeight;
	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "'Weighted average' view requires two field names as parameters";
	        if (viewParameters.Count != 2)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if ( (!(viewParameters[0] is String)) ||
	             (!(viewParameters[1] is String)) )
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        fieldNameX = (String) viewParameters[0];
	        fieldNameWeight = (String) viewParameters[1];
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        String result = PropertyCheckHelper.CheckNumeric(parentEventType, fieldNameX, fieldNameWeight);
	        if (result != null)
	        {
	            throw new ViewAttachException(result);
	        }
	        eventType = WeightedAverageView.CreateEventType(statementContext);
	    }

	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        return false;
	    }

	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        throw new UnsupportedOperationException("View capability " + viewCapability.GetType().FullName + " not supported");
	    }

	    public View MakeView(StatementContext statementContext)
	    {
	        return new WeightedAverageView(statementContext, fieldNameX, fieldNameWeight);
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is WeightedAverageView))
	        {
	            return false;
	        }

	        WeightedAverageView myView = (WeightedAverageView) view;
	        if ((!myView.FieldNameWeight.Equals(fieldNameWeight)) ||
	            (!myView.FieldNameX.Equals(fieldNameX)) )
	        {
	            return false;
	        }
	        return true;
	    }
	}
} // End of namespace
