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
	/// <summary>Factory for {@link UnivariateStatisticsView} instances.</summary>
	public class UnivariateStatisticsViewFactory : ViewFactory
	{
	    private String fieldName;
	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "'Univariate statistics' view require a single field name as a parameter";
	        if (viewParameters.Count != 1)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if (!(viewParameters[0] is String))
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        fieldName = (String) viewParameters[0];
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        String result = PropertyCheckHelper.CheckNumeric(parentEventType, fieldName);
	        if (result != null)
	        {
	            throw new ViewAttachException(result);
	        }
	        eventType = UnivariateStatisticsView.CreateEventType(statementContext);
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
	        return new UnivariateStatisticsView(statementContext, fieldName);
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is UnivariateStatisticsView))
	        {
	            return false;
	        }

	        UnivariateStatisticsView other = (UnivariateStatisticsView) view;
	        if (!other.FieldName.Equals(fieldName))
	        {
	            return false;
	        }

	        return true;
	    }
	}
} // End of namespace
