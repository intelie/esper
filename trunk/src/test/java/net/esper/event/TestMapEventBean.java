package net.esper.event;

import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class TestMapEventBean extends TestCase
{
    private Map<String, Class> testTypesMap;
    private Map<String, Object> testValuesMap;

    private EventType eventType;
    private MapEventBean eventBean;

    public void setUp()
    {
        testTypesMap = new HashMap<String, Class>();
        testTypesMap.put("aString", String.class);
        testTypesMap.put("anInt", Integer.class);

        testValuesMap = new HashMap<String, Object>();
        testValuesMap.put("aString", "test");
        testValuesMap.put("anInt", 10);

        eventType = new MapEventType(testTypesMap);
        eventBean = new MapEventBean(testValuesMap, eventType);
    }

    public void testGet()
    {
        assertEquals(eventType, eventBean.getEventType());
        assertEquals(testValuesMap, eventBean.getUnderlying());

        assertEquals("test", eventBean.get("aString"));
        assertEquals(10, eventBean.get("anInt"));

        // test wrong property name
        try
        {
            eventBean.get("dummy");
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
            log.debug(".testGetter Expected exception, msg=" + ex.getMessage());
        }
    }

    public void testEquals()
    {
        MapEventBean other = new MapEventBean(testValuesMap, eventType);
        assertTrue(eventBean.equals(other));

        testValuesMap.put("aString", "xxx");
        other = new MapEventBean(testValuesMap, eventType);
        assertFalse(eventBean.equals(other));
        assertFalse(other.equals(eventBean));

        testValuesMap.put("aString", "test");
        other = new MapEventBean(testValuesMap, eventType);
        assertTrue(eventBean.equals(other));

        // try another event type
        EventType otherEventType = SupportEventAdapterService.getService().createAnonymousMapType(testTypesMap);
        other = new MapEventBean(testValuesMap, otherEventType);
        assertFalse(other.equals(eventBean));
        assertFalse(eventBean.equals(other));

        // Try same event type but differint object references
        testValuesMap.put("anInt", new Integer(10));
        other = new MapEventBean(testValuesMap, eventType);
        assertTrue(eventBean.equals(other));

        // try null values
        MapEventBean beanOne = new MapEventBean(testValuesMap, eventType);
        HashMap<String, Object> beanTwoValues = new HashMap<String, Object>();
        beanTwoValues.put("aString", null);
        beanTwoValues.put("anInt", null);
        MapEventBean beanTwo = new MapEventBean(beanTwoValues, eventType);
        assertFalse(beanOne.equals(beanTwo));
        assertFalse(beanTwo.equals(beanOne));
    }

    public void testCreateUnderlying()
    {
        SupportBean beanOne = new SupportBean();
        SupportBean_A beanTwo = new SupportBean_A("a");

        EventBean eventOne = SupportEventBeanFactory.createObject(beanOne);
        EventBean eventTwo = SupportEventBeanFactory.createObject(beanTwo);

        // Set up event type
        testTypesMap.clear();
        testTypesMap.put("a", SupportBean.class);
        testTypesMap.put("b", SupportBean_A.class);
        EventType eventType = SupportEventAdapterService.getService().createAnonymousMapType(testTypesMap);

        Map<String, EventBean> events = new HashMap<String, EventBean>();
        events.put("a", eventOne);
        events.put("b", eventTwo);

        MapEventBean event = new MapEventBean(eventType, events);
        assertTrue(event.get("a") == beanOne);
        assertTrue(event.get("b") == beanTwo);
    }

    public void testHash()
    {
        // Check the normal non-null values
        assertEquals("test".hashCode() ^
                    (new Integer(10)).hashCode() ^
                    "aString".hashCode() ^
                    "anInt".hashCode(), eventBean.hashCode());

        // try out with a null value
        testValuesMap.put("aString", null);
        eventBean = new MapEventBean(testValuesMap, eventType);

        assertEquals((new Integer(10)).hashCode() ^
                    "anInt".hashCode(), eventBean.hashCode());    }

    private static final Log log = LogFactory.getLog(TestMapEventBean.class);
}
