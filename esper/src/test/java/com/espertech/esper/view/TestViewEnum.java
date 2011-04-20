package com.espertech.esper.view;

import junit.framework.TestCase;

public class TestViewEnum extends TestCase
{
    public void testForName()
    {
        ViewEnum enumValue = ViewEnum.forName(ViewEnum.CORRELATION.getNamespace(), ViewEnum.CORRELATION.getName());
        assertEquals(enumValue, ViewEnum.CORRELATION);

        enumValue = ViewEnum.forName(ViewEnum.CORRELATION.getNamespace(), "dummy");
        assertNull(enumValue);

        enumValue = ViewEnum.forName("dummy", ViewEnum.CORRELATION.getName());
        assertNull(enumValue);
    }
}
