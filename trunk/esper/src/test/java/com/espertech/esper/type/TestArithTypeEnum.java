package com.espertech.esper.type;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestArithTypeEnum extends TestCase
{
    public void testAddDouble()
    {
        MathArithTypeEnum.Computer computer = MathArithTypeEnum.ADD.getComputer(Double.class, Double.class, Double.class);
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
                MathArithTypeEnum.Computer computer = type.getComputer(clazz,clazz,clazz);
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

    public void testBigNumberComputers()
    {
        Object[][] params = new Object[][] {
                {false, BigInteger.valueOf(10), MathArithTypeEnum.ADD, BigInteger.valueOf(10), BigInteger.valueOf(20)},
                {false, BigInteger.valueOf(100), MathArithTypeEnum.SUBTRACT, BigInteger.valueOf(10), BigInteger.valueOf(90)},
                {false, BigInteger.valueOf(10), MathArithTypeEnum.MULTIPLY, BigInteger.valueOf(10), BigInteger.valueOf(100)},
                {false, BigInteger.valueOf(100), MathArithTypeEnum.DIVIDE, BigInteger.valueOf(5), BigInteger.valueOf(20)},

                {false, 9, MathArithTypeEnum.ADD, BigInteger.valueOf(10), BigInteger.valueOf(19)},
                {false, BigInteger.valueOf(6), MathArithTypeEnum.SUBTRACT, (byte)7, BigInteger.valueOf(-1)},
                {false, BigInteger.valueOf(10), MathArithTypeEnum.DIVIDE, (long) 4, BigInteger.valueOf(2)},
                {false, BigInteger.valueOf(6), MathArithTypeEnum.MULTIPLY, (byte)7, BigInteger.valueOf(42)},

                {true, BigInteger.valueOf(6), MathArithTypeEnum.ADD, (double)7, BigDecimal.valueOf(13.0)},
                {true, BigInteger.valueOf(6), MathArithTypeEnum.SUBTRACT, (double)5, BigDecimal.valueOf(1.0)},
                {true, BigInteger.valueOf(6), MathArithTypeEnum.MULTIPLY, (double)5, BigDecimal.valueOf(30.0)},
                {true, BigInteger.valueOf(6), MathArithTypeEnum.DIVIDE, (double)2, BigDecimal.valueOf(3)},

                {true, 9, MathArithTypeEnum.ADD, BigDecimal.valueOf(10), BigDecimal.valueOf(19)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.SUBTRACT, BigDecimal.valueOf(5), BigDecimal.valueOf(1)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.MULTIPLY, BigDecimal.valueOf(5), BigDecimal.valueOf(30)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.ADD, BigDecimal.valueOf(7), BigDecimal.valueOf(13)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.DIVIDE, BigDecimal.valueOf(3), BigDecimal.valueOf(2)},
                
                {true, BigDecimal.valueOf(10), MathArithTypeEnum.ADD, (long) 8, BigDecimal.valueOf(18)},
                {true, BigDecimal.valueOf(10), MathArithTypeEnum.DIVIDE, (long) 8, BigDecimal.valueOf(1.25)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.SUBTRACT, (byte)7, BigDecimal.valueOf(-1)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.MULTIPLY, (byte)7, BigDecimal.valueOf(42)},

                {true, BigDecimal.valueOf(6), MathArithTypeEnum.MULTIPLY, (double)3, BigDecimal.valueOf(18.0)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.ADD, (double)2, BigDecimal.valueOf(8.0)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.DIVIDE, (double)4, BigDecimal.valueOf(1.5)},
                {true, BigDecimal.valueOf(6), MathArithTypeEnum.SUBTRACT, (double)8, BigDecimal.valueOf(-2.0)},
                };

        for (int i = 0; i < params.length; i++)
        {
            boolean isBigDec = (Boolean) params[i][0];
            Object lhs = params[i][1];
            MathArithTypeEnum e = (MathArithTypeEnum) params[i][2];
            Object rhs = params[i][3];
            Object expected = params[i][4];

            MathArithTypeEnum.Computer computer;
            if (isBigDec)
            {
                computer = e.getComputer(BigDecimal.class, lhs.getClass(), rhs.getClass());
            }
            else
            {
                computer = e.getComputer(BigInteger.class, lhs.getClass(), rhs.getClass());
            }

            Object result = null;
            try
            {
                result = computer.compute((Number)lhs, (Number)rhs);
            }
            catch (RuntimeException ex)
            {
                ex.printStackTrace();
            }
            assertEquals("line " + i + " lhs=" + lhs + " op=" + e.toString() + " rhs=" + rhs, expected, result);
        }
    }

    private void tryInvalid(Class clazz)
    {
        try
        {
            MathArithTypeEnum.ADD.getComputer(clazz, clazz, clazz);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }
}