package com.espertech.esper.client.util;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.util.JSONRendererImpl;

public class JSONRendererFactory
{
    public static JSONRenderer getRenderer(EventType eventType, JSONRenderingOptions options)
    {
        return new JSONRendererImpl(eventType, options);
    }

    public static JSONRenderer getRenderer(EventType eventType)
    {
        return new JSONRendererImpl(eventType, new JSONRenderingOptions());
    }

    public static String render(EventBean event)
    {
        if (event == null)
        {
            return null;
        }
        return getRenderer(event.getEventType()).render(event);        
    }
}
