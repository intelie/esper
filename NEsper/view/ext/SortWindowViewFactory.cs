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

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "Sort window view requires a field name, a bool sort order and a numeric size parameter or parameter list";
	        if (viewParameters.Count == 3)
	        {
	            if ((viewParameters[0] is string) &&
	                (viewParameters[1] is bool) &&
	                (TypeHelper.IsIntegralNumber(viewParameters[2])))
	            {
	                sortFieldNames = new string[] {(string)viewParameters[0]};
	                isDescendingValues = new bool[] {(bool) viewParameters[1]};
	                sortWindowSize = Convert.ToInt32(viewParameters[2]);
	            }
	            else
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	        }
	        else if (viewParameters.Count == 2)
	        {
	            if ( (!(viewParameters[0] is Object[]) ||
	                 (!(viewParameters[1] is Int32))) )
	            {
	                throw new ViewParameterException(errorMessage);
	            }

	            Object[] ascFieldsArr = (Object[]) viewParameters[0];
	            try
	            {
	                SetNamesAndIsDescendingValues(ascFieldsArr);
	            }
	            catch (Exception ex)
	            {
	                throw new ViewParameterException(errorMessage + ",reason:" + ex.Message);
	            }

	            Object sizeParam = viewParameters[1];
	            if (TypeHelper.IsFloatingPointNumber(sizeParam))
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	            sortWindowSize = Convert.ToInt32(sizeParam);
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

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
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
            if (!CanProvideCapability(viewCapability))
	        {
	            throw new UnsupportedOperationException("View capability " + viewCapability.GetType().FullName + " not supported");
	        }

	        if (randomAccessGetterImpl == null)
	        {
	            randomAccessGetterImpl = new RandomAccessByIndexGetter();
	        }
	        resourceCallback.ViewResource = randomAccessGetterImpl;
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

		public EventType EventType
		{
			get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is SortWindowView))
	        {
	            return false;
	        }

	        SortWindowView other = (SortWindowView) view;
	        if ((other.SortWindowSize != sortWindowSize) ||
	            (!CollectionHelper.AreEqual(other.IsDescendingValues, isDescendingValues)) ||
	            (!CollectionHelper.AreEqual(other.SortFieldNames, sortFieldNames)) )
	        {
	            return false;
	        }

	        return other.IsEmpty;
	    }

	    private void SetNamesAndIsDescendingValues(Object[] propertiesAndDirections)
	    {
	        if(propertiesAndDirections.Length % 2 != 0)
	        {
	            throw new ArgumentException("Each property to sort by must have an isDescending bool qualifier");
	        }

	        int length = propertiesAndDirections.Length / 2;
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
