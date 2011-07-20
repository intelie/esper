/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event.xml;

import com.espertech.esper.client.EventPropertyGetter;
import org.w3c.dom.Node;

/**
 * Shortcut-getter for DOM underlying objects.
 */
public interface DOMPropertyGetter extends EventPropertyGetter
{
    /**
     * Returns a property value as a node.
     * @param node to evaluate
     * @return value node
     */
    public Node getValueAsNode(Node node);

    /**
     * Returns a property value that is indexed as a node array.
     * @param node to evaluate
     * @return nodes
     */
    public Node[] getValueAsNodeArray(Node node);

    /**
     * Returns a property value as a fragment.
     * @param node to evaluate
     * @return fragment
     */
    public Object getValueAsFragment(Node node);
}
