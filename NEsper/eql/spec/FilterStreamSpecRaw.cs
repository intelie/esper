///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.filter;
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>Unvalided filter-based stream specification.</summary>
	public class FilterStreamSpecRaw : StreamSpecBase, StreamSpecRaw, MetaDefItem
	{
	    private FilterSpecRaw rawFilterSpec;


	    /// <summary>Ctor.</summary>
	    /// <param name="rawFilterSpec">is unvalidated filter specification</param>
	    /// <param name="viewSpecs">is the view definition</param>
	    /// <param name="optionalStreamName">
	    /// is the stream name if supplied, or null if not supplied
	    /// </param>
	    public FilterStreamSpecRaw(FilterSpecRaw rawFilterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
	        : base(optionalStreamName, viewSpecs)
	    {
	        this.rawFilterSpec = rawFilterSpec;
	    }

	    /// <summary>Default ctor.</summary>
	    public FilterStreamSpecRaw()
	    {
	    }

	    /// <summary>Returns the unvalided filter spec.</summary>
	    /// <returns>filter def</returns>
	    public FilterSpecRaw GetRawFilterSpec()
	    {
	        return rawFilterSpec;
	    }

	    public StreamSpecCompiled Compile(EventAdapterService eventAdapterService,
	                                      MethodResolutionService methodResolutionService)
	    {
	        // Determine the event type
	        String eventName = rawFilterSpec.EventTypeAlias;
	        EventType eventType = ResolveType(eventName, eventAdapterService);

	        // Validate all nodes, make sure each returns a bool and types are good;
	        // Also decompose all AND super nodes into individual expressions
	        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"});

	        FilterSpecCompiled spec = FilterSpecCompiler.MakeFilterSpec(eventType, rawFilterSpec.FilterExpressions, null,
	                streamTypeService, methodResolutionService);

	        return new FilterStreamSpecCompiled(spec, this.ViewSpecs, this.OptionalStreamName);
	    }

	    /// <summary>Resolves a given event alias to an event type.</summary>
	    /// <param name="eventName">is the alias to resolve</param>
	    /// <param name="eventAdapterService">for resolving event types</param>
	    /// <returns>event type</returns>
	    /// <throws>ExprValidationException if the info cannot be resolved</throws>
	    protected static EventType ResolveType(String eventName, EventAdapterService eventAdapterService)
	    {
	        EventType eventType = eventAdapterService.GetExistsTypeByAlias(eventName);

	        // The type is not known yet, attempt to add as a JavaBean type with the same alias
	        if (eventType == null)
	        {
	            try
	            {
	                eventType = eventAdapterService.AddBeanType(eventName, eventName);
	            }
	            catch (EventAdapterException ex)
	            {
	                throw new ExprValidationException("Failed to resolve event type: " + ex.Message);
	            }
	        }

	        return eventType;
	    }
	}
} // End of namespace
