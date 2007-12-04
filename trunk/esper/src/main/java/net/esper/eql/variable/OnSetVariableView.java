package net.esper.eql.variable;

import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.OnTriggerSetAssignment;
import net.esper.eql.spec.OnTriggerSetDesc;
import net.esper.event.EventAdapterService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.util.JavaClassHelper;
import net.esper.view.ViewSupport;
import net.esper.collection.SingleEventIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OnSetVariableView extends ViewSupport
{
    private static final Log log = LogFactory.getLog(OnSetVariableView.class);
    private final OnTriggerSetDesc desc;
    private final EventAdapterService eventAdapterService;
    private final VariableService variableService;
    private final EventType eventType;
    private final VariableReader[] readers;
    private final EventBean[] eventsPerStream = new EventBean[1];
    private final boolean[] mustCoerce;

    public OnSetVariableView(OnTriggerSetDesc desc, EventAdapterService eventAdapterService, VariableService variableService)
            throws ExprValidationException
    {
        this.desc = desc;
        this.eventAdapterService = eventAdapterService;
        this.variableService = variableService;

        Map<String, Class> variableTypes = new HashMap<String, Class>();
        readers = new VariableReader[desc.getAssignments().size()];
        mustCoerce = new boolean[desc.getAssignments().size()];

        int count = 0;
        for (OnTriggerSetAssignment assignment : desc.getAssignments())
        {
            String variableName = assignment.getVariableName();
            readers[count] = variableService.getReader(variableName);
            if (readers[count] == null)
            {
                throw new ExprValidationException("Variables by name '" + variableName + "' has not been created or configured");
            }

            // determine types
            Class variableType = readers[count].getType();
            Class expressionType = assignment.getExpression().getType();
            variableTypes.put(variableName, variableType);

            // determine if the expression type can be assigned
            if ((JavaClassHelper.getBoxedType(expressionType) != variableType) &&
                (expressionType != null))
            {
                if ((!JavaClassHelper.isNumeric(variableType)) ||
                    (!JavaClassHelper.isNumeric(expressionType)))
                {
                    throw new ExprValidationException("Variables '" + variableName
                        + "' of declared type '" + variableType.getName() +
                            "' cannot be assigned a value of type '" + expressionType.getName() + "'");
                }

                if (!(JavaClassHelper.canCoerce(expressionType, variableType)))
                {
                    throw new ExprValidationException("Variables '" + variableName
                        + "' of declared type '" + variableType.getName() +
                            "' cannot be assigned a value of type '" + expressionType.getName() + "'");
                }

                mustCoerce[count] = true;
            }
            count++;
        }
        eventType = eventAdapterService.createAnonymousMapType(variableTypes);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if ((newData == null) || (newData.length == 0))
        {
            return;
        }

        Map<String, Object> values = null;
        if (this.hasViews())
        {
            values = new HashMap<String, Object>();
        }

        eventsPerStream[0] = newData[newData.length - 1];
        int count = 0;

        // We obtain a write lock global to the variable space
        // Since expressions can contain variables themselves, these need to be unchangeable for the duration
        // as there could be multiple statements that do "var1 = var1 + 1".
        variableService.getReadWriteLock().writeLock().lock();
        try
        {
            variableService.setLocalVersion();

            for (OnTriggerSetAssignment assignment : desc.getAssignments())
            {
                VariableReader reader = readers[count];
                Object value = assignment.getExpression().evaluate(eventsPerStream, true);
                if ((value != null) && (mustCoerce[count]))
                {
                    value = JavaClassHelper.coerceBoxed((Number) value, reader.getType());
                }

                variableService.write(reader.getVariableNumber(), value);
                count++;

                if (values != null)
                {
                    values.put(assignment.getVariableName(), value);
                }
            }

            variableService.commit();
        }
        catch (Throwable t)
        {
            variableService.rollback();
        }
        finally
        {
            variableService.getReadWriteLock().writeLock().unlock();            
        }

        if (values != null)
        {
            EventBean newDataOut[] = new EventBean[1];
            newDataOut[0] = eventAdapterService.createMapFromValues(values, eventType);
            this.updateChildren(newDataOut, null);
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        Map<String, Object> values = new HashMap<String, Object>();

        int count = 0;
        for (OnTriggerSetAssignment assignment : desc.getAssignments())
        {
            Object value = readers[count].getValue();
            values.put(assignment.getVariableName(), value);
            count++;
        }

        EventBean event = eventAdapterService.createMapFromValues(values, eventType);
        return new SingleEventIterator(event);
    }
}
