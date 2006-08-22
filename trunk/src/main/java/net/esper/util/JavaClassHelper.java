package net.esper.util;

/**
 * Helper for questions about Java classes such as
 * <p> what is the boxed type for a primitive type
 * <p> is this a numeric type.
 */
public class JavaClassHelper
{
    /**
     * Returns the boxed class for the given class, or the class itself if already boxed or not a primitive type.
     * For primitive unboxed types returns the boxed types, e.g. returns java.lang.Integer for passing int.class.
     * For any other class, returns the class passed.
     * @param clazz is the class to return the boxed class for
     * @return boxed variant of the same class
     */
    public static Class getBoxedType(Class clazz)
    {
        if (clazz == boolean.class)
        {
            return Boolean.class;
        }
        if (clazz == double.class)
        {
            return Double.class;
        }
        if (clazz == float.class)
        {
            return Float.class;
        }
        if (clazz == byte.class)
        {
            return Byte.class;
        }
        if (clazz == short.class)
        {
            return Short.class;
        }
        if (clazz == int.class)
        {
            return Integer.class;
        }
        if (clazz == long.class)
        {
            return Long.class;
        }
        return clazz;
    }

   /**
     * Determines if the class passed in is one of the numeric classes.
     * @param clazz to check
     * @return true if numeric, false if not
     */
    public static boolean isNumeric(Class clazz)
    {
        if ((clazz == Double.class) ||
            (clazz == double.class) ||
            (clazz == Float.class) ||
            (clazz == float.class) ||
            (clazz == Short.class) ||
            (clazz == short.class) ||
            (clazz == Integer.class) ||
            (clazz == int.class) ||
            (clazz == Long.class) ||
            (clazz == long.class) ||
            (clazz == Byte.class) ||
            (clazz == byte.class))
        {
            return true;
        }

        return false;
    }

    /**
     * Returns true if 2 classes are assignment compatible.
     * @param parameterType type to assign from
     * @param parameterization type to assign to
     * @return true if assignment compatible, false if not
     */
    public static final boolean isAssignmentCompatible(Class parameterType, Class parameterization)
    {
        if (parameterType.isAssignableFrom(parameterization))
        {
            return true;
        }

        if (parameterType.isPrimitive())
        {
            Class parameterWrapperClazz = getBoxedType(parameterType);
            if (parameterWrapperClazz != null)
            {
                return parameterWrapperClazz.equals(parameterization);
            }
        }

        return false;
    }

