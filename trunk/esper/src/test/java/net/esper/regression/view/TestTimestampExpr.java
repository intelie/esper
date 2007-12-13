package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.soda.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.*;
import net.esper.event.EventBean;
import net.esper.util.SerializableObjectCopier;

public class TestTimestampExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testGetTimestamp()
    {
        sendTimer(0);
        String stmtText = "select current_timestamp as t0, " +
                          " current_timestamp() as t1, " +
                          " current_timestamp + 1 as t2 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(stmtText);
        selectTestCase.addListener(listener);

        assertEquals(Long.class, selectTestCase.getEventType().getPropertyType("t0"));
        assertEquals(Long.class, selectTestCase.getEventType().getPropertyType("t1"));

        sendTimer(100);
        epService.getEPRuntime().sendEvent(new SupportBean());
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {100l, 100l, 101l});

        sendTimer(999);
        epService.getEPRuntime().sendEvent(new SupportBean());
        event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {999l, 999l, 1000l});
    }

    public void testGetTimestamp_OM() throws Exception
    {
        sendTimer(0);
        String stmtText = "select current_timestamp() as t0 from " + SupportBean.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().add(Expressions.currentTimestamp(), "t0"));
        model.setFromClause(FromClause.create().add(FilterStream.create(SupportBean.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEQL());

        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        assertEquals(Long.class, stmt.getEventType().getPropertyType("t0"));

        sendTimer(777);
        epService.getEPRuntime().sendEvent(new SupportBean());
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {777l});
    }

    public void testGetTimestamp_Compile() throws Exception
    {
        sendTimer(0);
        String stmtText = "select current_timestamp() as t0 from " + SupportBean.class.getName();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(stmtText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEQL());

        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        assertEquals(Long.class, stmt.getEventType().getPropertyType("t0"));

        sendTimer(777);
        epService.getEPRuntime().sendEvent(new SupportBean());
        EventBean event = listener.assertOneGetNewAndReset();
        assertResults(event, new Object[] {777l});
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void assertResults(EventBean event, Object[] result)
    {
        for (int i = 0; i < result.length; i++)
        {
            assertEquals("failed for index " + i, result[i], event.get("t" + i));
        }
    }
}
