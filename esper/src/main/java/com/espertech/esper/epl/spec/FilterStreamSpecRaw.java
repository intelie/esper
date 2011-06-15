/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.client.EventType;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.property.PropertyEvaluator;
import com.espertech.esper.epl.property.PropertyEvaluatorFactory;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecCompiler;
import com.espertech.esper.util.MetaDefItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Unvalided filter-based stream specification.
 */
public class FilterStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw, MetaDefItem, Serializable
{
    private static Log log = LogFactory.getLog(FilterStreamSpecRaw.class);
    private FilterSpecRaw rawFilterSpec;
    private static final long serialVersionUID = -7919060568262701953L;


    /**
     * Ctor.
     * @param rawFilterSpec is unvalidated filter specification
     * @param viewSpecs is the view definition
     * @param optionalStreamName is the stream name if supplied, or null if not supplied
     * @param streamSpecOptions - additional options, such as unidirectional stream in a join
     */
    public FilterStreamSpecRaw(FilterSpecRaw rawFilterSpec, List<ViewSpec> viewSpecs, String optionalStreamName, StreamSpecOptions streamSpecOptions)
    {
        super(optionalStreamName, viewSpecs, streamSpecOptions);
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

    public StreamSpecCompiled compile(StatementContext context, Set<String> eventTypeReferences, boolean isInsertInto, Collection<Integer> assignedTypeNumberStack)
            throws ExprValidationException
    {
        // Determine the event type
        String eventName = rawFilterSpec.getEventTypeName();

        // Could be a named window
        if (context.getNamedWindowService().isNamedWindow(eventName))
        {
            EventType namedWindowType = context.getNamedWindowService().getProcessor(eventName).getTailView().getEventType();
            StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {namedWindowType}, new String[] {"s0"}, new boolean[] {true}, context.getEngineURI(), false);

            List<ExprNode> validatedNodes = FilterSpecCompiler.validateAllowSubquery(rawFilterSpec.getFilterExpressions(), streamTypeService, context, null, null);

            PropertyEvaluator optionalPropertyEvaluator = null;
            if (rawFilterSpec.getOptionalPropertyEvalSpec() != null) {
                optionalPropertyEvaluator = PropertyEvaluatorFactory.makeEvaluator(rawFilterSpec.getOptionalPropertyEvalSpec(), namedWindowType, this.getOptionalStreamName(), context.getEventAdapterService(), context.getMethodResolutionService(), context.getTimeProvider(), context.getVariableService(), context.getEngineURI(), context.getStatementId(), context.getStatementName(), context.getAnnotations(), assignedTypeNumberStack);
            }
            eventTypeReferences.add(((EventTypeSPI) namedWindowType).getMetadata().getPrimaryName());
            return new NamedWindowConsumerStreamSpec(eventName, this.getOptionalStreamName(), this.getViewSpecs(), validatedNodes, this.getOptions(), optionalPropertyEvaluator);
        }

        EventType eventType = null;

        if (context.getValueAddEventService().isRevisionTypeName(eventName))
        {
            eventType = context.getValueAddEventService().getValueAddUnderlyingType(eventName);
            eventTypeReferences.add(((EventTypeSPI) eventType).getMetadata().getPrimaryName());
        }

        if (eventType == null)
        {
            eventType = resolveType(context.getEngineURI(), eventName, context.getEventAdapterService(), context.getPlugInTypeResolutionURIs());
            if (eventType instanceof EventTypeSPI) {
                eventTypeReferences.add(((EventTypeSPI) eventType).getMetadata().getPrimaryName());                
            }
        }

        // Validate all nodes, make sure each returns a boolean and types are good;
        // Also decompose all AND super nodes into individual expressions
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {super.getOptionalStreamName()}, new boolean[] {true}, context.getEngineURI(), false);

        FilterSpecCompiled spec = FilterSpecCompiler.makeFilterSpec(eventType, eventName, rawFilterSpec.getFilterExpressions(), rawFilterSpec.getOptionalPropertyEvalSpec(),
                null, null,  // no tags
                streamTypeService, context.getMethodResolutionService(), context.getSchedulingService(), context.getVariableService(), context.getEventAdapterService(), context.getEngineURI(), this.getOptionalStreamName(), context, assignedTypeNumberStack);

        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName(), this.getOptions());
    }

    /**
     * Resolves a given event name to an event type.
     * @param eventName is the name to resolve
     * @param eventAdapterService for resolving event types
     * @param engineURI the provider URI
     * @param optionalResolutionURIs is URIs for resolving the event name against plug-inn event representations, if any
     * @return event type
     * @throws ExprValidationException if the info cannot be resolved
     */
    public static EventType resolveType(String engineURI, String eventName, EventAdapterService eventAdapterService, URI[] optionalResolutionURIs)
            throws ExprValidationException
    {
        EventType eventType = eventAdapterService.getExistsTypeByName(eventName);

        // may already be known
        if (eventType != null)
        {
            return eventType;
        }

        String engineURIQualifier = engineURI;
        if (engineURI == null || EPServiceProviderSPI.DEFAULT_ENGINE_URI.equals(engineURI))
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
                    eventType = eventAdapterService.getExistsTypeByName(eventNameRemainder);
                }
            }
        }

        // may now be known
        if (eventType != null)
        {
            return eventType;
        }

        // The type is not known yet, attempt to add as a JavaBean type with the same name
        String message = null;
        try
        {
            eventType = eventAdapterService.addBeanType(eventName, eventName, true, false, false, false);
        }
        catch (EventAdapterException ex)
        {
            log.info(".resolveType Event type named '" + eventName + "' not resolved as Java-Class event");
            message = "Failed to resolve event type: " + ex.getMessage();
        }

        // Attempt to use plug-in event types
        try
        {
            eventType = eventAdapterService.addPlugInEventType(eventName, optionalResolutionURIs, null);
        }
        catch (EventAdapterException ex)
        {
            log.debug(".resolveType Event type named '" + eventName + "' not resolved by plug-in event representations");
            // remains unresolved
        }

        if (eventType == null)
        {
            throw new ExprValidationException(message);
        }
        return eventType;
    }
}
