/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.join.plan.QueryPlanIndexItem;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.EventTableFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * A repository of index tables for use with a single named window and all it's deleting statements that
 * may use the indexes to correlate triggering events with indexed events of the named window.
 * <p>
 * Maintains index tables and keeps a reference count for user. Allows reuse of indexes for multiple
 * deleting statements.
 */
public class NamedWindowIndexRepository
{
    private static final Log log = LogFactory.getLog(NamedWindowIndexRepository.class);

    private List<EventTable> tables;
    private Map<IndexMultiKey, Pair<EventTable, Integer>> tableIndexesRefCount;

    /**
     * Ctor.
     */
    public NamedWindowIndexRepository()
    {
        tables = new ArrayList<EventTable>();
        tableIndexesRefCount = new HashMap<IndexMultiKey, Pair<EventTable, Integer>>();
    }

    /**
     * Create a new index table or use an existing index table, by matching the
     * join descriptor properties to an existing table.
     * @param hashProps must be in sorted natural order and define the properties joined
     * @param btreeProps
     * @param prefilledEvents is the events to enter into a new table, if a new table is created
     * @param indexedType is the type of event to hold in the index
     * @param mustCoerce is an indicator whether coercion is required or not.
     * @return new or existing index table
     */
    public Pair<IndexMultiKey, EventTable> addTableCreateOrReuse(List<IndexedPropDesc> hashProps,
                               List<IndexedPropDesc> btreeProps,
                               Iterable<EventBean> prefilledEvents,
                               EventType indexedType,
                               boolean mustCoerce)
    {
        if (hashProps.isEmpty() && btreeProps.isEmpty()) {
            throw new IllegalArgumentException("Invalid zero element list for hash and btree columns");
        }

        // Get an existing table, if any
        IndexMultiKey indexPropKeyMatch = findExactMatchNameAndType(tableIndexesRefCount.keySet(), hashProps, btreeProps);
        if (indexPropKeyMatch != null)
        {
            Pair<EventTable, Integer> refTablePair = tableIndexesRefCount.get(indexPropKeyMatch);
            refTablePair.setSecond(refTablePair.getSecond() + 1);
            return new Pair<IndexMultiKey, EventTable>(indexPropKeyMatch, refTablePair.getFirst());
        }

        IndexMultiKey indexPropKey = new IndexMultiKey(hashProps, btreeProps);

        IndexedPropDesc[] indexedPropDescs = hashProps.toArray(new IndexedPropDesc[hashProps.size()]);
        String[] indexProps = IndexedPropDesc.getIndexProperties(indexedPropDescs);
        Class[] indexCoercionTypes = IndexedPropDesc.getCoercionTypes(indexedPropDescs);
        if (!mustCoerce) {
            indexCoercionTypes = null;
        }

        IndexedPropDesc[] rangePropDescs = btreeProps.toArray(new IndexedPropDesc[btreeProps.size()]);
        String[] rangeProps = IndexedPropDesc.getIndexProperties(rangePropDescs);
        Class[] rangeCoercionTypes = IndexedPropDesc.getCoercionTypes(rangePropDescs);

        QueryPlanIndexItem indexItem = new QueryPlanIndexItem(indexProps, indexCoercionTypes, rangeProps, rangeCoercionTypes);
        EventTable table = EventTableFactory.buildIndex(0, indexItem, indexedType, true);

        // fill table since its new
        EventBean[] events = new EventBean[1];
        for (EventBean prefilledEvent : prefilledEvents)
        {
            events[0] = prefilledEvent;
            table.add(events);
        }

        // add table
        tables.add(table);

        // add index, reference counted
        tableIndexesRefCount.put(indexPropKey, new Pair<EventTable, Integer>(table, 1));

        return new Pair<IndexMultiKey, EventTable>(indexPropKey, table);
    }

    private IndexMultiKey findExactMatchNameAndType(Set<IndexMultiKey> indexMultiKeys, List<IndexedPropDesc> hashProps, List<IndexedPropDesc> btreeProps) {
        for (IndexMultiKey existing : indexMultiKeys) {
            if (isExactMatch(existing, hashProps, btreeProps)) {
                return existing;
            }
        }
        return null;
    }

    private boolean isExactMatch(IndexMultiKey existing, List<IndexedPropDesc> hashProps, List<IndexedPropDesc> btreeProps) {
        boolean keyPropCompare = IndexedPropDesc.compare(Arrays.asList(existing.getHashIndexedProps()), hashProps);
        return keyPropCompare && IndexedPropDesc.compare(Arrays.asList(existing.getRangeIndexedProps()), btreeProps);
    }

