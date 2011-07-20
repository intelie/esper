/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.util;

import junit.framework.TestCase;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Arrays;

public class TestCollectionUtil extends TestCase
{
    public void testCopySort() {
        Object[][] testdata = new Object[][] {
                {new String[]{"a", "b"}, new String[] {"a", "b"}},
                {new String[]{"b", "a"}, new String[] {"a", "b"}},
                {new String[]{"a"}, new String[] {"a"}},
                {new String[]{"c", "b", "a"}, new String[] {"a", "b", "c"}},
                {new String[0], new String[0]},
              };

        for (int i = 0; i < testdata.length; i++)
        {
            String[] expected = (String[]) testdata[i][1];
            String[] input = (String[]) testdata[i][0];
            String[] received = CollectionUtil.copySortArray(input);
            if (!Arrays.equals(expected, received)) {
                fail("Failed for input " + Arrays.toString(input) + " expected " + Arrays.toString(expected) + " received " + Arrays.toString(received));
            }
            assertNotSame(input, expected);
        }
    }

    public void testCompare()
    {
        Object[][] testdata = new Object[][] {
                {new String[]{"a", "b"}, new String[]{"a", "b"}, true},
                {new String[]{"a"}, new String[]{"a", "b"}, false},
                {new String[]{"a"}, new String[]{"a"}, true},
                {new String[]{"b"}, new String[]{"a"}, false},
                {new String[]{"b", "a"}, new String[]{"a", "b"}, true},
                {new String[]{"a", "b", "b"}, new String[]{"a", "b"}, false},
                {new String[]{"a", "b", "b"}, new String[]{"b", "a", "b"}, true},
                {new String[0], new String[0], true},
              };

        for (int i = 0; i < testdata.length; i++)
        {
            String[] left = (String[]) testdata[i][0];
            String[] right = (String[]) testdata[i][1];
            boolean expected = (Boolean) testdata[i][2];
            assertEquals("Failed for input " + Arrays.toString(left), expected, CollectionUtil.sortCompare(left, right));
            assertTrue(Arrays.equals(left, (String[]) testdata[i][0]));
            assertTrue(Arrays.equals(right, (String[]) testdata[i][1]));
        }
    }

    public void testToString()
    {
        Object[][] testdata = new Object[][] {
                {new String[]{"a", "b"}, "a, b"},
                {new String[]{"a"}, "a"},
                {new String[]{""}, ""},
                {new String[]{"", ""}, ""},
                {new String[]{null, "b"}, "b"},
                {new String[0], ""},
                {null, "null"}
              };

        for (int i = 0; i < testdata.length; i++)
        {
            String expected = (String) testdata[i][1];
            String[] input = (String[]) testdata[i][0];
            assertEquals("Failed for input " + Arrays.toString(input), expected, CollectionUtil.toString(toSet(input)));
        }
    }

    private Set<String> toSet(String[] arr)
    {
        if (arr == null)
        {
            return null;
        }
        if (arr.length == 0)
        {
            return new HashSet<String>();
        }
        Set<String> set = new LinkedHashSet<String>();
        for (String a : arr)
        {
            set.add(a);
        }
        return set;
    }
}
