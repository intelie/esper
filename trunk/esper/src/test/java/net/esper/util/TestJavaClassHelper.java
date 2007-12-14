package net.esper.util;

import junit.framework.TestCase;
import net.esper.support.bean.*;

import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.TreeMap;

public class TestJavaClassHelper extends TestCase
{
    public void testCanCoerce()
    {
        final Class[] primitiveClasses = {
            float.class, double.class, byte.class, short.class, int.class, long.class};

        final Class[] boxedClasses = {
            Float.class, Double.class, Byte.class, Short.class, Integer.class, Long.class};

        for (int i = 0; i < primitiveClasses.length; i++)
        {
            assertTrue(JavaClassHelper.canCoerce(primitiveClasses[i], boxedClasses[i]));
            assertTrue(JavaClassHelper.canCoerce(boxedClasses[i], boxedClasses[i]));
            assertTrue(JavaClassHelper.canCoerce(primitiveClasses[i], primitiveClasses[i]));
            assertTrue(JavaClassHelper.canCoerce(boxedClasses[i], primitiveClasses[i]));    
        }

        assertTrue(JavaClassHelper.canCoerce(float.class, Double.class));
        assertFalse(JavaClassHelper.canCoerce(double.class, float.class));
        assertTrue(JavaClassHelper.canCoerce(int.class, long.class));
        assertFalse(JavaClassHelper.canCoerce(long.class, int.class));
        assertTrue(JavaClassHelper.canCoerce(long.class, double.class));
        assertTrue(JavaClassHelper.canCoerce(int.class, double.class));

        try
        {
            JavaClassHelper.canCoerce(String.class, Float.class);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }

        try
        {
            JavaClassHelper.canCoerce(Float.class, Boolean.class);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testCoerceNumber()
    {
        assertEquals(1d, JavaClassHelper.coerceBoxed(1d, Double.class));
        assertEquals(5d, JavaClassHelper.coerceBoxed(5, Double.class));
        assertEquals(6d, JavaClassHelper.coerceBoxed((byte) 6, Double.class));
        assertEquals(3f, JavaClassHelper.coerceBoxed((long) 3, Float.class));
        assertEquals((short) 2, JavaClassHelper.coerceBoxed((long) 2, Short.class));
        assertEquals(4, JavaClassHelper.coerceBoxed((long) 4, Integer.class));
        assertEquals((byte) 5, JavaClassHelper.coerceBoxed((long) 5, Byte.class));
        assertEquals(8l, JavaClassHelper.coerceBoxed((long) 8, Long.class));

        try
        {
            JavaClassHelper.coerceBoxed(10, int.class);
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

    public void testIsNumericNonFP()
    {
        final Class[] numericClasses = {
            byte.class, Byte.class, short.class, Short.class, int.class, Integer.class,
            long.class, Long.class };

        final Class[] nonnumericClasses = {
            float.class, Float.class, double.class, Double.class, String.class, boolean.class, Boolean.class, TestCase.class };

        for (Class clazz : numericClasses)
        {
            assertTrue(JavaClassHelper.isNumericNonFP(clazz));
        }

        for (Class clazz : nonnumericClasses)
        {
            assertFalse(JavaClassHelper.isNumericNonFP(clazz));
        }
    }

    public void testGetBoxed()
    {
        final Class[] primitiveClasses = {
            boolean.class, float.class, double.class, byte.class, short.class, int.class, long.class, char.class};

        final Class[] boxedClasses = {
            Boolean.class, Float.class, Double.class, Byte.class, Short.class, Integer.class, Long.class, Character.class};

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

    public void testGetPrimitive()
    {
        final Class[] primitiveClasses = {
            boolean.class, float.class, double.class, byte.class, short.class, int.class, long.class, char.class};

        final Class[] boxedClasses = {
            Boolean.class, Float.class, Double.class, Byte.class, Short.class, Integer.class, Long.class, Character.class};

        final Class[] otherClasses = {
            String.class, TestCase.class };

        for (int i = 0; i < primitiveClasses.length; i++)
        {
            Class primitive = JavaClassHelper.getPrimitiveType(boxedClasses[i]);
            assertEquals(primitive, primitiveClasses[i]);
        }

        for (int i = 0; i < boxedClasses.length; i++)
        {
            Class primitive = JavaClassHelper.getPrimitiveType(primitiveClasses[i]);
            assertEquals(primitive, primitiveClasses[i]);
        }

        for (int i = 0; i < otherClasses.length; i++)
        {
            Class clazz = JavaClassHelper.getPrimitiveType(otherClasses[i]);
            assertEquals(clazz, otherClasses[i]);
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
        catch (CoercionException ex)
        {
            // Expected
        }
        
        try
        {
            JavaClassHelper.getArithmaticCoercionType(int.class, boolean.class);
            fail();
        }
        catch (CoercionException ex)
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

    public void testClassForName() throws Exception
    {
        Object[][] tests = new Object[][] {
                {int.class, int.class.getName()},
                {long.class, long.class.getName()},
                {short.class, short.class.getName()},
                {double.class, double.class.getName()},
                {float.class, float.class.getName()},
                {boolean.class, boolean.class.getName()},
                {byte.class, byte.class.getName()},
                {char.class, char.class.getName()} };

        for (int i = 0; i < tests.length; i++)
        {
            assertEquals(tests[i][0], JavaClassHelper.getClassForName((String)tests[i][1]));
        }
    }

    public void testClassForSimpleName() throws Exception
    {
        Object[][] tests = new Object[][] {
                {"Boolean", Boolean.class},
                {"Bool", Boolean.class},
                {"boolean", Boolean.class},
                {"int", Integer.class},
                {"inTeger", Integer.class},
                {"long", Long.class},
                {"LONG", Long.class},
                {"short", Short.class},
                {"  short  ", Short.class},
                {"double", Double.class},
                {" douBle", Double.class},
                {"float", Float.class},
                {"float  ", Float.class},
                {"byte", Byte.class},
                {"   bYte ", Byte.class},
                {"char", Character.class},
                {"character", Character.class},
                {"string", String.class},
                {"varchar", String.class},
                {"varchar2", String.class},
                };

        for (int i = 0; i < tests.length; i++)
        {
            assertEquals("error in row:" + i, tests[i][1], JavaClassHelper.getClassForSimpleName((String)tests[i][0]));
        }
    }

    public void testParse() throws Exception
    {
        Object[][] tests = new Object[][] {
                {Boolean.class, "TrUe", true},
                {Boolean.class, "false", false},
                {boolean.class, "false", false},
                {boolean.class, "true", true},
                {int.class, "73737474 ", 73737474},
                {Integer.class, " -1 ", -1},
                {long.class, "123456789001222L", 123456789001222L},
                {Long.class, " -2 ", -2L},
                {Long.class, " -2L ", -2L},
                {Long.class, " -2l ", -2L},
                {Short.class, " -3 ", (short)-3},
                {short.class, "111", (short)111},
                {Double.class, " -3d ", -3d},
                {double.class, "111.38373", 111.38373d},
                {Double.class, " -3.1D ", -3.1D},
                {Float.class, " -3f ", -3f},
                {float.class, "111.38373", 111.38373f},
                {Float.class, " -3.1F ", -3.1f},
                {Byte.class, " -3 ", (byte) -3},
                {byte.class, " 1 ", (byte)1},
                {char.class, "ABC", 'A'},
                {Character.class, " AB", ' '},
                {String.class, "AB", "AB"},
                {String.class, " AB ", " AB "},
                };

        for (int i = 0; i < tests.length; i++)
        {
            assertEquals("error in row:" + i, tests[i][2], JavaClassHelper.parse((Class)tests[i][0], (String)tests[i][1]));
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

    public void testGetCommonCoercionType()
    {
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {String.class}));
        assertEquals(Boolean.class, JavaClassHelper.getCommonCoercionType(new Class[] {boolean.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {long.class}));

        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {String.class, null}));
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {String.class, String.class}));
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {String.class, String.class, String.class}));
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {String.class, String.class, null}));
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {null, String.class, null}));
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {null, String.class, String.class}));
        assertEquals(String.class, JavaClassHelper.getCommonCoercionType(new Class[] {null, null, String.class, String.class}));

        assertEquals(Boolean.class, JavaClassHelper.getCommonCoercionType(new Class[] {Boolean.class, Boolean.class}));
        assertEquals(Boolean.class, JavaClassHelper.getCommonCoercionType(new Class[] {Boolean.class, boolean.class}));
        assertEquals(Boolean.class, JavaClassHelper.getCommonCoercionType(new Class[] {boolean.class, Boolean.class}));
        assertEquals(Boolean.class, JavaClassHelper.getCommonCoercionType(new Class[] {boolean.class, boolean.class}));
        assertEquals(Boolean.class, JavaClassHelper.getCommonCoercionType(new Class[] {Boolean.class, boolean.class, boolean.class}));
        assertEquals(Integer.class, JavaClassHelper.getCommonCoercionType(new Class[] {int.class, byte.class, int.class}));
        assertEquals(Integer.class, JavaClassHelper.getCommonCoercionType(new Class[] {Integer.class, Byte.class, Short.class}));
        assertEquals(Integer.class, JavaClassHelper.getCommonCoercionType(new Class[] {byte.class, short.class, short.class}));
        assertEquals(Double.class, JavaClassHelper.getCommonCoercionType(new Class[] {Integer.class, Byte.class, Double.class}));
        assertEquals(Double.class, JavaClassHelper.getCommonCoercionType(new Class[] {Long.class, Double.class, Double.class}));
        assertEquals(Double.class, JavaClassHelper.getCommonCoercionType(new Class[] {double.class, byte.class}));
        assertEquals(Double.class, JavaClassHelper.getCommonCoercionType(new Class[] {double.class, byte.class, null}));
        assertEquals(Float.class, JavaClassHelper.getCommonCoercionType(new Class[] {float.class, float.class}));
        assertEquals(Float.class, JavaClassHelper.getCommonCoercionType(new Class[] {float.class, int.class}));
        assertEquals(Float.class, JavaClassHelper.getCommonCoercionType(new Class[] {Integer.class, int.class, Float.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {Integer.class, int.class, long.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {long.class, int.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {long.class, int.class, int.class, int.class, byte.class, short.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {long.class, null, int.class, null, int.class, int.class, null, byte.class, short.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {Integer.class, int.class, long.class}));
        assertEquals(Character.class, JavaClassHelper.getCommonCoercionType(new Class[] {char.class, char.class, char.class}));
        assertEquals(Long.class, JavaClassHelper.getCommonCoercionType(new Class[] {int.class, int.class, int.class, long.class, int.class, int.class}));
        assertEquals(Double.class, JavaClassHelper.getCommonCoercionType(new Class[] {int.class, long.class, int.class, double.class, int.class, int.class}));
        assertEquals(null, JavaClassHelper.getCommonCoercionType(new Class[] {null, null}));
        assertEquals(null, JavaClassHelper.getCommonCoercionType(new Class[] {null, null, null}));
        assertEquals(SupportBean.class, JavaClassHelper.getCommonCoercionType(new Class[] {SupportBean.class, null, null}));
        assertEquals(SupportBean.class, JavaClassHelper.getCommonCoercionType(new Class[] {null, SupportBean.class, null}));
        assertEquals(SupportBean.class, JavaClassHelper.getCommonCoercionType(new Class[] {null, SupportBean.class}));
        assertEquals(SupportBean.class, JavaClassHelper.getCommonCoercionType(new Class[] {null, null, SupportBean.class}));
        assertEquals(SupportBean.class, JavaClassHelper.getCommonCoercionType(new Class[] {SupportBean.class, null, SupportBean.class, SupportBean.class}));

        tryInvalidGetCommonCoercionType(new Class[] {String.class, Boolean.class});
        tryInvalidGetCommonCoercionType(new Class[] {String.class, String.class, Boolean.class});
        tryInvalidGetCommonCoercionType(new Class[] {Boolean.class, String.class, Boolean.class});
        tryInvalidGetCommonCoercionType(new Class[] {Boolean.class, Boolean.class, String.class});
        tryInvalidGetCommonCoercionType(new Class[] {long.class, Boolean.class, String.class});
        tryInvalidGetCommonCoercionType(new Class[] {double.class, long.class, String.class});
        tryInvalidGetCommonCoercionType(new Class[] {null, double.class, long.class, String.class});
        tryInvalidGetCommonCoercionType(new Class[] {String.class, String.class, long.class});
        tryInvalidGetCommonCoercionType(new Class[] {String.class, SupportBean.class});
        tryInvalidGetCommonCoercionType(new Class[] {boolean.class, null, null, String.class});
        tryInvalidGetCommonCoercionType(new Class[] {int.class, null, null, String.class});
        tryInvalidGetCommonCoercionType(new Class[] {SupportBean.class, Boolean.class});
        tryInvalidGetCommonCoercionType(new Class[] {String.class, SupportBean.class});
        tryInvalidGetCommonCoercionType(new Class[] {SupportBean.class, String.class, SupportBean.class});

        try
        {
            JavaClassHelper.getCommonCoercionType(new Class[0]);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testGetPrimitiveClassForName()
    {
        Object[][] tests = new Object[][] {
                {"int", int.class},
                {"Long", long.class},
                {"SHort", short.class},
                {"DOUBLE", double.class},
                {"float", float.class},
                {"boolean", boolean.class},
                {"ByTe", byte.class},
                {"char", char.class},
                {"jfjfjf", null},
                {SupportBean.class.getName(), null},
                {"string", String.class},
                {"STRINg", String.class}
        };

        for (int i = 0; i < tests.length; i++)
        {
            assertEquals(tests[i][1], JavaClassHelper.getPrimitiveClassForName((String) tests[i][0]));
        }
    }

    public void testImplementsInterface()
    {
        Object[][] tests = new Object[][] {
                {HashMap.class, Map.class, true},
                {AbstractMap.class, Map.class, true},
                {TreeMap.class, Map.class, true},
                {String.class, Map.class, false},
                {SupportBean_S0.class, SupportMarkerInterface.class, false},
                {SupportBean_E.class, SupportMarkerInterface.class, true},
                {SupportBean_F.class, SupportMarkerInterface.class, true},
                {SupportBeanBase.class, SupportMarkerInterface.class, true},
                {SupportOverrideOneB.class, SupportMarkerInterface.class, true}
                };

        for (int i = 0; i < tests.length; i++)
        {
            assertEquals("test failed for " + tests[i][0], tests[i][2], JavaClassHelper.isImplementsInterface((Class)tests[i][0], (Class)tests[i][1]));
        }
    }

    private void tryInvalidGetCommonCoercionType(Class[] types)
    {
        try
        {
            JavaClassHelper.getCommonCoercionType(types);
            fail();
        }
        catch (CoercionException ex)
        {
            // expected
        }
    }

}
