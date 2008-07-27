package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.util.SerializableObjectCopier;
import com.espertech.esper.event.EventBean;

public class TestCastExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testCastSimple()
    {
        String stmtText = "select cast(string as string) as t0, " +
                          " cast(intBoxed, int) as t1, " +
                          " cast(floatBoxed, java.lang.Float) as t2, " +
                          " cast(string, java.lang.String) as t3, " +
                          " cast(intPrimitive, java.lang.Integer) as t4, " +
                          " cast(intPrimitive, long) as t5, " +
                          " cast(intPrimitive, java.lang.Number) as t6, " +
                          " cast(floatBoxed, long) as t7 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        assertEquals(String.class, selectTestCase.getEventType().getPropertyType("t0"));
        assertEquals(Integer.class, selectTestCase.getEventType().getPropertyType("t1"));
        assertEquals(Float.class, selectTestCase.getEventType().getPropertyType("t2"));
        assertEquals(String.class, selectTestCase.getEventType().getPropertyType("t3"));
        assertEquals(Integer.class, selectTestCase.getEventType().getPropertyType("t4"));
        assertEquals(Long.class, selectTestCase.getEventType().getPropertyType("t5"));
        assertEquals(Number.class, selectTestCase.getEventType().getPropertyType("t6"));
        assertEquals(Long.class, selectTestCase.getEventType().getPropertyType("t7"));

        SupportBean bean = new SupportBean("abc", 100);
        bean.setFloatBoxed(9.5f);
        bean.setIntBoxed(3);
        epService.getEPRuntime().sendEvent(bean);
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {"abc", 3, 9.5f, "abc", 100, 100L, 100, 9l});

        bean = new SupportBean(null, 100);
        bean.setFloatBoxed(null);
        bean.setIntBoxed(null);
        epService.getEPRuntime().sendEvent(bean);
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {null, null, null, null, 100, 100L, 100, null});
    }

    public void testCastDoubleAndNull_OM() throws Exception
    {
        String stmtText = "select cast(item?, double) as t0 " +
                          "from " + SupportMarkerInterface.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().add(Expressions.cast("item?", "double"), "t0"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarkerInterface.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        assertEquals(Double.class, selectTestCase.getEventType().getPropertyType("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(100));
        assertEquals(100d, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot((byte)2));
        assertEquals(2d, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(77.7777));
        assertEquals(77.7777d, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(6L));
        assertEquals(6d, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertEquals(null, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("t0"));
    }

    public void testCastStringAndNull_Compile() throws Exception
    {
        String stmtText = "select cast(item?, java.lang.String) as t0 " +
                          "from " + SupportMarkerInterface.class.getName();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        assertEquals(stmtText, model.toEPL());
        selectTestCase.addListener(listener);

        assertEquals(String.class, selectTestCase.getEventType().getPropertyType("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(100));
        assertEquals("100", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot((byte)2));
        assertEquals("2", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(77.7777));
        assertEquals("77.7777", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(6L));
        assertEquals("6", listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertEquals(null, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertEquals("abc", listener.assertOneGetNewAndReset().get("t0"));
    }

    public void testCastInterface()
    {
        String caseExpr = "select cast(item?, " + SupportMarkerInterface.class.getName() + ") as t0, " +
                          " cast(item?, " + ISupportA.class.getName() + ") as t1, " +
                          " cast(item?, " + ISupportBaseAB.class.getName() + ") as t2, " +
                          " cast(item?, " + ISupportBaseABImpl.class.getName() + ") as t3, " +
                          " cast(item?, " + ISupportC.class.getName() + ") as t4, " +
                          " cast(item?, " + ISupportD.class.getName() + ") as t5, " +
                          " cast(item?, " + ISupportAImplSuperG.class.getName() + ") as t6, " +
                          " cast(item?, " + ISupportAImplSuperGImplPlus.class.getName() + ") as t7 " +
                          " from " + SupportMarkerInterface.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(caseExpr);
        selectTestCase.addListener(listener);

        assertEquals(SupportMarkerInterface.class, selectTestCase.getEventType().getPropertyType("t0"));
        assertEquals(ISupportA.class, selectTestCase.getEventType().getPropertyType("t1"));
        assertEquals(ISupportBaseAB.class, selectTestCase.getEventType().getPropertyType("t2"));
        assertEquals(ISupportBaseABImpl.class, selectTestCase.getEventType().getPropertyType("t3"));
        assertEquals(ISupportC.class, selectTestCase.getEventType().getPropertyType("t4"));
        assertEquals(ISupportD.class, selectTestCase.getEventType().getPropertyType("t5"));
        assertEquals(ISupportAImplSuperG.class, selectTestCase.getEventType().getPropertyType("t6"));
        assertEquals(ISupportAImplSuperGImplPlus.class, selectTestCase.getEventType().getPropertyType("t7"));

        Object bean = new SupportBeanDynRoot("abc");
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {bean, null, null, null, null, null, null, null});

        bean = new ISupportDImpl("", "", "");
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {null, null, null, null, null, bean, null, null});

        bean = new ISupportBCImpl("", "", "");
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {null, null, bean, null, bean, null, null, null});

        bean = new ISupportAImplSuperGImplPlus();
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {null, bean, bean, null, bean, null, bean, bean});

        bean = new ISupportBaseABImpl("");
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {null, null, bean, bean, null, null, null, null});
    }

    public void testCastBoolean()
    {
        String stmtText = "select cast(boolPrimitive as java.lang.Boolean) as t0, " +
                          " cast(boolBoxed | boolPrimitive, boolean) as t1, " +
                          " cast(boolBoxed, string) as t2 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t0"));
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t1"));
        assertEquals(String.class, selectTestCase.getEventType().getPropertyType("t2"));

        SupportBean bean = new SupportBean("abc", 100);
        bean.setBoolPrimitive(true);
        bean.setBoolBoxed(true);
        epService.getEPRuntime().sendEvent(bean);
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {true, true, "true"});

        bean = new SupportBean(null, 100);
        bean.setBoolPrimitive(false);
        bean.setBoolBoxed(false);
        epService.getEPRuntime().sendEvent(bean);
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {false, false, "false"});

        bean = new SupportBean(null, 100);
        bean.setBoolPrimitive(true);
        bean.setBoolBoxed(null);
        epService.getEPRuntime().sendEvent(bean);
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {true, null, null});
    }

    private void assertResults(EventBean event, Object[] result)
    {
        for (int i = 0; i < result.length; i++)
        {
            assertEquals("failed for index " + i, result[i], event.get("t" + i));
        }
    }
}
