package com.espertech.esper.filter;

import junit.framework.TestCase;

public class TestFilterServiceProvider extends TestCase
{
    public void testGetService()
    {
        FilterService serviceOne = FilterServiceProvider.newService();
        FilterService serviceTwo = FilterServiceProvider.newService();

        assertTrue(serviceOne != null);
        assertTrue(serviceOne != serviceTwo);
    }
}
