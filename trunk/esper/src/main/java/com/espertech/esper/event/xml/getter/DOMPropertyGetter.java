package com.espertech.esper.event.xml.getter;

import org.w3c.dom.Node;
import com.espertech.esper.client.EventPropertyGetter;

public interface DOMPropertyGetter extends EventPropertyGetter
{
    public Node getValueAsNode(Node node);
}
