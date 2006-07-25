package net.esper.event.xml.simple;


import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;

import net.esper.event.EventBean;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;
import net.esper.event.xml.BaseXMLEventType;
import net.esper.event.xml.XMLEventBean;
import net.esper.event.xml.XPathPropertyGetter;

/**
 * Optimistic try to resolve the property string into an appropiate xPath,
 * and use it as getter.
 * Mapped and Indexed properties supported.
 * Because no type information is given, all property are resolved to String.
 * No namespace support.
 * Cannot access to xml attributes, only elements content.
 * 
 * 
 * If an xsd is present, then use {@link net.esper.event.xml.schema.SchemaXMLEventType SchemaXMLEventType }  
 * 
 * @author pablo
 *
 */
public class SimpleXMLEventType extends BaseXMLEventType {
	private String eventName;
	
	
	@Override
	protected Class doResolvePropertyType(String property) {
		return String.class;
	}

	@Override
	protected EventPropertyGetter doResolvePropertyGetter(String property) {
		try {
			return new XPathPropertyGetter(property,SimpleXMLPropertyParser.parse(property,getXPathFactory(),eventName),XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			//TODO log errors
		}
		return null;
	}

	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
