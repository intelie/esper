package com.espertech.esper.client.util;

import com.espertech.esper.client.EventBean;

public interface JSONEventRenderer
{
    public String render(String title, EventBean event);
}
