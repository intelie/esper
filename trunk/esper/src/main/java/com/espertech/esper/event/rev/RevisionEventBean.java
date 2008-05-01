package com.espertech.esper.event.rev;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

public class RevisionEventBean implements EventBean
{
    private final RevisionEventType eventType;
    private final MultiKeyUntyped key;
    private final EventBean fullEvent;
    private final RevisionBeanHolder[] holders;

    public RevisionEventBean(RevisionEventType eventType, MultiKeyUntyped key, EventBean fullEvent, RevisionBeanHolder[] revisionsPerAuthoritySet)
    {
        this.eventType = eventType;
        this.key = key;
        this.fullEvent = fullEvent;
        this.holders = revisionsPerAuthoritySet;
    }

    public MultiKeyUntyped getKey()
    {
        return key;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Object get(String property) throws PropertyAccessException
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter == null)
        {
            return null;
        }
        return getter.get(this);
    }

    public Object getUnderlying()
    {
        return null;  // TODO
    }

    public Object getValue(RevisionGetterParameters params)
    {
        RevisionBeanHolder holderMostRecent = null;
        
        if (holders != null)
        {
            for (int numSet : params.getPropertyGroups())
            {
                RevisionBeanHolder holder = holders[numSet];
                if (holder != null)
                {
                    if (holderMostRecent == null)
                    {
                        holderMostRecent = holder;
                    }
                    else
                    {
                        if (holder.getVersion() > holderMostRecent.getVersion())
                        {
                            holderMostRecent = holder;
                        }
                    }
                }
            }
        }

        // none found, use last full event
        if (holderMostRecent == null)
        {
            return params.getFullGetter().get(fullEvent);
        }

        return holderMostRecent.getValueForProperty(params.getPropertyNumber());
    }
}
