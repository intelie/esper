package com.espertech.esper.util;

import com.espertech.esper.type.*;

public class SimpleTypeParserFactory
{
    /**
     * Returns a parsers for the String value using the given Java built-in class for parsing.
     * @param clazz is the class to parse the value to
     * @return value matching the type passed in
     */
    public static SimpleTypeParser getParser(Class clazz)
    {
        Class classBoxed = JavaClassHelper.getBoxedType(clazz);

        if (classBoxed == String.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String value)
                {
                    return value;
                }
            };
        }
        if (classBoxed == Character.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String value)
                {
                    return value.charAt(0);
                }
            };
        }
        if (classBoxed == Boolean.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return BoolValue.parseString(text.toLowerCase().trim());
                }
            };
        }
        if (classBoxed == Byte.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return ByteValue.parseString(text.trim());
                }
            };
        }
        if (classBoxed == Short.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return ShortValue.parseString(text.trim());
                }
            };
        }
        if (classBoxed == Long.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return LongValue.parseString(text.trim());
                }
            };
        }
        if (classBoxed == Float.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return FloatValue.parseString(text.trim());
                }
            };
        }
        if (classBoxed == Double.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return DoubleValue.parseString(text.trim());
                }
            };
        }
        if (classBoxed == Integer.class)
        {
            return new SimpleTypeParser() {
                public Object parse(String text)
                {
                    return IntValue.parseString(text.trim());
                }
            };
        }
        return null;
    }    
}
