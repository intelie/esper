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
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.core.StatementResultService;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.join.plan.FilterExprAnalyzer;
import com.espertech.esper.epl.join.plan.QueryGraph;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.lookup.IndexedTableLookupStrategy;
import com.espertech.esper.epl.lookup.IndexedTableLookupStrategyCoercing;
import com.espertech.esper.epl.lookup.JoinedPropDesc;
import com.espertech.esper.epl.lookup.TableLookupStrategy;
import com.espertech.esper.epl.spec.OnTriggerDesc;
import com.espertech.esper.epl.spec.OnTriggerType;
import com.espertech.esper.epl.spec.OnTriggerWindowUpdateDesc;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.filter.FilterOperator;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecParam;
import com.espertech.esper.filter.FilterSpecParamConstant;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.view.ViewSupport;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The root window in a named window plays multiple roles: It holds the indexes for deleting rows, if any on-delete statement
 * requires such indexes. Such indexes are updated when events arrive, or remove from when a data window
 * or on-delete statement expires events. The view keeps track of on-delete statements their indexes used.
 */
public class NamedWindowRootView extends ViewSupport
{
    private static final Log log = LogFactory.getLog(NamedWindowRootView.class);

    private EventType namedWindowEventType;
    private final NamedWindowIndexRepository indexRepository;
    private Iterable<EventBean> dataWindowContents;
    private final Map<LookupStrategy, PropertyIndexedEventTable> tablePerStrategy;
    private final ValueAddEventProcessor revisionProcessor;
    private final ConcurrentHashMap<String, PropertyIndexedEventTable> explicitIndexes;
    private boolean isChildBatching;

    /**
     * Ctor.
     * @param revisionProcessor handle update events if supplied, or null if not handling revisions
     */
    public NamedWindowRootView(ValueAddEventProcessor revisionProcessor)
    {
        this.indexRepository = new NamedWindowIndexRepository();
        this.tablePerStrategy = new HashMap<LookupStrategy, PropertyIndexedEventTable>();
        this.explicitIndexes = new ConcurrentHashMap<String, PropertyIndexedEventTable>();
        this.revisionProcessor = revisionProcessor;
    }

    /**
     * Sets the iterator to use to obtain current named window data window contents.
     * @param dataWindowContents iterator over events help by named window
     */
    public void setDataWindowContents(Iterable<EventBean> dataWindowContents)
    {
        this.dataWindowContents = dataWindowContents;
    }

    /**
     * Called by tail view to indicate that the data window view exired events that must be removed from index tables.
     * @param oldData removed stream of the data window
     */
    public void removeOldData(EventBean[] oldData)
    {
        if (revisionProcessor != null)
        {
            revisionProcessor.removeOldData(oldData, indexRepository);
        }
        else
        {
            for (EventTable table : indexRepository.getTables())
            {
                table.remove(oldData);
            }
        }
    }

