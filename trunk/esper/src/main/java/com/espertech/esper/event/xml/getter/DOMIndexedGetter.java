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
import com.espertech.esper.event.xml.FragmentFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMIndexedGetter implements EventPropertyGetter, DOMPropertyGetter
{
    private final String propertyName;
    private final int index;
    private final FragmentFactory fragmentFactory;

    public DOMIndexedGetter(String propertyMap, int index, FragmentFactory fragmentFactory)
    {
        this.propertyName = propertyMap;
        this.index = index;
        this.fragmentFactory = fragmentFactory;
    }

    public Node[] getValueAsNodeArray(Node node)
    {
        return null;
    }

    public Object getValueAsFragment(Node node)
    {
        if (fragmentFactory == null)
        {
            return null;
        }
        Node result = getValueAsNode(node);
        if (result == null)
        {
            return null;
        }
        return fragmentFactory.getEvent(result);
    }

    public Node getValueAsNode(Node node)
    {
        NodeList list = node.getChildNodes();
        int count = 0;
        for (int i = 0; i < list.getLength(); i++)
        {
            Node childNode = list.item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE)
            {
                continue;
            }
            if (!(childNode.getLocalName().equals(propertyName)))
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
        Object result = eventBean.getUnderlying();
        if (!(result instanceof Node))
        {
            return null;
        }
        Node node = (Node) result;
        return getValueAsFragment(node);
    }
}