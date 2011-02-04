package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRowPatternRecognitionAfter extends TestCase {

    private static final Log log = LogFactory.getLog(TestRowPatternRecognitionAfter.class);

    public void testAfterCurrentRow() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                " measures A.string as a, B[0].string as b0, B[1].string as b1" +
                " after match skip to current row" +
                " pattern (A B*)" +
                " define" +
                " A as A.string like \"A%\"," +
                " B as B.string like \"B%\"" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        runAssertion(epService, listener, stmt);
        
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        SerializableObjectCopier.copy(model);
        assertEquals(text, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(text, stmt.getText());

        runAssertion(epService, listener, stmt);
    }

    private void runAssertion(EPServiceProvider epService, SupportUpdateListener listener, EPStatement stmt)
    {
        String[] fields = "a,b0,b1".split(",");

        epService.getEPRuntime().sendEvent(new SupportRecogBean("A1", 1));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"A1", null, null}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"A1", null, null}});

        // since the first match skipped past A, we do not match again
        epService.getEPRuntime().sendEvent(new SupportRecogBean("B1", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"A1", "B1", null}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"A1", "B1", null}});
    }

    public void testAfterNextRow()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "a,b0,b1".split(",");
        String text = "select * from MyEvent.win:keepall() " +
                "match_recognize (" +
                "  measures A.string as a, B[0].string as b0, B[1].string as b1" +
                "  AFTER MATCH SKIP TO NEXT ROW " +
                "  pattern (A B*) " +
                "  define " +
                "    A as A.string like 'A%'," +
                "    B as B.string like 'B%'" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportRecogBean("A1", 1));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"A1", null, null}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"A1", null, null}});

        // since the first match skipped past A, we do not match again
        epService.getEPRuntime().sendEvent(new SupportRecogBean("B1", 2));
        assertFalse(listener.isInvoked());  // incremental skips to next 
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"A1", "B1", null}});
    }

    public void testSkipToNextRow()
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
                "  after match skip to next row " +
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
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E5", "E6"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E4", "E5"}, {"E5", "E6"}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("E7", 9));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("E8", 4));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E2", "E3"}, {"E4", "E5"}, {"E5", "E6"}});

        stmt.stop();
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
                "  all matches " +
                "  after match skip to next row " +
                "  pattern ( A B A ) " +
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
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"E7", "E8", "E9"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"E5", "E6", "E7"}, {"E7", "E8", "E9"}});
    }

    public void testSkipToNextRowPartitioned()
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
                "  all matches " +
                "  after match skip to next row " +
                "  pattern (A B) " +
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
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S4", 10, 11}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S4", -1, 10}, {"S4", 10, 11}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S3", 3));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", -1));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S3", 2));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 4));
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S4", -1, 10}, {"S4", 10, 11}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 7));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S1", 4, 7}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S1", 4, 7}, {"S4", -1, 10}, {"S4", 10, 11}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 12));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S4", -1, 12}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S1", 4, 7}, {"S4", -1, 10}, {"S4", 10, 11}, {"S4", -1, 12}});

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S4", 12));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 7));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S2", 4));
        epService.getEPRuntime().sendEvent(new SupportRecogBean("S1", 5));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportRecogBean("S2", 5));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields,
                new Object[][] {{"S2", 4, 5}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] {{"S1", 4, 6}, {"S1", 4, 7}, {"S2", 4, 5}, {"S4", -1, 10}, {"S4", 10, 11}, {"S4", -1, 12}});

        stmt.destroy();
    }

    public void testAfterSkipPastLast()
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
                "  after match skip past last row" +
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
}