    /**
      * Determines if the class passed in is a boolean boxed or unboxed type.
      * @param clazz to check
      * @return true if boolean, false if not
      */
    public static boolean isBoolean(Class clazz)
    {
        if ((clazz == Boolean.class) ||
            (clazz == boolean.class))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns the coercion type for the 2 numeric types for use in arithmatic.
     * Note: byte and short types always result in integer.
     * @param typeOne
     * @param typeTwo
     * @return coerced type
     */
    public static Class getArithmaticCoercionType(Class typeOne, Class typeTwo)
    {
        Class boxedOne = getBoxedType(typeOne);
        Class boxedTwo = getBoxedType(typeTwo);

        if (!isNumeric(boxedOne) || !isNumeric(boxedTwo))
        {
            throw new IllegalArgumentException("Cannot coerce types " + typeOne + " and " + typeTwo);
        }

        if ((boxedOne == Double.class) || (boxedTwo == Double.class))
        {
            return Double.class;
        }
        if ((boxedOne == Float.class) || (boxedTwo == Float.class))
        {
            return Float.class;
        }
        if ((boxedOne == Long.class) || (boxedTwo == Long.class))
        {
            return Long.class;
        }
        return Integer.class;
    }

    /**
     * Coerce the given number to the given type. Allows coerce to lower resultion number.
     * Does't coerce to primitive types.
     * @param numToCoerce is the number to coerce to the given type
     * @param resultType is the result type to return
     * @return the numToCoerce as a value in the given result type 
     */
    public static Number coerceNumber(Number numToCoerce, Class resultType)
    {
        if (numToCoerce.getClass() == resultType)
        {
            return numToCoerce;
        }
        if (resultType == Double.class)
        {
            return numToCoerce.doubleValue();
        }
        if (resultType == Long.class)
        {
            return numToCoerce.longValue();
        }
        if (resultType == Float.class)
        {
            return numToCoerce.floatValue();
        }
        if (resultType == Integer.class)
        {
            return numToCoerce.intValue();
        }
        if (resultType == Short.class)
        {
            return numToCoerce.shortValue();
        }
        if (resultType == Byte.class)
        {
            return numToCoerce.byteValue();
        }
        throw new IllegalArgumentException("Cannot coerce to number subtype " + resultType.getName());
    }

    /**
     * Returns true if the Number instance is a floating point number.
     * @param number to check
     * @return true if number is Float or Double type 
     */
    public static boolean isFloatingPointNumber(Number number)
    {
        if ((number instanceof Float) ||
            (number instanceof Double))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the supplied type is a floating point number.
     * @param clazz to check
     * @return true if primitive or boxed float or double
     */
    public static boolean isFloatingPointClass(Class clazz)
    {
        if ((clazz == Float.class) ||
            (clazz == Double.class) ||
            (clazz == float.class) ||
            (clazz == double.class))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns for 2 classes to be compared via relational operator the Class type of
     * common comparison. The output is always Long.class, Double.class, String.class or Boolean.class
     * depending on whether the passed types are numeric and floating-point.
     * Accepts primitive as well as boxed types.
     * @param typeOne
     * @param typeTwo
     * @return One of Long.class, Double.class or String.class
     * @throws IllegalArgumentException if the types cannot be compared
     */
    public static Class getCompareToCoercionType(Class typeOne, Class typeTwo)
    {
        if ((typeOne == String.class) && (typeTwo == String.class))
        {
            return String.class;
        }
        if (  ((typeOne == boolean.class) || ((typeOne == Boolean.class))) &&
              ((typeTwo == boolean.class) || ((typeTwo == Boolean.class))) )
        {
            return Boolean.class;
        }
        if (!isNumeric(typeOne) || !isNumeric(typeTwo))
        {
            throw new IllegalArgumentException("Types cannot be compared: " +
                    typeOne.getName() + " and " + typeTwo.getName());
        }
        if (isFloatingPointClass(typeOne) || isFloatingPointClass(typeTwo))
        {
            return Double.class;
        }
        return Long.class;
    }

    /**
     * Returns for the class name given the class name of the boxed (wrapped) type if
     * the class name is one of the Java primitive types.
     * @param className is a class name, a Java primitive type or other class
     * @return boxed class name if Java primitive type, or just same class name passed in if not a primitive type
     */
    public static String getBoxedClassName(String className)
    {
        if (className.equals(char.class.getName()))
        {
            return Character.class.getName();
        }
        if (className.equals(byte.class.getName()))
        {
            return Byte.class.getName();
        }
        if (className.equals(short.class.getName()))
        {
            return Short.class.getName();
        }
        if (className.equals(int.class.getName()))
        {
            return Integer.class.getName();
        }
        if (className.equals(long.class.getName()))
        {
            return Long.class.getName();
        }
        if (className.equals(float.class.getName()))
        {
            return Float.class.getName();
        }
        if (className.equals(double.class.getName()))
        {
            return Double.class.getName();
        }
        if (className.equals(boolean.class.getName()))
        {
            return Boolean.class.getName();
        }
        return className;
    }

    /**
     * Returns true if the class passed in is a Java built-in data type (primitive or wrapper) including String.
     * @param clazz to check
     * @return true if built-in data type, or false if not
     */
    public static boolean isJavaBuiltinDataType(Class clazz)
    {
        Class clazzBoxed = getBoxedType(clazz);
        if (isNumeric(clazzBoxed))
        {
            return true;
        }
        if (isBoolean(clazzBoxed))
        {
            return true;
        }
        if (clazzBoxed.equals(String.class))
        {
            return true;
        }
        if ((clazzBoxed.equals(char.class)) ||
            (clazzBoxed.equals(Character.class)))
        {
            return true;
        }
        return false;
    }
}
