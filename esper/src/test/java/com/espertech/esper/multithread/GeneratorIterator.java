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

package com.espertech.esper.multithread;

import com.espertech.esper.support.bean.SupportBean;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GeneratorIterator implements Iterator<Object>
{
    private final int maxNumEvents;
    private int numEvents;

    public GeneratorIterator(int maxNumEvents)
    {
        this.maxNumEvents = maxNumEvents;
    }

    public boolean hasNext()
    {
        if (numEvents < maxNumEvents)
        {
            return true;
        }
        return false;
    }

    public Object next()
    {     
        if (numEvents >= maxNumEvents)
        {
            throw new NoSuchElementException();
        }
        SupportBean bean = new SupportBean(Integer.toString(numEvents), numEvents);
        numEvents++;
        return bean;
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
