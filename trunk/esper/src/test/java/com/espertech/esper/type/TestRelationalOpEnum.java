package com.espertech.esper.type;

import junit.framework.TestCase;
import com.espertech.esper.type.RelationalOpEnum;

public class TestRelationalOpEnum extends TestCase
{
    boolean[][] expected = new boolean[][] { { false, false, true },  //GT
                                             { false, true, true }, // GE
                                             { true, false, false }, // LT
                                             { true, true, false }, // LE
        };

    public void testStringComputers()
    {
        String[][] params = new String[][] { {"a", "b"}, {"a", "a"}, {"b", "a"} };

        for (RelationalOpEnum op : RelationalOpEnum.values())
        {
            for (int i = 0; i < params.length; i++)
            {
                boolean result = op.getComputer(String.class).compare(params[i][0], params[i][1]);
                assertEquals("op=" + op.toString() + ",i=" + i, expected[op.ordinal()][i], result);
            }
        }
    }

    public void testLongComputers()
    {
        long[][] params = new long[][] { {1, 2}, {1, 1}, {2, 1} };

        for (RelationalOpEnum op : RelationalOpEnum.values())
        {
            for (int i = 0; i < params.length; i++)
            {
                boolean result = op.getComputer(Long.class).compare(params[i][0], params[i][1]);
                assertEquals("op=" + op.toString() + ",i=" + i, expected[op.ordinal()][i], result);
            }
        }
    }

    public void testDoubleComputers()
    {
        double[][] params = new double[][] { {1, 2}, {1, 1}, {2, 1} };

        for (RelationalOpEnum op : RelationalOpEnum.values())
        {
            for (int i = 0; i < params.length; i++)
            {
                boolean result = op.getComputer(Double.class).compare(params[i][0], params[i][1]);
                assertEquals("op=" + op.toString() + ",i=" + i, expected[op.ordinal()][i], result);
            }
        }
    }

    public void testInvalidGetComputer()
    {
        // Since we only do Double, Long and String compares
        tryInvalid(boolean.class);
        tryInvalid(long.class);
        tryInvalid(short.class);
        tryInvalid(Integer.class);
    }

    private void tryInvalid(Class clazz)
    {
        try
        {
            RelationalOpEnum.GE.getComputer(clazz);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }
}
