package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;

public interface RouteResultViewHandler
{
    public boolean handle(EventBean event);
}
