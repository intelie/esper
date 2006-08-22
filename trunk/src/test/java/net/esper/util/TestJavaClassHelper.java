package net.esper.util;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBean;

public class TestJavaClassHelper extends TestCase
{
    public void testCoerceNumber()
    {
        assertEquals(1d, JavaClassHelper.coerceNumber(1d, Double.class));
        assertEquals(5d, JavaClassHelper.coerceNumber(5, Double.class));
        assertEquals(6d, JavaClassHelper.coerceNumber((byte) 6, Double.class));
        assertEquals(3f, JavaClassHelper.coerceNumber((long) 3, Float.class));
        assertEquals((short) 2, JavaClassHelper.coerceNumber((long) 2, Short.class));
        assertEquals(4, JavaClassHelper.coerceNumber((long) 4, Integer.class));
        assertEquals((byte) 5, JavaClassHelper.coerceNumber((long) 5, Byte.class));
        assertEquals(8l, JavaClassHelper.coerceNumber((long) 8, Long.class));

        try
        {
            JavaClassHelper.coerceNumber(10, int.class);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testIsNumeric()
    {
        final Class[] numericClasses = {
            float.class, Float.class, double.class, Double.class,
            byte.class, Byte.class, short.class, Short.class, int.class, Integer.class,
            long.class, Long.class };

        final Class[] nonnumericClasses = {
            String.class, boolean.class, Boolean.class, TestCase.class };

        for (Class clazz : numericClasses)
        {
            assertTrue(JavaClassHelper.isNumeric(clazz));
        }

        for (Class clazz : nonnumericClasses)
        {
            assertFalse(JavaClassHelper.isNumeric(clazz));
        }
    }

    public void testGetBoxed()
    {
        final Class[] primitiveClasses = {
            boolean.class, float.class, double.class, byte.class, short.class, int.class, long.class};

        final Class[] boxedClasses = {
            Boolean.class, Float.class, Double.class, Byte.class, Short.class, Integer.class, Long.class};

        final Class[] otherClasses = {
            String.class, TestCase.class };

        for (int i = 0; i < primitiveClasses.length; i++)
        {
            Class boxed = JavaClassHelper.getBoxedType(primitiveClasses[i]);
            assertEquals(boxed, boxedClasses[i]);
        }

        for (int i = 0; i < boxedClasses.length; i++)
        {
            Class boxed = JavaClassHelper.getBoxedType(boxedClasses[i]);
            assertEquals(boxed, boxedClasses[i]);
        }

        for (int i = 0; i < otherClasses.length; i++)
        {
            Class boxed = JavaClassHelper.getBoxedType(otherClasses[i]);
            assertEquals(boxed, otherClasses[i]);
        }
    }

    public void testIsAssignmentCompatible()
    {
        assertTrue(JavaClassHelper.isAssignmentCompatible(boolean.class, Boolean.class));
        assertFalse(JavaClassHelper.isAssignmentCompatible(String.class, Boolean.class));
        assertFalse(JavaClassHelper.isAssignmentCompatible(int.class, Long.class));
        assertTrue(JavaClassHelper.isAssignmentCompatible(long.class, Long.class));
        assertTrue(JavaClassHelper.isAssignmentCompatible(double.class, double.class));
    }

    public void testIsBoolean()
    {
        assertTrue(JavaClassHelper.isBoolean(Boolean.class));
        assertTrue(JavaClassHelper.isBoolean(boolean.class));
        assertFalse(JavaClassHelper.isBoolean(String.class));
    }

    public void testGetCoercionType()
    {
        assertEquals(Double.class, JavaClassHelper.getArithmaticCoercionType(Double.class, int.class));
        assertEquals(Double.class, JavaClassHelper.getArithmaticCoercionType(byte.class, double.class));
        assertEquals(Long.class, JavaClassHelper.getArithmaticCoercionType(byte.class, long.class));
        assertEquals(Long.class, JavaClassHelper.getArithmaticCoercionType(byte.class, long.class));
        assertEquals(Float.class, JavaClassHelper.getArithmaticCoercionType(float.class, long.class));
        assertEquals(Float.class, JavaClassHelper.getArithmaticCoercionType(byte.class, float.class));
        assertEquals(Integer.class, JavaClassHelper.getArithmaticCoercionType(byte.class, int.class));
        assertEquals(Integer.class, JavaClassHelper.getArithmaticCoercionType(Integer.class, int.class));

        try
        {
            JavaClassHelper.getArithmaticCoercionType(String.class, float.class);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
        try
        {
            JavaClassHelper.getArithmaticCoercionType(int.class, boolean.class);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testIsFloatingPointNumber()
    {
        assertTrue(JavaClassHelper.isFloatingPointNumber(1d));
        assertTrue(JavaClassHelper.isFloatingPointNumber(1f));
        assertTrue(JavaClassHelper.isFloatingPointNumber(new Double(1)));
        assertTrue(JavaClassHelper.isFloatingPointNumber(new Float(1)));

        assertFalse(JavaClassHelper.isFloatingPointNumber(1));
        assertFalse(JavaClassHelper.isFloatingPointNumber(new Integer(1)));
    }

    public void testIsFloatingPointClass()
    {
        assertTrue(JavaClassHelper.isFloatingPointClass(double.class));
        assertTrue(JavaClassHelper.isFloatingPointClass(float.class));
        assertTrue(JavaClassHelper.isFloatingPointClass(Double.class));
        assertTrue(JavaClassHelper.isFloatingPointClass(Float.class));

        assertFalse(JavaClassHelper.isFloatingPointClass(String.class));
        assertFalse(JavaClassHelper.isFloatingPointClass(int.class));
        assertFalse(JavaClassHelper.isFloatingPointClass(Integer.class));
    }

    public void testGetCompareToCoercionType()
    {
        assertEquals(String.class, JavaClassHelper.getCompareToCoercionType(String.class, String.class));
        assertEquals(Boolean.class, JavaClassHelper.getCompareToCoercionType(Boolean.class, Boolean.class));
        assertEquals(Boolean.class, JavaClassHelper.getCompareToCoercionType(Boolean.class, boolean.class));
        assertEquals(Boolean.class, JavaClassHelper.getCompareToCoercionType(boolean.class, Boolean.class));
        assertEquals(Boolean.class, JavaClassHelper.getCompareToCoercionType(boolean.class, boolean.class));

        assertEquals(Double.class, JavaClassHelper.getCompareToCoercionType(int.class, float.class));
        assertEquals(Double.class, JavaClassHelper.getCompareToCoercionType(double.class, byte.class));
        assertEquals(Double.class, JavaClassHelper.getCompareToCoercionType(float.class, float.class));
        assertEquals(Double.class, JavaClassHelper.getCompareToCoercionType(float.class, Double.class));

        assertEquals(Long.class, JavaClassHelper.getCompareToCoercionType(int.class, int.class));
        assertEquals(Long.class, JavaClassHelper.getCompareToCoercionType(Short.class, Integer.class));

        tryInvalidGetRelational(String.class, int.class);
        tryInvalidGetRelational(Long.class, String.class);
        tryInvalidGetRelational(Long.class, Boolean.class);
        tryInvalidGetRelational(boolean.class, int.class);
    }

    public void testGetBoxedClassName() throws Exception
    {
        String[][] tests = new String[][] {
                {Integer.class.getName(), int.class.getName()},
                {Long.class.getName(), long.class.getName()},
                {Short.class.getName(), short.class.getName()},
                {Double.class.getName(), double.class.getName()},
                {Float.class.getName(), float.class.getName()},
                {Boolean.class.getName(), boolean.class.getName()},
                {Byte.class.getName(), byte.class.getName()},
                {Character.class.getName(), char.class.getName()}
        };

        for (int i = 0; i < tests.length; i++)
        {
            assertEquals(tests[i][0], JavaClassHelper.getBoxedClassName(tests[i][1]));
        }
    }

    public void testIsJavaBuiltinDataType()
    {
        Class[] classesDataType = new Class[] {int.class, Long.class, double.class, boolean.class, Boolean.class,
                char.class, Character.class, String.class};
        Class[] classesNotDataType = new Class[] {SupportBean.class, Math.class, Class.class};

        for (int i = 0; i < classesDataType.length; i++)
        {
            assertTrue(JavaClassHelper.isJavaBuiltinDataType(classesDataType[i]));
        }
        for (int i = 0; i < classesNotDataType.length; i++)
        {
            assertFalse(JavaClassHelper.isJavaBuiltinDataType(classesNotDataType[i]));
        }
    }

    private void tryInvalidGetRelational(Class classOne, Class classTwo)
    {
        try
        {
            JavaClassHelper.getCompareToCoercionType(classOne, classTwo);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

}
