package com.espertech.esper.epl.variable;

import com.espertech.esper.collection.SingleEventIterator;
import com.espertech.esper.core.StatementResultService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * View for handling create-variable syntax.
 * <p>
 * The view posts to listeners when a variable changes, if it has subviews.
 * <p>
 * The view returns the current variable value for the iterator.
 * <p>
 * The event type for such posted events is a single field Map with the variable value.
 */
public class CreateVariableView extends ViewSupport implements VariableChangeCallback
{
    private static final Log log = LogFactory.getLog(CreateVariableView.class);
    private final EventAdapterService eventAdapterService;
    private final VariableReader reader;
    private final EventType eventType;
    private final String variableName;
    private final StatementResultService statementResultService;

    /**
     * Ctor.
     * @param eventAdapterService for creating events
     * @param variableService for looking up variables
     * @param variableName is the name of the variable to create
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     */
    public CreateVariableView(EventAdapterService eventAdapterService, VariableService variableService, String variableName, StatementResultService statementResultService)
    {
        this.eventAdapterService = eventAdapterService;
        this.variableName = variableName;
        this.statementResultService = statementResultService;
        reader = variableService.getReader(variableName);

        Map<String, Object> variableTypes = new HashMap<String, Object>();
        variableTypes.put(variableName, reader.getType());
        eventType = eventAdapterService.createAnonymousMapType(variableTypes);
    }

    public void update(Object newValue, Object oldValue)
    {
        if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic())
        {
            Map<String, Object> valuesOld = new HashMap<String, Object>();
            valuesOld.put(variableName, oldValue);
            EventBean eventOld = eventAdapterService.createMapFromValues(valuesOld, eventType);

            Map<String, Object> valuesNew = new HashMap<String, Object>();
            valuesNew.put(variableName, newValue);
            EventBean eventNew = eventAdapterService.createMapFromValues(valuesNew, eventType);

            this.updateChildren(new EventBean[] {eventNew}, new EventBean[] {eventOld});
        }
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        throw new UnsupportedOperationException("Update not supported");
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        Object value = reader.getValue();
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(variableName, value);
        EventBean event = eventAdapterService.createMapFromValues(values, eventType);
        return new SingleEventIterator(event);
    }
}
