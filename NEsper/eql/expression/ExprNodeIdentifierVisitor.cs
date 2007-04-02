using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.eql.expression
{
	/// <summary> Visitor that collects event property identifier information under expression nodes.
	/// The visitor can be configued to not visit aggregation nodes thus ignoring
	/// properties under aggregation nodes such as sum, avg, min/max etc.
	/// </summary>

	public class ExprNodeIdentifierVisitor : ExprNodeVisitor
	{
		private readonly IList<Pair<Int32, String>> exprProperties;
		private readonly bool isVisitAggregateNodes;

		/// <summary> Ctor.</summary>
		/// <param name="visitAggregateNodes">true to indicate that the visitor should visit aggregate nodes, or false
		/// if the visitor ignores aggregate nodes
		/// </param>
		public ExprNodeIdentifierVisitor( bool visitAggregateNodes )
		{
			this.isVisitAggregateNodes = visitAggregateNodes;
            this.exprProperties = new List<Pair<Int32, String>>();
		}

        /// <summary>
        /// Allows visitor to indicate whether to visit a given node.
        /// Implicitly if a visitor doesn't visit a node it would also not visit any descendent child nodes of that node.
        /// </summary>
        /// <param name="exprNode">is the node in questions</param>
        /// <returns>
        /// true if the visitor wants to visit the child node (next call is visit), or false to skip child
        /// </returns>
		public virtual bool IsVisit( ExprNode exprNode )
		{
			if ( isVisitAggregateNodes )
			{
				return true;
			}

			return ( !( exprNode is ExprAggregateNode ) );
		}

		/// <summary> Returns list of event property stream numbers and names that uniquely identify which
		/// property is from whcih stream, and the name of each.
		/// </summary>
		/// <returns> list of event property statement-unique info
		/// </returns>
		public IList<Pair<Int32, String>> ExprProperties
		{
			get { return exprProperties; }
		}

        /// <summary>
        /// Visit the given expression node.
        /// </summary>
        /// <param name="exprNode">is the expression node to visit</param>
		public virtual void Visit( ExprNode exprNode )
		{
			ExprIdentNode identNode = exprNode as ExprIdentNode ;
			if ( identNode == null )
			{
				return;
			}

			int streamId = identNode.StreamId;
			String propertyName = identNode.ResolvedPropertyName;

			exprProperties.Add( new Pair<Int32, String>( streamId, propertyName ) );
		}
	}
}
