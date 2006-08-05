package net.esper.event.xml;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import net.esper.event.EventBean;

import net.esper.event.PropertyAccessException;
import net.esper.event.TypedEventPropertyGetter;

import org.w3c.dom.Node;

/**
 * Getter for properties of DOM xml events.
 * 
 * @author pablo
 */
public class XPathPropertyGetter implements TypedEventPropertyGetter {
	XPathExpression expression;
	String property;
	QName resultType;

    /**
     * Ctor.
     * @param propertyName is the name of the event property for which this getter gets values
     * @param xPathExpression is a compile XPath expression
     * @param resultType is the resulting type
     */
    public XPathPropertyGetter(String propertyName, XPathExpression xPathExpression, QName resultType) {
		this.expression = xPathExpression;
		this.property = propertyName;
		this.resultType = resultType;
	}

	public Object get(EventBean eventBean) throws PropertyAccessException {
		Object und = eventBean.getUnderlying();
		if (!(und instanceof Node))
			throw new PropertyAccessException("XPathPropertyGetter only usable on org.w3c.dom.Node underlyns events");
		try {
            Object result = expression.evaluate(und,resultType);
            return result;
		}
        catch (XPathExpressionException e) {
			throw new PropertyAccessException("Error getting property " + property,e);
		}
	}

	public Class getResultClass() {
		if (resultType == XPathConstants.BOOLEAN)
			return Boolean.class;
		if (resultType == XPathConstants.NUMBER)
			return Double.class;
		if (resultType == XPathConstants.STRING)
			return String.class;
		
		return String.class;
	}
}
