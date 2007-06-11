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
	/// <summary>
	/// Factory for {@link MultiDimStatsView} instances.
	/// </summary>
	public class MultiDimStatsViewFactory : ViewFactory
	{
	    private String[] derivedMeasures;
	    private String measureField;
	    private String columnField;
	    private String rowField;
	    private String pageField;
	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        String errorMessage = "'Multi-dimensional stats' view requires a String-array and 2 or more field names as parameters";
	        if (viewParameters.Count < 3)
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if ( (!(viewParameters[0] is String[])) ||
	             (!(viewParameters[1] is String)) ||
	             (!(viewParameters[2] is String)) )
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        derivedMeasures = (String[]) viewParameters[0];
	        measureField = (String) viewParameters[1];
	        columnField = (String) viewParameters[2];

	        if (viewParameters.Count > 3)
	        {
	        	if (!(viewParameters[3] is String))
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	            rowField = (String) viewParameters[3];
	        }

	        if (viewParameters.Count > 4)
	        {
	        	if (!(viewParameters[4] is String))
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	            pageField = (String) viewParameters[4];
	        }

	        foreach (String measureName in derivedMeasures)
	        {
	            if (Array.BinarySearch(ViewFieldEnum.MULTIDIM_OLAP__MEASURES, measureName) < 0)
	            {
	                throw new ViewParameterException("Derived measure named '" + measureName + "' is not a valid measure");
	            }
	        }
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        String message = PropertyCheckHelper.CheckNumeric(parentEventType, measureField);
	        if (message != null)
	        {
	            throw new ViewAttachException(message);
	        }

	        message = PropertyCheckHelper.Exists(parentEventType, columnField);
	        if (message != null)
	        {
	            throw new ViewAttachException(message);
	        }

	        if (rowField != null)
	        {
	            message = PropertyCheckHelper.Exists(parentEventType, rowField);
	            if (message != null)
	            {
	                throw new ViewAttachException(message);
	            }
	        }

	        if (pageField != null)
	        {
	            message = PropertyCheckHelper.Exists(parentEventType, pageField);
	            if (message != null)
	            {
	                throw new ViewAttachException(message);
	            }
	        }

	        eventType = MultiDimStatsView.CreateEventType(statementContext);
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
	        return new MultiDimStatsView(statementContext, derivedMeasures, measureField, columnField, rowField, pageField);
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is MultiDimStatsView))
	        {
	            return false;
	        }

	        MultiDimStatsView other = (MultiDimStatsView) view;
            if (!CollectionHelper.AreEqual(other.DerivedMeasures, derivedMeasures))
	        {
	            return false;
	        }
	        if (!other.MeasureField.Equals(measureField))
	        {
	            return false;
	        }
	        if (!other.ColumnField.Equals(columnField))
	        {
	            return false;
	        }
	        if ((other.RowField != null) && (rowField != null))
	        {
	            if (!other.RowField.Equals(rowField))
	            {
	                return false;
	            }
	        }
	        if ( ((other.RowField == null) && (rowField != null)) ||
	             ((other.RowField != null) && (rowField == null)) )
	        {
	            return false;
	        }
	        if ((other.PageField != null) && (pageField != null))
	        {
	            if (!other.PageField.Equals(pageField))
	            {
	                return false;
	            }
	        }
	        if ( ((other.PageField == null) && (pageField != null)) ||
	             ((other.PageField != null) && (pageField == null)) )
	        {
	            return false;
	        }

	        return true;
	    }
	}
} // End of namespace
