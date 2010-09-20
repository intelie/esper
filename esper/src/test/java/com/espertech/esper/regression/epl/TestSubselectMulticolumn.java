package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Map;

public class TestSubselectMulticolumn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();        
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("S0", SupportBean_S0.class);
        config.addEventType("S1", SupportBean_S1.class);
        config.addEventType("MarketData", SupportMarketDataBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    // TODO test stream select, wildcard
    // TODO test filter spec select expression
    // TODO validate only aggregation or only non-aggregation
    // TODO test non-row functions such as "in"
    // TODO test column alias not duplicates
    // TODO doc subquery returns single row multiple columns
    // TODO doc subquery returns multiple rows
    // TODO doc subquery correlation with aggregation is indexed, ordered delivery

    public void testWildcardAndColumns()
    {
        String stmtText = "select " +
                "(select *, intPrimitive*2 as val2 from SupportBean.std:lastevent()) as subrow " +
                "from S0 as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String[] fields = "subrow.v1,subrow.v2".split(",");

        /*
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, fields, new Object[] {null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20});
        */
    }

    public void testInvalid() {

        // TODO

    }

    private void tryInvalid(String epl, String message) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    public void testColumnsUncorrelated()
    {
        String stmtText = "select " +
                "(select string as v1, intPrimitive as v2 from SupportBean.std:lastevent()) as subrow " +
                "from S0 as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        
        runAssertion(stmt);
        
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(stmtText, stmt.getText());

        runAssertion(stmt);
    }

    private void runAssertion(EPStatement stmt) {

        FragmentEventType fragmentType = stmt.getEventType().getFragmentType("subrow");
        assertFalse(fragmentType.isIndexed());
        assertFalse(fragmentType.isNative());
        Object[][] rows = new Object[][] {
                {"v1", String.class},
                {"v2", int.class},
                };
        for (int i = 0; i < rows.length; i++) {
            String message = "Failed assertion for " + rows[i][0];
            EventPropertyDescriptor prop = fragmentType.getFragmentType().getPropertyDescriptors()[i];
            assertEquals(message, rows[i][0], prop.getPropertyName());
            assertEquals(message, rows[i][1], prop.getPropertyType());
        }

        String[] fields = "subrow.v1,subrow.v2".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, fields, new Object[] {null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20});
    }

    public void testCorrelatedAggregation()
    {
        String stmtText = "select p00, " +
                "(select " +
                "  sum(intPrimitive) as v1, " +
                "  sum(intPrimitive + 1) as v2, " +
                "  window(intPrimitive) as v3, " +
                "  window(sb.*) as v4 " +
                "  from SupportBean.win:keepall() sb " +
                "  where string = s0.p00) as subrow " +
                "from S0 as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        Object[][] rows = new Object[][] {
                {"p00", String.class, false},
                {"subrow", Map.class, true}
                };
        for (int i = 0; i < rows.length; i++) {
            String message = "Failed assertion for " + rows[i][0];
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(message, rows[i][0], prop.getPropertyName());
            assertEquals(message, rows[i][1], prop.getPropertyType());
            assertEquals(message, rows[i][2], prop.isFragment());
        }

        FragmentEventType fragmentType = stmt.getEventType().getFragmentType("subrow");
        assertFalse(fragmentType.isIndexed());
        assertFalse(fragmentType.isNative());
        rows = new Object[][] {
                {"v1", Integer.class},
                {"v2", Integer.class},
                {"v3", int[].class},
                {"v4", SupportBean[].class},
                };
        for (int i = 0; i < rows.length; i++) {
            String message = "Failed assertion for " + rows[i][0];
            EventPropertyDescriptor prop = fragmentType.getFragmentType().getPropertyDescriptors()[i];
            assertEquals(message, rows[i][0], prop.getPropertyName());
            assertEquals(message, rows[i][1], prop.getPropertyType());
        }

        String[] fields = "p00,subrow.v1,subrow.v2".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "T1"));
        EventBean row = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(row, fields, new Object[] {"T1", null, null});
        assertNull(row.get("subrow.v3"));
        assertNull(row.get("subrow.v4"));

        SupportBean sb1 = new SupportBean("T1", 10);
        epService.getEPRuntime().sendEvent(sb1);
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "T1"));
        row = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(row, fields, new Object[] {"T1", 10, 11});
        ArrayAssertionUtil.assertEqualsAnyOrder((int[]) row.get("subrow.v3"), new int[] {10});
        ArrayAssertionUtil.assertEqualsAnyOrder((Object[]) row.get("subrow.v4"), new Object[] {sb1});

        SupportBean sb2 = new SupportBean("T1", 20);
        epService.getEPRuntime().sendEvent(sb2);
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "T1"));
        row = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(row, fields, new Object[] {"T1", 30, 32});
        ArrayAssertionUtil.assertEqualsAnyOrder((int[]) row.get("subrow.v3"), new int[] {10,20});
        ArrayAssertionUtil.assertEqualsAnyOrder((Object[]) row.get("subrow.v4"), new Object[] {sb1,sb2});
    }
}
