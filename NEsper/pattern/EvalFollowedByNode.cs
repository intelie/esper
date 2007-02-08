using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
	/// This class represents a followed-by operator in the evaluation tree representing any event expressions.
	/// </summary>

	public sealed class EvalFollowedByNode : EvalNode
	{
		public override EvalStateNode newState( Evaluator parentNode, MatchedEventMap beginState, PatternContext context )
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".newState" );
			}

			if ( ChildNodes.Count <= 1 )
			{
				throw new SystemException( "Expected number of child nodes incorrect, expected >=2 child nodes, found " + ChildNodes.Count );
			}

			return new EvalFollowedByStateNode( parentNode, this.ChildNodes, beginState, context );
		}

		public override String ToString()
		{
			return ( "EvalFollowedByNode children=" + this.ChildNodes.Count );
		}

		private static readonly Log log = LogFactory.GetLog( typeof( EvalFollowedByNode ) );
	}
}