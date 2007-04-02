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

        /// <summary>
        /// Initializes a new instance of the <see cref="EvalAndStateNode"/> class.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator to call to indicate truth value</param>
        /// <param name="childNodes">is the and-nodes child nodes</param>
        /// <param name="beginState">contains the events that make up prior matches</param>
        /// <param name="context">contains handles to services required</param>

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
            this.activeChildNodes = new List<EvalStateNode>();
            this.eventsPerChild = new EHashDictionary<EvalStateNode, IList<MatchedEventMap>>();

            // In an "and" expression we need to create a state for all child listeners
            foreach (EvalNode node in childNodes)
            {
                EvalStateNode childState = node.NewState(this, beginState, context);
                activeChildNodes.Add(childState);
            }
        }

        /// <summary>
        /// Starts the event expression or an instance of it.
        /// Child classes are expected to initialize and Start any event listeners
        /// or schedule any time-based callbacks as needed.
        /// </summary>
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

        /// <summary>
        /// Indicate a change in truth value to true.
        /// </summary>
        /// <param name="matchEvent">is the container for events that caused the change in truth value</param>
        /// <param name="fromNode">is the node that indicates the change</param>
        /// <param name="isQuitted">is an indication of whether the node continues listenening or Stops listening</param>
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
                eventList = new List<MatchedEventMap>();
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

        /// <summary>
        /// Indicate a change in truth value to false.
        /// </summary>
        /// <param name="fromNode">is the node that indicates the change</param>
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

        /// <summary>Generate a list of matching event combinations constisting of the events per child that are passed in.</summary>
        /// <param name="matchEvent">can be populated with prior events that must be passed on</param>
        /// <param name="fromNode">is the EvalStateNode that will not take part in the combinations produced.</param>
        /// <param name="eventsPerChild">is the list of events for each child node to the "And" node.</param>
        /// <returns>list of events populated with all possible combinations</returns>

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
            IList<MatchedEventMap> results = new List<MatchedEventMap>();
            GenerateMatchEvents(listArray, 0, results, matchEvent);

            return results;
        }

        /// <summary>For each combination of MatchedEventMap instance in all collections, add an entry to the list.Recursive method.</summary>
        /// <param name="eventList">is an array of lists containing MatchedEventMap instances to combine</param>
        /// <param name="index">is the current index into the array</param>
        /// <param name="result">is the resulting list of MatchedEventMap</param>
        /// <param name="matchEvent">is the Start MatchedEventMap to generate from</param>

        public static void GenerateMatchEvents(
        	IList<IList<MatchedEventMap>> eventList,
            int index,
            IList<MatchedEventMap> result,
            MatchedEventMap matchEvent)
        {
        	IList<MatchedEventMap> events = eventList[index];

            foreach (MatchedEventMap ev in events)
            {
                MatchedEventMap current = matchEvent.ShallowCopy();
                current.Merge(ev);

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

        /// <summary>
        /// Stops the event expression or an instance of it. Child classes are expected to free resources
        /// and Stop any event listeners or remove any time-based callbacks.
        /// </summary>
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

        /// <summary>
        /// Accept a visitor. Child classes are expected to invoke the visit method on the visitor instance
        /// passed in.
        /// </summary>
        /// <param name="visitor">on which the visit method is invoked by each node</param>
        /// <param name="data">any additional data the visitor may need is passed in this parameter</param>
        /// <returns>
        /// any additional data the visitor may need or null
        /// </returns>
        public override Object Accept(EvalStateNodeVisitor visitor, Object data)
        {
            return visitor.Visit(this, data);
        }

        /// <summary>
        /// Pass the visitor to all child nodes.
        /// </summary>
        /// <param name="visitor">is the instance to be passed to all child nodes</param>
        /// <param name="data">any additional data the visitor may need is passed in this parameter</param>
        /// <returns>
        /// any additional data the visitor may need or null
        /// </returns>
        public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
        {
            foreach (EvalStateNode node in activeChildNodes)
            {
                node.Accept(visitor, data);
            }
            return data;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "EvalAndStateNode nodes=" + activeChildNodes.Count;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalAndStateNode));
    }
}
