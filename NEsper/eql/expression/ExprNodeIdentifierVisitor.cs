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

		public virtual bool isVisit( ExprNode exprNode )
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

		public virtual void visit( ExprNode exprNode )
		{
			if ( !( exprNode is ExprIdentNode ) )
			{
				return;
			}

			ExprIdentNode identNode = (ExprIdentNode) exprNode;

			int streamId = identNode.StreamId;
			String propertyName = identNode.ResolvedPropertyName;

			exprProperties.Add( new Pair<Int32, String>( streamId, propertyName ) );
		}
	}
}
