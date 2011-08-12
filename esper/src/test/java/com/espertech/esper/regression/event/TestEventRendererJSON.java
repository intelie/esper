/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.event;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.util.JSONEventRenderer;
import com.espertech.esper.event.util.OutputValueRendererJSONString;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportEnum;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestEventRendererJSON extends TestCase
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
        bean.setEnumValue(SupportEnum.ENUM_VALUE_1);
        
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        EPStatement statement = epService.getEPAdministrator().createEPL("select * from SupportBean");
        epService.getEPRuntime().sendEvent(bean);
        
        String result = epService.getEPRuntime().getEventRenderer().renderJSON("supportBean", statement.iterator().next());

        //System.out.println(result);
        String expected = "{ \"supportBean\": { \"bigDecimal\": null, \"boolBoxed\": null, \"boolPrimitive\": false, \"byteBoxed\": null, \"bytePrimitive\": 0, \"charBoxed\": null, \"charPrimitive\": \"x\", \"doubleBoxed\": null, \"doublePrimitive\": 0.0, \"enumValue\": \"ENUM_VALUE_1\", \"floatBoxed\": null, \"floatPrimitive\": 0.0, \"intBoxed\": 992, \"intPrimitive\": 1, \"longBoxed\": null, \"longPrimitive\": 0, \"shortBoxed\": null, \"shortPrimitive\": 0, \"string\": \"a\\nc\", \"this\": { \"bigDecimal\": null, \"boolBoxed\": null, \"boolPrimitive\": false, \"byteBoxed\": null, \"bytePrimitive\": 0, \"charBoxed\": null, \"charPrimitive\": \"x\", \"doubleBoxed\": null, \"doublePrimitive\": 0.0, \"enumValue\": \"ENUM_VALUE_1\", \"floatBoxed\": null, \"floatPrimitive\": 0.0, \"intBoxed\": 992, \"intPrimitive\": 1, \"longBoxed\": null, \"longPrimitive\": 0, \"shortBoxed\": null, \"shortPrimitive\": 0, \"string\": \"a\\nc\" } } }";
        assertEquals(removeNewline(expected), removeNewline(result));
        
        JSONEventRenderer renderer = epService.getEPRuntime().getEventRenderer().getJSONRenderer(statement.getEventType());
        String jsonEvent = renderer.render("supportBean", statement.iterator().next());
        assertEquals(removeNewline(expected), removeNewline(jsonEvent));
    }

    public void testMapAndNestedArray()
    {
        Map<String, Object> defOuter = new LinkedHashMap<String, Object>();
        defOuter.put("intarr", int[].class);
        defOuter.put("innersimple", "InnerMap");
        defOuter.put("innerarray", "InnerMap[]");
        defOuter.put("prop0", SupportBean_A.class);

        Map<String, Object> defInner = new LinkedHashMap<String, Object>();
        defInner.put("stringarr", String[].class);
        defInner.put("prop1", String.class);

        epService.getEPAdministrator().getConfiguration().addEventType("InnerMap", defInner);
        epService.getEPAdministrator().getConfiguration().addEventType("OuterMap", defOuter);
        EPStatement statement = epService.getEPAdministrator().createEPL("select * from OuterMap");

        Map<String, Object> dataInner = new LinkedHashMap<String, Object>();
        dataInner.put("stringarr", new String[] {"a", "b"});
        dataInner.put("prop1", "");
        Map<String, Object> dataInnerTwo = new LinkedHashMap<String, Object>();
        dataInnerTwo.put("stringarr", new String[0]);
        dataInnerTwo.put("prop1", "abcdef");
        Map<String, Object> dataOuter = new LinkedHashMap<String, Object>();
        dataOuter.put("intarr", new int[] {1, 2});
        dataOuter.put("innersimple", dataInner);
        dataOuter.put("innerarray", new Map[] {dataInner, dataInnerTwo});
        dataOuter.put("prop0", new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(dataOuter, "OuterMap");

        String result = epService.getEPRuntime().getEventRenderer().renderJSON("outerMap", statement.iterator().next());

        //System.out.println(result);
        String expected = "{\n" +
                "  \"outerMap\": {\n" +
                "    \"intarr\": [1, 2],\n" +
                "    \"innersimple\": {\n" +
                "      \"prop1\": \"\",\n" +
                "      \"stringarr\": [\"a\", \"b\"]\n" +
                "    },\n" +
                "    \"innerarray\": [{\n" +
                "        \"prop1\": \"\",\n" +
                "        \"stringarr\": [\"a\", \"b\"]\n" +
                "      },\n" +
                "      {\n" +
                "        \"prop1\": \"abcdef\",\n" +
                "        \"stringarr\": []\n" +
                "      }],\n" +
                "    \"prop0\": {\n" +
                "      \"id\": \"A1\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        assertEquals(removeNewline(expected), removeNewline(result));
    }

    public void testEmptyMap()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("EmptyMapEvent", EmptyMapEvent.class);
        EPStatement statement = epService.getEPAdministrator().createEPL("select * from EmptyMapEvent");

        epService.getEPRuntime().sendEvent(new EmptyMapEvent(null));
        String result = epService.getEPRuntime().getEventRenderer().renderJSON("outer", statement.iterator().next());
        String expected = "{ \"outer\": { \"props\": null } }";
        assertEquals(removeNewline(expected), removeNewline(result));

        epService.getEPRuntime().sendEvent(new EmptyMapEvent(Collections.<String, String>emptyMap()));
        result = epService.getEPRuntime().getEventRenderer().renderJSON("outer", statement.iterator().next());
        expected = "{ \"outer\": { \"props\": {} } }";
        assertEquals(removeNewline(expected), removeNewline(result));

        epService.getEPRuntime().sendEvent(new EmptyMapEvent(Collections.singletonMap("a", "b")));
        result = epService.getEPRuntime().getEventRenderer().renderJSON("outer", statement.iterator().next());
        expected = "{ \"outer\": { \"props\": { \"a\": \"b\" } } }";
        assertEquals(removeNewline(expected), removeNewline(result));
    }

    public static void testEnquote()
    {
        String[][] testdata = new String[][] {
                {"\t", "\"\\t\""},
                {"\n", "\"\\n\""},
                {"\r", "\"\\r\""},
                {Character.toString((char)0), "\"\\u0000\""},
        };

        for (int i = 0; i < testdata.length; i++)
        {
            StringBuilder buf = new StringBuilder();
            OutputValueRendererJSONString.enquote(testdata[i][0], buf);
            assertEquals(testdata[i][1], buf.toString());
        }
    }

    private String removeNewline(String text)
    {
        return text.replaceAll("\\s\\s+|\\n|\\r", " ").trim();
    }

    public static class EmptyMapEvent {
        private Map<String, String> props;

        public EmptyMapEvent(Map<String, String> props) {
            this.props = props;
        }

        public Map<String, String> getProps() {
            return props;
        }
    }
}
