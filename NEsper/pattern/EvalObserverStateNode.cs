using System;

using net.esper.pattern.observer;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	
	
	/// <summary> This class represents the state of an eventObserver sub-expression in the evaluation state tree.</summary>
	public sealed class EvalObserverStateNode:EvalStateNode, ObserverEventEvaluator
	{
		private EventObserver eventObserver;
		
		/// <summary> Constructor.</summary>
		/// <param name="parentNode">is the parent evaluator to call to indicate truth value
		/// </param>
		/// <param name="observerFactory">is the observer factory that makes observer instances
		/// </param>
		/// <param name="beginState">contains the events that make up prior matches
		/// </param>
		/// <param name="context">contains handles to services required
		/// </param>
		public EvalObserverStateNode(Evaluator parentNode, ObserverFactory observerFactory, MatchedEventMap beginState, PatternContext context):base(parentNode)
		{
			
			if (log.IsDebugEnabled)
			{
				log.Debug(".constructor");
			}
			
			eventObserver = observerFactory.makeObserver(context, beginState, this);
		}
		
		public void  observerEvaluateTrue(MatchedEventMap matchEvent)
		{
			this.ParentEvaluator.EvaluateTrue(matchEvent, this, true);
		}
		
		public void  observerEvaluateFalse()
		{
			this.ParentEvaluator.EvaluateFalse(this);
		}
		
		public override void  Start()
		{
			eventObserver.StartObserve();
		}
		
		public override void  Quit()
		{
			eventObserver.StopObserve();
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
			return "EvalObserverStateNode eventObserver=" + eventObserver;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalObserverStateNode));
	}
}
