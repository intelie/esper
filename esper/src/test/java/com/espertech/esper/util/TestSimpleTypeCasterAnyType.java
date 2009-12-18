package com.espertech.esper.util;

import com.espertech.esper.support.bean.*;
import junit.framework.TestCase;

public class TestSimpleTypeCasterAnyType extends TestCase
{
    private SimpleTypeCasterAnyType caster;

    public void setUp()
    {
        caster = new SimpleTypeCasterAnyType(ISupportA.class);
    }

    public void testCast()
    {
        assertNull(caster.cast(new Object()));
        assertNull(caster.cast(new SupportBean()));
        assertNotNull(caster.cast(new ISupportABCImpl("","","","")));
        assertNotNull(caster.cast(new ISupportABCImpl("","","","")));
        assertNull(caster.cast(new ISupportBCImpl("","","")));
    }
}
