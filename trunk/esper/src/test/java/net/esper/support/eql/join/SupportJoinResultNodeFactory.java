package net.esper.support.eql.join;

import net.esper.eql.join.rep.Node;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

public class SupportJoinResultNodeFactory
{
    public static List<Node>[] makeOneStreamResult(int numStreams, int fillStream, int numNodes, int numEventsPerNode)
    {
        List<Node>[] resultsPerStream = new List[numStreams];
        resultsPerStream[fillStream] = new LinkedList<Node>();

        for (int i = 0; i < numNodes; i++)
        {
            Node node = makeNode(i, numEventsPerNode);
            resultsPerStream[fillStream].add(node);
        }

        return resultsPerStream;
    }

    public static Node makeNode(int streamNum, int numEventsPerNode)
    {
        Node node = new Node(streamNum);
        node.setEvents(makeEventSet(numEventsPerNode));
        return node;
    }

    public static Set<EventBean> makeEventSet(int numObjects)
    {
        if (numObjects == 0)
        {
            return null;
        }
        Set<EventBean> set = new HashSet<EventBean>();
        for (int i = 0; i < numObjects; i++)
        {
            set.add(makeEvent());
        }
        return set;
    }

    public static Set<EventBean>[] makeEventSets(int[] numObjectsPerSet)
    {
        Set<EventBean>[] sets = new HashSet[numObjectsPerSet.length];
        for (int i = 0; i < numObjectsPerSet.length; i++)
        {
            if (numObjectsPerSet[i] == 0)
            {
                continue;
            }
            sets[i] = makeEventSet(numObjectsPerSet[i]);
        }
        return sets;
    }

    public static EventBean makeEvent()
    {
        EventBean event = SupportEventBeanFactory.createObject(new Object());
        return event;
    }

    public static EventBean[] makeEvents(int numEvents)
    {
        EventBean events[] = new EventBean[numEvents];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = makeEvent();
        }
        return events;
    }

    public static EventBean[][] convertTo2DimArr(List<EventBean[]> rowList)
    {
        EventBean[][] result = new EventBean[rowList.size()][];

        int count = 0;
        for (EventBean[] row : rowList)
        {
            result[count++] = row;
        }

        return result;
    }
}
