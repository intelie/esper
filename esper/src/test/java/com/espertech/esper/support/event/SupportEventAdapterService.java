package com.espertech.esper.support.event;

import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventAdapterServiceImpl;

public class SupportEventAdapterService
{
    private static EventAdapterService eventAdapterService;

    static
    {
        eventAdapterService = new EventAdapterServiceImpl();
    }

    public static void reset()
    {
        eventAdapterService = new EventAdapterServiceImpl();
    }
    public static EventAdapterService getService()
    {
        return eventAdapterService;
    }
}
