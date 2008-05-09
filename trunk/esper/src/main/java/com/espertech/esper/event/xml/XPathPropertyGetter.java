package com.espertech.esper.event.xml;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import com.espertech.esper.event.EventBean;

import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.event.TypedEventPropertyGetter;
import com.espertech.esper.util.SimpleTypeParser;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.SimpleTypeParserFactory;

import org.w3c.dom.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Getter for properties of DOM xml events.
 * 
 * @author pablo
 */
public class XPathPropertyGetter implements TypedEventPropertyGetter {
    private static final Log log = LogFactory.getLog(XPathPropertyGetter.class);
	private final XPathExpression expression;
	private final String property;
	private final QName resultType;
    private final SimpleTypeParser simpleTypeParser;
    private final Class optionalCastToType;

    /**
     * Ctor.
     * @param propertyName is the name of the event property for which this getter gets values
     * @param xPathExpression is a compile XPath expression
     * @param resultType is the resulting type
     * @param optionalCastToType if non-null then the return value of the xpath expression is cast to this value
     */
    public XPathPropertyGetter(String propertyName, XPathExpression xPathExpression, QName resultType, Class optionalCastToType) {
		this.expression = xPathExpression;
		this.property = propertyName;
		this.resultType = resultType;
        if (optionalCastToType != null)
        {
            simpleTypeParser = SimpleTypeParserFactory.getParser(optionalCastToType);
        }
        else
        {
            simpleTypeParser = null;
        }
        this.optionalCastToType = optionalCastToType;
    }

	public Object get(EventBean eventBean) throws PropertyAccessException {
		Object und = eventBean.getUnderlying();
        if (und == null)
        {
            throw new PropertyAccessException("Unexpected null underlying event encountered, expecting org.w3c.dom.Node instance as underlying");
        }
        if (!(und instanceof Node))
        {
            throw new PropertyAccessException("Unexpected underlying event of type '" + und.getClass() + "' encountered, expecting org.w3c.dom.Node as underlying");
        }
        try {
            // if there is no parser, return xpath expression type
            if (optionalCastToType == null)
            {
                return expression.evaluate(und,resultType);
            }

            // obtain result
            Object result = expression.evaluate(und,resultType);
            if (result == null)
            {
                return null;
            }

            // string results get parsed
            if (result instanceof String)
            {
                try
                {
                    return simpleTypeParser.parse(result.toString());
                }
                catch (RuntimeException ex)
                {
                    log.warn("Error parsing XPath property named '" + property + "' expression result '" + result + " as type " + optionalCastToType.getName());
                    return null;
                }
            }

            // coercion
            if (result instanceof Double)
            {
                try
                {
                    return JavaClassHelper.coerceBoxed((Number)result, optionalCastToType);
                }
                catch (RuntimeException ex)
                {
                    log.warn("Error coercing XPath property named '" + property + "' expression result '" + result + " as type " + optionalCastToType.getName());
                    return null;
                }
            }

            // check boolean type
            if (result instanceof Boolean)
            {
                if (optionalCastToType != Boolean.class)
                {
                    log.warn("Error coercing XPath property named '" + property + "' expression result '" + result + " as type " + optionalCastToType.getName());
                    return null;
                }
            }

            log.warn("Error processing XPath property named '" + property + "' expression result '" + result + ", not a known type");
            return null;
        }
        catch (XPathExpressionException e) {
			throw new PropertyAccessException("Error getting property " + property,e);
		}
	}

	public Class getResultClass() {
        if (optionalCastToType != null)
        {
            return optionalCastToType;
        }

        if (resultType.equals(XPathConstants.BOOLEAN))
			return Boolean.class;
		if (resultType.equals(XPathConstants.NUMBER))
			return Double.class;
		if (resultType.equals(XPathConstants.STRING))
			return String.class;
		
		return String.class;
	}

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }    
}
