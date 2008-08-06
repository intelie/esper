package com.espertech.esper.epl.metric;

import junit.framework.TestCase;
import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestStatementMetricArray extends TestCase
{
    public void testFlowReportActive()
    {
        StatementMetricArray rep = new StatementMetricArray("uri", "name", 3, false);

        assertEquals(0, rep.size());

        assertEquals(0, rep.addStatementGetIndex("001"));
        assertEquals(1, rep.size());

        assertEquals(1, rep.addStatementGetIndex("002"));
        assertEquals(2, rep.addStatementGetIndex("003"));
        assertEquals(3, rep.size());

        rep.removeStatement("002");

        assertEquals(3, rep.addStatementGetIndex("004"));
        assertEquals(4, rep.addStatementGetIndex("005"));

        rep.removeStatement("005");
        assertEquals(5, rep.addStatementGetIndex("006"));

        StatementMetric metrics[] = new StatementMetric[6];
        for (int i = 0; i < 6; i++)
        {
            metrics[i] = rep.getAddMetric(i);
        }

        StatementMetric flushed[] = rep.flushMetrics();
        ArrayAssertionUtil.assertSameExactOrder(metrics, flushed);

        assertEquals(1, rep.addStatementGetIndex("007"));
        assertEquals(4, rep.addStatementGetIndex("008"));

        rep.removeStatement("001");
        rep.removeStatement("003");
        rep.removeStatement("004");
        rep.removeStatement("006");
        rep.removeStatement("007");
        assertEquals(6, rep.size());
        rep.removeStatement("008");
        assertEquals(6, rep.size());

        flushed = rep.flushMetrics();
        assertEquals(6, flushed.length);
        assertEquals(0, rep.size());

        flushed = rep.flushMetrics();
        assertNull(flushed);
        assertEquals(0, rep.size());

        assertEquals(0, rep.addStatementGetIndex("009"));
        assertEquals(1, rep.size());

        flushed = rep.flushMetrics();
        assertEquals(6, flushed.length);
        for (int i = 0; i < flushed.length; i++)
        {
            assertNull(flushed[i]);
        }
        assertEquals(1, rep.size());
    }

    public void testFlowReportInactive()
    {
        StatementMetricArray rep = new StatementMetricArray("uri", "name", 3, true);

        assertEquals(0, rep.addStatementGetIndex("001"));
        assertEquals(1, rep.addStatementGetIndex("002"));
        assertEquals(2, rep.addStatementGetIndex("003"));
        rep.removeStatement("002");

        StatementMetric[] flushed = rep.flushMetrics();
        for (int i = 0; i < 3; i++)
        {
            assertNotNull(flushed[i]);
        }
    }
}
