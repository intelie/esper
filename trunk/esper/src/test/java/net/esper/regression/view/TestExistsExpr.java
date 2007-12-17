package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.soda.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.*;
import net.esper.event.EventBean;
import net.esper.util.SerializableObjectCopier;

public class TestExistsExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testExistsSimple()
    {
        String stmtText = "select exists(string) as t0, " +
                          " exists(intBoxed?) as t1, " +
                          " exists(dummy?) as t2, " +
                          " exists(intPrimitive?) as t3, " +
                          " exists(intPrimitive) as t4 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(stmtText);
        selectTestCase.addListener(listener);

        for (int i = 0; i < 5; i++)
        {
            assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t" + i));            
        }

        SupportBean bean = new SupportBean("abc", 100);
        bean.setFloatBoxed(9.5f);
        bean.setIntBoxed(3);
        epService.getEPRuntime().sendEvent(bean);
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {true, true, false, true, true});
    }

    public void testExistsInner()
    {
        String stmtText = "select exists(inner?.id) as t0, " +
                          " exists(inner?.id?) as t1, " +
                          " exists(inner?.inner.intBoxed) as t2, " +
                          " exists(inner?.indexed[0]?) as t3, " +
                          " exists(inner?.mapped('keyOne')?) as t4, " +
                          " exists(inner?.nested?) as t5, " +
                          " exists(inner?.nested.nestedValue?) as t6, " +
                          " exists(inner?.nested.nestedNested?) as t7, " +
                          " exists(inner?.nested.nestedNested.nestedNestedValue?) as t8, " +
                          " exists(inner?.nested.nestedNested.nestedNestedValue.dummy?) as t9, " +
                          " exists(inner?.nested.nestedNested.dummy?) as t10 " +
                          " from " + SupportMarkerInterface.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(stmtText);
        selectTestCase.addListener(listener);

        for (int i = 0; i < 11; i++)
        {
            assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t" + i));
        }

        // cannot exists if the inner is null
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, false, false, false, false, false, false, false, false, false});

        // try nested, indexed and mapped
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(SupportBeanComplexProps.makeDefaultBean()));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, false, true, true, true, true, true, true, false, false});

        // try nested, indexed and mapped
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(SupportBeanComplexProps.makeDefaultBean()));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, false, true, true, true, true, true, true, false, false});

        // try a boxed that returns null but does exists
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBeanDynRoot(new SupportBean())));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {false, false, true, false, false, false, false, false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean_A("10")));
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new boolean[] {true, true, false, false, false, false, false, false, false, false, false});
    }

    public void testCastDoubleAndNull_OM() throws Exception
    {
        String stmtText = "select exists(inner?.intBoxed) as t0 " +
                          "from " + SupportMarkerInterface.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().add(Expressions.existsProperty("inner?.intBoxed"), "t0"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarkerInterface.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEQL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean()));
        assertEquals(true, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));
    }

    public void testCastStringAndNull_Compile() throws Exception
    {
        String stmtText = "select exists(inner?.intBoxed) as t0 " +
                          "from " + SupportMarkerInterface.class.getName();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(stmtText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEQL());

        EPStatement selectTestCase = epService.getEPAdministrator().create(model);
        selectTestCase.addListener(listener);

        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean()));
        assertEquals(true, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(null));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot("abc"));
        assertEquals(false, listener.assertOneGetNewAndReset().get("t0"));
    }

    private void assertResults(EventBean event, boolean[] result)
    {
        for (int i = 0; i < result.length; i++)
        {
            assertEquals("failed for index " + i, result[i], event.get("t" + i));
        }
    }
}
