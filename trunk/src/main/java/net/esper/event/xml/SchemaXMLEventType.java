package net.esper.event.xml;

import javax.xml.xpath.XPathExpressionException;

import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSImplementation;
import com.sun.org.apache.xerces.internal.xs.XSLoader;
import com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl;

import net.esper.event.EventPropertyGetter;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.EPException;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import java.util.Map;
import java.util.HashMap;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * EventType for xml events that have a Schema.
 * Mapped and Indexed properties are supported.
 * All property types resolved via the declared xsd types.
 * Can access attributes.
 * Validates the property string at construction time. 
 * @author pablo
 *
 */
public class SchemaXMLEventType extends BaseXMLEventType {

    // schema model
    private XSModel xsModel;

    // namespace of the root Element
    private String namespace;

    private Map<String, TypedEventPropertyGetter> propertyGetterCache;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM - configuration for type
     */
    public SchemaXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        super(configurationEventTypeXMLDOM.getRootElementName());
        propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();

        // Load schema
        String schemaURL = configurationEventTypeXMLDOM.getSchemaURL();
        try
        {
            readSchema(schemaURL);
        }
        catch (EPException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new EPException("Failed to read schema '" + schemaURL + "'", ex);
        }

        // Set up namespace context
        namespace = configurationEventTypeXMLDOM.getRootElementNamespace();
        XPathNamespaceContext ctx = new XPathNamespaceContext();
        if (configurationEventTypeXMLDOM.getRootElementNamespace() != null)
        {
            ctx.setDefaultNamespace(configurationEventTypeXMLDOM.getRootElementNamespace());
        }
        for (Map.Entry<String, String> entry : configurationEventTypeXMLDOM.getNamespacePrefixes().entrySet())
        {
            ctx.addPrefix(entry.getKey(), entry.getValue());
        }
        super.setNamespaceContext(ctx);

        // Finally add XPath properties as that may depend on the namespace
        super.setExplicitProperties(configurationEventTypeXMLDOM.getXPathProperties().values());
    }

    private void readSchema(String schemaURL) throws IllegalAccessException, InstantiationException, ClassNotFoundException,
            EPException, URISyntaxException
    {
        URL url = null;

        try
        {
            url = new URL(schemaURL);
        }
        catch (MalformedURLException ex)
        {
            throw new EPException("Malformed URL encountered for schema URL '" + schemaURL + "'", ex);
        }
        String uri = url.toURI().toString();

        // Uses Xerxes internal classes
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        registry.addSource(new DOMXSImplementationSourceImpl());
        XSImplementation impl =(XSImplementation) registry.getDOMImplementation("XS-Loader");
        XSLoader schemaLoader = impl.createXSLoader(null);
        xsModel = schemaLoader.loadURI(uri);

        if (xsModel == null)
        {
            throw new EPException("Failed to read schema via URL '" + schemaURL + "'");
        }
    }

    protected Class doResolvePropertyType(String property) {
        TypedEventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null){
            return getter.getResultClass();
        }

        getter = (TypedEventPropertyGetter) doResolvePropertyGetter(property);
        if (getter != null) {
            return getter.getResultClass();
        }
        return null;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        TypedEventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null) {
            return getter;
        }

        try
        {
            getter = SchemaXMLPropertyParser.parse(property,getXPathFactory(),getRootElementName(),namespace,xsModel);
            propertyGetterCache.put(property, getter);
            return getter;
        }
        catch (XPathExpressionException e) {
            throw new EPException("Error constructing XPath expression from property name '" + property + "'", e);
        }
    }

    protected String[] doListPropertyNames() {
        return null;
    }
}
