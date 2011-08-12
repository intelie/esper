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

package com.espertech.esper.support.util;

import java.util.List;
import java.util.ArrayList;

public class SupportSubscriberMRD
{
    private boolean isInvoked;
    private List<Object[][]> insertStreamList = new ArrayList<Object[][]>();
    private List<Object[][]> removeStreamList = new ArrayList<Object[][]>();

    public void update(Object[][] insertStream, Object[][] removeStream)
    {
        isInvoked = true;
        insertStreamList.add(insertStream);
        removeStreamList.add(insertStream);
    }

    public List<Object[][]> getInsertStreamList() {
        return insertStreamList;
    }

    public void setInsertStreamList(List<Object[][]> insertStreamList) {
        this.insertStreamList = insertStreamList;
    }

    public List<Object[][]> getRemoveStreamList() {
        return removeStreamList;
    }

    public void setRemoveStreamList(List<Object[][]> removeStreamList) {
        this.removeStreamList = removeStreamList;
    }

    public void reset()
    {
        isInvoked = false;
        insertStreamList.clear();
        removeStreamList.clear();
    }

    public boolean isInvoked()
    {
        return isInvoked;
    }

    public boolean getAndClearIsInvoked()
    {
        boolean invoked = isInvoked;
        isInvoked = false;
        return invoked;
    }
}
