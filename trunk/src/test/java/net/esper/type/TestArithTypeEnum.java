package net.esper.type;

import junit.framework.TestCase;
import net.esper.type.MathArithTypeEnum;

public class TestArithTypeEnum extends TestCase
{
    public void testAddDouble()
    {
        MathArithTypeEnum.Computer computer = MathArithTypeEnum.ADD.getComputer(Double.class);
        assertEquals(12.1d, computer.compute(5.5,6.6));
    }

    public void testInvalidGetComputer()
    {
        // Since we only do Double, Float, Integer and Long as results
        tryInvalid(String.class);
        tryInvalid(long.class);
        tryInvalid(short.class);
        tryInvalid(byte.class);
    }

    public void testAllComputers()
    {
        final Class[] testClasses = {
            Float.class, Double.class, Integer.class, Long.class};

        for (Class clazz : testClasses)
        {
            for (MathArithTypeEnum type : MathArithTypeEnum.values())
            {
                MathArithTypeEnum.Computer computer = type.getComputer(clazz);
                Number result = computer.compute(3, 4);
                assertEquals(clazz, result.getClass());

                if (type == MathArithTypeEnum.ADD)
                {
                    assertEquals(7d, result.doubleValue());
                }
                if (type == MathArithTypeEnum.SUBTRACT)
                {
                    assertEquals(-1d, result.doubleValue());
                }
                if (type == MathArithTypeEnum.MULTIPLY)
                {
                    assertEquals(12d, result.doubleValue());
                }
                if (type == MathArithTypeEnum.DIVIDE)
                {
                    if ((clazz == Integer.class) || (clazz == Long.class))
                    {
                        assertEquals("clazz=" + clazz, 0d, result.doubleValue());
                    }
                    else
                    {
                        assertEquals("clazz=" + clazz, 3/4d, result.doubleValue());
                    }
                }
            }
        }
    }

    private void tryInvalid(Class clazz)
    {
        try
        {
            MathArithTypeEnum.ADD.getComputer(clazz);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }
}
