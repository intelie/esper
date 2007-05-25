package net.esper.eql.core;

import net.esper.collection.MultiKeyUntyped;
import net.esper.collection.Pair;
import net.esper.eql.expression.ExprNode;
import net.esper.event.EventBean;
import net.esper.util.MultiKeyComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class OrderBySorter
{
    private static final Log log = LogFactory.getLog(OrderBySorter.class);
    private final Comparator<MultiKeyUntyped> comparator;

    public OrderBySorter(List<Pair<ExprNode, Boolean>> orderByList)
    {
        Boolean ascDesc[] = new Boolean[orderByList.size()];
        int count = 0;
        for(Pair<ExprNode, Boolean> pair : orderByList)
        {
            ascDesc[count++] = pair.getSecond();
        }
        this.comparator = new MultiKeyComparator(ascDesc);
    }

    public EventBean[] sort(EventBean[] outgoingEvents, MultiKeyUntyped[] orderKeys)
    {
        TreeMap<MultiKeyUntyped, Object> sort = new TreeMap<MultiKeyUntyped, Object>(comparator);

        if (outgoingEvents == null || outgoingEvents.length < 2)
        {
            return outgoingEvents;
        }

        for (int i = 0; i < outgoingEvents.length; i++)
        {
            Object entry = sort.get(orderKeys[i]);
            if (entry == null)
            {
                sort.put(orderKeys[i], outgoingEvents[i]);
            }
            else if (entry instanceof EventBean)
            {
                List<EventBean> list = new ArrayList<EventBean>();
                list.add((EventBean)entry);
                list.add(outgoingEvents[i]);
                sort.put(orderKeys[i], list);
            }
            else
            {
                List<EventBean> list = (List<EventBean>) entry;
                list.add(outgoingEvents[i]);
            }
        }

        EventBean[] result = new EventBean[outgoingEvents.length];
        int count = 0;
        for (Object entry : sort.values())
        {
            if (entry instanceof List)
            {
                List<EventBean> output = (List<EventBean>) entry;
                for(EventBean event : output)
                {
                    result[count++] = event;
                }
            }
            else
            {
                result[count++] = (EventBean) entry;
            }
        }
        return result;
    }
}
