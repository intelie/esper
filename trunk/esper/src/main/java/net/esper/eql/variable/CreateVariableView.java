package net.esper.eql.variable;

import net.esper.view.ViewSupport;
import net.esper.eql.spec.OnTriggerSetDesc;
import net.esper.eql.spec.OnTriggerSetAssignment;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.collection.SingleEventIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class CreateVariableView extends ViewSupport implements VariableChangeCallback
{
    private static final Log log = LogFactory.getLog(CreateVariableView.class);
    private final EventAdapterService eventAdapterService;
    private final VariableReader reader;
    private final EventType eventType;
    private final String variableName;

    public CreateVariableView(EventAdapterService eventAdapterService, VariableService variableService, String variableName)
            throws ExprValidationException
    {
        this.eventAdapterService = eventAdapterService;
        this.variableName = variableName;
        reader = variableService.getReader(variableName);

        Map<String, Class> variableTypes = new HashMap<String, Class>();
        variableTypes.put(variableName, reader.getType());
        eventType = eventAdapterService.createAnonymousMapType(variableTypes);
    }

    public void update(Object newValue, Object oldValue)
    {
        if (this.hasViews())
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
