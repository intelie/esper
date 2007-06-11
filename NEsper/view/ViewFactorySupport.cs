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

namespace net.esper.view
{
	/// <summary>
	/// Abstract base class for view factories that do not make re-useable views and that do
	/// not share view resources with expression nodes.
	/// </summary>
	public abstract class ViewFactorySupport : ViewFactory
	{
	    public bool CanProvideCapability(ViewCapability viewCapability)
	    {
	        return false;
	    }

	    public void SetProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
	    {
	        throw new UnsupportedOperationException("View capability " + viewCapability.GetType().FullName + " not supported");
	    }

	    public bool CanReuse(View view)
	    {
	        return false;
	    }
		
		abstract public net.esper.events.EventType EventType { get ; }
		
		abstract public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<object> viewParameters);
		
		abstract public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories);
		
		abstract public View MakeView(net.esper.core.StatementContext statementContext);
	}
} // End of namespace
