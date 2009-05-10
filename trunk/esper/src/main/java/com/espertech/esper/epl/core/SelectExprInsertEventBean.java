package com.espertech.esper.epl.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventBeanManufactureException;
import com.espertech.esper.event.EventBeanManufacturer;
import com.espertech.esper.event.WriteablePropertyDescriptor;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.TypeWidener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

public class SelectExprInsertEventBean
{
    private static Log log = LogFactory.getLog(SelectExprInsertEventBean.class);

    private EventType eventType;
    private Set<WriteablePropertyDescriptor> writables;

    private ExprEvaluator[] expressionNodes;
    private TypeWidener[] wideners;
    private EventBeanManufacturer manufacturer;
    private WriteablePropertyDescriptor writableProperties[];

    public static SelectExprInsertEventBean getManufacturer(EventAdapterService eventAdapterService, EventType eventType)
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

    public void initialize(boolean isUsingWildcard, StreamTypeService typeService, ExprEvaluator[] expressionNodes, String[] columnNames, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        this.expressionNodes = expressionNodes;
        this.wideners = new TypeWidener[columnNames.length];
        writableProperties = new WriteablePropertyDescriptor[columnNames.length];

        // loop over all columns selected
        for (int i = 0; i < columnNames.length; i++)
        {
            WriteablePropertyDescriptor selectedWritable = null;
            for (WriteablePropertyDescriptor desc : writables)
            {
                if (!desc.getPropertyName().equals(columnNames[i]))
                {
                    continue;
                }

                String columnName = columnNames[i];
                Object columnType = expressionReturnTypes[i];
                if (!(columnType instanceof Class) && (columnType != null))
                {
                    String message = "Invalid assignment of column '" + columnName +
                            "' of type '" + columnType +
                            "' to event property '" + desc.getPropertyName() +
                            "' typed as '" + desc.getType().getName() +
                            "', column and parameter types mismatch";
                    throw new ExprValidationException(message);
                }

                Class columnClass = (Class) columnType;
                Class columnClassBoxed = JavaClassHelper.getBoxedType((Class) columnType);
                Class targetClass = desc.getType();
                Class targetClassBoxed = JavaClassHelper.getBoxedType(desc.getType());

                if ((columnClass == null) && (targetClass.isPrimitive()))
                {
                    String message = "Invalid assignment of column '" + columnName +
                            "' of null type to event property '" + desc.getPropertyName() +
                            "' typed as '" + desc.getType().getName() +
                            "', nullable type mismatch";
                    throw new ExprValidationException(message);
                }
                else if (columnClassBoxed != targetClassBoxed)
                {
                    if (columnClassBoxed == String.class && targetClassBoxed == Character.class)
                    {
                        wideners[i] = new StringToCharCoercer();
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
            writableProperties[i] = selectedWritable;
        }

        try
        {
            manufacturer = eventAdapterService.getManufacturer(eventType, writableProperties, methodResolutionService);
        }
        catch (EventBeanManufactureException e)
        {
            throw new ExprValidationException(e.getMessage(), e);
        }
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

        return manufacturer.make(values);
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
