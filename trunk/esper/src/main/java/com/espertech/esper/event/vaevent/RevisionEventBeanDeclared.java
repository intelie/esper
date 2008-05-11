package com.espertech.esper.event.vaevent;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

/**
 * Revision event bean for the overlayed scheme.
 */
public class RevisionEventBeanDeclared implements EventBean
{
    private final RevisionEventType revisionEventType;
    private final EventBean underlyingFullOrDelta;

    private MultiKeyUntyped key;
    private EventBean lastBaseEvent;
    private RevisionBeanHolder[] holders;
    private boolean isLatest;

    /**
     * Ctor.
     * @param eventType revision event type
     * @param underlying event wrapped
     */
    public RevisionEventBeanDeclared(RevisionEventType eventType, EventBean underlying)
    {
        this.revisionEventType = eventType;
        this.underlyingFullOrDelta = underlying;
    }

    /**
     * Returns true if latest event, or false if not.
     * @return indicator if latest
     */
    public boolean isLatest()
    {
        return isLatest;
    }

    /**
     * Set flag to indicate there is a later event.
     * @param latest flag to set
     */
    public void setLatest(boolean latest)
    {
        isLatest = latest;
    }

    /**
     * Sets the key value.
     * @param key value
     */
    public void setKey(MultiKeyUntyped key)
    {
        this.key = key;
    }

    /**
     * Sets the last base event.
     * @param lastBaseEvent base event
     */
    public void setLastBaseEvent(EventBean lastBaseEvent)
    {
        this.lastBaseEvent = lastBaseEvent;
    }

    /**
     * Sets versions.
     * @param holders versions
     */
    public void setHolders(RevisionBeanHolder[] holders)
    {
        this.holders = holders;
    }

    /**
     * Returns last base event.
     * @return base event
     */
    public EventBean getLastBaseEvent()
    {
        return lastBaseEvent;
    }

    /**
     * Returns wrapped event.
     * @return wrapped event
     */
    public EventBean getUnderlyingFullOrDelta()
    {
        return underlyingFullOrDelta;
    }

    /**
     * Returns the key.
     * @return key
     */
    public MultiKeyUntyped getKey()
    {
        return key;
    }

    /**
     * Returns the revision event type.
     * @return type
     */
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
        return RevisionEventBeanDeclared.class;
    }

    /**
     * Returns a versioned value.
     * @param params getter parameters
     * @return value
     */
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
            return params.getBaseGetter().get(lastBaseEvent);
        }

        return holderMostRecent.getValueForProperty(params.getPropertyNumber());
    }
}
