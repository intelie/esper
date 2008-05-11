package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestInstanceOfExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testInstanceofSimple()
    {
        String stmtText = "select instanceof(string, string) as t0, " +
                          " instanceof(intBoxed, int) as t1, " +
                          " instanceof(floatBoxed, java.lang.Float) as t2, " +
                          " instanceof(string, java.lang.Float, char, byte) as t3, " +
                          " instanceof(intPrimitive, java.lang.Integer) as t4, " +
                          " instanceof(intPrimitive, long) as t5, " +
                          " instanceof(intPrimitive, long, long, java.lang.Number) as t6, " +
                          " instanceof(floatBoxed, long, float) as t7 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        for (int i = 0; i < 7; i++)
        {
            assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t" + i));
        }

        SupportBean bean = new SupportBean("abc", 100);
        bean.setFloatBoxed(100F);
        epService.getEPRuntime().sendEvent(bean);
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {true, false, true, false, true, false, true, true});

        bean = new SupportBean(null, 100);
        bean.setFloatBoxed(null);
        epService.getEPRuntime().sendEvent(bean);
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, false, false, false, true, false, true, false});

        Float f = null;
        assertFalse(f instanceof Float);
    }

    public void testInstanceofStringAndNull_OM() throws Exception
    {
        String stmtText = "select instanceof(string, string) as t0, " +
                          "instanceof(string, float, string, int) as t1 " +
                          "from " + SupportBean.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create()
                .add(Expressions.instanceOf("string", "string"), "t0")
                .add(Expressions.instanceOf(Expressions.property("string"), "float", "string", "int"), "t1"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("abc", 100));
        EventBean event = listener.assertOneGetNewAndReset();
        assertTrue((Boolean) event.get("t0"));
        assertTrue((Boolean) event.get("t1"));

        epService.getEPRuntime().sendEvent(new SupportBean(null, 100));
        event = listener.assertOneGetNewAndReset();
        assertFalse((Boolean) event.get("t0"));
        assertFalse((Boolean) event.get("t1"));
    }

    public void testInstanceofStringAndNull_Compile() throws Exception
    {
        String stmtText = "select instanceof(string, string) as t0, " +
                          "instanceof(string, float, string, int) as t1 " +
                          "from " + SupportBean.class.getName();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("abc", 100));
        EventBean event = listener.assertOneGetNewAndReset();
        assertTrue((Boolean) event.get("t0"));
        assertTrue((Boolean) event.get("t1"));

        epService.getEPRuntime().sendEvent(new SupportBean(null, 100));
        event = listener.assertOneGetNewAndReset();
        assertFalse((Boolean) event.get("t0"));
        assertFalse((Boolean) event.get("t1"));
    }

    public void testDynamicPropertyJavaTypes()
    {
        String stmtText = "select instanceof(inner?, string) as t0, " +
                          " instanceof(inner?, int) as t1, " +
                          " instanceof(inner?, java.lang.Float) as t2, " +
                          " instanceof(inner?, java.lang.Float, char, byte) as t3, " +
                          " instanceof(inner?, java.lang.Integer) as t4, " +
                          " instanceof(inner?, long) as t5, " +
                          " instanceof(inner?, long, long, java.lang.Number) as t6, " +
                          " instanceof(inner?, long, float) as t7 " +
                          " from " + SupportMarkerInterface.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {true, false, false, false, false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(100f));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, false, true, true, false, false, true, true});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, false, false, false, false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(10));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, true, false, false, true, false, true, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(99l));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, false, false, false, false, true, true, true});
    }

    public void testDynamicSuperTypeAndInterface()
    {
        String stmtText = "select instanceof(inner?, " + SupportMarkerInterface.class.getName() + ") as t0, " +
                          " instanceof(inner?, " + ISupportA.class.getName() + ") as t1, " +
                          " instanceof(inner?, " + ISupportBaseAB.class.getName() + ") as t2, " +
                          " instanceof(inner?, " + ISupportBaseABImpl.class.getName() + ") as t3, " +
                          " instanceof(inner?, " + ISupportA.class.getName() + ", " + ISupportB.class.getName() + ") as t4, " +
                          " instanceof(inner?, " + ISupportBaseAB.class.getName() + ", " + ISupportB.class.getName() + ") as t5, " +
                          " instanceof(inner?, " + ISupportAImplSuperG.class.getName() + ", " + ISupportB.class.getName() + ") as t6, " +
                          " instanceof(inner?, " + ISupportAImplSuperGImplPlus.class.getName() + ", " + SupportBeanBase.class.getName() + ") as t7 " +

                          " from " + SupportMarkerInterface.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEPL(stmtText);
        selectTestCase.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBeanDynRoot("abc")));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {true, false, false, false, false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new ISupportAImplSuperGImplPlus()));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, true, true, false, true, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new ISupportAImplSuperGImpl("", "", "")));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, true, true, false, true, true, true, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new ISupportBaseABImpl("")));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, false, true, true, false, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new ISupportBImpl("", "")));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, false, true, false, true, true, true, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new ISupportAImpl("", "")));
        assertResults(listener.assertOneGetNewAndReset(), new boolean[] {false, true, true, false, true, true, false, false});
    }

    private void assertResults(EventBean event, boolean[] result)
    {
        for (int i = 0; i < result.length; i++)
        {
            assertEquals("failed for index " + i, result[i], event.get("t" + i));            
        }
    }
}
