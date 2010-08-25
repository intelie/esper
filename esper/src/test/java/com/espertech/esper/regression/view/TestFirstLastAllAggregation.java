package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
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
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    // SUMMARY
    // ==> Rename "first" to "firstever"
    // ==> Leave "last" as is, enhance as below
    // ==> Add new aggregation function (window) and support for "*" and "a.*" and support for index
    // ==> Leave "nth" as is, document its differences: any expression, always separately managed, circular buffer, out of order remove behavior, similar to last(expr, n_index), never uses stream access instance

    // SYNTAX:
    //  firstever(expression)                                    // formerly "first", not windowed (ever)   // never uses stream access instance
    //  first(*/a.*/stream_expression, [index_expression])       // (now its windowed and supports index)   // always requires stream access instances
    //  last(*/a.*/stream_expression, [index_expression])        // (now supports index)                    // may use stream access instance
    //  window(*/a.*/stream_expression)                          // all new,                                // always requires stream access instances

    // IMPLEMENTATION (1)
    // When only last(expression) appears as an access aggregation function, make it a regular aggregation function (use nth).
    // When "first" or "all" or "last with index" appear provide as access aggregation function. In this case also make "last" backed as an access aggregation function.

    // TODO: EsperHA testing
    // TODO: test with batched window
    // TODO: test remove stream
    // TODO: test out of order remove (named window)

    // TODO - Performance
    // TODO: consider passing rolling-flag so it can be determined whether its worth to have an index
    // TODO; optimization: use the right Collection implementation for following data window
    // TODO: performance of nth and all access

    // TODO - Testing - Positive
    // TODO: test join, second and 4th stream has access semantic
    // TODO: test equalsNodeAggregate semantics i.e. node reuse, equivalency testing
    // TODO: clear result testing, init by named window data
    // TODO: test automatic expression assignment
    // TODO: test match recognize
    // TODO: test having and order by
    // TODO: test fully-grouped versus row-per-group and row-per-event
    // TODO: test join
    // TODO: test subquery
    // TODO: test cumulative and access-based together
    // TODO: test group by
    // TODO: test output rate limiting
    // TODO: test outer join, i.e. null event
    // TODO: test named window with delete
    // TODO: test "select firstw(*) from ABC" outputs the right expression string for the expression resulting bean property (toExpressionString)

    // TODO - Testing - Negative
    // TODO: distinct should not matter, invalid when distinct is used
    // TODO: must access properties of the same stream
    // TODO: test invalid: prev not allowed, stream wildcard stream not matched, wildcard used on join, allow expressions as long as properties from same stream

    // TODO - Documentation
    // TODO: document performance risk: additional tracking of data window data; batch performance; remove performance when not rolling but random
    // TODO: document cost only paid once regardless of number of aggregation functions as long as same stream
    // TODO: document comparison to prev: prev+aggregation, since prev is not an aggregation function, produces other results
    // TODO: document Nth index for nth function is an integer and must return a constant value
    // TODO: document DISABLE_RECLAIM_GROUP as a performance tip

    // TODO - Consider
    // TODO: support previous version with prev(1, *)
    
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
        /**
         * TODO
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());

        runAssertion();
         */
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
                "from SupportBean.win:length(5)" +
                "group by string order by string asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionGrouped();

        stmt.destroy();
        /**
         * TODO
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());

        runAssertion();
         */
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
}
