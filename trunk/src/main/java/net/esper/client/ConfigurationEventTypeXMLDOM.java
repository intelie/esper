package net.esper.client;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.HashMap;

public class ConfigurationEventTypeXMLDOM
{
    private String rootNodeName;
    private String optSchemaURI;
    private Map<String, PropertyDesc> properties;

    public ConfigurationEventTypeXMLDOM()
    {
        properties = new HashMap<String, PropertyDesc>();
    }

    public void setRootNodeName(String rootNodeName)
    {
        this.rootNodeName = rootNodeName;
    }

    public void setSchemaURI(String schemaURI)
    {
        this.optSchemaURI = schemaURI;
    }

    public String getRootNodeName()
    {
        return rootNodeName;
    }

    public String getOptSchemaURI()
    {
        return optSchemaURI;
    }

    public Map<String, PropertyDesc> getProperties()
    {
        return properties;
    }

    public void addProperty(String name, String xpath, QName type)
    {
        PropertyDesc desc = new PropertyDesc(name, xpath, type);
        properties.put(name, desc);
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
