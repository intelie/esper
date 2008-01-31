/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.spec;

import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.core.StreamTypeService;
import com.espertech.esper.eql.core.StreamTypeServiceImpl;
import com.espertech.esper.eql.named.NamedWindowService;
import com.espertech.esper.eql.expression.ExprValidationException;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.eql.variable.VariableService;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecCompiler;
import com.espertech.esper.pattern.PatternObjectResolutionService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.MetaDefItem;

import java.util.List;

/**
 * Unvalided filter-based stream specification.
 */
public class FilterStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw, MetaDefItem
{
    private FilterSpecRaw rawFilterSpec;


    /**
     * Ctor.
     * @param rawFilterSpec is unvalidated filter specification
     * @param viewSpecs is the view definition
     * @param optionalStreamName is the stream name if supplied, or null if not supplied
     * @param isUnidirectional - true to indicate a unidirectional stream in a join, applicable for joins
     */
    public FilterStreamSpecRaw(FilterSpecRaw rawFilterSpec, List<ViewSpec> viewSpecs, String optionalStreamName, boolean isUnidirectional)
    {
        super(optionalStreamName, viewSpecs, isUnidirectional);
        this.rawFilterSpec = rawFilterSpec;
    }

    /**
     * Default ctor.
     */
    public FilterStreamSpecRaw()
    {
    }

    /**
     * Returns the unvalided filter spec.
     * @return filter def
     */
    public FilterSpecRaw getRawFilterSpec()
    {
        return rawFilterSpec;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      MethodResolutionService methodResolutionService,
                                      PatternObjectResolutionService patternObjectResolutionService,
                                      TimeProvider timeProvider,
                                      NamedWindowService namedWindowService,
                                      VariableService variableService)
            throws ExprValidationException
    {
        // Determine the event type
        String eventName = rawFilterSpec.getEventTypeAlias();

        // Could be a named window
        if (namedWindowService.isNamedWindow(eventName))
        {
            EventType namedWindowType = namedWindowService.getProcessor(eventName).getTailView().getEventType();
            StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {namedWindowType}, new String[] {"s0"});

            List<ExprNode> validatedNodes = FilterSpecCompiler.validateDisallowSubquery(rawFilterSpec.getFilterExpressions(),
                streamTypeService, methodResolutionService, timeProvider, variableService);
            
            return new NamedWindowConsumerStreamSpec(eventName, this.getOptionalStreamName(), this.getViewSpecs(), validatedNodes, this.isUnidirectional());
        }
        
        EventType eventType = resolveType(eventName, eventAdapterService);

        // Validate all nodes, make sure each returns a boolean and types are good;
        // Also decompose all AND super nodes into individual expressions
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"});

        FilterSpecCompiled spec = FilterSpecCompiler.makeFilterSpec(eventType, rawFilterSpec.getFilterExpressions(), null,
                streamTypeService, methodResolutionService, timeProvider, variableService);

        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName(), this.isUnidirectional());
    }

    /**
     * Resolves a given event alias to an event type.
     * @param eventName is the alias to resolve
     * @param eventAdapterService for resolving event types
     * @return event type
     * @throws ExprValidationException if the info cannot be resolved
     */
    protected static EventType resolveType(String eventName, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        EventType eventType = eventAdapterService.getExistsTypeByAlias(eventName);

        // The type is not known yet, attempt to add as a JavaBean type with the same alias
        if (eventType == null)
        {
            try
            {
                eventType = eventAdapterService.addBeanType(eventName, eventName, true);
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException("Failed to resolve event type: " + ex.getMessage());
            }
        }

        return eventType;
    }
}
