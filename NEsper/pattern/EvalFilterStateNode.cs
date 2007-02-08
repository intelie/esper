using System;

using net.esper.events;
using net.esper.filter;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// This class contains the state of a single filter expression in the evaluation state tree.
    /// </summary>

    public sealed class EvalFilterStateNode : EvalStateNode, FilterCallback
    {
        private readonly FilterSpec filterSpec;
        private readonly String eventAsName;
        private readonly MatchedEventMap beginState;
        private readonly PatternContext context;

        private bool isStarted;

        /// <summary> Constructor.</summary>
        /// <param name="parentNode">is the parent evaluator to call to indicate truth value
        /// </param>
        /// <param name="filterSpec">is the filter definition
        /// </param>
        /// <param name="eventAsName">is the name to use to store the event
        /// </param>
        /// <param name="beginState">contains the events that make up prior matches
        /// </param>
        /// <param name="context">contains handles to services required
        /// </param>

        public EvalFilterStateNode(Evaluator parentNode, FilterSpec filterSpec, String eventAsName, MatchedEventMap beginState, PatternContext context)
            : base(parentNode)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

            this.filterSpec = filterSpec;
            this.eventAsName = eventAsName;
            this.beginState = beginState;
            this.context = context;
        }

        public override void Start()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".Start Starting filter expression");
            }

            if (isStarted)
            {
                throw new SystemException("Filter state node already active");
            }

            // Start the filter
            isStarted = true;
            StartFiltering();
        }

        public override void Quit()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".guardQuit Stop filter expression");
            }

            isStarted = false;
            StopFiltering();
        }

        private void EvaluateTrue(MatchedEventMap _event, bool isQuitted)
        {
            this.ParentEvaluator.EvaluateTrue(_event, this, isQuitted);
        }

        public void matchFound(EventBean _event)
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

            MatchedEventMap passUp = beginState.shallowCopy();

            // Add event itself to the match event structure if a tag was provided
            if (eventAsName != null)
            {
                passUp.Add(eventAsName, _event);
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

        public override Object Accept(EvalStateNodeVisitor visitor, Object data)
        {
            return visitor.visit(this, data);
        }

        public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
        {
            return data;
        }

        public override String ToString()
        {
            System.Text.StringBuilder buffer = new System.Text.StringBuilder();
            buffer.Append("EvalFilterStateNode spec=" + this.filterSpec);
            buffer.Append(" tag=" + this.filterSpec);
            return buffer.ToString();
        }

        private void StartFiltering()
        {
            FilterValueSet filterValues = filterSpec.getValueSet(beginState);
            context.FilterService.Add(filterValues, this);
        }

        private void StopFiltering()
        {
            context.FilterService.Remove(this);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalFilterStateNode));
    }
}
