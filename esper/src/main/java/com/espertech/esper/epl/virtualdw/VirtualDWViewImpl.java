package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.hook.*;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.join.exec.base.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValue;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueRange;
import com.espertech.esper.epl.join.plan.*;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.lookup.SubordPropHashKey;
import com.espertech.esper.epl.lookup.SubordPropRangeKey;
import com.espertech.esper.epl.lookup.SubordTableLookupStrategy;
import com.espertech.esper.epl.named.IndexMultiKey;
import com.espertech.esper.epl.named.IndexedPropDesc;
import com.espertech.esper.filter.Range;
import com.espertech.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class VirtualDWViewImpl extends ViewSupport implements VirtualDWView {

    private static final Log log = LogFactory.getLog(VirtualDWViewImpl.class);

    private final VirtualDataWindow dataExternal;
    private final EventType eventType;
    private final String namedWindowName;

    public VirtualDWViewImpl(VirtualDataWindow dataExternal, EventType eventType, String namedWindowName) {
        this.dataExternal = dataExternal;
        this.eventType = eventType;
        this.namedWindowName = namedWindowName;
    }

    public VirtualDataWindow getVirtualDataWindow() {
        return dataExternal;
    }

    public Pair<IndexMultiKey, EventTable> getSubordinateQueryDesc(List<IndexedPropDesc> hashedProps, List<IndexedPropDesc> btreeProps) {
        List<VirtualDataWindowLookupFieldDesc> hashFields = new ArrayList<VirtualDataWindowLookupFieldDesc>();
        for (IndexedPropDesc hashprop : hashedProps) {
            hashFields.add(new VirtualDataWindowLookupFieldDesc(hashprop.getIndexPropName(), VirtualDataWindowLookupOp.EQUALS, hashprop.getCoercionType()));
        }
        List<VirtualDataWindowLookupFieldDesc> btreeFields = new ArrayList<VirtualDataWindowLookupFieldDesc>();
        for (IndexedPropDesc btreeprop : btreeProps) {
            btreeFields.add(new VirtualDataWindowLookupFieldDesc(btreeprop.getIndexPropName(), null, btreeprop.getCoercionType()));
        }
        VirtualDWEventTable eventTable = new VirtualDWEventTable(hashFields, btreeFields); 
        IndexMultiKey imk = new IndexMultiKey(hashedProps, btreeProps);
        return new Pair<IndexMultiKey, EventTable>(imk, eventTable);
    }

    public SubordTableLookupStrategy getSubordinateLookupStrategy(EventType[] outerStreamTypes, List<SubordPropHashKey> hashKeys, CoercionDesc hashKeyCoercionTypes, List<SubordPropRangeKey> rangeKeys, CoercionDesc rangeKeyCoercionTypes, boolean nwOnTrigger, EventTable eventTable) {
        VirtualDWEventTable noopTable = (VirtualDWEventTable) eventTable;
        for (int i = 0; i < noopTable.getBtreeAccess().size(); i++) {
            VirtualDataWindowLookupOp op = VirtualDataWindowLookupOp.fromOpString(rangeKeys.get(i).getRangeInfo().getType().getStringOp());
            noopTable.getBtreeAccess().get(i).setOperator(op);
        }
        VirtualDataWindowLookup index = dataExternal.getLookup(new VirtualDataWindowLookupContext(noopTable.getHashAccess(), noopTable.getBtreeAccess()));
        checkIndex(index);
        return new SubordTableLookupStrategyVirtualDW(namedWindowName, index, hashKeys, hashKeyCoercionTypes, rangeKeys, rangeKeyCoercionTypes, nwOnTrigger, outerStreamTypes.length);
    }

    public EventTable getJoinIndexTable(QueryPlanIndexItem queryPlanIndexItem) {

        List<VirtualDataWindowLookupFieldDesc> hashFields = new ArrayList<VirtualDataWindowLookupFieldDesc>();
        int count = 0;
        if (queryPlanIndexItem.getIndexProps() != null) {
            for (String indexProp : queryPlanIndexItem.getIndexProps()) {
                Class coercionType = queryPlanIndexItem.getOptIndexCoercionTypes() == null ? null : queryPlanIndexItem.getOptIndexCoercionTypes()[count];
                hashFields.add(new VirtualDataWindowLookupFieldDesc(indexProp, VirtualDataWindowLookupOp.EQUALS, coercionType));
                count++;
            }
        }

        List<VirtualDataWindowLookupFieldDesc> btreeFields = new ArrayList<VirtualDataWindowLookupFieldDesc>();
        count = 0;
        if (queryPlanIndexItem.getRangeProps() != null) {
            for (String btreeprop : queryPlanIndexItem.getRangeProps()) {
                Class coercionType = queryPlanIndexItem.getOptRangeCoercionTypes() == null ? null : queryPlanIndexItem.getOptRangeCoercionTypes()[count];
                btreeFields.add(new VirtualDataWindowLookupFieldDesc(btreeprop, null, coercionType));
                count++;
            }
        }

        return new VirtualDWEventTable(hashFields, btreeFields);
    }

    public JoinExecTableLookupStrategy getJoinLookupStrategy(EventTable eventTable, TableLookupKeyDesc keyDescriptor, int lookupStreamNum) {
        VirtualDWEventTable noopTable = (VirtualDWEventTable) eventTable;
        for (int i = 0; i < noopTable.getHashAccess().size(); i++) {
            QueryGraphValueEntryHashKeyed hashKey = keyDescriptor.getHashes().get(i);
            noopTable.getHashAccess().get(i).setLookupValueType(hashKey.getKeyExpr().getExprEvaluator().getType());
        }
        for (int i = 0; i < noopTable.getBtreeAccess().size(); i++) {
            QueryGraphValueEntryRange range = keyDescriptor.getRanges().get(i);
            VirtualDataWindowLookupOp op = VirtualDataWindowLookupOp.fromOpString(range.getType().getStringOp());
            VirtualDataWindowLookupFieldDesc rangeField = noopTable.getBtreeAccess().get(i);
            rangeField.setOperator(op);
            if (range instanceof QueryGraphValueEntryRangeRelOp) {
                rangeField.setLookupValueType(((QueryGraphValueEntryRangeRelOp) range).getExpression().getExprEvaluator().getType());
            }
            else {
                rangeField.setLookupValueType(((QueryGraphValueEntryRangeIn) range).getExprStart().getExprEvaluator().getType());
            }
        }

        VirtualDataWindowLookup index = dataExternal.getLookup(new VirtualDataWindowLookupContext(noopTable.getHashAccess(), noopTable.getBtreeAccess()));
        checkIndex(index);
        return new JoinExecTableLookupStrategyVirtualDW(namedWindowName, index, keyDescriptor, lookupStreamNum);
    }

    public Pair<IndexMultiKey, EventTable> getFireAndForgetDesc(Set<String> keysAvailable, Set<String> rangesAvailable) {
        List<VirtualDataWindowLookupFieldDesc> hashFields = new ArrayList<VirtualDataWindowLookupFieldDesc>();
        List<IndexedPropDesc> hashIndexedFields = new ArrayList<IndexedPropDesc>();
        for (String hashprop : keysAvailable) {
            hashFields.add(new VirtualDataWindowLookupFieldDesc(hashprop, VirtualDataWindowLookupOp.EQUALS, null));
            hashIndexedFields.add(new IndexedPropDesc(hashprop, null));
        }

        List<VirtualDataWindowLookupFieldDesc> btreeFields = new ArrayList<VirtualDataWindowLookupFieldDesc>();
        List<IndexedPropDesc> btreeIndexedFields = new ArrayList<IndexedPropDesc>();
        for (String btreeprop : rangesAvailable) {
            btreeFields.add(new VirtualDataWindowLookupFieldDesc(btreeprop, null, null));
            btreeIndexedFields.add(new IndexedPropDesc(btreeprop, null));
        }

        VirtualDWEventTable noopTable = new VirtualDWEventTable(hashFields, btreeFields);
        IndexMultiKey imk = new IndexMultiKey(hashIndexedFields, btreeIndexedFields);

        return new Pair<IndexMultiKey, EventTable>(imk, noopTable);
    }

    public Collection<EventBean> getFireAndForgetData(EventTable eventTable, Object[] keyValues, RangeIndexLookupValue[] rangeValues) {
        VirtualDWEventTable noopTable = (VirtualDWEventTable) eventTable;
        for (int i = 0; i < noopTable.getBtreeAccess().size(); i++) {
            RangeIndexLookupValueRange range = (RangeIndexLookupValueRange) rangeValues[i];
            VirtualDataWindowLookupOp op = VirtualDataWindowLookupOp.fromOpString(range.getOperator().getStringOp());
            noopTable.getBtreeAccess().get(i).setOperator(op);
        }

        Object[] keys = new Object[keyValues.length + rangeValues.length];
        for (int i = 0; i < keyValues.length; i++) {
            keys[i] = keyValues[i];
            noopTable.getHashAccess().get(i).setLookupValueType(keyValues[i] == null ? null : keyValues[i].getClass());
        }
        int offset = keyValues.length;
        for (int j = 0; j < rangeValues.length; j++) {
            Object rangeValue = rangeValues[j].getValue();
            if (rangeValue instanceof Range) {
                Range range = (Range) rangeValue;
                keys[j + offset] = new VirtualDataWindowKeyRange(range.getLowEndpoint(), range.getHighEndpoint());
                noopTable.getBtreeAccess().get(j).setLookupValueType(range.getLowEndpoint() == null ? null : range.getLowEndpoint().getClass());
            }
            else {
                keys[j + offset] = rangeValue;
                noopTable.getBtreeAccess().get(j).setLookupValueType(rangeValue == null ? null : rangeValue.getClass());
            }
        }

        VirtualDataWindowLookup index = dataExternal.getLookup(new VirtualDataWindowLookupContext(noopTable.getHashAccess(), noopTable.getBtreeAccess()));
        checkIndex(index);
        if (index == null) {
            throw new EPException("Exception obtaining index from virtual data window '" + namedWindowName + "'");
        }

        Set<EventBean> events = null;
        try {
            events = index.lookup(keys);
        }
        catch (RuntimeException ex) {
            log.warn("Exception encountered invoking virtual data window external index for window '" + namedWindowName + "': " + ex.getMessage(), ex);
        }
        return events;
    }

    private void checkIndex(VirtualDataWindowLookup index) {
        if (index == null) {
            throw new EPException("Exception obtaining index lookup from virtual data window, the implementation has returned a null index");
        }
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
        dataExternal.update(newData, oldData);
    }

    public EventType getEventType() {
        return eventType;
    }

    public void destroy() {
        dataExternal.destroy();
    }

    public Iterator<EventBean> iterator() {
        return Collections.<EventBean>emptyList().iterator();
    }
}
