using System;
using System.Collections.Generic;

using net.esper.compat;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.pattern
{
    /// <summary>
    /// This class represents the state of a "and" operator in the evaluation state tree.
    /// </summary>

    public sealed class EvalAndStateNode : EvalStateNode, Evaluator
    {
        private readonly ELinkedList<EvalNode> childNodes;
        private readonly IList<EvalStateNode> activeChildNodes;
        private EDictionary<EvalStateNode, IList<MatchedEventMap>> eventsPerChild;

        /**
         * Constructor.
         * @param parentNode is the parent evaluator to call to indicate truth value
         * @param childNodes is the and-nodes child nodes
         * @param beginState contains the events that make up prior matches
         * @param context contains handles to services required
         */
        public EvalAndStateNode(Evaluator parentNode,
                                      ELinkedList<EvalNode> childNodes,
                                      MatchedEventMap beginState,
                                      PatternContext context)
            : base(parentNode)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

            this.childNodes = childNodes;
            this.activeChildNodes = new ELinkedList<EvalStateNode>();
            this.eventsPerChild = new EHashDictionary<EvalStateNode, IList<MatchedEventMap>>();

            // In an "and" expression we need to create a state for all child listeners
            foreach (EvalNode node in childNodes)
            {
                EvalStateNode childState = node.newState(this, beginState, context);
                activeChildNodes.Add(childState);
            }
        }

        public override void Start()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".Start Starting and-expression all children, size=" + childNodes.Count);
            }

            if (activeChildNodes.Count < 2)
            {
                throw new IllegalStateException("EVERY state node is inactive");
            }

            // Start all child nodes
            foreach (EvalStateNode child in activeChildNodes)
            {
                child.Start();
            }
        }

        public void EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, Boolean isQuitted)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".evaluateTrue fromNode=" + fromNode.GetHashCode());
            }

            // If one of the children quits, remove the child
            if (isQuitted)
            {
                activeChildNodes.Remove(fromNode);
            }

            // Add the event received to the list of events per child
            IList<MatchedEventMap> eventList = eventsPerChild.Fetch(fromNode);
            if (eventList == null)
            {
                eventList = new ELinkedList<MatchedEventMap>();
                eventsPerChild[fromNode] = eventList;
            }
            eventList.Add(matchEvent);

            // If all nodes have events received, the AND expression turns true
            if (eventsPerChild.Count < childNodes.Count)
            {
                return;
            }

            // For each combination in eventsPerChild for all other state nodes generate an event to the parent
            IList<MatchedEventMap> result = GenerateMatchEvents(matchEvent, fromNode, eventsPerChild);

            // Check if this is quitting
            Boolean quitted = (activeChildNodes.Count == 0);
            if (quitted)
            {
                Quit();
            }

            // Send results to parent
            foreach (MatchedEventMap ev in result)
            {
                this.ParentEvaluator.EvaluateTrue(ev, this, quitted);
            }
        }

        public void EvaluateFalse(EvalStateNode fromNode)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".evaluateFalse Removing fromNode=" + fromNode.GetHashCode());
            }

            eventsPerChild.Remove(fromNode);

            // The and node cannot turn true anymore, might as well quit all child nodes
            this.ParentEvaluator.EvaluateFalse(this);
            Quit();
        }

        /**
         * Generate a list of matching event combinations constisting of the events per child that are passed in.
         * @param matchEvent can be populated with prior events that must be passed on
         * @param fromNode is the EvalStateNode that will not take part in the combinations produced.
         * @param eventsPerChild is the list of events for each child node to the "And" node.
         * @return list of events populated with all possible combinations
         */
        public static IList<MatchedEventMap> GenerateMatchEvents(
            MatchedEventMap matchEvent,
            EvalStateNode fromNode,
            EDictionary<EvalStateNode, IList<MatchedEventMap>> eventsPerChild)
        {
            // Place event list for each child state node into an array, excluding the node where the event came from
            List<IList<MatchedEventMap>> listArray = new List<IList<MatchedEventMap>>();
            int index = 0;
            foreach (KeyValuePair<EvalStateNode, IList<MatchedEventMap>> entry in eventsPerChild)
            {
                if (fromNode != entry.Key)
                {
                    listArray.Insert(index++, entry.Value);
                }
            }

            // Recusively generate MatchedEventMap instances for all accumulated events
            IList<MatchedEventMap> results = new ELinkedList<MatchedEventMap>();
            GenerateMatchEvents(listArray, 0, results, matchEvent);

            return results;
        }

        /**
         * For each combination of MatchedEventMap instance in all collections, add an entry to the list.
         * Recursive method.
         * @param eventList is an array of lists containing MatchedEventMap instances to combine
         * @param index is the current index into the array
         * @param result is the resulting list of MatchedEventMap
         * @param matchEvent is the Start MatchedEventMap to generate from
         */
        public static void GenerateMatchEvents(
        	IList<IList<MatchedEventMap>> eventList,
            int index,
            IList<MatchedEventMap> result,
            MatchedEventMap matchEvent)
        {
        	IList<MatchedEventMap> events = eventList[index];

            foreach (MatchedEventMap ev in events)
            {
                MatchedEventMap current = matchEvent.shallowCopy();
                current.merge(ev);

                // If this is the very last list in the array of lists, add accumulated MatchedEventMap events to result
                if ((index + 1) == eventList.Count)
                {
                    result.Add(current);
                }
                else
                {
                    // make a copy of the event collection and hand to next list of events
                    GenerateMatchEvents(eventList, index + 1, result, current);
                }
            }
        }

        public override void Quit()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".guardQuit Stopping all children");
            }

            foreach (EvalStateNode child in activeChildNodes)
            {
                child.Quit();
            }
			activeChildNodes.Clear();
            eventsPerChild = null;
        }

        public override Object Accept(EvalStateNodeVisitor visitor, Object data)
        {
            return visitor.visit(this, data);
        }

        public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
        {
            foreach (EvalStateNode node in activeChildNodes)
            {
                node.Accept(visitor, data);
            }
            return data;
        }

        public override String ToString()
        {
            return "EvalAndStateNode nodes=" + activeChildNodes.Count;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalAndStateNode));
    }
}
