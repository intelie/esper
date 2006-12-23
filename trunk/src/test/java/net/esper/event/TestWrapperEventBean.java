package net.esper.event;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBeanCombinedProps;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.event.SupportEventAdapterService;

public class TestWrapperEventBean extends TestCase 
{
	private EventBean eventBeanSimple;
	private EventBean eventBeanCombined;
	private Map<String, Object> properties;
	private EventType eventTypeSimple;
	private EventType eventTypeCombined;
	private EventAdapterService eventService;
	
	protected void setUp()
	{
		eventService = SupportEventAdapterService.getService();
		EventType underlyingEventTypeSimple = eventService.addBeanType("underlyingSimpleBean", SupportBeanSimple.class);
		EventType underlyingEventTypeCombined = eventService.addBeanType("underlyingCombinedBean", SupportBeanCombinedProps.class);
		
		Map<String, Class> typeMap = new HashMap<String, Class>();
		typeMap.put("string", String.class);
		typeMap.put("int", Integer.class);
		
		eventTypeSimple = new WrapperEventType(underlyingEventTypeSimple, typeMap, eventService);
		eventTypeCombined = new WrapperEventType(underlyingEventTypeCombined, typeMap, eventService);
		properties = new HashMap<String, Object>();
		properties.put("string", "xx");
		properties.put("int", 11);
		
		eventBeanSimple = eventService.createWrapper(new SupportBeanSimple("eventString", 0), properties, eventTypeSimple);
		eventBeanCombined = eventService.createWrapper(SupportBeanCombinedProps.makeDefaultBean(), properties, eventTypeCombined);
	}
	
	public void testGetSimple()
	{	
		assertEquals("eventString", eventBeanSimple.get("myString"));
		assertEquals(0, eventBeanSimple.get("myInt"));
		assertMap(eventBeanSimple);
	}
	
	public void testGetCombined()
	{
        assertEquals("0ma0", eventBeanCombined.get("indexed[0].mapped('0ma').value"));
        assertEquals("0ma1", eventBeanCombined.get("indexed[0].mapped('0mb').value"));
        assertEquals("1ma0", eventBeanCombined.get("indexed[1].mapped('1ma').value"));
        assertEquals("1ma1", eventBeanCombined.get("indexed[1].mapped('1mb').value"));

        assertEquals("0ma0", eventBeanCombined.get("array[0].mapped('0ma').value"));
        assertEquals("1ma1", eventBeanCombined.get("array[1].mapped('1mb').value"));
        
		assertMap(eventBeanCombined);
	}
	
	private void assertMap(EventBean eventBean)
	{
		assertEquals("xx", eventBean.get("string"));
		assertEquals(11, eventBean.get("int"));
	}
}
