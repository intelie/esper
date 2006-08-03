package net.esper.client;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.HashMap;

public class ConfigurationEventTypeXMLDOM
{
    private String rootElementName;

    // Root element namespace.
    // Used to find root element in schema. Useful and required in the case where the root element exists in
    // multiple namespaces.
    private String rootElementNamespace;

    // Default name space.
    // For XPath expression evaluation.
    private String defaultNamespace;
    
    private String schemaURI;
    private Map<String, PropertyDesc> properties;
    private Map<String, String> namespacePrefixes;

    public ConfigurationEventTypeXMLDOM()
    {
        properties = new HashMap<String, PropertyDesc>();
        namespacePrefixes = new HashMap<String, String>();
    }

    public void setRootElementName(String rootElementName)
    {
        this.rootElementName = rootElementName;
    }

    public void setSchemaURI(String schemaURI)
    {
        this.schemaURI = schemaURI;
    }

    public String getRootElementNamespace()
    {
        return rootElementNamespace;
    }

    public void setRootElementNamespace(String rootElementNamespace)
    {
        this.rootElementNamespace = rootElementNamespace;
    }

    public String getDefaultNamespace()
    {
        return defaultNamespace;
    }

    public void setDefaultNamespace(String defaultNamespace)
    {
        this.defaultNamespace = defaultNamespace;
    }

    public String getRootElementName()
    {
        return rootElementName;
    }

    public String getSchemaURI()
    {
        return schemaURI;
    }

    public Map<String, PropertyDesc> getProperties()
    {
        return properties;
    }

    public Map<String, String> getNamespacePrefixes()
    {
        return namespacePrefixes;
    }

    public void addProperty(String name, String xpath, QName type)
    {
        PropertyDesc desc = new PropertyDesc(name, xpath, type);
        properties.put(name, desc);
    }

    public void addNamespacePefix(String prefix, String namespace)
    {
        namespacePrefixes.put(prefix, namespace);
    }

    public class PropertyDesc
    {
        private String name;
        private String xpath;
        private QName type;

        public PropertyDesc(String name, String xpath, QName type)
        {
            this.name = name;
            this.xpath = xpath;
            this.type = type;
        }

        public String getName()
        {
            return name;
        }

        public String getXpath()
        {
            return xpath;
        }

        public QName getType()
        {
            return type;
        }
    }
}
