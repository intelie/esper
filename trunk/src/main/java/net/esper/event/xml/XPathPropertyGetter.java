package net.esper.event.xml;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import net.esper.event.EventBean;
import net.esper.event.TypedEventPropertyGetter;

import net.esper.event.PropertyAccessException;

import org.w3c.dom.Node;



/**
 * Getter for properties of DOM xml events.
 * 
 * 
 * @author pablo
 *
 */
public class XPathPropertyGetter implements TypedEventPropertyGetter {
	XPathExpression expression;
	String property;
	QName resultType;
	
	public XPathPropertyGetter(String propertyName, XPathExpression xPathExpression, QName resultType) throws XPathExpressionException {
		this.expression = xPathExpression;
		this.property = propertyName;
		this.resultType = resultType;
	}

	public Object get(EventBean eventBean) throws PropertyAccessException {
		Object und = eventBean.getUnderlying();
		if (!(und instanceof Node))
			throw new PropertyAccessException("XPathPropertyGetter only usable on org.w3c.dom.Node underlyns events");
		try {
			return expression.evaluate(und,resultType);
		} catch (XPathExpressionException e) {
			throw new PropertyAccessException("Error getting property " + property,e);
		}
	}

	public QName getResultType() {
		return resultType;
	}

	public Class getResultClass() {
		if (resultType == XPathConstants.BOOLEAN)
			return Boolean.class;
		if (resultType == XPathConstants.NUMBER)
			return Double.class;
		if (resultType == XPathConstants.STRING)
			return String.class;
		
		return String.class; //TODO
		}
		
		

}
