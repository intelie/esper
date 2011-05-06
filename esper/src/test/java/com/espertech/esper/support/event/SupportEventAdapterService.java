package com.espertech.esper.support.event;

import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventAdapterServiceImpl;
import com.espertech.esper.event.EventTypeIdGeneratorImpl;

public class SupportEventAdapterService
{
    private static EventAdapterService eventAdapterService;

    static
    {
        eventAdapterService = new EventAdapterServiceImpl(new EventTypeIdGeneratorImpl());
    }

    public static void reset()
    {
        eventAdapterService = new EventAdapterServiceImpl(new EventTypeIdGeneratorImpl());
    }
    public static EventAdapterService getService()
    {
        return eventAdapterService;
    }
}
