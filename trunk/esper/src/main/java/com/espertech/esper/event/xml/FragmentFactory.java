package com.espertech.esper.event.xml;

import com.espertech.esper.client.EventBean;
import org.w3c.dom.Node;

public interface FragmentFactory
{
    public EventBean getEvent(Node result);
}
