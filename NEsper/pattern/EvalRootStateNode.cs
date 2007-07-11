using System;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.pattern
{
    /// <summary> This class is always the root node in the evaluation state tree representing any activated event expression.
    /// It hold the handle to a further state node with subnodes making up a whole evaluation state tree.
    /// </summary>

    public sealed class EvalRootStateNode
		: EvalStateNode
		, EvalRootState
		, Evaluator
    {
        /// <summary>
        /// Gets or sets the callback to use to indicate matching events.
        /// </summary>
        /// <value>The callback.</value>

        public PatternMatchCallback Callback
        {
        	get { return this.callback ; }
            set { this.callback = value; }
        }

        private EvalStateNode topStateNode;
        private PatternMatchCallback callback;

        /// <summary> Constructor.</summary>
        /// <param name="rootSingleChildNode">is the root nodes single child node
        /// </param>
        /// <param name="beginState">contains the events that make up prior matches
        /// </param>
        /// <param name="context">contains handles to services required
        /// </param>
        public EvalRootStateNode(EvalNode rootSingleChildNode, MatchedEventMap beginState, PatternContext context)
            : base(rootSingleChildNode, null, null)
        {

            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

            topStateNode = rootSingleChildNode.NewState(this, beginState, context, null);

            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor Done, dumping full tree");
                EvalStateNodePrinterVisitor visitor = new EvalStateNodePrinterVisitor();
                this.Accept(visitor, (Object)null);
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
                log.Debug(".Start Starting single child");
            }

            if (topStateNode == null)
            {
                throw new IllegalStateException("root state node is inactive");
            }

            topStateNode.Start();
        }

        /// <summary>
        /// Stops this instance.
        /// </summary>
        public void Stop()
        {
            Quit();
        }

        /// <summary>
        /// Stops the event expression or an instance of it. Child classes are expected to free resources
        /// and Stop any event listeners or remove any time-based callbacks.
        /// </summary>
        public override void Quit()
        {
            if (topStateNode != null)
            {
                topStateNode.Quit();
            }
            topStateNode = null;
        }

        /// <summary>
        /// Indicate a change in truth value to true.
        /// </summary>
        /// <param name="matchEvent">is the container for events that caused the change in truth value</param>
        /// <param name="fromNode">is the node that indicates the change</param>
        /// <param name="isQuitted">is an indication of whether the node continues listenening or Stops listening</param>
        public void EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".evaluateTrue isQuitted=" + isQuitted);
            }

            if (isQuitted)
            {
                topStateNode = null;
            }

            callback.MatchFound(matchEvent.MatchingEvents);
        }

        /// <summary>
        /// Indicate a change in truth value to false.
        /// </summary>
        /// <param name="fromNode">is the node that indicates the change</param>
        public void EvaluateFalse(EvalStateNode fromNode)
        {
            log.Debug(".evaluateFalse");
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
            if (topStateNode != null)
            {
                topStateNode.Accept(visitor, data);
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
            return "EvalRootStateNode topStateNode=" + topStateNode;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
