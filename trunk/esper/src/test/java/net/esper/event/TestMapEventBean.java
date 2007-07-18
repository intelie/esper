package net.esper.event;

import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBeanComplexProps;
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

    private SupportBeanComplexProps supportBean = SupportBeanComplexProps.makeDefaultBean();

    public void setUp()
    {
        testTypesMap = new HashMap<String, Class>();
        testTypesMap.put("aString", String.class);
        testTypesMap.put("anInt", Integer.class);
        testTypesMap.put("myComplexBean", SupportBeanComplexProps.class);

        testValuesMap = new HashMap<String, Object>();
        testValuesMap.put("aString", "test");
        testValuesMap.put("anInt", 10);
        testValuesMap.put("myComplexBean", supportBean);

        eventType = new MapEventType("", testTypesMap, SupportEventAdapterService.getService());
        eventBean = new MapEventBean(testValuesMap, eventType);
    }

    public void testGet()
    {
        assertEquals(eventType, eventBean.getEventType());
        assertEquals(testValuesMap, eventBean.getUnderlying());

        assertEquals("test", eventBean.get("aString"));
        assertEquals(10, eventBean.get("anInt"));

        assertEquals("nestedValue", eventBean.get("myComplexBean.nested.nestedValue"));

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

    private static final Log log = LogFactory.getLog(TestMapEventBean.class);
}
