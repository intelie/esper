package com.espertech.esper.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.SingleEventIterator;
import com.espertech.esper.event.EventBeanCopyMethod;
import com.espertech.esper.event.NaturalEventBean;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

public class InternalRoutePreprocessView extends ViewSupport
{
    private static final Log log = LogFactory.getLog(InternalRoutePreprocessView.class);
    private final EventType eventType;
    private final Iterator<EventBean> noiter = new SingleEventIterator(null);
    private final StatementResultService statementResultService;

    public InternalRoutePreprocessView(EventType eventType, StatementResultService statementResultService)
    {
        this.eventType = eventType;
        this.statementResultService =  statementResultService;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return noiter;
    }

    public void indicate(EventBean event, boolean isRequiresCopy, EventBeanCopyMethod copyMethod) {

        boolean produceOutputEvents = (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic());

        if (!produceOutputEvents)
        {
            return;
        }

        EventBean copiedEvent = event;
        if (isRequiresCopy)
        {
            copiedEvent = copyMethod.copy(event);
        }

        try
        {
            if (statementResultService.isMakeNatural())
            {
                NaturalEventBean natural = new NaturalEventBean(eventType, new Object[] {event.getUnderlying()}, copiedEvent);
                this.updateChildren(new NaturalEventBean[]{natural}, null);                
            }
            else
            {
                this.updateChildren(new EventBean[]{copiedEvent}, null);
            }
        }
        catch (RuntimeException ex)
        {
            log.error("Unexpected error updating child view: " + ex.getMessage());
        }
    }
}
