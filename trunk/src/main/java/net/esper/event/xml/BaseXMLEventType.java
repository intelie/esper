package net.esper.event.xml;

import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

import net.esper.event.BaseConfigurableEventType;
import net.esper.event.EventBean;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;

import java.util.Iterator;

/**
 * Base class for XMLEventTypes.
 * Using this class as EventType only allow preconfigured properties 
 * (normally via {@link net.esper.event.xml.XPathPropertyGetter XPathPropertyGetter} ).
 * 
 * For "on the fly" property resolvers, use either
 * {@link net.esper.event.xml.simple.SimpleXMLEventType SimpleXMLEventType} or
 * {@link net.esper.event.xml.schema.SchemaXMLEventType SchemaXMLEventType}
 *  
 * 
 * @author pablo
 *
 */
public class BaseXMLEventType extends BaseConfigurableEventType {
    private XPathFactory xPathFactory;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public BaseXMLEventType() {
        setUnderlyngType(Node.class);
    }


    protected XPathFactory getXPathFactory() {
        return xPathFactory;
    }

    public void setXPathFactory(XPathFactory pathFactory) {
        xPathFactory = pathFactory;
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


    @Override
    protected String[] doListPropertyNames() {
        return EMPTY_STRING_ARRAY;
    }


    @Override
    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected Class doResolvePropertyType(String property) {
        // TODO Auto-generated method stub
        return null;
    }



}
