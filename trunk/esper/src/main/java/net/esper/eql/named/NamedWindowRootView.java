package net.esper.eql.named;

import net.esper.collection.Pair;
import net.esper.eql.join.plan.FilterExprAnalyzer;
import net.esper.eql.join.plan.QueryGraph;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.lookup.IndexedTableLookupStrategy;
import net.esper.eql.lookup.IndexedTableLookupStrategyCoercing;
import net.esper.eql.lookup.TableLookupStrategy;
import net.esper.eql.lookup.JoinedPropDesc;
import net.esper.eql.spec.OnDeleteDesc;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.util.JavaClassHelper;
import net.esper.view.StatementStopService;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private final Map<DeletionStrategy, PropertyIndexedEventTable> tablePerStrategy;

    /**
     * Ctor.
     */
    public NamedWindowRootView()
    {
        this.indexRepository = new NamedWindowIndexRepository();
        this.tablePerStrategy = new HashMap<DeletionStrategy, PropertyIndexedEventTable>();
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
    //
    public void removeOldData(EventBean[] oldData)
    {
        for (EventTable table : indexRepository.getTables())
        {
            table.remove(oldData);
        }
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

        // Update indexes for fast deletion, if there are any
        for (EventTable table : indexRepository.getTables())
        {
            table.add(newData);
            table.remove(oldData);
        }

        // Update child views
        updateChildren(newData, oldData);
    }

    /**
     * Add a on-delete view that, using a deletion strategy, deletes from the named window.
     * @param onDeleteDesc the specification for the on-delete
     * @param filterEventType the event type for the on-clause in the on-delete
     * @param statementStopService for stopping the statement
     * @return view representing the on-delete view chain, posting delete events to it's listeners
     */
    public NamedWindowDeleteView addDeleter(OnDeleteDesc onDeleteDesc, EventType filterEventType, StatementStopService statementStopService)
    {
        // Determine strategy for deletion and index table to use (if any)
        Pair<DeletionStrategy,PropertyIndexedEventTable> strategy = getDeletionStrategy(onDeleteDesc, filterEventType);
        if (strategy.getSecond() != null)
        {
            tablePerStrategy.put(strategy.getFirst(), strategy.getSecond());
        }
        return new NamedWindowDeleteView(statementStopService, strategy.getFirst(), this);
    }

    /**
     * Unregister an on-delete statement view, using the strategy as a key to remove a reference to the index table
     * used by the strategy.
     * @param strategy to use for deleting events
     */
    public void removeDeleter(DeletionStrategy strategy)
    {
        PropertyIndexedEventTable table = tablePerStrategy.remove(strategy);
        if (table != null)
        {
            indexRepository.removeTableReference(table);
        }
    }

    private Pair<DeletionStrategy,PropertyIndexedEventTable> getDeletionStrategy(OnDeleteDesc onDeleteDesc, EventType filterEventType)
    {
        // No join expression means delete all
        if (onDeleteDesc.getJoinExpr() == null)
        {
            return new Pair<DeletionStrategy,PropertyIndexedEventTable>(new DeletionStrategyDeleteAll(dataWindowContents), null);
        }

        // analyze query graph; Whereas stream0=named window, stream1=delete-expr filter
        QueryGraph queryGraph = new QueryGraph(2);
        FilterExprAnalyzer.analyze(onDeleteDesc.getJoinExpr(), queryGraph);

        // index and key property names
        String[] keyPropertiesJoin = queryGraph.getKeyProperties(1, 0);
        String[] indexPropertiesJoin = queryGraph.getIndexProperties(1, 0);

        // If the analysis revealed no join columns, must use the brute-force full table scan
        if ((keyPropertiesJoin == null) || (keyPropertiesJoin.length == 0))
        {
            return new Pair<DeletionStrategy,PropertyIndexedEventTable>(new DeletionStrategyTableScan(onDeleteDesc.getJoinExpr(), dataWindowContents), null);
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
        for (int i = 0; i < joinedPropDesc.length; i++)
        {
            joinedPropDesc[i] = new JoinedPropDesc(indexPropertiesJoin[i], coercionTypes[i], keyPropertiesJoin[i], 1);
        }
        Arrays.sort(joinedPropDesc);
        keyPropertiesJoin = JoinedPropDesc.getKeyProperties(joinedPropDesc);
        indexPropertiesJoin = JoinedPropDesc.getIndexProperties(joinedPropDesc);

        // Get the table for this index
        PropertyIndexedEventTable table = indexRepository.addTable(joinedPropDesc, dataWindowContents, namedWindowEventType, mustCoerce);

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

        return new Pair<DeletionStrategy,PropertyIndexedEventTable>(new DeletionStrategyIndexed(onDeleteDesc.getJoinExpr(), lookupStrategy), table);
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
}
