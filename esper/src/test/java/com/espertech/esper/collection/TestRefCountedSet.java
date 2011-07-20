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

package com.espertech.esper.collection;

import junit.framework.TestCase;

public class TestRefCountedSet extends TestCase
{
    private RefCountedSet<String> refSet;

    public void setUp()
    {
        refSet = new RefCountedSet<String>();
    }

    public void testAdd()
    {
        assertTrue(refSet.add("a"));
        assertEquals(1, refSet.size());

        assertFalse(refSet.add("a"));
        assertEquals(2, refSet.size());

        assertTrue(refSet.add("A"));
        assertEquals(3, refSet.size());
    }

    public void testRemove()
    {
        refSet.add("a");
        refSet.add("a");
        refSet.add("a");
        assertEquals(3, refSet.size());
        assertFalse(refSet.remove("a"));
        assertEquals(2, refSet.size());
        assertFalse(refSet.remove("a"));
        assertEquals(1, refSet.size());
        assertTrue(refSet.remove("a"));
        assertEquals(0, refSet.size());

        refSet.add("a");
        assertTrue(refSet.remove("a"));

        refSet.add("b");
        refSet.add("b");
        assertFalse(refSet.remove("b"));
        assertTrue(refSet.remove("b"));
        
        try
        {
            refSet.remove("c");
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            refSet.add("a");
            refSet.remove("a");
            refSet.remove("a");
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        refSet.add("C");
        refSet.add("C");
        assertTrue(refSet.removeAll("C"));
        try
        {
            refSet.remove("C");
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
        assertFalse(refSet.removeAll("C"));
    }
}
