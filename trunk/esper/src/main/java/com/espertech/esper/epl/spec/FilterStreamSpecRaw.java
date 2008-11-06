/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.vaevent.ValueAddEventService;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecCompiler;
import com.espertech.esper.pattern.PatternObjectResolutionService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.List;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Unvalided filter-based stream specification.
 */
public class FilterStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw, MetaDefItem
{
    private static Log log = LogFactory.getLog(FilterStreamSpecRaw.class);
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
                                      ValueAddEventService valueAddEventService,
                                      VariableService variableService,
                                      String engineURI,
                                      URI[] optionalPlugInTypeResolutionURIS)
            throws ExprValidationException
    {
        // Determine the event type
        String eventName = rawFilterSpec.getEventTypeAlias();

        // Could be a named window
        if (namedWindowService.isNamedWindow(eventName))
        {
            EventType namedWindowType = namedWindowService.getProcessor(eventName).getTailView().getEventType();
            StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {namedWindowType}, new String[] {"s0"}, engineURI, new String[] {eventName});

            List<ExprNode> validatedNodes = FilterSpecCompiler.validateDisallowSubquery(rawFilterSpec.getFilterExpressions(),
                streamTypeService, methodResolutionService, timeProvider, variableService);
            
            return new NamedWindowConsumerStreamSpec(eventName, this.getOptionalStreamName(), this.getViewSpecs(), validatedNodes, this.isUnidirectional());
        }

        EventType eventType = null;

        if (valueAddEventService.isRevisionTypeAlias(eventName))
        {
            eventType = valueAddEventService.getValueAddUnderlyingType(eventName);
        }

        if (eventType == null)
        {
            eventType = resolveType(engineURI, eventName, eventAdapterService, optionalPlugInTypeResolutionURIS);
        }

        // Validate all nodes, make sure each returns a boolean and types are good;
        // Also decompose all AND super nodes into individual expressions
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"}, engineURI, new String[] {eventName});

        FilterSpecCompiled spec = FilterSpecCompiler.makeFilterSpec(eventType, eventName, rawFilterSpec.getFilterExpressions(),
                null, null,  // no tags
                streamTypeService, methodResolutionService, timeProvider, variableService, eventAdapterService);

        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName(), this.isUnidirectional());
    }

    /**
     * Resolves a given event alias to an event type.
     * @param eventName is the alias to resolve
     * @param eventAdapterService for resolving event types
     * @param engineURI the provider URI
     * @param optionalResolutionURIs is URIs for resolving the event name against plug-inn event representations, if any
     * @return event type
     * @throws ExprValidationException if the info cannot be resolved
     */
    protected static EventType resolveType(String engineURI, String eventName, EventAdapterService eventAdapterService, URI[] optionalResolutionURIs)
            throws ExprValidationException
    {
        EventType eventType = eventAdapterService.getExistsTypeByAlias(eventName);

        // may already be known
        if (eventType != null)
        {
            return eventType;
        }

        String engineURIQualifier = engineURI;
        if (engineURI == null)
        {
            engineURIQualifier = EPServiceProviderSPI.DEFAULT_ENGINE_URI__QUALIFIER;
        }

        // The event name can be prefixed by the engine URI, i.e. "select * from default.MyEvent"
        if (eventName.startsWith(engineURIQualifier))
        {
            int indexDot = eventName.indexOf(".");
            if (indexDot > 0)
            {
                String eventNameURI = eventName.substring(0, indexDot);
                String eventNameRemainder = eventName.substring(indexDot + 1);

                if (engineURIQualifier.equals(eventNameURI))
                {
                    eventType = eventAdapterService.getExistsTypeByAlias(eventNameRemainder);
                }
            }
        }

        // may now be known
        if (eventType != null)
        {
            return eventType;
        }

        // The type is not known yet, attempt to add as a JavaBean type with the same alias
        String message = null;
        try
        {
            eventType = eventAdapterService.addBeanType(eventName, eventName, true);
        }
        catch (EventAdapterException ex)
        {
            log.info(".resolveType Event type alias '" + eventName + "' not resolved as POJO event");
            message = "Failed to resolve event type: " + ex.getMessage();
        }

        // Attempt to use plug-in event types
        try
        {
            eventType = eventAdapterService.addPlugInEventType(eventName, optionalResolutionURIs, null);
        }
        catch (EventAdapterException ex)
        {
            log.debug(".resolveType Event type alias '" + eventName + "' not resolved by plug-in event representations");
            // remains unresolved
        }

        if (eventType == null)
        {
            throw new ExprValidationException(message);
        }
        return eventType;
    }
}
