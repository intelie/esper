using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
    /// <summary> This class is always the root node in the evaluation state tree representing any activated event expression.
    /// It hold the handle to a further state node with subnodes making up a whole evaluation state tree.
    /// </summary>

    public sealed class EvalRootStateNode : EvalStateNode, Evaluator //, PatternStopCallback
    {
        /// <summary> Hands the callback to use to indicate matching events.</summary>
        /// <param name="callback">is invoked when the event expressions turns true.
        /// </param>

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
            : base(null)
        {

            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

            topStateNode = rootSingleChildNode.newState(this, beginState, context);

            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor Done, dumping full tree");
                EvalStateNodePrinterVisitor visitor = new EvalStateNodePrinterVisitor();
                this.Accept(visitor, (Object)null);
            }
        }

        public override void Start()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".Start Starting single child");
            }

            if (topStateNode == null)
            {
                throw new SystemException("root state node is inactive");
            }

            topStateNode.Start();
        }

        public void Stop()
        {
            Quit();
        }

        public override void Quit()
        {
            if (topStateNode != null)
            {
                topStateNode.Quit();
            }
            topStateNode = null;
        }

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

            callback.matchFound(matchEvent.getMatchingEvents());
        }

        public void EvaluateFalse(EvalStateNode fromNode)
        {
            log.Debug(".evaluateFalse");
        }

        public override Object Accept(EvalStateNodeVisitor visitor, Object data)
        {
            return visitor.visit(this, data);
        }

        public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
        {
            if (topStateNode != null)
            {
                topStateNode.Accept(visitor, data);
            }
            return data;
        }

        public override String ToString()
        {
            return "EvalRootStateNode topStateNode=" + topStateNode;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalRootStateNode));
    }
}
