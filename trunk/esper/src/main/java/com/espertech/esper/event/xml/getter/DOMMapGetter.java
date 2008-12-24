/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml.getter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMMapGetter implements EventPropertyGetter, DOMPropertyGetter
{
    private final String propertyMap;
    private final String mapKey;

    public DOMMapGetter(String propertyMap, String mapKey)
    {
        this.propertyMap = propertyMap;
        this.mapKey = mapKey;
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
            if (!(childNode.getLocalName().equals(propertyMap)))
            {
                continue;
            }

            Node attribute = childNode.getAttributes().getNamedItem("id");
            if (attribute == null)
            {
                continue;
            }
            if (!(attribute.getTextContent().equals(mapKey)))
            {
                continue;
            }

            return childNode;
        }
        return null;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object result = eventBean.getUnderlying();
        if (!(result instanceof Node))
        {
            return null;
        }
        Node node = (Node) result;

        return getValueAsNode(node);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        Object result = eventBean.getUnderlying();
        if (!(result instanceof Node))
        {
            return false;
        }
        Node node = (Node) result;

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            Node childNode = list.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }
            if (!(childNode.getLocalName().equals(propertyMap)))
            {
                continue;
            }

            Node attribute = childNode.getAttributes().getNamedItem(mapKey);
            if (attribute == null)
            {
                continue;
            }
            if (!(attribute.getTextContent().equals(mapKey)))
            {
                continue;
            }

            return true;
        }
        return false;
    }

    public Object getFragment(EventBean eventBean)
    {
        return null;
    }
}