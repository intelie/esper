package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.EPException;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;

import java.util.Map;

public class AxionEventBeanFactory
{
    private final Map<String, EventType> types;

    public AxionEventBeanFactory(Map<String, EventType> types)
    {
        this.types = types;
    }

    public EventBean adapterForAXIOM(OMNode node) {
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

        String rootElementName = namedNode.getLocalName();
        EventType eventType = types.get(rootElementName);
        if (eventType == null)
        {
            return null;
        }

        return new AxiomEventBean(namedNode, eventType);
	}
}
