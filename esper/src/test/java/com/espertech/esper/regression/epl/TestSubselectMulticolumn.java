package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
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

    // TODO test window(*) and window(price) aggregation function
    // TODO assert fragment nature of result
    // TODO test stream select, wildcard
    // TODO test filter spec select expression
    // TODO validate only aggregation or only non-aggregation
    // TODO test non-row functions such as "in"
    // TODO test uncorrelated
    // TODO test column alias not duplicates
    // TODO doc subquery returns single row multiple columns
    // TODO doc subquery returns multiple rows
    
    public void testCorrelatedAggregationSelectEquals()
    {
        String stmtText = "select p00, " +
                "(select sum(intPrimitive) as v1, sum(intPrimitive + 1) as v2 from SupportBean.win:keepall() where string = s0.p00) as sump00 " +
                "from S0 as s0";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        String[] fields = "p00,sump00.v1,sump00.v2".split(",");

        Object[][] rows = new Object[][] {
                {"p00", String.class},
                {"sump00", Map.class}   // TODO
                };
        for (int i = 0; i < rows.length; i++) {
            String message = "Failed assertion for " + rows[i][0];
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(message, rows[i][0], prop.getPropertyName());
            assertEquals(message, rows[i][1], prop.getPropertyType());
        }

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1", null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("T1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1", 10, 11});

        epService.getEPRuntime().sendEvent(new SupportBean("T1", 20));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, "T1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"T1", 30, 32});
    }
}
