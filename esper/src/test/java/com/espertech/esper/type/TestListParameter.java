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

package com.espertech.esper.type;

import junit.framework.TestCase;

import java.util.Set;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.type.FrequencyParameter;
import com.espertech.esper.type.ListParameter;
import com.espertech.esper.type.IntParameter;

public class TestListParameter extends TestCase
{
    private ListParameter listParam;

    public void setUp()
    {
        listParam = new ListParameter();
        listParam.add(new IntParameter(5));
        listParam.add(new FrequencyParameter(3));
    }

    public void testIsWildcard()
    {
        // Wildcard is expected to make only a best-guess effort, not be perfect
        assertTrue(listParam.isWildcard(5, 5));
        assertFalse(listParam.isWildcard(6, 10));
    }

    public void testGetValues()
    {
        Set<Integer> result = listParam.getValuesInRange(1, 8);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 5, 6}, result);
    }
}
