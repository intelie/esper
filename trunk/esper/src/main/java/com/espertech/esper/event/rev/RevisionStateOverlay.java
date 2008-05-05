package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.NullableObject;

public class RevisionStateOverlay
{
    private EventBean fullEventUnderlying;
    private NullableObject<Object>[] overlays;
    private RevisionEventBeanOverlay lastEvent;

    public RevisionStateOverlay(EventBean fullEventUnderlying, NullableObject<Object>[] overlays, RevisionEventBeanOverlay lastEvent)
    {
        this.fullEventUnderlying = fullEventUnderlying;
        this.overlays = overlays;
        this.lastEvent = lastEvent;
    }

    public void setOverlays(NullableObject<Object>[] overlays)
    {
        this.overlays = overlays;
    }

    public EventBean getFullEventUnderlying()
    {
        return fullEventUnderlying;
    }

    public void setFullEventUnderlying(EventBean fullEventUnderlying)
    {
        this.fullEventUnderlying = fullEventUnderlying;
    }

    public NullableObject<Object>[] getOverlays()
    {
        return overlays;
    }

    public RevisionEventBeanOverlay getLastEvent()
    {
        return lastEvent;
    }

    public void setLastEvent(RevisionEventBeanOverlay lastEvent)
    {
        this.lastEvent = lastEvent;
    }
}
