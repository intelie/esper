package net.esper.support.event;

import net.esper.event.EventAdapterService;
import net.esper.event.EventAdapterServiceImpl;

public class SupportEventAdapterService
{
    private static EventAdapterService eventAdapterService;

    static
    {
        eventAdapterService = new EventAdapterServiceImpl();
    }

    public static EventAdapterService getService()
    {
        return eventAdapterService;
    }
}
