/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.type;

import net.esper.collection.MultiKey;

import java.util.Map;
import java.util.HashMap;

/**
 * Enum representing relational types of operation.
 */
public enum RelationalOpEnum
{
    /**
     * Greater then.
     */
    GT (">"),

    /**
     * Greater equals.
     */
    GE (">="),

    /**
     * Lesser then.
     */
    LT ("<"),

    /**
     * Lesser equals.
     */
    LE ("<=");

    private static Map<MultiKey<Object>, RelationalOpEnum.Computer> computers;

    private String expressionText;

    private RelationalOpEnum(String expressionText)
    {
        this.expressionText = expressionText;
    }

    /**
     * Parses the operator and returns an enum for the operator.
     * @param op to parse
     * @return enum representing relational operation
     */
    public static RelationalOpEnum parse(String op)
    {
        if (op.equals("<"))
        {
            return LT;
        }
        else if (op.equals(">"))
        {
            return GT;
        }
        else if ((op.equals(">=")) || op.equals("=>"))
        {
            return GE;
        }
        else if ((op.equals("<=")) || op.equals("=<"))
        {
            return LE;
        }
        else throw new IllegalArgumentException("Invalid relational operator '" + op + "'");
    }

    static
    {
        computers = new HashMap<MultiKey<Object>, RelationalOpEnum.Computer>();
        computers.put(new MultiKey<Object>(new Object[] {String.class, GT}), new GTStringComputer());
        computers.put(new MultiKey<Object>(new Object[] {String.class, GE}), new GEStringComputer());
        computers.put(new MultiKey<Object>(new Object[] {String.class, LT}), new LTStringComputer());
        computers.put(new MultiKey<Object>(new Object[] {String.class, LE}), new LEStringComputer());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, GT}), new GTLongComputer());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, GE}), new GELongComputer());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, LT}), new LTLongComputer());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, LE}), new LELongComputer());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, GT}), new GTDoubleComputer());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, GE}), new GEDoubleComputer());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, LT}), new LTDoubleComputer());
        computers.put(new MultiKey<Object>(new Object[] {Double.class, LE}), new LEDoubleComputer());
    }

    /**
     * Returns the computer to use for the relational operation based on the coercion type.
     * @param coercedType is the object type
     * @return computer for performing the relational op
     */
    public RelationalOpEnum.Computer getComputer(Class coercedType)
    {
        if ( (coercedType != Double.class) &&
             (coercedType != Long.class) &&
             (coercedType != String.class))
        {
            throw new IllegalArgumentException("Unsupported type for relational op compare, type " + coercedType);
        }
        MultiKey<Object> key = new MultiKey<Object>(new Object[] {coercedType, this});
        return computers.get(key);
    }

    /**
     * Computer for relational op.
     */
    public interface Computer
    {
        /**
         * Compares objects and returns boolean indicating larger (true) or smaller (false).
         * @param objOne object to compare
         * @param objTwo object to compare
         * @return true if larger, false if smaller
         */
        public boolean compare(Object objOne, Object objTwo);
    }

    /**
     * Computer for relational op compare.
     */
    public static class GTStringComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            String s1 = (String) objOne;
            String s2 = (String) objTwo;
            int result = s1.compareTo(s2);
            return result > 0;
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class GEStringComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            String s1 = (String) objOne;
            String s2 = (String) objTwo;
            return s1.compareTo(s2) >= 0;
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class LEStringComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            String s1 = (String) objOne;
            String s2 = (String) objTwo;
            return s1.compareTo(s2) <= 0;
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class LTStringComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            String s1 = (String) objOne;
            String s2 = (String) objTwo;
            return s1.compareTo(s2) < 0;
        }
    }

    /**
     * Computer for relational op compare.
     */
    public static class GTLongComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.longValue() > s2.longValue();
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class GELongComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.longValue() >= s2.longValue();
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class LTLongComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.longValue() < s2.longValue();
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class LELongComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.longValue() <= s2.longValue();
        }
    }

    /**
     * Computer for relational op compare.
     */
    public static class GTDoubleComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.doubleValue() > s2.doubleValue();
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class GEDoubleComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.doubleValue() >= s2.doubleValue();
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class LTDoubleComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.doubleValue() < s2.doubleValue();
        }
    }
    /**
     * Computer for relational op compare.
     */
    public static class LEDoubleComputer implements Computer
    {
        public boolean compare(Object objOne, Object objTwo)
        {
            Number s1 = (Number) objOne;
            Number s2 = (Number) objTwo;
            return s1.doubleValue() <= s2.doubleValue();
        }
    }

    /**
     * Returns string rendering of enum.
     * @return relational op string
     */
    public String getExpressionText()
    {
        return expressionText;
    }
}
