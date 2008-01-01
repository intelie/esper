package net.esper.event.property;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.event.BeanEventTypeFactory;
import net.esper.event.BeanEventType;
import net.esper.event.BeanEventAdapter;

public class TestPropertyParser extends TestCase
{
    private BeanEventTypeFactory beanEventTypeFactory;

    public void setUp()
    {
        beanEventTypeFactory = new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>());
    }

    public void testParse() throws Exception
    {
        Property property = PropertyParser.parse("a", beanEventTypeFactory);
        assertEquals("a", ((SimpleProperty)property).getPropertyName());

        property = PropertyParser.parse("i[1]", beanEventTypeFactory);
        assertEquals("i", ((IndexedProperty)property).getPropertyName());
        assertEquals(1, ((IndexedProperty)property).getIndex());

        property = PropertyParser.parse("m('key')", beanEventTypeFactory);
        assertEquals("m", ((MappedProperty)property).getPropertyName());
        assertEquals("key", ((MappedProperty)property).getKey());

        property = PropertyParser.parse("a.b[2].c('m')", beanEventTypeFactory);
        List<Property> nested = ((NestedProperty)property).getProperties();
        assertEquals(3, nested.size());
        assertEquals("a", ((SimpleProperty)nested.get(0)).getPropertyName());
        assertEquals("b", ((IndexedProperty)nested.get(1)).getPropertyName());
        assertEquals(2, ((IndexedProperty)nested.get(1)).getIndex());
        assertEquals("c", ((MappedProperty)nested.get(2)).getPropertyName());
        assertEquals("m", ((MappedProperty)nested.get(2)).getKey());
    }

    public void testParseMapKey() throws Exception
    {
        assertEquals("a", tryKey("a"));
    }

    private String tryKey(String key) throws Exception
    {
        String propertyName = "m(\"" + key + "\")";
        log.debug(".tryKey propertyName=" + propertyName + " key=" + key);
        Property property = PropertyParser.parse(propertyName, beanEventTypeFactory);
        return ((MappedProperty)property).getKey();
    }

    private static Log log = LogFactory.getLog(TestPropertyParser.class);
}
