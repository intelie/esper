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
import java.net.URISyntaxException;
import java.net.URL;

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

    // schema model
    private XSModel xsModel;

    // namespace of the root Element
    private String namespace;

    public SchemaXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        super(configurationEventTypeXMLDOM.getRootElementName());

        // Load schema
        String schemaURI = configurationEventTypeXMLDOM.getSchemaURI();
        try
        {
            readSchema(schemaURI);
        }
        catch (EPException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new EPException("Failed to read schema '" + schemaURI + "'", ex);
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
        super.setExplicitProperties(configurationEventTypeXMLDOM.getProperties().values());
    }

    private void readSchema(String schemaURI) throws IllegalAccessException, InstantiationException, ClassNotFoundException,
            EPException, URISyntaxException
    {
        // Locate via Classloader
        URL url = ClassLoader.getSystemResource(schemaURI);
        if (url == null)
        {
            throw new EPException("Failed to locate schema using ClassLoader.getSystemResource for URI '" + schemaURI + "'");
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
            throw new EPException("Failed to read schema via URI '" + schemaURI + "'");
        }
    }

    protected Class doResolvePropertyType(String property) {
        TypedEventPropertyGetter getter = (TypedEventPropertyGetter) doResolvePropertyGetter(property);
        if (getter != null)
            return getter.getResultClass();
        return null;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        try
        {
            return SchemaXMLPropertyParser.parse(property,getXPathFactory(),getRootElementName(),namespace,xsModel);
        }
        catch (XPathExpressionException e) {
            throw new EPException("Error constructing XPath expression from property name '" + property + "'", e);
        }
    }

    protected String[] doListPropertyNames() {
        return null;
    }
}
