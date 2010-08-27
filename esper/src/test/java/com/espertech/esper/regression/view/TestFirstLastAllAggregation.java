package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestFirstLastAllAggregation extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBean_A", SupportBean_A.class);
        config.addEventType("SupportBean_B", SupportBean_B.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    // SUMMARY
    // ==> Rename "first" to "firstever"
    // ==> Rename "last" to "lastever" (this makes a difference as out-of-order deletes are handled by last only and not by lastever)
    // ==> Add new aggregation function (window) and support for "*" and "a.*" and support for index
    // ==> Leave "nth" as is, document its differences: any expression, always separately managed, circular buffer, out of order remove behavior, similar to last(expr, n_index), never uses stream access instance

    // SYNTAX:
    //  firstever(expression)                                    // formerly "first", not windowed (ever)   // never uses stream access instance
    //  lastever(expression)                                     // formerly "last", not windowed (ever)   // never uses stream access instance
    //  first(*/a.*/stream_expression, [index_expression])       // (now its windowed and supports index)   // always requires stream access instances
    //  last(*/a.*/stream_expression, [index_expression])        // all new,                                // always requires stream access instances to guarantee remove stream correctness
    //  window(*/a.*/stream_expression)                          // all new,                                // always requires stream access instances

    // TODO - Testing - Positive
    // TODO: test match recognize
    // TODO: test cumulative and access-based together, @Hint
    // TODO: test output rate limiting

    // TODO - Testing - Negative
    // TODO: distinct should not matter, invalid when distinct is used
    // TODO: must access properties of the same stream
    // TODO: test invalid: prev not allowed, stream wildcard stream not matched, wildcard used on join, allow expressions as long as properties from same stream
    // TODO: test non-window
    // TODO: no (*, a.*) support for firstever and lastever
    // TODO: subquery (*) must use stream op

    // TODO - Documentation
    // TODO: document performance risk: additional tracking of data window data; batch performance; remove performance when not rolling but ooo delete
    // TODO: document cost only paid once regardless of number of aggregation functions as long as same stream
    // TODO: document comparison to prev: prev+aggregation, since prev is not an aggregation function, produces other results
    // TODO: document Nth index for nth function is an integer and must return a constant value
    // TODO: document DISABLE_RECLAIM_GROUP as a performance tip

    // TODO - Consider
    // TODO: support previous version with prev(1, *)

    public void testSubquery() {
        String epl = "select id, (select window(sb.*) from SupportBean.win:length(2) as sb) as w from SupportBean_A";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        String[] fields = "id,w".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A1", new Object[0]});

        SupportBean beanOne = sendEvent(epService, "E1", 0, 1);
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A2", new Object[] {beanOne}});

        SupportBean beanTwo = sendEvent(epService, "E2", 0, 1);
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A3", new Object[] {beanOne, beanTwo}});

        SupportBean beanThree = sendEvent(epService, "E2", 0, 1);
        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A4", new Object[] {beanTwo, beanThree}});
    }

    public void testTypeAndColNameAndEquivalency() {
        epService.getEPAdministrator().getConfiguration().addImport(SupportStaticMethodLib.class.getName());

        String epl = "select " +
                "first(sa.doublePrimitive + sa.intPrimitive), " +
                "first(sa.intPrimitive), " +
                "window(sa.*), " +
                "last(*) from SupportBean.win:length(2) as sa";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        Object[][] rows = new Object[][] {
                {"first((sa.doublePrimitive+sa.intPrimitive))", Double.class},
                {"first(sa.intPrimitive)", int.class},
                {"window(sa.*)", SupportBean.class},
                {"last(*)", SupportBean.class},
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        stmt.destroy();
        epl = "select " +
                "first(sa.doublePrimitive + sa.intPrimitive) as f1, " +
                "first(sa.intPrimitive) as f2, " +
                "window(sa.*) as w1, " +
                "last(*) as l1 " +
                "from SupportBean.win:length(2) as sa";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionType(false);

        stmt.destroy();
        epl = "select " +
                "first(sa.doublePrimitive + sa.intPrimitive) as f1, " +
                "first(sa.intPrimitive) as f2, " +
                "window(sa.*) as w1, " +
                "last(*) as l1 " +
                "from SupportBean.win:length(2) as sa " +
                "having SupportStaticMethodLib.alwaysTrue({first(sa.doublePrimitive + sa.intPrimitive), " +
                "first(sa.intPrimitive), window(sa.*), last(*)})";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionType(true);
    }

    private void runAssertionType(boolean isCheckStatic) {
        String[] fields = "f1,f2,w1,l1".split(",");

        SupportBean beanOne = sendEvent(epService, "E1", 10d, 100);
        EventBean event = listener.assertOneGetNewAndReset();
        Object[] expected = new Object[] {110d, 100, new Object[] {beanOne}, beanOne};
        ArrayAssertionUtil.assertProps(event, fields, expected);
        if (isCheckStatic) {
            Object[] params = SupportStaticMethodLib.getInvocations().get(0);
            SupportStaticMethodLib.getInvocations().clear();
            ArrayAssertionUtil.assertEqualsExactOrder(params, expected);
        }
    }

    public void testJoin2Access() {
        String epl = "select " +
                "sa.id as ast, " +
                "sb.id as bst, " +
                "first(sa.id) as fas, " +
                "window(sa.id) as was, " +
                "last(sa.id) as las, " +
                "first(sb.id) as fbs, " +
                "window(sb.id) as wbs, " +
                "last(sb.id) as lbs " +
                "from SupportBean_A.win:length(2) as sa, SupportBean_B.win:length(2) as sb " +
                "order by ast, bst";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        String[] fields = "ast,bst,fas,was,las,fbs,wbs,lbs".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A1", "B1", "A1", split("A1"), "A1", "B1", split("B1"), "B1"});

        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {
                        {"A2", "B1", "A1", split("A1,A2"), "A2", "B1", split("B1"), "B1"}
                });

        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {
                        {"A3", "B1", "A2", split("A2,A3"), "A3", "B1", split("B1"), "B1"}
                });

        epService.getEPRuntime().sendEvent(new SupportBean_B("B2"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {
                        {"A2", "B2", "A2", split("A2,A3"), "A3", "B1", split("B1,B2"), "B2"},
                        {"A3", "B2", "A2", split("A2,A3"), "A3", "B1", split("B1,B2"), "B2"}
                });

        epService.getEPRuntime().sendEvent(new SupportBean_B("B3"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {
                        {"A2", "B3", "A2", split("A2,A3"), "A3", "B2", split("B2,B3"), "B3"},
                        {"A3", "B3", "A2", split("A2,A3"), "A3", "B2", split("B2,B3"), "B3"}
                });

        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {
                        {"A4", "B2", "A3", split("A3,A4"), "A4", "B2", split("B2,B3"), "B3"},
                        {"A4", "B3", "A3", split("A3,A4"), "A4", "B2", split("B2,B3"), "B3"}
                });
    }

    public void testOuterJoin1Access() {
        epService.getEPAdministrator().getConfiguration().addEventType("S0", SupportBean_S0.class);
        epService.getEPAdministrator().getConfiguration().addEventType("S1", SupportBean_S1.class);
        String epl = "select " +
                "sa.id as aid, " +
                "sb.id as bid, " +
                "first(sb.p10) as fb, " +
                "window(sb.p10) as wb, " +
                "last(sb.p10) as lb " +
                "from S0.win:keepall() as sa " +
                "left outer join " +
                "S1.win:keepall() as sb " +
                "on sa.id = sb.id";        
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        String[] fields = "aid,bid,fb,wb,lb".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {1, null, null, split(null), null});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {1, 1, "A", split("A"), "A"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "B"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "A"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {2, 2, "A", split("A,B"), "B"});

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "C"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "C"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields,
                new Object[] {3, 3, "A", split("A,B,C"), "C"});
    }

    public void testBatchWindow()
    {
        String epl = "select irstream " +
                "first(string) as fs, " +
                "window(string) as ws, " +
                "last(string) as ls " +
                "from SupportBean.win:length_batch(2) as sb";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        String[] fields = "fs,ws,ls".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {null, split(null), null});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E1", split("E1,E2"), "E2"});
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E1", split("E1,E2"), "E2"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E3", split("E3,E4"), "E4"});
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E6", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetOld(), fields, new Object[] {"E3", split("E3,E4"), "E4"});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNew(), fields, new Object[] {"E5", split("E5,E6"), "E6"});
        listener.reset();
    }

    public void testBatchWindowGrouped()
    {
        String epl = "select " +
                "string, " +
                "first(intPrimitive) as fi, " +
                "window(intPrimitive) as wi, " +
                "last(intPrimitive) as li " +
                "from SupportBean.win:length_batch(6) as sb group by string order by string asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        String[] fields = "string,fi,wi,li".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 30));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 31));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 12));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {
                {"E1", 10, intArray(10,11,12), 12},
                {"E2", 20, intArray(20), 20},
                {"E3", 30, intArray(30,31), 31}
        });

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 13));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 14));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 15));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 16));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 17));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 18));
        EventBean[] result = listener.getAndResetLastNewData();
        ArrayAssertionUtil.assertPropsPerRow(result, fields, new Object[][] {
                {"E1", 13, intArray(13,14,15,16,17,18), 18},
                {"E2", null, intArray(null), null},
                {"E3", null, intArray(null), null}
        });
    }

    public void testLateInitialize()
    {
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));

        String[] fields = "firststring,windowstring,laststring".split(",");
        String epl = "select " +
                "first(string) as firststring, " +
                "window(string) as windowstring, " +
                "last(string) as laststring " +
                "from MyWindow";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 30));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", split("E1,E2,E3"), "E3"});
    }

    public void testOnDelete()
    {
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");
        epService.getEPAdministrator().createEPL("on SupportBean_A delete from MyWindow where string = id");

        String[] fields = "firststring,windowstring,laststring".split(",");
        String epl = "select " +
                "first(string) as firststring, " +
                "window(string) as windowstring, " +
                "last(string) as laststring " +
                "from MyWindow";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", split("E1"), "E1"});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", split("E1,E2"), "E2"});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 30));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", split("E1,E2,E3"), "E3"});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", split("E1,E3"), "E3"});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", split("E1"), "E1"});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, split(null), null});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 40));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4", split("E4"), "E4"});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 50));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4", split("E4,E5"), "E5"});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5", split("E5"), "E5"});

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 60));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5", split("E5,E6"), "E6"});
    }

    public void testOnDemandQuery()
    {
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 30));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 31));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 12));

        EPOnDemandPreparedQuery q = epService.getEPRuntime().prepareQuery("select first(intPrimitive) as f, window(intPrimitive) as w, last(intPrimitive) as l from MyWindow as s");
        ArrayAssertionUtil.assertPropsPerRow(q.execute().getArray(), "f,w,l".split(","),
                new Object[][] {{10, intArray(10, 20, 30, 31, 11, 12), 12}});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 13));
        ArrayAssertionUtil.assertPropsPerRow(q.execute().getArray(), "f,w,l".split(","),
                new Object[][] {{10, intArray(10, 20, 30, 31, 11, 12, 13), 13}});

        q = epService.getEPRuntime().prepareQuery("select string as s, first(intPrimitive) as f, window(intPrimitive) as w, last(intPrimitive) as l from MyWindow as s group by string order by string asc");
        Object[][] expected = new Object[][] {
                        {"E1", 10, intArray(10, 11, 12, 13), 13},
                        {"E2", 20, intArray(20), 20},
                        {"E3", 30, intArray(30, 31), 31}
                };
        ArrayAssertionUtil.assertPropsPerRow(q.execute().getArray(), "s,f,w,l".split(","), expected);
        ArrayAssertionUtil.assertPropsPerRow(q.execute().getArray(), "s,f,w,l".split(","), expected);
    }

    public void testStar()
    {
        String epl = "select " +
                    "first(*) as firststar, " +
                    "first(sb.*) as firststarsb, " +
                    "last(*) as laststar, " +
                    "last(sb.*) as laststarsb, " +
                    "window(*) as windowstar, " +
                    "window(sb.*) as windowstarsb " +
                    "from SupportBean.win:length(2) as sb";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionStar();
        stmt.destroy();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());

        runAssertionStar();
    }

    private void runAssertionStar() {
        String[] fields = "firststar,firststarsb,laststar,laststarsb,windowstar,windowstarsb".split(",");

        Object beanE1 = new SupportBean("E1", 10);
        epService.getEPRuntime().sendEvent(beanE1);
        Object[] window = new Object[] {beanE1};
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {beanE1, beanE1, beanE1, beanE1, window, window});

        Object beanE2 = new SupportBean("E2", 20);
        epService.getEPRuntime().sendEvent(beanE2);
        window = new Object[] {beanE1, beanE2};
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {beanE1, beanE1, beanE2, beanE2, window, window});

        Object beanE3 = new SupportBean("E3", 30);
        epService.getEPRuntime().sendEvent(beanE3);
        window = new Object[] {beanE2, beanE3};
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {beanE2, beanE2, beanE3, beanE3, window, window});
    }

    public void testWindowedUnGrouped()
    {
        String epl = "select " +
                "first(string) as firststring, " +
                "last(string) as laststring, " +
                "first(intPrimitive) as firstint, " +
                "last(intPrimitive) as lastint, " +
                "window(intPrimitive) as allint " +
                "from SupportBean.win:length(2)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionUngrouped();

        stmt.destroy();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());

        runAssertionUngrouped();
    }

    public void testWindowedGrouped()
    {
        String epl = "select " +
                "string, " +
                "first(string) as firststring, " +
                "last(string) as laststring, " +
                "first(intPrimitive) as firstint, " +
                "last(intPrimitive) as lastint, " +
                "window(intPrimitive) as allint " +
                "from SupportBean.win:length(5) " +
                "group by string order by string";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionGrouped();

        stmt.destroy();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());

        runAssertionGrouped();
    }

    private void runAssertionGrouped() {
        String[] fields = "string,firststring,firstint,laststring,lastint,allint".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1", 10, "E1", 10, new int[] {10}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", "E2", 11, "E2", 11, new int[] {11}});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 12));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1", 10, "E1", 12, new int[] {10, 12}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 13));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", "E2", 11, "E2", 13, new int[] {11, 13}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 14));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", "E2", 11, "E2", 14, new int[] {11, 13, 14}});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 15));  // push out E1/10
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1", 12, "E1", 15, new int[] {12, 15}});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 16));  // push out E2/11 --> 2 events
        EventBean[] received = listener.getAndResetLastNewData();
        ArrayAssertionUtil.assertPropsPerRow(received, fields,
                new Object[][] {
                        new Object[] {"E1", "E1", 12, "E1", 16, new int[] {12, 15, 16}},
                        new Object[] {"E2", "E2", 13, "E2", 14, new int[] {13, 14}}
                        });
    }

    private void runAssertionUngrouped() {
        String[] fields = "firststring,firstint,laststring,lastint,allint".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10, "E1", 10, new int[] {10}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10, "E2", 11, new int[] {10,11}});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 12));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 11, "E3", 12, new int[] {11,12}});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 13));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 12, "E4", 13, new int[] {12,13}});
    }

    private Object split(String s)
    {
        if (s == null) {
            return new Object[0];
        }
        return s.split(",");
    }

    private int[] intArray(int ...value)
    {
        if (value == null) {
            return new int[0];
        }
        return value;
    }

    private SupportBean sendEvent(EPServiceProvider epService, String string, double doublePrimitive, int intPrimitive) {
        SupportBean bean = new SupportBean(string, intPrimitive);
        bean.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

}