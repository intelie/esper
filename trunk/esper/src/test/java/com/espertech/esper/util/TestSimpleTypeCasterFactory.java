package com.espertech.esper.util;

import junit.framework.TestCase;

import java.math.BigInteger;
import java.math.BigDecimal;

public class TestSimpleTypeCasterFactory extends TestCase
{
    public void testGetCaster() throws Exception
    {
        Object[][] tests = new Object[][] {
                {long.class, 10, 10L},
                {double.class, 1, 1d},
                {int.class, 0x1, 1},
                {float.class, 100, 100f},
                {Integer.class, (short)2, 2},
                {Byte.class, (short)2, (byte)2},
                {short.class, (long)2, (short)2},
                };

        for (int i = 0; i < tests.length; i++)
        {
            SimpleTypeCaster caster = SimpleTypeCasterFactory.getCaster(null, (Class)tests[i][0]);
            assertEquals("error in row:" + i, tests[i][2], caster.cast(tests[i][1]));
        }
        
        assertEquals(BigInteger.valueOf(100), SimpleTypeCasterFactory.getCaster(Long.class, BigInteger.class).cast(100L));
        assertEquals(BigDecimal.valueOf(100), SimpleTypeCasterFactory.getCaster(Long.class, BigDecimal.class).cast(100L));
        assertEquals(BigDecimal.valueOf(100d), SimpleTypeCasterFactory.getCaster(Double.class, BigDecimal.class).cast(100d));
    }
}