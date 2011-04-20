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
import com.espertech.esper.core.*;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValue;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueEquals;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueRange;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexLookup;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexLookupFactory;
import com.espertech.esper.epl.join.plan.*;
import com.espertech.esper.epl.join.table.*;
import com.espertech.esper.epl.lookup.*;
import com.espertech.esper.epl.metric.MetricReportingService;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.epl.virtualdw.VirtualDWView;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.filter.*;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.CollectionUtil;
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
    private static final Log queryPlanLog = LogFactory.getLog(AuditPath.QUERYPLAN_LOG);
    private static final Log log = LogFactory.getLog(NamedWindowRootView.class);

    private EventType namedWindowEventType;
    private final NamedWindowIndexRepository indexRepository;
    private Iterable<EventBean> dataWindowContents;
    private final Map<NamedWindowLookupStrategy, EventTable> tablePerMultiLookup;
    private final Map<SubordTableLookupStrategy, EventTable> tablePerSingleLookup;
    private final ValueAddEventProcessor revisionProcessor;
    private final ConcurrentHashMap<String, EventTable> explicitIndexes;
    private final boolean queryPlanLogging;
    private boolean isChildBatching;
    private StatementLock statementResourceLock;
    private MetricReportingService metricReportingService;
    private EPStatementHandle createWindowStatementHandle;

    /**
     * Ctor.
     * @param revisionProcessor handle update events if supplied, or null if not handling revisions
     */
    public NamedWindowRootView(ValueAddEventProcessor revisionProcessor, StatementLock statementResourceLock, boolean queryPlanLogging, EPStatementHandle createWindowStatementHandle, MetricReportingService metricReportingService)
    {
        this.indexRepository = new NamedWindowIndexRepository();
        this.tablePerMultiLookup = new HashMap<NamedWindowLookupStrategy, EventTable>();
        this.tablePerSingleLookup = new HashMap<SubordTableLookupStrategy, EventTable>();
        this.explicitIndexes = new ConcurrentHashMap<String, EventTable>();
        this.revisionProcessor = revisionProcessor;
        this.statementResourceLock = statementResourceLock;
        this.queryPlanLogging = queryPlanLogging;
        this.metricReportingService = metricReportingService;
        this.createWindowStatementHandle = createWindowStatementHandle;
    }

    public IndexMultiKey[] getIndexes() {
        return indexRepository.getIndexDescriptors();
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
    public synchronized void addExplicitIndex(String namedWindowName, String indexName, List<CreateIndexItem> columns) throws ExprValidationException {

        if (explicitIndexes.containsKey(indexName)) {
            throw new ExprValidationException("Index by name '" + indexName + "' already exists");
        }

        List<IndexedPropDesc> hashProps = new ArrayList<IndexedPropDesc>();
        List<IndexedPropDesc> btreeProps = new ArrayList<IndexedPropDesc>();

        Set<String> indexed = new HashSet<String>();
        for (CreateIndexItem columnDesc : columns) {
            String columnName = columnDesc.getName();

            Class type = JavaClassHelper.getBoxedType(namedWindowEventType.getPropertyType(columnName));
            if (type == null) {
                throw new ExprValidationException("Property named '" + columnName + "' not found on named window '" + namedWindowName + "'");
            }
            if (!indexed.add(columnName)) {
                throw new ExprValidationException("Property named '" + columnName + "' has been declared more then once");
            }

            IndexedPropDesc desc = new IndexedPropDesc(columnName, type);
            if (columnDesc.getType() == CreateIndexType.HASH) {
                hashProps.add(desc);
            } else {
                btreeProps.add(desc);
            }
        }

        Pair<IndexMultiKey, EventTable> pair = indexRepository.addTableCreateOrReuse(hashProps, btreeProps, dataWindowContents, namedWindowEventType, false);
        explicitIndexes.put(indexName, pair.getSecond());
    }

    // Called by deletion strategy and also the insert-into for new events only
    public void update(EventBean[] newData, EventBean[] oldData)
    {
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
    public NamedWindowOnExprBaseView addOnExpr(OnTriggerDesc onTriggerDesc, ExprNode joinExpr, EventType filterEventType, String filterStreamName, StatementStopService statementStopService, InternalEventRouter internalEventRouter, boolean addToFront, ResultSetProcessor resultSetProcessor, EPStatementHandle statementHandle, StatementResultService statementResultService, StatementContext statementContext, boolean isDistinct)
            throws ExprValidationException
    {
        // Determine strategy for deletion and index table to use (if any)
        Pair<NamedWindowLookupStrategy,EventTable> strategy = getStrategyPair(joinExpr, filterEventType);

        if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
            queryPlanLog.info("Strategy " + strategy.getFirst().toQueryPlan());
            queryPlanLog.info("Table " + ((strategy.getSecond() == null) ? "N/A" : strategy.getSecond().toQueryPlan()));
        }

        // If a new table is required, add that table to be updated
        if (strategy.getSecond() != null)
        {
            tablePerMultiLookup.put(strategy.getFirst(), strategy.getSecond());
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
        else if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_MERGE)
        {
            OnTriggerMergeDesc desc = (OnTriggerMergeDesc) onTriggerDesc;
            return new NamedWindowOnMergeView(statementStopService, strategy.getFirst(), this, statementResultService, statementContext, desc, filterEventType, filterStreamName, createWindowStatementHandle, metricReportingService, internalEventRouter, namedWindowEventType.getName());
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
    public void removeOnExpr(NamedWindowLookupStrategy strategy)
    {
        EventTable table = tablePerMultiLookup.remove(strategy);
        if (table != null)
        {
            indexRepository.removeTableReference(table);
        }
    }

    private Pair<IndexKeyInfo, EventTable> findCreateIndex(SubordPropPlan joinDesc) {

        // hash property names and types
        String[] hashIndexPropsProvided = new String[joinDesc.getHashProps().size()];
        Class[] hashIndexCoercionType = new Class[joinDesc.getHashProps().size()];
        SubordPropHashKey[] hashJoinedProps = new SubordPropHashKey[joinDesc.getHashProps().size()];
        int count = 0;
        for (Map.Entry<String, SubordPropHashKey> entry : joinDesc.getHashProps().entrySet()) {
            hashIndexPropsProvided[count] = entry.getKey();
            hashIndexCoercionType[count] = entry.getValue().getCoercionType();
            hashJoinedProps[count++] = entry.getValue();
        }

        // hash property names and types
        String[] rangeIndexPropsProvided = new String[joinDesc.getRangeProps().size()];
        Class[] rangeIndexCoercionType = new Class[joinDesc.getRangeProps().size()];
        SubordPropRangeKey[] rangeJoinedProps = new SubordPropRangeKey[joinDesc.getRangeProps().size()];
        count = 0;
        for (Map.Entry<String, SubordPropRangeKey> entry : joinDesc.getRangeProps().entrySet()) {
            rangeIndexPropsProvided[count] = entry.getKey();
            rangeIndexCoercionType[count] = entry.getValue().getCoercionType();
            rangeJoinedProps[count++] = entry.getValue();
        }

        // Add all joined fields to an array for sorting
        List<IndexedPropDesc> hashedProps = new ArrayList<IndexedPropDesc>();
        List<IndexedPropDesc> btreeProps = new ArrayList<IndexedPropDesc>();
        for (int i = 0; i < hashIndexPropsProvided.length; i++) {
            hashedProps.add(new IndexedPropDesc(hashIndexPropsProvided[i], hashIndexCoercionType[i]));
        }
        for (int i = 0; i < rangeIndexPropsProvided.length; i++) {
            btreeProps.add(new IndexedPropDesc(rangeIndexPropsProvided[i], rangeIndexCoercionType[i]));
        }

        // Get or Create the table for this index (exact match or property names, type of index and coercion type is expected)
        Pair<IndexMultiKey, EventTable> tableDesc;
        if (this.getViews().get(0) instanceof VirtualDWView) {
            VirtualDWView viewExternal = (VirtualDWView) this.getViews().get(0);
            tableDesc = viewExternal.getSubordinateQueryDesc(hashedProps, btreeProps);
        }
        else {
            if (joinDesc.getHashProps().isEmpty() && joinDesc.getRangeProps().isEmpty()) {
                return null;
            }
            tableDesc = indexRepository.addTableCreateOrReuse(hashedProps, btreeProps, dataWindowContents, namedWindowEventType, true);
        }

        // map the order of indexed columns (key) to the key information available
        IndexedPropDesc[] indexedKeyProps = tableDesc.getFirst().getHashIndexedProps();
        SubordPropHashKey[] hashesDesc = new SubordPropHashKey[indexedKeyProps.length];
        Class[] hashPropCoercionTypes = new Class[indexedKeyProps.length];
        boolean isCoerceHash = false;
        for (int i = 0; i < indexedKeyProps.length; i++) {
            String indexField = indexedKeyProps[i].getIndexPropName();
            int index = CollectionUtil.findItem(hashIndexPropsProvided, indexField);
            if (index == -1) {
                throw new IllegalStateException("Could not find index property for lookup '" + indexedKeyProps[i]);
            }
            hashesDesc[i] = hashJoinedProps[index];
            hashPropCoercionTypes[i] = indexedKeyProps[i].getCoercionType();
            if (indexedKeyProps[i].getCoercionType() != hashesDesc[i].getHashKey().getKeyExpr().getExprEvaluator().getType()) {
                isCoerceHash = true;
            }
        }

        // map the order of range columns (range) to the range information available
        indexedKeyProps = tableDesc.getFirst().getRangeIndexedProps();
        SubordPropRangeKey[] rangesDesc = new SubordPropRangeKey[indexedKeyProps.length];
        Class[] rangePropCoercionTypes = new Class[indexedKeyProps.length];
        boolean isCoerceRange = false;
        for (int i = 0; i < indexedKeyProps.length; i++) {
            String indexField = indexedKeyProps[i].getIndexPropName();
            int index = CollectionUtil.findItem(rangeIndexPropsProvided, indexField);
            if (index == -1) {
                throw new IllegalStateException("Could not find range property for lookup '" + indexedKeyProps[i]);
            }
            rangesDesc[i] = rangeJoinedProps[index];
            rangePropCoercionTypes[i] = rangeJoinedProps[index].getCoercionType();
            if (indexedKeyProps[i].getCoercionType() != rangePropCoercionTypes[i]) {
                isCoerceRange = true;
            }
        }

        IndexKeyInfo info = new IndexKeyInfo(Arrays.asList(hashesDesc),
                new CoercionDesc(isCoerceHash, hashPropCoercionTypes), Arrays.asList(rangesDesc), new CoercionDesc(isCoerceRange, rangePropCoercionTypes));
        return new Pair<IndexKeyInfo, EventTable>(info, tableDesc.getSecond());
    }

    private Pair<SubordTableLookupStrategy, EventTable> getSubqueryStrategyPair(EventType[] outerStreamTypes, SubordPropPlan joinDesc, boolean isNWOnTrigger, boolean forceTableScan) {

        Pair<IndexKeyInfo, EventTable> accessDesc = findCreateIndex(joinDesc);

        if (accessDesc == null) {
            return null;
        }

        IndexKeyInfo indexKeyInfo = accessDesc.getFirst();
        EventTable eventTable = accessDesc.getSecond();

        List<SubordPropHashKey> hashKeys = indexKeyInfo.getOrderedHashProperties();
        CoercionDesc hashKeyCoercionTypes = indexKeyInfo.getOrderedKeyCoercionTypes();
        List<SubordPropRangeKey> rangeKeys = indexKeyInfo.getOrderedRangeDesc();
        CoercionDesc rangeKeyCoercionTypes = indexKeyInfo.getOrderedRangeCoercionTypes();

        SubordTableLookupStrategy lookupStrategy;
        if (this.getViews().get(0) instanceof VirtualDWView) {
            VirtualDWView viewExternal = (VirtualDWView) this.getViews().get(0);
            lookupStrategy = viewExternal.getSubordinateLookupStrategy(outerStreamTypes,
                    hashKeys, hashKeyCoercionTypes, rangeKeys, rangeKeyCoercionTypes, isNWOnTrigger, eventTable);
        }
        else {
            if (forceTableScan) {
                lookupStrategy = null;
            }
            else {
                lookupStrategy = SubordinateTableLookupStrategyFactory.getLookupStrategy(outerStreamTypes,
                    hashKeys, hashKeyCoercionTypes, rangeKeys, rangeKeyCoercionTypes, isNWOnTrigger, eventTable);
            }
        }

        return new Pair<SubordTableLookupStrategy,EventTable>(lookupStrategy, eventTable);
    }

    private Pair<NamedWindowLookupStrategy,EventTable> getStrategyPair(ExprNode joinExpr, EventType filterEventType)
    {
        EventType[] allStreamsZeroIndexed = new EventType[] {namedWindowEventType, filterEventType};
        EventType[] outerStreams = new EventType[] {filterEventType};
        SubordPropPlan joinedPropPlan = QueryPlanIndexBuilder.getJoinProps(joinExpr, 1, allStreamsZeroIndexed);

        // No join expression means delete all
        if (joinExpr == null && (!(this.getViews().get(0) instanceof VirtualDWView)))
        {
            return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyAllRows(dataWindowContents), null);
        }

        // Here the stream offset is 1 as the named window lookup provides the arriving event in stream 1
        Pair<SubordTableLookupStrategy,EventTable> lookupPair = getSubqueryStrategyPair(outerStreams, joinedPropPlan, true, false);

        if (lookupPair == null) {
            return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyTableScan(joinExpr.getExprEvaluator(), dataWindowContents), null);
        }

        if (joinExpr == null) {   // it can be null when using virtual data window
            return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyIndexedUnfiltered(lookupPair.getFirst()), lookupPair.getSecond());
        }
        else {
            return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyIndexed(joinExpr.getExprEvaluator(), lookupPair.getFirst()), lookupPair.getSecond());
        }
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
        tablePerMultiLookup.clear();
    }

    /**
     * Return a snapshot using index lookup filters.
     * @param optionalFilter to index lookup
     * @return events
     */
    public Collection<EventBean> snapshot(FilterSpecCompiled optionalFilter) {

        // Determine virtual data window
        VirtualDWView virtualDataWindow = null;
        if (this.getViews().get(0) instanceof VirtualDWView) {
            virtualDataWindow = (VirtualDWView) this.getViews().get(0);
        }

        if (optionalFilter == null || optionalFilter.getParameters().isEmpty()) {
            if (virtualDataWindow != null) {
                Pair<IndexMultiKey,EventTable> pair = virtualDataWindow.getFireAndForgetDesc(Collections.<String>emptySet(), Collections.<String>emptySet());
                return virtualDataWindow.getFireAndForgetData(pair.getSecond(), new Object[0], new RangeIndexLookupValue[0]);
            }
            return null;
        }

        // Determine what straight-equals keys and which ranges are available.
        // Widening/Coercion is part of filter spec compile.
        Set<String> keysAvailable = new HashSet<String>();
        Set<String> rangesAvailable = new HashSet<String>();
        for (FilterSpecParam param : optionalFilter.getParameters()) {
            if (!(param instanceof FilterSpecParamConstant || param instanceof FilterSpecParamRange)) {
                continue;
            }
            if (param.getFilterOperator() == FilterOperator.EQUAL) {
                keysAvailable.add(param.getPropertyName());
            }
            else if (param.getFilterOperator().isRangeOperator() ||
                     param.getFilterOperator().isInvertedRangeOperator() ||
                     param.getFilterOperator().isComparisonOperator()) {
                rangesAvailable.add(param.getPropertyName());
            }
            else if (param.getFilterOperator().isRangeOperator()) {
                rangesAvailable.add(param.getPropertyName());
            }
        }

        // Find an index that matches the needs
        Pair<IndexMultiKey, EventTable> tablePair;
        if (virtualDataWindow != null) {
            tablePair = virtualDataWindow.getFireAndForgetDesc(keysAvailable, rangesAvailable);
        }
        else {
            tablePair = indexRepository.findTable(keysAvailable, rangesAvailable, explicitIndexes);
        }

        if (tablePair == null) {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("no index found");
            }
            return null;    // indicates table scan
        }

        // Compile key index lookup values
        String[] keyIndexProps = IndexedPropDesc.getIndexProperties(tablePair.getFirst().getHashIndexedProps());
        Object[] keyValues = new Object[keyIndexProps.length];
        for (int keyIndex = 0; keyIndex < keyIndexProps.length; keyIndex++) {
            for (FilterSpecParam param : optionalFilter.getParameters()) {
                if (param.getPropertyName().equals(keyIndexProps[keyIndex])) {
                    keyValues[keyIndex] = param.getFilterValue(null);
                    break;
                }
            }
        }

        // Analyze ranges - these may include key lookup value (EQUALS semantics)
        String[] rangeIndexProps = IndexedPropDesc.getIndexProperties(tablePair.getFirst().getRangeIndexedProps());
        RangeIndexLookupValue[] rangeValues;
        if (rangeIndexProps.length > 0) {
            rangeValues = compileRangeLookupValues(rangeIndexProps, optionalFilter.getParameters());
        }
        else {
            rangeValues = new RangeIndexLookupValue[0];
        }

        if (virtualDataWindow != null) {
            return virtualDataWindow.getFireAndForgetData(tablePair.getSecond(), keyValues, rangeValues);
        }

        IndexMultiKey indexMultiKey = tablePair.getFirst();
        Set<EventBean> result;
        if (indexMultiKey.getHashIndexedProps().length > 0 && indexMultiKey.getRangeIndexedProps().length == 0) {
            if (indexMultiKey.getHashIndexedProps().length == 1) {
                PropertyIndexedEventTableSingle table = (PropertyIndexedEventTableSingle) tablePair.getSecond();
                result = table.lookup(keyValues[0]);
            }
            else {
                PropertyIndexedEventTable table = (PropertyIndexedEventTable) tablePair.getSecond();
                result = table.lookup(keyValues);
            }
        }
        else if (indexMultiKey.getHashIndexedProps().length == 0 && indexMultiKey.getRangeIndexedProps().length == 1) {
            PropertySortedEventTable table = (PropertySortedEventTable) tablePair.getSecond();
            result = table.lookupConstants(rangeValues[0]);
        }
        else {
            PropertyCompositeEventTable table = (PropertyCompositeEventTable) tablePair.getSecond();
            Class[] rangeCoercion = table.getOptRangeCoercedTypes();
            CompositeIndexLookup lookup = CompositeIndexLookupFactory.make(keyValues, rangeValues, rangeCoercion);
            result = new HashSet<EventBean>();
            lookup.lookup(table.getIndex(), result);
        }
        if (result != null) {
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    private RangeIndexLookupValue[] compileRangeLookupValues(String[] rangeIndexProps, List<FilterSpecParam> parameters) {
        RangeIndexLookupValue[] result = new RangeIndexLookupValue[rangeIndexProps.length];

        for (int rangeIndex = 0; rangeIndex < rangeIndexProps.length; rangeIndex++) {
            for (FilterSpecParam param : parameters) {
                if (!param.getPropertyName().equals(rangeIndexProps[rangeIndex])) {
                    continue;
                }

                if (param.getFilterOperator() == FilterOperator.EQUAL) {
                    result[rangeIndex] = new RangeIndexLookupValueEquals(param.getFilterValue(null));
                }
                else if (param.getFilterOperator().isRangeOperator() || param.getFilterOperator().isInvertedRangeOperator()) {
                    QueryGraphRangeEnum opAdd = QueryGraphRangeEnum.mapFrom(param.getFilterOperator());
                    result[rangeIndex] = new RangeIndexLookupValueRange(param.getFilterValue(null), opAdd, true);
                }
                else if (param.getFilterOperator().isComparisonOperator()) {

                    RangeIndexLookupValue existing = result[rangeIndex];
                    QueryGraphRangeEnum opAdd = QueryGraphRangeEnum.mapFrom(param.getFilterOperator());
                    if (existing == null) {
                        result[rangeIndex] = new RangeIndexLookupValueRange(param.getFilterValue(null), opAdd, true);
                    }
                    else {
                        if (!(existing instanceof RangeIndexLookupValueRange)) {
                            continue;
                        }
                        RangeIndexLookupValueRange existingRange = (RangeIndexLookupValueRange) existing;
                        QueryGraphRangeEnum opExist = existingRange.getOperator();
                        QueryGraphRangeConsolidateDesc desc = QueryGraphRangeUtil.getCanConsolidate(opExist, opAdd);
                        if (desc != null) {
                            DoubleRange doubleRange = getDoubleRange(desc.isReverse(), existing.getValue(), param.getFilterValue(null));
                            result[rangeIndex] = new RangeIndexLookupValueRange(doubleRange, desc.getType(), false);
                        }
                    }
                }
            }
        }
        return result;
    }

    private DoubleRange getDoubleRange(boolean reverse, Object start, Object end) {
        if (start == null || end == null) {
            return null;
        }
        double startDbl = ((Number) start).doubleValue();
        double endDbl = ((Number) end).doubleValue();
        if (reverse) {
            return new DoubleRange(startDbl, endDbl);
        }
        else {
            return new DoubleRange(endDbl, startDbl);
        }
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

    public SubordTableLookupStrategy getAddSubqueryLookupStrategy(EventType[] eventTypesPerStream, SubordPropPlan joinDesc, boolean fullTableScan) {

        // NOTE: key stream nums are relative to the outer streams, i.e. 0=first outer, 1=second outer (index stream is implied and not counted).
        // Here the stream offset for key is zero as in a subquery only the outer events are provided in events-per-stream.
        Pair<SubordTableLookupStrategy,EventTable> strategyTablePair = getSubqueryStrategyPair(eventTypesPerStream, joinDesc, false, fullTableScan);
        if (strategyTablePair == null || strategyTablePair.getFirst() == null) {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("shared, full table scan");
            }
            return new SubordFullTableScanLookupStrategyLocking(dataWindowContents, statementResourceLock);
        }

        SubordIndexedTableLookupStrategyLocking locking = new SubordIndexedTableLookupStrategyLocking(strategyTablePair.getFirst(), statementResourceLock);
        tablePerSingleLookup.put(locking, strategyTablePair.getSecond());

        if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
            queryPlanLog.info("shared index");
            queryPlanLog.info("strategy " + strategyTablePair.getFirst().toQueryPlan());
            queryPlanLog.info("table " + strategyTablePair.getSecond().toQueryPlan());
        }
        return locking;
    }

    public void removeSubqueryLookupStrategy(SubordTableLookupStrategy namedWindowSubqueryLookup) {
        EventTable table = tablePerSingleLookup.remove(namedWindowSubqueryLookup);
        if (table != null) {
            indexRepository.removeTableReference(table);
        }
    }
}
