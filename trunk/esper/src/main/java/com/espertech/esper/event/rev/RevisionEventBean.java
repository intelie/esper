package com.espertech.esper.event.rev;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

public class RevisionEventBean implements EventBean
{
    private final RevisionEventType revisionEventType;
    private final EventBean underlyingFullOrDelta;

    private MultiKeyUntyped key;
    private EventBean fullEvent;
    private RevisionBeanHolder[] holders;
    private boolean isLatest;

    public RevisionEventBean(RevisionEventType eventType, EventBean underlying)
    {
        this.revisionEventType = eventType;
        this.underlyingFullOrDelta = underlying;
    }

    public RevisionEventBean(RevisionEventType eventType, MultiKeyUntyped key, EventBean fullEvent, RevisionBeanHolder[] revisionsPerAuthoritySet)
    {
        this.revisionEventType = eventType;
        this.key = key;
        this.fullEvent = fullEvent;
        this.holders = revisionsPerAuthoritySet;
        this.underlyingFullOrDelta = null;
    }

    public boolean isLatest()
    {
        return isLatest;
    }

    public void setLatest(boolean latest)
    {
        isLatest = latest;
    }

    public void setKey(MultiKeyUntyped key)
    {
        this.key = key;
    }

    public void setFullEvent(EventBean fullEvent)
    {
        this.fullEvent = fullEvent;
    }

    public void setHolders(RevisionBeanHolder[] holders)
    {
        this.holders = holders;
    }

    public EventBean getUnderlyingFullOrDelta()
    {
        return underlyingFullOrDelta;
    }

    public MultiKeyUntyped getKey()
    {
        return key;
    }

    public RevisionEventType getRevisionEventType()
    {
        return revisionEventType;
    }

    public EventType getEventType()
    {
        return revisionEventType;
    }

    public Object get(String property) throws PropertyAccessException
    {
        EventPropertyGetter getter = revisionEventType.getGetter(property);
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

    public Object getVersionFreeValue(int index)
    {
        return key.getKeys()[index];
    }

    public Object getVersionedValue(RevisionGetterParameters params)
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
