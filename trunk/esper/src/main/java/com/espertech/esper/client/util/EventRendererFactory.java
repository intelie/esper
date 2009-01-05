package com.espertech.esper.client.util;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.util.JSONRendererImpl;
import com.espertech.esper.event.util.XMLRendererImpl;

public class EventRendererFactory
{
    public static JSONEventRenderer getJSONRenderer(EventType eventType, JSONRenderingOptions options)
    {
        return new JSONRendererImpl(eventType, options);
    }

    public static JSONEventRenderer getJSONRenderer(EventType eventType)
    {
        return new JSONRendererImpl(eventType, new JSONRenderingOptions());
    }

    public static String renderJSON(String title, EventBean event)
    {
        return renderJSON(title, event, new JSONRenderingOptions());
    }

    public static String renderJSON(String title, EventBean event, JSONRenderingOptions options)
    {
        if (event == null)
        {
            return null;
        }
        return getJSONRenderer(event.getEventType(), options).render(title, event);
    }

    public static XMLEventRenderer getXMLRenderer(EventType eventType, XMLRenderingOptions options)
    {
        return new XMLRendererImpl(eventType, options);
    }

    public static XMLEventRenderer getXMLRenderer(EventType eventType)
    {
        return new XMLRendererImpl(eventType, new XMLRenderingOptions());
    }

    public static String renderXML(String rootElementName, EventBean event)
    {
        return renderXML(rootElementName, event, new XMLRenderingOptions());
    }

    public static String renderXML(String rootElementName, EventBean event, XMLRenderingOptions options)
    {
        if (event == null)
        {
            return null;
        }
        return getXMLRenderer(event.getEventType(), options).render(rootElementName, event);
    }
}
