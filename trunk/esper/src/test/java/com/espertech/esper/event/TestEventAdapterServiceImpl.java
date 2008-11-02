package com.espertech.esper.event;

import junit.framework.TestCase;
import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.support.bean.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class TestEventAdapterServiceImpl extends TestCase
{
    private EventAdapterServiceImpl adapterService;

    public void setUp()
    {
        adapterService = new EventAdapterServiceImpl();
    }

    public void testSelfRefEvent()
    {
        EventBean originalBean = adapterService.adapterForBean(new SupportSelfReferenceEvent());
        assertEquals(null, originalBean.get("selfRef.selfRef.selfRef.value"));
    }

    public void testCreateMapType()
    {
        Map<String, Object> testTypesMap;
        testTypesMap = new HashMap<String, Object>();
        testTypesMap.put("key1", String.class);
        EventType eventType = adapterService.createAnonymousMapType(testTypesMap);

        assertEquals(Map.class, eventType.getUnderlyingType());
        assertEquals(1, eventType.getPropertyNames().length);
        assertEquals("key1", eventType.getPropertyNames()[0]);        
    }

    public void testGetType()
    {
        adapterService.addBeanType("NAME", TestEventAdapterServiceImpl.class.getName(), false);

        EventType type = adapterService.getExistsTypeByAlias("NAME");
        assertEquals(TestEventAdapterServiceImpl.class, type.getUnderlyingType());

        EventType typeTwo = adapterService.getExistsTypeByAlias(TestEventAdapterServiceImpl.class.getName());
        assertSame(typeTwo, typeTwo);

        assertNull(adapterService.getExistsTypeByAlias("xx"));
    }

    public void testAddInvalid()
    {
        try
        {
            adapterService.addBeanType("x", "xx", false);
            fail();
        }
        catch (EventAdapterException ex)
        {
            // Expected
        }
    }

    public void testAddMapType()
    {
        Map<String, Class> props = new HashMap<String, Class>();
        props.put("a", Long.class);
        props.put("b", String.class);

        // check result type
        EventType typeOne = adapterService.addMapType("latencyEvent", props, null);
        assertEquals(Long.class, typeOne.getPropertyType("a"));
        assertEquals(String.class, typeOne.getPropertyType("b"));
        assertEquals(2, typeOne.getPropertyNames().length);

        assertSame(typeOne, adapterService.getExistsTypeByAlias("latencyEvent"));

        // add the same type with the same name, should succeed and return the same reference
        EventType typeTwo = adapterService.addMapType("latencyEvent", props, null);
        assertSame(typeOne, typeTwo);

        // add the same name with a different type, should fail
        props.put("b", boolean.class);
        try
        {
            adapterService.addMapType("latencyEvent", props, null);
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testAddWrapperType()
    {
        EventType beanEventType = adapterService.addBeanType("mybean", SupportMarketDataBean.class, true);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("a", Long.class);
        props.put("b", String.class);

        // check result type
        EventType typeOne = adapterService.addWrapperType("latencyEvent", beanEventType, props, false, true);
        assertEquals(Long.class, typeOne.getPropertyType("a"));
        assertEquals(String.class, typeOne.getPropertyType("b"));
        assertEquals(7, typeOne.getPropertyNames().length);

        assertSame(typeOne, adapterService.getExistsTypeByAlias("latencyEvent"));

        // add the same name with a different type, should fail
        props.put("b", boolean.class);
        try
        {
            EventType beanTwoEventType = adapterService.addBeanType("mybean", SupportBean.class, true);
            adapterService.addWrapperType("latencyEvent", beanTwoEventType, props, false, false);
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testAddClassName()
    {
        EventType typeOne = adapterService.addBeanType("latencyEvent", SupportBean.class.getName(), true);
        assertEquals(SupportBean.class, typeOne.getUnderlyingType());

        assertSame(typeOne, adapterService.getExistsTypeByAlias("latencyEvent"));

        EventType typeTwo = adapterService.addBeanType("latencyEvent", SupportBean.class.getName(), false);
        assertSame(typeOne, typeTwo);

        try
        {
            adapterService.addBeanType("latencyEvent", SupportBean_A.class.getName(), true);
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testAddClass()
    {
        EventType typeOne = adapterService.addBeanType("latencyEvent", SupportBean.class, false);
        assertEquals(SupportBean.class, typeOne.getUnderlyingType());

        assertSame(typeOne, adapterService.getExistsTypeByAlias("latencyEvent"));

        EventType typeTwo = adapterService.addBeanType("latencyEvent", SupportBean.class, false);
        assertSame(typeOne, typeTwo);

        try
        {
            adapterService.addBeanType("latencyEvent", SupportBean_A.class.getName(), false);
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testWrap()
    {
        SupportBean bean = new SupportBean();
        EventBean event = adapterService.adapterForBean(bean);
        assertSame(event.getUnderlying(), bean);
    }

    public void testCreateAddToEventType()
    {
        Map<String, Object> schema = new HashMap<String, Object>();
        schema.put("STDDEV", Double.class);
        EventType parentEventType = adapterService.createAnonymousMapType(schema);

        EventType newEventType = adapterService.createAddToEventType(parentEventType, new String[] {"test"}, new Class[] {Integer.class});

        assertTrue(newEventType.isProperty("test"));
        assertEquals(Integer.class, newEventType.getPropertyType("test"));
    }

    public void testAddXMLDOMType() throws Exception
    {
        adapterService.addXMLDOMType("XMLDOMTypeOne", getXMLDOMConfig());
        EventType eventType = adapterService.getExistsTypeByAlias("XMLDOMTypeOne");
        assertEquals(Node.class, eventType.getUnderlyingType());

        assertSame(eventType, adapterService.getExistsTypeByAlias("XMLDOMTypeOne"));
        
        try
        {
            adapterService.addXMLDOMType("a", new ConfigurationEventTypeXMLDOM());
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testAdapterForDOM() throws Exception
    {
        adapterService.addXMLDOMType("XMLDOMTypeOne", getXMLDOMConfig());

        String xml =
                "<simpleEvent>\n" +
                "  <nested1>value</nested1>\n" +
                "</simpleEvent>";

        InputSource source = new InputSource(new StringReader(xml));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        EventBean bean = adapterService.adapterForDOM(simpleDoc);
        assertEquals("value", bean.get("nested1"));
    }

    private static ConfigurationEventTypeXMLDOM getXMLDOMConfig()
    {
        ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
        config.setRootElementName("simpleEvent");
        config.addXPathProperty("nested1", "/simpleEvent/nested1", XPathConstants.STRING);
        return config;
    }
}
