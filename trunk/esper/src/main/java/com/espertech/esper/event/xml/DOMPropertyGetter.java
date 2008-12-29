package com.espertech.esper.event.xml;

import org.w3c.dom.Node;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventBean;

public interface DOMPropertyGetter extends EventPropertyGetter
{
    public Node getValueAsNode(Node node);
    public Node[] getValueAsNodeArray(Node node);
    public Object getValueAsFragment(Node node);
}
