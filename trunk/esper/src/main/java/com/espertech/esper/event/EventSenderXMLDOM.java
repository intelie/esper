package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.event.xml.BaseXMLEventType;
import com.espertech.esper.event.xml.XMLEventBean;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EventSenderXMLDOM implements EventSender
{
    private final EPRuntimeEventSender runtimeEventSender;
    private final BaseXMLEventType baseXMLEventType;

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
