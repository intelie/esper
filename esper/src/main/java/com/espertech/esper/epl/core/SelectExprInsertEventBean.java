/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.core;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventBeanManufactureException;
import com.espertech.esper.event.EventBeanManufacturer;
import com.espertech.esper.event.WriteablePropertyDescriptor;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.event.map.MapEventType;
import com.espertech.esper.util.TypeWidener;
import com.espertech.esper.util.TypeWidenerFactory;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper for processing insert-into clauses and their select expression for writing from the
 * insert-into to an event underlying object.
 */
public class SelectExprInsertEventBean
{
    private static Log log = LogFactory.getLog(SelectExprInsertEventBean.class);

    private EventType eventType;
    private Set<WriteablePropertyDescriptor> writables;

    private EventBeanManufacturer eventManufacturer;

    private WriteablePropertyDescriptor writableProperties[];
    private ExprEvaluator[] exprEvaluators;
    private TypeWidener[] wideners;

    /**
     * Ctor.
     * @param eventAdapterService event factory
     * @param eventType event type to produce
     * @return helper instance or null if the type's underlying cannot be populated
     */
    public static SelectExprInsertEventBean getInsertUnderlying(EventAdapterService eventAdapterService, EventType eventType)
    {
        Set<WriteablePropertyDescriptor> writableProps = eventAdapterService.getWriteableProperties(eventType);
        if (writableProps == null)
        {
            return null;    // no writable properties, not a writable type, proceed
        }
        // For map event types this class does not handle fragment inserts; all fragments are required however and must be explicit
        if (eventType instanceof MapEventType) {
            for (EventPropertyDescriptor prop : eventType.getPropertyDescriptors()) {
                if (prop.isFragment()) {
                    return null;
                }
            }
        }
        return new SelectExprInsertEventBean(eventType, writableProps);
    }

    private SelectExprInsertEventBean(EventType eventType, Set<WriteablePropertyDescriptor> writables)
    {
        this.eventType = eventType;
        this.writables = writables;
    }

    /**
     * Initialize, validating writable properties and assigning a factory.
     * @param isUsingWildcard when wildcard is present
     * @param typeService event types
     * @param expressionNodes select-clause expressions
     * @param columnNames column names
     * @param expressionReturnTypes return types of expressions
     * @param methodResolutionService for resolving write methods
     * @param eventAdapterService event factory
     * @throws ExprValidationException if validation fails
     */
    public void initialize(boolean isUsingWildcard, StreamTypeService typeService, ExprEvaluator[] expressionNodes, String[] columnNames, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        try {
            initializeSetterManufactor(isUsingWildcard, typeService, expressionNodes, columnNames, expressionReturnTypes, methodResolutionService, eventAdapterService);
        }
        catch (ExprValidationException ex) {
            if (!(eventType instanceof BeanEventType)) {
                throw ex;
            }
            // Try constructor injection
            try {
                initializeCtorInjection(expressionNodes, typeService, expressionReturnTypes, methodResolutionService, eventAdapterService);
            }
            catch (ExprValidationException ctorEx) {
                if (writables.isEmpty()) {
                    throw ctorEx;
                }
                throw ex;
            }
        }
    }

    /**
     * Initialize, validating writable properties and assigning a factory.
     * @param streamNames names of streams
     * @param streamTypes types
     * @param methodResolutionService for resolving write methods
     * @param eventAdapterService event factory
     * @throws ExprValidationException if validation fails
     */
    public void initializeJoinWildcard(String[] streamNames, EventType[] streamTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        try {
            initializeJoinWildcardInternal(streamNames, streamTypes, methodResolutionService, eventAdapterService);
        }
        catch (ExprValidationException ex) {
            if (!(eventType instanceof BeanEventType)) {
                throw ex;
            }
            // Try constructor injection
            try {
                ExprEvaluator[] evaluators = new ExprEvaluator[streamTypes.length];
                Object[] resultTypes = new Object[streamTypes.length];
                for (int i = 0; i < streamTypes.length; i++) {
                    evaluators[i] = new ExprEvaluatorJoinWildcard(i, streamTypes[i].getUnderlyingType());
                    resultTypes[i] = evaluators[i].getType();
                }

                initializeCtorInjection(evaluators, null, resultTypes, methodResolutionService, eventAdapterService);
            }
            catch (ExprValidationException ctorEx) {
                if (writables.isEmpty()) {
                    throw ctorEx;
                }
                throw ex;
            }
        }
    }


