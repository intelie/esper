/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.StreamTypeServiceImpl;
import net.esper.eql.named.NamedWindowService;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.filter.FilterSpecCompiled;
import net.esper.filter.FilterSpecCompiler;
import net.esper.pattern.PatternObjectResolutionService;
import net.esper.schedule.TimeProvider;
import net.esper.util.MetaDefItem;

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
     */
    public FilterStreamSpecRaw(FilterSpecRaw rawFilterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
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
                                      NamedWindowService namedWindowService)
            throws ExprValidationException
    {
        // Determine the event type
        String eventName = rawFilterSpec.getEventTypeAlias();

        // Could be a named window
        if (namedWindowService.isNamedWindow(eventName))
        {
            // Validate that no filter criteria have been used, and no views
            if (!rawFilterSpec.getFilterExpressions().isEmpty())
            {
                throw new ExprValidationException("Use of named window '" + eventName + "' does not allow filter expressions");
            }

            return new NamedWindowConsumerStreamSpec(eventName, this.getOptionalStreamName(), this.getViewSpecs());
        }
        
        EventType eventType = resolveType(eventName, eventAdapterService);

        // Validate all nodes, make sure each returns a boolean and types are good;
        // Also decompose all AND super nodes into individual expressions
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"});

        FilterSpecCompiled spec = FilterSpecCompiler.makeFilterSpec(eventType, rawFilterSpec.getFilterExpressions(), null,
                streamTypeService, methodResolutionService, timeProvider);

        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName());
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
