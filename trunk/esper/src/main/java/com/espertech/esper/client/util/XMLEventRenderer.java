package com.espertech.esper.client.util;

import com.espertech.esper.client.EventBean;

public interface XMLEventRenderer
{
    public String render(String rootElementName, EventBean event);
}
