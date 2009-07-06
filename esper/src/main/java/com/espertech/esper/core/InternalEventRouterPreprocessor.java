package com.espertech.esper.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventBeanCopyMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Interface for a service that routes events within the engine for further processing.
 */
public class InternalEventRouterPreprocessor
{
    private static final Log log = LogFactory.getLog(InternalEventRouterPreprocessor.class);
    private static final Comparator<InternalEventRouterEntry> comparator = new Comparator<InternalEventRouterEntry>()
    {
        public int compare(InternalEventRouterEntry o1, InternalEventRouterEntry o2)
        {
            if (o1.getPriority() > o2.getPriority())
            {
                return 1;
            }
            else if (o1.getPriority() < o2.getPriority())
            {
                return -1;
            }
            else if (o1.isDrop())
            {
                return -1;
            }
            else if (o2.isDrop())
            {
                return -1;
            }
            return 0;
        }
    };

    private final EventBeanCopyMethod copyMethod;
    private final List<InternalEventRouterEntry> entries;

    public InternalEventRouterPreprocessor(EventBeanCopyMethod copyMethod, List<InternalEventRouterEntry> entries)
    {
        this.copyMethod = copyMethod;
        this.entries = entries;
        Collections.sort(entries, comparator);
    }

    public EventBean process(EventBean event)
    {
        boolean haveCloned = false;
        EventBean[] eventsPerStream = new EventBean[1];
        eventsPerStream[0] = event;

        for (InternalEventRouterEntry entry : entries)
        {
            ExprNode whereClause = entry.getOptionalWhereClause();
            if (whereClause != null)
            {
                Boolean result = (Boolean) whereClause.evaluate(eventsPerStream, true);
                if ((result == null) || (!result))
                {
                    continue;
                }
            }

            if (entry.isDrop())
            {
                return null;
            }

            if (!haveCloned)
            {
                event = copyMethod.copy(event);
                if (event == null)
                {
                    log.warn("Event of type " + event.getEventType().getName() + " could not be copied");
                    return null;
                }
                haveCloned = true;
                eventsPerStream[0] = event;
            }
            
            apply(event, eventsPerStream, entry);
        }
        
        return event;
    }

    private void apply(EventBean event, EventBean[] eventsPerStream, InternalEventRouterEntry entry)
    {
        // evaluate
        Object[] values = new Object[entry.getAssignments().length];
        for (int i = 0; i < entry.getAssignments().length; i++)
        {
            Object value = entry.getAssignments()[i].evaluate(eventsPerStream, true);

            if ((value != null) && (entry.getWideners()[i] != null))
            {
                value = entry.getWideners()[i].widen(value);
            }

            values[i] = value;
        }

        // apply
        entry.getWriter().write(values, event);
    }
}
