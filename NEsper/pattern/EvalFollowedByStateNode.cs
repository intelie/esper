using System;
using System.Collections.Generic;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
	/// This class represents the state of a followed-by operator in the evaluation state tree.
	/// </summary>

	public sealed class EvalFollowedByStateNode : EvalStateNode, Evaluator
	{
		private readonly ELinkedList<EvalNode> childNodes;
		private readonly Dictionary<EvalStateNode, Int32> nodes;
		private readonly PatternContext context;

		/// <summary> Constructor.</summary>
		/// <param name="parentNode">is the parent evaluator to call to indicate truth value
		/// </param>
		/// <param name="childNodes">are the child nodes of the followed by node
		/// </param>
		/// <param name="beginState">contains the events that make up prior matches
		/// </param>
		/// <param name="context">contains handles to services required
		/// </param>

		public EvalFollowedByStateNode( Evaluator parentNode, ELinkedList<EvalNode> childNodes, MatchedEventMap beginState, PatternContext context )
			: base( parentNode )
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".constructor" );
			}

			this.nodes = new Dictionary<EvalStateNode, Int32>();
			this.childNodes = childNodes;
			this.context = context;

			EvalNode child = childNodes[ 0 ];
			EvalStateNode childState = child.NewState( this, beginState, context );
			nodes[childState] = 0;
		}

        /// <summary>
        /// Starts the event expression or an instance of it.
        /// Child classes are expected to initialize and Start any event listeners
        /// or schedule any time-based callbacks as needed.
        /// </summary>
		public override void Start()
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".Start Starting followed-by expression for the first child" );
			}

			if ( nodes.Count == 0 )
			{
				throw new SystemException( "Followed by state node is inactive" );
			}

            List<EvalStateNode> temp = new List<EvalStateNode>(nodes.Keys);
			foreach ( EvalStateNode child in temp )
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
		public void EvaluateTrue( MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted )
		{
			int index = nodes[ fromNode ];

			if ( log.IsDebugEnabled )
			{
				log.Debug( ".evaluateTrue index=" + index + "  fromNode=" + fromNode.GetHashCode() + "  isQuitted=" + isQuitted );
			}

			if ( isQuitted )
			{
				nodes.Remove( fromNode );
			}

			// If the match came from the very last filter, need to escalate
			int numChildNodes = childNodes.Count;
			if ( index == ( numChildNodes - 1 ) )
			{
				bool isFollowedByQuitted = false;
				if ( nodes.Count == 0 )
				{
					isFollowedByQuitted = true;
				}

				this.ParentEvaluator.EvaluateTrue( matchEvent, this, isFollowedByQuitted );
			}
			// Else Start a new listener for the next-in-line filter
			else
			{
				EvalNode child = childNodes[ index + 1 ];
				EvalStateNode childState = child.NewState( this, matchEvent, context );
				nodes[childState] = index + 1;
				childState.Start();
			}
		}

        /// <summary>
        /// Indicate a change in truth value to false.
        /// </summary>
        /// <param name="fromNode">is the node that indicates the change</param>
		public void EvaluateFalse( EvalStateNode fromNode )
		{
			log.Debug( ".evaluateFalse" );
		}

        /// <summary>
        /// Stops the event expression or an instance of it. Child classes are expected to free resources
        /// and Stop any event listeners or remove any time-based callbacks.
        /// </summary>
		public override void Quit()
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".guardQuit Stopping followed-by all children" );
			}

			foreach ( EvalStateNode child in nodes.Keys )
			{
				child.Quit();
			}
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
		public override Object Accept( EvalStateNodeVisitor visitor, Object data )
		{
			return visitor.Visit( this, data );
		}

        /// <summary>
        /// Pass the visitor to all child nodes.
        /// </summary>
        /// <param name="visitor">is the instance to be passed to all child nodes</param>
        /// <param name="data">any additional data the visitor may need is passed in this parameter</param>
        /// <returns>
        /// any additional data the visitor may need or null
        /// </returns>
		public override Object ChildrenAccept( EvalStateNodeVisitor visitor, Object data )
		{
			foreach ( EvalStateNode node in nodes.Keys )
			{
				node.Accept( visitor, data );
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
			return "EvalFollowedByStateNode nodes=" + nodes.Count;
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
