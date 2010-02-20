package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.rowregex.RegexPartitionStateRepoGroup;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRowPatternRecognitionOps extends TestCase {

    private static final Log log = LogFactory.getLog(TestRowPatternRecognitionOps.class);

    // Out-list
    //  - SUBSET
    //  - ORDER BY
    //  - MATCH_NUMBER
    //  - WINDOW clause is implied
    //  - aggregation support in DEFINE clauses, running aggregates
    //  - ^(begin partition), $(end partition), {n}, {n,}, {n,m}, {,m}, {n,m}?)
    //  - ALL ROWS PER MATCH: one row for each row of each match (is not the default) (then Measure selection can include all properties of EventType) and CLASSIFIER (4 hrs)
    //  - excluding portions of a pattern {-NOTA-}
    //  - PERMUTE
    //  - fire and forget query support
    //  - TO_TIMESTAMP
    //  - AFTER MATCH SKIP TO FIRST/LAST <variable>

    public void testConcatenation()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,b_string".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a_string, B.string as b_string " +
                "  all matches " +
                "  pattern (A B) " +
                "  define B as B.value > A.value" +
                ") " +
                "order by a_string, b_string";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 3));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 6));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E2", "E3"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 4));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 6));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E4", "E5"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E4", "E5"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 10));
        assertFalse(listener.isInvoked());      // E5-E6 not a match since "skip past last row"
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E4", "E5"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 9));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 4));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E4", "E5"}});

        stmt.stop();
    }

    public void testZeroToMany()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,b0_string,b1_string,b2_string,c_string".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a_string, " +
                "    B[0].string as b0_string, " +
                "    B[1].string as b1_string, " +
                "    B[2].string as b2_string, " +
                "    C.string as c_string" +
                "  all matches " +
                "  pattern (A B* C) " +
                "  define \n" +
                "    A as A.value = 10,\n" +
                "    B as B.value > 10,\n" +
                "    C as C.value < 10\n" +
                ") " +
                "order by a_string, c_string";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 10));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 8));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E2", null, null, null, "E3"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, null, null, "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 12));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, null, null, "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 8));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E4", "E5", null, null, "E6"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, null, null, "E3"}, {"E4", "E5", null, null, "E6"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E9", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E10", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E11", 9));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E7", "E8", "E9", "E10", "E11"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, null, null, "E3"}, {"E4", "E5", null, null, "E6"}, {"E7", "E8", "E9", "E10", "E11"}});

        stmt.stop();
    }

    public void testOneToMany()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,b0_string,b1_string,b2_string,c_string".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a_string, " +
                "    B[0].string as b0_string, " +
                "    B[1].string as b1_string, " +
                "    B[2].string as b2_string, " +
                "    C.string as c_string" +
                "  all matches " +
                "  pattern (A B+ C) " +
                "  define \n" +
                "    A as (A.value = 10),\n" +
                "    B as (B.value > 10),\n" +
                "    C as (C.value < 10)\n" +
                ") " +
                "order by a_string, c_string";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 8));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 12));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 8));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E4", "E5", null, null, "E6"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E4", "E5", null, null, "E6"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E9", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E10", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E11", 9));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E7", "E8", "E9", "E10", "E11"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E4", "E5", null, null, "E6"}, {"E7", "E8", "E9", "E10", "E11"}});

        stmt.stop();
    }

    public void testZeroToOne()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,b_string,c_string".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a_string, B.string as b_string, " +
                "    C.string as c_string" +
                "  all matches " +
                "  pattern (A B? C) " +
                "  define \n" +
                "    A as (A.value = 10),\n" +
                "    B as (B.value > 10),\n" +
                "    C as (C.value < 10)\n" +
                ") " +
                "order by a_string";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 10));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 8));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E2", null, "E3"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 12));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 8));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E4", "E5", "E6"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, "E3"}, {"E4", "E5", "E6"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 10));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E9", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E11", 9));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", null, "E3"}, {"E4", "E5", "E6"}});

        stmt.stop();
    }

    public void testPartitionBy()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,a_value,b_value".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  partition by string" +
                "  measures A.string as a_string, A.value as a_value, B.value as b_value " +
                "  all matches pattern (A B) " +
                "  define B as (B.value > A.value)" +
                ")" +
                " order by a_string";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S2", 6));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S3", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S2", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", -1));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 6));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S1", 4, 6}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 10));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S4", -1, 10}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S4", -1, 10}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 11));
        assertFalse(listener.isInvoked());      // since skip past last row
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S4", -1, 10}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S3", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", -2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S3", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 4));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S4", -1, 10}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 7));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S1", 4, 7}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S1", 4, 7}, {"S4", -1, 10}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 12));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S4", -2, 12}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S1", 4, 7}, {"S4", -1, 10}, {"S4", -2, 12}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 7));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S2", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 5));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S2", 5));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S2", 4, 5}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S1", 4, 7}, {"S2", 4, 5}, {"S4", -1, 10}, {"S4", -2, 12}});

        stmt.destroy();
    }

    public void testUnlimitedPartition()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  partition by value" +
                "  measures A.string as a_string " +
                "  pattern (A B) " +
                "  define " +
                "    A as (A.string = 'A')," +
                "    B as (B.string = 'B')" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        for (int i = 0; i < 5 * RegexPartitionStateRepoGroup.INITIAL_COLLECTION_MIN; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportRecogBean("A", i));
            epService.getEPRuntime().sendEvent(new SupportRecogBean("B", i));
            assertTrue(listener.getAndClearIsInvoked());
        }

        for (int i = 0; i < 5 * RegexPartitionStateRepoGroup.INITIAL_COLLECTION_MIN; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportRecogBean("A", i + 100000));
        }
        assertFalse(listener.getAndClearIsInvoked());
        for (int i = 0; i < 5 * RegexPartitionStateRepoGroup.INITIAL_COLLECTION_MIN; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportRecogBean("B", i + 100000));
            assertTrue(listener.getAndClearIsInvoked());
        }
    }

    public void testConcatWithinAlter()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,b_string,c_string,d_string".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a_string, B.string as b_string, C.string as c_string, D.string as d_string " +
                "  all matches pattern ( A B | C D ) " +
                "  define " +
                "    A as (A.value = 1)," +
                "    B as (B.value = 2)," +
                "    C as (C.value = 3)," +
                "    D as (D.value = 4)" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 3));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 4));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{null, null, "E4", "E5"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{null, null, "E4", "E5"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E1", "E2", null, null}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{null, null, "E4", "E5"}, {"E1", "E2", null, null}});

        stmt.stop();
    }

    public void testAlterWithinConcat()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a_string,b_string,c_string,d_string".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a_string, B.string as b_string, C.string as c_string, D.string as d_string " +
                "  all matches pattern ( (A | B) (C | D) ) " +
                "  define " +
                "    A as (A.value = 1)," +
                "    B as (B.value = 2)," +
                "    C as (C.value = 3)," +
                "    D as (D.value = 4)" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 1));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E5", null, "E6", null}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", null, "E6", null}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{null, "E7", "E8", null}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", null, "E6", null}, {null, "E7", "E8", null}});
    }

    public void testVariableMoreThenOnce()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a0,b,a1".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A[0].string as a0, B.string as b, A[1].string as a1 " +
                "  all matches pattern ( A B A ) " +
                "  define " +
                "    A as (A.value = 1)," +
                "    B as (B.value = 2)" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 5));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 2));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 1));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E5", "E6", "E7"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", "E6", "E7"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E9", 1));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", "E6", "E7"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E10", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E11", 1));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E9", "E10", "E11"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", "E6", "E7"}, {"E9", "E10", "E11"}});
    }

    public void testRegex()
    {
        assertTrue("aq".matches("^aq|^id"));
        assertTrue("id".matches("^aq|^id"));
        assertTrue("ad".matches("a(q|i)?d"));
        assertTrue("aqd".matches("a(q|i)?d"));
        assertTrue("aid".matches("a(q|i)?d"));
        assertFalse("aed".matches("a(q|i)?d"));
        assertFalse("a".matches("(a(b?)c)?"));
    }
}
