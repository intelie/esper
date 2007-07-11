using System;
using System.Text;

using net.esper.core;
using net.esper.compat;
using net.esper.events;
using net.esper.filter;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// This class contains the state of a single filter expression in the evaluation state tree.
    /// </summary>

    public sealed class EvalFilterStateNode
		: EvalStateNode
		, FilterHandleCallback
    {
		private readonly EvalFilterNode evalFilterNode;
	    private readonly MatchedEventMap beginState;
	    private readonly PatternContext context;

	    private bool isStarted;
	    private EPStatementHandleCallback handle;

	    /// <summary>Constructor.</summary>
	    /// <param name="parentNode">
	    /// is the parent evaluator to call to indicate truth value
	    /// </param>
	    /// <param name="beginState">contains the events that make up prior matches</param>
	    /// <param name="context">contains handles to services required</param>
	    /// <param name="evalFilterNode">is the factory node associated to the state</param>
	    public EvalFilterStateNode(Evaluator parentNode,
	                               EvalFilterNode evalFilterNode,
	                               MatchedEventMap beginState,
	                               PatternContext context)
            : base(evalFilterNode, parentNode, null)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

			this.evalFilterNode = evalFilterNode;
            this.beginState = beginState;
            this.context = context;
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
                log.Debug(".Start Starting filter expression");
            }

            if (isStarted)
            {
                throw new IllegalStateException("Filter state node already active");
            }

            // Start the filter
            isStarted = true;
            StartFiltering();
        }

        /// <summary>
        /// Stops the event expression or an instance of it. Child classes are expected to free resources
        /// and Stop any event listeners or remove any time-based callbacks.
        /// </summary>
        public override void Quit()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".Quit Stop filter expression");
            }

            isStarted = false;
            StopFiltering();
        }

        private void EvaluateTrue(MatchedEventMap _event, bool isQuitted)
        {
            this.ParentEvaluator.EvaluateTrue(_event, this, isQuitted);
        }

        /// <summary>
        /// Indicate that an event was evaluated by the <seealso cref="FilterService"/>
        /// which matches the filter specification <seealso cref="FilterSpec"/> associated with this callback.
        /// </summary>
        /// <param name="_event"></param>
        public void MatchFound(EventBean _event)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".matchFound Filter node received match");
            }

            if (!isStarted)
            {
                log.Debug(".matchFound Match ignored, filter was Stopped");
                return;
            }

            MatchedEventMap passUp = beginState.ShallowCopy();

            // Add event itself to the match event structure if a tag was provided
	        if (evalFilterNode.EventAsName != null)
	        {
	            passUp.Add(evalFilterNode.EventAsName, _event);
	        }

            // Explanation for the type cast...
            // Each state node Stops listening if it resolves to true, and all nodes newState
            // new listeners again. However this would be a performance drain since
            // and expression such as "on all b()" would remove the listener for b() for every match
            // and the all node would newState a new listener. The remove operation and the add operation
            // therefore don't take place if the EvalEveryStateNode node sits on top of a EvalFilterStateNode node.
            bool isQuitted = false;
            if (!(this.ParentEvaluator is EvalEveryStateNode))
            {
                StopFiltering();
                isQuitted = true;
            }

            this.EvaluateTrue(passUp, isQuitted);
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
	        StringBuilder buffer = new StringBuilder();
	        buffer.Append("EvalFilterStateNode");
	        buffer.Append(" tag=");
	        buffer.Append(evalFilterNode.FilterSpec);
	        buffer.Append(" spec=");
	        buffer.Append(evalFilterNode.FilterSpec);
	        return buffer.ToString();
        }

        private void StartFiltering()
        {
	        handle = new EPStatementHandleCallback(context.EpStatementHandle, this);
	        FilterValueSet filterValues = evalFilterNode.FilterSpec.GetValueSet(beginState);
	        context.FilterService.Add(filterValues, handle);
        }

        private void StopFiltering()
        {
            context.FilterService.Remove(handle);
			handle = null;
			isStarted = false;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
