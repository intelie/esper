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

package com.espertech.esper.support.epl.join;

import com.espertech.esper.epl.join.rep.Repository;
import com.espertech.esper.epl.join.rep.Cursor;
import com.espertech.esper.epl.join.rep.SingleCursorIterator;
import com.espertech.esper.client.EventBean;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;

public class SupportRepositoryImpl implements Repository
{
    private List<Cursor> cursorList = new LinkedList<Cursor>();
    private List<Set<EventBean>> lookupResultsList = new LinkedList<Set<EventBean>>();
    private List<Integer> resultStreamList = new LinkedList<Integer>();

    public Iterator<Cursor> getCursors(int lookupStream)
    {
        return new SingleCursorIterator(new Cursor(SupportJoinResultNodeFactory.makeEvent(), 0, null));
    }

    public void addResult(Cursor cursor, Set<EventBean> lookupResults, int resultStream)
    {
        cursorList.add(cursor);
        lookupResultsList.add(lookupResults);
        resultStreamList.add(resultStream);
    }

    public List<Cursor> getCursorList()
    {
        return cursorList;
    }

    public List<Set<EventBean>> getLookupResultsList()
    {
        return lookupResultsList;
    }

    public List<Integer> getResultStreamList()
    {
        return resultStreamList;
    }
}
