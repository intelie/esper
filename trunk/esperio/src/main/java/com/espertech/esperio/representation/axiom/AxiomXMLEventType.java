package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.EPException;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.TypedEventPropertyGetter;
import com.espertech.esper.event.xml.SimpleXMLPropertyParser;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.jaxen.JaxenException;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Apache Axiom event type provides event metadata for Axiom OMDocument events.
 * <p>
 * Optimistic try to resolve the property string into an appropiate xPath, and
 * use it as getter. Mapped and Indexed properties supported. Because no type
 * information is given, all property are resolved to String. No namespace
 * support. Cannot access to xml attributes, only elements content.
 * <p>
 * See {@link AxiomEventRepresentation} for more details.
 */
public class AxiomXMLEventType implements EventType
{
    private String defaultNamespacePrefix;
    private ConfigurationEventTypeAxiom config;
    private AxiomXPathNamespaceContext namespaceContext;
    private Map<String, TypedEventPropertyGetter> propertyGetterCache;

    /**
     * Ctor.
     * @param configurationEventTypeAxiom is the configuration for XML access
     */
    public AxiomXMLEventType(ConfigurationEventTypeAxiom configurationEventTypeAxiom)
    {
        this.config = configurationEventTypeAxiom;
        this.propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();

        // Set up a namespace context for XPath expressions
        namespaceContext = new AxiomXPathNamespaceContext();
        for (Map.Entry<String, String> entry : configurationEventTypeAxiom.getNamespacePrefixes().entrySet())
        {
            namespaceContext.addPrefix(entry.getKey(), entry.getValue());
        }

        // add namespaces
        if (configurationEventTypeAxiom.getDefaultNamespace() != null)
        {
            String defaultNamespace = configurationEventTypeAxiom.getDefaultNamespace();
            namespaceContext.setDefaultNamespace(defaultNamespace);

            // determine a default namespace prefix to use to construct XPath
            // expressions from pure property names
            defaultNamespacePrefix = null;
            for (Map.Entry<String, String> entry : configurationEventTypeAxiom.getNamespacePrefixes().entrySet())
            {
                if (entry.getValue().equals(defaultNamespace))
                {
                    defaultNamespacePrefix = entry.getKey();
                    break;
                }
            }
        }

        // determine XPath properties that are predefined
        String xpathExpression = null;
        try {
            for (ConfigurationEventTypeAxiom.XPathPropertyDesc property : config.getXPathProperties().values())
            {
                TypedEventPropertyGetter getter = resolvePropertyGetter(property.getName(), property.getXpath(), property.getType(), property.getOptionalCastToType());
                propertyGetterCache.put(property.getName(), getter);
            }
        }
        catch (XPathExpressionException ex)
        {
            throw new EPException("XPath expression could not be compiled for expression '" + xpathExpression + '\'', ex);
        }
    }

    public Class getPropertyType(String property) {
		TypedEventPropertyGetter getter = propertyGetterCache.get(property);
		if (getter != null)
			return getter.getResultClass();
		return String.class;    // all other types are assumed to exist and be of type String
	}

	public Class getUnderlyingType() {
		return OMNode.class;
	}

	public EventPropertyGetter getGetter(String property) {
		EventPropertyGetter getter = propertyGetterCache.get(property);
		if (getter != null)
			return getter;
        try
        {
            return resolveDynamicProperty(property);
        }
        catch (XPathExpressionException e)
        {
            return null;
        }
    }

	public String[] getPropertyNames() {
		Set<String> properties = propertyGetterCache.keySet();
		return properties.toArray(new String[properties.size()]);
	}

	public boolean isProperty(String property) {
		return (getGetter(property) != null);
	}

    public EventType[] getSuperTypes() {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes() {
        return null;
    }

    /**
     * Returns the configuration for the alias.
     * @return configuration details underlying the type
     */
    public ConfigurationEventTypeAxiom getConfig()
    {
        return config;
    }

    private TypedEventPropertyGetter resolveDynamicProperty(String property) throws XPathExpressionException
    {
        // not defined, come up with an XPath
        String xPathExpr = SimpleXMLPropertyParser.parse(property, config.getRootElementName(), defaultNamespacePrefix, config.isResolvePropertiesAbsolute());
        return resolvePropertyGetter(property, xPathExpr, XPathConstants.STRING, null);
    }

    private TypedEventPropertyGetter resolvePropertyGetter(String propertyName, String xPathExpr, QName type, Class optionalCastToType) throws XPathExpressionException
    {
        AXIOMXPath axXPath;
        try
        {
            axXPath = new AXIOMXPath(xPathExpr);
        }
        catch (JaxenException e)
        {
            throw new EPException("Error constructing XPath expression from property name '" + propertyName + '\'', e);
        }

        axXPath.setNamespaceContext(namespaceContext);
        return new AxiomXPathPropertyGetter(propertyName, axXPath, type, optionalCastToType);
    }
}
