package com.espertech.esper.regression.event;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.util.EventRendererFactory;
import com.espertech.esper.client.util.XMLRenderingOptions;
import com.espertech.esper.client.util.JSONRenderingOptions;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.event.util.JSONOutputString;
import com.espertech.esper.event.util.XMLOutputString;

import java.util.Map;
import java.util.HashMap;

public class TestEventRendererXML extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testRenderSimple()
    {
        SupportBean bean = new SupportBean();
        bean.setString("a\nc");
        bean.setIntPrimitive(1);
        bean.setIntBoxed(992);
        bean.setCharPrimitive('x');

        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBean", SupportBean.class);
        EPStatement statement = epService.getEPAdministrator().createEPL("select * from SupportBean");
        epService.getEPRuntime().sendEvent(bean);

        String result = EventRendererFactory.renderXML("supportBean", statement.iterator().next());
        //System.out.println(result);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<supportBean>\n" +
                "  <boolPrimitive>false</boolPrimitive>\n" +
                "  <bytePrimitive>0</bytePrimitive>\n" +
                "  <charPrimitive>x</charPrimitive>\n" +
                "  <doublePrimitive>0.0</doublePrimitive>\n" +
                "  <floatPrimitive>0.0</floatPrimitive>\n" +
                "  <intBoxed>992</intBoxed>\n" +
                "  <intPrimitive>1</intPrimitive>\n" +
                "  <longPrimitive>0</longPrimitive>\n" +
                "  <shortPrimitive>0</shortPrimitive>\n" +
                "  <string>a\\u000ac</string>\n" +
                "  <this>\n" +
                "    <boolPrimitive>false</boolPrimitive>\n" +
                "    <bytePrimitive>0</bytePrimitive>\n" +
                "    <charPrimitive>x</charPrimitive>\n" +
                "    <doublePrimitive>0.0</doublePrimitive>\n" +
                "    <floatPrimitive>0.0</floatPrimitive>\n" +
                "    <intBoxed>992</intBoxed>\n" +
                "    <intPrimitive>1</intPrimitive>\n" +
                "    <longPrimitive>0</longPrimitive>\n" +
                "    <shortPrimitive>0</shortPrimitive>\n" +
                "    <string>a\\u000ac</string>\n" +
                "  </this>\n" +
                "</supportBean>";
        assertEquals(removeNewline(expected), removeNewline(result));

        result = EventRendererFactory.renderXML("supportBean", statement.iterator().next(), new XMLRenderingOptions().setDefaultAsAttribute(true));
        System.out.println(result);
        expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<supportBean boolPrimitive=\"false\" bytePrimitive=\"0\" charPrimitive=\"x\" doublePrimitive=\"0.0\" floatPrimitive=\"0.0\" intBoxed=\"992\" intPrimitive=\"1\" longPrimitive=\"0\" shortPrimitive=\"0\" string=\"a\\u000ac\">\n" +
                "    <this boolPrimitive=\"false\" bytePrimitive=\"0\" charPrimitive=\"x\" doublePrimitive=\"0.0\" floatPrimitive=\"0.0\" intBoxed=\"992\" intPrimitive=\"1\" longPrimitive=\"0\" shortPrimitive=\"0\" string=\"a\\u000ac\"/>\n" +
                "</supportBean>";
        assertEquals(removeNewline(expected), removeNewline(result));
    }

    public void testMapAndNestedArray()
    {
        Map<String, Object> defOuter = new HashMap<String, Object>();
        defOuter.put("prop0", SupportBean_A.class);
        defOuter.put("intarr", int[].class);
        defOuter.put("innersimple", "InnerMap");
        defOuter.put("innerarray", "InnerMap[]");

        Map<String, Object> defInner = new HashMap<String, Object>();
        defInner.put("stringarr", String[].class);
        defInner.put("prop1", String.class);

        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("InnerMap", defInner);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OuterMap", defOuter);
        EPStatement statement = epService.getEPAdministrator().createEPL("select * from OuterMap");

        Map<String, Object> dataInner = new HashMap<String, Object>();
        dataInner.put("stringarr", new String[] {"a", "b"});
        dataInner.put("prop1", "");
        Map<String, Object> dataInnerTwo = new HashMap<String, Object>();
        dataInnerTwo.put("stringarr", new String[0]);
        dataInnerTwo.put("prop1", "abcdef");
        Map<String, Object> dataOuter = new HashMap<String, Object>();
        dataOuter.put("prop0", new SupportBean_A("A1"));
        dataOuter.put("intarr", new int[] {1, 2});
        dataOuter.put("innersimple", dataInner);
        dataOuter.put("innerarray", new Map[] {dataInner, dataInnerTwo});
        epService.getEPRuntime().sendEvent(dataOuter, "OuterMap");

        String result = EventRendererFactory.renderXML("outerMap", statement.iterator().next());
        System.out.println(result);
        String expected = "";
        assertEquals(removeNewline(expected), removeNewline(result));

        result = EventRendererFactory.renderXML("outerMap xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", statement.iterator().next(), new XMLRenderingOptions().setDefaultAsAttribute(true));
        //System.out.println(result);
    }

    public static void testEnquote()
    {
        String[][] testdata = new String[][] {
                {"\"", "&quot;"},
                {"'", "&apos;"},
                {"&", "&amp;"},
                {"<", "&lt;"},
                {">", "&gt;"},
                {Character.toString((char)0), "\\u0000"},
        };

        for (int i = 0; i < testdata.length; i++)
        {
            StringBuilder buf = new StringBuilder();
            XMLOutputString.xmlEncode(testdata[i][0], buf);
            assertEquals(testdata[i][1], buf.toString());
        }
    }

    private String removeNewline(String text)
    {
        return text.replaceAll("\\s\\s+|\\n|\\r", " ").trim();
    }
}
