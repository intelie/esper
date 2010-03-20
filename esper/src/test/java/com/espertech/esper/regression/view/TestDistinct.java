package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.soda.SelectClause;
import com.espertech.esper.client.soda.FromClause;
import com.espertech.esper.client.soda.FilterStream;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_N;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.SupportSubscriber;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestDistinct extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_N", SupportBean_N.class);
    }

    public void testWildcardJoinPattern() {
        String epl = "select distinct * from " +
                "SupportBean(intPrimitive=0) as fooB unidirectional " +
                "inner join " +
                "pattern [" +
                "every-distinct(fooA.string) fooA=SupportBean(intPrimitive=1)" +
                "->" +
                "every-distinct(wooA.string) wooA=SupportBean(intPrimitive=2)" +
                " where timer:within(1 hour)" +
                "].win:time(1 hour) as fooWooPair " +
                "on fooB.longPrimitive = fooWooPair.fooA.longPrimitive";

        SupportSubscriber subs = new SupportSubscriber();
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        sendEvent("E1", 1, 10L);
        sendEvent("E1", 2, 10L);

        sendEvent("E2", 1, 10L);
        sendEvent("E2", 2, 10L);

        sendEvent("E3", 1, 10L);
        sendEvent("E3", 2, 10L);

        sendEvent("Query", 0, 10L);
        assertTrue(listener.isInvoked());
    }

    private void sendEvent(String string, int intPrimitive, long longPrimitive) {
        SupportBean bean = new SupportBean(string, intPrimitive);
        bean.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    public void testOnDemandAndOnSelect()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        
        String query = "select distinct string, intPrimitive from MyWindow order by string, intPrimitive";
        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery(query);
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", 1}, {"E1", 2}, {"E2", 2}});

        EPStatement stmt = epService.getEPAdministrator().createEPL("on SupportBean_A select distinct string, intPrimitive from MyWindow order by string, intPrimitive asc");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("x"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E1", 2}, {"E2", 2}});
    }

    public void testSubquery()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean where string in (select distinct id from SupportBean_A.win:keepall())");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 2});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 3});
    }

    // Since the "this" property will always be unique, this test verifies that condition
    public void testBeanEventWildcardThisProperty()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "select distinct * from SupportBean.win:keepall()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E1", 1}});
    }

    public void testBeanEventWildcardSODA()
    {
        String[] fields = new String[] {"id"};
        String statementText = "select distinct * from SupportBean_A.win:keepall()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1"}, {"E2"}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1"}, {"E2"}});
        
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(statementText);
        assertEquals(statementText, model.toEPL());

        model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard().distinct(true));
        model.setFromClause(FromClause.create(FilterStream.create("SupportBean_A")));
        assertEquals("select distinct * from SupportBean_A", model.toEPL());
    }

    public void testBeanEventWildcardPlusCols()
    {
        String[] fields = new String[] {"intPrimitive", "val1", "val2"};
        String statementText = "select distinct *, intBoxed%5 as val1, intBoxed as val2 from SupportBean_N.win:keepall()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_N(1, 8));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, 3, 8}});

        epService.getEPRuntime().sendEvent(new SupportBean_N(1, 3));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, 3, 8}, {1, 3, 3}});

        epService.getEPRuntime().sendEvent(new SupportBean_N(1, 8));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, 3, 8}, {1, 3, 3}});
    }

    public void testMapEventWildcard()
    {
        Map<String, Object> def = new HashMap<String, Object>();
        def.put("k1", String.class);
        def.put("v1", int.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMapType", def);

        String[] fields = new String[] {"k1", "v1"};
        String statementText = "select distinct * from MyMapType.win:keepall()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        sendMapEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});

        sendMapEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        sendMapEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testOutputSimpleColumn()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "select distinct string, intPrimitive from SupportBean.win:keepall()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        runAssertionSimpleColumn(stmt, fields);
        stmt.destroy();
        
        // test join
        statementText = "select distinct string, intPrimitive from SupportBean.win:keepall() a, SupportBean_A.win:keepall() b where a.string = b.id";
        stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        runAssertionSimpleColumn(stmt, fields);
    }

    public void testOutputLimitEveryColumn()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "select distinct string, intPrimitive from SupportBean output every 3 events";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        runAssertionOutputEvery(stmt, fields);
        stmt.destroy();

        // test join
        statementText = "select distinct string, intPrimitive from SupportBean.std:lastevent() a, SupportBean_A.win:keepall() b where a.string = b.id output every 3 events";
        stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        runAssertionOutputEvery(stmt, fields);
    }

    public void testOutputRateSnapshotColumn()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "select distinct string, intPrimitive from SupportBean.win:keepall() output snapshot every 3 events order by string asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        runAssertionSnapshotColumn(stmt, fields);
        stmt.destroy();
        
        statementText = "select distinct string, intPrimitive from SupportBean.win:keepall() a, SupportBean_A.win:keepall() b where a.string = b.id output snapshot every 3 events order by string asc";
        stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E3"));
        runAssertionSnapshotColumn(stmt, fields);
    }

    public void testBatchWindow()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "select distinct string, intPrimitive from SupportBean.win:length_batch(3)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E2", 2}, {"E1", 1}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E2", 3}});
    }

    public void testBatchWindowJoin()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "select distinct string, intPrimitive from SupportBean.win:length_batch(3) a, SupportBean_A.win:keepall() b where a.string = b.id";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E2", 2}, {"E1", 1}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E2", 3}});
    }

    public void testBatchWindowInsertInto()
    {
        String[] fields = new String[] {"string", "intPrimitive"};
        String statementText = "insert into MyStream select distinct string, intPrimitive from SupportBean.win:length_batch(3)";
        epService.getEPAdministrator().createEPL(statementText);

        statementText = "select * from MyStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.getNewDataListFlattened()[0], fields, new Object[] {"E2", 2});
        ArrayAssertionUtil.assertProps(listener.getNewDataListFlattened()[1], fields, new Object[] {"E3", 3});
    }

    private void runAssertionOutputEvery(EPStatement stmt, String[] fields)
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E2", 2}, {"E1", 1}});
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E2", 3}});
        listener.reset();
    }

    private void runAssertionSimpleColumn(EPStatement stmt, String[] fields)
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 1}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 1}, {"E1", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 2});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 1}, {"E1", 2}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 1}, {"E1", 2}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 1}, {"E1", 2}, {"E2", 2}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});
    }

    private void runAssertionSnapshotColumn(EPStatement stmt, String[] fields)
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}});
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 1}, {"E2", 2}, {"E3", 3}});
        listener.reset();
    }

    private void sendMapEvent(String s, int i)
    {
        Map<String, Object> def = new HashMap<String, Object>();
        def.put("k1", s);
        def.put("v1", i);
        epService.getEPRuntime().sendEvent(def, "MyMapType");
    }
}