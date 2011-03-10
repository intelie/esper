/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.util;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Utility for handling collection or array tasks.
 */
public class CollectionUtil<T>
{
    public static Object expandAddElement(Object array, Object[] elementsToAdd) {
        Class cl = array.getClass();
        if (!cl.isArray()) return null;
        int length = Array.getLength(array);
        int newLength = length + elementsToAdd.length;
        Class componentType = array.getClass().getComponentType();
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(array, 0, newArray, 0, length);
        for (int i = 0; i < elementsToAdd.length; i++) {
            Array.set(newArray, length + i, elementsToAdd[i]);
        }
        return newArray;
    }

    public static int[] addValue(int[] ints, int i) {
        int[] copy = new int[ints.length + 1];
        System.arraycopy(ints, 0, copy, 0, ints.length);
        copy[ints.length] = i;
        return copy;
    }

    public static int findItem(String[] items, String item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns an array of integer values from the set of integer values
     * @param set to return array for
     * @return array
     */
    public static int[] intArray(Set<Integer> set)
    {
        if (set == null)
        {
            return new int[0];
        }
        int[] result = new int[set.size()];
        int index = 0;
        for (Integer value : set) {
            result[index++] = value;
        }
        return result;
    }

    public static String[] copySortArray(String[] values) {
        if (values == null) {
            return null;
        }
        String[] copy = new String[values.length];
        System.arraycopy(values, 0, copy, 0, values.length);
        Arrays.sort(copy);
        return copy;
    }

    public static boolean sortCompare(String[] valuesOne, String[] valuesTwo) {
        if (valuesOne == null) {
            return valuesTwo == null;
        }
        if (valuesTwo == null) {
            return false;
        }
        String[] copyOne = copySortArray(valuesOne);
        String[] copyTwo = copySortArray(valuesTwo);
        return Arrays.equals(copyOne, copyTwo);
    }

    /**
     * Returns a list of the elements invoking toString on non-null elements.
     * @param set to render
     * @param <T> type
     * @return comma-separate list of values (no escape)
     */
    public static <T> String toString(Set<T> set)
    {
        if (set == null)
        {
            return "null";
        }
        if (set.isEmpty())
        {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        String delimiter = "";
        for (T t : set)
        {
            if (t == null)
            {
                continue;
            }
            buf.append(delimiter);
            buf.append(t);
            delimiter = ", ";
        }
        return buf.toString();
    }

    public static boolean compare(String[] otherIndexProps, String[] thisIndexProps) {
        if (otherIndexProps != null && thisIndexProps != null) {
            return Arrays.equals(otherIndexProps, thisIndexProps);
        }
        return otherIndexProps == null && thisIndexProps == null;
    }
}