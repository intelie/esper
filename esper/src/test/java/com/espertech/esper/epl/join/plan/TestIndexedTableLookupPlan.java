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

package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.epl.expression.ExprIdentNodeImpl;
import com.espertech.esper.epl.join.exec.base.IndexedTableLookupStrategy;
import com.espertech.esper.epl.join.exec.base.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.virtualdw.VirtualDWView;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import junit.framework.TestCase;

import java.util.*;

public class TestIndexedTableLookupPlan extends TestCase
{
    private PropertyIndexedEventTable propertyMapEventIndex;
    private EventType[] types;

    public void setUp()
    {
        types = new EventType[] { SupportEventTypeFactory.createBeanType(SupportBean.class) };

        propertyMapEventIndex = new PropertyIndexedEventTable(1, types[0], new String[] {"intBoxed"});
    }

    public void testLookup()
    {
        List<QueryGraphValueEntryHashKeyed> keys = new ArrayList<QueryGraphValueEntryHashKeyed>();
        keys.add(new QueryGraphValueEntryHashKeyedProp(new ExprIdentNodeImpl(types[0], "intBoxed", 0), "intBoxed"));
        IndexedTableLookupPlanMulti spec = new IndexedTableLookupPlanMulti(0, 1, "idx1", keys);

        Map<String,EventTable>[] indexes = new Map[2];
        indexes[0] = new HashMap<String,EventTable>();
        indexes[1] = new HashMap<String,EventTable>();
        indexes[1].put("idx1", propertyMapEventIndex);

        JoinExecTableLookupStrategy lookupStrategy = spec.makeStrategy(indexes, types, new VirtualDWView[2]);

        IndexedTableLookupStrategy strategy = (IndexedTableLookupStrategy) lookupStrategy;
        assertEquals(types[0], strategy.getEventType());
        assertEquals(propertyMapEventIndex, strategy.getIndex());
        assertTrue(Arrays.equals(new String[] {"intBoxed"}, strategy.getProperties()));
    }
}
