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
public abstract class BaseXMLEventType extends BaseConfigurableEventType {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final XPathFactory xPathFactory;
    private final String rootElementName;
    private final ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM;

    /**
     * XPath namespace context. 
     */
    protected XPathNamespaceContext namespaceContext;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM is the XML DOM configuration such as root element and schema names
     */
    public BaseXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        super(Node.class);
        this.rootElementName = configurationEventTypeXMLDOM.getRootElementName();
        this.configurationEventTypeXMLDOM = configurationEventTypeXMLDOM;
        xPathFactory = XPathFactory.newInstance();
    }

    /**
     * Returns the name of the root element.
     * @return root element name
     */
    protected String getRootElementName()
    {
        return rootElementName;
    }

    /**
     * Sets the namespace context for use in XPath expression resolution.
     * @param namespaceContext for XPath expressions
     */
    protected void setNamespaceContext(XPathNamespaceContext namespaceContext)
    {
        this.namespaceContext = namespaceContext;
    }

    /**
     * Set the preconfigured event properties resolved by XPath expression.
     * @param explicitProperties are preconfigured event properties
     */
    protected void setExplicitProperties(Collection<ConfigurationEventTypeXMLDOM.XPathPropertyDesc> explicitProperties)
    {
        // Convert explicit properties to XPath expressions
        Map<String, TypedEventPropertyGetter> getters = new HashMap<String, TypedEventPropertyGetter>();

        String xpathExpression = null;
        try {

            for (ConfigurationEventTypeXMLDOM.XPathPropertyDesc property : explicitProperties)
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
            throw new EPException("XPath expression could not be compiled for expression '" + xpathExpression + '\'', ex);
        }

        setExplicitProperties(getters);
    }

    /**
     * Returns the XPath factory used.
     * @return XPath factory
     */
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

    protected String[] doListPropertyNames() {
        return EMPTY_STRING_ARRAY;
    }

    /**
     * Returns the configuration XML for the XML type.
     * @return config XML
     */
    public ConfigurationEventTypeXMLDOM getConfigurationEventTypeXMLDOM()
    {
        return configurationEventTypeXMLDOM;
    }

    public boolean equals(Object otherObj)
    {
        if (!(otherObj instanceof BaseXMLEventType))
        {
            return false;
        }
        BaseXMLEventType other = (BaseXMLEventType) otherObj;
        return (configurationEventTypeXMLDOM.equals(other.configurationEventTypeXMLDOM));
    }

    public int hashCode()
    {
        return configurationEventTypeXMLDOM.hashCode();
    }
}
