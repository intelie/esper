package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBeanKeywords;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

public class TestSelectExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }
    
    public void testGraphSelect()
    {
        epService.getEPAdministrator().createEPL("insert into MyStream select nested from " + SupportBeanComplexProps.class.getName());

        String viewExpr = "select nested.nestedValue, nested.nestedNested.nestedNestedValue from MyStream";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        assertNotNull(testListener.assertOneGetNewAndReset());
    }

    public void testKeywordsAllowed()
    {
        String fields = "count,escape,every,sum,avg,max,min,coalesce,median,stddev,avedev,events,first,last,unidirectional,pattern,sql,metadatasql,prev,prior,weekday,lastweekday,cast,snapshot,variable,window,left,right,full,outer,join";
        epService.getEPAdministrator().getConfiguration().addEventType("Keywords", SupportBeanKeywords.class);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select " + fields + " from Keywords");
        stmt.addListener(testListener);
        epService.getEPRuntime().sendEvent(new SupportBeanKeywords());
        ArrayAssertionUtil.assertEqualsExactOrder(fields.split(","), stmt.getEventType().getPropertyNames());

        EventBean event = testListener.assertOneGetNewAndReset();
        String[] fieldsArr = fields.split(",");
        for (int i = 0; i < fieldsArr.length; i++)
        {
            assertEquals(1, event.get(fieldsArr[i]));
        }
        stmt.destroy();

        stmt = epService.getEPAdministrator().createEPL("select escape as stddev, count(*) as count, last from Keywords");
        stmt.addListener(testListener);
        epService.getEPRuntime().sendEvent(new SupportBeanKeywords());

        event = testListener.assertOneGetNewAndReset();
        assertEquals(1, event.get("stddev"));
        assertEquals(1L, event.get("count"));
        assertEquals(1, event.get("last"));
    }

    public void testGetEventType()
    {
        String viewExpr = "select string, boolBoxed as aBool, 3*intPrimitive, floatBoxed+floatPrimitive as result" +
                          " from " + SupportBean.class.getName() + ".win:length(3) " +
                          " where boolBoxed = true";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        log.debug(".testGetEventType properties=" + Arrays.toString(type.getPropertyNames()));
        ArrayAssertionUtil.assertEqualsAnyOrder(type.getPropertyNames(), new String[] {"(3*intPrimitive)", "string", "result", "aBool"});
        assertEquals(String.class, type.getPropertyType("string"));
        assertEquals(Boolean.class, type.getPropertyType("aBool"));
        assertEquals(Float.class, type.getPropertyType("result"));
        assertEquals(Integer.class, type.getPropertyType("(3*intPrimitive)"));
    }

    public void testWindowStats()
    {
        String viewExpr = "select string, boolBoxed as aBool, 3*intPrimitive, floatBoxed+floatPrimitive as result" +
                          " from " + SupportBean.class.getName() + ".win:length(3) " +
                          " where boolBoxed = true";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        testListener.reset();

        sendEvent("a", false, 0, 0, 0);
        sendEvent("b", false, 0, 0, 0);
        assertTrue(testListener.getLastNewData() == null);
        sendEvent("c", true, 3, 10, 20);

        EventBean received = testListener.getAndResetLastNewData()[0];
        assertEquals("c", received.get("string"));
        assertEquals(true, received.get("aBool"));
        assertEquals(30f, received.get("result"));
    }

    private void sendEvent(String s, boolean b, int i, float f1, float f2)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setBoolBoxed(b);
        bean.setIntPrimitive(i);
        bean.setFloatPrimitive(f1);
        bean.setFloatBoxed(f2);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestSelectExpr.class);
}
