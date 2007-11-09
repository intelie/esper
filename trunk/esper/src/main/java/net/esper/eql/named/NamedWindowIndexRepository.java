package net.esper.eql.named;

import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.PropertyIndTableCoerceAdd;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.lookup.JoinedPropDesc;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;

import java.util.*;

public class NamedWindowIndexRepository
{
    private List<EventTable> tables;
    private Map<MultiKey<IndexedProp>, Pair<PropertyIndexedEventTable, Integer>> tableIndexes;

    public NamedWindowIndexRepository()
    {
        tables = new ArrayList<EventTable>();
        tableIndexes = new HashMap<MultiKey<IndexedProp>, Pair<PropertyIndexedEventTable, Integer>>();
    }

    // properties MUST be sorted
    public PropertyIndexedEventTable addTable(JoinedPropDesc[] joinedPropDesc,
                               Iterable<EventBean> prefilledEvents,
                               EventType indexedType,
                               boolean mustCoerce)
    {
        MultiKey indexPropKey = new MultiKey<JoinedPropDesc>(joinedPropDesc);

        // Get an existing table, if any
        Pair<PropertyIndexedEventTable, Integer> refTablePair = tableIndexes.get(indexPropKey);
        if (refTablePair != null)
        {
            refTablePair.setSecond(refTablePair.getSecond() + 1);
            return refTablePair.getFirst();
        }

        String[] indexProps = JoinedPropDesc.getIndexProperties(joinedPropDesc);
        Class[] coercionTypes = JoinedPropDesc.getCoercionTypes(joinedPropDesc);
        PropertyIndexedEventTable table;
        if (!mustCoerce)
        {
            table = new PropertyIndexedEventTable(0, indexedType, indexProps);
        }
        else
        {
            table = new PropertyIndTableCoerceAdd(0, indexedType, indexProps, coercionTypes);
        }

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
        tableIndexes.put(indexPropKey, new Pair<PropertyIndexedEventTable, Integer>(table, 1));
        
        return table;
    }

    public void removeTableReference(EventTable table)
    {
        for (Map.Entry<MultiKey<IndexedProp>, Pair<PropertyIndexedEventTable, Integer>> entry : tableIndexes.entrySet())
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
                tableIndexes.remove(entry.getKey());
                break;
            }
        }
    }

    public List<EventTable> getTables()
    {
        return tables;
    }

    public class IndexedProp implements Comparable
    {
        private String name;
        private Class type;

        public IndexedProp(String name, Class type)
        {
            this.name = name;
            this.type = type;
        }

        public String getName()
        {
            return name;
        }

        public Class getType()
        {
            return type;
        }

        public int compareTo(Object o)
        {
            IndexedProp other = (IndexedProp) o;
            return name.compareTo(other.getName());
        }

        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            IndexedProp that = (IndexedProp) o;

            if (!name.equals(that.name))
            {
                return false;
            }
            if (!type.equals(that.type))
            {
                return false;
            }

            return true;
        }

        public int hashCode()
        {
            int result;
            result = name.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }
}
