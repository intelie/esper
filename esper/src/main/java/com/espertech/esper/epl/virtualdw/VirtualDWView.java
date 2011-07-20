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

package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.join.exec.base.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValue;
import com.espertech.esper.epl.join.plan.CoercionDesc;
import com.espertech.esper.epl.join.plan.QueryPlanIndexItem;
import com.espertech.esper.epl.join.plan.TableLookupKeyDesc;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.lookup.SubordPropHashKey;
import com.espertech.esper.epl.lookup.SubordPropPlan;
import com.espertech.esper.epl.lookup.SubordPropRangeKey;
import com.espertech.esper.epl.lookup.SubordTableLookupStrategy;
import com.espertech.esper.epl.named.IndexMultiKey;
import com.espertech.esper.epl.named.IndexedPropDesc;
import com.espertech.esper.epl.spec.CreateIndexDesc;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface VirtualDWView {
    public Pair<IndexMultiKey,EventTable> getSubordinateQueryDesc(List<IndexedPropDesc> hashedProps, List<IndexedPropDesc> btreeProps);
    public SubordTableLookupStrategy getSubordinateLookupStrategy(String accessedByStatementName, EventType[] outerStreamTypes, List<SubordPropHashKey> hashKeys, CoercionDesc hashKeyCoercionTypes, List<SubordPropRangeKey> rangeKeys, CoercionDesc rangeKeyCoercionTypes, boolean nwOnTrigger, EventTable eventTable, SubordPropPlan joinDesc, boolean forceTableScan);

    public EventTable getJoinIndexTable(QueryPlanIndexItem queryPlanIndexItem);
    public JoinExecTableLookupStrategy getJoinLookupStrategy(EventTable eventTable, TableLookupKeyDesc keyDescriptor, int lookupStreamNum);

    public Pair<IndexMultiKey,EventTable> getFireAndForgetDesc(Set<String> keysAvailable, Set<String> rangesAvailable);
    public Collection<EventBean> getFireAndForgetData(EventTable eventTable, Object[] keyValues, RangeIndexLookupValue[] rangeValues);

    public VirtualDataWindow getVirtualDataWindow();

    public void destroy();

    public void handleStartIndex(CreateIndexDesc spec);
    public void handleStopIndex(CreateIndexDesc spec);
    public void handleStopWindow();
}
