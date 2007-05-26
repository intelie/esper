using System;

using net.esper.pattern.observer;

using org.apache.commons.logging;

namespace net.esper.pattern
{
    /// <summary>
	/// This class represents the state of an eventObserver sub-expression
	/// in the evaluation state tree.
	/// </summary>
    public sealed class EvalObserverStateNode
		: EvalStateNode
		, ObserverEventEvaluator
    {
        private EventObserver eventObserver;

        /// <summary> Constructor.</summary>
        /// <param name="parentNode">is the parent evaluator to call to indicate truth value
        /// </param>
        /// <param name="evalObserverNode">the factory node associated to the state
        /// </param>
        /// <param name="beginState">contains the events that make up prior matches
        /// </param>
        /// <param name="context">contains handles to services required
        /// </param>
        public EvalObserverStateNode(Evaluator parentNode, 
									 EvalObserverNode evalObserverNode,
									 MatchedEventMap beginState,
									 PatternContext context)
            : base(evalObserverNode, parentNode, null)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

            eventObserver = evalObserverNode.ObserverFactory.MakeObserver(context, beginState, this, null, null);
        }

        /// <summary>
        /// Indicate an event for evaluation (sub-expression the observer represents has turned true).
        /// </summary>
        /// <param name="matchEvent">is the matched events so far</param>
        public void ObserverEvaluateTrue(MatchedEventMap matchEvent)
        {
            this.ParentEvaluator.EvaluateTrue(matchEvent, this, true);
        }

        /// <summary>
        /// Indicate that the observer turned permanently false.
        /// </summary>
        public void ObserverEvaluateFalse()
        {
            this.ParentEvaluator.EvaluateFalse(this);
        }

        /// <summary>
        /// Starts the event expression or an instance of it.
        /// Child classes are expected to initialize and Start any event listeners
        /// or schedule any time-based callbacks as needed.
        /// </summary>
        public override void Start()
        {
            eventObserver.StartObserve();
        }

        /// <summary>
        /// Stops the event expression or an instance of it. Child classes are expected to free resources
        /// and Stop any event listeners or remove any time-based callbacks.
        /// </summary>
        public override void Quit()
        {
            eventObserver.StopObserve();
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
            return "EvalObserverStateNode eventObserver=" + eventObserver;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
