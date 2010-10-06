package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestDataWindowIntersectExpiry extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testUniqueAndFirstLength()
    {
        init(false);

        String epl = "select irstream string, intPrimitive from SupportBean.win:firstlength(3).std:unique(string)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionUniqueAndFirstLength(stmt);

        stmt.destroy();
        listener.reset();
        
        epl = "select irstream string, intPrimitive from SupportBean.std:unique(string).win:firstlength(3)";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionUniqueAndFirstLength(stmt);
    }

    private void runAssertionUniqueAndFirstLength(EPStatement stmt)
    {
        String[] fields = new String[] {"string", "intPrimitive"};

        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});

        sendEvent("E1", 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"E1", 3});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"E1", 1});
        listener.reset();

        sendEvent("E3", 30);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}, {"E3", 30}});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"E3", 30});
        listener.reset();

        sendEvent("E4", 40);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}, {"E3", 30}});
        assertFalse(listener.isInvoked());
    }

    public void testFirstUniqueAndFirstLength()
    {
        init(false);

        String epl = "select irstream string, intPrimitive from SupportBean.std:firstunique(string).win:firstlength(3)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionFirstUniqueAndLength(stmt);

        stmt.destroy();
        epl = "select irstream string, intPrimitive from SupportBean.win:firstlength(3).std:firstunique(string)";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionFirstUniqueAndLength(stmt);
    }

    public void testFirstUniqueAndLengthOnDelete()
    {
        init(false);

        EPStatement nwstmt = epService.getEPAdministrator().createEPL("create window MyWindow.std:firstunique(string).win:firstlength(3) as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("on SupportBean_S0 delete from MyWindow where string = p00");

        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream * from MyWindow");
        stmt.addListener(listener);

        String[] fields = new String[] {"string", "intPrimitive"};

        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        sendEvent("E1", 99);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 1}});
        assertFalse(listener.isInvoked());

        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});
        
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1});

        sendEvent("E1", 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 3});

        sendEvent("E1", 99);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}});
        assertFalse(listener.isInvoked());

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});

        sendEvent("E3", 98);
        ArrayAssertionUtil.assertEqualsAnyOrder(nwstmt.iterator(), fields, new Object[][] {{"E1", 3}, {"E2", 2}, {"E3", 3}});
        assertFalse(listener.isInvoked());
    }

    private void runAssertionFirstUniqueAndLength(EPStatement stmt) {

        String[] fields = new String[] {"string", "intPrimitive"};

        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});

        sendEvent("E2", 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        assertFalse(listener.isInvoked());

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 3});

        sendEvent("E4", 4);
        sendEvent("E4", 5);
        sendEvent("E5", 5);
        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        assertFalse(listener.isInvoked());
    }

    public void testBatchWindow()
    {
        init(false);
        String[] fields = new String[] {"string"};

        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string from SupportBean.win:length_batch(3).std:unique(intPrimitive) order by string asc");
        stmt.addListener(listener);

        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3"});

        sendEvent("E4", 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});

        sendEvent("E5", 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E5"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5"});
        listener.reset();

        sendEvent("E6", 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E6"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E5"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E6"});
        listener.reset();

        sendEvent("E7", 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E6", "E7"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E7"});
        listener.reset();

        sendEvent("E8", 6);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][] {{"E1"}, {"E2"}, {"E3"}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E8"});
        listener.reset();
    }

    public void testIntersectAndDerivedValue()
    {
        init(false);
        String[] fields = new String[] {"total"};

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.std:unique(intPrimitive).std:unique(intBoxed).stat:uni(doublePrimitive)");
        stmt.addListener(listener);

        sendEvent("E1", 1, 10, 100d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr(100d));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {100d});

        sendEvent("E2", 2, 20, 50d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr(150d));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {150d});

        sendEvent("E3", 1, 20, 20d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr(20d));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {20d});
    }

    public void testIntersectGroupBy()
    {
        init(false);
        String[] fields = new String[] {"string"};

        String text = "select irstream string from SupportBean.std:groupwin(intPrimitive).win:length(2).std:unique(intBoxed) retain-intersection";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        sendEvent("E1", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendEvent("E2", 2, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        sendEvent("E3", 1, 20);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2", "E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3"});

        sendEvent("E4", 1, 30);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E3", "E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E4"});
        listener.reset();

        sendEvent("E5", 2, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3", "E4", "E5"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5"});
        listener.reset();

        sendEvent("E6", 1, 20);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E4", "E5", "E6"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E3"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E6"});
        listener.reset();

        sendEvent("E7", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E5", "E6", "E7"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E7"});
        listener.reset();

        sendEvent("E8", 2, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E6", "E7", "E8"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E5"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E8"});
        listener.reset();
    }

    public void testIntersectSubselect()
    {
        init(false);

        String text = "select * from SupportBean_S0 where p00 in (select string from SupportBean.win:length(2).std:unique(intPrimitive) retain-intersection)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        sendEvent("E1", 1);
        sendEvent("E2", 2);
        sendEvent("E3", 3); // throws out E1
        sendEvent("E4", 2); // throws out E2
        sendEvent("E5", 1); // throws out E3

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E1"));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E2"));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E3"));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E4"));
        assertTrue(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E5"));
        assertTrue(listener.getAndClearIsInvoked());
    }

    public void testIntersectThreeUnique()
    {
        init(false);
        String[] fields = new String[] {"string"};

        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string from SupportBean.std:unique(intPrimitive).std:unique(intBoxed).std:unique(doublePrimitive) retain-intersection");
        stmt.addListener(listener);

        sendEvent("E1", 1, 10, 100d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendEvent("E2", 2, 10, 200d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E2"});
        listener.reset();

        sendEvent("E3", 2, 20, 100d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E3"});
        listener.reset();

        sendEvent("E4", 1, 30, 300d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3", "E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});

        sendEvent("E5", 3, 40, 400d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3", "E4", "E5"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5"});

        sendEvent("E6", 3, 40, 300d);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3", "E6"));
        Object[] result = {listener.getLastOldData()[0].get("string"), listener.getLastOldData()[1].get("string")};
        ArrayAssertionUtil.assertEqualsAnyOrder(result, new String[] {"E4", "E5"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E6"});
        listener.reset();
    }

    public void testIntersectPattern()
    {
        init(false);
        String[] fields = new String[] {"string"};

        String text = "select irstream a.p00||b.p10 as string from pattern [every a=SupportBean_S0 -> b=SupportBean_S1].std:unique(a.id).std:unique(b.id) retain-intersection";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "E2"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1E2"});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(10, "E3"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(20, "E4"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1E2", "E3E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3E4"});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E5"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "E6"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3E4", "E5E6"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E1E2"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5E6"});
    }

    public void testIntersectTwoUnique()
    {
        init(false);
        String[] fields = new String[] {"string"};

        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string from SupportBean.std:unique(intPrimitive).std:unique(intBoxed) retain-intersection");
        stmt.addListener(listener);

        sendEvent("E1", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendEvent("E2", 2, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E2"});
        listener.reset();

        sendEvent("E3", 1, 20);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3"});

        sendEvent("E4", 3, 20);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E3"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E4"});
        listener.reset();

        sendEvent("E5", 2, 30);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E4", "E5"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5"});
        listener.reset();

        sendEvent("E6", 3, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E5", "E6"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E6"});
        listener.reset();

        sendEvent("E7", 3, 30);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E7"));
        assertEquals(2, listener.getLastOldData().length);
        Object[] result = {listener.getLastOldData()[0].get("string"), listener.getLastOldData()[1].get("string")};
        ArrayAssertionUtil.assertEqualsAnyOrder(result, new String[] {"E5", "E6"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E7"});
        listener.reset();

        sendEvent("E8", 4, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E7", "E8"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E8"});

        sendEvent("E9", 3, 50);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E8", "E9"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E7"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E9"});
        listener.reset();

        sendEvent("E10", 2, 50);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E8", "E10"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E9"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E10"});
        listener.reset();
    }

    public void testIntersectSorted()
    {
        init(false);
        String[] fields = new String[] {"string"};

        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string from SupportBean.ext:sort(2, intPrimitive).ext:sort(2, intBoxed) retain-intersection");
        stmt.addListener(listener);

        sendEvent("E1", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendEvent("E2", 2, 9);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        sendEvent("E3", 0, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3"));
        Object[] result = {listener.getLastOldData()[0].get("string"), listener.getLastOldData()[1].get("string")};
        ArrayAssertionUtil.assertEqualsAnyOrder(result, new String[] {"E1", "E2"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E3"});
        listener.reset();

        sendEvent("E4", -1, -1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3", "E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});

        sendEvent("E5", 1, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3", "E4"));
        assertEquals(1, listener.getLastOldData().length);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E5"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5"});
        listener.reset();

        sendEvent("E6", 0, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E4", "E6"));
        assertEquals(1, listener.getLastOldData().length);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E3"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E6"});
        listener.reset();
    }

    public void testIntersectTimeWin()
    {
        init(false);

        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string from SupportBean.std:unique(intPrimitive).win:time(10 sec) retain-intersection");
        stmt.addListener(listener);

        runAssertionTimeWinUnique(stmt);
    }

    public void testIntersectTimeWinReversed()
    {
        init(false);

        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string from SupportBean.win:time(10 sec).std:unique(intPrimitive) retain-intersection");
        stmt.addListener(listener);

        runAssertionTimeWinUnique(stmt);
    }

    public void testIntersectTimeWinSODA()
    {
        init(false);

        sendTimer(0);
        String stmtText = "select irstream string from SupportBean.win:time(10 seconds).std:unique(intPrimitive) retain-intersection";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());
        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        runAssertionTimeWinUnique(stmt);
    }

    public void testIntersectTimeWinNamedWindow()
    {
        init(false);

        sendTimer(0);
        EPStatement stmtWindow = epService.getEPAdministrator().createEPL("create window MyWindow.win:time(10 sec).std:unique(intPrimitive) retain-intersection as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("on SupportBean_S0 delete from MyWindow where intBoxed = id");
        stmtWindow.addListener(listener);

        runAssertionTimeWinUnique(stmtWindow);
    }

    public void testIntersectTimeWinNamedWindowDelete()
    {
        init(false);

        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL("create window MyWindow.win:time(10 sec).std:unique(intPrimitive) retain-intersection as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("on SupportBean_S0 delete from MyWindow where intBoxed = id");
        stmt.addListener(listener);

        String[] fields = new String[] {"string"};

        sendTimer(1000);
        sendEvent("E1", 1, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendTimer(2000);
        sendEvent("E2", 2, 20);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(20));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));

        sendTimer(3000);
        sendEvent("E3", 3, 30);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3"});
        sendEvent("E4", 3, 40);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E3"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E4"});
        listener.reset();

        sendTimer(4000);
        sendEvent("E5", 4, 50);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E4", "E5"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5"});
        sendEvent("E6", 4, 50);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E4", "E6"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E5"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E6"});
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean_S0(20));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E4", "E6"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(50));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E6"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E4"));

        sendTimer(10999);
        assertFalse(listener.isInvoked());
        sendTimer(11000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E4"));

        sendTimer(12999);
        assertFalse(listener.isInvoked());
        sendTimer(13000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr());

        sendTimer(10000000);
        assertFalse(listener.isInvoked());
    }

    private void runAssertionTimeWinUnique(EPStatement stmt)
    {
        String[] fields = new String[] {"string"};

        sendTimer(1000);
        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        sendTimer(2000);
        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E1", "E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        sendTimer(3000);
        sendEvent("E3", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E3"});
        listener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E3"));

        sendTimer(4000);
        sendEvent("E4", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E3", "E4"));
        sendEvent("E5", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E4"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5"});
        listener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E2", "E3", "E5"));

        sendTimer(11999);
        assertFalse(listener.isInvoked());
        sendTimer(12000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E3","E5"));

        sendTimer(12999);
        assertFalse(listener.isInvoked());
        sendTimer(13000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E3"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr("E5"));

        sendTimer(13999);
        assertFalse(listener.isInvoked());
        sendTimer(14000);
        ArrayAssertionUtil.assertProps(listener.assertOneGetOldAndReset(), fields, new Object[] {"E5"});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, toArr());
    }

    private void sendEvent(String string, int intPrimitive, int intBoxed, double doublePrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        bean.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent(String string, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private Object[][] toArr(Object ...values)
    {
        Object[][] arr = new Object[values.length][];
        for (int i = 0; i < values.length; i++)
        {
            arr[i] = new Object[] {values[i]};
        }
        return arr;
    }

    private void tryInvalid(String text, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(text);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void init(boolean isAllowMultipleDataWindows)
    {
        listener = new SupportUpdateListener();

        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(isAllowMultipleDataWindows);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_S0", SupportBean_S0.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_S1", SupportBean_S1.class);
    }
}
