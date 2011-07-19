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

import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestSuperIterator extends TestCase {

    public void testEmpty() {
        SuperIterator<String> it = new SuperIterator<String>(make(null), make(null));
        assertFalse(it.hasNext());
        try {
            it.next();
            fail();
        }
        catch (NoSuchElementException ex) {
        }
    }

    public void testFlow() {
        SuperIterator<String> it = new SuperIterator<String>(make("a"), make(null));
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {"a"});

        it = new SuperIterator<String>(make("a,b"), make(null));
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {"a", "b"});

        it = new SuperIterator<String>(make("a"), make("b"));
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {"a", "b"});

        it = new SuperIterator<String>(make(null), make("a,b"));
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {"a", "b"});
    }

    private Iterator<String> make(String csv) {
        if ((csv == null) || (csv.length() == 0)) {
            return new NullIterator<String>();
        }
        String[] fields = csv.split(",");
        return Arrays.asList(fields).iterator();
    }

}
