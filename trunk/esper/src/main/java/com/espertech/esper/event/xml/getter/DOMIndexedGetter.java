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

public class DOMIndexedGetter implements EventPropertyGetter, DOMPropertyGetter
{
    private final String propertyName;
    private final int index;

    public DOMIndexedGetter(String propertyMap, int index)
    {
        this.propertyName = propertyMap;
        this.index = index;
    }

    public Node getValueAsNode(Node node)
    {
        NodeList list = node.getChildNodes();
        int count = 0;
        for (int i = 0; i < list.getLength(); i++)
        {
            Node childNode = list.item(i);
            if (childNode.getNodeType() != 1)
            {
                continue;
            }
            if (!(childNode.getNodeName().equals(propertyName)))
            {
                continue;
            }

            if (count == index)
            {
                return childNode;
            }
            count++;
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

        return getValueAsNode(node) != null;
    }

    public Object getFragment(EventBean eventBean)
    {
        return null;     // TODO
    }
}