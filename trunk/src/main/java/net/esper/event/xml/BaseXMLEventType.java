package net.esper.event.xml;

import javax.xml.xpath.*;

import org.w3c.dom.Node;

import net.esper.event.*;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.EPException;

import java.util.*;

/**
 * Base class for XMLEventTypes.
 * Using this class as EventType only allow preconfigured properties 
 * (normally via {@link net.esper.event.xml.XPathPropertyGetter XPathPropertyGetter} ).
 * 
 * For "on the fly" property resolvers, use either
 * {@link net.esper.event.xml.SimpleXMLEventType SimpleXMLEventType} or
 * {@link net.esper.event.xml.SchemaXMLEventType SchemaXMLEventType}
 *
 * @author pablo
 */
public class BaseXMLEventType extends BaseConfigurableEventType {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final XPathFactory xPathFactory;
    private final String rootElementName;
    private XPathNamespaceContext namespaceContext;

    public BaseXMLEventType(String rootElementName)
    {
        super(Node.class);
        this.rootElementName = rootElementName;
        xPathFactory = XPathFactory.newInstance();
    }

    protected String getRootElementName()
    {
        return rootElementName;
    }

    protected void setNamespaceContext(XPathNamespaceContext namespaceContext)
    {
        this.namespaceContext = namespaceContext;
    }

    protected void setExplicitProperties(Collection<ConfigurationEventTypeXMLDOM.PropertyDesc> explicitProperties)
    {
        // Convert explicit properties to XPath expressions
        Map<String, TypedEventPropertyGetter> getters = new HashMap<String, TypedEventPropertyGetter>();

        String xpathExpression = null;
        try {

            for (ConfigurationEventTypeXMLDOM.PropertyDesc property : explicitProperties)
            {
                XPath xPath = xPathFactory.newXPath();
                if (namespaceContext != null)
                {
                    xPath.setNamespaceContext(namespaceContext);
                }

                xpathExpression = property.getXpath();
                XPathExpression expression = xPath.compile(xpathExpression);
                getters.put(property.getName(), new XPathPropertyGetter(property.getName(), expression, property.getType()));
            }
        }
        catch (XPathExpressionException ex)
        {
            throw new EPException("XPath expression could not be compiled for expression '" + xpathExpression + "'");
        }

        setExplicitProperties(getters);
    }

    protected XPathFactory getXPathFactory() {
        return xPathFactory;
    }

    public EventType[] getSuperTypes() {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return null;
    }

    public EventBean newEvent(Object event) {
        return new XMLEventBean((Node)event,this);
    }

    protected String[] doListPropertyNames() {
        return EMPTY_STRING_ARRAY;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        return null;
    }

    protected Class doResolvePropertyType(String property) {
        return null;
    }
}
