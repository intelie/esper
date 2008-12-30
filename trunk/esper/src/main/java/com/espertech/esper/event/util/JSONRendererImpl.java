package com.espertech.esper.event.util;

import com.espertech.esper.client.util.JSONRenderer;
import com.espertech.esper.client.util.JSONRenderingOptions;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

public class JSONRendererImpl implements JSONRenderer
{
    private RendererMeta meta;

    public JSONRendererImpl(EventType eventType, JSONRenderingOptions options)
    {

    }

    public String render(EventBean event)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
