package net.esper.type;

import net.esper.collection.MultiKey;

import java.util.Map;
import java.util.HashMap;

/**
 * Enumeration for the type of arithmatic to use.
 */
public enum ArithTypeEnum
{
    /**
     * Plus.
     */
    ADD ("+"),

    /**
     * Minus.
     */
    SUBTRACT ("-"),

    /**
     * Divide.
     */
    DIVIDE ("/"),

    /**
     * Multiply.
     */
    MULTIPLY ("*");

    private static Map<MultiKey<Object>, Computer> computers;

    static
    {
        computers = new HashMap<MultiKey<Object>, Computer>();
        computers.put(new MultiKey<Object>(new Object[] {Double.class, ADD}), new AddDouble());
        computers.put(new MultiKey<Object>(new Object[] {Float.class, ADD}), new AddFloat());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, ADD}), new AddLong());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, ADD}), new AddInt());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, SUBTRACT}), new SubtractDouble());
        computers.put(new MultiKey<Object>(new Object[] {Float.class, SUBTRACT}), new SubtractFloat());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, SUBTRACT}), new SubtractLong());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, SUBTRACT}), new SubtractInt());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, DIVIDE}), new DivideDouble());
        computers.put(new MultiKey<Object>(new Object[] {Float.class, DIVIDE}), new DivideFloat());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, DIVIDE}), new DivideLong());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, DIVIDE}), new DivideInt());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, MULTIPLY}), new MultiplyDouble());
        computers.put(new MultiKey<Object>(new Object[] {Float.class, MULTIPLY}), new MultiplyFloat());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, MULTIPLY}), new MultiplyLong());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, MULTIPLY}), new MultiplyInt());
    }

    /**
     * Interface for number cruncher.
     */
    public interface Computer
    {
        /**
         * Computes using the 2 numbers a result number.
         * @param d1 is the first number
         * @param d2 is the second number
         * @return result
         */
        public Number compute(Number d1, Number d2);
    }

    private String expressionText;

    private ArithTypeEnum(String expressionText)
    {
        this.expressionText = expressionText;
    }

    /**
     * Returns number cruncher for the target coercion type.
     * @param coercedType - target type
     * @return number cruncher
     */
    public Computer getComputer(Class coercedType)
    {
        if ( (coercedType != Double.class) &&
             (coercedType != Float.class) &&
             (coercedType != Long.class) &&
             (coercedType != Integer.class))
        {
            throw new IllegalArgumentException("Expected base numeric type for computation result but got type " + coercedType);
        }
        MultiKey<Object> key = new MultiKey<Object>(new Object[] {coercedType, this});
        return computers.get(key);
    }

    /**
     * Computer for type-specific arith. operations.
     */
    public static class AddDouble implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.doubleValue() + d2.doubleValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class AddFloat implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.floatValue() + d2.floatValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class AddLong implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.longValue() + d2.longValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class AddInt implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.intValue() + d2.intValue();
            return result;
        }
    }

    /**
     * Computer for type-specific arith. operations.
     */
    public static class SubtractDouble implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.doubleValue() - d2.doubleValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class SubtractFloat implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.floatValue() - d2.floatValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class SubtractLong implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.longValue() - d2.longValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class SubtractInt implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.intValue() - d2.intValue();
            return result;
        }
    }

    /**
     * Computer for type-specific arith. operations.
     */
    public static class DivideDouble implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.doubleValue() / d2.doubleValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class DivideFloat implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.floatValue() / d2.floatValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class DivideLong implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.longValue() / d2.longValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class DivideInt implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.intValue() / d2.intValue();
            return result;
        }
    }

    /**
     * Computer for type-specific arith. operations.
     */
    public static class MultiplyDouble implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.doubleValue() * d2.doubleValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class MultiplyFloat implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.floatValue() * d2.floatValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class MultiplyLong implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.longValue() * d2.longValue();
            return result;
        }
    }
    /**
     * Computer for type-specific arith. operations.
     */
    public static class MultiplyInt implements Computer
    {
        public Number compute(Number d1, Number d2)
        {
            Number result = d1.intValue() * d2.intValue();
            return result;
        }
    }

    /**
     * Returns string representation of enum.
     * @return text for enum
     */
    public String getExpressionText()
    {
        return expressionText;
    }
}
