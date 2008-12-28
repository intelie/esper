package com.espertech.esper.event.xml.getter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

public class DOMSimpleAttributeGetter implements EventPropertyGetter, DOMPropertyGetter
{
    private final String propertyName;

    public DOMSimpleAttributeGetter(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public Object getValueAsFragment(Node node)
    {
        return null;
    }

    public Node[] getValueAsNodeArray(Node node)
    {
        return null;
    }

    public Node getValueAsNode(Node node)
    {
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); i++)
        {
            Node attrNode = namedNodeMap.item(i);
            if (attrNode.getLocalName().equals(propertyName))
            {
                return attrNode;
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
        return null;  // Never a fragment
    }
}