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
	/// <summary>Factory for {@link GroupByView} instances.</summary>
	public class GroupByViewFactory : ViewFactory
	{
	    private String[] groupFieldNames;
	    private EventType eventType;

	    public void SetViewParameters(ViewFactoryContext viewFactoryContext, IList<Object> viewParameters)
	    {
	        groupFieldNames = GetFieldNameParams(viewParameters, "Group-by");
	    }

	    public void Attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, IList<ViewFactory> parentViewFactories)
	    {
	        // Attaches to just about anything as long as all the fields exists
	        for (int i = 0; i < groupFieldNames.Length; i++)
	        {
	            String message = PropertyCheckHelper.Exists(parentEventType, groupFieldNames[i]);
	            if (message != null)
	            {
	                throw new ViewAttachException(message);
	            }
	        }

	        this.eventType = parentEventType;
	    }

	    /// <summary>Returns the names of fields to group by</summary>
	    /// <returns>field names</returns>
	    public String[] GetGroupFieldNames()
	    {
	        return groupFieldNames;
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
	        return new GroupByView(statementContext, groupFieldNames);
	    }

	    public EventType EventType
	    {
	    	get { return eventType; }
	    }

	    /// <summary>
	    /// Parses the given view parameters into a list of field names to group-by.
	    /// </summary>
	    /// <param name="viewParameters">is the raw parameter objects</param>
	    /// <param name="viewName">is the name of the view</param>
	    /// <returns>field names</returns>
	    /// <throws>ViewParameterException thrown to indicate a parameter problem</throws>
	    protected static String[] GetFieldNameParams(List<Object> viewParameters, String viewName)
	    {
	        String[] fieldNames;

	        String errorMessage = '\'' + viewName + "' view requires a list of String values or a String-array as parameter";
	        if (viewParameters.IsEmpty())
	        {
	            throw new ViewParameterException(errorMessage);
	        }

	        if (viewParameters.Count > 1)
	        {
	            List<String> fields = new List<String>();
	            foreach (Object param in viewParameters)
	            {
	                if (!(param is String))
	                {
	                    throw new ViewParameterException(errorMessage);
	                }
	                fields.Add((String) param);
	            }
	            fieldNames = fields.ToArray(new String[0]);
	        }
	        else
	        {
	            Object param = viewParameters[0];
	            if (param is String[])
	            {
	                String[] arr = (String[]) param;
	                if (arr.Length == 0)
	                {
	                    throw new ViewParameterException(errorMessage);
	                }
	                fieldNames = arr;
	            }
	            else if (param is String)
	            {
	                fieldNames = new String[] {(String)param};
	            }
	            else
	            {
	                throw new ViewParameterException(errorMessage);
	            }
	        }

	        return fieldNames;
	    }

	    public bool CanReuse(View view)
	    {
	        if (!(view is GroupByView))
	        {
	            return false;
	        }

	        GroupByView myView = (GroupByView) view;
	        if (!Arrays.DeepEquals(myView.GroupFieldNames, groupFieldNames))
	        {
	            return false;
	        }

	        return true;
	    }
	}
} // End of namespace
