package net.esper.client;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.HashMap;

public class XMLDOMEventTypeDesc
{
    private String nodeName;
    private String optSchemaURI;
    private Map<String, PropertyDesc> properties;

    public XMLDOMEventTypeDesc()
    {
        properties = new HashMap<String, PropertyDesc>();
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public void setSchemaURI(String schemaURI)
    {
        this.optSchemaURI = schemaURI;
    }

    public String getNodeName()
    {
        return nodeName;
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
        PropertyDesc desc = new PropertyDesc(xpath, type);
        properties.put(name, desc);
    }

    public class PropertyDesc
    {
        private String xpath;
        private QName type;

        public PropertyDesc(String xpath, QName type)
        {
            this.xpath = xpath;
            this.type = type;
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
