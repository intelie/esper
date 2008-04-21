package com.espertech.esperio.representation.axiom;

import com.espertech.esper.event.xml.XPathNamespaceContext;

import org.jaxen.NamespaceContext;

/**
 * XPathContext for Jaxen compatibility.
 * <p>
 * See {@link AxiomEventRepresentation} for more details.
 */
public class AxiomXPathNamespaceContext extends XPathNamespaceContext implements NamespaceContext
{	
	public String translateNamespacePrefixToUri(String prefix) {
		return this.getNamespaceURI(prefix);
	}
}

