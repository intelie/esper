package net.esper.view.std;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventAdapterService;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.ContextAwareView;
import net.esper.view.ViewServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This view simply adds a property to the events posted to it. This is useful for the group-merge views.
 */
public final class AddPropertyValueView extends ViewSupport implements ContextAwareView
{
    private ViewServiceContext viewServiceContext;
    private String[] propertyNames;
    private Object[] propertyValues;
    private EventType eventType;
    private boolean mustAddProperty;

    /**
     * Empty constructor - required for Java bean.
     */
    public AddPropertyValueView()
    {
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;
    }

    public void setParent(Viewable parent)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".setParent parent=" + parent);
        }

        super.setParent(parent);

        if (parent == null)
        {
            return;
        }

        // If the parent event type contains the merge fields, we use the same event type
        if (parent.getEventType().isProperty(propertyNames[0]))
        {
            mustAddProperty = false;
            eventType = parent.getEventType();
        }
        // If the parent event type does not contain the event type (generates a map or such like the statistics views)
        // then we need to add in the merge field as an event property thus changing event types.
        else
        {
            mustAddProperty = true;
            Class[] propertyValueTypes = new Class[propertyValues.length];
            for (int i = 0; i < propertyValueTypes.length; i++)
            {
                propertyValueTypes[i] = propertyValues[i].getClass();
            }
            eventType = viewServiceContext.getEventAdapterService().createAddToEventType(
                    parent.getEventType(), propertyNames, propertyValueTypes);
        }
    }

    /**
     * Constructor.
     * @param fieldNames is the name of the field that is added to any events received by this view.
     * @param mergeValues is the values of the field that is added to any events received by this view.
     */
    public AddPropertyValueView(String[] fieldNames, Object[] mergeValues)
    {
        this.propertyNames = fieldNames;
        this.propertyValues = mergeValues;
    }

    public final String attachesTo(Viewable object)
    {
        // Attaches to all views
        return null;
    }

    /**
     * Returns field name for which to set the merge value for.
     * @return field name to use to set value
     */
    public final String[] getPropertyNames()
    {
        return propertyNames;
    }

    /**
     * Sets the field name for which to set the merge value for.
     * @param propertyNames to set
     */
    public final void setPropertyNames(String[] propertyNames)
    {
        this.propertyNames = propertyNames;
    }

    /**
     * Returns the value to set for the field.
     * @return value to set
     */
    public final Object[] getPropertyValues()
    {
        return propertyValues;
    }

    /**
     * Sets the value for the field to merge into the events coming into this view.
     * @param propertyValue is the value to merge in
     */
    public final void setPropertyValues(Object[] propertyValue)
    {
        this.propertyValues = propertyValue;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (!mustAddProperty)
        {
            updateChildren(newData, oldData);
            return;
        }

        EventBean[] newEvents = null;
        EventBean[] oldEvents = null;

        if (newData != null)
        {
            newEvents = new EventBean[newData.length];

            int index = 0;
            for (EventBean newEvent : newData)
            {
                EventBean event = addProperty(newEvent, propertyNames, propertyValues, eventType, viewServiceContext.getEventAdapterService());
                newEvents[index++] = event;
            }
        }

        if (oldData != null)
        {
            oldEvents = new EventBean[oldData.length];

            int index = 0;
            for (EventBean oldEvent : oldData)
            {
                EventBean event = addProperty(oldEvent, propertyNames, propertyValues, eventType, viewServiceContext.getEventAdapterService());
                oldEvents[index++] = event;
            }
        }

        updateChildren(newEvents, oldEvents);
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        final Iterator<EventBean> parentIterator = parent.iterator();

        return new Iterator<EventBean>()
        {
            public boolean hasNext()
            {
                return parentIterator.hasNext();
            }

            public EventBean next()
            {
                EventBean nextEvent = parentIterator.next();
                if (mustAddProperty)
                {
                    EventBean event = addProperty(nextEvent, propertyNames, propertyValues, eventType,
                            viewServiceContext.getEventAdapterService());
                    return event;
                }
                else
                {
                    return nextEvent;
                }
            }

            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Add a property to the event passed in.
     * @param originalEvent - event to add property to
     * @param propertyNames - names of properties to add
     * @param propertyValues - value of properties to add
     * @param targetEventType - new event type
     * @param eventAdapterService - service for generating events and handling event types
     * @return event with added property
     */
    protected static EventBean addProperty(EventBean originalEvent,
                                       String[] propertyNames,
                                       Object[] propertyValues,
                                       EventType targetEventType,
                                       EventAdapterService eventAdapterService)
    {
        Map<String, Object> values = new HashMap<String, Object>();

        // Copy properties of original event, add property value
        for (String property : originalEvent.getEventType().getPropertyNames())
        {
            values.put(property, originalEvent.get(property));
        }

        for (int i = 0; i < propertyNames.length; i++)
        {
            values.put(propertyNames[i], propertyValues[i]);
        }

        return eventAdapterService.createMapFromValues(values, targetEventType);
    }

    public final String toString()
    {
        return this.getClass().getName() + " propertyNames=" + Arrays.toString(propertyNames) +
                " propertyValue=" + Arrays.toString(propertyValues);
    }

    private static final Log log = LogFactory.getLog(AddPropertyValueView.class);
}
