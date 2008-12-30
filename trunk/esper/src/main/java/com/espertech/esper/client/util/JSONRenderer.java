package com.espertech.esper.client.util;

import com.espertech.esper.client.EventBean;

public interface JSONRenderer
{
    public String render(EventBean event);
}
