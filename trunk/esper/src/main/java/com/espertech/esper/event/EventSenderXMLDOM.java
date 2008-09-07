/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.event.xml.BaseXMLEventType;
import com.espertech.esper.event.xml.XMLEventBean;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Event sender for XML DOM-backed events.
 * <p>
 * Allows sending only event objects of type Node or Document, does check the root name of the XML document
 * which must match the event type root name as configured. Any other event object generates an error.
 */
public class EventSenderXMLDOM implements EventSender
{
    private final EPRuntimeEventSender runtimeEventSender;
    private final BaseXMLEventType baseXMLEventType;

    /**
     * Ctor.
     * @param runtimeEventSender for processing events
     * @param baseXMLEventType the event type
     */
    public EventSenderXMLDOM(EPRuntimeEventSender runtimeEventSender, BaseXMLEventType baseXMLEventType)
    {
        this.runtimeEventSender = runtimeEventSender;
        this.baseXMLEventType = baseXMLEventType;
    }

    public void sendEvent(Object node) throws EPException
    {
        Node namedNode;
        if (node instanceof Document)
        {
            namedNode = ((Document) node).getDocumentElement();
        }
        else if (node instanceof Element)
        {
            namedNode = (Element) node;
        }
        else
        {
            throw new EPException("Unexpected event object type '" + node.getClass().getName() + "' encountered, please supply a org.w3c.dom.Document or Element node");
        }

        String rootElementName = namedNode.getLocalName();
        if (rootElementName == null)
        {
            rootElementName = namedNode.getNodeName();
        }

        if (!rootElementName.equals(baseXMLEventType.getRootElementName()))
        {
            throw new EPException("Unexpected root element name '" + rootElementName + "' encountered, expected a root element name of '" + baseXMLEventType.getRootElementName() + "'");
        }

        EventBean event = new XMLEventBean(namedNode, baseXMLEventType);
        runtimeEventSender.processWrappedEvent(event);
    }
}
