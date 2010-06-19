package com.espertech.esper.util;

import junit.framework.TestCase;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Arrays;

public class TestCollectionUtil extends TestCase
{
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