    /**
     * Called by tail view to indicate that the data window view has new events that must be added to index tables.
     * @param newData new event
     */
    public void addNewData(EventBean[] newData)
    {
        if (revisionProcessor == null) {
            // Update indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
                table.add(newData);
            }
        }
    }

    /**
     * Add an explicit index.
     * @param namedWindowName window name
     * @param indexName indexname
     * @param columns properties indexed
     * @throws ExprValidationException if the index fails to be valid
     */
    public synchronized void addExplicitIndex(String namedWindowName, String indexName, List<String> columns) throws ExprValidationException {

        if (explicitIndexes.containsKey(indexName)) {
            throw new ExprValidationException("Index by name '" + indexName + "' already exists");
        }

        Set<String> indexed = new HashSet<String>();
        IndexedPropDesc[] desc = new IndexedPropDesc[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            Class type = namedWindowEventType.getPropertyType(columnName);
            if (type == null) {
                throw new ExprValidationException("Property named '" + columnName + "' not found on named window '"+ namedWindowName + "'");
            }
            if (!indexed.add(columnName)) {
                throw new ExprValidationException("Property named '" + columnName + "' has been declared more then once");
            }
            desc[i] = new IndexedPropDesc(columnName, type);
        }

        PropertyIndexedEventTable table = indexRepository.addTable(desc, dataWindowContents, namedWindowEventType, false);
        explicitIndexes.put(indexName, table);
    }

    // Called by deletion strategy and also the insert-into for new events only
    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (revisionProcessor != null)
        {
            revisionProcessor.onUpdate(newData, oldData, this, indexRepository);
        }
        else
        {
            // Update indexes for fast deletion, if there are any
            for (EventTable table : indexRepository.getTables())
            {
                if (isChildBatching) {
                    table.add(newData);
                }
                table.remove(oldData);
            }

            // Update child views
            updateChildren(newData, oldData);
        }
    }

    /**
     * Add an on-trigger view that, using a lookup strategy, looks up from the named window and may select or delete rows.
     * @param onTriggerDesc the specification for the on-delete
     * @param filterEventType the event type for the on-clause in the on-delete
     * @param statementStopService for stopping the statement
     * @param internalEventRouter for insert-into behavior
     * @param resultSetProcessor @return view representing the on-delete view chain, posting delete events to it's listeners
     * @param statementHandle is the handle to the statement, used for routing/insert-into
     * @param joinExpr is the join expression or null if there is none
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     * @param statementContext statement services
     * @param isDistinct is true for distinct output
     * @return base view for on-trigger expression
     * @throws com.espertech.esper.epl.expression.ExprValidationException when expression validation fails
     */
    public NamedWindowOnExprBaseView addOnExpr(OnTriggerDesc onTriggerDesc, ExprNode joinExpr, EventType filterEventType, StatementStopService statementStopService, InternalEventRouter internalEventRouter, boolean addToFront, ResultSetProcessor resultSetProcessor, EPStatementHandle statementHandle, StatementResultService statementResultService, StatementContext statementContext, boolean isDistinct)
            throws ExprValidationException
    {
        // Determine strategy for deletion and index table to use (if any)
        Pair<LookupStrategy,PropertyIndexedEventTable> strategy = getStrategyPair(onTriggerDesc, joinExpr, filterEventType);

        // If a new table is required, add that table to be updated
        if (strategy.getSecond() != null)
        {
            tablePerStrategy.put(strategy.getFirst(), strategy.getSecond());
        }

        if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_DELETE)
        {
            return new NamedWindowOnDeleteView(statementStopService, strategy.getFirst(), this, statementResultService, statementContext);
        }
        else if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_SELECT)
        {
            return new NamedWindowOnSelectView(statementStopService, strategy.getFirst(), this, internalEventRouter, addToFront, resultSetProcessor, statementHandle, statementResultService, statementContext, isDistinct);
        }
        else if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_UPDATE)
        {
            OnTriggerWindowUpdateDesc desc = (OnTriggerWindowUpdateDesc) onTriggerDesc;
            return new NamedWindowOnUpdateView(statementStopService, strategy.getFirst(), this, statementResultService, statementContext, desc);
        }
        else
        {
            throw new IllegalStateException("Unknown trigger type " + onTriggerDesc.getOnTriggerType());
        }
    }

    /**
     * Unregister an on-delete statement view, using the strategy as a key to remove a reference to the index table
     * used by the strategy.
     * @param strategy to use for deleting events
     */
    public void removeOnExpr(LookupStrategy strategy)
    {
        PropertyIndexedEventTable table = tablePerStrategy.remove(strategy);
        if (table != null)
        {
            indexRepository.removeTableReference(table);
        }
    }

    private Pair<LookupStrategy,PropertyIndexedEventTable> getStrategyPair(OnTriggerDesc onTriggerDesc, ExprNode joinExpr, EventType filterEventType)
    {
        // No join expression means delete all
        if (joinExpr == null)
        {
            return new Pair<LookupStrategy,PropertyIndexedEventTable>(new LookupStrategyAllRows(dataWindowContents), null);
        }

        // analyze query graph; Whereas stream0=named window, stream1=delete-expr filter
        QueryGraph queryGraph = new QueryGraph(2);
        FilterExprAnalyzer.analyze(joinExpr, queryGraph);

        // index and key property names
        String[] keyPropertiesJoin = queryGraph.getKeyProperties(1, 0);
        String[] indexPropertiesJoin = queryGraph.getIndexProperties(1, 0);

        // If the analysis revealed no join columns, must use the brute-force full table scan
        if ((keyPropertiesJoin == null) || (keyPropertiesJoin.length == 0))
        {
            return new Pair<LookupStrategy,PropertyIndexedEventTable>(new LookupStrategyTableScan(joinExpr.getExprEvaluator(), dataWindowContents), null);
        }

        // Build a set of index descriptors with property name and coercion type
        boolean mustCoerce = false;
        Class[] coercionTypes = new Class[indexPropertiesJoin.length];
        for (int i = 0; i < keyPropertiesJoin.length; i++)
        {
            Class keyPropType = JavaClassHelper.getBoxedType(filterEventType.getPropertyType(keyPropertiesJoin[i]));
            Class indexedPropType = JavaClassHelper.getBoxedType(namedWindowEventType.getPropertyType(indexPropertiesJoin[i]));
            Class coercionType = indexedPropType;
            if (keyPropType != indexedPropType)
            {
                coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, keyPropType);
                mustCoerce = true;
            }

            coercionTypes[i] = coercionType;
        }

        // Add all joined fields to an array for sorting
        JoinedPropDesc[] joinedPropDesc = new JoinedPropDesc[keyPropertiesJoin.length];
        IndexedPropDesc[] indexedPropDesc = new IndexedPropDesc[keyPropertiesJoin.length];
        for (int i = 0; i < joinedPropDesc.length; i++)
        {
            joinedPropDesc[i] = new JoinedPropDesc(indexPropertiesJoin[i], coercionTypes[i], keyPropertiesJoin[i], 1);
            indexedPropDesc[i] = new IndexedPropDesc(indexPropertiesJoin[i], coercionTypes[i]);
        }
        Arrays.sort(joinedPropDesc);
        Arrays.sort(indexedPropDesc);
        keyPropertiesJoin = JoinedPropDesc.getKeyProperties(joinedPropDesc);

        // Get the table for this index
        PropertyIndexedEventTable table = indexRepository.addTable(indexedPropDesc, dataWindowContents, namedWindowEventType, mustCoerce);

        // assign types and stream numbers
        EventType[] eventTypePerStream = new EventType[] {null, filterEventType};
        int[] streamNumbersPerProperty = new int[joinedPropDesc.length];
        for (int i = 0; i < streamNumbersPerProperty.length; i++)
        {
            streamNumbersPerProperty[i] = 1;
        }

        // create the strategy
        TableLookupStrategy lookupStrategy = null;
        if (!mustCoerce)
        {
            lookupStrategy = new IndexedTableLookupStrategy(eventTypePerStream, streamNumbersPerProperty, keyPropertiesJoin, table);
        }
        else
        {
            lookupStrategy = new IndexedTableLookupStrategyCoercing(eventTypePerStream, streamNumbersPerProperty, keyPropertiesJoin, table, coercionTypes);
        }

        return new Pair<LookupStrategy,PropertyIndexedEventTable>(new LookupStrategyIndexed(joinExpr.getExprEvaluator(), lookupStrategy), table);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        namedWindowEventType = parent.getEventType();
    }

    public EventType getEventType()
    {
        return namedWindowEventType;
    }

    public Iterator<EventBean> iterator()
    {
        return null;
    }

    /**
     * Destroy and clear resources.
     */
    public void destroy()
    {
        indexRepository.destroy();
        tablePerStrategy.clear();
    }

    /**
     * Return a snapshot using index lookup filters.
     * @param filter to index lookup
     * @return events
     */
    public Collection<EventBean> snapshot(FilterSpecCompiled filter) {
        if (filter.getParameters().isEmpty()) {
            return null;
        }

        // Widening/Coercion is part of filter spec compile 
        Map<String, Class> keysAndTypes = new HashMap<String, Class>();
        for (FilterSpecParam param : filter.getParameters()) {
            if (!(param instanceof FilterSpecParamConstant)) {
                continue;
            }
            if (param.getFilterOperator() == FilterOperator.EQUAL) {
                Object filterValue = param.getFilterValue(null);
                if (filterValue == null) {
                    keysAndTypes.put(param.getPropertyName(), null);
                }
                else {
                    keysAndTypes.put(param.getPropertyName(), filterValue.getClass());
                }
            }
        }
        if (keysAndTypes.isEmpty()) {
            return null;
        }

        List<PropertyIndexedEventTable> candidateTables = null;
        for (PropertyIndexedEventTable table : indexRepository.getTables()) {

            boolean missed = false;
            for (String indexedProp : table.getPropertyNames()) {
                if (!keysAndTypes.containsKey(indexedProp)) {
                    missed = true;
                    break;
                }
            }
            if (!missed) {
                if (candidateTables == null) {
                    candidateTables = new ArrayList<PropertyIndexedEventTable>();
                    candidateTables.add(table);
                }
                else {
                    candidateTables.add(table);
                }
            }
        }

        if (candidateTables == null) {
            if (log.isDebugEnabled()) {
                log.debug("No index found.");
            }
            return null;
        }

        // take the best available table
        PropertyIndexedEventTable tableFound;
        if (candidateTables.size() > 1) {
            Comparator<PropertyIndexedEventTable> comparator = new Comparator<PropertyIndexedEventTable>() {
                public int compare(PropertyIndexedEventTable o1, PropertyIndexedEventTable o2)
                {
                    if (o1.getPropertyNames().length > o2.getPropertyNames().length) {
                        return -1;  // sort desc by count columns
                    }
                    if (o1.getPropertyNames().length == o2.getPropertyNames().length) {
                        return 0;
                    }
                    return 1;
                }
            };
            Collections.sort(candidateTables,comparator);
            tableFound = candidateTables.get(0);
        }
        else {
            tableFound = candidateTables.get(0);
        }

        if (log.isDebugEnabled()) {
            String indexName = null;
            for (Map.Entry<String, PropertyIndexedEventTable> entry : explicitIndexes.entrySet()) {
                if (entry.getValue() == tableFound) {
                    indexName = entry.getKey();
                }
            }
            log.debug("Using index " + indexName + " for on-demand query");
        }

        Object[] keyValues = new Object[tableFound.getPropertyNames().length];
        for (int keyIndex = 0; keyIndex < tableFound.getPropertyNames().length; keyIndex++) {
            for (FilterSpecParam param : filter.getParameters()) {
                if (param.getPropertyName().equals(tableFound.getPropertyNames()[keyIndex])) {
                    keyValues[keyIndex] = param.getFilterValue(null);
                    break;
                }
            }
        }

        Set<EventBean> result = tableFound.lookup(keyValues);
        if (result != null) {
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Drop an explicit index.
     * @param indexName to drop
     */
    public void removeExplicitIndex(String indexName)
    {
        EventTable table = explicitIndexes.remove(indexName);
        if (table != null) {
            indexRepository.removeTableReference(table);
        }
    }

    /**
     * Set batch view indicator
     * @param batchView true for batch view
     */
    public void setBatchView(boolean batchView) {
        isChildBatching = batchView;
    }
}
