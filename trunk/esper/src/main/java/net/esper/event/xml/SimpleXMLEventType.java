package net.esper.event.xml;


import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathExpression;

import net.esper.event.EventPropertyGetter;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.EPException;

import java.util.Map;
import java.util.HashMap;

/**
 * Optimistic try to resolve the property string into an appropiate xPath,
 * and use it as getter.
 * Mapped and Indexed properties supported.
 * Because no type information is given, all property are resolved to String.
 * No namespace support.
 * Cannot access to xml attributes, only elements content.
 * 
 * If an xsd is present, then use {@link net.esper.event.xml.SchemaXMLEventType SchemaXMLEventType }
 * 
 * @author pablo
 *
 */
public class SimpleXMLEventType extends BaseXMLEventType {

    private Map<String, TypedEventPropertyGetter> propertyGetterCache;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM configures the event type
     */
    public SimpleXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        super(configurationEventTypeXMLDOM);
        super.setExplicitProperties(configurationEventTypeXMLDOM.getXPathProperties().values());
        propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();
    }

    protected Class doResolvePropertyType(String property) {
        return String.class;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        TypedEventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null) {
            return getter;
        }

        XPathExpression xPathExpression = null;
        try
        {
            xPathExpression = SimpleXMLPropertyParser.parse(property,getXPathFactory(),getRootElementName());
        }
        catch (XPathExpressionException e)
        {
            throw new EPException("Error constructing XPath expression from property name '" + property + '\'', e);
        }

        getter = new XPathPropertyGetter(property, xPathExpression, XPathConstants.STRING);
        propertyGetterCache.put(property, getter);
        return getter;
    }
}
