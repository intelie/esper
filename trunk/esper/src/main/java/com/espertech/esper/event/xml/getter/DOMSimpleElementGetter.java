package com.espertech.esper.event.xml.getter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMSimpleElementGetter implements EventPropertyGetter, DOMPropertyGetter
{
    private final String propertyName;

    public DOMSimpleElementGetter(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public Node getValueAsNode(Node node)
    {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            Node childNode = list.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }
            if (childNode.getLocalName().equals(propertyName))
            {
                return childNode;
            }
        }
        return null;
    }

    public Object get(EventBean obj) throws PropertyAccessException
    {
        // The underlying is expected to be a map
        if (!(obj.getUnderlying() instanceof Node))
        {
            throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                    "the underlying data object is not of type Node");
        }

        Node node = (Node) obj.getUnderlying();
        return getValueAsNode(node);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true;
    }

    public Object getFragment(EventBean eventBean) throws PropertyAccessException
    {
        return get(eventBean);
    }
}
