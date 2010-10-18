package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRowPatternRecognitionDelete extends TestCase {

    private static final Log log = LogFactory.getLog(TestRowPatternRecognitionDelete.class);

    // This test is for
    //  (a) on-delete of events from a named window
    //  (b) a sorted window which also posts a remove stream that is out-of-order
    // ... also termed Out-Of-Sequence Delete (OOSD).
    //
    // The test is for out-of-sequence (and in-sequence) deletes:
    //  (1) Make sure that partial pattern matches get removed
    //  (2) Make sure that PREV is handled by order-of-arrival, and is not affected (by default) by delete (versus normal ordered remove stream).
    //      Since it is impossible to make guarantees as the named window could be entirely deleted, and "prev" depth is therefore unknown.
    //
    // Prev
    //    has OOSD
    //      update          PREV operates on original order-of-arrival; OOSD impacts matching: resequence only when partial matches deleted
    //      iterate         PREV operates on original order-of-arrival; OOSD impacts matching: iterator may present unseen-before matches after delete
    //    no OOSD
    //      update          PREV operates on original order-of-arrival; no resequencing when in-order deleted
    //      iterate         PREV operates on original order-of-arrival
    // No-Prev
    //    has OOSD
    //      update
    //      iterate
    //    no OOSD
    //      update
    //      iterate

    public void testNamedWindowOnDeleteOutOfSeq()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        config.addEventType("MyDeleteEvent", SupportBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        epService.getEPAdministrator().createEPL("create window MyNamedWindow.win:keepall() as MyEvent");
        epService.getEPAdministrator().createEPL("insert into MyNamedWindow select * from MyEvent");
        epService.getEPAdministrator().createEPL("on MyDeleteEvent as d delete from MyNamedWindow w where d.intPrimitive = w.value");

        String[] fields = "a_string,b_string".split(",");
        String text = "select * from MyNamedWindow " +
                "match_recognize (" +
                "  measures A.string as a_string, B.string as b_string" +
                "  all matches pattern (A B) " +
                "  define " +
                "    A as PREV(A.string, 3) = 'P3' and PREV(A.string, 2) = 'P2' and PREV(A.string, 4) = 'P4'," +
                "    B as B.value in (PREV(B.value, 4), PREV(B.value, 2))" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("P2", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P1", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P3", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P4", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P2", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 3));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("P4", 11));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P3", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P2", 13));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("xx", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", -4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 12));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E2", "E3"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("P4", 21));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P3", 22));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("P2", 23));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("xx", -2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", -1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", -2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E5", "E6"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E5", "E6"}});

        // delete an PREV-referenced event: no effect as PREV is an order-of-arrival operator
        epService.getEPRuntime().sendEvent(new SupportBean("D1", 21));      // delete P4 of second batch
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E5", "E6"}});

        // delete an partial-match event
        epService.getEPRuntime().sendEvent(new SupportBean("D2", -1));      // delete E5 of second batch
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}});

        epService.getEPRuntime().sendEvent(new SupportBean("D3", 12));      // delete P3 and E3 of first batch
        assertFalse(stmt.iterator().hasNext());
    }

    public void testNamedWindowOutOfSequenceDelete()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportRecogBean", SupportRecogBean.class);
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportRecogBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportRecogBean");
        epService.getEPAdministrator().createEPL("on SupportBean as s delete from MyWindow as w where s.string = w.string");

        String[] fields = "a0,a1,b0,b1,c".split(",");
        String text = "select * from MyWindow " +
                "match_recognize (" +
                "  measures A[0].string as a0, A[1].string as a1, B[0].string as b0, B[1].string as b1, C.string as c" +
                "  pattern ( A+ B* C ) " +
                "  define " +
                "    A as (A.value = 1)," +
                "    B as (B.value = 2)," +
                "    C as (C.value = 3)" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));       // deletes E2
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E1", null, null, null, "E3"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E1", null, null, null, "E3"}});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));       // deletes E1
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 0));       // deletes E4

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 0));       // deletes E4
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E5", null, null, null, "E6"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", null, null, null, "E6"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E9", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E10", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E11", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E9", 0));       // deletes E9
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E12", 3));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E7", "E8", "E10", "E11", "E12"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", null, null, null, "E6"}, {"E7", "E8", "E10", "E11", "E12"}});    // note interranking among per-event result

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E13", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E14", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E15", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E16", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E14", 0));       // deletes E14
        epService.getEPRuntime().sendEvent(new SupportBean("E15", 0));       // deletes E15
        epService.getEPRuntime().sendEvent(new SupportBean("E16", 0));       // deletes E16
        epService.getEPRuntime().sendEvent(new SupportBean("E13", 0));       // deletes E17
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E18", 3));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", null, null, null, "E6"}, {"E7", "E8", "E10", "E11", "E12"}});    // note interranking among per-event result
    }

    public void testNamedWindowInSequenceDelete()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportRecogBean", SupportRecogBean.class);
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportRecogBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportRecogBean");
        epService.getEPAdministrator().createEPL("on SupportBean as s delete from MyWindow as w where s.string = w.string");

        String[] fields = "a0,a1,b".split(",");
        String text = "select * from MyWindow " +
                "match_recognize (" +
                "  measures A[0].string as a0, A[1].string as a1, B.string as b" +
                "  pattern ( A* B ) " +
                "  define " +
                "    A as (A.value = 1)," +
                "    B as (B.value = 2)" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E2", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));       // deletes E1
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));       // deletes E2
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E3", 3));
        assertFalse(listener.isInvoked());
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E4", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E5", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 0));       // deletes E4
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E6", 1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E5", "E6", "E7"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", "E6", "E7"}});
    }
}