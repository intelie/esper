/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.vaevent.ValueAddEventService;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecCompiler;
import com.espertech.esper.pattern.*;
import com.espertech.esper.pattern.observer.ObserverFactory;
import com.espertech.esper.pattern.observer.ObserverParameterException;
import com.espertech.esper.pattern.guard.GuardFactory;
import com.espertech.esper.pattern.guard.GuardParameterException;
import com.espertech.esper.util.UuidGenerator;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.collection.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.net.URI;

/**
 * Pattern specification in unvalidated, unoptimized form.
 */
public class PatternStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw
{
    private final EvalNode evalNode;

    /**
     * Ctor.
     * @param evalNode - pattern evaluation node representing pattern statement
     * @param viewSpecs - specifies what view to use to derive data
     * @param optionalStreamName - stream name, or null if none supplied
     * @param isUnidirectional - true to indicate a unidirectional stream in a join, applicable for joins
     */
    public PatternStreamSpecRaw(EvalNode evalNode, List<ViewSpec> viewSpecs, String optionalStreamName, boolean isUnidirectional)
    {
        super(optionalStreamName, viewSpecs, isUnidirectional);
        this.evalNode = evalNode;
    }

    /**
     * Returns the pattern expression evaluation node for the top pattern operator.
     * @return parent pattern expression node
     */
    public EvalNode getEvalNode()
    {
        return evalNode;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      MethodResolutionService methodResolutionService,
                                      PatternObjectResolutionService patternObjectResolutionService,
                                      TimeProvider timeProvider,
                                      NamedWindowService namedWindowService,
                                      ValueAddEventService valueAddEventService,
                                      VariableService variableService,
                                      String engineURI,
                                      URI[] plugInTypeResolutionURIs)
            throws ExprValidationException
    {
        // Determine all the filter nodes used in the pattern
        EvalNodeAnalysisResult evalNodeAnalysisResult = EvalNode.recursiveAnalyzeChildNodes(evalNode);

        // Resolve guard and observers factories 
        try
        {
            for (EvalGuardNode guardNode : evalNodeAnalysisResult.getGuardNodes())
            {
                GuardFactory guardFactory = patternObjectResolutionService.create(guardNode.getPatternGuardSpec());
                guardFactory.setGuardParameters(guardNode.getPatternGuardSpec().getObjectParameters());
                guardNode.setGuardFactory(guardFactory);
            }
            for (EvalObserverNode observerNode : evalNodeAnalysisResult.getObserverNodes())
            {
                ObserverFactory observerFactory = patternObjectResolutionService.create(observerNode.getPatternObserverSpec());
                observerFactory.setObserverParameters(observerNode.getPatternObserverSpec().getObjectParameters());
                observerNode.setObserverFactory(observerFactory);
            }
        }
        catch (ObserverParameterException e)
        {
            throw new ExprValidationException("Invalid parameter for pattern observer: " + e.getMessage(), e); 
        }
        catch (GuardParameterException e)
        {
            throw new ExprValidationException("Invalid parameter for pattern guard: " + e.getMessage(), e);
        }
        catch (PatternObjectException e)
        {
            throw new ExprValidationException("Failed to resolve pattern object: " + e.getMessage(), e); 
        }

        // Resolve all event types; some filters are tagged and we keep the order in which they are specified
        LinkedHashMap<String, Pair<EventType, String>> taggedEventTypes = new LinkedHashMap<String, Pair<EventType, String>>();
        for (EvalFilterNode filterNode : evalNodeAnalysisResult.getFilterNodes())
        {
            String eventName = filterNode.getRawFilterSpec().getEventTypeAlias();
            EventType eventType = FilterStreamSpecRaw.resolveType(engineURI, eventName, eventAdapterService, plugInTypeResolutionURIs);
            String optionalTag = filterNode.getEventAsName();

            // If a tag was supplied for the type, the tags must stay with this type, i.e. a=BeanA -> b=BeanA -> a=BeanB is a no
            if (optionalTag != null)
            {
                Pair<EventType, String> pair = taggedEventTypes.get(optionalTag);
                EventType existingType = null;
                if (pair != null)
                {
                    existingType = pair.getFirst();
                }                
                if ((existingType != null) && (existingType != eventType))
                {
                    throw new IllegalArgumentException("Tag '" + optionalTag + "' for event '" + eventName +
                            "' has already been declared for events of type " + existingType.getUnderlyingType().getName());
                }
                pair = new Pair<EventType, String>(eventType, eventName);
                taggedEventTypes.put(optionalTag, pair);
            }

            // For this filter, filter types are all known tags at this time,
            // and additionally stream 0 (self) is our event type.
            // Stream type service allows resolution by property name event if that name appears in other tags.
            // by defaulting to stream zero.
            // Stream zero is always the current event type, all others follow the order of the map (stream 1 to N).
            String selfStreamName = optionalTag;
            if (selfStreamName == null)
            {
                selfStreamName = "s_" + UuidGenerator.generate(filterNode);
            }
            LinkedHashMap<String, Pair<EventType, String>> filterTypes = new LinkedHashMap<String, Pair<EventType, String>>();
            Pair<EventType, String> typePair = new Pair<EventType, String>(eventType, eventName);
            filterTypes.put(selfStreamName, typePair);
            filterTypes.putAll(taggedEventTypes);
            StreamTypeService streamTypeService = new StreamTypeServiceImpl(filterTypes, engineURI, true, false);

            List<ExprNode> exprNodes = filterNode.getRawFilterSpec().getFilterExpressions();
            FilterSpecCompiled spec = FilterSpecCompiler.makeFilterSpec(eventType, eventName, exprNodes, taggedEventTypes, streamTypeService, methodResolutionService, timeProvider, variableService);
            filterNode.setFilterSpec(spec);
        }

        return new PatternStreamSpecCompiled(evalNode, taggedEventTypes, this.getViewSpecs(), this.getOptionalStreamName(), this.isUnidirectional());
    }
}
