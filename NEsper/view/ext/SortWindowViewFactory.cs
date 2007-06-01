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
using net.esper.view.window;

namespace net.esper.view.ext
{
	/// <summary>Factory for sort window views.</summary>
	public class SortWindowViewFactory : ViewFactory
	{
	    private String[] sortFieldNames;
	    private Boolean[] isDescendingValues;
	    private int sortWindowSize;
	    private EventType eventType;
	    private RandomAccessByIndexGetter randomAccessGetterImpl;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters)
	    {
	        String errorMessage = "Sort window view requires a field name, a bool sort order and a numeric size parameter or parameter list";
	        if (viewParameters.Size() == 3)
	        {
	            if ((viewParameters.Get(0) is String) &&
	                (viewParameters.Get(1) is Boolean) &&
	                (viewParameters.Get(2) is Number))
	            {
	                sortFieldNames = new String[] {(String)viewParameters.Get(0)};
	                isDescendingValues = new Boolean[] {(Boolean) viewParameters.Get(1)};
	                Number sizeParam = (Number) viewParameters.Get(2);
	                if (JavaClassHelper.IsFloatingPointNumber(sizeParam))
	                {
	                    throw new ViewParameterException(errorMessage);
	                }
	                sortWindowSize = sizeParam.IntValue();
	            }
	            else
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	        }
	        else if (viewParameters.Size() == 2)
	        {
	            if ( (!(viewParameters.Get(0) is Object[]) ||
	                 (!(viewParameters.Get(1) is Integer))) )
	            {
	                throw new ViewParameterException(errorMessage);
	            }

	            Object[] ascFieldsArr = (Object[]) viewParameters.Get(0);
	            try
	            {
	                SetNamesAndIsDescendingValues(ascFieldsArr);
	            }
	            catch (RuntimeException ex)
	            {
	                throw new ViewParameterException(errorMessage + ",reason:" + ex.Message);
	            }

	            Number sizeParam = (Number) viewParameters.Get(1);
	            if (JavaClassHelper.IsFloatingPointNumber(sizeParam))
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	            sortWindowSize = sizeParam.IntValue();
	        }
	        else
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if (sortWindowSize < 1)
	        {
	            throw new ViewParameterException("Illegal argument for sort window size of sort window");
	        }
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories)
	    {
	        // Attaches to parent views where the sort fields exist and implement Comparable
	        String result = null;
	        foreach (String name in sortFieldNames)
	        {
	            result = PropertyCheckHelper.Exists(parentEventType, name);

	            if(result != null)
	            {
	                throw new ViewAttachException(result);
	            }
	        }

	        eventType = parentEventType;
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
	        IStreamSortedRandomAccess sortedRandomAccess = null;

	        if (randomAccessGetterImpl != null)
	        {
	            sortedRandomAccess = new IStreamSortedRandomAccess(randomAccessGetterImpl);
	            randomAccessGetterImpl.Updated(sortedRandomAccess);
	        }

	        return new SortWindowView(this, sortFieldNames, isDescendingValues, sortWindowSize, sortedRandomAccess);
	    }

	    public EventType GetEventType()
	    {
	        return eventType;
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is SortWindowView))
	        {
	            return false;
	        }

	        SortWindowView other = (SortWindowView) view;
	        if ((other.SortWindowSize != sortWindowSize) ||
	            (!Arrays.DeepEquals(other.IsDescendingValues, isDescendingValues)) ||
	            (!Arrays.DeepEquals(other.SortFieldNames, sortFieldNames)) )
	        {
	            return false;
	        }

	        return other.IsEmpty();
	    }

	    private void SetNamesAndIsDescendingValues(Object[] propertiesAndDirections)
	    {
	        if(propertiesAndDirections.length % 2 != 0)
	        {
	            throw new IllegalArgumentException("Each property to sort by must have an isDescending bool qualifier");
	        }

	        int length = propertiesAndDirections.length / 2;
	        sortFieldNames = new String[length];
	        isDescendingValues  = new Boolean[length];

	        for(int i = 0; i < length; i++)
	        {
	            sortFieldNames[i] = (String)propertiesAndDirections[2*i];
	            isDescendingValues[i] = (Boolean)propertiesAndDirections[2*i + 1];
	        }
	    }
	}
} // End of namespace
