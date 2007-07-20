// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.events;
using net.esper.view;

namespace net.esper.regression.client
{
	public class MyTrendSpotterViewFactory : ViewFactorySupport
	{
	    private String fieldName;
	    private EventType eventType;

	    public override void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "'Trend spotter' view require a single field name as a parameter";
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

	    public override void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories) 
	    {
	        String result = PropertyCheckHelper.CheckNumeric(parentEventType, fieldName);
	        if (result != null)
	        {
	            throw new ViewAttachException(result);
	        }
	        eventType = MyTrendSpotterView.CreateEventType(statementContext);
	    }

	    public override View MakeView(StatementContext statementContext)
	    {
	        return new MyTrendSpotterView(statementContext, fieldName);
	    }

	    public override EventType EventType
	    {
            get { return eventType; }
	    }
	}
} // End of namespace
