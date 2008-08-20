package com.espertech.esper.view.stat;

import junit.framework.TestCase;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;

public class TestCorrelationBean extends TestCase
{
    private final int PRECISION_DIGITS = 6;

    public void testCORREL()
    {
        CorrelationBean stat = new CorrelationBean();

        assertEquals(Double.NaN, stat.getCorrelation());
        assertEquals(0, stat.getN());

        stat.addPoint(1, 10);
        assertEquals(Double.NaN, stat.getCorrelation());
        assertEquals(1, stat.getN());

        stat.addPoint(2, 20);
        assertEquals(1d, stat.getCorrelation());
        assertEquals(2, stat.getN());

        stat.addPoint(1.5, 14);
        assertTrue(DoubleValueAssertionUtil.equals(stat.getCorrelation(),0.993399268, PRECISION_DIGITS));
        assertEquals(3, stat.getN());

        stat.addPoint(1.4, 14);
        assertTrue(DoubleValueAssertionUtil.equals(stat.getCorrelation(),0.992631989, PRECISION_DIGITS));
        assertEquals(4, stat.getN());

        stat.removePoint(1.5, 14);
        assertTrue(DoubleValueAssertionUtil.equals(stat.getCorrelation(),1, PRECISION_DIGITS));
        assertEquals(3, stat.getN());

        stat.addPoint(100, 1);
        assertTrue(DoubleValueAssertionUtil.equals(stat.getCorrelation(),-0.852632057, PRECISION_DIGITS));
        assertEquals(4, stat.getN());
    }
}