package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeEventSender;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;

public class AxionEventSender implements EventSender
{
    private final AxiomXMLEventType eventType;
    private final EPRuntimeEventSender runtimeEventSender;

    public AxionEventSender(AxiomXMLEventType eventType, EPRuntimeEventSender runtimeEventSender)
    {
        this.eventType = eventType;
        this.runtimeEventSender = runtimeEventSender;
    }

    public void sendEvent(Object node)
    {
        OMElement namedNode;
        if (node instanceof OMDocument)
        {
            namedNode = ((OMDocument) node).getOMDocumentElement();
        }
        else if (node instanceof OMElement)
        {
            namedNode = (OMElement)node;
        }
        else
        {
            throw new EPException("Unexpected AXIOM node of type '" + node.getClass() + "' encountered, please supply a Document or Element node");
        }

        String rootElementNameRequired = eventType.getConfig().getRootElementName();
        String rootElementNameFound = namedNode.getLocalName();
        if (!rootElementNameFound.equals(rootElementNameRequired))
        {
            throw new EPException("Unexpected root element name '" + rootElementNameFound + "' encountered, expected '" + rootElementNameRequired + "'");
        }

        runtimeEventSender.processWrappedEvent(new AxiomEventBean(namedNode, eventType));
    }
}
