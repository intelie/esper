package com.espertech.esperio.representation.axiom;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.jaxen.JaxenException;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.event.TypedEventPropertyGetter;

/**
 * Implementation of a property getter for the Axiom XML data model.
 * <p>
 * See {@link AxiomEventRepresentation} for more details.
 */
public class AxiomXPathPropertyGetter implements TypedEventPropertyGetter
{
    private final AXIOMXPath expression;
    private final String property;
    private final QName resultType;

    /**
     * Ctor.
     * @param propertyName    is the name of the event property for which this getter gets values
     * @param resultType      is the resulting type
     * @param xPath           the Axiom xpath expression 
     */
    public AxiomXPathPropertyGetter(String propertyName, AXIOMXPath xPath, QName resultType)
    {
        this.expression = xPath;
        this.property = propertyName;
        this.resultType = resultType;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object und = eventBean.getUnderlying();
        if (und == null)
        {
            throw new PropertyAccessException(
                    "Unexpected null underlying event encountered, expecting org.w3c.dom.Node instance as underlying");
        }
        if (!(und instanceof OMNode))
        {
            throw new PropertyAccessException(
                    "Unexpected underlying event of type '"
                            + und.getClass()
                            + "' encountered, expecting org.w3c.dom.Node as underlying");
        }
        try
        {
            if (resultType.equals(XPathConstants.BOOLEAN))
            {
                return expression.booleanValueOf(und);
            }
            else if (resultType.equals(XPathConstants.NUMBER))
            {
                Number n = expression.numberValueOf(und);
                return n.doubleValue();
            }
            else
            {
                String result = expression.stringValueOf(und);
                return result;
            }
        }
        catch (JaxenException e)
        {
            throw new PropertyAccessException("Error getting property '"+ property + "' : " + e.getMessage(), e);
        }
    }

    public Class getResultClass()
    {
        if (resultType.equals(XPathConstants.BOOLEAN))
        {
            return Boolean.class;
        }
        if (resultType.equals(XPathConstants.NUMBER))
        {
            return Double.class;
        }
        if (resultType.equals(XPathConstants.STRING))
        {
            return String.class;
        }

        return String.class;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property always exists as the property is not dynamic
    }
}
