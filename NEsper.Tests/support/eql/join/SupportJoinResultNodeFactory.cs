using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.support.events;

namespace net.esper.support.eql.join
{
    public class SupportJoinResultNodeFactory
    {
        public static IList<Node>[] makeOneStreamResult(int numStreams, int fillStream, int numNodes, int numEventsPerNode)
        {
            IList<Node>[] resultsPerStream = new List<Node>[numStreams];
            resultsPerStream[fillStream] = new List<Node>();

            for (int i = 0; i < numNodes; i++)
            {
                Node node = makeNode(i, numEventsPerNode);
                resultsPerStream[fillStream].Add(node);
            }

            return resultsPerStream;
        }

        public static Node makeNode(int streamNum, int numEventsPerNode)
        {
            Node node = new Node(streamNum);
            node.Events = makeEventSet(numEventsPerNode);
            return node;
        }

        public static ISet<EventBean> makeEventSet(int numObjects)
        {
            if (numObjects == 0)
            {
                return null;
            }
            ISet<EventBean> set = new EHashSet<EventBean>();
            for (int i = 0; i < numObjects; i++)
            {
                set.Add(MakeEvent());
            }
            return set;
        }

        public static ISet<EventBean>[] makeEventSets(int[] numObjectsPerSet)
        {
            ISet<EventBean>[] sets = new EHashSet<EventBean>[numObjectsPerSet.Length];
            for (int i = 0; i < numObjectsPerSet.Length; i++)
            {
                if (numObjectsPerSet[i] == 0)
                {
                    continue;
                }
                sets[i] = makeEventSet(numObjectsPerSet[i]);
            }
            return sets;
        }

        public static EventBean MakeEvent()
        {
            EventBean _event = SupportEventBeanFactory.createObject(new Object());
            return _event;
        }

        public static EventBean[] MakeEvents(int numEvents)
        {
            EventBean[] events = new EventBean[numEvents];
            for (int i = 0; i < events.Length; i++)
            {
                events[i] = MakeEvent();
            }
            return events;
        }

        public static EventBean[][] convertTo2DimArr(IList<EventBean[]> rowList)
        {
            EventBean[][] result = new EventBean[rowList.Count][];

            int count = 0;
            foreach (EventBean[] row in rowList)
            {
                result[count++] = row;
            }

            return result;
        }
    }
}
