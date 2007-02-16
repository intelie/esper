package net.esper.view.window;

import net.esper.event.EventBean;

import java.util.Map;
import java.util.HashMap;

public class RelativeAccessByEventNIndexGetter implements IStreamRelativeAccess.UpdateObserver
{
    private final Map<EventBean, IStreamRelativeAccess> accessorByEvent = new HashMap<EventBean, IStreamRelativeAccess>();
    private final Map<IStreamRelativeAccess, EventBean[]> eventsByAccessor  = new HashMap<IStreamRelativeAccess, EventBean[]>();

    public void updated(IStreamRelativeAccess iStreamRelativeAccess, EventBean[] newData)
    {
        // remove data posted from the last update
        EventBean[] lastNewData = eventsByAccessor.get(iStreamRelativeAccess);
        if (lastNewData != null)
        {
            for (int i = 0; i < lastNewData.length; i++)
            {
                accessorByEvent.remove(lastNewData[i]);
            }
        }

        if (newData == null)
        {
            return;
        }

        // hold accessor per event for querying
        for (int i = 0; i < newData.length; i++)
        {
            accessorByEvent.put(newData[i], iStreamRelativeAccess);
        }

        // save new data for access to later removal
        eventsByAccessor.put(iStreamRelativeAccess, newData);
    }

    public IStreamRelativeAccess getAccessor(EventBean event)
    {
        IStreamRelativeAccess iStreamRelativeAccess = accessorByEvent.get(event);
        if (iStreamRelativeAccess == null)
        {
            throw new IllegalStateException("Accessor for window random access not found for event " + event);
        }
        return iStreamRelativeAccess;
    }
}