    private void initializeJoinWildcardInternal(String[] streamNames, EventType[] streamTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
            throws ExprValidationException
        {
        List<WriteablePropertyDescriptor> writablePropertiesList = new ArrayList<WriteablePropertyDescriptor>();
        List<ExprEvaluator> evaluatorsList = new ArrayList<ExprEvaluator>();
        List<TypeWidener> widenersList = new ArrayList<TypeWidener>();

        // loop over all columns selected, if any
        for (int i = 0; i < streamNames.length; i++)
        {
            WriteablePropertyDescriptor selectedWritable = null;
            TypeWidener widener = null;

            for (WriteablePropertyDescriptor desc : writables)
            {
                if (!desc.getPropertyName().equals(streamNames[i]))
                {
                    continue;
                }

                widener = TypeWidenerFactory.getCheckPropertyAssignType(streamNames[i], streamTypes[i].getUnderlyingType(), desc.getType(), desc.getPropertyName());
                selectedWritable = desc;
                break;
            }

            if (selectedWritable == null)
            {
                String message = "Stream underlying object for stream '" + streamNames[i] +
                        "' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?)";
                throw new ExprValidationException(message);
            }

            final int streamNum = i;
            final Class returnType = streamTypes[streamNum].getUnderlyingType();
            ExprEvaluator evaluator = new ExprEvaluator() {
                public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                {
                    EventBean event = eventsPerStream[streamNum];
                    if (event != null)
                    {
                        return event.getUnderlying();
                    }
                    return null;
                }

                public Class getType()
                {
                    return returnType;
                }

                public Map<String, Object> getEventType() {
                    return null;
                }
            };

            // add
            writablePropertiesList.add(selectedWritable);
            evaluatorsList.add(evaluator);
            widenersList.add(widener);
        }

        // assign
        this.writableProperties = writablePropertiesList.toArray(new WriteablePropertyDescriptor[writablePropertiesList.size()]);
        this.exprEvaluators = evaluatorsList.toArray(new ExprEvaluator[evaluatorsList.size()]);
        this.wideners = widenersList.toArray(new TypeWidener[widenersList.size()]);

        try
        {
            eventManufacturer = eventAdapterService.getManufacturer(eventType, writableProperties, methodResolutionService);
        }
        catch (EventBeanManufactureException e)
        {
            throw new ExprValidationException(e.getMessage(), e);
        }
    }

    private void initializeCtorInjection(ExprEvaluator[] exprEvaluators, StreamTypeService typeService, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
        throws ExprValidationException {

        BeanEventType beanEventType = (BeanEventType) eventType;

        Class[] ctorTypes = new Class[expressionReturnTypes.length];
        ExprEvaluator[] evaluators = new ExprEvaluator[exprEvaluators.length];

        for (int i = 0; i < expressionReturnTypes.length; i++) {
            Object columnType = expressionReturnTypes[i];

            if (columnType instanceof Class || columnType == null) {
                ctorTypes[i] = (Class) expressionReturnTypes[i];
                evaluators[i] = exprEvaluators[i];
                continue;
            }

            if (columnType instanceof EventType) {
                EventType columnEventType = (EventType) columnType;
                final Class returnType = columnEventType.getUnderlyingType();
                int streamNum = 0;
                for (int j = 0; j < typeService.getEventTypes().length; j++)
                {
                    if (typeService.getEventTypes()[j] == columnEventType)
                    {
                        streamNum = j;
                        break;
                    }
                }
                final int streamNumEval = streamNum;
                evaluators[i] = new ExprEvaluator() {
                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                    {
                        EventBean event = eventsPerStream[streamNumEval];
                        if (event != null)
                        {
                            return event.getUnderlying();
                        }
                        return null;
                    }

                    public Class getType()
                    {
                        return returnType;
                    }

                    public Map<String, Object> getEventType() {
                        return null;
                    }
                };
                continue;
            }

            // handle case where the select-clause contains an fragment array            
            if (columnType instanceof EventType[])
            {
                EventType columnEventType = ((EventType[]) columnType)[0];
                final Class componentReturnType = columnEventType.getUnderlyingType();

                final ExprEvaluator inner = exprEvaluators[i];
                evaluators[i] = new ExprEvaluator() {
                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                    {
                        Object result = inner.evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
                        if (!(result instanceof EventBean[])) {
                            return null;
                        }
                        EventBean[] events = (EventBean[]) result;
                        Object values = Array.newInstance(componentReturnType, events.length);
                        for (int i = 0; i < events.length; i++) {
                            Array.set(values, i, events[i].getUnderlying());
                        }
                        return values;
                    }

                    public Class getType()
                    {
                        return componentReturnType;
                    }

                    public Map<String, Object> getEventType() {
                        return null;
                    }
                };
                continue;
            }

            String message = "Invalid assignment of expression " + i + " returning type '" + columnType +
                    "', column and parameter types mismatch";
            throw new ExprValidationException(message);
        }

        FastConstructor fctor;
        try {
            Constructor ctor = methodResolutionService.resolveCtor(beanEventType.getUnderlyingType(), ctorTypes);
            FastClass fastClass = FastClass.create(beanEventType.getUnderlyingType());
            fctor = fastClass.getConstructor(ctor);
        }
        catch (EngineImportException ex) {
            throw new ExprValidationException("Failed to find a suitable constructor for bean-event type '" + eventType.getName() + "': " + ex.getMessage(), ex);
        }

        this.exprEvaluators = evaluators;
        this.wideners = new TypeWidener[evaluators.length];
        this.eventManufacturer = new EventBeanManufacturerCtor(fctor, beanEventType, eventAdapterService);
    }

    private void initializeSetterManufactor(boolean isUsingWildcard, StreamTypeService typeService, ExprEvaluator[] expressionNodes, String[] columnNames, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        List<WriteablePropertyDescriptor> writablePropertiesList = new ArrayList<WriteablePropertyDescriptor>();
        List<ExprEvaluator> evaluatorsList = new ArrayList<ExprEvaluator>();
        List<TypeWidener> widenersList = new ArrayList<TypeWidener>();

        // loop over all columns selected, if any
        for (int i = 0; i < columnNames.length; i++)
        {
            WriteablePropertyDescriptor selectedWritable = null;
            TypeWidener widener = null;
            ExprEvaluator evaluator = expressionNodes[i];

            for (WriteablePropertyDescriptor desc : writables)
            {
                if (!desc.getPropertyName().equals(columnNames[i]))
                {
                    continue;
                }

                Object columnType = expressionReturnTypes[i];
                if (columnType == null)
                {
                    TypeWidenerFactory.getCheckPropertyAssignType(columnNames[i], null, desc.getType(), desc.getPropertyName());
                }
                else if (columnType instanceof EventType)
                {
                    EventType columnEventType = (EventType) columnType;
                    final Class returnType = columnEventType.getUnderlyingType();
                    widener = TypeWidenerFactory.getCheckPropertyAssignType(columnNames[i], columnEventType.getUnderlyingType(), desc.getType(), desc.getPropertyName());
                    int streamNum = 0;
                    for (int j = 0; j < typeService.getEventTypes().length; j++)
                    {
                        if (typeService.getEventTypes()[j] == columnEventType)
                        {
                            streamNum = j;
                            break;
                        }
                    }
                    final int streamNumEval = streamNum;
                    evaluator = new ExprEvaluator() {
                        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                        {
                            EventBean event = eventsPerStream[streamNumEval];
                            if (event != null)
                            {
                                return event.getUnderlying();
                            }
                            return null;
                        }

                        public Class getType()
                        {
                            return returnType;
                        }

                        public Map<String, Object> getEventType() {
                            return null;
                        }
                    };
                }
                // handle case where the select-clause contains an fragment array
                else if (columnType instanceof EventType[])
                {
                    EventType columnEventType = ((EventType[]) columnType)[0];
                    final Class componentReturnType = columnEventType.getUnderlyingType();
                    final Class arrayReturnType = Array.newInstance(componentReturnType, 0).getClass();

                    widener = TypeWidenerFactory.getCheckPropertyAssignType(columnNames[i], arrayReturnType, desc.getType(), desc.getPropertyName());
                    final ExprEvaluator inner = evaluator;
                    evaluator = new ExprEvaluator() {
                        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                        {
                            Object result = inner.evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
                            if (!(result instanceof EventBean[])) {
                                return null;
                            }
                            EventBean[] events = (EventBean[]) result;
                            Object values = Array.newInstance(componentReturnType, events.length);
                            for (int i = 0; i < events.length; i++) {
                                Array.set(values, i, events[i].getUnderlying());
                            }
                            return values;
                        }

                        public Class getType()
                        {
                            return componentReturnType;
                        }

                        public Map<String, Object> getEventType() {
                            return null;
                        }
                    };
                }
                else if (!(columnType instanceof Class))
                {
                    String message = "Invalid assignment of column '" + columnNames[i] +
                            "' of type '" + columnType +
                            "' to event property '" + desc.getPropertyName() +
                            "' typed as '" + desc.getType().getName() +
                            "', column and parameter types mismatch";
                    throw new ExprValidationException(message);
                }
                else
                {
                    widener = TypeWidenerFactory.getCheckPropertyAssignType(columnNames[i], (Class) columnType, desc.getType(), desc.getPropertyName());
                }

                selectedWritable = desc;
                break;
            }

            if (selectedWritable == null)
            {
                String message = "Column '" + columnNames[i] +
                        "' could not be assigned to any of the properties of the underlying type (missing column names, event property, setter method or constructor?)";
                throw new ExprValidationException(message);
            }

            // add
            writablePropertiesList.add(selectedWritable);
            evaluatorsList.add(evaluator);
            widenersList.add(widener);
        }

        // handle wildcard
        if (isUsingWildcard)
        {
            EventType sourceType = typeService.getEventTypes()[0];
            for (EventPropertyDescriptor eventPropDescriptor : sourceType.getPropertyDescriptors())
            {
                if (eventPropDescriptor.isRequiresIndex() || (eventPropDescriptor.isRequiresMapkey()))
                {
                    continue;
                }

                WriteablePropertyDescriptor selectedWritable = null;
                TypeWidener widener = null;
                ExprEvaluator evaluator = null;

                for (WriteablePropertyDescriptor writableDesc : writables)
                {
                    if (!writableDesc.getPropertyName().equals(eventPropDescriptor.getPropertyName()))
                    {
                        continue;
                    }

                    widener = TypeWidenerFactory.getCheckPropertyAssignType(eventPropDescriptor.getPropertyName(), eventPropDescriptor.getPropertyType(), writableDesc.getType(), writableDesc.getPropertyName());
                    selectedWritable = writableDesc;

                    final String propertyName = eventPropDescriptor.getPropertyName();
                    final Class propertyType = eventPropDescriptor.getPropertyType();
                    evaluator = new ExprEvaluator() {

                        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData,ExprEvaluatorContext exprEvaluatorContext)
                        {
                            EventBean event = eventsPerStream[0];
                            if (event != null)
                            {
                                return event.get(propertyName);
                            }
                            return null;
                        }

                        public Class getType()
                        {
                            return propertyType;
                        }
                        public Map<String, Object> getEventType() {
                            return null;
                        }
                    };
                    break;
                }

                if (selectedWritable == null)
                {
                    String message = "Event property '" + eventPropDescriptor.getPropertyName() +
                            "' could not be assigned to any of the properties of the underlying type (missing column names, event property, setter method or constructor?)";
                    throw new ExprValidationException(message);
                }

                writablePropertiesList.add(selectedWritable);
                evaluatorsList.add(evaluator);
                widenersList.add(widener);
            }
        }

        // assign
        this.writableProperties = writablePropertiesList.toArray(new WriteablePropertyDescriptor[writablePropertiesList.size()]);
        this.exprEvaluators = evaluatorsList.toArray(new ExprEvaluator[evaluatorsList.size()]);
        this.wideners = widenersList.toArray(new TypeWidener[widenersList.size()]);

        try
        {
            eventManufacturer = eventAdapterService.getManufacturer(eventType, writableProperties, methodResolutionService);
        }
        catch (EventBeanManufactureException e)
        {
            throw new ExprValidationException(e.getMessage(), e);
        }
    }

    /**
     * Manufacture an event for events-per-stream.
     * @param eventsPerStream result events
     * @param newData flag whether insert or remove stream
     * @param exprEvaluatorContext context for expression evalauation
     * @return manufactured event
     */
    public EventBean manufacture(EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object[] values = new Object[exprEvaluators.length];

        for (int i = 0; i < exprEvaluators.length; i++)
        {
            Object evalResult = exprEvaluators[i].evaluate(eventsPerStream, newData, exprEvaluatorContext);
            if ((evalResult != null) && (wideners[i] != null))
            {
                evalResult = wideners[i].widen(evalResult);
            }
            values[i] = evalResult;
        }

        return eventManufacturer.make(values);
    }

    public static class EventBeanManufacturerCtor implements EventBeanManufacturer {

        private final FastConstructor fastConstructor;
        private final BeanEventType beanEventType;
        private final EventAdapterService eventAdapterService;

        public EventBeanManufacturerCtor(FastConstructor fastConstructor, BeanEventType beanEventType, EventAdapterService eventAdapterService) {
            this.fastConstructor = fastConstructor;
            this.beanEventType = beanEventType;
            this.eventAdapterService = eventAdapterService;
        }

        public EventBean make(Object[] properties) {
            try {
                Object instance = fastConstructor.newInstance(properties);
                return eventAdapterService.adapterForTypedBean(instance, beanEventType);
            }
            catch (InvocationTargetException e) {
                throw new EPException("InvocationTargetException received invoking constructor for type '" + beanEventType.getName() + "': " + e.getTargetException().getMessage(), e.getTargetException());
            }
        }
    }

    public static class ExprEvaluatorJoinWildcard implements ExprEvaluator {
        private final int streamNum;
        private final Class returnType;

        public ExprEvaluatorJoinWildcard(int streamNum, Class returnType) {
            this.streamNum = streamNum;
            this.returnType = returnType;
        }

        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            EventBean bean = eventsPerStream[streamNum];
            if (bean == null) {
                return null;
            }
            return bean.getUnderlying();
        }

        public Class getType() {
            return returnType;
        }

        public Map<String, Object> getEventType() throws ExprValidationException {
            return null;
        }
    }

}
