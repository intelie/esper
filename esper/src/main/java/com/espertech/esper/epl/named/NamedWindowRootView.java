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
import com.espertech.esper.epl.join.exec.RangeIndexLookupValue;
import com.espertech.esper.epl.join.exec.RangeIndexLookupValueEquals;
import com.espertech.esper.epl.join.exec.RangeIndexLookupValueRange;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexLookup;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexLookupFactory;
import com.espertech.esper.epl.join.plan.*;
import com.espertech.esper.epl.join.table.*;
import com.espertech.esper.epl.lookup.*;
import com.espertech.esper.epl.metric.MetricReportingService;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.filter.*;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.CollectionUtil;
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
    private static final Log queryPlanLog = LogFactory.getLog(AuditPath.QUERYPLAN_LOG);
    private static final Log log = LogFactory.getLog(NamedWindowRootView.class);

    private EventType namedWindowEventType;
    private final NamedWindowIndexRepository indexRepository;
    private Iterable<EventBean> dataWindowContents;
    private final Map<NamedWindowLookupStrategy, EventTable> tablePerMultiLookup;
    private final Map<SubqTableLookupStrategy, EventTable> tablePerSingleLookup;
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
        this.tablePerSingleLookup = new HashMap<SubqTableLookupStrategy, EventTable>();
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
        Pair<NamedWindowLookupStrategy,EventTable> strategy = getStrategyPair(joinExpr, filterEventType);

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
            return new NamedWindowOnMergeView(statementStopService, strategy.getFirst(), this, statementResultService, statementContext, desc, filterEventType, createWindowStatementHandle, metricReportingService);
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
    
    private Pair<IndexKeyInfo, EventTable> getCreateIndex(ExprNode joinExpr, EventType filterEventType) {
        // analyze query graph; Whereas stream0=named window, stream1=delete-expr filter
        QueryGraph queryGraph = new QueryGraph(2);
        FilterExprAnalyzer.analyze(joinExpr, queryGraph);

        // index and key property names
        String[] keyPropertiesAvailable = queryGraph.getKeyProperties(1, 0);
        String[] indexPropertiesRequired = queryGraph.getIndexProperties(1, 0);
        List<QueryGraphValueRange> rangePropertiesAvailable = queryGraph.getGraphValue(1, 0, false).getRangeEntries();

        // If the analysis revealed no join columns, must use the brute-force full table scan
        if ((keyPropertiesAvailable == null) || (keyPropertiesAvailable.length == 0) && rangePropertiesAvailable.isEmpty())
        {
            return null;    // no index required
        }

        CoercionDesc keyCoercionTypes = CoercionUtil.getCoercionTypes(new EventType[] {namedWindowEventType, filterEventType}, 1, 0, keyPropertiesAvailable, indexPropertiesRequired);
        CoercionDesc rangeCoercionTypes = CoercionUtil.getCoercionTypes(new EventType[] {namedWindowEventType, filterEventType}, 1, 0, rangePropertiesAvailable);

        // Add all joined fields to an array for sorting
        List<IndexedPropDesc> hashedProps = new ArrayList<IndexedPropDesc>();
        List<IndexedPropDesc> btreeProps = new ArrayList<IndexedPropDesc>();
        for (int i = 0; i < indexPropertiesRequired.length; i++) {
            hashedProps.add(new IndexedPropDesc(indexPropertiesRequired[i], keyCoercionTypes.getCoercionTypes()[i]));
        }
        for (int i = 0; i < rangePropertiesAvailable.size(); i++) {
            btreeProps.add(new IndexedPropDesc(rangePropertiesAvailable.get(i).getPropertyValue(), rangeCoercionTypes.getCoercionTypes()[i]));
        }

        // Get or Create the table for this index (exact match or property names, type of index and coercion type is expected)
        Pair<IndexMultiKey, EventTable> tableDesc = indexRepository.addTableCreateOrReuse(hashedProps, btreeProps, dataWindowContents, namedWindowEventType, keyCoercionTypes.isCoerce());

        // map the order of indexed columns (key) to the key information available
        IndexedPropDesc[] indexedKeyProps = tableDesc.getFirst().getKeyProps();
        String[] indexValueProps = IndexedPropDesc.getIndexProperties(indexedKeyProps);
        String[] keyPropertyNames = new String[indexedKeyProps.length];
        for (int i = 0; i < indexedKeyProps.length; i++) {
            String indexField = indexedKeyProps[i].getIndexPropName();
            int index = CollectionUtil.findItem(indexPropertiesRequired, indexField);
            if (index == -1) {
                throw new IllegalStateException("Could not find index property for lookup '" + indexedKeyProps[i]);
            }
            keyPropertyNames[i] = keyPropertiesAvailable[index];
        }

        // map the order of range columns (range) to the range information available
        indexedKeyProps = tableDesc.getFirst().getRangeProps();
        SubqueryRangeKeyDesc[] rangesDesc = new SubqueryRangeKeyDesc[indexedKeyProps.length];
        QueryGraphValueRange[] ranges = new QueryGraphValueRange[indexedKeyProps.length];
        for (int i = 0; i < indexedKeyProps.length; i++) {
            String indexField = indexedKeyProps[i].getIndexPropName();
            int index = -1;
            for (int j = 0; j < rangePropertiesAvailable.size(); j++) {
                if (rangePropertiesAvailable.get(j).getPropertyValue().equals(indexField)) {
                    index = j;
                }
            }
            if (index == -1) {
                throw new IllegalStateException("Could not find index property for lookup '" + indexedKeyProps[i]);
            }
            ranges[i] = rangePropertiesAvailable.get(index);
            rangesDesc[i] = SubqueryRangeKeyDesc.createSingleStreamNum(rangePropertiesAvailable.get(index), 1);
        }

        keyCoercionTypes = CoercionUtil.getCoercionTypes(new EventType[] {namedWindowEventType, filterEventType}, 1, 0, keyPropertyNames, indexValueProps);
        rangeCoercionTypes = CoercionUtil.getCoercionTypes(new EventType[] {namedWindowEventType, filterEventType}, 1, 0, Arrays.asList(ranges));

        IndexKeyInfo info = new IndexKeyInfo(keyPropertyNames, keyCoercionTypes, Arrays.asList(rangesDesc), rangeCoercionTypes);
        return new Pair<IndexKeyInfo, EventTable>(info, tableDesc.getSecond());
    }

    private Pair<NamedWindowLookupStrategy,EventTable> getStrategyPair(ExprNode joinExpr, EventType filterEventType)
    {
        // No join expression means delete all
        if (joinExpr == null)
        {
            return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyAllRows(dataWindowContents), null);
        }

        Pair<IndexKeyInfo, EventTable> accessDesc = getCreateIndex(joinExpr, filterEventType);

        if (accessDesc == null) {
            return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyTableScan(joinExpr.getExprEvaluator(), dataWindowContents), null);
        }

        String[] keyProperties = accessDesc.getFirst().getOrderedKeyProperties();
        CoercionDesc keyCoercionTypes = accessDesc.getFirst().getOrderedKeyCoercionTypes();
        List<SubqueryRangeKeyDesc> rangeProperties = accessDesc.getFirst().getOrderedRangeDesc();
        CoercionDesc rangeCoercionTypes = accessDesc.getFirst().getOrderedRangeCoercionTypes();

        // assign types and stream numbers
        EventType[] eventTypePerStream = new EventType[] {null, filterEventType};
        int[] keyStreamNums = new int[keyProperties.length];
        Arrays.fill(keyStreamNums, 1);

        SubqTableLookupStrategy lookupStrategy;
        if (keyProperties.length > 0 && rangeProperties.isEmpty()) {
            if (!keyCoercionTypes.isCoerce()) {
                lookupStrategy = new SubqIndexedTableLookupStrategy(eventTypePerStream, keyStreamNums, keyProperties, (PropertyIndexedEventTable) accessDesc.getSecond());
            }
            else {
                lookupStrategy = new SubqIndexedTableLookupStrategyCoercing(eventTypePerStream, keyStreamNums, keyProperties, (PropertyIndexedEventTable) accessDesc.getSecond(), keyCoercionTypes.getCoercionTypes());
            }
        }
        else if (keyProperties.length == 0 && (rangeProperties.size() == 1)) {
            lookupStrategy = new SubqSortedTableLookupStrategy(eventTypePerStream, rangeProperties.get(0), (PropertySortedEventTable) accessDesc.getSecond());
        }
        else {
            lookupStrategy = new SubqCompositeTableLookupStrategy(eventTypePerStream, keyStreamNums, keyProperties, keyCoercionTypes.getCoercionTypes(), rangeProperties, rangeCoercionTypes.getCoercionTypes(), (PropertyCompositeEventTable) accessDesc.getSecond());
        }

        return new Pair<NamedWindowLookupStrategy,EventTable>(new NamedWindowLookupStrategyIndexed(joinExpr.getExprEvaluator(), lookupStrategy), accessDesc.getSecond());
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
     * @param filter to index lookup
     * @return events
     */
    public Collection<EventBean> snapshot(FilterSpecCompiled filter) {
        if (filter.getParameters().isEmpty()) {
            return null;
        }

        // Determine what straight-equals keys and which ranges are available.
        // Widening/Coercion is part of filter spec compile.
        Set<String> keysAvailable = new HashSet<String>();
        Set<String> rangesAvailable = new HashSet<String>();
        for (FilterSpecParam param : filter.getParameters()) {
            if (!(param instanceof FilterSpecParamConstant || param instanceof FilterSpecParamRange)) {
                continue;
            }
            if (param.getFilterOperator() == FilterOperator.EQUAL) {
                Object filterValue = param.getFilterValue(null);
                if (filterValue == null) {
                    keysAvailable.add(param.getPropertyName());
                }
                else {
                    keysAvailable.add(param.getPropertyName());
                }
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
        Pair<IndexMultiKey, EventTable> tablePair = indexRepository.findTable(keysAvailable, rangesAvailable, explicitIndexes);
        if (tablePair == null) {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("no index found");
            }
            return null;    // indicates table scan
        }

        // Compile key index lookup values
        String[] keyIndexProps = IndexedPropDesc.getIndexProperties(tablePair.getFirst().getKeyProps());
        Object[] keyValues = new Object[keyIndexProps.length];
        for (int keyIndex = 0; keyIndex < keyIndexProps.length; keyIndex++) {
            for (FilterSpecParam param : filter.getParameters()) {
                if (param.getPropertyName().equals(keyIndexProps[keyIndex])) {
                    keyValues[keyIndex] = param.getFilterValue(null);
                    break;
                }
            }
        }

        // Analyze ranges - these may include key lookup value (EQUALS semantics)
        String[] rangeIndexProps = IndexedPropDesc.getIndexProperties(tablePair.getFirst().getRangeProps());
        RangeIndexLookupValue[] rangeValues;
        if (rangeIndexProps.length > 0) {
            rangeValues = compileRangeLookupValues(rangeIndexProps, filter.getParameters());
        }
        else {
            rangeValues = new RangeIndexLookupValue[0];
        }

        IndexMultiKey indexMultiKey = tablePair.getFirst();
        Set<EventBean> result;
        if (indexMultiKey.getKeyProps().length > 0 && indexMultiKey.getRangeProps().length == 0) {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("hash-only index lookup, using keys " + Arrays.toString(keyIndexProps));
            }
            PropertyIndexedEventTable table = (PropertyIndexedEventTable) tablePair.getSecond();
            result = table.lookup(keyValues);
        }
        else if (indexMultiKey.getKeyProps().length == 0 && indexMultiKey.getRangeProps().length == 1) {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("btree-only index lookup, using key " + Arrays.toString(indexMultiKey.getRangeProps()));
            }
            PropertySortedEventTable table = (PropertySortedEventTable) tablePair.getSecond();
            result = table.lookupConstants(rangeValues[0]);
        }
        else {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("hash-btree-combination index lookup, using keys " + Arrays.toString(keyIndexProps) + " and " + Arrays.toString(indexMultiKey.getRangeProps()));
            }
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

    public SubqTableLookupStrategy getAddSubqueryLookupStrategy(EventType[] eventTypesPerStream, JoinedPropPlan joinDesc, boolean fullTableScan) {
        // if there are no join criteria to use, return default copy strategy
        if (fullTableScan || (joinDesc.getJoinProps().isEmpty() && joinDesc.getRangeProps().isEmpty())) {
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("shared, full table scan");
            }
            return new SubqFullTableScanLookupStrategyLocking(dataWindowContents, statementResourceLock);
        }

        Set<String> indexedCols = new LinkedHashSet<String>();
        for (Map.Entry<String, JoinedPropDesc> entry : joinDesc.getJoinProps().entrySet()) {
            indexedCols.add(entry.getValue().getIndexPropName());
        }

        Set<String> rangeCols = new LinkedHashSet<String>();
        for (Map.Entry<String, SubqueryRangeKeyDesc> entry : joinDesc.getRangeProps().entrySet()) {
            rangeCols.add(entry.getValue().getRangeInfo().getPropertyValue());
        }

        // find matching table that could be used
        Pair<IndexMultiKey, EventTable> tablePair = indexRepository.findTable(indexedCols, rangeCols, explicitIndexes);

        // no matching existing table found, add new one
        if (tablePair == null) {
            Pair<SubqTableLookupStrategy, String> strategy = addTableGetStrategy(joinDesc, eventTypesPerStream);
            if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
                queryPlanLog.info("new sharable index, " + strategy.getSecond());
            }
            return new SubqIndexedTableLookupStrategyLocking(strategy.getFirst(), statementResourceLock);
        }

        // build strategy based on existing table
        String[] tableIndexProps = IndexedPropDesc.getIndexProperties(tablePair.getFirst().getKeyProps());
        int[] streamNumbers = new int[tableIndexProps.length];
        String[] keyProps = new String[tableIndexProps.length];
        Class[] coercionTypes = new Class[tableIndexProps.length];

        for (int i = 0; i < tableIndexProps.length; i++) {
            String indexedProp = tableIndexProps[i];

            for (Map.Entry<String, JoinedPropDesc> entry : joinDesc.getJoinProps().entrySet()) {
                JoinedPropDesc joinPlan = entry.getValue();
                if (entry.getValue().getIndexPropName().equals(indexedProp)) {
                    streamNumbers[i] = joinPlan.getKeyStreamId();
                    keyProps[i] = joinPlan.getKeyPropName();
                    coercionTypes[i] = joinPlan.getCoercionType();
                }
            }
        }

        SubqIndexedTableLookupStrategy strategy;
        String message = "index lookup on " + Arrays.toString(tableIndexProps) + " based on " + Arrays.toString(keyProps);
        if (!joinDesc.isMustCoerce()) {
            strategy = new SubqIndexedTableLookupStrategy(eventTypesPerStream, streamNumbers, keyProps, (PropertyIndexedEventTable) tablePair.getSecond());
        }
        else {
            strategy = new SubqIndexedTableLookupStrategyCoercing(eventTypesPerStream, streamNumbers, keyProps, (PropertyIndexedEventTable) tablePair.getSecond(), coercionTypes);
            message = "coerced " + message;
        }
        SubqIndexedTableLookupStrategyLocking locking = new SubqIndexedTableLookupStrategyLocking(strategy, statementResourceLock);
        tablePerSingleLookup.put(locking, tablePair.getSecond());
        indexRepository.addTableReference(tablePair.getSecond());
        
        if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
            queryPlanLog.info("existing index, " + message);
        }
        return locking;
    }

    private Pair<SubqTableLookupStrategy, String> addTableGetStrategy(JoinedPropPlan joinDesc, EventType[] eventTypePerStream) {

        Collection<JoinedPropDesc> propsJoined = joinDesc.getJoinProps().values();
        JoinedPropDesc[] propsJoinedArr = propsJoined.toArray(new JoinedPropDesc[propsJoined.size()]);
        Arrays.sort(propsJoinedArr);

        String[] indexPropertiesJoin = JoinedPropUtil.getIndexProperties(propsJoinedArr);
        String[] keyPropertiesJoin = JoinedPropUtil.getKeyProperties(propsJoinedArr);
        Class[] coercionTypes = JoinedPropUtil.getCoercionTypes(propsJoinedArr);
        int[] streamNumbersPerProperty = JoinedPropUtil.getKeyStreamNums(propsJoinedArr);

        // Add all joined fields to an array for sorting
        // TODO
        List<IndexedPropDesc> indexedPropDesc = new ArrayList<IndexedPropDesc>();
        for (int i = 0; i < propsJoinedArr.length; i++)
        {
            indexedPropDesc.add(new IndexedPropDesc(indexPropertiesJoin[i], coercionTypes[i]));
        }

        // Get the table for this index
        Pair<IndexMultiKey, EventTable> tablePair = indexRepository.addTableCreateOrReuse(indexedPropDesc, Collections.EMPTY_LIST, dataWindowContents, namedWindowEventType, joinDesc.isMustCoerce());

        // create the strategy
        SubqTableLookupStrategy lookupStrategy;
        String message = "index lookup on " + Arrays.toString(indexPropertiesJoin) + " based on " + Arrays.toString(keyPropertiesJoin);
        if (!joinDesc.isMustCoerce())
        {
            lookupStrategy = new SubqIndexedTableLookupStrategy(eventTypePerStream, streamNumbersPerProperty, keyPropertiesJoin, (PropertyIndexedEventTable) tablePair.getSecond());
        }
        else
        {
            lookupStrategy = new SubqIndexedTableLookupStrategyCoercing(eventTypePerStream, streamNumbersPerProperty, keyPropertiesJoin, (PropertyIndexedEventTable) tablePair.getSecond(), coercionTypes);
            message = "coerced " + message;
        }
        return new Pair<SubqTableLookupStrategy, String>(lookupStrategy, message);
    }

    public void removeSubqueryLookupStrategy(SubqTableLookupStrategy namedWindowSubqueryLookup) {
        EventTable table = tablePerSingleLookup.remove(namedWindowSubqueryLookup);
        if (table != null) {
            indexRepository.removeTableReference(table);
        }
    }
}
