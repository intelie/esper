/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.util;

import net.esper.event.EventAdapterException;
import net.esper.type.*;

import java.util.LinkedList;
import java.util.List;

import antlr.collections.AST;

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
        if (clazz == char.class)
        {
            return Character.class;
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
     * Returns the un-boxed class for the given class, or the class itself if already un-boxed or not a primitive type.
     * For primitive boxed types returns the unboxed primitive type, e.g. returns int.class for passing Integer.class.
     * For any other class, returns the class passed.
     * @param clazz is the class to return the unboxed (or primitive) class for
     * @return primitive variant of the same class
     */
    public static Class getPrimitiveType(Class clazz)
    {
        if (clazz == Boolean.class)
        {
            return boolean.class;
        }
        if (clazz == Character.class)
        {
            return char.class;
        }
        if (clazz == Double.class)
        {
            return double.class;
        }
        if (clazz == Float.class)
        {
            return float.class;
        }
        if (clazz == Byte.class)
        {
            return byte.class;
        }
        if (clazz == Short.class)
        {
            return short.class;
        }
        if (clazz == Integer.class)
        {
            return int.class;
        }
        if (clazz == Long.class)
        {
            return long.class;
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
      * Determines if the class passed in is one of the numeric classes and not a floating point.
      * @param clazz to check
      * @return true if numeric and not a floating point, false if not
      */
     public static boolean isNumericNonFP(Class clazz)
     {
         if ((clazz == Short.class) ||
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
    public static boolean isAssignmentCompatible(Class parameterType, Class parameterization)
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
     * @param typeOne is the first type
     * @param typeTwo is the second type
     * @return coerced type
     * @throws CoercionException if types don't allow coercion
     */
    public static Class getArithmaticCoercionType(Class typeOne, Class typeTwo)
            throws CoercionException
    {
        Class boxedOne = getBoxedType(typeOne);
        Class boxedTwo = getBoxedType(typeTwo);

        if (!isNumeric(boxedOne) || !isNumeric(boxedTwo))
        {
            throw new CoercionException("Cannot coerce types " + typeOne.getName() + " and " + typeTwo.getName());
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
     * Coerce the given number to the given type, assuming the type is a Boxed type. Allows coerce to lower resultion number.
     * Does't coerce to primitive types.
     * @param numToCoerce is the number to coerce to the given type
     * @param resultBoxedType is the boxed result type to return
     * @return the numToCoerce as a value in the given result type
     */
    public static Number coerceBoxed(Number numToCoerce, Class resultBoxedType)
    {
        if (numToCoerce.getClass() == resultBoxedType)
        {
            return numToCoerce;
        }
        if (resultBoxedType == Double.class)
        {
            return numToCoerce.doubleValue();
        }
        if (resultBoxedType == Long.class)
        {
            return numToCoerce.longValue();
        }
        if (resultBoxedType == Float.class)
        {
            return numToCoerce.floatValue();
        }
        if (resultBoxedType == Integer.class)
        {
            return numToCoerce.intValue();
        }
        if (resultBoxedType == Short.class)
        {
            return numToCoerce.shortValue();
        }
        if (resultBoxedType == Byte.class)
        {
            return numToCoerce.byteValue();
        }
        throw new IllegalArgumentException("Cannot coerce to number subtype " + resultBoxedType.getName());
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
     * @param typeOne is the first type
     * @param typeTwo is the second type
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
     * Determines if a number can be coerced upwards to another number class without loss.
     * <p>
     * Clients must pass in two classes that are numeric types.
     * <p>
     * Any number class can be coerced to double, while only double cannot be coerced to float.
     * Any non-floating point number can be coerced to long.
     * Integer can be coerced to Byte and Short even though loss is possible, for convenience.
     * @param numberClassToBeCoerced the number class to be coerced
     * @param numberClassToCoerceTo the number class to coerce to
     * @return true if numbers can be coerced without loss, false if not
     */
    public static boolean canCoerce(Class numberClassToBeCoerced, Class numberClassToCoerceTo)
    {
        Class boxedFrom = getBoxedType(numberClassToBeCoerced);
        Class boxedTo = getBoxedType(numberClassToCoerceTo);

        if (!isNumeric(numberClassToBeCoerced))
        {
            throw new IllegalArgumentException("Class '" + numberClassToBeCoerced + "' is not a numeric type'");
        }

        if (boxedTo == Float.class)
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class) ||
                    (boxedFrom == Long.class) ||
                    (boxedFrom == Float.class));
        }
        else if (boxedTo == Double.class)
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class) ||
                    (boxedFrom == Long.class) ||
                    (boxedFrom == Float.class) ||
                    (boxedFrom == Double.class));
        }
        else if (boxedTo == Long.class)
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class) ||
                    (boxedFrom == Long.class));
        }
        else if ((boxedTo == Integer.class) ||
                 (boxedTo == Short.class) ||
                 (boxedTo == Byte.class))
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class));
        }
        else
        {
            throw new IllegalArgumentException("Class '" + numberClassToCoerceTo + "' is not a numeric type'");
        }
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

    // null values are allowed and represent and unknown type

    /**
     * Determines a common denominator type to which one or more types can be casted or coerced.
     * For use in determining the result type in certain expressions (coalesce, case).
     * <p>
     * Null values are allowed as part of the input and indicate a 'null' constant value
     * in an expression tree. Such as value doesn't have any type and can be ignored in
     * determining a result type.
     * <p>
     * For numeric types, determines a coercion type that all types can be converted to
     * via the method getArithmaticCoercionType.
     * <p>
     * Indicates that there is no common denominator type by throwing {@link CoercionException}.
     * @param types is an array of one or more types, which can be Java built-in (primitive or wrapper)
     * or user types
     * @return common denominator type if any can be found, for use in comparison
     * @throws CoercionException when no coercion type could be determined
     */
    public static Class getCommonCoercionType(Class[] types)
            throws CoercionException
    {
        if (types.length < 1)
        {
            throw new IllegalArgumentException("Unexpected zero length array");
        }
        if (types.length == 1)
        {
            return getBoxedType(types[0]);
        }

        // Reduce to non-null types
        List<Class> nonNullTypes = new LinkedList<Class>();
        for (int i = 0; i < types.length; i++)
        {
            if (types[i] != null)
            {
                nonNullTypes.add(types[i]);
            }
        }
        types = nonNullTypes.toArray(new Class[0]);

        if (types.length == 0)
        {
            return null;    // only null types, result is null
        }
        if (types.length == 1)
        {
            return getBoxedType(types[0]);
        }

        // Check if all String
        if (types[0] == String.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != String.class)
                {
                    throw new CoercionException("Cannot coerce to String type " + types[i].getName());
                }
            }
            return String.class;
        }

        // Convert to boxed types
        for (int i = 0; i < types.length; i++)
        {
            types[i] = getBoxedType(types[i]);
        }

        // Check if all boolean
        if (types[0] == Boolean.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != Boolean.class)
                {
                    throw new CoercionException("Cannot coerce to Boolean type " + types[i].getName());
                }
            }
            return Boolean.class;
        }

        // Check if all char
        if (types[0] == Character.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != Character.class)
                {
                    throw new CoercionException("Cannot coerce to Boolean type " + types[i].getName());
                }
            }
            return Character.class;
        }

        // Check if all the same non-Java builtin type, i.e. Java beans etc.
        if (!isJavaBuiltinDataType(types[0]))
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != types[0])
                {
                    throw new CoercionException("Cannot coerce to type " + types[0].getName());
                }
            }
            return types[0];
        }

        // Test for numeric
        if (!isNumeric(types[0]))
        {
            throw new CoercionException("Cannot coerce to numeric type " + types[0].getName());
        }

        // Use arithmatic coercion type as the final authority, considering all types
        Class result = getArithmaticCoercionType(types[0], types[1]);
        int count = 2;
        while(count < types.length)
        {
            result = getArithmaticCoercionType(result, types[count]);
            count++;
        }
        return result;
    }

    /**
     * Returns the class given a fully-qualified class name.
     * @param className is the fully-qualified class name, java primitive types included.
     * @return class for name
     * @throws ClassNotFoundException if the class cannot be found
     */
    public static Class getClassForName(String className) throws ClassNotFoundException
    {
        if (className.equals(boolean.class.getName()))
        {
            return boolean.class;
        }
        if (className.equals(char.class.getName()))
        {
            return char.class;
        }
        if (className.equals(double.class.getName()))
        {
            return double.class;
        }
        if (className.equals(float.class.getName()))
        {
            return float.class;
        }
        if (className.equals(byte.class.getName()))
        {
            return byte.class;
        }
        if (className.equals(short.class.getName()))
        {
            return short.class;
        }
        if (className.equals(int.class.getName()))
        {
            return int.class;
        }
        if (className.equals(long.class.getName()))
        {
            return long.class;
        }
        return Class.forName(className);
    }

    /**
     * Returns the boxed class for the given classname, recognizing all primitive and abbreviations,
     * uppercase and lowercase.
     * <p>
     * Recognizes "int" as Integer.class and "strIng" as String.class, and "Integer" as Integer.class, and so on. 
     * @param className is the name to recognize
     * @return class
     * @throws EventAdapterException is throw if the class cannot be identified
     */
    public static Class getClassForSimpleName(String className)
            throws EventAdapterException
    {
        if (("string".equals(className.toLowerCase().trim())) ||
            ("varchar".equals(className.toLowerCase().trim())) ||
            ("varchar2".equals(className.toLowerCase().trim())))
        {
            return String.class;
        }

        if ("integer".equals(className.toLowerCase().trim()))
        {
            return Integer.class;
        }

        if ("bool".equals(className.toLowerCase().trim()))
        {
            return Boolean.class;
        }

        if ("character".equals(className.toLowerCase().trim()))
        {
            return Character.class;
        }

        // use the boxed type for primitives
        String boxedClassName = JavaClassHelper.getBoxedClassName(className.trim());

        try
        {
            return Class.forName(boxedClassName);
        }
        catch (ClassNotFoundException ex)
        {
        }

        boxedClassName = JavaClassHelper.getBoxedClassName(className.toLowerCase().trim());
        try
        {
            return Class.forName(boxedClassName);
        }
        catch (ClassNotFoundException ex)
        {
            throw new EventAdapterException("Unable to load class '" + boxedClassName + "', class not found", ex);
        }
    }

    /**
     * Returns the class for a Java primitive type name, ignoring case, and considering String as a primitive.
     * @param typeName is a potential primitive Java type, or some other type name
     * @return class for primitive type name, or null if not a primitive type.
     */
    public static Class getPrimitiveClassForName(String typeName)
    {
        typeName = typeName.toLowerCase();
        if (typeName.equals("boolean"))
        {
            return boolean.class;
        }
        if (typeName.equals("char"))
        {
            return char.class;
        }
        if (typeName.equals("double"))
        {
            return double.class;
        }
        if (typeName.equals("float"))
        {
            return float.class;
        }
        if (typeName.equals("byte"))
        {
            return byte.class;
        }
        if (typeName.equals("short"))
        {
            return short.class;
        }
        if (typeName.equals("int"))
        {
            return int.class;
        }
        if (typeName.equals("long"))
        {
            return long.class;
        }
        if (typeName.equals("string"))
        {
            return String.class;
        }
        return null;
    }

    /**
     * Parse the String using the given Java built-in class for parsing.
     * @param clazz is the class to parse the value to
     * @param text is the text to parse
     * @return value matching the type passed in
     */
    public static Object parse(Class clazz, String text)
    {
        Class classBoxed = JavaClassHelper.getBoxedType(clazz);

        if (classBoxed == String.class)
        {
            return text;
        }
        if (classBoxed == Character.class)
        {
            return text.charAt(0);
        }
        if (classBoxed == Boolean.class)
        {
            return BoolValue.parseString(text.toLowerCase().trim());
        }
        if (classBoxed == Byte.class)
        {
            return ByteValue.parseString(text.trim());
        }
        if (classBoxed == Short.class)
        {
            return ShortValue.parseString(text.trim());
        }
        if (classBoxed == Long.class)
        {
            return LongValue.parseString(text.trim());
        }
        if (classBoxed == Float.class)
        {
            return FloatValue.parseString(text.trim());
        }
        if (classBoxed == Double.class)
        {
            return DoubleValue.parseString(text.trim());
        }
        if (classBoxed == Integer.class)
        {
            return IntValue.parseString(text.trim());
        }
        return null;
    }
}
