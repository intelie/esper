package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.view.Viewable;

import java.util.Collection;
import java.util.Iterator;

public class VariantRevisionProcessor implements RevisionProcessor
{
    private final String variantEventTypeAlias;
    private final EventType[] variants;
    private final VariantEventType variantEventType;

    public VariantRevisionProcessor(String variantEventTypeAlias, EventType[] eventTypes)
    {
        this.variantEventTypeAlias = variantEventTypeAlias;
        this.variants = eventTypes;
        variantEventType = new VariantEventType(eventTypes, new VariantPropertyResolutionStrategyImpl());
    }

    public EventType getEventType()
    {
        return variantEventType;
    }

    public String getRevisionEventTypeAlias()
    {
        return variantEventTypeAlias;
    }

    public boolean validateRevisionableEventType(EventType eventType)
    {
        for (EventType variant : variants)
        {
            if (variant == eventType)
            {
                // Check all the supertypes to see if one of the matches the full or delta types
                Iterator<EventType> deepSupers = eventType.getDeepSuperTypes();
                if (deepSupers == null)
                {
                    return false;
                }

                EventType type;
                for (;deepSupers.hasNext();)
                {
                    type = deepSupers.next();
                    if (type == eventType)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public EventBean getRevision(EventBean event)
    {
        return new VariantEventBean(variantEventType, event);
    }

    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository)
    {
        throw new UnsupportedOperationException();
    }

    public Collection<EventBean> getSnapshot(EPStatementHandle createWindowStmtHandle, Viewable parent)
    {
        throw new UnsupportedOperationException();
    }

    public void removeOldData(EventBean[] oldData, NamedWindowIndexRepository indexRepository)
    {
        throw new UnsupportedOperationException();
    }
}
