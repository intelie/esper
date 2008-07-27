package com.espertech.esper.event.property;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.event.BeanEventTypeFactory;
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.event.BeanEventAdapter;

public class TestPropertyParser extends TestCase
{
    private BeanEventTypeFactory beanEventTypeFactory;

    public void setUp()
    {
        beanEventTypeFactory = new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>());
    }

    public void testParse() throws Exception
    {
        Property property = PropertyParser.parse("a", beanEventTypeFactory, false);
        assertEquals("a", ((SimpleProperty)property).getPropertyNameAtomic());

        property = PropertyParser.parse("i[1]", beanEventTypeFactory, false);
        assertEquals("i", ((IndexedProperty)property).getPropertyNameAtomic());
        assertEquals(1, ((IndexedProperty)property).getIndex());

        property = PropertyParser.parse("m('key')", beanEventTypeFactory, false);
        assertEquals("m", ((MappedProperty)property).getPropertyNameAtomic());
        assertEquals("key", ((MappedProperty)property).getKey());

        property = PropertyParser.parse("a.b[2].c('m')", beanEventTypeFactory, false);
        List<Property> nested = ((NestedProperty)property).getProperties();
        assertEquals(3, nested.size());
        assertEquals("a", ((SimpleProperty)nested.get(0)).getPropertyNameAtomic());
        assertEquals("b", ((IndexedProperty)nested.get(1)).getPropertyNameAtomic());
        assertEquals(2, ((IndexedProperty)nested.get(1)).getIndex());
        assertEquals("c", ((MappedProperty)nested.get(2)).getPropertyNameAtomic());
        assertEquals("m", ((MappedProperty)nested.get(2)).getKey());

        property = PropertyParser.parse("a", beanEventTypeFactory, true);
        assertEquals("a", ((DynamicSimpleProperty)property).getPropertyNameAtomic());
    }

    public void testParseMapKey() throws Exception
    {
        assertEquals("a", tryKey("a"));
    }

    private String tryKey(String key) throws Exception
    {
        String propertyName = "m(\"" + key + "\")";
        log.debug(".tryKey propertyName=" + propertyName + " key=" + key);
        Property property = PropertyParser.parse(propertyName, beanEventTypeFactory, false);
        return ((MappedProperty)property).getKey();
    }

    private static Log log = LogFactory.getLog(TestPropertyParser.class);
}
