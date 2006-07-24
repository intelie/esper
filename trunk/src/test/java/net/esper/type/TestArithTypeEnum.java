package net.esper.type;

import junit.framework.TestCase;
import net.esper.type.ArithTypeEnum;

public class TestArithTypeEnum extends TestCase
{
    public void testAddDouble()
    {
        ArithTypeEnum.Computer computer = ArithTypeEnum.ADD.getComputer(Double.class);
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
            for (ArithTypeEnum type : ArithTypeEnum.values())
            {
                ArithTypeEnum.Computer computer = type.getComputer(clazz);
                Number result = computer.compute(3, 4);
                assertEquals(clazz, result.getClass());

                if (type == ArithTypeEnum.ADD)
                {
                    assertEquals(7d, result.doubleValue());
                }
                if (type == ArithTypeEnum.SUBTRACT)
                {
                    assertEquals(-1d, result.doubleValue());
                }
                if (type == ArithTypeEnum.MULTIPLY)
                {
                    assertEquals(12d, result.doubleValue());
                }
                if (type == ArithTypeEnum.DIVIDE)
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
            ArithTypeEnum.ADD.getComputer(clazz);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }
}
