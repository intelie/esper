package com.espertech.esper.util;

import junit.framework.TestCase;

public class TestLevenshteinDistance extends TestCase
{
    public void testDistance()
    {
        int result = LevenshteinDistance.computeLevenshteinDistance("abc", "abcd");
        System.out.println(result);
    }
}
