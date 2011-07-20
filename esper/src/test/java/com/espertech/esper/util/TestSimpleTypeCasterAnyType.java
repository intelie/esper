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
