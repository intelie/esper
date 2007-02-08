using System;

using net.esper.filter;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary> This class represents a filter of events in the evaluation tree representing any event expressions.</summary>
	public sealed class EvalFilterNode:EvalNode
	{
		/// <summary> Returns filter specification.</summary>
		/// <returns> filter definition
		/// </returns>
		public FilterSpec FilterSpec
		{
			get
			{
				return filterSpec;
			}
			
		}
		/// <summary> Returns the tag for any matching events to this filter, or null since tags are optional.</summary>
		/// <returns> tag string for event
		/// </returns>
		public String EventAsName
		{
			get
			{
				return eventAsName;
			}
			
		}

        private readonly FilterSpec filterSpec;
		private readonly String eventAsName;
		
		public override EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".newState");
			}
			
			if (ChildNodes.Count != 0)
			{
				throw new SystemException("Expected number of child nodes incorrect, expected no child nodes, found " + ChildNodes.Count);
			}
			
			return new EvalFilterStateNode(parentNode, filterSpec, eventAsName, beginState, context);
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="filterSpecification">specifies the filter properties
		/// </param>
		/// <param name="eventAsName">is the name to use for adding matching events to the MatchedEventMap
		/// table used when indicating truth value of true.
		/// </param>
		public EvalFilterNode(FilterSpec filterSpecification, String eventAsName)
		{
			this.filterSpec = filterSpecification;
			this.eventAsName = eventAsName;
		}
		
		public override String ToString()
		{
			System.Text.StringBuilder buffer = new System.Text.StringBuilder();
			buffer.Append("EvalFilterNode spec=" + this.filterSpec.ToString());
			buffer.Append(" eventAsName=" + this.eventAsName);
			return buffer.ToString();
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalFilterNode));
	}
}