    public void addTableReference(EventTable table) {
        for (Map.Entry<IndexMultiKey, Pair<EventTable, Integer>> entry : tableIndexesRefCount.entrySet())
        {
            if (entry.getValue().getFirst() == table)
            {
                int current = entry.getValue().getSecond() + 1;
                entry.getValue().setSecond(current);
            }
        }
    }

    /**
     * Remove a reference to an index table, decreasing its reference count.
     * If the table is no longer used, discard it and no longer update events into the index.
     * @param table to remove a reference to
     */
    public void removeTableReference(EventTable table)
    {
        for (Map.Entry<IndexMultiKey, Pair<EventTable, Integer>> entry : tableIndexesRefCount.entrySet())
        {
            if (entry.getValue().getFirst() == table)
            {
                int current = entry.getValue().getSecond();
                if (current > 1)
                {
                    current--;
                    entry.getValue().setSecond(current);
                    break;
                }

                tables.remove(table);
                tableIndexesRefCount.remove(entry.getKey());
                break;
            }
        }
    }

    /**
     * Returns a list of current index tables in the repository.
     * @return index tables
     */
    public List<EventTable> getTables()
    {
        return tables;
    }

    /**
     * Destroy indexes.
     */
    public void destroy()
    {
        tables.clear();
        tableIndexesRefCount.clear();
    }

    public Pair<IndexMultiKey, EventTable> findTable(Set<String> keyPropertyNames, Set<String> rangePropertyNames, Map<String, EventTable> explicitIndexNames) {

        if (keyPropertyNames.isEmpty() && rangePropertyNames.isEmpty()) {
            return null;
        }

        List<IndexMultiKey> candidateTables = null;
        for (Map.Entry<IndexMultiKey, Pair<EventTable, Integer>> entry : tableIndexesRefCount.entrySet()) {

            boolean missed = false;
            String[] indexedProps = IndexedPropDesc.getIndexProperties(entry.getKey().getHashIndexedProps());
            for (String indexedProp : indexedProps) {
                if (!keyPropertyNames.contains(indexedProp)) {
                    missed = true;
                    break;
                }
            }

            String[] rangeIndexProps = IndexedPropDesc.getIndexProperties(entry.getKey().getRangeIndexedProps());
            for (String rangeProp : rangeIndexProps) {
                if (!rangePropertyNames.contains(rangeProp) && !keyPropertyNames.contains(rangeProp)) {
                    missed = true;
                    break;
                }
            }
            
            if (!missed) {
                if (candidateTables == null) {
                    candidateTables = new ArrayList<IndexMultiKey>();
                }
                candidateTables.add(entry.getKey());
            }
        }

        if (candidateTables == null) {
            if (log.isDebugEnabled()) {
                log.debug("No index found.");
            }
            return null;
        }

        // take the best available table
        IndexMultiKey indexMultiKey;
        if (candidateTables.size() > 1) {
            Comparator<IndexMultiKey> comparator = new Comparator<IndexMultiKey>() {
                public int compare(IndexMultiKey o1, IndexMultiKey o2)
                {
                    String[] indexedProps1 = IndexedPropDesc.getIndexProperties(o1.getHashIndexedProps());
                    String[] indexedProps2 = IndexedPropDesc.getIndexProperties(o2.getHashIndexedProps());
                    if (indexedProps1.length > indexedProps2.length) {
                        return -1;  // sort desc by count columns
                    }
                    if (indexedProps1.length == indexedProps2.length) {
                        return 0;
                    }
                    return 1;
                }
            };
            Collections.sort(candidateTables,comparator);
        }
        indexMultiKey = candidateTables.get(0);
        EventTable tableFound = tableIndexesRefCount.get(indexMultiKey).getFirst();

        if (log.isDebugEnabled()) {
            String indexName = null;
            for (Map.Entry<String, EventTable> entry : explicitIndexNames.entrySet()) {
                if (entry.getValue() == tableFound) {
                    indexName = entry.getKey();
                }
            }
            log.debug("Found index " + indexName + " for on-demand query");
        }

        return new Pair<IndexMultiKey, EventTable>(indexMultiKey, tableFound);
    }

    public IndexMultiKey[] getIndexDescriptors() {
        Set<IndexMultiKey> keySet = tableIndexesRefCount.keySet();
        return keySet.toArray(new IndexMultiKey[keySet.size()]);
    }
}
