package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;

public interface RouteResultViewHandler
{
    public void handle(EventBean event);
}
