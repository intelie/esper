package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.EPException;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.plugin.PlugInEventBeanFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNode;

import java.util.Map;
import java.net.URI;

/**
 * A event bean factory implementation that understands Apache Axiom OMNode events
 * and that can look up the root element name to determine the right event type.
 * <p>
 * See {@link AxiomEventRepresentation} for more details.
 */
public class AxionEventBeanFactory implements PlugInEventBeanFactory
{
    private final Map<String, AxiomXMLEventType> types;

    /**
     * Ctor.
     * @param types the currently known event type aliases and their types
     */
    public AxionEventBeanFactory(Map<String, AxiomXMLEventType> types)
    {
        this.types = types;
    }

    public EventBean create(Object node, URI resolutionURI)
    {
        // Check event type - only handle the Axiom types of OMDocument and OMElement 
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
            return null;    // not the right event type, return null and let others handle it, or ignore
        }

        // Look up the root element name and map to a known event type
        String rootElementName = namedNode.getLocalName();
        EventType eventType = types.get(rootElementName);
        if (eventType == null)
        {
            return null;    // not a root element name, let others handle it
        }

        return new AxiomEventBean(namedNode, eventType);
    }
}
