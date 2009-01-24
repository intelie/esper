package com.espertech.esper.regression.event;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.util.XMLRenderingOptions;
import com.espertech.esper.support.bean.SupportBeanRendererOne;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestEventRenderer extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testPOJOMap()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRendererOne", SupportBeanRendererOne.class);

        SupportBeanRendererOne bean = new SupportBeanRendererOne();
        Map<String, Object> otherMap = new LinkedHashMap<String, Object>();
        otherMap.put("abc", "def");
        otherMap.put("def", 123);
        otherMap.put("efg", null);
        otherMap.put(null, 1234);
        bean.setStringObjectMap(otherMap);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanRendererOne");
        epService.getEPRuntime().sendEvent(bean);

        String json = epService.getEPRuntime().getEventRenderer().renderJSON("MyEvent", stmt.iterator().next());
        String expectedJson = "{ \"MyEvent\": { \"stringObjectMap\": { \"abc\": \"def\", \"def\": 123, \"efg\": null } } }";
        assertEquals(removeNewline(expectedJson), removeNewline(json));

        String xmlOne = epService.getEPRuntime().getEventRenderer().renderXML("MyEvent", stmt.iterator().next());
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MyEvent>\n" +
                "  <stringObjectMap>\n" +
                "    <abc>def<abc>\n" +
                "    <def>123<def>\n" +
                "    <efg><efg>\n" +
                "  </stringObjectMap>\n" +
                "</MyEvent>";
        assertEquals(removeNewline(expected), removeNewline(xmlOne));

        XMLRenderingOptions opt = new XMLRenderingOptions();
        opt.setDefaultAsAttribute(true);
        String xmlTwo = epService.getEPRuntime().getEventRenderer().renderXML("MyEvent", stmt.iterator().next(), opt);
        String expectedTwo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MyEvent>\n" +
                "  <stringObjectMap abc=\"def\" def=\"123\"/>\n" +
                "</MyEvent>";
        assertEquals(removeNewline(expectedTwo), removeNewline(xmlTwo));
    }

    private String removeNewline(String text)
    {
        return text.replaceAll("\\s\\s+|\\n|\\r", " ").trim();
    }
}
