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
using net.esper.util;
using net.esper.view;

namespace net.esper.view.window
{
	/// <summary>Factory for {@link LengthWindowView}.</summary>
	public class LengthWindowViewFactory : ViewFactory
	{
	    /// <summary>Size of length window.</summary>
	    protected int size;

	    private EventType eventType;
	    private RandomAccessByIndexGetter randomAccessGetterImpl;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters)
	    {
	        String errorMessage = "Length window view requires a single integer-type parameter";
	        if (viewParameters.Size() != 1)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        Object parameter = viewParameters.Get(0);
	        if (!(parameter is Number))
	        {
	            throw new ViewParameterException(errorMessage);
	        }
	        Number numParam = (Number) parameter;
	        if ( (JavaClassHelper.IsFloatingPointNumber(numParam)) ||
	             (numParam is Long))
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        size =  numParam.IntValue();
	        if (size <= 0)
	        {
	            throw new ViewParameterException("Length window requires a positive number");
	        }
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
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

	        return new LengthWindowView(this, size, randomAccess);
	    }

	    public EventType GetEventType()
	    {
	        return eventType;
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is LengthWindowView))
	        {
	            return false;
	        }

	        LengthWindowView myView = (LengthWindowView) view;
	        if (myView.Size != size)
	        {
	            return false;
	        }
	        return myView.IsEmpty();
	    }
	}
} // End of namespace
