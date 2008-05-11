package com.espertech.esper.util;

/**
 * Factory for casters, which take an object and safely cast to a given type, performing coercion or dropping
 * precision if required.
 */
public class SimpleTypeCasterFactory
{
    /**
     * Returns a caster that casts to a target type.
     * @param targetType to cast to
     * @return caster for casting objects to the required type
     */
    public static SimpleTypeCaster getCaster(Class targetType)
    {
        targetType = JavaClassHelper.getBoxedType(targetType);
        if (targetType == Integer.class)
        {
            return new IntCaster();
        }
        else if (targetType == Long.class)
        {
            return new LongCaster();
        }
        else if (targetType == Double.class)
        {
            return new DoubleCaster();
        }
        else if (targetType == Float.class)
        {
            return new FloatCaster();
        }
        else if (targetType == Short.class)
        {
            return new ShortCaster();
        }
        else if (targetType == Byte.class)
        {
            return new ByteCaster();
        }
        else
        {
            return new SimpleTypeCasterAnyType(targetType);
        }
    }

    /**
     * Cast implementation for numeric values.
     */
    private static class DoubleCaster implements SimpleTypeCaster
    {
        public Object cast(Object object)
        {
            return ((Number) object).doubleValue();
        }

        public boolean isNumericCast()
        {
            return true;
        }
    }

    /**
     * Cast implementation for numeric values.
     */
    private static class FloatCaster implements SimpleTypeCaster
    {
        public Object cast(Object object)
        {
            return ((Number) object).floatValue();
        }

        public boolean isNumericCast()
        {
            return true;
        }
    }

    /**
     * Cast implementation for numeric values.
     */
    private static class LongCaster implements SimpleTypeCaster
    {
        public Object cast(Object object)
        {
            return ((Number) object).longValue();
        }

        public boolean isNumericCast()
        {
            return true;
        }
    }

    /**
     * Cast implementation for numeric values.
     */
    private static class IntCaster implements SimpleTypeCaster
    {
        public Object cast(Object object)
        {
            return ((Number) object).intValue();
        }

        public boolean isNumericCast()
        {
            return true;
        }
    }

    /**
     * Cast implementation for numeric values.
     */
    private static class ShortCaster implements SimpleTypeCaster
    {
        public Object cast(Object object)
        {
            return ((Number) object).shortValue();
        }

        public boolean isNumericCast()
        {
            return true;
        }
    }

    /**
     * Cast implementation for numeric values.
     */
    private static class ByteCaster implements SimpleTypeCaster
    {
        public Object cast(Object object)
        {
            return ((Number) object).byteValue();
        }

        public boolean isNumericCast()
        {
            return true;
        }
    }
}
