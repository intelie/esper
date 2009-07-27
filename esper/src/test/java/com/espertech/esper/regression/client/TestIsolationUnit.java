package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestIsolationUnit extends TestCase
{
    // TODO
    // Fix current date expression
    // Test EHA
    //
    // Test isolation unit mgmt + destroy
    //   epServiceManager.getIsolationUnit(name);
    //   String[] = epServiceManager.getIsolationUnits();
    //   epStatement.getIsolationUnit();
    //   
    // Test subquery, aggregation state, view state
    // Test create statement in isolation unit
    //  (1) Annotation - hint but part of statement text
    //  (2) new createEPL method
    //  (3) Change EPServiceProdider to EPServiceProdiderIsolated
    //  (4) add createEPL method to EPRuntimeIsolation 
    // update doc: document schedule adjustment, view sharing must be disabled, note on listener delivery/threading, expensive op., Update statements apply to a stream even if the statement is not isolated.

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        configuration.addEventType("SupportBean_A", SupportBean_A.class);
        configuration.getEngineDefaults().getViewResources().setShareViews(false);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testIsolateFilter()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from pattern [every a=SupportBean -> b=SupportBean(string=a.string)]");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertFalse(listener.getAndClearIsInvoked());

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();

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

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testIsolatedSchedule()
    {
        sendTimerUnisolated(100000);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from pattern [every a=SupportBean -> timer:interval(10)]");
        stmt.addListener(listener);

        sendTimerUnisolated(105000);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
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

        epService.getEPAdministrator().destroyAllStatements();
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
        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
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

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testIsolateMultiple()
    {
        String[] fields = new String[] {"string", "sumi"};
        int count = 4;
        SupportUpdateListener[] listeners = new SupportUpdateListener[count];
        for (int i = 0; i < count; i++)
        {
            String epl = "@Name('S" + i + "') select string, sum(intPrimitive) as sumi from SupportBean(string='" + i + "').win:time(10)";
            listeners[i] = new SupportUpdateListener();
            epService.getEPAdministrator().createEPL(epl).addListener(listeners[i]);
        }

        EPStatement[] statements = new EPStatement[2];
        statements[0] = epService.getEPAdministrator().getStatement("S0");
        statements[1] = epService.getEPAdministrator().getStatement("S2");

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
        unit.takeStatement(statements);

        // send to unisolated
        for (int i = 0; i < count; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean(Integer.toString(i), i));
        }
        assertFalse(listeners[0].isInvoked());
        assertFalse(listeners[2].isInvoked());
        ArrayAssertionUtil.assertProps(listeners[1].assertOneGetNewAndReset(), fields, new Object[] {"1", 1});
        ArrayAssertionUtil.assertProps(listeners[3].assertOneGetNewAndReset(), fields, new Object[] {"3", 3});
        
        // send to isolated
        for (int i = 0; i < count; i++)
        {
            unit.sendEvent(new SupportBean(Integer.toString(i), i));
        }
        assertFalse(listeners[1].isInvoked());
        assertFalse(listeners[3].isInvoked());
        ArrayAssertionUtil.assertProps(listeners[0].assertOneGetNewAndReset(), fields, new Object[] {"0", 0});
        ArrayAssertionUtil.assertProps(listeners[2].assertOneGetNewAndReset(), fields, new Object[] {"2", 2});

        unit.sendEvent(new SupportBean(Integer.toString(2), 2));
        ArrayAssertionUtil.assertProps(listeners[2].assertOneGetNewAndReset(), fields, new Object[] {"2", 4});

        // return
        unit.returnStatement(statements);

        // send to unisolated
        for (int i = 0; i < count; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean(Integer.toString(i), i));
        }
        ArrayAssertionUtil.assertProps(listeners[0].assertOneGetNewAndReset(), fields, new Object[] {"0", 0});
        ArrayAssertionUtil.assertProps(listeners[1].assertOneGetNewAndReset(), fields, new Object[] {"1", 2});
        ArrayAssertionUtil.assertProps(listeners[2].assertOneGetNewAndReset(), fields, new Object[] {"2", 6});
        ArrayAssertionUtil.assertProps(listeners[3].assertOneGetNewAndReset(), fields, new Object[] {"3", 6});

        // send to isolated
        for (int i = 0; i < count; i++)
        {
            unit.sendEvent(new SupportBean(Integer.toString(i), i));
            assertFalse(listeners[i].isInvoked());
        }

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testStartStop()
    {
        String[] fields = new String[] {"string"};
        String epl = "select string from SupportBean.win:time(60)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
        unit.takeStatement(stmt);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        unit.sendEvent(new SupportBean("E2", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E2"}});

        stmt.stop();

        unit.returnStatement(stmt);

        stmt.start();
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 0));
        unit.sendEvent(new SupportBean("E4", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E3"}});

        unit.takeStatement(stmt);

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 0));
        unit.sendEvent(new SupportBean("E6", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E3"}, {"E6"}});

        unit.returnStatement(stmt);

        epService.getEPRuntime().sendEvent(new SupportBean("E7", 0));
        unit.sendEvent(new SupportBean("E8", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E3"}, {"E6"}, {"E7"}});

        stmt.stop();

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testNamedWindowTakeCreate()
    {
        String[] fields = new String[] {"string"};
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL("@Name('create') create window MyWindow.win:keepall() as SupportBean");
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("@Name('insert') insert into MyWindow select * from SupportBean");
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL("@Name('delete') on SupportBean_A delete from MyWindow where string = id");
        EPStatement stmtConsume = epService.getEPAdministrator().createEPL("@Name('consume') select irstream * from MyWindow");
        stmtConsume.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
        unit.takeStatement(epService.getEPAdministrator().getStatement("create"));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));
        unit.sendEvent(new SupportBean("E3", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}});
        assertFalse(listener.isInvoked());

        unit.takeStatement(stmtInsert);

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 0));
        unit.sendEvent(new SupportBean("E5", 0));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}, {"E5"}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5"});    // yes receives it

        // Note: Named window is global across isolation units: they are a relation and not a stream.
        
        // The insert-into to a named window is a stream that can be isolated from the named window.
        // The streams of on-select and on-delete can be isolated, however they select or change the named window even if that is isolated.
        // Consumers to a named window always receive all changes to a named window (regardless of whether the consuming statement is isolated or not), even if the window itself was isolated.
        //
        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E5"}});

        unit.takeStatement(stmtDelete);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E5"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E5"}});

        unit.sendEvent(new SupportBean_A("E5"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, null);

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testNamedWindowTimeCatchup()
    {
        sendTimerUnisolated(100000);
        String[] fields = new String[] {"string"};
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL("@Name('create') create window MyWindow.win:time(10) as SupportBean");
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("@Name('insert') insert into MyWindow select * from SupportBean");

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
        sendTimerIso(0, unit);
        unit.takeStatement(new EPStatement[] {stmtCreate, stmtInsert});

        sendTimerIso(1000, unit);
        unit.sendEvent(new SupportBean("E1", 1));

        sendTimerIso(2000, unit);
        unit.sendEvent(new SupportBean("E2", 2));

        sendTimerIso(9000, unit);
        unit.sendEvent(new SupportBean("E3", 3));

        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E1"}, {"E2"}, {"E3"}});
        unit.returnStatement(new EPStatement[] {stmtCreate});

        sendTimerUnisolated(101000);    // equivalent to 10000  (E3 is 1 seconds old)

        sendTimerUnisolated(102000);    // equivalent to 11000  (E3 is 2 seconds old)
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E2"}, {"E3"}});

        sendTimerUnisolated(103000);    // equivalent to 12000  (E3 is 3 seconds old)
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3"}});

        sendTimerUnisolated(109000);    // equivalent to 18000 (E3 is 9 seconds old)
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3"}});

        unit.takeStatement(new EPStatement[] {stmtCreate});

        sendTimerIso(9999, unit);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, new Object[][] {{"E3"}});

        sendTimerIso(10000, unit);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.iterator(), fields, null);

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testCurrentTimestamp()
    {
        sendTimerUnisolated(5000);
        String[] fields = new String[] {"ct"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select current_timestamp() as ct from SupportBean");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {5000L});

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
        sendTimerIso(100000, unit);
        unit.takeStatement(new EPStatement[] {stmt});

        unit.sendEvent(new SupportBean());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {100000L});        

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testUpdate()
    {
        sendTimerUnisolated(5000);
        String[] fields = new String[] {"string"};
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into NewStream select * from SupportBean");
        EPStatement stmtUpd = epService.getEPAdministrator().createEPL("update istream NewStream set string = 'X'");
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from NewStream");
        stmtSelect.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"X"});

        EPRuntimeIsolated unit = epService.getEPRuntimeIsolated();
        unit.takeStatement(new EPStatement[] {stmtSelect});

        unit.sendEvent(new SupportBean());
        assertFalse(listener.isInvoked());

        /**
         * Update statements apply to a stream even if the statement is not isolated.
         */
        unit.takeStatement(new EPStatement[] {stmtInsert});
        unit.sendEvent(new SupportBean("E1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"X"});

        unit.takeStatement(stmtUpd);
        unit.sendEvent(new SupportBean("E2", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"X"});

        stmtUpd.stop();

        unit.sendEvent(new SupportBean("E3", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3"});

        epService.getEPAdministrator().destroyAllStatements();
    }

    private void sendTimerUnisolated(long millis){
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(millis));
    }

    private void sendTimerIso(long millis, EPRuntimeIsolated unit){
        unit.sendEvent(new CurrentTimeEvent(millis));
    }
}
