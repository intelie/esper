package com.espertech.esper.epl.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventBeanManufactureException;
import com.espertech.esper.event.EventBeanManufacturer;
import com.espertech.esper.event.WriteablePropertyDescriptor;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.SimpleNumberCoercer;
import com.espertech.esper.util.SimpleNumberCoercerFactory;
import com.espertech.esper.util.TypeWidener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectExprInsertEventBean
{
    private static Log log = LogFactory.getLog(SelectExprInsertEventBean.class);

    private EventType eventType;
    private Set<WriteablePropertyDescriptor> writables;

    private EventBeanManufacturer eventManufacturer;

    private WriteablePropertyDescriptor writableProperties[];
    private ExprEvaluator[] expressionNodes;
    private TypeWidener[] wideners;

    public static SelectExprInsertEventBean getInsertUnderlying(EventAdapterService eventAdapterService, EventType eventType)
    {
        Set<WriteablePropertyDescriptor> writableProps = eventAdapterService.getWriteableProperties(eventType);
        if (writableProps == null)
        {
            return null;    // no writable properties, not a writable type, proceed
        }
        return new SelectExprInsertEventBean(eventType, writableProps);
    }

    private SelectExprInsertEventBean(EventType eventType, Set<WriteablePropertyDescriptor> writables)
    {
        this.eventType = eventType;
        this.writables = writables;
    }

    public void initializeJoinWildcard(String[] streamNames, EventType[] streamTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
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

                widener = checkAssignment(streamNames[i], streamTypes[i].getUnderlyingType(), desc);
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
            ExprEvaluator evaluator = new ExprEvaluator() {
                public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
                {
                    EventBean event = eventsPerStream[streamNum];
                    if (event != null)
                    {
                        return event.getUnderlying();
                    }
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
        this.expressionNodes = evaluatorsList.toArray(new ExprEvaluator[evaluatorsList.size()]);
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

    public void initialize(boolean isUsingWildcard, StreamTypeService typeService, ExprEvaluator[] expressionNodes, String[] columnNames, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
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
                    checkAssignment(columnNames[i], (Class) columnType, desc);
                }
                else if (columnType instanceof EventType)
                {
                    EventType columnEventType = (EventType) columnType;
                    widener = checkAssignment(columnNames[i], columnEventType.getUnderlyingType(), desc);
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
                        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
                        {
                            EventBean event = eventsPerStream[streamNumEval];
                            if (event != null)
                            {
                                return event.getUnderlying();
                            }
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
                    widener = checkAssignment(columnNames[i], (Class) columnType, desc);                    
                }

                selectedWritable = desc;
                break;
            }

            if (selectedWritable == null)
            {
                String message = "Column '" + columnNames[i] +
                        "' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?)";
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

                    widener = checkAssignment(eventPropDescriptor.getPropertyName(), eventPropDescriptor.getPropertyType(), writableDesc);
                    selectedWritable = writableDesc;

                    final String propertyName = eventPropDescriptor.getPropertyName();
                    evaluator = new ExprEvaluator() {

                        public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
                        {
                            EventBean event = eventsPerStream[0];
                            if (event != null)
                            {
                                return event.get(propertyName);
                            }
                            return null;
                        }
                    };
                    break;
                }

                if (selectedWritable == null)
                {
                    String message = "Event property '" + eventPropDescriptor.getPropertyName() +
                            "' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?)";
                    throw new ExprValidationException(message);
                }

                writablePropertiesList.add(selectedWritable);
                evaluatorsList.add(evaluator);
                widenersList.add(widener);
            }
        }

        // assign
        this.writableProperties = writablePropertiesList.toArray(new WriteablePropertyDescriptor[writablePropertiesList.size()]);
        this.expressionNodes = evaluatorsList.toArray(new ExprEvaluator[evaluatorsList.size()]);
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

    private TypeWidener checkAssignment(String columnName, Class columnType, WriteablePropertyDescriptor desc)
            throws ExprValidationException
    {
        Class columnClass = (Class) columnType;
        Class columnClassBoxed = JavaClassHelper.getBoxedType((Class) columnType);
        Class targetClass = desc.getType();
        Class targetClassBoxed = JavaClassHelper.getBoxedType(desc.getType());

        if (columnClass == null)
        {
            if (targetClass.isPrimitive())
            {
                String message = "Invalid assignment of column '" + columnName +
                        "' of null type to event property '" + desc.getPropertyName() +
                        "' typed as '" + desc.getType().getName() +
                        "', nullable type mismatch";
                throw new ExprValidationException(message);
            }
        }
        else if (columnClassBoxed != targetClassBoxed)
        {
            if (columnClassBoxed == String.class && targetClassBoxed == Character.class)
            {
                return new StringToCharCoercer();
            }
            else if (!JavaClassHelper.isAssignmentCompatible(columnClassBoxed, targetClassBoxed))
            {
                String message = "Invalid assignment of column '" + columnName +
                        "' of type '" + columnClass.getName() +
                        "' to event property '" + desc.getPropertyName() +
                        "' typed as '" + desc.getType().getName() +
                        "', column and parameter types mismatch";
                throw new ExprValidationException(message);
            }

            if (!columnClass.isPrimitive() && JavaClassHelper.isNumeric(targetClass))
            {
                final SimpleNumberCoercer coercer = SimpleNumberCoercerFactory.getCoercer(columnClassBoxed, targetClassBoxed);
                return new TypeWidener()
                {
                    public Object widen(Object input)
                    {
                       return coercer.coerceBoxed((Number) input);
                    }
                };
            }
        }

        return null;
    }

    public EventBean manufacture(EventBean[] eventsPerStream, boolean newData)
    {
        Object[] values = new Object[writableProperties.length];

        for (int i = 0; i < writableProperties.length; i++)
        {
            Object evalResult = expressionNodes[i].evaluate(eventsPerStream, newData);
            if ((evalResult != null) && (wideners[i] != null))
            {
                evalResult = wideners[i].widen(evalResult);
            }
            values[i] = evalResult;
        }

        return eventManufacturer.make(values);
    }

    private class StringToCharCoercer implements TypeWidener
    {
        public Object widen(Object input)
        {
            String result = input.toString();
            if ((result != null) && (result.length() > 0))
            {
                return result.charAt(0);
            }
            return null;
        }
    }
}
