using System;
namespace net.esper.pattern
{
	
	/// <summary> Superclass of all state nodes in an evaluation node tree representing an event expressions.
	/// Follows the Composite pattern. Subclasses are expected to keep their own collection containing child nodes
	/// as needed.
	/// </summary>
	public abstract class EvalStateNode
	{
		//UPGRADE_NOTE: Respective javadoc comments were merged.  It should be changed in order to comply with .NET documentation conventions. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1199'"
		/// <summary> Returns the parent evaluator.</summary>
		/// <returns> parent evaluator instance
		/// </returns>
		/// <summary> Sets the parent evaluator.</summary>
		/// <param name="parentEvaluator">for this node
		/// </param>
		virtual internal Evaluator ParentEvaluator
		{
			get
			{
				return parentEvaluator;
			}
			
			set
			{
				this.parentEvaluator = value;
			}
			
		}
		private Evaluator parentEvaluator;
		
		/// <summary> Starts the event expression or an instance of it.
		/// Child classes are expected to initialize and Start any event listeners
		/// or schedule any time-based callbacks as needed.
		/// </summary>
		public abstract void  Start();
		
		/// <summary> Stops the event expression or an instance of it. Child classes are expected to free resources
		/// and Stop any event listeners or remove any time-based callbacks.
		/// </summary>
		public abstract void  Quit();
		
		/// <summary> Accept a visitor. Child classes are expected to invoke the visit method on the visitor instance
		/// passed in.
		/// </summary>
		/// <param name="visitor">on which the visit method is invoked by each node
		/// </param>
		/// <param name="data">any additional data the visitor may need is passed in this parameter
		/// </param>
		/// <returns> any additional data the visitor may need or null
		/// </returns>
		public abstract Object Accept(EvalStateNodeVisitor visitor, Object data);
		
		/// <summary> Pass the visitor to all child nodes.</summary>
		/// <param name="visitor">is the instance to be passed to all child nodes
		/// </param>
		/// <param name="data">any additional data the visitor may need is passed in this parameter
		/// </param>
		/// <returns> any additional data the visitor may need or null
		/// </returns>
		public abstract Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data);
		
		/// <summary> Constructor.</summary>
		/// <param name="parentNode">is the evaluator for this node on which to indicate a change in truth value
		/// </param>
		internal EvalStateNode(Evaluator parentNode)
		{
			this.parentEvaluator = parentNode;
		}
	}
}
