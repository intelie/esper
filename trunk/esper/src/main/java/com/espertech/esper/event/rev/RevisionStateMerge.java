package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.NullableObject;

/**
 * State for merge stratgies.
 */
public class RevisionStateMerge
{
    private EventBean baseEventUnderlying;
    private NullableObject<Object>[] overlays;
    private RevisionEventBeanMerge lastEvent;

    /**
     * Ctor.
     * @param baseEventUnderlying base event
     * @param overlays merged values
     * @param lastEvent last event
     */
    public RevisionStateMerge(EventBean baseEventUnderlying, NullableObject<Object>[] overlays, RevisionEventBeanMerge lastEvent)
    {
        this.baseEventUnderlying = baseEventUnderlying;
        this.overlays = overlays;
        this.lastEvent = lastEvent;
    }

    /**
     * Set merged values.
     * @param overlays values
     */
    public void setOverlays(NullableObject<Object>[] overlays)
    {
        this.overlays = overlays;
    }

    /**
     * Returns base event.
     * @return base event
     */
    public EventBean getBaseEventUnderlying()
    {
        return baseEventUnderlying;
    }

    /**
     * Sets base event.
     * @param baseEventUnderlying to set
     */
    public void setBaseEventUnderlying(EventBean baseEventUnderlying)
    {
        this.baseEventUnderlying = baseEventUnderlying;
    }

    /**
     * Returns merged values.
     * @return merged values
     */
    public NullableObject<Object>[] getOverlays()
    {
        return overlays;
    }

    /**
     * Returns the last event.
     * @return last event
     */
    public RevisionEventBeanMerge getLastEvent()
    {
        return lastEvent;
    }

    /**
     * Sets the last event.
     * @param lastEvent to set
     */
    public void setLastEvent(RevisionEventBeanMerge lastEvent)
    {
        this.lastEvent = lastEvent;
    }
}
