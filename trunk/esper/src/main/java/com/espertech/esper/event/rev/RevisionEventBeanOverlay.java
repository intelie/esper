package com.espertech.esper.event.rev;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.util.NullableObject;

public class RevisionEventBeanOverlay implements EventBean
{
    private final RevisionEventType revisionEventType;
    private final EventBean underlyingFullOrDelta;

    private NullableObject<Object>[] overlay;
    private EventBean lastFullEvent;
    private MultiKeyUntyped key;
    private boolean latest;

    public RevisionEventBeanOverlay(RevisionEventType revisionEventType, EventBean underlyingFull)
    {
        this.revisionEventType = revisionEventType;
        this.underlyingFullOrDelta = underlyingFull;
    }

    public void setOverlay(NullableObject<Object>[] overlay)
    {
        this.overlay = overlay;
    }

    public boolean isLatest()
    {
        return latest;
    }

    public void setLatest(boolean latest)
    {
        this.latest = latest;
    }

    public MultiKeyUntyped getKey()
    {
        return key;
    }

    public void setKey(MultiKeyUntyped key)
    {
        this.key = key;
    }

    public Object[] getOverlay()
    {
        return overlay;
    }

    public EventBean getLastFullEvent()
    {
        return lastFullEvent;
    }

    public void setLastFullEvent(EventBean lastFullEvent)
    {
        this.lastFullEvent = lastFullEvent;
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
        return RevisionEventBeanOverlay.class;
    }

    public EventBean getUnderlyingFullOrDelta()
    {
        return underlyingFullOrDelta;
    }

    public Object getKeyValue(int index)
    {
        return key.getKeys()[index];
    }

    public Object getFullEventValue(RevisionGetterParameters params)
    {
        return params.getFullGetter().get(lastFullEvent);
    }

    public Object getVersionedValue(RevisionGetterParameters params)
    {
        int propertyNumber = params.getPropertyNumber();

        if (overlay != null)
        {
            NullableObject<Object> value = overlay[propertyNumber];
            if (value != null)
            {
                return value.getObject();
            }
        }

        return params.getFullGetter().get(lastFullEvent);
    }
}
