package net.esper.event.xml.schema;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import com.sun.org.apache.xerces.internal.xs.XSModel;

import net.esper.event.EventPropertyGetter;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.event.xml.BaseXMLEventType;

/**
 * EventType for xml events that have a Schema.
 * Mapped and Indexed properties are supported.
 * All property types resolved via the declared xsd types.
 * Can access attributes.
 * Validates the property string at construction time. 
 * 
 * 
 * @author pablo
 *
 */
public class SchemaXMLEventType extends BaseXMLEventType {

	private XSModel xsModel; 
	
	//root Element of the xml
	private String eventName;

	//namespace of the root Element
	private String namespace; 
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public void setNamespace(String rootElementNamespace) {
		this.namespace = rootElementNamespace;
		
	}
	
	/**
	 * 
	 * @param xsModel schema to use.
	 */
	public void setXsModel(XSModel xsModel) {
		this.xsModel = xsModel;
	}

	@Override
	protected Class doResolvePropertyType(String property) {
		TypedEventPropertyGetter getter = (TypedEventPropertyGetter) doResolvePropertyGetter(property);
		if (getter != null)
			return getter.getResultClass();
		return null;
	}

	@Override
	protected EventPropertyGetter doResolvePropertyGetter(String property) {
		try {
			return  SchemaXMLPropertyParser.parse(property,getXPathFactory(),eventName,namespace,xsModel);
		} catch (XPathExpressionException e) {
			// TODO log error
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String[] doListPropertyNames() {
		// TODO wath to list here?
		return null;
	}


}
