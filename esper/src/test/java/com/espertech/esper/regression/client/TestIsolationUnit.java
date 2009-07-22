package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestIsolationUnit extends TestCase
{
    // TODO
    // write doc
    // Test threading
    // Test isolation unit mgmt + destroy
    //   epServiceManager.getIsolationUnit(name);
    //   String[] = epServiceManager.getIsolationUnits();
    //   epStatement.getIsolationUnit();
    //   
    // Test statement start/stop while taken or after return
    // Test named windows
    // Test subquery, aggregation state, view state

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        listener = new SupportUpdateListener();
    }

    public void testIsolateFilter()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from pattern [every a=SupportBean -> b=SupportBean(string=a.string)]");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertFalse(listener.getAndClearIsInvoked());

        EPRuntimeIsolated unit = epService.getIsolatedRuntime();

        // send fake to wrong place
        unit.sendEvent(new SupportBean("E1", -1));

        unit.takeStatement(stmt);

        // send to 'wrong' engine
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        assertFalse(listener.getAndClearIsInvoked());

        // send to 'right' engine
        unit.sendEvent(new SupportBean("E1", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,a.intPrimitive,b.intPrimitive".split(","), new Object[] {"E1", 1, 3});

        // send second pair, and a fake to the wrong place
        unit.sendEvent(new SupportBean("E2", 4));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", -1));

        unit.returnStatement(stmt);

        // send to 'wrong' engine
        unit.sendEvent(new SupportBean("E2", 5));
        assertFalse(listener.getAndClearIsInvoked());

        // send to 'right' engine
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 6));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string,a.intPrimitive,b.intPrimitive".split(","), new Object[] {"E2", 4, 6});
    }

    public void testIsolatedSchedule()
    {
        sendTimerUnisolated(100000);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from pattern [every a=SupportBean -> timer:interval(10)]");
        stmt.addListener(listener);

        sendTimerUnisolated(105000);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        EPRuntimeIsolated unit = epService.getIsolatedRuntime();
        sendTimerIso(0, unit);
        unit.takeStatement(stmt);

        sendTimerIso(9999, unit);
        assertFalse(listener.isInvoked());

        sendTimerIso(10000, unit);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string".split(","), new Object[] {"E1"});

        sendTimerIso(11000, unit);
        unit.sendEvent(new SupportBean("E2", 1));

        sendTimerUnisolated(120000);
        assertFalse(listener.isInvoked());

        unit.returnStatement(stmt);

        sendTimerUnisolated(129999);
        assertFalse(listener.isInvoked());

        sendTimerUnisolated(130000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.string".split(","), new Object[] {"E2"});

        sendTimerIso(30000, unit);
        assertFalse(listener.isInvoked());
    }

    public void testInsertInto()
    {
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyStream select * from SupportBean");
        SupportUpdateListener listenerInsert = new SupportUpdateListener();
        stmtInsert.addListener(listenerInsert);

        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from MyStream");
        SupportUpdateListener listenerSelect = new SupportUpdateListener();
        stmtSelect.addListener(listenerSelect);

        // unit takes "insert"
        EPRuntimeIsolated unit = epService.getIsolatedRuntime();
        unit.takeStatement(stmtInsert);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertFalse(listenerSelect.getAndClearIsInvoked());
        assertFalse(listenerInsert.getAndClearIsInvoked());

        unit.sendEvent(new SupportBean("E2", 2));
        assertFalse(listenerSelect.getAndClearIsInvoked());
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), "string".split(","), new Object[] {"E2"});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        assertFalse(listenerSelect.getAndClearIsInvoked());
        assertFalse(listenerInsert.getAndClearIsInvoked());    // is there a remaining event that gets flushed with the last one?

        // unit returns insert
        unit.returnStatement(stmtInsert);

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), "string".split(","), new Object[] {"E4"});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), "string".split(","), new Object[] {"E4"});

        unit.sendEvent(new SupportBean("E5", 5));
        assertFalse(listenerSelect.getAndClearIsInvoked());
        assertFalse(listenerInsert.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 6));
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), "string".split(","), new Object[] {"E6"});
        ArrayAssertionUtil.assertProps(listenerSelect.assertOneGetNewAndReset(), "string".split(","), new Object[] {"E6"});
    }

    private void sendTimerUnisolated(long millis){
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(millis));
    }

    private void sendTimerIso(long millis, EPRuntimeIsolated unit){
        unit.sendEvent(new CurrentTimeEvent(millis));
    }
}
