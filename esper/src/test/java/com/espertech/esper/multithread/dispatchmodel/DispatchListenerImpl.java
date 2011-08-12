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

package com.espertech.esper.multithread.dispatchmodel;

import java.util.ArrayList;
import java.util.List;

public class DispatchListenerImpl implements DispatchListener
{
    private ArrayList<int[][]> received = new ArrayList<int[][]>();

    public synchronized void dispatched(int[][] objects)
    {
        received.add(objects);
    }

    public List<int[][]> getReceived()
    {
        return received;
    }
}
