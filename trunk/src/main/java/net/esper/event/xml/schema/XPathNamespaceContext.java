package net.esper.event.xml.schema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;


public class XPathNamespaceContext implements NamespaceContext {

	//namespace to prefix
	private Map<String,String> namespaces;
	
	//prefix to namespace
	private Map<String,String> prefix;
	
	private String defaultNamespace;
	
	public XPathNamespaceContext() {
		super();
		namespaces = new HashMap<String,String>();
		prefix = new HashMap<String,String>();
		
	}

	public String getNamespaceURI(String prefix) {
		if (prefix == null)
			throw new IllegalArgumentException("prefix can't be null");
		if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
			return defaultNamespace;
		if (prefix.equals(XMLConstants.XML_NS_PREFIX))
			return XMLConstants.XML_NS_URI;
		if (prefix.equals(XMLConstants.XMLNS_ATTRIBUTE))
			return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
		
		String namespace = namespaces.get(prefix);
		if (namespace == null)
			return XMLConstants.NULL_NS_URI;
		
		return namespace;
	}

	public String getPrefix(String namespaceURI) {
		//TODO add predefined constant (see getNamespaceURI())
		return prefix.get(namespaceURI);
	}

	public Iterator getPrefixes(String namespaceURI) {
		throw new RuntimeException("Not Implemented");
	}

	public void setDefaultNamespace(String defaultNamespace) {
		this.defaultNamespace = defaultNamespace;
	}
	
	public void addPrefix(String prefix,String uri) {
		namespaces.put(prefix,uri);
		this.prefix.put(uri,prefix);
	}

}
