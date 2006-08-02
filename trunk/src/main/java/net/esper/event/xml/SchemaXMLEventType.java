package net.esper.event.xml;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.sun.org.apache.xerces.internal.xs.XSModel;

import net.esper.event.EventPropertyGetter;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.EPException;

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
        super(configurationEventTypeXMLDOM.getRootNodeName());
        super.setExplicitProperties(configurationEventTypeXMLDOM.getProperties().values());
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
